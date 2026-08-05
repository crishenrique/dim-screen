# DimScreen

Reduz o brilho da tela do Android com uma camada de escurecimento sobre todos os apps.

Foi feito para celulares com controle de brilho limitado (ex: bug de backlight em alguns aparelhos) e para quem quer uma redução de brilho mais forte do que a oferecida pelo sistema, principalmente à noite.

## Funcionalidades

* Camada de escurecimento sobre todos os apps (usa permissão de sobreposição do Android)
* Controle de intensidade de 0% a 100% com aplicação em tempo real
* Serviço em primeiro plano com notificação (evita que o sistema mate o processo)
* Reativação automática após reiniciar o celular
* Sem permissão de internet, nenhum dado é enviado

## Instalação

Baixe o APK (aba Releases) e instale no celular, permitindo "fontes desconhecidas" quando solicitado.

**Permissões necessárias (uma vez):**

1. **Exibir sobre outros apps** (o app pede ao ativar o escurecimento pela primeira vez)
2. **Notificações** (mantém a notificação do serviço ativa, Android 13+)

## Como funciona

O app desenha uma View preta semi-transparente sobre a tela inteira, com opacidade conforme o slider. A janela é mantida por um `ForegroundService` (`DimService`) com notificação persistente. Um `BootReceiver` reativa o escurecimento após o boot se o usuário tinha deixado ativado.

## Stack

* Kotlin 1.9.24
* Android Views + Material Design 3 (ViewBinding)
* MVVM: ViewModel + StateFlow + Repository (SharedPreferences)
* Gradle 8.7 / AGP 8.5.2
* minSdk 24, targetSdk 34

## Estrutura

```
app/src/main/java/com/dim/screen/dimmer/
|-- data/        DimRepository, DimState
|-- receiver/    BootReceiver
|-- service/     DimService, NotificationHelper
`-- ui/          MainActivity, DimViewModel, DimServiceController
```

## Desenvolvimento

```bash
# Build do APK de debug
gradle assembleDebug --no-daemon --console=plain

# APK gerado em
app/build/outputs/apk/debug/app-debug.apk
```

## Licença

MIT, veja o arquivo [LICENSE](LICENSE).
