package com.seongho.spring.slack.repository;

import com.seongho.spring.slack.entity.SlackNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackNotificationHistoryRepository extends JpaRepository<SlackNotificationHistory, Long> {
}
