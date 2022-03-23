package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.entity.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestResourceTest {

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        BasicAuthorizationInterceptor basicAuthorizationInterceptor = new BasicAuthorizationInterceptor("admin", "secret");
        restTemplate.setInterceptors(Arrays.asList(basicAuthorizationInterceptor));
    }

    @Test
    public void testGetOwnerByIdAuth() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8081/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        //MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("John"));
    }

    @Test
    public void testDeleteOwner() {
        restTemplate.delete("http://localhost:8080/rest/owner/1");
        try {
            restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
            Assert.fail("should have not returned owner");
        } catch (HttpClientErrorException exception) {
            MatcherAssert.assertThat(exception.getStatusCode().value(), Matchers.equalTo(404));
        }
    }

    @Test
    public void testUpdateOwner() {
        ResponseEntity<Owner> ownerResponse = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(ownerResponse.getBody().getFirstName(), Matchers.equalTo("John"));
        Owner owner = ownerResponse.getBody();
        owner.setFirstName("Marcus");
        restTemplate.put("http://localhost:8080/rest/owner/1", owner);
        owner = restTemplate.getForObject("http://localhost:8080/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Marcus"));
    }

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Marcus");
        owner.setLastName("Souza");
        URI uriLocation = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);
        Owner createdOwner = restTemplate.getForObject(uriLocation, Owner.class);
        MatcherAssert.assertThat(createdOwner.getFirstName(), Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(createdOwner.getLastName(), Matchers.equalTo(owner.getLastName()));
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("John"));
    }

    @Test
    public void testGetOwnersByLastName() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners-by-last-name?lastName=Doe", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }

    @Test
    public void testGetOwner() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }
}