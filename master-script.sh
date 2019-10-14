#!/bin/bash

printline_start="#####"
echo "$printline_start initializing aws region, account_id, repo_name, image_name, image_tag"
region="$(aws configure get region)"
temp="$(aws sts get-caller-identity --output text)"
account_id=${temp:0:12}
repo_name="ubi-repo-cli"
image_name="ubisoft-sample-ssh"
image_tag="latest"
echo "$printline_start using region=$region, account_id=$account_id, repo_name=$repo_name, image_name=$image_name, image_tag=$image_tag"

echo "$printline_start cleaning docker image"
docker rmi $account_id.dkr.ecr.$region.amazonaws.com/$repo_name:$image_tag 
docker rmi $(docker images -q $image_name) 
echo "$printline_start removed docker image"

echo "$printline_start building docker image"
cd MyApp
./mvnw package docker:build "-Dimage_name=$image_name"
cd ..
echo "$printline_start created docker image"

echo "$printline_start preparing upload image to repo"
aws ecr delete-repository --repository-name $repo_name --force > /dev/null
$(aws ecr get-login --no-include-email --region "$region")
aws ecr create-repository --repository-name $repo_name > /dev/null
echo "$printline_start tagging image and starting upload"
docker tag $image_name:$image_tag $account_id.dkr.ecr.$region.amazonaws.com/$repo_name:$image_tag >nul
docker push $account_id.dkr.ecr.$region.amazonaws.com/$repo_name:$image_tag
echo "$printline_start uploaded docker image"

echo "$printline_start installing node modules if required"
npm i
echo "$printline_start installation completed"

echo "$printline_start invoking aws script"
node aws-script.js $region $account_id $repo_name $image_tag
echo "$printline_start aws script completed - exiting"
