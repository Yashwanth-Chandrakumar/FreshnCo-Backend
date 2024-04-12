package com.freshnco.backend.controller;

import com.freshnco.backend.model.User;
import com.freshnco.backend.model.Details;
import com.freshnco.backend.repository.Userrepo;
import com.freshnco.backend.repository.DetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private DetailsRepo detailsRepo;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        User savedUser = userrepo.save(newUser);

        // Get details from the saved user
        Details details = newUser.getDetails();

        // Set the user for the details
        details.setUser(savedUser);

        // Save details
        detailsRepo.save(details);

        // Return the saved user
        return savedUser;

    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Sort sortObj = Sort.by("fname").ascending()
                .and(Sort.by("lname").descending());

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<User> users = userrepo.findAll(pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userrepo.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/userstate/{state}")
    public ResponseEntity<List<User>> getUsersByState(@PathVariable String state) {
        List<User> users = userrepo.findByState(state);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
    @GetMapping("/userdetails/{id}")
    public ResponseEntity<Details> getUserDetails(@PathVariable Long id) {
        Optional<User> userOptional = userrepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Details details = user.getDetails();
            if (details != null) {
                return ResponseEntity.ok(details);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{id}")
    ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        Optional<User> userOptional = userrepo.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFname(newUser.getFname());
            existingUser.setLname(newUser.getLname());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPassword(newUser.getPassword());

            // Update user details
            Details existingDetails = existingUser.getDetails();
            if (existingDetails == null) {
                // Create new details if it doesn't exist
                Details newDetails = newUser.getDetails();
                newDetails.setUser(existingUser);
                existingUser.setDetails(newDetails);
            } else {
                // Update existing details
                Details newDetails = newUser.getDetails();
                existingDetails.setPhone(newDetails.getPhone());
                existingDetails.setAddress(newDetails.getAddress());
                existingDetails.setCity(newDetails.getCity());
                existingDetails.setState(newDetails.getState());
                existingDetails.setPincode(newDetails.getPincode());
            }

            User updatedUser = userrepo.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/user/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userrepo.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        Details details = user.getDetails();
        if (details != null) {
            // Delete associated details
            detailsRepo.delete(details);
        }

        // Delete user
        userrepo.deleteById(id);

        return ResponseEntity.ok("User with id " + id + " has been deleted successfully.");
    }
    @PostMapping("/user/{id}/details")
public ResponseEntity<Details> addUserDetailsForUser(@PathVariable Long id, @RequestBody Details details) {
    Optional<User> userOptional = userrepo.findById(id);
    if (userOptional.isPresent()) {
        User user = userOptional.get();

        details.setUser(user);

        Details savedDetails = detailsRepo.save(details);

        return ResponseEntity.ok(savedDetails);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}
