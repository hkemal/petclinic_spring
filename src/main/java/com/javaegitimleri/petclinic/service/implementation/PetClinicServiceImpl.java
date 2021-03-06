package com.javaegitimleri.petclinic.service.implementation;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.entity.Vet;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.repository.repo.OwnerRepository;
import com.javaegitimleri.petclinic.repository.repo.PetRepository;
import com.javaegitimleri.petclinic.repository.repo.VetRepository;
import com.javaegitimleri.petclinic.service.petclinic.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Service
@Transactional(rollbackFor = Exception.class)
public class PetClinicServiceImpl implements PetClinicService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    VetRepository vetRepository;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Secured(value = {"ROLE_USER", "ROLE_EDITOR"})
    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Owner> findOwners(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Owner findOwner(Long id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id);
        if (owner == null) throw new OwnerNotFoundException("Owner not found with id : " + id);
        return owner;
    }

    @Override
    @CacheEvict(cacheNames = "allOwners", allEntries = true)
    public void createOwner(@Valid Owner owner) {
        ownerRepository.create(owner);

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("k@g");
//        message.setTo("d@a");
//        message.setSubject("Owner created!");
//        message.setText("Owner entity with id : " + owner.getId() + "created successfully.");
//        javaMailSender.send(message);
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

    @Override
    public List<Vet> findVets() {
        return vetRepository.findAll();
    }

    @Override
    public Vet findVet(Long id) throws VetNotFoundException {
        return vetRepository.findById(id).get();
    }
}
