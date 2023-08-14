package com.umc.yourweather.repository.impl;

import com.umc.yourweather.domain.entity.Advertisement;
import com.umc.yourweather.repository.AdvertisementRepository;
import com.umc.yourweather.repository.jpa.AdvertisementJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryImpl implements AdvertisementRepository {
    private final AdvertisementJpaRepository advertisementJpaRepository;

    @Override
    public Advertisement save(Advertisement advertisement) {
        return advertisementJpaRepository.save(advertisement);
    }

    @Override
    public Optional<Advertisement> findById(Long id) {
        return advertisementJpaRepository.findById(id);
    }

    @Override
    public Optional<Advertisement> findByAdId(String adId) {
        return advertisementJpaRepository.findByAdId(adId);
    }

    @Override
    public void delete(Advertisement advertisement) {
        advertisementJpaRepository.delete(advertisement);
    }
}
