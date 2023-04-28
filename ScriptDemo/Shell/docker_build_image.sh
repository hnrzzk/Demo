#!/bin/bash
cd "$(dirname "$0")"
mvn clean package -U -Dmaven.test.skip=true
if [ $? -ne 0 ]; then
    echo "Maven command failed"
    exit 1
fi


docker_version= 
image_name=
harbor_host=
harbor_user=
harbor_port=
harbor_path=
docker login ${harbor_host} -u ${harbor_user} -p ${harbor_port}
docker image rm -f "$image_name:$docker_version"
docker build -t "$image_name:$docker_version" .
if [ $? -ne 0 ]; then
    echo "Docker build failed，exit!"
    exit $?
fi

docker tag "$image_name:$docker_version" ${harbor_host}/${harbor_path}/"$image_name:$docker_version"
docker push ${harbor_host}/${harbor_path}/"$image_name:$docker_version"

for image_id in $(docker images -f "dangling=true" -q); do
    docker rmi -f "$image_id"
done