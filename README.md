# AppAutenticacionFirebase

Aplicación móvil que permite a los usuarios registrarse e iniciar sesión utilizando Firebase Authentication. Además, proporciona información sobre los perímetros en los que se puede conducir una moto en determinadas fechas y cuenta con integración de mapas mediante Google Maps y OpenStreetMaps.

## Características

- Registro e inicio de sesión con Firebase Authentication.
- Recuperación de contraseña.
- Soporte para internacionalización.
- Integración con Google Maps y OpenStreetMaps para visualizar perímetros de conducción.
- Interfaz amigable y fácil de usar.

## Tecnologías Utilizadas

- **Kotlin**
- **Firebase Authentication**
- **Google Maps API**
- **OpenStreetMaps**

## Estructura del Proyecto

El proyecto contiene los siguientes archivos principales:

- `LoginActivity.kt` - Maneja el inicio de sesión de los usuarios.
- `RegisterActivity.kt` - Permite el registro de nuevos usuarios.
- `forgetPassword.kt` - Funcionalidad para recuperar la contraseña.
- `MainActivity.kt` - Pantalla principal de la aplicación.
- `MapsActivity.kt` - Implementación de Google Maps.
- `OSMMapsActivity.kt` - Implementación de OpenStreetMaps.

## Instalación y Configuración

1. Clona este repositorio:
   ```sh
   git clone https://github.com/jhonomar26/AppAutenticacionFirebase.git
   ```
2. Abre el proyecto en Android Studio.
3. Configura Firebase en el proyecto:
   - Crea un proyecto en Firebase Console.
   - Descarga el archivo `google-services.json` y agrégalo a la carpeta `app/`.
4. Asegúrate de que tienes habilitadas las API de Google Maps y OpenStreetMaps.
5. Compila y ejecuta la aplicación en un dispositivo o emulador.

## Contribuciones

Si deseas contribuir a este proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama con tu mejora:
   ```sh
   git checkout -b feature-nueva-funcionalidad
   ```
3. Realiza tus cambios y súbelos al repositorio.
4. Envía un pull request para revisión.

## Licencia

Este proyecto está bajo la licencia MIT. Puedes ver más detalles en el archivo `LICENSE`.
