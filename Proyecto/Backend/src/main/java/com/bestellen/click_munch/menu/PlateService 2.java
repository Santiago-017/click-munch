package com.bestellen.click_munch.menu;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PlaceholderResolutionException;

@Service
public class PlateService {

    private final PlateRepository plateRepository;


    public PlateService(PlateRepository plateRepository) {
        this.plateRepository = plateRepository;
    }

    public Integer findStoreByPlate(Integer plateId){
        return plateRepository.findStoreByPlate(plateId);
    }

}
