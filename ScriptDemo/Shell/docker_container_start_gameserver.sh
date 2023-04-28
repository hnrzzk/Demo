#!/bin/bash

shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

# 定义变量
containers_name=
http_port=
type=
image_name=
root_path=
config_path=
target_config_path=

config_file_name="config.properties"
#容器停止超时时间
stop_timeout_second=3600

function update_containers() {
    # 更新操作
    echo "stop container ${containers_name} ..."
    docker stop -t ${stop_timeout_second} ${containers_name}
    echo ""

    echo "rm container ${containers_name} ... "
    docker rm ${containers_name}
    echo ""

    echo "rm old image ${image_name} ... "
    docker image rm -f ${image_name}
    echo ""

    echo "pull new image ${image_name} ... "
    docker pull ${image_name}
    echo ""

    echo "create new container ${containers_name} ... "
    docker create \
        --name=${containers_name} \
        --net=host \
        -e START_TYPE=${type} \
        -e APP_NAME=${containers_name} \
        -e SERVER_DEBUG=true \
        -e TZ=Asia/Shanghai \
        ${image_name}

    #将target_config_path从容器中考到config_path
    echo "data dir is ${config_path}"
    if [ -e "$config_path/$config_file_name" ]; then
        echo "$config_path/$config_file_name exist."
    else
        echo "$config_path/$config_file_name not exist."
    	echo "create dir ${config_path}"
        mkdir -p ${config_path}
        docker start ${containers_name}
        docker cp ${containers_name}:${target_config_path} ${config_path}
    fi

    docker rmi $(docker images -a | grep none | awk '{print $3}')
    echo "${containers_name} update finished."
    
    #强制删除所有未被使用的docker镜像，不需要人工确认。
    docker image prune -f
}

function start_containers() {
    # 启动操作
    docker restart -t ${stop_timeout_second} ${containers_name}
    num=1
    sleep 5
    # 判断容器是否在运行
    if [ ! "$(docker ps -q -f name=${containers_name})" ]; then
        echo "container ${containers_name} not running!"
        exit 1
    fi
}

# 判断参数个数是否正确
if [ $# -lt 1 ]; then
    echo "Usage: $0 [update|start]"
    exit 1
fi

if [ "$1" == "update" ]; then
    update_containers
elif [ "$1" == "start" ]; then
    start_containers
else
    echo "Usage: $0 [update|start]"
    exit 1
fi
