package com.seongho.spring.giftcard.service;

import com.seongho.spring.common.exception.NotFoundException;
import com.seongho.spring.common.exception.ValidationException;
import com.seongho.spring.giftcard.repository.GiftCardRepository;
import com.seongho.spring.giftcard.entity.GiftCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.seongho.spring.common.exception.enums.ExceptionStatus.*;
import static com.seongho.spring.common.utils.CodeGenerator.generateRandomCode;
import static com.seongho.spring.giftcard.enums.GiftCardStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class GiftCardService {

    private final GiftCardRepository giftCardRepository;

    public static final int GIFT_CARD_CODE_LENGTH = 18;
    public static final int GIFT_CARD_MINIMUM_VALUE = 10000; // 10,000원
    public static final int GIFT_CARD_MAXIMUM_VALUE = 10000000; // 10,000,000원(천만원)

    public void issueGiftCard(int value) {
        validateGiftCardValue(value);

        GiftCard giftCard = GiftCard.builder()
                .code(createUniqueCode())
                .value(BigDecimal.valueOf(value))
                .status(UNSUED)
                .expiredAt(LocalDateTime.now().plusYears(5))
                .build();
        giftCardRepository.save(giftCard);
    }

    public void useGiftCard(String code) {
        GiftCard giftCard = giftCardRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_GIFT_CARD));

        checkIfGiftCardIsExpired(giftCard);
        checkIfGiftCardIsUsed(giftCard);

        giftCard.modifyStatus(USED);
    }

    public void disposeGiftCard(String code) {
        GiftCard giftCard = giftCardRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_GIFT_CARD));
        giftCardRepository.delete(giftCard);
    }

    // ========== PRIVATE METHODS ========== //


    private void validateGiftCardValue(int value) {
        if (value < GIFT_CARD_MINIMUM_VALUE || value > GIFT_CARD_MAXIMUM_VALUE) {
            throw new ValidationException(INVALID_INPUT_VALUE);
        }
    }

    private String createUniqueCode() {
        String code;
        do {
            code = generateRandomCode(GIFT_CARD_CODE_LENGTH);
        } while (giftCardRepository.existsByCode(code));

        return formatCodeWithHyphen(code);
    }

    private String formatCodeWithHyphen(String code) {
        StringBuilder sb = new StringBuilder(code);
        sb.insert(4, '-');
        sb.insert(9, '-');
        sb.insert(14, '-');
        return sb.toString();
    }

    private void checkIfGiftCardIsUsed(GiftCard giftCard) {
        if (giftCard.getStatus().equals(USED)) {
            throw new ValidationException(GIFT_CARD_ALREADY_USED);
        }
    }

    private void checkIfGiftCardIsExpired(GiftCard giftCard) {
        boolean isExpired = giftCard.getExpiredAt().isBefore(LocalDateTime.now())
                && giftCard.getStatus().equals(EXPIRED);
        if (isExpired) {
            throw new ValidationException(GIFT_CARD_EXPIRED);
        }
    }

}
