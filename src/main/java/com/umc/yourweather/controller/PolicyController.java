package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.domain.Policies;
import com.umc.yourweather.response.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RestController
@RequestMapping(RequestURI.POLICY_URI)
public class PolicyController {

    @GetMapping("/privacy-policy")
    public ResponseDto<String> getPrivacyPolicy() {

        return ResponseDto.success("개인정보처리방침 전달 성공", Policies.PRIVACY_POLICY);
    }

    @GetMapping("/terms-of-use")
    public ResponseDto<String> getTermsOfUse() {

        return ResponseDto.success("서비스 이용약관 전달 성공", Policies.TERMS_OF_USE);
    }
}
