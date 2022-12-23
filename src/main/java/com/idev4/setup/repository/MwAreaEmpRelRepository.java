package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAreaEmpRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MwAreaEmpRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAreaEmpRelRepository extends JpaRepository<MwAreaEmpRel, Long> {

    public List<MwAreaEmpRel> findAllByAreaSeqAndCrntRecFlg(long areaSeq, boolean flag);

    public MwAreaEmpRel findOneByAreaEmpSeqAndCrntRecFlg(long seq, boolean flag);

    public MwAreaEmpRel findOneByAreaSeqAndCrntRecFlg(long seq, boolean flag);
}
