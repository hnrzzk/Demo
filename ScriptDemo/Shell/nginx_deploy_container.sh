#!/bin/bash
shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

#判断参数个数
if [ $# -lt 1 ]; then
    echo "Usage: $0 [ContainerName]"
    exit 1
fi

container_name=$1
# export_port=$2
root_path=/data/nginx/${container_name}

# #判断第二个参数是否是正整数
# if [[ $2 =~ ^[0-9]+$ && $2 -gt 0 ]]; then
#   export_port=$2
# else
#   echo "Port must be a positive integer!"
#   exit 1
# fi

docker pull nginx

nginx_temp_name=nginx_temp
docker run --name ${nginx_temp_name} nginx

mkdir -p ${root_path}/conf.d 
mkdir -p ${root_path}/html
mkdir -p ${root_path}/logs
mkdir -p ${root_path}/conf/nginx.conf

docker cp ${nginx_temp_name}:/etc/nginx/nginx.conf ${root_path}/conf/nginx.conf
docker cp ${nginx_temp_name}:/etc/nginx/conf.d ${root_path}/conf.d
docker cp ${nginx_temp_name}:/usr/share/nginx/html ${root_path}/html

docker stop ${nginx_temp_name} 
docker rm ${nginx_temp_name}

docker create --net=host --name ${container_name} --restart=always \
-v ${root_path}/conf/nginx.conf/:/etc/nginx/nginx.conf/ \
-v ${root_path}/conf.d/:/etc/nginx/conf.d/ \
-v ${root_path}/html/:/usr/share/nginx/html/ \
-v ${root_path}/logs/:/var/log/nginx/ \
-d  nginx