package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdChrgAdjOrdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdChrgAdjOrdrRepository extends JpaRepository<MwPrdChrgAdjOrdr, Long> {

    public MwPrdChrgAdjOrdr findOneByPrdChrgAdjOrdrSeqAndCrntRecFlg(Long prdChrgAdjOrdrSeq, boolean flag);

    public List<MwPrdChrgAdjOrdr> findAllByPrdChrgAdjOrdrSeq(Long prdChrgAdjOrdrSeq);

    public List<MwPrdChrgAdjOrdr> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdChrgAdjOrdr> findAllByCrntRecFlg(boolean flag);
}


