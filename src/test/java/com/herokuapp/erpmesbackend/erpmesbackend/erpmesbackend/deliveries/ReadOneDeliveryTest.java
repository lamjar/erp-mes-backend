package com.herokuapp.erpmesbackend.erpmesbackend.erpmesbackend.deliveries;

import com.herokuapp.erpmesbackend.erpmesbackend.erpmesbackend.FillBaseTemplate;
import com.herokuapp.erpmesbackend.erpmesbackend.shop.deliveries.Delivery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadOneDeliveryTest extends FillBaseTemplate {

    private List<Delivery> deliveries;

    @Before
    public void init() {
        addManyDeliveryRequests(true);
        deliveries = Arrays.asList(restTemplate.getForEntity("/deliveries", Delivery[].class).getBody());
    }

    @Test
    public void checkIfResponseContainsRequestedDelivery() {
        for(int i = 0; i < deliveryRequests.size(); i++) {
            ResponseEntity<Delivery> forEntity = restTemplate.getForEntity("/deliveries/{id}",
                    Delivery.class, i + 1);
            assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertTrue(deliveries.stream().anyMatch(delivery -> delivery
                    .checkIfDataEquals(forEntity.getBody())));
        }
    }
}