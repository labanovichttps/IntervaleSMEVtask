package com.example.smev.repo;

import com.example.smev.dao.FineRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FineRequestRepo extends JpaRepository<FineRequest, Long> {

    Optional<FineRequest> findFirstByVehicleCertificate(String vehicleCertificate);

    Optional<FineRequest> findFirstByTaxPayerID(String taxPayerID);
}
