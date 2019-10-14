@echo off
set printline_start=#####
echo %printline_start% initializing aws region, account_id, repo_name, image_name, image_tag
for /f "delims=" %%F in ('aws configure get region')do (
    set region=%%F
)
for /f "delims=" %%G in ('aws sts get-caller-identity --output text')do (
    set z=%%G
    set account_id=%z:~0,12%
)
set repo_name=ubi-repo-cli
set image_name=ubisoft-sample-ssh
set image_tag=latest
echo %printline_start% using region=%region%, account_id=%account_id%, repo_name=%repo_name%, image_name=%image_name%, image_tag=%image_tag%

echo %printline_start% cleaning docker image
docker rmi %account_id%.dkr.ecr.%region%.amazonaws.com/%repo_name%:%image_tag%
for /f "delims=" %%F in ('docker images -q %image_name%') do docker rmi %%F 
echo %printline_start% removed docker image

echo %printline_start% building docker image
cd MyApp
call mvnw package docker:build 
cd ..
echo %printline_start% created docker image

echo %printline_start% preparing upload image to repo
aws ecr delete-repository --repository-name %repo_name% --force >nul
for /f "delims=" %%F in ('aws ecr get-login --no-include-email --region %region%') do %%F%
aws ecr create-repository --repository-name %repo_name% >nul
echo %printline_start% tagging image and starting upload
docker tag %image_name%:%image_tag% %account_id%.dkr.ecr.%region%.amazonaws.com/%repo_name%:%image_tag% >nul
call docker push %account_id%.dkr.ecr.%region%.amazonaws.com/%repo_name%:%image_tag%
echo %printline_start% uploaded docker image

echo %printline_start% installing node modules if required
call npm i
echo %printline_start% installation completed

echo %printline_start% invoking aws script
node aws-script.js %region% %account_id% %repo_name% %image_tag%
echo %printline_start% aws script completed - exiting

for /f "delims=" %%F in ('aws sts get-caller-identity --output text') 