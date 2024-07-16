package kangenh.springboot.controller.task;

import kangenh.springboot.service.task.TaskEntity;
import kangenh.springboot.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String list(TaskSearchForm searchForm, Model model){
        List<TaskDTO> taskList = taskService.find(searchForm.toEntity())
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
        model.addAttribute("taskList", taskList);
        model.addAttribute("searchDTO", searchForm.toDTO());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") Long taskId, Model model){
        TaskDTO taskDTO = taskService.findById(taskId)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute("taskForm") TaskForm taskForm, Model model){
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }


    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        TaskForm taskForm = taskService.findById(id)
                .map(TaskForm::fromEntity)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") long id,
                         @Validated @ModelAttribute TaskForm form, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "tasks/form";
        }
        TaskEntity entity = form.toEntity(id);
        taskService.update(entity);
        return "redirect:/tasks/" + id;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}
