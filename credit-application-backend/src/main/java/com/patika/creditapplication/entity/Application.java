package com.patika.creditapplication.entity;


import com.patika.creditapplication.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "credit_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credit_limit")
    private Float creditLimit;

    private Float collateral;

    @Column(name = "is_approved")
    @Enumerated
    private CreditStatus isApproved;
}
