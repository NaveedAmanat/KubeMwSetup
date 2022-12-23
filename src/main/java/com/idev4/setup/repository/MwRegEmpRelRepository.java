package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRegEmpRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MwRegEmpRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwRegEmpRelRepository extends JpaRepository<MwRegEmpRel, Long> {

    public List<MwRegEmpRel> findAllByRegSeqAndCrntRecFlg(long regSeq, boolean flag);

    public MwRegEmpRel findOneByRegEmpSeqAndCrntRecFlg(long seq, boolean flag);

    public MwRegEmpRel findOneByRegSeqAndCrntRecFlg(long seq, boolean flag);
}
