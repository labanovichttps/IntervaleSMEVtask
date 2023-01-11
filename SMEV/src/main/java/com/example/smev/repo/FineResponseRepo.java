package com.example.smev.repo;

import com.example.smev.dao.FineResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FineResponseRepo extends JpaRepository<FineResponse, Long> {

    Optional<FineResponse> findFirstByVehicleCertificate(String vehicleCertificate);
    Optional<FineResponse> findFirstByTaxPayerID(String TaxPayerID);
}
