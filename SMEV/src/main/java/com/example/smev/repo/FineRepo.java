package com.example.smev.repo;

import com.example.smev.dao.Fine;
import com.example.smev.dao.FineRequest;
import com.example.smev.dao.FineResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FineRepo extends JpaRepository<Fine, Long> {
    Optional<Fine> findFineByTaxPayerID(String taxPayerID);
    Optional<Fine> findFineByVehicleCertificate(String vehicleCertificate);
}
