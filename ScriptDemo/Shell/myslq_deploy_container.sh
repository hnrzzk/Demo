if [ $# -lt 2 ]; then
    echo "Usage: $0 [ContainerName] [Port]"
    exit 1
fi

NAME=$1
PORT=6379
password=root
if [[ $2 =~ ^[0-9]+$ && $2 -gt 0 ]]; then
  PORT=$2
else
  echo "Port must be a positive integer!"
  exit 1
fi

ROOT_PATH=/workspace/mysql_data/$NAME
mkdir -p ${ROOT_PATH}/conf/conf.d
docker run --name $NAME -p $PORT:3306 -e MYSQL_ROOT_PASSWORD=${password} -v ${ROOT_PATH}/conf:/etc/mysql/ -v ${ROOT_PATH}/data:/var/lib/mysql -v ${ROOT_PATH}/logs:/logs -d mysql
