package com.seongho.spring.giftcard.repository;

import com.seongho.spring.giftcard.entity.GiftCardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftCardHistoryRepository extends JpaRepository<GiftCardHistory, Long> {
}
