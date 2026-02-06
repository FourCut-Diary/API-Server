package com.fourcut.diary.diary.repository;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

    Optional<Diary> findByUserAndDate(User user, LocalDate date);

    /**
     * 완료된 일기만 카운트 (제목과 최종 이미지가 모두 있는 경우)
     */
    Integer countByUserAndTitleIsNotNullAndImageUrlIsNotNull(User user);

    Boolean existsByUserAndDate(User user, LocalDate date);
}
