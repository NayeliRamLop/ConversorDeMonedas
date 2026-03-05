# Conversor de Monedas (Java)

Aplicacion de consola en Java que consulta la API de ExchangeRate-API para convertir montos entre monedas.

## Descripcion

Este proyecto implementa un conversor de monedas con:

- Consumo de API REST usando `HttpClient`, `HttpRequest` y `HttpResponse`.
- Parseo de JSON con `Gson` (`JsonParser`, `JsonObject` y mapeo a objetos Java).
- Menu interactivo en consola con opciones numericas.
- Validacion de entradas del usuario (opcion y monto).

## Monedas habilitadas en el desafio

- `ARS` - Peso argentino
- `BOB` - Boliviano boliviano
- `BRL` - Real brasileno
- `CLP` - Peso chileno
- `COP` - Peso colombiano
- `USD` - Dolar estadounidense

## Requisitos

- JDK 17 o superior
- IntelliJ IDEA (recomendado) o cualquier IDE Java
- Conexion a internet para consultar la API

## Dependencias

El proyecto usa Maven y tiene Gson en `pom.xml`:

- `com.google.code.gson:gson:2.13.2`

Adicionalmente, el modulo IntelliJ tiene configurado `lib/gson-2.13.2.jar`.

## Configuracion de API Key

Opcion recomendada (variable de entorno):

```powershell
$env:EXCHANGE_RATE_API_KEY="TU_API_KEY"
```

El proyecto tambien incluye una clave por defecto en codigo para facilitar pruebas locales.

## Como ejecutar

## IntelliJ IDEA

1. Abrir el proyecto.
2. Ejecutar la clase principal `com.conversordemonedas.Principal`.

## Terminal (PowerShell, ejemplo)

```powershell
"C:\Program Files\Java\jdk-17.0.18\bin\javac.exe" -cp "lib/gson-2.13.2.jar" -d out (Get-ChildItem -Recurse -File src/main/java/*.java | ForEach-Object { $_.FullName })
"C:\Program Files\Java\jdk-17.0.18\bin\java.exe" -cp "out;lib/gson-2.13.2.jar" com.conversordemonedas.Principal
```

## Menu de opciones

Al iniciar, se muestra un menu como este:

```text
***********************************************
Sea bienvenido/a al Conversor de Moneda =]

1) Dolar =>> Peso argentino
2) Peso argentino =>> Dolar
3) Dolar =>> Real brasileno
4) Real brasileno =>> Dolar
5) Dolar =>> Peso colombiano
6) Peso colombiano =>> Dolar
7) Salir
Elija una opcion valida:
***********************************************
```

## Estructura del proyecto

```text
src/main/java/com/conversordemonedas/
  Principal.java
  modelo/
    ExchangeRatePairResponse.java
    RespuestaCambio.java
  servicio/
    ConvertidorMoneda.java
    ExchangeRateApiCliente.java
```

