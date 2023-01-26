package com.example.smev.repo;

import com.example.smev.dao.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepo extends JpaRepository<Fine, Long> {
    Optional<List<Fine>> findAllByTaxPayerID(String taxPayerID);
    Optional<List<Fine>> findAllByVehicleCertificate(String vehicleCertificate);
}
