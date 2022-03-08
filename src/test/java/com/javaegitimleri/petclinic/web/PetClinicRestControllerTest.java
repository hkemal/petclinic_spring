package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.entity.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Test
    public void testGetOwnersByLastName() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8082/rest/owners-by-last-name?lastName=Doe", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }

    @Test
    public void testGetOwner() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8082/rest/owners", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }
}
