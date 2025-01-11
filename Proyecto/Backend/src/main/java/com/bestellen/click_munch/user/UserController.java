package com.bestellen.click_munch.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> user)
                .orElseThrow();
    }

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping("")
    public void create(@RequestBody User user) {
        userRepository.save(user);
    }

    @PutMapping("/{email}")
    public void update(@PathVariable String email, @RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }


}
