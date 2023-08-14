package com.umc.yourweather.service;

import com.umc.yourweather.domain.entity.Advertisement;
import com.umc.yourweather.repository.AdvertisementRepository;
import com.umc.yourweather.request.AddAdvertisementRequestDto;
import com.umc.yourweather.response.AddAdvertisementResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AddAdvertisementResponseDto addAdvertisement(AddAdvertisementRequestDto requestDto) {
        Advertisement advertisement = requestDto.toEntity();

        advertisementRepository.save(advertisement);

        return AddAdvertisementResponseDto.builder()
                .summary(advertisement.getSummary())
                .url(advertisement.getUrl())
                .build();
    }
}
