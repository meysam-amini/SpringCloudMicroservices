package com.meysam.common.dao;

import com.meysam.common.model.entity.GeneralProperties;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralPropertiesRepository extends BaseRepository<GeneralProperties> {

    GeneralProperties findBySettingKey(String key);
}
