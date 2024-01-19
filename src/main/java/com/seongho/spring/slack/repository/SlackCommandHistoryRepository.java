package com.seongho.spring.slack.repository;

import com.seongho.spring.slack.entity.SlackCommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlackCommandHistoryRepository extends JpaRepository<SlackCommandHistory, Long> {
}
