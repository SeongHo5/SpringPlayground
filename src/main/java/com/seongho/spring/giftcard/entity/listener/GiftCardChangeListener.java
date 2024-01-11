package com.seongho.spring.giftcard.entity.listener;

import com.seongho.spring.giftcard.entity.GiftCard;
import com.seongho.spring.giftcard.entity.GiftCardHistory;
import com.seongho.spring.giftcard.repository.GiftCardHistoryRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.seongho.spring.common.utils.BeansUtil.getBean;

@Slf4j
@Component
@NoArgsConstructor
public class GiftCardChangeListener {

    @PostPersist
    public void onGiftCardCreated(GiftCard giftCard) {
        String description = "상품권 발행됨 : " + giftCard.getCode();
        saveHistory(giftCard, description);
        log.info("상품권 발행 이력 기록: {}", giftCard.getCode());
    }

    @PostUpdate
    public void onGiftCardUpdated(GiftCard giftCard) {
        String description = "상품권 사용 또는 수정됨 : " + giftCard.getCode();
        saveHistory(giftCard, description);
        log.info("상품권 사용 및 수정 이력 기록: {}", giftCard.getCode());
    }

    @PostRemove
    public void onGiftCardRemoved(GiftCard giftCard) {
        String description = "상품권 폐기됨 : " + giftCard.getCode();
        saveHistory(giftCard, description);
        log.info("상품권 폐기 이력 기록: {}", giftCard.getCode());
    }

    private void saveHistory(GiftCard giftCard, String description) {
        GiftCardHistoryRepository historyRepository = getBean(GiftCardHistoryRepository.class);
        GiftCardHistory history = GiftCardHistory.builder()
                .giftCard(giftCard)
                .description(description)
                .build();
        historyRepository.save(history);
    }
}
