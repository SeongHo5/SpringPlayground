package com.seongho.spring.giftcard.repository;

import com.seongho.spring.giftcard.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

    Optional<GiftCard> findByCode(String code);

    boolean existsByCode(String code);
}
