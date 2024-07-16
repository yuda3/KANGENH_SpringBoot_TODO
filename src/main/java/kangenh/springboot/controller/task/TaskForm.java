package kangenh.springboot.controller.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kangenh.springboot.service.task.TaskEntity;
import kangenh.springboot.service.task.TaskStatus;

public record TaskForm(
        @NotBlank
        @Size(max = 256, message = "max is 256 length")
        String summary,

        @Size(max = 256)
        String description,

        @NotBlank
        @Pattern(regexp = "TODO|DOING|DONE",message="Choose TODO,DOING,DONE")
        String status
)
{
    public static TaskForm fromEntity(TaskEntity taskEntity) {

        return new TaskForm(
                taskEntity.summary(), taskEntity.description(), taskEntity.status().name()
        );
    }

    public TaskEntity toEntity() {
        return new TaskEntity(null, summary(), description(), TaskStatus.valueOf(status()));
    }

    public TaskEntity toEntity(long id) {
        return new TaskEntity(id, summary(), description(), TaskStatus.valueOf(status()));
    }

}
