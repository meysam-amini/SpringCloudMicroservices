package com.meysam.auth.webapi;

import com.meysam.common.configs.constants.DefaultConstants;
import com.meysam.common.service.api.GeneralPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEvent implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private GeneralPropertiesService generalPropertiesService;


    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        insertDefaultGeneralProperties();
    }

    private void insertDefaultGeneralProperties(){
        generalPropertiesService.addSettings(DefaultConstants.defaultConstants);
    }
}
