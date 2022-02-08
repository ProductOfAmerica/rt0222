package com.rt0222.service;

import com.rt0222.domain.model.RentalAgreement;
import com.rt0222.domain.model.request.DiscountDTO;
import com.rt0222.domain.model.request.RentalAgreementDTO;
import com.rt0222.domain.model.request.ToolRentalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CheckoutServiceTests {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutServiceTests(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Test()
    void test1() {
        // Throw error, discount is above 100
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("JAKR", "9/3/15", 5, 101L);

        ResponseStatusException responseStatusException = Assertions.assertThrows(ResponseStatusException.class, () -> checkoutService.createRentalAgreement(rentalAgreementDTO));

        Assertions.assertTrue(Objects.requireNonNull(responseStatusException.getMessage()).contains("Invalid total discount amount."));
    }

    @Test
    void test2() {
        // LADW is $1.99
        // Renting 3 days, not including 7/2/20
        //  7/3/20 friday - $1.99
        //  7/4/20 saturday - free
        //  7/5/20 sunday - $1.99
        // Ladders not charable on holiday. Means total is $3.98.
        // Discount is 10%, so final should be $3.58
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("LADW", "7/2/20", 3, 10L);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(rentalAgreementDTO);
        System.out.println(rentalAgreement.toString());
        Assertions.assertEquals(3.58, rentalAgreement.getFinalCharge());
    }

    @Test
    void test3() {
        // CHNS is $1.49
        // Renting 5 days, not including 7/2/15
        //  7/3/15 friday - $1.49
        //  7/4/15 saturday - free (weekend, but is holiday?? but the observed is on Friday, so this is free? I didn't get a reply to my question email)
        //  7/5/15 sunday - free
        //  7/6/15 monday - $1.49
        //  7/7/15 tuesday - $1.49
        // Ladders not charable on holiday. Means total is $4.47.
        // Discount is 25%, so final should be $3.35
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("CHNS", "7/2/15", 5, 25L);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(rentalAgreementDTO);
        Assertions.assertEquals(3.35, rentalAgreement.getFinalCharge());
    }

    @Test
    void test4() {
        // JAKD is $2.99
        // Renting 6 days, not including 9/3/15
        // 9/4/15 friday - $2.99
        // 9/5/15 saturday - free
        // 9/6/15 sunday - free
        // 9/7/15 monday - free (labor day)
        // 9/8/15 tuesday - $2.99
        // 9/9/15 wednesday - $2.99
        // Ladders not charable on holiday. Means total is $8.97.
        // Discount is 0%, so final should be $8.97
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("JAKD", "9/3/15", 6, 0L);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(rentalAgreementDTO);
        Assertions.assertEquals(8.97, rentalAgreement.getFinalCharge());
    }

    @Test
    void test5() {
        // JAKR is $2.99
        // Renting 9 days, not including 7/2/15
        // 7/3/15 friday - $2.99
        // 7/4/15 saturday - free
        // 7/5/15 sunday - free
        // 7/6/15 monday - free (labor day)
        // 7/7/15 tuesday - $2.99
        // 7/8/15 wednesday - $2.99
        // 7/9/15 thursday - $2.99
        // 7/10/15 friday - $2.99
        // 7/11/15 saturday - free
        // Ladders not charable on holiday. Means total is $14.95.
        // Discount is 0%, so final should be $14.95
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("JAKR", "7/2/15", 9, 0L);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(rentalAgreementDTO);
        Assertions.assertEquals(14.95, rentalAgreement.getFinalCharge());
    }

    @Test
    void test6() {
        // JAKR is $2.99
        // Renting 9 days, not including 7/2/20
        // 7/3/20 friday - free (observed holiday, so free)
        // 7/4/20 saturday - free (holiday, but not observed, but weekend, so free)
        // 7/5/20 sunday - free
        // 7/6/20 monday - $2.99
        // Ladders not charable on holiday. Means total is $2.99.
        // Discount is 50%, so final should be $1.49, because $2.99 - $1.5 (because discount amount rounded from 1.495 to 1.5)
        RentalAgreementDTO rentalAgreementDTO = createRentalAgreementDTO("JAKR", "7/2/20", 4, 50L);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(rentalAgreementDTO);
        Assertions.assertEquals(1.49, rentalAgreement.getFinalCharge());
    }

    private RentalAgreementDTO createRentalAgreementDTO(String code, String date, int rentalDays, long discount) {
        ToolRentalDTO toolRentalDTO = new ToolRentalDTO();
        toolRentalDTO.setToolCode(code);
        toolRentalDTO.setRentalDays(rentalDays);

        Set<ToolRentalDTO> toolRentalDTOSet = new HashSet<>();
        toolRentalDTOSet.add(toolRentalDTO);

        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setClerkId(1L);
        discountDTO.setDiscountPercent(discount);

        Set<DiscountDTO> discountDTOS = new HashSet<>();
        discountDTOS.add(discountDTO);

        RentalAgreementDTO rentalAgreementDTO = new RentalAgreementDTO();
        rentalAgreementDTO.setRentals(toolRentalDTOSet);
        rentalAgreementDTO.setDiscounts(discountDTOS);
        rentalAgreementDTO.setDate(date);
        rentalAgreementDTO.setCustomerId(1L);
        return rentalAgreementDTO;
    }
}
