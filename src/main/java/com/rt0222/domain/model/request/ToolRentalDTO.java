package com.rt0222.domain.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ToolRentalDTO {
    private String toolCode;
    private Integer rentalDays;
}
