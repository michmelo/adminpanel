package com.vitalconnect.adminpanel.repository;

import com.vitalconnect.adminpanel.model.Admin;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByRut(String rut);
    //boolean existsByRut(String rut);

    @Modifying
    @Transactional
    @Query
    void deleteByRut(@Param("rut")String rut);
}
