package com.sgaraba.library.repository;

import com.sgaraba.library.domain.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Livro entity.
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

    @Query(value = "select distinct livro from Livro livro left join fetch livro.autors",
        countQuery = "select count(distinct livro) from Livro livro")
    Page<Livro> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct livro from Livro livro left join fetch livro.autors")
    List<Livro> findAllWithEagerRelationships();

    @Query("select livro from Livro livro left join fetch livro.autors where livro.id =:id")
    Optional<Livro> findOneWithEagerRelationships(@Param("id") Long id);

}
