package com.sgaraba.library.repository;

import com.sgaraba.library.domain.NotaFiscal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotaFiscal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
}
