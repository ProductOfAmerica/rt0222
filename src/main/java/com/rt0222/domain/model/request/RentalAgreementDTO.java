package com.rt0222.domain.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RentalAgreementDTO {
    private String date;
    private Long customerId;
    private Set<ToolRentalDTO> rentals;
    private Set<DiscountDTO> discounts;
}
