package com.rt0222.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TOOL_TYPE")
public class ToolType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tool_name", nullable = false)
    private String toolName;

    @Column(name = "daily_charge", nullable = false)
    private Double dailyCharge;

    @Column(name = "weekly_charge", nullable = false)
    private Boolean weeklyCharge;

    @Column(name = "weekend_charge", nullable = false)
    private Boolean weekendCharge;

    @Column(name = "holiday_charge", nullable = false)
    private Boolean holidayCharge;
}
