package service;

import entity.Owner;
import exception.OwnersNotFoundException;

import java.util.List;

public interface PetClinicService {

    List<Owner> findOwners();

    List<Owner> findOwners(String lastName);

    Owner findOwner(Long id) throws OwnersNotFoundException;

    void createOwner(Owner owner);

    void updateOwner(Owner owner);

    void deleteOwner(Long id);
}
