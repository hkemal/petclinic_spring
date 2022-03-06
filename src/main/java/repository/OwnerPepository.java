package repository;

import entity.Owner;

import java.util.List;

public interface OwnerPepository {

    List<Owner> findAll();

    Owner findById(Long id);

    List<Owner> findByLastName(String lastName);

    void create(Owner owner);

    Owner update(Owner owner);

    void delete(Long id);
}