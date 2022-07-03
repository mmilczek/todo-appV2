package io.github.mat3e.todoapp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    public static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    public Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        String description = "ApplicationContextEvent";
        if (!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Addind it");
            TaskGroup group = new TaskGroup();
            group.setDescription(description);
            Set<Task> tasks = new HashSet<>();
            tasks.add(new Task("ContextClosedEvent", null, group));
            tasks.add(new Task("ContextRefreshedEvent", null, group));
            tasks.add(new Task("ContextStoppedEvent", null, group));
            tasks.add(new Task("ContextStartedEvent", null, group));
            group.setTasks(tasks);
            groupRepository.save(group);
        }

    }
}
