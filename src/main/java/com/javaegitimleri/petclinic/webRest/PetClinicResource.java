package com.javaegitimleri.petclinic.webRest;

import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetClinicResource {

    @Autowired
    PetClinicService petClinicService;

    @RequestMapping(value = {"/", "/index.html"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/owners")
    public ModelAndView getOwners() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("owners", petClinicService.findOwners());
        modelAndView.setViewName("owners");
        return modelAndView;
    }

    @RequestMapping("/welcome")
    @ResponseBody
    public String welcome() {
        return "Welcome to PetClinic World!";
    }
}