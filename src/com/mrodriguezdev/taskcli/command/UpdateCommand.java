package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.exception.TaskNotFoundException;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;
import com.mrodriguezdev.taskcli.util.FileUtil;
import com.mrodriguezdev.taskcli.util.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateCommand {
    public void update(String description, Long id, String name) {
        String json = FileUtil.readJsonAsString(name);
        if (json.isEmpty()) {
            throw new TaskNotFoundException("No tasks found in the specified file.");
        }

        TaskJsonWrapper taskJsonWrapper = JsonUtil.fromJson(json);
        List<Task> tasks = taskJsonWrapper.getTasks();

        Task updatedTask = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException(String.format("No task found with id '%d'", id)));

        updatedTask.setDescription(description);
        updatedTask.setUpdatedAt(LocalDateTime.now());

        String updatedJson = JsonUtil.toJson(new TaskJsonWrapper(tasks));
        FileUtil.create(name, updatedJson);
    }
}
