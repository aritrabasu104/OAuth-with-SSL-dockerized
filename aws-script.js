var AWS = require("aws-sdk");
const log = console.log;
regionProvided = process.argv[2];
accoundId = process.argv[3];
repo_name=process.argv[4];
image_tag=process.argv[5];

// region not reflected correctly in node sdk
AWS.config.update({ region: regionProvided });
var ecs = new AWS.ECS();

var clusterParams = {
  clusterName: "ubi_cluster_node"
};
var taskParams = {
  executionRoleArn: "arn:aws:iam::"+accoundId+":role/ecsTaskExecutionRole",
  containerDefinitions: [
    {
      logConfiguration: {
        logDriver: "awslogs",
        options: {
          "awslogs-group": "/ecs/ubi-task",
          "awslogs-region": regionProvided,
          "awslogs-stream-prefix": "ecs"
        }
      },
      portMappings: [
        {
          hostPort: 8080,
          protocol: "tcp",
          containerPort: 8080
        },
        {
          hostPort: 8443,
          protocol: "tcp",
          containerPort: 8443
        }
      ],
      essential: true,
      image: accoundId+".dkr.ecr."+regionProvided+".amazonaws.com/"+repo_name+":"+image_tag,
      memoryReservation: 100,
      name: "ubisoft-cont"
    }
  ],
  memory: "1024",
  taskRoleArn: "arn:aws:iam::"+accoundId+":role/ecsTaskExecutionRole",
  family: "ubi-task",
  requiresCompatibilities: [
    "FARGATE"
  ],

  networkMode: "awsvpc",
  cpu: "512",
};

var serviceParams = {
  desiredCount: 1,
  serviceName: "ecs-ubisoft-service-node",
  taskDefinition: "ubi-task",
  cluster: clusterParams.clusterName,
  deploymentController: {
    type: "ECS"
  },
  launchType: "FARGATE",
  networkConfiguration: {
    awsvpcConfiguration: {
      subnets: [ /* required */
        'subnet-aeadfad4',
        /* more items */
      ],
      assignPublicIp: "ENABLED",
      securityGroups: [
        'sg-0682a728d70d04298',
        /* more items */
      ]
    }
  },
};


ecs.createCluster(clusterParams, function (err, data) {
  if (err) log(err, err.stack); // an error occurred
  //else log(data);           // successful response

}).promise().then(() => {
  ecs.registerTaskDefinition(taskParams, function (err, data) {
    if (err) log(err, err.stack); // an error occurred
    //else log(data);
  })
})
.then(() => {

  ecs.listServices({ cluster: serviceParams.cluster }, function (err, data) {
    if (err) log(err, err.stack); // an error occurred
    else return data;
  }).promise().then((services) => {
    return services.serviceArns.filter(item => item.split("/")[1] === serviceParams.serviceName).length > 0;
  }).then((flag) => {
    if (flag) {
      ecs.updateService({ service: serviceParams.serviceName, cluster: serviceParams.cluster, desiredCount: 0 }, function (err, data) {
        if (err) log(err, err.stack); // an error occurred
        //else log(data);           // successful response

      })
    }
    return flag;
  }).then((flag) => {
    if (flag) {
      ecs.deleteService({ service: serviceParams.serviceName, cluster: serviceParams.cluster }, function (err, data) {
        if (err) log(err, err.stack); // an error occurred
        //else log(data);           // successful response
      });
    }
  return flag;
}).then((flag) => {
  setTimeout(() => ecs.createService(serviceParams, function (err, data) {
    if (err) log(err, err.stack); // an error occurred
    //else log(data);
  }), flag ? 80000 : 0);
});

});