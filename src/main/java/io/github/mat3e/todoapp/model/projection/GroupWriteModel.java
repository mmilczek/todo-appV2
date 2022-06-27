package io.github.mat3e.todoapp.model.projection;

import io.github.mat3e.todoapp.model.Project;
import io.github.mat3e.todoapp.model.TaskGroup;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupWriteModel {

    @NotBlank(message = "Tasks group's description must not be empty")
    private String description;

    private List<GroupTaskWriteModel> tasks = new ArrayList<>();

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    public TaskGroup toGroup(Project project) {
        TaskGroup result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(tasks.stream()
                .map(source -> source.toTask(result))
                .collect(Collectors.toSet()));
        result.setProject(project);
        return result;
    }
}
