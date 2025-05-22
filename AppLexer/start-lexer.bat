@echo off
setlocal enabledelayedexpansion

set START_PORT=8081
set INSTANCES=5
set COUNT=0
set PORT=%START_PORT%

:next_instance
netstat -aon | findstr :%PORT% >nul
if %errorlevel%==0 (
    echo El puerto %PORT% está en uso. Buscando siguiente...
    set /a PORT+=1
    goto next_instance
) else (
    echo Levantando instancia de lexer en el puerto %PORT%
    start cmd /k "mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=%PORT%"
    set /a COUNT+=1
    set /a PORT+=1
)

if %COUNT% lss %INSTANCES% (
    timeout /t 2 > nul
    goto next_instance
)

pause