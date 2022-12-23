package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAsocPrdRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the MwPrd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAsocPrdRelRepository extends JpaRepository<MwAsocPrdRel, Long> {

    public MwAsocPrdRel findOneByAsocPrdRelSeqAndCrntRecFlg(Long asocPrdRelSeq, boolean flag);

    public List<MwAsocPrdRel> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwAsocPrdRel> findAllByCrntRecFlg(boolean flag);

}



