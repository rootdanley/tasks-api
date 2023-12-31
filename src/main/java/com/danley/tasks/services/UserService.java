package com.danley.tasks.services;

import com.danley.tasks.models.User;
import com.danley.tasks.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByid (Long id){
    Optional<User> user = this.userRepository.findById(id);
    return user.orElseThrow(() -> new RuntimeException(
       "Usuario não encontrado."
    ));
  }

  @Transactional
  public User create(User obj) {
    obj.setId(null);

    obj = this.userRepository.save(obj);
    return obj;
  }


  @Transactional
  public User update(User obj) {
    User newObj = findByid(obj.getId());
    newObj.setPassword(obj.getPassword());

    return this.userRepository.save(newObj);
  }


  public void delete(Long id){
    findByid(id);

    try{
      this.userRepository.deleteById(id);
    }catch (Exception e) {
      throw new RuntimeException("Error ao excluir: Usuario com tasks relacionadas");
    }
  }
}
