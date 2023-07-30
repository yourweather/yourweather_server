package com.umc.yourweather.controller;

import com.umc.yourweather.api.RequestURI;
import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.request.MemoRequestDto;
import com.umc.yourweather.request.MemoUpdateRequestDto;
import com.umc.yourweather.response.MemoResponseDto;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.service.MemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.MEMO_URI)
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/write")
    public ResponseDto<MemoResponseDto> write(@RequestBody @Valid MemoRequestDto memoRequestDto
        , @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseDto.success("메모 저장 완료", memoService.write(memoRequestDto, userDetails));
    }

    @PutMapping("/memo/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemoUpdateRequestDto requestDto) {
        return memoService.update(id, requestDto);
    }

    @DeleteMapping("/memo/{id}") //
    public Long delete(@PathVariable Long id) {
        memoService.delete(id);
        return id;
    }
}
