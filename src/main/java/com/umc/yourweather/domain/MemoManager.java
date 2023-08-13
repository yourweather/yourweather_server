package com.umc.yourweather.domain;

import com.umc.yourweather.domain.entity.Memo;
import com.umc.yourweather.exception.MemoNotFoundException;
import java.util.List;

public class MemoManager {

    public static void isMemoListEmpty(List<Memo> memoList) {
        if (memoList.isEmpty())
            throw new MemoNotFoundException("오늘 날짜의 날씨에 대한 메모가 없습니다.");
    }
}
