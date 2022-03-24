package com.testaf.demo1.repo;

import com.testaf.demo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findOneByUserName(String username);
}
