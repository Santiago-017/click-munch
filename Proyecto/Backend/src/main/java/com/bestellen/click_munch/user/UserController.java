package com.bestellen.click_munch.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JdbcUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
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
    public void create(@RequestBody UserRequest user) {
        if (userDetailsManager.userExists(user.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        else if (userDetailsManager.userExists(user.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        else if (userDetailsManager.userExists(user.phone())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.password());

        assert userDetailsManager.getJdbcTemplate() != null;
        userDetailsManager.getJdbcTemplate().update(
                "insert into users (name, email, username, password, phone, orders) values (?,?,?,?,?,0)",
                user.name(), user.email(), user.username(), encodedPassword, user.phone()
        );
            userDetailsManager.getJdbcTemplate().update(
                    "insert into authorities (username, authority) values (?,?)",
                    user.username(), "USER"
            );
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
