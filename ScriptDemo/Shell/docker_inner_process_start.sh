#!/bin/bash
robot_host

function exportBot() {
    curl ${robot_host} \
       -H 'Content-Type: application/json' \
       -d '
       {
            "msgtype": "text",
            "text": {"content": "'"${error_msg}"'"}
       }'
}

shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

java -version
if [ -z "${APP_NAME}" ]; then
    APP_NAME=Docker_Server
    echo "use default APP_NAME ${APP_NAME}"
fi

if [ -z "${JAVA_PARAM}" ]; then
    JAVA_PARAM="-Xmx1024m -Xms1024m -Xmn384m"
    echo "use default JAVA_PARAM:${JAVA_PARAM}"
fi

JAVA_PARAMS="${JAVA_MEM_PARAM} -XX:+PrintGC -XX:+PrintGCDetails -Xloggc:gc.log --add-opens java.base/java.math=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED -Dzookeeper.sasl.client=false"

if [ -z "${PROD}" ]; then
    PROD=test
    echo "use default PROD:${PROD}"
fi

echo ${APP_NAME} to start
exec java $JAVA_PARAMS org/springframework/boot/loader/JarLauncher --spring.profiles.active=$PROD --$APP_NAME

end_time=$(date "+%Y-%m-%d %H:%M:%S")
num=0
error_msg="${APP_NAME} exited on ${end_time}"
echo ${error_msg}
exportBot "${error_msg}"

if [ "${SERVER_DEBUG}" == "true" ]; then
    while true
    do
        echo "wait manual exit ${num}s... "
        num=$((num + 10))
        sleep 10
    done
fi