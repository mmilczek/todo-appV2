package io.github.mat3e.todoapp.logic;

import io.github.mat3e.todoapp.model.TaskGroup;
import io.github.mat3e.todoapp.model.TaskGroupRepository;
import io.github.mat3e.todoapp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {
    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwIllegalStateException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);

        //system inder test
        TaskGroupService toTest = new TaskGroupService(null, mockTaskRepository);

        //when
        Throwable exception = catchThrowable(() -> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        //and
        TaskGroupRepository mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        //system under test
        TaskGroupService toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        //when
        Throwable exception = catchThrowable(() -> toTest.toggleGroup(1));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");


    }

    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_worksAsExpected() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);

        //and
        TaskGroup group = new TaskGroup();
        boolean beforeToggle = group.isDone();

        //and
        TaskGroupRepository mockRepository = mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));

        //system under test
        TaskGroupService toTest = new TaskGroupService(mockRepository, mockTaskRepository);

        //when
        toTest.toggleGroup(0);

        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);


    }


    private TaskRepository taskRepositoryReturning(boolean result) {
        TaskRepository mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }



}
