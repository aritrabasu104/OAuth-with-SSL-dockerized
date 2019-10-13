#!/bin/sh

echo "##### checking aws region,account_id"
region="$(aws configure get region)"
temp="$(aws sts get-caller-identity --output text)"
account_id=${temp:0:12}

echo "##### cleaning docker image"
docker rmi $account_id.dkr.ecr.$region.amazonaws.com/ubi-repo-cli:latest 
docker rmi $(docker images -q ubisoft-sample-ssh) 
echo "##### removed docker image"

cd MyApp

echo "##### building docker image"
./mvnw package docker:build
cd ..
echo "##### created docker image"

echo "##### preparing upload image to repo"
aws ecr delete-repository --repository-name ubi-repo-cli --force > /dev/null
$(aws ecr get-login --no-include-email --region "$region")
aws ecr create-repository --repository-name ubi-repo-cli > /dev/null
echo "##### tagging image and starting upload"
docker tag ubisoft-sample-ssh:latest $account_id.dkr.ecr.$region.amazonaws.com/ubi-repo-cli:latest >nul
docker push $account_id.dkr.ecr.$region.amazonaws.com/ubi-repo-cli:latest
echo "##### uploaded docker image"

echo "##### installing node modules if required"
npm i
echo "##### installation completed"

echo "##### invoking aws script"
node aws-script.js
echo "##### aws script completed - exiting"
