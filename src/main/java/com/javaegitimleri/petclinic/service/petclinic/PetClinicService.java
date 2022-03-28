package com.javaegitimleri.petclinic.service.petclinic;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.entity.Vet;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;

import java.util.List;

public interface PetClinicService {

    List<Owner> findOwners();

    List<Owner> findOwners(String lastName);

    Owner findOwner(Long id) throws OwnerNotFoundException;

    void createOwner(Owner owner);

    void updateOwner(Owner owner);

    void deleteOwner(Long id);

    List<Vet> findVets();

    Vet findVet(Long id) throws VetNotFoundException;
}
