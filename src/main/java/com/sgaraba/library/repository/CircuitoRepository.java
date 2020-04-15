package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Circuito;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Circuito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CircuitoRepository extends JpaRepository<Circuito, Long> {
}
