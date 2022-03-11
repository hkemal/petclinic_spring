package com.javaegitimleri.petclinic.repository.jpaImpl;

import com.javaegitimleri.petclinic.entity.Pet;
import com.javaegitimleri.petclinic.repository.repo.PetRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("petRepository")
public class PetRepositoryJpaImpl implements PetRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = entityManager.createQuery("select O from Pet as O", Pet.class).getResultList();
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        Pet pet = entityManager.find(Pet.class, id);
        return pet;
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        List<Pet> pets = entityManager.createQuery("select P from Pet as P where P.owner.id = :ownerId ", Pet.class)
                .setParameter("ownerId", ownerId).getResultList();
        return pets;
    }

    @Override
    public void create(Pet pet) {
        entityManager.persist(pet);
    }

    @Override
    public Pet update(Pet pet) {
        Pet updatedPet = entityManager.merge(pet);
        return updatedPet;
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Pet.class, id));
    }

    @Override
    public void deleteByOwnerId(Long ownerId) {
        entityManager.createQuery("delete from Pet as P where P.owner.id = :ownerId")
                .setParameter("ownerId", ownerId).executeUpdate();
    }
}
