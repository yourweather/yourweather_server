package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.request.AddAdvertisementRequestDto;
import com.umc.yourweather.response.AddAdvertisementResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(RequestURI.ADVERTISEMENT_URI)
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping("/add-advertisement")
    @ResponseBody
    @Operation(summary = "광고 추가 api", description = "광고를 추가하는 api입니다. 어드민 권한이 있어야지만 실행 가능합니다.")
    public ResponseDto<AddAdvertisementResponseDto> addAdvertisement(
            @RequestBody AddAdvertisementRequestDto addAdvertisementRequestDto) {
        AddAdvertisementResponseDto responseDto = advertisementService.addAdvertisement(
                addAdvertisementRequestDto);

        return ResponseDto.success("광고 추가 성공", responseDto);
    }

    @PutMapping("/edit-advertisement")
    @ResponseBody
    @Operation(summary = "광고 수정 api", description = "광고를 수정하는 api입니다. 어드민 권한이 있어야지만 실행 가능합니다.")
    public ResponseDto<AddAdvertisementResponseDto> editAdvertisement(
            @RequestBody AddAdvertisementRequestDto addAdvertisementRequestDto) {
        AddAdvertisementResponseDto responseDto = advertisementService.editAdvertisement(
                addAdvertisementRequestDto);

        return ResponseDto.success("광고 수정 완료", responseDto);
    }

    @DeleteMapping("/delete-advertisement")
    @ResponseBody
    @Operation(summary = "광고 수정 api", description = "광고를 수정하는 api입니다. 어드민 권한이 있어야지만 실행 가능합니다.")
    public ResponseDto<Void> deleteAdvertisement(
            @RequestParam String adId) {
        advertisementService.deleteAdvertisement(adId);

        return ResponseDto.success("광고 삭제 성공");
    }

    @GetMapping("/get-advertisement")
    @Operation(summary = "광고 제공 api", description = "광고를 가져오는 api입니다")
    public String getAdvertisement() {
        return "광고 페이지";
    }
}
