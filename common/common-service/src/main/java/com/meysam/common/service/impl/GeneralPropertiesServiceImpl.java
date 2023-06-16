package com.meysam.common.service.impl;

import com.meysam.common.dao.GeneralPropertiesRepository;
import com.meysam.common.service.api.GeneralPropertiesService;
import com.meysam.common.model.entity.GeneralProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralPropertiesServiceImpl implements GeneralPropertiesService {


    private final GeneralPropertiesRepository generalPropertiesRepository;

    @Override
    public String findSettingByKey(String key) {
        return generalPropertiesRepository.findByKey(key).getValue();
    }

    @Override
    public void addSettings(HashMap<String, String> settingsKeyValue) {
        List<GeneralProperties> generalProperties = new ArrayList<>();

        settingsKeyValue.entrySet().forEach(property->{
            generalProperties.add(new GeneralProperties(null,property.getKey(),property.getValue()));
        });

        generalPropertiesRepository.saveAll(generalProperties);
    }
}
