# PokeQuiz

API REST para generación, administración y evaluación de quizzes de Pokémon.

El sistema cubre el ciclo completo de vida de un cuestionario, desde su creación hasta la calificación final, incluyendo generación de preguntas, persistencia, control de estados y seguridad basada en tokens.

Pensado como proyecto de práctica de arquitectura backend moderna y estándares de calidad profesional.

---

## Características principales

- Creación de quizzes
- Generación automática de contenido
- Registro de respuestas del usuario
- Evaluación y cálculo de puntuación
- Máquina de estados del quiz
- Seguridad con JWT (access / refresh)
- Migraciones versionadas con Liquibase
- Documentación OpenAPI
- Entorno reproducible con Docker

---

## Ciclo de vida del Quiz

```
CREATED
   ↓
GENERATING
   ↓
READY / GENERATING_ERROR
   ↓
IN_PROGRESS
   ↓
COMPLETED
```

Las transiciones están controladas por reglas de dominio.

---

## Stack Tecnológico

- Java 21
- Spring Boot 3.x
- Spring Security
- JWT
- JPA / Hibernate
- PostgreSQL
- Liquibase
- Docker / Docker Compose
- Maven
- Swagger / OpenAPI

---

## Arquitectura

Separación clara de responsabilidades.

```
Controller → Service / Use Case → Repository
                  ↓
                Domain
                  ↓
              DTO / Mapper
```

### Decisiones importantes

- Controllers sin lógica de negocio
- DTOs independientes de entidades
- Validaciones en aplicación y dominio
- Estados gestionados explícitamente
- Migraciones idempotentes
- Manejo global de errores

---

## Requisitos

- JDK 21
- Docker
- Docker Compose

No necesitas PostgreSQL instalado localmente.

---

## Configuración

Revisar:

```
application.yml
application-*.yml
```

Ejemplos de variables relevantes:

```
DB_HOST
DB_PORT
DB_NAME
DB_USER
DB_PASS

JWT_SECRET
JWT_ACCESS_EXPIRATION
JWT_REFRESH_EXPIRATION
```

---

## Levantar el proyecto en local

### Base de datos

```bash
docker compose up -d
```

### Aplicación

```bash
./mvnw spring-boot:run
```

o

```bash
./mvnw clean package
java -jar target/pokequiz.jar
```

Liquibase ejecutará las migraciones automáticamente al iniciar.

---

## Documentación API

```
http://localhost:8080/swagger-ui.html
```

---

## Autenticación

Enviar en cada request:

```
Authorization: Bearer <access_token>
```

Cuando expire, usar refresh.

---

## Endpoints relevantes

### Auth
```
POST /auth/register
POST /auth/login
POST /auth/refresh
POST /auth/logout
```

### Quiz
```
POST /quiz/create
POST /quiz/generateAndSaveContent
GET  /quiz/getQuizById
POST /quiz/completeQuizAnswers
```

---

## Tests

```bash
./mvnw test
```

---

## Build

```bash
./mvnw clean package
```

Resultado:

```
target/pokequiz.jar
```

---

## Docker

Incluye:

- Dockerfile para la aplicación
- docker-compose para dependencias
- configuración separada para QA

---

## Objetivo del proyecto

Practicar:

- Diseño orientado a dominio
- Arquitectura mantenible
- Seguridad
- Separación de capas
- Contratos API
- Buenas prácticas enterprise