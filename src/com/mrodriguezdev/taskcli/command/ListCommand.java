package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.exception.TaskNotFoundException;
import com.mrodriguezdev.taskcli.util.FileUtil;

public class ListCommand {
    public String list(String name) {
        String json = FileUtil.readJsonAsString(name);
        if (json.isEmpty()) {
            throw new TaskNotFoundException("No tasks found in the specified file.");
        }
        return json;
    }
}
