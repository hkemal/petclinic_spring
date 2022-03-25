package com.javaegitimleri.petclinic.webRest;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PetClinicDeleteOwnerResource {

    @Autowired
    PetClinicService petClinicService;

    @RequestMapping(value = "/owners/delete/{id}", method = RequestMethod.GET)
    public String loadOwner(@PathVariable Long id, ModelMap modelMap) {
        Owner owner = petClinicService.findOwner(id);
        modelMap.put("owner", owner);
        return "deleteOwner";
    }

    @RequestMapping(value = "/owners/delete/{id}", method = RequestMethod.POST)
    public String handleFormSubmit(@PathVariable Long id) {
        petClinicService.deleteOwner(id);
        return "redirect:/owners";
    }
}