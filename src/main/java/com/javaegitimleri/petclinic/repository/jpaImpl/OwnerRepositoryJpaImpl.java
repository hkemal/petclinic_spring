package com.javaegitimleri.petclinic.repository.jpaImpl;

import com.javaegitimleri.petclinic.entity.Owner;
import com.javaegitimleri.petclinic.repository.repo.OwnerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("ownerRepository")
public class OwnerRepositoryJpaImpl implements OwnerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = entityManager.createQuery("select O from Owner as O", Owner.class).getResultList();
        return owners;
    }

    @Override
    public Owner findById(Long id) {
        Owner owner = entityManager.find(Owner.class, id);
        return owner;
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        List<Owner> owners = entityManager.createQuery("select O from Owner as O where O.lastName = :lastName", Owner.class)
                .setParameter("lastName", lastName).getResultList();
        return owners;
    }

    @Override
    public void create(Owner owner) {
        entityManager.persist(owner);
    }

    @Override
    public Owner update(Owner owner) {
        Owner updatedOwner = entityManager.merge(owner);
        return updatedOwner;
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Owner.class, id));
    }
}
