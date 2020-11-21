package com.mizuho.transformer;

import com.mizuho.model.Price;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PriceTransformer {


    public List<Price> extractPrices(String fileName, List<String> priceLines) {
        String vendorName = extractVendorName(fileName);

        List<Price> vendorPrices = getPrices(vendorName, priceLines);
        return vendorPrices;
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

}
