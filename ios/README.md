# COMPANION iOS App

Aplicativo iOS nativo desenvolvido em Swift com SwiftUI.

## Requisitos

- macOS (para desenvolver iOS)
- Xcode 15.0 ou superior
- iOS 16.0+ (deployment target)
- CocoaPods (para gerenciar dependências)

## Estrutura do Projeto

```
ios/
├── Companion/
│   ├── CompanionApp.swift          # Entry point
│   ├── Models/                     # Modelos de dados
│   ├── Views/                      # Telas (SwiftUI)
│   ├── ViewModels/                 # ViewModels (MVVM)
│   ├── Services/                   # Serviços (API, Storage)
│   ├── Utils/                      # Utilitários
│   └── Resources/                  # Assets, cores, etc.
└── Podfile                         # Dependências
```

## Setup

1. Instalar CocoaPods:
```bash
sudo gem install cocoapods
```

2. Instalar dependências:
```bash
cd ios
pod install
```

3. Abrir o projeto:
```bash
open Companion.xcworkspace
```

4. Configurar a URL da API em `Services/APIService.swift`

5. Executar no simulador ou dispositivo físico

