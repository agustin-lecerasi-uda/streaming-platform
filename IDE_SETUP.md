# Configuración IDE - IntelliJ IDEA / NetBeans

## IntelliJ IDEA

### Paso 1: Abrir Proyecto

1. **File → Open**
2. Navegar a: `/home/agus/Desktop/UDA/Programacion avanzada/tp3`
3. Click en **Open**
4. Esperar a que IntelliJ indexe los archivos

### Paso 2: Verificar Configuración de Maven

1. **File → Project Structure** (Ctrl+Alt+Shift+S)
2. **Project**:
   - Project SDK: `17` o superior
   - Language Level: `17`
3. **Modules**:
   - En la pestaña **Sources**:
     - Source Folders: `src/main/java`
     - Test Source Folders: `src/test/java` (si existe)
     - Resources: `src/main/resources`

### Paso 3: Sincronizar con Maven

1. **View → Tool Windows → Maven** (o Ctrl+Alt+M)
2. En el panel Maven que aparece a la derecha
3. Click en el icono de **Reload Projects** (esquina superior derecha)
4. Esperar a que descargue dependencias

### Paso 4: Compilar

**Opción A: Usando Maven Panel**
1. En el panel Maven a la derecha
2. Expandir: `streaming-platform → Lifecycle`
3. Double-click en `compile`

**Opción B: Línea de comandos integrada**
1. **View → Tool Windows → Terminal**
2. Ejecutar:
```bash
mvn clean compile
```

**Opción C: Atajo directo**
- Ctrl+F9 (Build Project)

### Paso 5: Ejecutar Aplicación

**Opción A: Click derecho en main**
1. Click derecho en `StreamingPlatformApp.java`
2. **Run 'StreamingPlatformApp.main()'**

**Opción B: Crear configuración de ejecución**
1. **Run → Edit Configurations**
2. Click en **+** (Add new configuration)
3. Seleccionar **Application**
4. Configurar:
   - Name: `Streaming Platform Demo`
   - Main class: `com.streaming.StreamingPlatformApp`
   - Working directory: Proyecto root
5. Click **OK**
6. **Run → Run 'Streaming Platform Demo'** (o Shift+F10)

### Paso 6: Generar JAR

1. **View → Tool Windows → Maven**
2. Expandir: `streaming-platform → Lifecycle`
3. Double-click en `package`
4. El JAR se genera en: `target/streaming-platform-1.0.0.jar`

### Troubleshooting IntelliJ

**Problema: "Maven projects need to be imported"**
- Solución: Click en "Import" que aparece en la notificación

**Problema: "Project SDK not specified"**
- Solución: File → Project Structure → Project SDK → Seleccionar JDK 17

**Problema: "Cannot find main class"**
- Solución: Verificar que `src/main/java/com/streaming/StreamingPlatformApp.java` existe
- Click derecho en proyecto → Reload from Disk

---

## NetBeans

### Paso 1: Abrir Proyecto

1. **File → Open Project** (Ctrl+O)
2. Navegar a: `/home/agus/Desktop/UDA/Programacion avanzada/tp3`
3. Click en **Open Project**
4. NetBeans reconocerá automáticamente que es un proyecto Maven

### Paso 2: Verificar Configuración de Maven

1. **Tools → Options** (NetBeans → Preferences en Mac)
2. Ir a **Java → Maven**
3. Verificar que Maven Home está correctamente configurado
4. Click **OK**

### Paso 3: Sincronizar Proyecto

1. **Tools → Maven → Update Project Configuration**
2. O click derecho en proyecto → **Update Project** (puede haber "x" roja)
3. Esperar a que descargue dependencias

### Paso 4: Compilar

**Opción A: Menú contextual**
1. Click derecho en proyecto
2. **Build** (o Build Project)

**Opción B: Atajo**
- F11 (Build Main Project)

**Opción C: Maven directo**
1. **View → Output → Output** (para ver los logs)
2. Click derecho en proyecto
3. **Run Maven → Clean and Build**

### Paso 5: Ejecutar Aplicación

