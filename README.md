# task-cli

Este proyecto de rastreador de tareas está basado en una tarea propuesta en [Roadmap.sh](https://roadmap.sh/projects/task-tracker). El objetivo de este proyecto es crear una interfaz de línea de comandos (CLI) simple para rastrear y administrar las tareas que necesita hacer, lo que ha hecho, y en qué está trabajando actualmente.

## Requisitos
La aplicación debe ejecutarse desde la línea de comandos, aceptar las acciones y entradas del usuario como argumentos y almacenar las tareas en un archivo JSON. El usuario debe poder:

- Agregar, Actualizar y Eliminar tareas.
- Marcar una tarea como en progreso o realizada.
- Enumerar todas las tareas.
- Enumerar todas las tareas que se realizan.
- Enumerar todas las tareas que no se realizan.
- Enumerar todas las tareas que están en progreso.

Aquí hay algunas limitaciones para guiar la implementación:

- Puede utilizar cualquier lenguaje de programación para construir este proyecto.
- Use argumentos posicionales en la línea de comandos para aceptar entradas de usuario.
- Use un archivo JSON para almacenar las tareas en el directorio actual.
- El archivo JSON debe crearse si no existe.
- Utilice el módulo de sistema de archivos nativo de su lenguaje de programación para interactuar con el archivo JSON.
- No utilice bibliotecas o marcos externos para construir este proyecto.
- Asegúrese de manejar los errores y los casos de borde con gracia.

## Ejemplo
La lista de comandos y su uso se da a continuación:

### Agregar una nueva tarea
```bash
task-cli add "Buy groceries"
```
**Output: Tarea agregada exitosamente (ID: 1)**

### Actualizar y eliminar tareas
```bash
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1
```
**Output: Tarea actualizada exitosamente (ID: 1)**  
**Output: Tarea eliminada exitosamente (ID: 1)**

### Marcar una tarea como en progreso o realizada
```bash
task-cli mark-in-progress 1
task-cli mark-done 1
```
**Output: Tarea marcada como en progreso exitosamente (ID: 1)**  
**Output: Tarea marcada como realizada exitosamente (ID: 1)**

### Listar todas las tareas
```bash
task-cli list
```

### Listar tareas por estado
```bash
task-cli list done
task-cli list todo
task-cli list in-progress
```

# Propiedades de la Tarea
Cada tarea debe tener las siguientes propiedades:

- `id`: Un identificador único para la tarea.
- `description`: Una breve descripción de la tarea.
- `status`: El estado de la tarea (todo, in-progress, done).
- `createdAt`: La fecha y hora en que se creó la tarea.
- `updatedAt`: La fecha y hora en que se actualizó la tarea por última vez.

# Compilar y ejecutar el proyecto en formato `.jar`
Para utilizar el proyecto desde la línea de comandos, primero debemos compilar el código fuente y empaquetarlo en un archivo `.jar`. Sigue estos pasos:

1. **Compila el código fuente:**  
    Ejecuta el siguiente comando en la raíz del proyecto para compilar los archivos `.java` y crear el directorio bin con los archivos `.class` generados:
    ```bash
    javac -d bin $(find src -name "*.java")
    ```

2. **Crea el archivo `.jar`:**  
   Una vez compilado el código, genera el archivo `.jar` con el siguiente comando:
    ```bash
    jar cfe task-cli.jar com.mrodriguezdev.taskcli.TaskCli -C bin .
    ```

3. **Ejecutar el archivo `.jar`:**  
   Puedes ejecutar el archivo `.jar` con el siguiente comando. Los comandos específicos para la aplicación se añaden después del nombre del archivo `.jar`. Por ejemplo, para listar todas las tareas, usa:
    ```bash
    java -jar task-cli.jar list
    ```
   
