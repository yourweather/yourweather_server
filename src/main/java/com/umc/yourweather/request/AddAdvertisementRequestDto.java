package com.umc.yourweather.request;

import com.umc.yourweather.domain.entity.Advertisement;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddAdvertisementRequestDto {

    @NotBlank
    private String company;

    @NotBlank
    private String adId;

    @NotBlank
    private String summary;

    @NotBlank
    private String message;

    @NotBlank
    private String url;


    public Advertisement toEntity() {
        return Advertisement.builder()
                .company(company)
                .adId(adId)
                .summary(summary)
                .message(message)
                .url(url)
                .build();
    }
}
