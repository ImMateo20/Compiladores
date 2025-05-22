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
    echo Levantando instancia de compilador en el puerto %PORT%
    start cmd /k "mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=%PORT%"
    set /a COUNT+=1
    set /a PORT+=1
)

if %COUNT% lss %INSTANCES% (
    timeout /t 2 > nul
    goto next_instance
)

pause

@REM @echo off
@REM setlocal enabledelayedexpansion

@REM set START_PORT=8081
@REM set INSTANCES=5

@REM for /l %%i in (0,1,%INSTANCES%) do (
@REM     set /a PORT=!START_PORT! + %%i
@REM     echo Levantando instancia de parser en el puerto !PORT!
@REM     start cmd /k "mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=!PORT!"
@REM     timeout /t 3 > nul
@REM )

@REM pause




@REM @echo off

@REM echo Levantar Compilador en puerto 8081.
@REM start cmd /k "mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081"

@REM timeout /t 5

@REM echo Levantar Compilador en puerto 8085.
@REM start cmd /k "mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085"

@REM pause