**Opción A: Click derecho en Main**
1. Expandir: `Source Packages → com.streaming`
2. Click derecho en `StreamingPlatformApp.java`
3. **Run File** (Shift+F6)

**Opción B: Menú del Proyecto**
1. Click derecho en proyecto
2. **Run**

**Opción C: Atajo**
- F6 (Run Main Project)

### Paso 6: Generar JAR

1. Click derecho en proyecto
2. **Build** o **Clean and Build**
3. El JAR se genera en: `target/streaming-platform-1.0.0.jar`

### Troubleshooting NetBeans

**Problema: "No Main class configured"**
- Solución: Click derecho en proyecto → Properties → Run
- Establecer Main Class: `com.streaming.StreamingPlatformApp`

**Problema: "Dependencies not downloaded"**
- Solución: Click derecho en proyecto → Resolve Project Problems

**Problema: "Source not found after Maven build"**
- Solución: 
  1. Click derecho en proyecto → Properties
  2. Sources → Browse
  3. Asegurar que `src/main/java` está listado

---

## Comparación de Atajos

| Acción | IntelliJ | NetBeans |
|--------|----------|----------|
| Compilar | Ctrl+F9 | F11 |
| Ejecutar | Shift+F10 | F6 |
| Build Maven | Ctrl+Alt+M | Maven Tools |
| Terminal | Alt+F12 | Ctrl+Alt+T |
| Project Structure | Ctrl+Alt+Shift+S | Alt+7 |
| Búsqueda | Ctrl+Shift+F | Ctrl+H |

---

## Estructura de Carpetas Esperada

```
tp3/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/streaming/
│   │   │       ├── StreamingPlatformApp.java
│   │   │       ├── controllers/
│   │   │       ├── interfaces/
│   │   │       ├── models/
│   │   │       ├── repositories/
│   │   │       └── utils/
│   │   └── resources/
│   │       ├── db/
│   │       │   └── schema.sql
│   │       └── diagrams/
│   │           ├── erd.md
│   │           ├── playback_flow.md
│   │           └── architecture_dip.md
│   └── test/java/  (opcional)
├── target/         (se genera después de compilar)
│   ├── classes/
│   ├── streaming-platform-1.0.0.jar
│   └── ...
├── pom.xml
├── README.md
├── DIP_GUIDE.md
├── USAGE_EXAMPLES.md
├── COMPILATION_GUIDE.md
└── IDE_SETUP.md
```

---

## Verificación Final

### Checklist IntelliJ

- [ ] Proyecto abierto sin errores
- [ ] Maven panel visible (View → Tool Windows → Maven)
- [ ] JDK 17 configurado (File → Project Structure)
- [ ] Dependencias descargadas (sin ícono de x rojo)
- [ ] Click derecho en StreamingPlatformApp.java → Run disponible
- [ ] Compilar sin errores (Ctrl+F9)
- [ ] Ejecutar exitosamente (Shift+F10)

### Checklist NetBeans

- [ ] Proyecto abierto como Maven Project
- [ ] Source Packages mostrado en árbol
- [ ] JDK 17 configurado (Tools → Options → Java)
- [ ] Dependencias descargadas (sin ícono de x rojo)
- [ ] "Libraries" mostrado en lado izquierdo
- [ ] Compilar sin errores (F11)
- [ ] Ejecutar exitosamente (F6)

---

## Tips Productivos

### IntelliJ

```
Ctrl+Shift+O    → Optimizar imports
Ctrl+Alt+L      → Formatear código
Ctrl+/          → Comentar línea
Ctrl+D          → Duplicar línea
Alt+Enter       → Sugerencias rápidas
Ctrl+E          → Archivos recientes
Ctrl+Shift+P    → Estructura del proyecto
```

### NetBeans

```
Ctrl+Shift+I    → Organizar imports
Ctrl+Shift+F    → Formatear código
Ctrl+/          → Comentar línea
Ctrl+D          → Duplicar línea
Ctrl+Space      → Autocompletado
Ctrl+Shift+X    → Ir a línea
Alt+Shift+F     → Formatear código
```
