package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.exception.TaskNotFoundException;
import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;
import com.mrodriguezdev.taskcli.util.FileUtil;
import com.mrodriguezdev.taskcli.util.JsonUtil;

import java.util.List;

public class ListCommand {
    public String list(String name) {
        String json = FileUtil.readJsonAsString(name);
        if (json.isEmpty()) {
            throw new TaskNotFoundException("No tasks found in the specified file.");
        }
        TaskJsonWrapper taskJsonWrapper = JsonUtil.fromJson(json);
        return JsonUtil.toJson(taskJsonWrapper);
    }

    public String listBy(String name, Status status) {
        String json = FileUtil.readJsonAsString(name);
        if (json.isEmpty()) {
            throw new TaskNotFoundException("No tasks found in the specified file.");
        }

        TaskJsonWrapper taskJsonWrapper = JsonUtil.fromJson(json);
        List<Task> filteredTasks = taskJsonWrapper.getTasks().stream()
                .filter(task -> task.getStatus().equals(status.get()))
                .toList();

        if (filteredTasks.isEmpty()) {
            throw new TaskNotFoundException(String.format("No tasks found with status '%s'.", status.get()));
        }

        taskJsonWrapper.setTasks(filteredTasks);
        return JsonUtil.toJson(taskJsonWrapper);
    }

}
