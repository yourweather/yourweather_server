package com.umc.yourweather.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String summary;
    private String message;
    private String url;


    @Builder
    public Advertisement(String company, String summary, String message, String url) {
        this.company = company;
        this.summary = summary;
        this.message = message;
        this.url = url;
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
}
