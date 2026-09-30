@echo off
REM Cierra solo las 3 ventanas de backend (no toca IntelliJ ni otros procesos Java).
taskkill /T /F /FI "WINDOWTITLE eq Backend 8081*" >nul 2>&1
taskkill /T /F /FI "WINDOWTITLE eq Backend 8082*" >nul 2>&1
taskkill /T /F /FI "WINDOWTITLE eq Backend 8083*" >nul 2>&1
echo Backends detenidos.
pause
