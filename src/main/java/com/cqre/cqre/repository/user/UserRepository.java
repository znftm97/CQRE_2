package com.cqre.cqre.repository.user;

import com.cqre.cqre.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
