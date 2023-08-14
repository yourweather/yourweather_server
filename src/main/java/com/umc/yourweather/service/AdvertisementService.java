package com.umc.yourweather.service;

import com.umc.yourweather.domain.entity.Advertisement;
import com.umc.yourweather.repository.AdvertisementRepository;
import com.umc.yourweather.request.AddAdvertisementRequestDto;
import com.umc.yourweather.response.AddAdvertisementResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public AddAdvertisementResponseDto addAdvertisement(AddAdvertisementRequestDto requestDto) {
        Advertisement advertisement = requestDto.toEntity();

        advertisementRepository.save(advertisement);

        return AddAdvertisementResponseDto.builder()
                .summary(advertisement.getSummary())
                .url(advertisement.getUrl())
                .build();
    }

    @Transactional
    public AddAdvertisementResponseDto editAdvertisement(
            AddAdvertisementRequestDto addAdvertisementRequestDto) {

        Advertisement advertisement = advertisementRepository.findByAdId(
                        addAdvertisementRequestDto.getAdId())
                .orElseThrow(
                        () -> new EntityNotFoundException("광고 찾기 실패: 해당 AdId와 매칭되는 광고가 없습니다."));

        advertisement.update(addAdvertisementRequestDto);

        return AddAdvertisementResponseDto.builder()
                .summary(advertisement.getSummary())
                .url(advertisement.getUrl())
                .build();
    }

    @Transactional
    public void deleteAdvertisement(String adId) {
        Advertisement advertisement = advertisementRepository.findByAdId(adId)
                .orElseThrow(
                        () -> new EntityNotFoundException("광고 찾기 실패: 해당 AdId와 매칭되는 광고가 없습니다."));
        advertisementRepository.delete(advertisement);
    }
}
