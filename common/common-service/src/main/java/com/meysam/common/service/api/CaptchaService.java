//package com.meysam.common.service.api;
//
//
//import com.meysam.common.model.dto.CaptchaDto;
//import com.meysam.common.model.enums.CaptchaOperation;
//
//public interface CaptchaService {
//
//    CaptchaDto generatePublicCaptcha(Integer width, Integer height, CaptchaOperation op);
//    void validatePublicCaptchaByCaptchaId(String answer, String captchaId , CaptchaOperation OP);
//
//    void generateCaptchaByUsername(Integer width, Integer height, CaptchaOperation OP,String username);
//    void validateCaptchaByUsername(String answer,String username,CaptchaOperation OP);
//
//    void removePublicCaptcha(String captchaId, CaptchaOperation OP);
//    void removeCaptchaByUsername(String username, CaptchaOperation OP);
//}
