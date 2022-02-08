package com.rt0222.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TOOL_RENTAL")
public class ToolRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_agreement_id")
    private RentalAgreement agreement;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tool_id")
    private Tool tool;

    @Column(name = "rental_days", nullable = false)
    private Integer rentalDays;

    @Column(name = "due_date", nullable = false)
    private Timestamp dueDate;
}
