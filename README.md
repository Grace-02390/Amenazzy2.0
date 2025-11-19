Aplicación Spring Boot

## Despliegue en Render

### Pasos para desplegar en Render:

1. **Crear cuenta en Render**
   - Ve a [render.com](https://render.com)
   - Crea una cuenta gratuita

2. **Crear un nuevo Web Service**
   - En el dashboard de Render, haz clic en "New +"
   - Selecciona "Web Service"
   - Conecta tu repositorio de GitHub/GitLab

3. **Configurar el servicio**
   - **Name**: dime-gracias-app
   - **Environment**: Java
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/demo-0.0.1-SNAPSHOT.jar`

4. **Configurar variables de entorno**
   En la sección "Environment Variables" agrega:
   ```
   SPRING_PROFILES_ACTIVE=production
   DATABASE_URL=jdbc:mysql://sql10.freesqldatabase.com:3306/sql10789373
   DATABASE_USERNAME=sql10789373
   DATABASE_PASSWORD=BwFfZLar1e
   JWT_SECRET=tu-clave-secreta-muy-segura-aqui
   ADMIN_USERNAME=admin
   ADMIN_PASSWORD=tu-contraseña-admin
   ```

5. **Desplegar**
   - Haz clic en "Create Web Service"
   - Render comenzará a construir y desplegar tu aplicación

### Notas importantes:
- La aplicación se ejecutará en el puerto que Render asigne automáticamente
- Asegúrate de que tu base de datos MySQL esté accesible desde internet
- Considera usar una base de datos más robusta para producción

### Estructura del proyecto:
- `src/main/java/` - Código Java
- `src/main/resources/` - Archivos de configuración y templates
- `src/main/resources/static/` - Archivos estáticos (CSS, JS, imágenes)
- `src/main/resources/templates/` - Templates Thymeleaf 
- Todo correcto ;)
