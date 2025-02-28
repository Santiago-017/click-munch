package com.bestellen.click_munch.user;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);

    default void createAll(List<User> users){
        this.saveAll(users);
    }
    @Modifying
    @Query("UPDATE users SET orders = orders + 1 WHERE id = :userId")
    void addOrder(@Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE users SET name = :name, email = :email, password = :password, phone = :phone WHERE username = :username")
    void updateUser(@Param("name") String name,
                    @Param("email") String email,
                    @Param("password") String password,
                    @Param("phone") String phone,
                    @Param("username") String username);

}
