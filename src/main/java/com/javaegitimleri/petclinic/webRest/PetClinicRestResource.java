package com.javaegitimleri.petclinic.webRest;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.exception.InternalServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PetClinicRestResource {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/owner/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable(name = "id") Long id) {
        try {
            petClinicService.findOwner(id);
            petClinicService.deleteOwner(id);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new InternalServerException(exception);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @RequestBody Owner ownerRequest) {
        try {
            Owner owner = petClinicService.findOwner(id);
            owner.setFirstName(ownerRequest.getFirstName());
            owner.setLastName(ownerRequest.getLastName());
            petClinicService.updateOwner(owner);
            return ResponseEntity.ok().build();
        } catch (OwnerNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/owner")
    public ResponseEntity<URI> createOwner(@RequestBody Owner owner) {
        try {
            petClinicService.createOwner(owner);
            Long id = owner.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public ResponseEntity<List<Owner>> getOwners() {
        List<Owner> owners = petClinicService.findOwners();
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owners-by-last-name")
    public ResponseEntity<List<Owner>> getOwnersByLastName(@RequestParam("lastName") String lastName) {
        List<Owner> owners = petClinicService.findOwners(lastName);
        return ResponseEntity.ok(owners);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
    public ResponseEntity<Owner> getOneOwner(@PathVariable("id") Long id) {
        try {
            Owner owner = petClinicService.findOwner(id);
            return ResponseEntity.ok(owner);
        } catch (OwnerNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    //TODO: this api must be parametical by id
    @RequestMapping(method = RequestMethod.GET, value = "/owner/hateoas-test", produces = "application/json")
    public ResponseEntity<?> getOwnerAsHateoasResource() {
        try {
            Owner owner = petClinicService.findOwner(1L);
            Link self = ControllerLinkBuilder.linkTo(PetClinicRestResource.class).slash("/owner/" + 1).withSelfRel();
            Link create = ControllerLinkBuilder.linkTo(PetClinicRestResource.class).slash("/owner").withRel("create");
            Link update = ControllerLinkBuilder.linkTo(PetClinicRestResource.class).slash("/owner/" + 1).withRel("update");
            Link delete = ControllerLinkBuilder.linkTo(PetClinicRestResource.class).slash("/owner/" + 1).withRel("delete");
            Resource<Owner> resource = new Resource<Owner>(owner, self, create, update, delete);
            return ResponseEntity.ok(resource);
        } catch (OwnerNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
