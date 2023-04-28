#!/bin/bash
shell_path=$(dirname $0)
echo "cd to filePath:${shell_path}"
cd $shell_path

source_file=./config/config.properties
target_file=./BOOT-INF/classes/application.properties

dos2unix source_file
dos2unix target_file
# 读取config.properties文件 并忽略掉包含特殊符号的行
grep -v '^[[:space:]]*$' ${source_file} | while IFS='=' read -r key value; do
  # 忽略注释行和空行
   if [[ $key == \#* ]] || [[ -z "${key// }" ]]; then
      echo "skip empty line"
      continue
  fi

  # 判断是否存在只有key没有value的行
  if [[ -z $value ]]; then
    echo "Error: $key has no value"
    exit 1
  fi

  # 在application.properties中替换或追加配置项
  if grep -q "^$key=" ${target_file}; then
    sed -i "s/^$key=.*/$key=$value/" ${target_file}
  else
    echo "$key=$value" >> ${target_file}
  fi
done