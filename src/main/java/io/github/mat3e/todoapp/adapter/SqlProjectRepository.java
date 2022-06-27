package io.github.mat3e.todoapp.adapter;

import io.github.mat3e.todoapp.model.Project;
import io.github.mat3e.todoapp.model.ProjectRepository;
import io.github.mat3e.todoapp.model.TaskGroup;
import io.github.mat3e.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();
}
