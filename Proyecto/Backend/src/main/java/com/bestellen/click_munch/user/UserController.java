package com.bestellen.click_munch.user;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public List<User> findAll() {
        return (List<User>) userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        try {
            return userService.findById(id);
        } catch (Exception e) {
            System.out.println("User not found");
        }
        return null;
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) {
        try {
            return userService.findByUsername(username);
        }
        catch (Exception e) {
            System.out.println("User not found");
        }
        return null;
    }

    @PostMapping("add-user")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User user) {
        if (userService.findByUsername(user.username()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }else if (userService.findByEmail(user.email()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        } else if (userService.findByPhone(user.phone()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }
            userService.save(user);
    }

    @PutMapping("/{username}")
    public void update(@PathVariable String username, @RequestBody User user) {
        if (userService.findByUsername(username)!=null && Objects.equals(username, user.username())) {
            userService.updateUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username does not match");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

}
