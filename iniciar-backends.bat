@echo off
REM Levanta 3 instancias de la app en los puertos 8081, 8082 y 8083.
REM Ejecutar desde la carpeta del proyecto (donde esta target\app.jar).

cd /d "%~dp0"

if not exist "target\app.jar" (
    echo No se encontro target\app.jar. Primero ejecuta: mvnw.cmd clean package -DskipTests
    pause
    exit /b 1
)

echo Iniciando Backend 1 (8081)...
start "Backend 8081" cmd /k java -jar target\app.jar --server.port=8081
timeout /t 12 /nobreak >nul

echo Iniciando Backend 2 (8082)...
start "Backend 8082" cmd /k java -jar target\app.jar --server.port=8082
timeout /t 12 /nobreak >nul

echo Iniciando Backend 3 (8083)...
start "Backend 8083" cmd /k java -jar target\app.jar --server.port=8083

echo.
echo Listo. Espera a que las 3 ventanas digan "Started LabBalanceoApplication".
pause
