# Instrucciones de Compilación y Ejecución

## Requisitos Previos

- **Java**: JDK 17 o superior
- **Maven**: 3.8 o superior
- **MySQL**: 8.0 o superior (opcional para demo)
- **IDE**: IntelliJ IDEA, Eclipse, NetBeans o VS Code

## Compilación

### Opción 1: Maven (Recomendado)

```bash
# Navegar al directorio del proyecto
cd /home/agus/Desktop/UDA/Programacion\ avanzada/tp3

# Compilar proyecto
mvn clean compile

# Generar JAR ejecutable
mvn clean package
```

### Opción 2: IntelliJ IDEA

1. Abrir IntelliJ IDEA
2. File → Open → Seleccionar el directorio del proyecto
3. Maven automáticamente reconocerá el pom.xml
4. Build → Build Project (Ctrl+F9)

### Opción 3: NetBeans

1. Abrir NetBeans
2. File → Open Project
3. Seleccionar el directorio
4. Click derecho en proyecto → Build

## Ejecución

### Opción 1: Maven directo

```bash
mvn exec:java -Dexec.mainClass="com.streaming.StreamingPlatformApp"
```

### Opción 2: JAR generado

```bash
# Generar JAR
mvn clean package

# Ejecutar
java -jar target/streaming-platform-1.0.0.jar
```

### Opción 3: IDE

**IntelliJ:**
1. Click derecho en StreamingPlatformApp.java
2. Run 'StreamingPlatformApp.main()'

**NetBeans:**
1. Click derecho en proyecto
2. Run

## Configuración de Base de Datos (Opcional)

### Crear base de datos

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script
source /home/agus/Desktop/UDA/Programacion\ avanzada/tp3/src/main/resources/db/schema.sql
```

O dentro de MySQL:
```sql
mysql> source /ruta/al/proyecto/src/main/resources/db/schema.sql;
```

## Estructura de Directorios Después de Compilación

```
tp3/
├── src/
├── target/
│   ├── classes/
│   │   └── com/streaming/
│   │       ├── StreamingPlatformApp.class
│   │       ├── controllers/
│   │       ├── interfaces/
│   │       ├── models/
│   │       ├── repositories/
│   │       └── utils/
│   └── streaming-platform-1.0.0.jar
├── pom.xml
└── README.md
```

## Salida Esperada

Al ejecutar la aplicación, verás:

```
╔════════════════════════════════════════════════════════════╗
║   Plataforma de Streaming Multimedia - Backend Demo         ║
║   (DIP - Dependency Inversion Principle aplicado)           ║
╚════════════════════════════════════════════════════════════╝

============================================================
1. REGISTRANDO USUARIOS
============================================================
=== [UserController] Registering user: juan_perez ===
[UserRepository] User created: User{id=1, username='juan_perez', email='juan@example.com', subscription='Free'}
[UserController] User successfully registered with ID: 1
...
```

## Troubleshooting

### Error: "Maven not found"
```bash
# Verificar instalación
mvn -v

# Si no está instalado
# Linux/Mac:
brew install maven

# O descargar desde: https://maven.apache.org/download.cgi
```

### Error: "Java version not compatible"
```bash
# Verificar versión de Java
java -version

# Debe ser Java 17 o superior
# Descargar desde: https://www.oracle.com/java/technologies/downloads/
```

### Error: "Cannot find class"
```bash
# Limpiar y recompilar
mvn clean compile

# Si persiste, verificar pom.xml
mvn validate
```

### Error en MySQL
```bash
# Verificar conexión a MySQL
mysql -u root -p -h localhost

# Si no funciona, iniciar servicio MySQL:
# Linux: sudo systemctl start mysql
# Mac: brew services start mysql-community-server
# Windows: net start MySQL80
```

## Optimización

### Aumentar memoria para Maven (si es necesario)
```bash
export MAVEN_OPTS=-Xmx1024m
mvn clean package
```

### Compilación paralela (más rápida)
```bash
mvn clean package -T 1C
```

### Saltar tests durante compilación
```bash
mvn clean package -DskipTests
```

## Integración con IDEs

### IntelliJ IDEA

1. Instalar plugin Maven (usualmente viene por defecto)
2. En Project Structure → Modules:
   - Source: `src/main/java`
   - Resources: `src/main/resources`
   - Tests: `src/test/java`

### NetBeans

- Automático al reconocer pom.xml
- Verificar en Tools → Options → Java → Maven

### VS Code

Instalar extensiones:
- Extension Pack for Java
- Maven for Java
- Project Manager for Java

Luego: Ctrl+Shift+P → "Run Maven Goal"

## Línea de Comandos Útiles

```bash
# Ver dependencias
mvn dependency:tree

# Validar sintaxis
mvn validate

# Compilar solo
mvn compile

# Ejecutar tests
mvn test

# Generar documentación
mvn javadoc:javadoc

# Limpiar directorio target
mvn clean

# Instalar en repositorio local
mvn install

# Publicar (si está configurado)
mvn deploy
```
