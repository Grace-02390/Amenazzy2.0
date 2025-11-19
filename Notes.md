Topitop App

Aplicación desarrollada por el equipo de ingeniería de Topitop, orientada a optimizar los procesos internos de la empresa y ofrecer una experiencia de compra moderna y eficiente a nuestros clientes.

Descripción

Topitop App es una plataforma integral que centraliza la visualización del catálogo de productos, la gestión de inventarios, el programa de fidelización y el proceso de compra. La solución está diseñada para ser escalable, modular y adaptable a nuevas necesidades comerciales.

Características principales
Para clientes

Catálogo actualizado con productos, tallas, colores y disponibilidad.

Carrito inteligente con recomendaciones personalizadas.

Proceso de compra con múltiples medios de pago.

Seguimiento de pedidos en tiempo real.

Integración con el programa de puntos Topitop.

Notificaciones personalizadas según preferencias del usuario.

Para uso interno (opcional según la versión del sistema)

Gestión de inventarios en tienda y almacén.

Consulta de stock en tiempo real entre sucursales.

Validación de precios y verificación de etiquetas.

Módulo de control de calidad y recepción de mercadería.

Arquitectura

La aplicación sigue una arquitectura basada en Clean Architecture y MVVM, garantizando la separación de responsabilidades y facilitando el mantenimiento a largo plazo.

Estructura general:

/app
 ├── data
 │   ├── datasources
 │   ├── repositories
 │   └── models
 ├── domain
 │   ├── entities
 │   ├── repositories
 │   └── usecases
 └── presentation
     ├── ui
     ├── viewmodels
     └── widgets

Tecnologías utilizadas
Frontend / Móvil

Flutter 3.x

Dart

Provider o Riverpod para gestión de estado

Firebase (Authentication, Firestore, Messaging, Crashlytics)

Backend

Node.js con Express

PostgreSQL

Redis para cache

Docker para contenedorización

Infraestructura

Google Cloud Platform

Cloud Run o Kubernetes

Pipeline CI/CD con GitHub Actions

Instalación y configuración

Clonar el repositorio

git clone https://github.com/topitop/app.git
cd app


Crear el archivo de variables de entorno .env con las credenciales internas:

API_URL=https://api.topitop.com
FIREBASE_API_KEY=XXXX
FIREBASE_PROJECT_ID=XXXX


Instalar dependencias

flutter pub get


Ejecutar en modo desarrollo

flutter run

Testing

Para ejecutar las pruebas unitarias:

flutter test

Contribuciones

El proyecto sigue un flujo de trabajo basado en GitFlow. Las ramas deben crearse bajo los siguientes esquemas:

feature/nombre-funcionalidad

bugfix/descripcion-arreglo

release/vX.X.X

Toda contribución debe incluir pruebas unitarias actualizadas, revisión por parte del equipo y documentación técnica pertinente.
