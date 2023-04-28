if [ $# -lt 2 ]; then
    echo "Usage: $0 [ContainerName] [Port]"
    exit 1
fi

NAME=$1
PORT=6379
if [[ $2 =~ ^[0-9]+$ && $2 -gt 0 ]]; then
  PORT=$2
else
  echo "Port must be a positive integer!"
  exit 1
fi

ROOT_PATH=/workspace/redis/${NAME}

docker run --restart=always -d \
 --log-opt max-size=100m \
 --log-opt max-file=2 \
 -p ${PORT}:6379 \
 --name ${NAME} \
 -v ${ROOT_PATH}/myredis.conf:/etc/redis/redis.conf \
 -v ${ROOT_PATH}/data:/data \
 redis \
 redis-server /etc/redis/redis.conf  --appendonly yes  --requirepass root

