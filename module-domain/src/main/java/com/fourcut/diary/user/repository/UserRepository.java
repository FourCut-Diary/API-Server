package com.fourcut.diary.user.repository;

import com.fourcut.diary.user.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    // CREATE

    // READ
    boolean existsBySocialId(String socialId);
    Optional<User> findBySocialId(String socialId);

    @Query("""
                SELECT u\s
                FROM User u\s
                WHERE NOT EXISTS (
                    SELECT 1\s
                    FROM Diary d\s
                    WHERE d.user = u\s
                    AND d.date = :nextDay
                )
            \s""")
    List<User> findAllWithoutDiaryOn(@Param("nextDay") LocalDate nextDay);

    // UPDATE

    // DELETE
}
