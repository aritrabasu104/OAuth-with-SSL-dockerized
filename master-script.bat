@echo off
echo ##### cleaning docker image
for /f "delims=" %%F in ('docker images -q ubisoft-sample-ssh') do docker rmi %%F
echo ##### removed docker image

cd MyApp

echo ##### building docker image
call mvnw docker:build
cd ..
echo ##### created docker image

echo ##### installing node modules if required
call npm i
echo ##### installation completed

echo ##### invoking aws script
node aws.js
echo ##### aws script completed - exiting