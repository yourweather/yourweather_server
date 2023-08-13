package com.umc.yourweather.domain;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.exception.MemoNotFoundException;
import java.util.List;

public class MemoManager {

    static class StatusChecker {
        boolean SUNNY;
        boolean CLOUDY;
        boolean RAINY;
        boolean LIGHTNING;
    }

    public void isMemoListEmpty(List<Memo> memoList) {
        if (memoList.isEmpty())
            throw new MemoNotFoundException("오늘 날짜의 날씨에 대한 메모가 없습니다.");
    }

    public String getImageName(List<Memo> memoList) {
        StatusChecker statusChecker = new StatusChecker();

        memoList.forEach(memo -> {
            switch (memo.getStatus()) {
                case SUNNY -> statusChecker.SUNNY = true;
                case CLOUDY -> statusChecker.CLOUDY = true;
                case RAINY -> statusChecker.RAINY = true;
                case LIGHTNING -> statusChecker.LIGHTNING = true;
            }
        });

        // 가장 단순하면서 쉬운 방식.
        // 케이스가 적어서 무식한 방법을 써도 무방하지만, 나중에 날씨가 더 추가되거나 하면 좀 더 똑똑한 방식으로 접근할 필요성이 있을 듯.
        // 파일의 네이밍을 의미없이 짓지말고 각 날씨별 우선순위를 생각하면서 지으면 훨씬 쉽게 풀어낼 수 있을 듯
        if (statusChecker.SUNNY && !statusChecker.CLOUDY && !statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home1_sunny.jpg";

        if (!statusChecker.SUNNY && statusChecker.CLOUDY && !statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home1_cloudy.jpg";

        if (!statusChecker.SUNNY && !statusChecker.CLOUDY && statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home1_rainy.jpg";

        if (!statusChecker.SUNNY && !statusChecker.CLOUDY && !statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home1_lightning.jpg";

        if (!statusChecker.SUNNY && statusChecker.CLOUDY && statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home2_rainycloudy.jpg";

        if (!statusChecker.SUNNY && !statusChecker.CLOUDY && statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home2_rainylightning.jpg";

        if (statusChecker.SUNNY && statusChecker.CLOUDY && !statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home2_sunnycloudy.jpg";

        if (statusChecker.SUNNY && !statusChecker.CLOUDY && statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home2_sunnyrainy.jpg";

        if (statusChecker.SUNNY && !statusChecker.CLOUDY && !statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home2_sunnylightning.jpg";

        if (!statusChecker.SUNNY && statusChecker.CLOUDY && !statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home2_lightningcloudy.jpg";

        if (!statusChecker.SUNNY && statusChecker.CLOUDY && statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home3_cloudyrainylightning.jpg";

        if (statusChecker.SUNNY && statusChecker.CLOUDY && statusChecker.RAINY && !statusChecker.LIGHTNING)
            return "bg_home3_sunnycloudyrainy.jpg";

        if (statusChecker.SUNNY && statusChecker.CLOUDY && !statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home3_sunnycloudylightning.jpg";

        if (statusChecker.SUNNY && !statusChecker.CLOUDY && statusChecker.RAINY && statusChecker.LIGHTNING)
            return "bg_home3_sunnyrainylightning.jpg";

        return "bg_home4_max.jpg";
    }
}
