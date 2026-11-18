@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

:: =============================================
:: 配置项
:: =============================================
set "REDIS_PASS=123456"
set "REDIS_PORT=6379"
set "INSTALL_DIR=C:\Program Files\Redis"
set "DATA_DIR=E:\Redis\data"
set "LOG_DIR=E:\Redis\logs"

:: 项目内安装包文件名（和脚本同目录）
set "LOCAL_MSI=%~dp0Redis-x64-3.0.504.msi"
set "LOCAL_ZIP=%~dp0Redis-x64-3.2.100.zip"
set "DOWNLOAD_URL=https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.zip"

:: =============================================
:: 1. 检测管理员权限
:: =============================================
net session >nul 2>&1
if %errorlevel% neq 0 (
    echo 请右键选择「以管理员身份运行」此脚本
    pause
    exit /b 1
)

echo =============================================
echo 正在检测本机 Redis 安装情况...
echo =============================================

:: =============================================
:: 2. 自动查找已安装的 Redis
:: =============================================
set "REDIS_CLI="

:: 方式1：环境变量
where redis-cli >nul 2>&1
if %errorlevel% equ 0 (
    for /f "delims=" %%i in ('where redis-cli') do set "REDIS_CLI=%%i"
    goto :found
)

:: 方式2：常见安装目录
for %%d in (
    "C:\Program Files\Redis"
    "C:\Redis"
    "D:\Redis"
    "E:\Redis"
    "C:\Program Files (x86)\Redis"
) do (
    if exist "%%~d\redis-cli.exe" (
        set "REDIS_CLI=%%~d\redis-cli.exe"
        goto :found
    )
)

:: 方式3：服务注册表
for /f "tokens=2*" %%a in ('reg query "HKLM\SYSTEM\CurrentControlSet\Services\Redis" /v ImagePath 2^>nul') do (
    set "SVC_PATH=%%~b"
    set "SVC_PATH=!SVC_PATH:redis-server.exe=!"
    if exist "!SVC_PATH!redis-cli.exe" (
        set "REDIS_CLI=!SVC_PATH!redis-cli.exe"
        goto :found
    )
)

:: =============================================
:: 3. 未找到 → 优先本地安装，无本地包再下载
:: =============================================
:notfound
echo 未检测到 Redis，开始自动安装...
echo.

:: 创建数据、日志目录
if not exist "%DATA_DIR%" md "%DATA_DIR%"
if not exist "%LOG_DIR%" md "%LOG_DIR%"

:: 3.1 优先使用项目内的 MSI 安装包
if exist "%LOCAL_MSI%" (
    echo 检测到项目内 MSI 安装包，正在静默安装...
    msiexec /i "%LOCAL_MSI%" INSTALLDIR="%INSTALL_DIR%" /qn /norestart >nul 2>&1
    timeout /t 8 /nobreak >nul
    goto :config
)

:: 3.2 其次使用项目内的 ZIP 压缩包
if exist "%LOCAL_ZIP%" (
    echo 检测到项目内 ZIP 安装包，正在解压安装...
    if not exist "%INSTALL_DIR%" md "%INSTALL_DIR%"
    tar -xf "%LOCAL_ZIP%" -C "%INSTALL_DIR%" >nul 2>&1
    goto :config
)

:: 3.3 本地无包 → 在线下载
echo 项目内未找到安装包，正在在线下载...
set "ZIP_FILE=%temp%\Redis-x64-3.2.100.zip"
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%ZIP_FILE%' -UseBasicParsing}" >nul 2>&1

if not exist "%ZIP_FILE%" (
    echo 下载失败，请将 Redis 安装包放入 init 文件夹后重试
    pause
    exit /b 1
)

if not exist "%INSTALL_DIR%" md "%INSTALL_DIR%"
tar -xf "%ZIP_FILE%" -C "%INSTALL_DIR%" >nul 2>&1
del /f /q "%ZIP_FILE%" >nul 2>&1

:config
if not exist "%INSTALL_DIR%\redis-server.exe" (
    echo 安装失败，请检查安装包完整性
    pause
    exit /b 1
)

:: 生成标准化配置文件
echo 正在生成配置文件...
(
echo port %REDIS_PORT%
echo bind 127.0.0.1
echo timeout 300
echo requirepass %REDIS_PASS%
echo databases 16
echo save 900 1
echo save 300 10
echo save 60 10000
echo stop-writes-on-bgsave-error no
echo dbfilename dump.rdb
echo dir %DATA_DIR:\=/%
echo maxmemory 536870912
echo maxmemory-policy allkeys-lru
echo loglevel notice
echo logfile %LOG_DIR:\=/%/redis.log
) > "%INSTALL_DIR%\redis.windows.conf"

:: 注册系统服务并启动
echo 正在注册 Windows 系统服务...
cd /d "%INSTALL_DIR%"
redis-server --service-install redis.windows.conf --service-name Redis >nul 2>&1
redis-server --service-start --service-name Redis >nul 2>&1

timeout /t 3 /nobreak >nul
set "REDIS_CLI=%INSTALL_DIR%\redis-cli.exe"
echo Redis 安装完成，服务已启动
echo.

:: =============================================
:: 4. 清理项目缓存
:: =============================================
:found
echo 已找到 Redis：%REDIS_CLI%
echo.
echo 正在清理留言板项目所有缓存...

for /f "delims=" %%k in ('"%REDIS_CLI%" -h 127.0.0.1 -p %REDIS_PORT% -a %REDIS_PASS% --raw keys "msg_board:*" 2^>nul') do (
    "%REDIS_CLI%" -h 127.0.0.1 -p %REDIS_PORT% -a %REDIS_PASS% del "%%k" >nul 2>&1
)

echo.
echo =============================================
echo 操作完成！项目缓存已全部清空
echo =============================================
pause
endlocal
