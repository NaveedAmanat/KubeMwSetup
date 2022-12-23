package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdAcctSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface MwPrdAcctSetRepository extends JpaRepository<MwPrdAcctSet, Long> {

    public MwPrdAcctSet findOneByPrdAcctSetSeqAndCrntRecFlg(Long prdAcctSetSeq, boolean flag);

    public List<MwPrdAcctSet> findAllByPrdAcctSetSeq(Long prdAcctSetSeq);

    public List<MwPrdAcctSet> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdAcctSet> findAllByCrntRecFlg(boolean flag);

//	public List<MwPrdAcctSet> findAllByPrdSeqCrntRecFlg(long seq, boolean flag);
}

