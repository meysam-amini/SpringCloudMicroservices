package com.meysam.common.service.api;

import java.util.HashMap;

public interface GeneralPropertiesService {

    String findSettingByKey(String key);

    void addSettings(HashMap<String,String> settings);
}
