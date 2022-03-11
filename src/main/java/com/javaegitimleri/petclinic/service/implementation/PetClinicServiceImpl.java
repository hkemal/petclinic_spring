package com.javaegitimleri.petclinic.service.implementation;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.exception.OwnersNotFoundException;
import com.javaegitimleri.petclinic.repository.repo.OwnerRepository;
import com.javaegitimleri.petclinic.repository.repo.PetRepository;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @Override
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public List<Owner> findOwners(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Owner findOwner(Long id) throws OwnersNotFoundException {
        Owner owner = ownerRepository.findById(id);
        if (owner == null) throw new OwnersNotFoundException("Owner not found with id : " + id);
        return owner;
    }

    @Override
    public void createOwner(Owner owner) {
        ownerRepository.create(owner);
    }

    @Override
    public void updateOwner(Owner owner) {
        ownerRepository.update(owner);
    }

    @Override
    public void deleteOwner(Long id) {
        petRepository.deleteByOwnerId(id);
        ownerRepository.delete(id);
//        if (true) throw new RuntimeException();
    }
}
