package io.github.mat3e.todoapp.logic;

import io.github.mat3e.todoapp.model.Project;
import io.github.mat3e.todoapp.model.TaskGroup;
import io.github.mat3e.todoapp.model.TaskGroupRepository;
import io.github.mat3e.todoapp.model.TaskRepository;
import io.github.mat3e.todoapp.model.projection.GroupReadModel;
import io.github.mat3e.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

public class TaskGroupService {

    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }

    public GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first.");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
