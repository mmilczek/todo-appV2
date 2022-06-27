package io.github.mat3e.todoapp.model.projection;

import io.github.mat3e.todoapp.model.Task;
import io.github.mat3e.todoapp.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupReadModelTest {

    @Test
    @DisplayName("should create null deadline for group when no task deadlines")
    void constructor_noDeadlines_createsNullDeadline() {
        //given
        TaskGroup source = new TaskGroup();
        source.setDescription("foo");
        Set tasks = new HashSet<>();
        tasks.add(new Task("foo", null));
        source.setTasks(tasks);

        //when
        GroupReadModel result = new GroupReadModel(source);

        //then
        assertThat(result).hasFieldOrPropertyWithValue("deadline", null);
    }
}
