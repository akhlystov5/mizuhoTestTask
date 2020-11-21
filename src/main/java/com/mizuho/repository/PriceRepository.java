package com.mizuho.repository;

import com.mizuho.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
    List<Price> findByVendor(String vendor);
    List<Price> findByIsin(String isin);

    Long deleteAllByVendor(String vendor);

    List<Price> deleteAllByCreatedLessThanEqual(LocalDateTime dateTime);
}
