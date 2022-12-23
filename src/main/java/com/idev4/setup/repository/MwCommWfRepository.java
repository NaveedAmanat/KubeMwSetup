package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCommWf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwCommWf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCommWfRepository extends JpaRepository<MwCommWf, Long> {

    public List<MwCommWf> findAllByCrntRecFlg(boolean flag);

    public MwCommWf findOneByCommWfSeqAndCrntRecFlg(long seq, boolean flag);
}
