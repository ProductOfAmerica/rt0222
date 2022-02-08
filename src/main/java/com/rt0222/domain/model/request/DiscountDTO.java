package com.rt0222.domain.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountDTO {
    private Long clerkId;
    private Long discountPercent;
}
