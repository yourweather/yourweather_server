package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.request.AddAdvertisementRequestDto;
import com.umc.yourweather.response.AddAdvertisementResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestURI.ADVERTISEMENT_URI)
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("/add-advertisement")
    @Operation(summary = "광고 추가 api", description = "광고를 추가하는 api입니다. 어드민 권한이 있어야지만 실행 가능합니다.")
    public ResponseDto<AddAdvertisementResponseDto> addAdvertisement(
            @RequestBody AddAdvertisementRequestDto addAdvertisementRequestDto) {
        AddAdvertisementResponseDto responseDto = advertisementService.addAdvertisement(
                addAdvertisementRequestDto);

        return ResponseDto.success("광고 추가 성공", responseDto);
    }
}
