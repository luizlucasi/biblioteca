package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Projeto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Projeto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
