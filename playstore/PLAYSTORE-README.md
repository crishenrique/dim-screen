# DimScreen — Material pronto para a Play Store

Tudo pronto. Só falta a conta do Google Play ser aprovada.
Quando o e-mail de aprovação chegar, siga o passo a passo no final.

---

## 1. Ficha do app

| Campo | Valor |
|---|---|
| **Nome do app** | `DimScreen` |
| **Package / Application ID** | `com.dimscreen.app` |
| **Versão** | 1.0 (versionCode 1) |
| **Sdk mínimo** | Android 6.0 (API 23) |
| **Idioma da página** | Inglês (Estados Unidos) |

---

## 2. Título (30 caracteres max)

```
DimScreen - Screen Dimmer
```
(26 caracteres — OK)

## 3. Descrição curta (80 caracteres max)

```
Dim your screen beyond the minimum brightness
```
(50 caracteres — OK)

## 4. Descrição completa (inglês — até 4000 caracteres)

```
DimScreen lets you dim your Android screen much further than the system allows. Perfect for reading at night, protecting your eyes, or fixing devices that can't go dark enough.

WHY DIMMER?
Many phones still glow too bright at the lowest brightness setting. DimScreen adds a smooth, adjustable dark layer on top of everything so you can reach the exact comfort level you want.

FEATURES
• Dim from 0% to 100% with a simple slider
• Applies instantly, in real time
• Works over every app (launcher, games, videos, browsers)
• Stays active after you restart your phone
• Runs as a lightweight foreground service
• No ads, no account, no tracking

HOW IT WORKS
Toggle the switch and pick your intensity. DimScreen draws a semi-transparent dark layer over your whole screen. A persistent notification keeps it running in the background so it doesn't get closed.

PRIVACY
DimScreen does not collect, store, or transmit any data. It has no internet permission. All it does is dim your screen — nothing leaves your device.

Compatible with Android 6.0 and above.
```

## 5. Categoria e detalhes

| Campo | Valor |
|---|---|
| **Categoria** | Tools |
| **Tipo de app** | Apps e jogos |
| **Preço** | Gratuito |
| **Gratuito?** | **Sim** |
| **Data de disponibilidade** | Assim que a conta aprovar |

## 6. Classificação etária (questionário IARC)

Responda todas com o menor nível:
- **Conteúdo** → "Nenhum" / "Não se aplica"
- Resultado esperado: **Todos / Everyone (PEGI 3)**

## 7. Dados de privacidade (formulário obrigatório "Declaração de dados")

| Pergunta | Resposta |
|---|---|
| Coleta dados? | **Não** |
| Compartilha dados? | **Não** |
| Dados criptografados? | Não se aplica |
| Permite exclusão de dados? | Não se aplica |
| Requer política de privacidade? | **Sim** (já existe) |

Política de privacidade (URL pública — já está no ar):
- URL para colar na Play Console: **`https://crishenrique.github.io/dim-screen/privacy-policy.html`**
- Arquivo: `dim_app/privacy-policy.html`

## 8. Ícone e gráficos (arquivos em `dim_app/playstore/`)

| Recurso | Arquivo | Tamanho exigido |
|---|---|---|
| Ícone do app | `icon-512.png` | 512x512 |
| Feature graphic | `feature-graphic.png` | 1024x500 (1024x500 gerado) |
| Capturas de tela | `screenshot-1-main.png` (1080x2400) | mínimo 2, recomendo 4+ |
| Ícone TV | — | opcional, pode pular |

> **Nota capturas de tela:** a Play Store exige no mínimo 2 screenshots de telefone. A que temos (`screenshot-1-main.png`) mostra o app com o slider. Para completar, tire mais na Play Console ou reutilize com variações:
> 1. Tela principal (switch + slider) — já temos
> 2. Um slide explicativo com texto das funcionalidades
> 3. Slide mostrando o dimmer ativo sobre um app (ex: fundo escuro)
>
> Se preferir, eu gero as variações com texto usando as ferramentas que já tenho.

---

# PASSO A PASSO PARA PUBLICAR (quando a conta for aprovada)

### 1. Recebeu o e-mail de aprovação
- Entre em **play.google.com/console** → conta de desenvolvedor aprovada
- Confirme o **número de telefone** se ainda pedir (etapa pendente da conta)

### 2. Criar o app
- Botão **Criar app**
- Nome: `DimScreen`
- Idioma padrão: **Inglês (Estados Unidos)**
- Tipo: **App** → Categoria: **Tools**
- **Gratuito**: deixe marcado "Meu app é gratuito"
- **Criar app**

### 3. Preencher a página (copie o texto da seção 2-4 acima)
- **Principais informações** → título, descrição curta e completa
- **Gráficos** → enviar `icon-512.png`, `feature-graphic.png` e as screenshots
- **Classificação de conteúdo** → responder questionário (seção 6)

### 4. Declaração de dados e política de privacidade
- **Política de privacidade** → colar a URL hospedada (seção 7)
- **Declaração de dados** → marcar "Não coleta" conforme tabela

### 5. Enviar o AAB
- **Versões de produção → Criar nova versão**
- Arraste `dim_app\app\build\outputs\bundle\release\app-release.aab`
- Nota de versão: `First release.`
- Enviar para revisão

### 6. Revisar e publicar
- O Google analisa em horas~dias (primeiro app costuma demorar 1–3 dias)
- Quando aprovar → botão **Publicar**

---

## Arquivos já prontos (resumo)

| O quê | Onde |
|---|---|
| AAB assinado | `dim_app\app\build\outputs\bundle\release\app-release.aab` |
| Keystore (NUNCA perder) | `C:\Users\crish\DimScreen-Keystore\dimscreen-release.keystore` |
| Senha do keystore | `C:\Users\crish\DimScreen-Keystore\keystore-password.txt` |
| Ícone 512 | `dim_app\playstore\icon-512.png` |
| Feature graphic | `dim_app\playstore\feature-graphic.png` |
| Screenshot | `dim_app\playstore\screenshot-1-main.png` |
| Política de privacidade | `dim_app\privacy-policy.html` |
| Código-fonte | GitHub `crishenrique/dim-screen` (público) |
