package com.mizuho.service;

import com.mizuho.model.Price;
import com.mizuho.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Transactional
    public void savePrices(List<Price> vendorPrices) {
        Long deletedItems = priceRepository.deleteAllByVendor(vendorPrices.get(0).getVendor());
        log.info("deleted Prices="+deletedItems + " for Vendor=" + vendorPrices.get(0).getVendor());

        priceRepository.saveAll(vendorPrices);
    }

    public List<Price> getPrices(String vendor) {
        return priceRepository.findByVendor(vendor);
    }

    public List<Price> getPricesByIsin(String isin) {
        return priceRepository.findByIsin(isin);
    }

    @Transactional
    public long deletePricesOlderThan(LocalDateTime timestamp) {
        List<Price> deletedPrices = priceRepository.deleteAllByCreatedLessThanEqual(timestamp);
        log.info("Deleted {} prices {}", deletedPrices.size(), deletedPrices);
        return deletedPrices.size();
    }
}
