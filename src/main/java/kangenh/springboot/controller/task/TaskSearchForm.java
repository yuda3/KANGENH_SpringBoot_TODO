package kangenh.springboot.controller.task;

import kangenh.springboot.service.task.TaskSearchEntity;
import kangenh.springboot.service.task.TaskStatus;

import java.util.List;
import java.util.Optional;

public record TaskSearchForm(
        String summary,
        List<String> status
) {
    public TaskSearchEntity toEntity() {
        var statusEntityList = Optional.ofNullable(status())
                .map(statusList -> statusList.stream().map(TaskStatus::valueOf).toList())
                .orElse(List.of());

        return new TaskSearchEntity(summary(), statusEntityList);
    }

    public TaskSearchDTO toDTO() {
        return new TaskSearchDTO(summary(), status());
    }
}
