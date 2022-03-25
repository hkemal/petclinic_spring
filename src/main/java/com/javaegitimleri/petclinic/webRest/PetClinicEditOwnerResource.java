package com.javaegitimleri.petclinic.webRest;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PetClinicEditOwnerResource {

    @Autowired
    PetClinicService petClinicService;

    @RequestMapping(value = "/owners/update/{id}", method = RequestMethod.GET)
    public String loadOwner(@PathVariable Long id, ModelMap modelMap) {
        Owner owner = petClinicService.findOwner(id);
        modelMap.put("owner", owner);
        return "editOwner";
    }

    @RequestMapping(value = "/owners/update/{id}", method = RequestMethod.POST)
    public String handleFormSubmit(@ModelAttribute @Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editOwner";
        }
        petClinicService.updateOwner(owner);
        return "redirect:/owners";
    }
}