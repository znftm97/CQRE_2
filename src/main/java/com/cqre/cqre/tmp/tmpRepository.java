package com.cqre.cqre.tmp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface tmpRepository extends JpaRepository<tmp, Long> {
    @Query("select t from tmp t where t.id = :id")
    tmp CFindById(@Param("id") Long id);
}
