shell_path=$(dirname $0)
echo "filePath: ${shell_path}"
cd $shell_path
p4 reconcile -f -m ...