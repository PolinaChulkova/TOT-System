package com.example.testproject.repo;

import com.example.testproject.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepo extends JpaRepository<Security, Long> {

    Optional<Security> findById(Long id);

    Security findBySecId(String secId);

    Boolean existsBySecId(String secId);

    void deleteById(Long id);

}
