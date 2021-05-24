package com.javatechie.multiple.ds.api.user.repository;

import com.javatechie.multiple.ds.api.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
