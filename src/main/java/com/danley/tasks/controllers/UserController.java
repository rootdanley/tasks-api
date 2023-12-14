package com.danley.tasks.controllers;


import com.danley.tasks.models.User;
import com.danley.tasks.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

  @Autowired
  private UserService userService;

//  @GetMapping("/{id}")
//  public ResponseEntity<User> findById(@PathVariable Long id) {
//    User obj = this.userService.findByid(id);
//    return  ResponseEntity.ok().body(obj);
//  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    try {
      User obj = this.userService.findByid(id);

      if (obj != null) {
        return ResponseEntity.ok().body(obj);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      // Log the exception for debugging purposes
      e.printStackTrace(); // You might want to use a logger like SLF4J instead

      // Return a meaningful error response to the client
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }



  @PostMapping
  @Validated(User.CreateUser.class)
  public ResponseEntity<Void> create(@Valid @RequestBody User obj){
    this.userService.create(obj);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
    return  ResponseEntity.created(uri).build();
  }


  @PutMapping("/{id}")
  @Validated(User.UpdateUser.class)
  public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id){
    obj.setId(id);
    this.userService.update(obj);

    return ResponseEntity.noContent().build();
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    this.userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
