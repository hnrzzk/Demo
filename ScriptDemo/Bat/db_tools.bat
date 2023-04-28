cd /d %~dp0

@echo off
REM 开启环境变量延迟扩展，也就是在运行时才扩展变量。这样可以避免在批处理脚本中出现一些问题，如变量重定向、嵌套变量等。
setlocal EnableDelayedExpansion

set "inst=0"
set "center_config=1"
set "zone_config=2"

set /p "input=Please enter the index (0 for inst, 1 for center_config, 2 for zone_config): "

if "%input%" == "" (
  set /a "index=%inst%"
) else (
  set /a "index=%input%"
)

set /a "index+=0" 2>nul

if "%index%" == "%inst%" (
  echo "inst" selected
) else if "%index%" == "%center_config%" (
  echo "center_config" selected
) else if "%index%" == "%zone_config%" (
  echo "zone_config" selected
) else (
  echo "Invalid index. Please choose from the following:"
  echo "0 - inst"
  echo "1 - center_config"
  echo "2 - zone_config"
  exit
)

java -Dfile.encoding=utf-8 -jar lib/tools.jar 0 %index%
pause