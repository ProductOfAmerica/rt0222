package com.rt0222.service.impl;

import com.rt0222.domain.model.*;
import com.rt0222.domain.model.request.DiscountDTO;
import com.rt0222.domain.model.request.RentalAgreementDTO;
import com.rt0222.domain.model.request.ToolRentalDTO;
import com.rt0222.domain.repository.*;
import com.rt0222.service.CheckoutService;
import com.rt0222.service.util.CalendarUtil;
import com.rt0222.service.util.MathUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final Logger logger = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    private final RentalAgreementRepository rentalAgreementRepository;
    private final ToolRentalRepository toolRentalRepository;
    private final ToolRepository toolRepository;
    private final ClerkRepository clerkRepository;
    private final DiscountRepository discountRepository;

    @Override
    public List<RentalAgreement> getAllRentalAgreements() {
        List<RentalAgreement> agreements = new ArrayList<>();
        rentalAgreementRepository.findAll().iterator().forEachRemaining(agreements::add);
        if (agreements.isEmpty()) {
            throw new EmptyResultDataAccessException("No Rental Agreements found.", 1);
        }
        return agreements;
    }

    @Override
    public RentalAgreement createRentalAgreement(RentalAgreementDTO rentalAgreement) {
        RentalAgreement agreement = new RentalAgreement();
        Date checkoutDate;
        try {
            checkoutDate = CalendarUtil.DATE_FORMAT.parse(rentalAgreement.getDate());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Must be yyyy-mm-dd.");
        }
        agreement.setCustomerId(rentalAgreement.getCustomerId());
        agreement.setCheckoutDate(new Timestamp(checkoutDate.getTime()));
        agreement = rentalAgreementRepository.save(agreement);

        Set<ToolRental> rentals = populateRentals(rentalAgreement, checkoutDate, agreement);
        Set<Discount> discounts = populateDiscount(rentalAgreement, agreement);

        Double preDiscountCharge = 0.0;
        Long chargeableDays = 0L;

        for (ToolRental rental : rentals) {
            for (int day = 0; day < rental.getRentalDays(); day++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(checkoutDate);
                cal.add(Calendar.DAY_OF_WEEK, day + 1);

//                logger.info("isHoliday: " + CalendarUtil.isHoliday(cal));
//                logger.info("isWeekend: " + CalendarUtil.isWeekend(cal));
//                logger.info("getWeekendCharge: " + rental.getTool().getToolType().getWeekendCharge());
//                logger.info("getHolidayCharge: " + rental.getTool().getToolType().getHolidayCharge());

                // Check if weekend or holiday
                if (rental.getTool().getToolType().getWeekendCharge() && CalendarUtil.isWeekend(cal) || rental.getTool().getToolType().getHolidayCharge() && CalendarUtil.isHoliday(cal)) {
                    logger.info("Charging " + cal.getTime() + " as weekend or holiday.");
                    preDiscountCharge += rental.getTool().getToolType().getDailyCharge();
                    chargeableDays++;
                } else if (!CalendarUtil.isHoliday(cal) && !CalendarUtil.isWeekend(cal)) {
                    logger.info("Charging " + cal.getTime() + " as weekday.");
                    preDiscountCharge += rental.getTool().getToolType().getDailyCharge();
                    chargeableDays++;
                } else {
                    logger.info("No charge!");
                }
            }
        }

        Long totalDiscountPercent = 0L;
        for (Discount discount : discounts) {
            totalDiscountPercent += discount.getDiscountPercent();
        }

        if (totalDiscountPercent < 0 || totalDiscountPercent > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid total discount amount. %s".formatted(totalDiscountPercent < 0 ? "Too low." : "Over 100%."));

        preDiscountCharge = MathUtil.roundHalfUp(preDiscountCharge);

        agreement.setRentals(rentals);
        agreement.setDiscounts(discounts);
        agreement.setChargeableDays(chargeableDays);
        agreement.setPreDiscountCharge(preDiscountCharge);
        agreement.setDiscountPercent(totalDiscountPercent);
        agreement.setDiscountAmount(MathUtil.roundHalfUp(preDiscountCharge * (totalDiscountPercent / 100.0)));
        agreement.setFinalCharge(MathUtil.roundHalfUp(preDiscountCharge - agreement.getDiscountAmount()));
        agreement = rentalAgreementRepository.save(agreement);

        logger.info("preDiscountCharge " + preDiscountCharge);
        logger.info("getDiscountAmount " + agreement.getDiscountAmount());
        logger.info("getFinalCharge " + agreement.getFinalCharge());

        return agreement;
    }

    private Set<Discount> populateDiscount(RentalAgreementDTO rentalAgreement, RentalAgreement agreement) {
        Set<Discount> discounts = new HashSet<>();
        for (DiscountDTO discountDTO : rentalAgreement.getDiscounts()) {
            clerkRepository.findAll().iterator().forEachRemaining(clerk -> logger.info("Clerk: " + clerk.getId()));
            Clerk clerk = clerkRepository.findClerkById(discountDTO.getClerkId());

            if (clerk == null) {
                throw new EmptyResultDataAccessException("Clerk \"%s\" not found.".formatted(discountDTO.getClerkId()), 1);
            }

            Discount discount = new Discount();
            discount.setAgreement(agreement);
            discount.setClerk(clerk);
            discount.setDiscountPercent(discountDTO.getDiscountPercent());
            discount = discountRepository.save(discount);
            discounts.add(discount);
        }
        return discounts;
    }

    private Set<ToolRental> populateRentals(RentalAgreementDTO rentalAgreement, Date checkoutDate, RentalAgreement agreement) {
        Set<ToolRental> rentals = new HashSet<>();
        for (ToolRentalDTO toolRentalDTO : rentalAgreement.getRentals()) {
            Tool tool = toolRepository.findToolByToolCode(toolRentalDTO.getToolCode());

            if (tool == null) {
                throw new EmptyResultDataAccessException("Tool rental \"%s\" not found.".formatted(toolRentalDTO.getToolCode()), 1);
            }

            ToolRental toolRental = new ToolRental();
            toolRental.setAgreement(agreement);
            toolRental.setRentalDays(toolRentalDTO.getRentalDays());

            if (toolRentalDTO.getRentalDays() < 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must have at least 1 or more rental days.");
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkoutDate);
            cal.add(Calendar.DAY_OF_WEEK, toolRentalDTO.getRentalDays());
            toolRental.setDueDate(new Timestamp(cal.getTime().getTime()));
            toolRental.setTool(tool);
            toolRental = toolRentalRepository.save(toolRental);
            rentals.add(toolRental);
        }
        return rentals;
    }
}
