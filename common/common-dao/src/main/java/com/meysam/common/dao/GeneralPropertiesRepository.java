package com.meysam.common.dao;

import com.meysam.common.model.entity.GeneralProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralPropertiesRepository extends JpaRepository<GeneralProperties, Long> {

    GeneralProperties findBySettingKey(String key);
}
