package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPerd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwPort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPerdRepository extends JpaRepository<MwPerd, Long> {

}
