package com.javaegitimleri.petclinic.web;

import com.javaegitimleri.petclinic.entity.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicRestResourceTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() {
//        restTemplate = new RestTemplate();
//        BasicAuthorizationInterceptor basicAuthorizationInterceptor = new BasicAuthorizationInterceptor("user2",
//                "secret");
//        restTemplate.setInterceptors(Arrays.asList(basicAuthorizationInterceptor));
        restTemplate = restTemplate.withBasicAuth("user2", "secret");
    }

    @Test
    public void testServiceLevelValidation() {
        Owner owner = new Owner();
//        owner.setFirstName("John");
//        owner.setLastName("Doe");
        ResponseEntity<URI> responseEntity = restTemplate.postForEntity("http://localhost:8081/rest/owner", owner, URI.class);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.equalTo(HttpStatus.PRECONDITION_FAILED));
    }

    @Test
    public void testGetOwnerByIdAuth() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8081/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Ziya"));
    }

    @Test
    public void testDeleteOwner() {
        //restTemplate.delete("http://localhost:8081/rest/owner/1");
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8081/rest/owner/1", HttpMethod.DELETE, null, Void.class);
        try {
            restTemplate.getForEntity("http://localhost:8081/rest/owner/1", Owner.class);
            Assert.fail("should have not returned owner");
        } catch (HttpClientErrorException exception) {
            MatcherAssert.assertThat(exception.getStatusCode().value(), Matchers.equalTo(404));
        }
    }

    @Test
    public void testUpdateOwner() {
        ResponseEntity<Owner> ownerResponse = restTemplate.getForEntity("http://localhost:8081/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(ownerResponse.getBody().getFirstName(), Matchers.equalTo("John"));
        Owner owner = ownerResponse.getBody();
        owner.setFirstName("Marcus");
        restTemplate.put("http://localhost:8081/rest/owner/1", owner);
        owner = restTemplate.getForObject("http://localhost:8081/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Marcus"));
    }

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Marcus");
        owner.setLastName("Souza");
        URI uriLocation = restTemplate.postForLocation("http://localhost:8081/rest/owner", owner);
        Owner createdOwner = restTemplate.getForObject(uriLocation, Owner.class);
        MatcherAssert.assertThat(createdOwner.getFirstName(), Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(createdOwner.getLastName(), Matchers.equalTo(owner.getLastName()));
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8081/rest/owner/1", Owner.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("John"));
    }

    @Test
    public void testGetOwnersByLastName() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/rest/owners-by-last-name?lastName=Doe", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }

    @Test
    public void testGetOwner() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8081/rest/owners", List.class);
        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        List<Map<String, String>> body = response.getBody();
        List<String> firstNameOfOwners = body.stream().map(item -> item.get("firstName")).collect(Collectors.toList());
        MatcherAssert.assertThat(firstNameOfOwners, Matchers.containsInAnyOrder("Charles", "Susan", "John", "Jane"));
    }
}
