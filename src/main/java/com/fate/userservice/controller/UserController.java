package com.fate.userservice.controller;

import com.fate.userservice.entity.User;
import com.fate.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/findById/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> findOne(@PathVariable Long userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    public ResponseEntity<User> ratingHotelFallback(Long userId, Exception ex) {
//        logger.info("Fallback is executed because service is down : ", ex.getMessage());
        ex.printStackTrace();
        User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created dummy because some service is down").id(1l).build();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }
}
