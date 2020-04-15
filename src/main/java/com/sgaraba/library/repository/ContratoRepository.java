package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Contrato;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contrato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
