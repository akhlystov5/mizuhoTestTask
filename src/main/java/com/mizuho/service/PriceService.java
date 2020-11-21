package com.mizuho.service;

import com.mizuho.model.Price;
import com.mizuho.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.file.Paths;
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
    public void savePrices(String fileName, List<String> priceLines) {
        String vendorName = extractVendorName(fileName);

        List<Price> vendorPrices = getPrices(vendorName, priceLines);

        priceRepository.deleteAllByVendor(vendorName);
        priceRepository.saveAll(vendorPrices);
    }

    private List<Price> getPrices(String vendorName, List<String> priceLines) {
        priceLines.remove(0);//remove header
        List<Price> prices = priceLines.stream().map(s -> {
            String[] values = s.split(",");
            log.info(Arrays.asList(values).toString());
            return Price.builder().vendor(vendorName).isin(values[1]).instrument(values[0]).price(new BigDecimal(values[2])).build();
        }).collect(Collectors.toList());
        log.info("prices=" + prices);
        return prices;
    }

    private String extractVendorName(String fileName) {

        return Paths.get(fileName).getFileName().toString().split(" ")[0];
    }

    public List<Price> getPrices(String vendor) {
        return priceRepository.findByVendor(vendor);
    }
}
