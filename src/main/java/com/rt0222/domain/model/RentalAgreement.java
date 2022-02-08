package com.rt0222.domain.model;

import com.rt0222.service.util.CalendarUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "RENTAL_AGREEMENT")
public class RentalAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "checkout_date", nullable = false)
    private Timestamp checkoutDate;

    @Column(name = "final_charge")
    private Double finalCharge;

    @Column(name = "pre_discount_charge")
    private Double preDiscountCharge;

    @Column(name = "chargeable_days")
    private Long chargeableDays;

    @Column(name = "discount_percent")
    private Long discountPercent;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @OneToMany(mappedBy = "agreement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ToolRental> rentals;

    @OneToMany(mappedBy = "agreement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Discount> discounts;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Rental Agreement: ").append(id).append("\n");
        str.append("\tCheckout Date: ").append(CalendarUtil.DATE_FORMAT.format(checkoutDate)).append("\n");
        str.append("\tFinal Charge: ").append(String.format("$%,.2f", finalCharge)).append("\n");
        str.append("\tPre Discount Charge: ").append(String.format("$%,.2f", preDiscountCharge)).append("\n");
        str.append("\tChargeable Days: ").append(chargeableDays).append("\n");
        str.append("\tTotal Discount Percent: ").append(discountPercent).append("%\n");
        str.append("\tDiscount Amount: ").append(String.format("$%,.2f", discountAmount)).append("\n");

        if (rentals != null && !rentals.isEmpty()) {
            str.append("\tRentals:").append("\n");
            for (ToolRental rental : rentals) {
                str.append("\t\tTool Rental: ").append(rental.getId()).append("\n");
                Tool tool = rental.getTool();
                str.append("\t\t\tTool code: ").append(tool.getToolCode()).append("\n");
                str.append("\t\t\tTool type: ").append(tool.getToolType().getToolName()).append("\n");
                str.append("\t\t\tTool brand: ").append(tool.getToolBrand()).append("\n");
            }
        }
        if (discounts != null && !discounts.isEmpty()) {
            str.append("\tDiscounts:").append("\n");
            for (Discount discount : discounts) {
                str.append("\t\tDiscount: ").append(discount.getId()).append("\n");
                str.append("\t\t\tDiscount percent: ").append(discount.getDiscountPercent()).append("%\n");
                str.append("\t\t\tDiscount Clerk ID: ").append(discount.getClerk().getId()).append("\n");
            }
        }

        return str.toString();
    }
}
