package com.vin.poc.processor;

import com.vin.poc.model.VehicleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class VehicleItemProcessor implements ItemProcessor<VehicleDTO, VehicleDTO> {
    @Override
    public VehicleDTO process(VehicleDTO item) {
        log.info("Item Processor will return same object as it is ");
        return item;
    }
}
