#!/bin/bash

# 定义列表
servers=(
    "loginserver"
    "gameserver"
)

function check_config() {
  if [ ! -f "$1/config/config.properties" ]; then
    cp "$1/BOOT-INF/classes/config.properties" "$1/config/"
    echo "Check failed! config.properties not exist! Copied config.properties to $1/config/"
    echo "Please configure local properties in the config.properties file."
    exit 1
  fi
}

# 获取环境变量START_TYPE并转为小写
start_type=$(echo "$START_TYPE" | tr '[:upper:]' '[:lower:]')

root_path=/home/server
# 判断START_TYPE是否在列表中
if [[ " ${servers[@]} " =~ " $start_type " ]]; then
    echo "chekc config.properteis"
    root_path=${root_path}/${start_type}
    check_config "${root_path}"
    echo "start ${start_type}"

    bash ${root_path}/properties_sync.sh
    if [ $? -ne 0 ]; then
        if [ "$SERVER_DEBUG" = "true" ]; then
            while true; do
                echo "sync properties failed! start failed!"
                sleep 10
            done
        else
            echo "sync properties failed! start failed!"
            exit 1
        fi
    fi

    exec bash ${root_path}/docker_inner_process_start.sh
else
    echo "${start_type} not exist in support list"
fi