package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.entity.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PetClinicRestControllerTest {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8082/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("John"));
    }
}
