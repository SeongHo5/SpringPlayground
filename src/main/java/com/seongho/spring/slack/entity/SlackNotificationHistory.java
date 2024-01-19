package com.seongho.spring.slack.entity;

import com.seongho.spring.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "slack_notification_history")
public class SlackNotificationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("알림을 발송한 채널 ID")
    private String channelId;

    @Comment("알림 발송 구분(알람, 시세 정보)")
    private String type;

    @Comment("발송한 메시지")
    private String message;

}
