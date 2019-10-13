#!/bin/sh
echo "##### cleaning docker image"
docker images -q ubisoft-sample-ssh | docker rmi
echo "##### removed docker image"

cd MyApp

echo "##### building docker image"
./mvnw package docker:build
cd ..
echo "##### created docker image"

echo "##### installing node modules if required"
npm i
echo "##### installation completed"

echo "##### invoking aws script"
node aws.js
echo "##### aws script completed - exiting"
