package com.meysam.common.service.impl;

import com.meysam.common.dao.GeneralPropertiesRepository;
import com.meysam.common.service.api.GeneralPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralPropertiesServiceImpl implements GeneralPropertiesService {

    private final GeneralPropertiesRepository generalPropertiesRepository;

    @Override
    public String findSettingByKey(String key) {
        return generalPropertiesRepository.findByKey(key);
    }
}
