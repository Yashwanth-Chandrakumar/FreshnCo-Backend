package com.yashwanth.backend.controller;

import com.yashwanth.backend.model.User;
import com.yashwanth.backend.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yashwanth.backend.exception.UserNotFoundException;

import java.util.List;
@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {
    @Autowired
    private Userrepo userrepo;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userrepo.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userrepo.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userrepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userrepo.findById(id)
                .map(user -> {
                    user.setFname(newUser.getFname());
                    user.setLname(newUser.getLname());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    user.setPhone(newUser.getPhone());
                    user.setCity(newUser.getCity());
                    user.setAddress(newUser.getAddress());
                    user.setState(newUser.getState()); // Fix: Set the state instead of city
                    user.setPincode(newUser.getPincode()); // Fix: Corrected method name
                    return userrepo.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }
    @PostMapping("/")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        User user = userrepo.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful"+"["+user.getFname()+","+user.getId()+"]");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userrepo.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userrepo.deleteById(id);
        return "User with id " + id + " has been deleted successfully."; // Fix: Corrected the success message
    }
}
