package repository;

import entity.Pet;

import java.util.List;

public interface PetPepository {

    List<Pet> findAll();

    Pet findById(Long id);

    List<Pet> findByOwnerId(Long ownerId);

    void create(Pet pet);

    Pet update(Pet pet);

    void delete(Long id);

    void deleteByOwnerId(Long ownerId);
}
