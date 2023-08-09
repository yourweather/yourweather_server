package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.request.MemoUpdateRequestDto;
import com.umc.yourweather.response.MemoDailyResponseDto;
import com.umc.yourweather.response.MemoResponseDto;
import com.umc.yourweather.response.MemoUpdateResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.service.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.MEMO_URI)
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/write")
    @Operation(summary = "메모 작성 api", description = "메모 작성을 위한 API입니다. 전달받은 요청 데이터들을 참조하여 메모를 저장합니다.")
    public ResponseDto<MemoResponseDto> write(@RequestBody @Valid MemoRequestDto memoRequestDto
            , @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("메모 저장 완료", memoService.write(memoRequestDto, userDetails));
    }

    @GetMapping("/daily/{year}/{month}/{day}")
    @Operation(summary = "하루 치 메모 반환 api", description = "메모 작성을 위한 API입니다. 전달받은 요청 데이터들을 참조하여 메모를 저장합니다.")
    public ResponseDto<MemoDailyResponseDto> daily(@PathVariable int year,
                                                   @PathVariable int month,
                                                   @PathVariable int day,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        LocalDate localDate = LocalDate.of(year, month, day);

        return ResponseDto.success(year+month+day+" 메모 조회 성공", memoService.getDailyList(localDate, userDetails));
    }


    @Operation(summary = "메모 수정 api", description = "메모 수정을 위한 API입니다.")
    @PutMapping("/update/{memoId}")
    public ResponseDto<MemoUpdateResponseDto> update(@PathVariable Long memoId, @RequestBody MemoUpdateRequestDto requestDto) {
        return ResponseDto.success("메모 수정 완료", memoService.update(memoId, requestDto));
    }

    @Operation(summary = "메모 삭제 api", description = "메모 삭제을 위한 API입니다.")
    @DeleteMapping("/delete/{memoId}")
    public ResponseDto<Void> delete(@PathVariable Long memoId) {
        memoService.delete(memoId);
        return ResponseDto.success("메모 삭제 완료");
    }
}