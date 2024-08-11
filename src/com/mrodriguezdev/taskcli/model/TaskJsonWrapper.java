package com.mrodriguezdev.taskcli.model;

import java.util.ArrayList;
import java.util.List;

public class TaskJsonWrapper {
    private List<Task> tasks;

    public TaskJsonWrapper() {
        this.tasks = new ArrayList<>();
    }

    public TaskJsonWrapper(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
