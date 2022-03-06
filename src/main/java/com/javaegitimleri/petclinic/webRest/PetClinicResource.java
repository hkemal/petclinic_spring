package com.javaegitimleri.petclinic.webRest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PetClinicResource {

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "Welcome to PetClinic World!";
    }
}