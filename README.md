# OAuth-with-SSL-dockerized
This is a sample app with SSL and OAuth that creates docker containers,uploads it in AWS ECR and deployes it using AWS ECS - all done in a single master script.
## Requirements 

#### Installations : 
1. node
2. docker 
3. aws-cli
4. JDK 1.8


#### Configurations : 
1. aws credentials and region
2. Facebook app Id and app secret for OAuth(Need to substitute these values in $Project/MyApp/src/main/resources/application.yml) 