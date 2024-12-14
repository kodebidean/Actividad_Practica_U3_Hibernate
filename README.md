
# Hibernate CRUD Application
<hr>
<hr>
Este proyecto corresponde a la actividad práctica de la unidad 3 del módulo de Acceso a Datos de la formación profesional superior de DAM, que implementa operaciones CRUD (Crear, Leer, Actualizar y Eliminar) y consultas avanzadas utilizando **Hibernate** como framework ORM (Object-Relational Mapping). El objetivo principal es gestionar una base de datos relacional con entidades relacionadas de manera eficiente y profesional.

## Tecnologías Utilizadas
<hr>

- **Java** (JDK 17)
- **Hibernate ORM** (v6.2)
- **PostgreSQL** (Base de datos relacional)
- **Maven** (Gestión de dependencias)
- **IntelliJ IDEA** (IDE recomendado)

## Funcionalidades
<hr>

### Operaciones CRUD
La aplicación realiza las siguientes operaciones para las entidades `User` y `Ticket`:
- Crear usuarios y tickets.
- Leer usuarios y tickets, incluyendo relaciones entre entidades.
- Actualizar los datos de usuarios.
- Eliminar tickets de la base de datos.

### Consultas HQL
Se implementaron consultas avanzadas utilizando **Hibernate Query Language (HQL)**:
1. **Obtener todas las entradas de un usuario específico.**
2. **Obtener todas las entradas de una atracción en concreto.**
3. **Calcular el gasto medio de un usuario en las atracciones.**
4. Consultas adicionales para mejorar el funcionamiento general.

### Manejo de Excepciones
Se creó una clase personalizada `CustomException` para manejar excepciones relacionadas con Hibernate, lo que garantiza robustez y control sobre los errores en las operaciones.

## Requisitos Previos
<hr>

1. **Instalar PostgreSQL** y crear una base de datos.
2. Configurar las credenciales de conexión en el archivo `hibernate.cfg.xml`.
3. Tener configurado **Java JDK 17** y **Maven**.


## Estructura del Proyecto
<hr>

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.imanol/
│   │   │   ├── dao/         # Interfaces DAO
│   │   │   ├── dao.impl/    # Implementaciones de DAO
│   │   │   ├── models/      # Clases modelo (User, Ticket)
│   │   │   ├── util/        # Utilidades (HibernateUtil)
│   │   │   └── Main.java    # Punto de entrada principal
│   └── resources/
│       ├── hibernate.cfg.xml # Configuración de Hibernate
│       └── logback.xml       # Personalizar los logs
└── test/                     # Clases para pruebas (futuro)
```


## Ejemplo de Ejecución
<hr>

```plaintext
Usuarios creados:
1: Sergio
2: Imanol

Tickets creados para cada usuario:
Sergio: Montaña Rusa - 10.50
Sergio: Casa del Terror - 5.75
Imanol: Carrusel - 3.25

Entradas de Sergio:
Montaña Rusa - 10.50
Casa del Terror - 5.75

Entradas para 'Montaña Rusa':
Sergio - 10.50

Gasto medio de Sergio:
8.125

Usuario actualizado: Sergio Actualizado

Eliminando el ticket 'Casa del Terror'...
Tickets restantes:
Sergio: Montaña Rusa - 10.50
Imanol: Carrusel - 3.25
```
