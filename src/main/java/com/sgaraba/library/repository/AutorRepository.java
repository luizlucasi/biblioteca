package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Autor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Autor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>, JpaSpecificationExecutor<Autor> {

}
