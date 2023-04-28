#!/bin/bash
shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

#判断参数个数
if [ $# -lt 2 ]; then
    echo "Usage: $0 [ContainerName] [Port]"
    exit 1
fi

container_name=$1
export_port=$2

#判断第二个参数是否是正整数
if [[ $2 =~ ^[0-9]+$ && $2 -gt 0 ]]; then
  export_port=$2
else
  echo "Port must be a positive integer!"
  exit 1
fi

docker pull nginx

nginx_temp_name=nginx_temp
docker run --name ${nginx_temp_name} nginx


