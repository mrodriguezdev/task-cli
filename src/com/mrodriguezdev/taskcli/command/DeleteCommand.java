package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.exception.TaskNotFoundException;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;
import com.mrodriguezdev.taskcli.util.FileUtil;
import com.mrodriguezdev.taskcli.util.JsonUtil;

import java.util.List;

public class DeleteCommand {
    public void delete(Long id, String name) {
        String json = FileUtil.readJsonAsString(name);
        if (json.isEmpty()) {
            throw new TaskNotFoundException("No se encontraron tareas en el archivo especificado.");
        }

        TaskJsonWrapper taskJsonWrapper = JsonUtil.fromJson(json);
        List<Task> tasks = taskJsonWrapper.getTasks();

        Task deletedTask = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(String.format("No se encontró ninguna tarea con el ID '%d'.", id)));

        tasks.remove(deletedTask);
        String updatedJson = JsonUtil.toJson(new TaskJsonWrapper(tasks));
        FileUtil.create(name, updatedJson);
    }
}
