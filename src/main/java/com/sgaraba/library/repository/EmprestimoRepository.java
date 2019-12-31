package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Emprestimo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Emprestimo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>, JpaSpecificationExecutor<Emprestimo> {

}
