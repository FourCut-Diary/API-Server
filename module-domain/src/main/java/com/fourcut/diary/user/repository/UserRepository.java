package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    // CREATE

    // READ
    boolean existsBySocialId(String socialId);
    Optional<User> findBySocialId(String socialId);

    // UPDATE

    // DELETE
}
