//package com.meysam.common.service.impl;
//
//import cn.apiclub.captcha.Captcha;
//import com.meysam.common.configs.constants.Constants;
//import com.meysam.common.configs.exception.BusinessException;
//import com.meysam.common.configs.messages.LocaleMessageSourceService;
//import com.meysam.common.model.dto.CaptchaDto;
//import com.meysam.common.model.enums.CaptchaOperation;
//import com.meysam.common.service.api.CaptchaService;
//import com.meysam.common.utils.utils.CaptchaUtil;
//import jakarta.xml.bind.DatatypeConverter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//@Service
//public class CaptchaServiceImpl implements CaptchaService {
//
//    private final RedisTemplate<String,String> redisTemplate;
//    private final LocaleMessageSourceService messageSourceService;
//
//    @Value("${captcha.exiration.timeinminute:#{5}}")
//    private String CAPTCHA_EXPIRATION_TIME_MINUTE;
//
//    @Value("${captcha.enabled:#{true}}")
//    private String CAPTCHA_ENABLED;
//
//    public CaptchaServiceImpl(RedisTemplate<String, String> redisTemplate, LocaleMessageSourceService messageSourceService) {
//        this.redisTemplate = redisTemplate;
//        this.messageSourceService = messageSourceService;
//    }
//
//    @Override
//    public CaptchaDto generatePublicCaptcha(Integer width, Integer height, CaptchaOperation OP) {
//
//        Captcha captcha = generateCaptcha(width,height);
//        String captchaId = UUID.randomUUID().toString();
//        String key = Constants.CAPTCHA_PREFIX+OP+captchaId;
//        String value = captcha.getAnswer();
//        setDataOnRedis(key,value,OP);
//
//        return CaptchaDto.builder()
//                .captchaImage(imgToBase64String(captcha.getImage()))
//                .captchaId(captchaId)
//                .build();
//    }
//
//    @Override
//    public void validatePublicCaptchaByCaptchaId(String answer, String captchaId , CaptchaOperation OP){
//        if (Boolean.parseBoolean(CAPTCHA_ENABLED)) {
//            String key = Constants.CAPTCHA_PREFIX + OP + captchaId;
//            String dataOnRedis = getDataFromRedis(key, OP);
//            if (Objects.isNull(dataOnRedis)||!dataOnRedis.equals(answer)) {
//                throw new BusinessException(messageSourceService.getMessage("CAPTCHA_ANSWER_IS_WRONG"));
//            }
//        }
//    }
//
//    @Override
//    public void generateCaptchaByUsername(Integer width, Integer height, CaptchaOperation OP,String username) {
//
//        Captcha captcha = generateCaptcha(width,height);
//        String key = Constants.CAPTCHA_PREFIX+OP+username;
//        String value = captcha.getAnswer();
//        setDataOnRedis(key,value,OP);
//
//    }
//
//    @Override
//    public void validateCaptchaByUsername(String answer,String username,CaptchaOperation OP){
//        if (Boolean.parseBoolean(CAPTCHA_ENABLED)) {
//            String key = Constants.CAPTCHA_PREFIX + OP + username;
//            String dataOnRedis = getDataFromRedis(key, OP);
//            if (Objects.isNull(dataOnRedis)||!dataOnRedis.equals(answer)) {
//                throw new BusinessException(messageSourceService.getMessage("CAPTCHA_ANSWER_IS_WRONG"));
//            }
//        }
//    }
//
//    @Override
//    public void removePublicCaptcha(String captchaId, CaptchaOperation OP) {
//        if (Boolean.parseBoolean(CAPTCHA_ENABLED)){
//            String key = Constants.CAPTCHA_PREFIX+OP+captchaId;
//            removeDataFromRedis(key,OP);
//        }
//    }
//
//    @Override
//    public void removeCaptchaByUsername(String username, CaptchaOperation OP) {
//        if (Boolean.parseBoolean(CAPTCHA_ENABLED)){
//            String key = Constants.CAPTCHA_PREFIX+OP+username;
//            removeDataFromRedis(key,OP);
//        }
//    }
//
//
//    private String getDataFromRedis(String key,CaptchaOperation OP){
//        try {
//            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//            return valueOperations.get(key);
//        }catch (Exception e){
//            log.error("Exception on getting data from redis for operation: "+OP+" at time :{}, exception is:{} ",System.currentTimeMillis(),e);
//            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_CAPTCHA_SERVICE"));
//        }
//    }
//
//    private void removeDataFromRedis(String key,CaptchaOperation OP){
//        try {
//            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//            valueOperations.getOperations().delete(key);
//        }catch (Exception e){
//            log.error("Exception on REMOVING data from redis for operation: "+OP+" at time :{}, exception is:{} ",System.currentTimeMillis(),e);
//        }
//    }
//
//
//    private void setDataOnRedis(String key,String value,CaptchaOperation OP){
//        try {
//            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//            valueOperations.set(key, value, Long.valueOf(CAPTCHA_EXPIRATION_TIME_MINUTE), TimeUnit.MINUTES);
//        }catch (Exception e){
//            log.error("Exception on accessing redis on generate captcha process for operation: "+OP+" at time :{}, exception is:{} ",System.currentTimeMillis(),e);
//            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_CAPTCHA_SERVICE"));
//        }
//    }
//
//    private Captcha generateCaptcha(Integer width,Integer height){
//        if(Objects.isNull(width)){
//            width=170;
//        }
//        if (Objects.isNull(height)){
//            height=50;
//        }
//        return CaptchaUtil.createCaptcha(width,height);
//    }
//
//    private String imgToBase64String(BufferedImage image) {
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(image, "png", output);
//        } catch (IOException e) {
//            log.error("exception on creating captcha image at time:{}, exception is:{}",System.currentTimeMillis(),e);
//            throw new BusinessException(messageSourceService.getMessage("PROBLEM_ON_CAPTCHA_SERVICE"));
//        }
//        return DatatypeConverter.printBase64Binary(output.toByteArray());
//    }
//}
