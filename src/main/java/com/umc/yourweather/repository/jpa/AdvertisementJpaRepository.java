package com.umc.yourweather.repository.jpa;

import com.umc.yourweather.domain.entity.Advertisement;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementJpaRepository extends JpaRepository<Advertisement, Long> {
    Optional<Advertisement> findByAdId(String adId);
}
