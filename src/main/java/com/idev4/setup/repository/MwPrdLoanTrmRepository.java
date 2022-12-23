package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdLoanTrm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdLoanTrmRepository extends JpaRepository<MwPrdLoanTrm, Long> {

    public MwPrdLoanTrm findOneByPrdTrmSeqAndCrntRecFlg(Long prdTrmSeq, boolean flag);

    public MwPrdLoanTrm findOneByPrdTrmSeq(Long prdTrmSeq);

    public List<MwPrdLoanTrm> findAllByPrdTrmSeq(Long prdTrmSeq);

    public MwPrdLoanTrm findOneByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public MwPrdLoanTrm findOneByPrdSeqAndRulSeqAndCrntRecFlg(Long prdSeq, Long seq, boolean flag);

    public List<MwPrdLoanTrm> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdLoanTrm> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdLoanTrm> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                            Instant ldate, boolean delFlg);

    @Query(value = "select distinct  ptrm.* from mw_prd_loan_trm ptrm  join mw_prd prd on prd.prd_seq=ptrm.prd_seq \r\n"
        + "            join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "            join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "            where bprel.brnch_seq=:brnchSeq \r\n"
        + "            and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "            (ptrm.last_upd_dt > :syncDate and (ptrm.crnt_rec_flg=1 or ptrm.del_flg=1)))\r\n"
        + "            order by ptrm.last_upd_dt", nativeQuery = true)
    public List<MwPrdLoanTrm> findUpdatedMwPrdLoanTrmForBrnch(@Param("brnchSeq") Long brnchSeq,
                                                              @Param("syncDate") Instant syncDate);
}
