package kangenh.springboot.controller.task;

import kangenh.springboot.service.task.TaskEntity;

public record TaskDTO (
    long id,
    String summary,
    String description,
    String status
) {

    public static TaskDTO toDTO(TaskEntity entity) {
        return new TaskDTO(
                entity.id(),
                entity.summary(),
                entity.description(),
                entity.status().name()
        );
    }
}