package kangenh.springboot.service.task;

import kangenh.springboot.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskEntity> find(TaskSearchEntity searchEntity){
        return taskRepository.select(searchEntity);
    }

    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.selectById(id);
    }

    @Transactional
    public void create(TaskEntity taskEntity) {
        taskRepository.insert(taskEntity);
    }

    @Transactional
    public void update(TaskEntity entity) {
        taskRepository.update(entity);

    }

    @Transactional
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
