@echo off
echo ##### checking aws region,account_id
for /f "delims=" %%F in ('aws configure get region')do (
    set region=%%F
)
for /f "delims=" %%G in ('aws sts get-caller-identity --output text')do (
    set z=%%G
    set account_id=%z:~0,12%
)

echo ##### cleaning docker image
docker rmi %account_id%.dkr.ecr.%region%.amazonaws.com/ubi-repo-cli:latest
for /f "delims=" %%F in ('docker images -q ubisoft-sample-ssh') do docker rmi %%F 
echo ##### removed docker image

cd MyApp

echo ##### building docker image
call mvnw package docker:build 
cd ..
echo ##### created docker image

echo ##### preparing upload image to repo
aws ecr delete-repository --repository-name ubi-repo-cli --force >nul
for /f "delims=" %%F in ('aws ecr get-login --no-include-email --region %region%') do %%F%
aws ecr create-repository --repository-name ubi-repo-cli >nul
echo ##### tagging image and starting upload
docker tag ubisoft-sample-ssh:latest %account_id%.dkr.ecr.%region%.amazonaws.com/ubi-repo-cli:latest >nul
call docker push %account_id%.dkr.ecr.%region%.amazonaws.com/ubi-repo-cli:latest
echo ##### uploaded docker image

echo ##### installing node modules if required
call npm i
echo ##### installation completed

echo ##### invoking aws script
node aws-script.js
echo ##### aws script completed - exiting

for /f "delims=" %%F in ('aws sts get-caller-identity --output text') 