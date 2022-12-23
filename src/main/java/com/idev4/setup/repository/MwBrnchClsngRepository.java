package com.idev4.setup.repository;

import com.idev4.setup.domain.MwBrnchClsng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwBrnchClsngRepository extends JpaRepository<MwBrnchClsng, Long> {

    public MwBrnchClsng findOneByBrnchSeqAndBrnchOpnFlg(Long seq, boolean flag);

}
