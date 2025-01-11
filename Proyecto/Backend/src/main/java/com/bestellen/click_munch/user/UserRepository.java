package com.bestellen.click_munch.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    default void createAll(List<User> users){
        this.saveAll(users);
    };

}
