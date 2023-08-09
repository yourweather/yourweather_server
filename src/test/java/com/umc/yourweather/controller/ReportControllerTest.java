package com.umc.yourweather.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.umc.yourweather.auth.CustomUserDetails;
import com.umc.yourweather.domain.entity.User;
import com.umc.yourweather.domain.enums.Status;
import com.umc.yourweather.repository.MemoRepository;
import com.umc.yourweather.repository.test.MemoTestRepository;
import com.umc.yourweather.response.ResponseDto;
import com.umc.yourweather.response.SpecificMemoResponseDto;
import com.umc.yourweather.service.ReportService;
import org.junit.jupiter.api.Test;

class ReportControllerTest {
    private MemoRepository memoRepository = new MemoTestRepository(31);
    private ReportService reportService = new ReportService(memoRepository);
    private ReportController reportController = new ReportController(reportService);

    @Test
    void getSpecificMemoList() {
        // given
        User user = User.builder()
                .email("sbs8239@gmail.com")
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        int month = 7;
        Status sunny = Status.SUNNY;

        // when
        ResponseDto<SpecificMemoResponseDto> specificMemoList = reportController.getSpecificMemoList(
                customUserDetails, month, sunny);

        // then
        System.out.println(specificMemoList.toString());
        assertEquals(200, specificMemoList.getCode());
    }
}
/**
 * ResponseDto(success=true, code=200, message=월간 특정 날씨 일자 조회 성공.,
 *             result= SpecificMemoResponseDto(
 *                     memoList=[
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-07-28T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-07-29T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-07-30T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-07-31T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-01T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-02T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-03T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-04T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-05T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-06T21:19:01.570862400),
 *                             MemoReportResponseDto(memoId=null, dateTime=2023-08-07T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-08T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-09T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-10T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-11T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-12T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-13T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-14T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-15T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-16T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-17T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-18T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-19T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-20T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-21T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-22T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-23T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-24T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-25T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-26T21:19:01.570862400), MemoReportResponseDto(memoId=null, dateTime=2023-08-27T21:19:01.570862400)],
 *                             proportion=12.903225
 *         )
 *     )
 * **/