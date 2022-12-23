package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAprvlWf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwAprvlWf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAprvlWfRepository extends JpaRepository<MwAprvlWf, Long> {

    public List<MwAprvlWf> findAllByCrntRecFlg(boolean flag);
}
