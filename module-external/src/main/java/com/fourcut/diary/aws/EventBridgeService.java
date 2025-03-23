package com.fourcut.diary.aws;

import com.fourcut.diary.config.AwsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventBridgeService {

    private final AwsConfig awsConfig;

    public void enrollPushNotificationScheduler(Long userId, List<LocalDateTime> timeSlot) {
        try (SchedulerClient schedulerClient = createEventBridgeClient()) {
            for (int i = 0; i < timeSlot.size(); i++) {
                LocalDateTime slot = timeSlot.get(i);

                // 한국시간 → UTC 변환
                String isoTime = slot.atZone(ZoneId.of("Asia/Seoul"))
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

                String scheduleName = String.format("push-%d-%s-%d",
                        userId,
                        slot.toLocalDate().toString().replace("-", ""),
                        i + 1
                );

                // 기존 스케줄 삭제 (중복 방지)
                deleteScheduleIfExists(schedulerClient, scheduleName);

                int slotIndex = i + 1;
                String inputJson = String.format("{\"userId\": %d, \"slotIndex\": %d}", userId, slotIndex);
                // 새로운 스케줄 등록
                CreateScheduleRequest request = CreateScheduleRequest.builder()
                        .name(scheduleName)
                        .scheduleExpression("at(" + isoTime + ")")
                        .flexibleTimeWindow(FlexibleTimeWindow.builder().mode("OFF").build())
                        .target(Target.builder()
                                .arn(awsConfig.getPushLambdaArn())
                                .roleArn(awsConfig.getEventBridgeRoleArn())
                                .input(inputJson)
                                .build())
                        .build();

                schedulerClient.createSchedule(request);
            }

        } catch (SchedulerException e) {
            log.error("❌ EventBridge 스케줄 등록 실패: {}", e.getMessage(), e);
        }
    }

    public void deletePastSchedules() {
        try (SchedulerClient client = createEventBridgeClient()) {
            ListSchedulesResponse response = client.listSchedules(ListSchedulesRequest.builder().build());
            LocalDate today = LocalDate.now();

            for (ScheduleSummary summary : response.schedules()) {
                String name = summary.name();

                if (name.startsWith("push-") && name.split("-").length >= 4) {
                    try {
                        String dateStr = name.split("-")[2];
                        LocalDate scheduleDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));

                        if (scheduleDate.isBefore(today.minusDays(3))) {
                            client.deleteSchedule(DeleteScheduleRequest.builder()
                                    .name(name)
                                    .build());
                        }

                    } catch (Exception e) {
                        log.warn("❗ 예약 이름 파싱 실패 (건너뜀): {}", name);
                    }
                }
            }

        } catch (SchedulerException e) {
            log.error("❌ 스케줄 정리 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void deleteScheduleIfExists(SchedulerClient client, String scheduleName) {
        try {
            client.deleteSchedule(DeleteScheduleRequest.builder()
                    .name(scheduleName)
                    .build());
        } catch (ResourceNotFoundException e) {
            // 없으면 무시
        } catch (SchedulerException e) {
            log.warn("⚠️ 스케줄 삭제 중 오류 발생 - {}: {}", scheduleName, e.getMessage());
        }
    }

    private SchedulerClient createEventBridgeClient() {
        return SchedulerClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsConfig.credentialsProviderForEventBridge())
                .build();
    }
}
