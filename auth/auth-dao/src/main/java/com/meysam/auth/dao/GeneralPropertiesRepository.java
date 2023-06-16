package com.meysam.auth.dao;

import com.meysam.common.model.entity.GeneralProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GeneralPropertiesRepository extends JpaRepository<GeneralProperties, Long> {

    GeneralProperties findByKey(String key);
}
