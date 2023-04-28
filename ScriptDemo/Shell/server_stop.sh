#!/bin/bash

java -version
APP_NAME=

PID=$(pgrep -f ${APP_NAME})

for id in $PID
do
  kill "$id"
  echo "kill $id"
done

num=1
while true
do
    old_pid=$(pgrep -f ${APP_NAME})
    if [ -z "$old_pid" ];
    then
         echo "$PID stopped"
         break;
    else
         echo "wait $old_pid stop ${num}s... "
         num=$((num + 1))
         sleep 1
    fi
done