package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.exception.TaskNotFoundException;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.util.FileUtil;

import java.util.List;

public class ListCommand {
    public List<Task> list(String name) {
        List<Task> tasks = FileUtil.readJson(name);
        if (tasks.isEmpty()) throw new TaskNotFoundException("No se encontraron tareas");
        return tasks;
    }
}
