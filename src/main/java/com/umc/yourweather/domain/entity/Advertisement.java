package com.umc.yourweather.domain.entity;

import com.umc.yourweather.request.AddAdvertisementRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String adId;
    private String summary;
    private String message;
    private String url;


    @Builder
    public Advertisement(String company, String adId, String summary, String message, String url) {
        this.company = company;
        this.adId = adId;
        this.summary = summary;
        this.message = message;
        this.url = url;
    }

    public void update(AddAdvertisementRequestDto requestDto) {
        this.company = requestDto.getCompany();
        this.adId = requestDto.getAdId();
        this.summary = requestDto.getSummary();
        this.message = requestDto.getMessage();
        this.url = requestDto.getUrl();
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getCompany() {
        return company;
    }

    public String getSummary() {
        return summary;
    }

    public String getAdId() {
        return adId;
    }
}
