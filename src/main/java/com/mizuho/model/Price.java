package com.mizuho.model;

import lombok.*;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private String vendor;

    private String isin;

    private String instrument;

    private BigDecimal price;
}
