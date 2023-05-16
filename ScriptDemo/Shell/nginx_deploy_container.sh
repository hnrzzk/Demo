#!/bin/bash

#判断参数个数
if [ $# -ne 1 ] && [ $# -ne 2 ] ; then
  echo "Usage: $0 [init | update] [container_name]"
  exit 1
fi

if [ "$1" != "init" ] && [ "$1" != "update" ] ; then
  echo "Invalid argument: $1"
  echo "Usage: $0 [init | update] [container_name]"
  exit 1
fi

shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

container_name=$2
root_path=/data/nginx/${container_name}

if [ "$1" == "init" ]; then
  docker pull nginx
  nginx_temp_name=nginx_temp
  docker run -d --name ${nginx_temp_name} nginx

  mkdir -p ${root_path}/conf
  mkdir -p ${root_path}/html
  mkdir -p ${root_path}/logs

  docker cp ${nginx_temp_name}:/etc/nginx/nginx.conf ${root_path}/conf/nginx.conf
  docker cp ${nginx_temp_name}:/etc/nginx/conf.d ${root_path}/conf/conf.d
  docker cp ${nginx_temp_name}:/usr/share/nginx/html ${root_path}/html

  docker stop ${nginx_temp_name}
  docker rm ${nginx_temp_name}
else
 docker stop ${container_name}
 docker rm ${container_name}

 docker create \
 --network host \
 --name ${container_name} --restart=always \
 -v ${root_path}/conf/nginx.conf:/etc/nginx/nginx.conf \
 -v ${root_path}/conf/conf.d/:/etc/nginx/conf.d/ \
 -v ${root_path}/html/:/usr/share/nginx/html/ \
 -v ${root_path}/logs/:/var/log/nginx/ \
 -v ${root_path}/data/:/home/nginx/data/ \
 nginx
fi

