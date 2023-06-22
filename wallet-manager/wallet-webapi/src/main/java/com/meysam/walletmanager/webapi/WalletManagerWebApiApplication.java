package com.meysam.walletmanager.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={
        "com.meysam.common.utils.*"
        ,"com.meysam.common.security"
        ,"com.meysam.common.service.*"
        ,"com.meysam.walletmanager.*"
        ,"com.meysam.users.service.*"
})
@EnableJpaRepositories(basePackages={"com.meysam.walletmanager.dao","com.meysam.common.dao"})
@EntityScan(basePackages = "com.meysam.common.model.entity")
@EnableFeignClients(basePackages = {"com.meysam.users.service.*","com.meysam.common.service.*"})
public class WalletManagerWebApiApplication {

    public static void main(String []args){
        SpringApplication.run(WalletManagerWebApiApplication.class,args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource= new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    //for default local=US uncomment these
    /*@Bean
    public SessionLocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return  localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }*/

    /*@Bean
    public LocaleMessageSourceService getLocaleMessageSourceService(){
        return new LocaleMessageSourceService();
    }*/

}
