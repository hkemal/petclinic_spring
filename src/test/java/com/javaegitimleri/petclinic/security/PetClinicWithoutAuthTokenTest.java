package com.javaegitimleri.petclinic.security;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=dev")
public class PetClinicWithoutAuthTokenTest {

    @Autowired
    PetClinicService petClinicService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void testFindOwners() {
        List<Owner> owners = petClinicService.findOwners();
    }
}
