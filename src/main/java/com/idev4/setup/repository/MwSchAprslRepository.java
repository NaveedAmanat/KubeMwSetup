package com.idev4.setup.repository;

import com.idev4.setup.domain.MwSchAprsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwSchAprslRepository extends JpaRepository<MwSchAprsl, Long> {

    public List<MwSchAprsl> findAllByCrntRecFlg(boolean flag);

    public MwSchAprsl findOneBySchAprslSeqAndCrntRecFlg(long seq, boolean flag);

    public MwSchAprsl findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(long seq, boolean delFlag, boolean crntFlag);

    public List<MwSchAprsl> findAllBySchAprslSeq(long seq);
}

