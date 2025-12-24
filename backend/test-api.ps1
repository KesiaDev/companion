# Script de teste da API COMPANION

Write-Host "🧪 Testando API do COMPANION..." -ForegroundColor Cyan
Write-Host ""

# Teste 1: Registrar usuário
Write-Host "1️⃣ Testando registro de usuário..." -ForegroundColor Yellow
try {
    $body = @{
        email = "teste@teste.com"
        password = "senha123"
        name = "Usuário Teste"
    } | ConvertTo-Json

    $response = Invoke-RestMethod -Uri "http://localhost:3001/api/auth/register" `
        -Method POST `
        -Headers @{"Content-Type"="application/json"} `
        -Body $body

    Write-Host "✅ Registro bem-sucedido!" -ForegroundColor Green
    Write-Host "   Token: $($response.token.Substring(0, 20))..." -ForegroundColor Gray
    Write-Host ""
    
    # Teste 2: Login
    Write-Host "2️⃣ Testando login..." -ForegroundColor Yellow
    $loginBody = @{
        email = "teste@teste.com"
        password = "senha123"
    } | ConvertTo-Json

    $loginResponse = Invoke-RestMethod -Uri "http://localhost:3001/api/auth/login" `
        -Method POST `
        -Headers @{"Content-Type"="application/json"} `
        -Body $loginBody

    Write-Host "✅ Login bem-sucedido!" -ForegroundColor Green
    Write-Host "   Token: $($loginResponse.token.Substring(0, 20))..." -ForegroundColor Gray
    Write-Host ""
    
    Write-Host "🎉 API está funcionando perfeitamente!" -ForegroundColor Green
    
} catch {
    Write-Host "❌ Erro:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    
    if ($_.Exception.Response) {
        $stream = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($stream)
        $responseBody = $reader.ReadToEnd()
        $reader.Close()
        $stream.Close()
        Write-Host "Resposta do servidor:" -ForegroundColor Yellow
        Write-Host $responseBody -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "Pressione qualquer tecla para sair..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

