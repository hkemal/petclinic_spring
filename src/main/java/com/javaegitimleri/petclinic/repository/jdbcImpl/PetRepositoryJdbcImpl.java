package com.javaegitimleri.petclinic.repository.jdbcImpl;

import com.javaegitimleri.petclinic.entity.Pet;
import com.javaegitimleri.petclinic.repository.repo.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetRepositoryJdbcImpl implements PetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Pet> findAll() {
        return null;
    }

    @Override
    public Pet findById(Long id) {
        return null;
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public void create(Pet pet) {

    }

    @Override
    public Pet update(Pet pet) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteByOwnerId(Long ownerId) {
        String sql = " delete from t_pet where owner_id = ?";
        jdbcTemplate.update(sql, ownerId);
    }
}
