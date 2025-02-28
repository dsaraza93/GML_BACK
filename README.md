# GML_BACK – Servicio REST para Administración de Clientes

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen)](https://spring.io/projects/spring-boot)
[![H2 Database](https://img.shields.io/badge/H2%20Database-InMemory-blue)](https://www.h2database.com)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203-blueviolet)](https://swagger.io/)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

## Descripción

GML_BACK es un servicio REST desarrollado con **Spring Boot** para la administración de clientes.  
El proyecto incluye operaciones CRUD (crear, leer, actualizar y eliminar), búsqueda simple y avanzada,  
y exportación a CSV. Se utiliza **H2** como base de datos en memoria para facilitar el desarrollo y la verificación,  
y **Swagger** (OpenAPI) para documentar y probar la API de forma interactiva.

## Características

- **CRUD completo:**  
  Permite la creación, consulta, actualización y eliminación de clientes.

- **Búsqueda:**  
  - **Búsqueda simple:** Filtra clientes por _shared key_.  
  - **Búsqueda avanzada:** Permite filtrar por _shared key, business ID, email, phone_ y un rango de fechas.

- **Exportación a CSV:**  
  Genera y descarga un archivo CSV con la lista de clientes.

- **Base de Datos H2:**  
  Configurada en modo in-memory. Accede a la consola H2 para inspeccionar los datos.

- **Documentación Swagger:**  
  Explora y prueba la API de forma interactiva en:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

- **Validación de entrada y Manejo Global de Excepciones:**  
  Se utilizan anotaciones de validación (`@Valid`, `@NotBlank`, `@Email`, etc.) y un controlador global de excepciones (`GlobalExceptionHandler`) para devolver respuestas estructuradas y consistentes ante errores.

- **Logging:**  
  Se ha implementado SLF4J (con Lombok) para registrar eventos críticos, facilitando la trazabilidad y el mantenimiento.

## Requisitos

- **Java 17** o superior.
- **Maven** o **Gradle** para gestionar dependencias.
- Conexión a Internet para descargar las dependencias desde Maven Central.

## Instalación y Ejecución

### Clonar el repositorio

```bash
git clone https://github.com/dsaraza93/GML_BACK.git
cd GML_BACK
