package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

    // CREATE

    // READ
    boolean existsBySocialId(String socialId);
    Optional<User> findBySocialId(String socialId);

    // UPDATE

    // DELETE
}
