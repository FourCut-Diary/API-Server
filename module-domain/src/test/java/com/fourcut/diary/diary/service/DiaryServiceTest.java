package com.fourcut.diary.diary.service;

import com.fourcut.diary.diary.domain.Diary;
import com.fourcut.diary.user.domain.User;
import com.fourcut.diary.user.service.UserRetriever;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiaryServiceTest {

    @InjectMocks
    private DiaryService diaryService;

    @Mock
    private UserRetriever userRetriever;

    @Mock
    private DiaryRetriever diaryRetriever;

    @Test
    @DisplayName("오늘의 일기가 존재할 때 오늘의 일기 반환 테스트")
    void getTodayDiary_WhenDiaryExists_ReturnsExistingDiary() {
        // Given
        String socialId = "testSocialId";
        User mockUser = User.builder().socialId(socialId).build();
        Diary existingDiary = Diary.builder().id(1L).date(LocalDate.now()).user(mockUser).build();

        when(userRetriever.getUserBySocialId(socialId)).thenReturn(mockUser);
        when(diaryRetriever.getTodayDiary(mockUser)).thenReturn(existingDiary);

        // When
        Diary result = diaryService.getTodayDiary(socialId);

        // Then
        assertThat(result).isEqualTo(existingDiary);
        verify(userRetriever).getUserBySocialId(socialId);
        verify(diaryRetriever).getTodayDiary(mockUser);
    }

    @Test
    @DisplayName("오늘의 일기가 존재하지 않을 때 생성 후 반환 테스트")
    void getTodayDiary_WhenDiaryDoesNotExist_CreatesNewDiary() {
        // Given
        String socialId = "testSocialId";
        User mockUser = User.builder().socialId(socialId).build();
        Diary newDiary = Diary.builder().id(1L).date(LocalDate.now()).user(mockUser).build();

        when(userRetriever.getUserBySocialId(socialId)).thenReturn(mockUser);
        when(diaryRetriever.getTodayDiary(mockUser)).thenReturn(newDiary);

        // When
        Diary result = diaryService.getTodayDiary(socialId);

        // Then
        assertThat(result.getDate()).isEqualTo(LocalDate.now());
        assertThat(result.getUser()).isEqualTo(mockUser);
        verify(userRetriever).getUserBySocialId(socialId);
        verify(diaryRetriever).getTodayDiary(mockUser);
    }
}
