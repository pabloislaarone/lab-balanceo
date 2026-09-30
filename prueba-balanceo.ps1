# Envia varias peticiones a Nginx (puerto 80) y muestra que backend respondio cada una.
# Uso:  powershell -ExecutionPolicy Bypass -File .\prueba-balanceo.ps1

param([int]$Peticiones = 9)

Write-Host "Enviando $Peticiones peticiones a http://localhost/api/info`n" -ForegroundColor Cyan

$conteo = @{}
for ($i = 1; $i -le $Peticiones; $i++) {
    try {
        $r = Invoke-RestMethod -Uri "http://localhost/api/info" -TimeoutSec 5
        $puerto = $r.puerto
        Write-Host ("Peticion {0,2} -> Backend puerto {1}  ({2})" -f $i, $puerto, $r.hora)
        $conteo[$puerto] = 1 + [int]$conteo[$puerto]
    } catch {
        Write-Host ("Peticion {0,2} -> ERROR: {1}" -f $i, $_.Exception.Message) -ForegroundColor Red
    }
}

Write-Host "`nResumen (peticiones por backend):" -ForegroundColor Cyan
$conteo.GetEnumerator() | Sort-Object Name | ForEach-Object {
    Write-Host ("  Puerto {0}: {1} peticiones" -f $_.Name, $_.Value)
}
