package com.meysam.auth.webapi;

import com.meysam.auth.service.api.GeneralPropertiesService;
import com.meysam.common.utils.constants.DefaultConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@ComponentScan({"com.meysam.common.*"})
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
