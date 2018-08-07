package com.herokuapp.erpmesbackend.erpmesbackend.erpmesbackend.holidays;

import com.herokuapp.erpmesbackend.erpmesbackend.erpmesbackend.FillBaseTemplate;
import com.herokuapp.erpmesbackend.erpmesbackend.holidays.Holiday;
import com.herokuapp.erpmesbackend.erpmesbackend.holidays.HolidayRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadAllHolidaysTest extends FillBaseTemplate {

    @Before
    public void init() {
        addOneAdminRequest(true);
        addNonAdminRequests(true);
        addManyHolidayRequests(2, true);
    }

    @Test
    public void checkIfResponseContainsAllHolidays() {
        ResponseEntity<Holiday[]> forEntity = restTemplate.getForEntity("/employees/{id}/holidays",
                Holiday[].class, 2);

        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Holiday> holidays = Arrays.asList(forEntity.getBody());
        holidayRequests.forEach(request -> assertTrue(holidays.stream()
                .anyMatch(holiday -> checkIfHolidayAndRequestMatch(holiday, request))));
    }

    private boolean checkIfHolidayAndRequestMatch(Holiday holiday,
                                                  HolidayRequest holidayRequest) {
        return holiday.getStartDate().equals(holidayRequest.getStartDate()) &&
                holiday.getDuration() == holidayRequest.getDuration() &&
                holiday.getHolidayType().equals(holidayRequest.getHolidayType());
    }
}