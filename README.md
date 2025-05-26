# Notifications Service - VitalConnect
Este microservicio forma parte del ecosistema de la plataforma VitalConnect, diseñada para la gestión descentralizada, segura y trazable de historiales médicos.

El notifications-service es responsable del envío de alertas y notificaciones en tiempo real a pacientes, médicos y administradores del sistema.

## Funcionalidad
- Gestión de datos de administrador.
- Generación de reportes.
- Definición de roles de administrador (super_admin, moderador, auditor)
- Validación de datos con Spring Validation.
- Exposición de endpoints RESTful (eventualmente para pruebas o reintentos).
- Base de datos relacional (MySQL).

## Tecnologías a utilizar
- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- Lombok
- Maven
- MySQL (como base de datos relacional)

## Requisitos para ejecutar localmente
- Java 17
- Maven (versión por confirmar)
- Base de datos MySQL en ejecución (con configuración en application.properties)
- IntelliJ IDEA (recomendado)

## Cómo ejecutar el microservicio
Desde el directorio raíz del proyecto VitalConnect:
```bash
cd adminpanel
mvn spring-boot:run
```

O desde IntelliJ IDEA:
- Click derecho sobre la clase principal → Run

## Estructura del proyecto
```
notifications-service/
├── src/
│   ├── main/java/com/vitalconnect/adminpanel/
│   └── test/java/com/vitalconnect/adminpanel/
├── resources/
│   └── application.properties
└── pom.xml
```

## Pruebas
Este microservicio incluye dependencias para pruebas unitarias y de integración:
- spring-boot-starter-test
- spring-kafka-test

---
## Autoría
Proyecto propuesto y desarrollado como parte de la asignatura DSY1103 – Desarrollo Fullstack I (DuocUC) por Milenka Guerra y Michelle Melo.

## Licencia
Uso educativo. Código desarrollado con fines académicos.
