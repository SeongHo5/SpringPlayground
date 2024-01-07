package com.seongho.spring.giftcard.controller;

import com.seongho.spring.giftcard.service.GiftCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift-card")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GiftCardController {

    private final GiftCardService giftCardService;

    @PostMapping("/issue")
    public void issueGiftCard(final @RequestParam("value") String value) {
        giftCardService.issueGiftCard(Integer.parseInt(value));
    }

    @PostMapping("/use")
    public void useGiftCard(final @RequestParam("code") String code) {
        giftCardService.useGiftCard(code);
    }

    @DeleteMapping("/dispose")
    public void disposeGiftCard(final @RequestParam("code") String code) {
        giftCardService.disposeGiftCard(code);
    }

}
