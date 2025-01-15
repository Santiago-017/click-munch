package com.bestellen.click_munch.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void updateOrder(Integer userId) {
        userRepository.addOrder(userId);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.updateUser(user.name(), user.email(), user.password(), user.phone(), user.username());
    }
}
