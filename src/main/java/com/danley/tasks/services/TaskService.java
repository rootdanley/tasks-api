package com.danley.tasks.services;

import com.danley.tasks.models.Task;
import com.danley.tasks.models.User;
import com.danley.tasks.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;


  public Task findById(Long id) {
    Optional<Task> task = this.taskRepository.findById(id);

    return task.orElseThrow(() -> new RuntimeException(
       "Error: Tarefa não encontrada"
    ));
  }

  public List<Task> findAllByUserId(Long userId){
    List<Task> objs =
        this.taskRepository.findByUser_Id(userId);
    return objs;
  }


  @Transactional
  public Task create(Task obj) {
    User user = this.userService.findByid(obj.getUser().getId());
    obj.setId(null);

    obj.setUser(user);
    obj = this.taskRepository.save(obj);

    return obj;
  }

  @Transactional
  public Task update(Task obj) {
    Task newObj = findById(obj.getId());
    newObj.setDescription(obj.getDescription());

    return this.taskRepository.save(newObj);
  }

  public void delete(Long id) {
    findById(id);

    try {
      this.taskRepository.deleteById(id);
    }catch (Exception e){
      throw new RuntimeException("Não é possivel deletar");
    }
  }

}
