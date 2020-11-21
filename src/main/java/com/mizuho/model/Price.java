package com.mizuho.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String vendor;

    @NotNull
    private String isin;

    @NotNull
    private String instrument;

    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime created;

}
