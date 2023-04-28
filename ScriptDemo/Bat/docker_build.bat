cd /d %~dp0
call mvn clean package -U -Dmaven.test.skip=true
if %ERRORLEVEL% neq 0 (
    echo Maven command failed
    exit /b 1
)

set harbor_host=#{harbor host}
set docker_version=#{version}
set image_name=#{name}

docker login %harbor_host% -u #{user} -p #{port}

docker image rm -f %image_name%:%docker_version%
docker build -t %image_name%:%docker_version% .
if %errorlevel% neq 0 (
    echo Docker 构建失败，退出脚本
    exit /b %errorlevel%
)

docker tag %image_name%:%docker_version% %harbor_host%/#{path}/%image_name%:%docker_version%
docker push %harbor_host%/#{path}/%image_name%:%docker_version%

for /f "tokens=*" %%a in ('docker images -f "dangling=true" -q') do (
    docker rmi -f %%a
)
pause