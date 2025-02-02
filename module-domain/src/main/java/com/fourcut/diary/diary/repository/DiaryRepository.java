package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

    Optional<Diary> findByUserAndDate(User user, LocalDate date);
    Integer countByUser(User user);
}
