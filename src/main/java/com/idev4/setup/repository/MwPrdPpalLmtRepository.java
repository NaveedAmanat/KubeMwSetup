package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdPpalLmt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdPpalLmtRepository extends JpaRepository<MwPrdPpalLmt, Long> {

    public MwPrdPpalLmt findOneByPrdPpalLmtSeqAndCrntRecFlg(Long prdPpalLmtSeq, boolean flag);

    public MwPrdPpalLmt findOneByPrdPpalLmtSeq(Long prdPpalLmtSeq);

    public List<MwPrdPpalLmt> findAllByPrdPpalLmtSeq(Long prdTrmSeq);

    public List<MwPrdPpalLmt> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdPpalLmt> findAllByPrdSeqAndCrntRecFlgOrderByPrdPpalLmtSeq(Long prdSeq, boolean flag);

    public List<MwPrdPpalLmt> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdPpalLmt> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                            Instant ldate, boolean delFlg);

    @Query(value = "select distinct pplmt.* from mw_prd_ppal_lmt pplmt  \r\n" + "join mw_prd prd on prd.prd_seq=pplmt.prd_seq \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate  and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate  and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(pplmt.last_upd_dt > :syncDate  and (pplmt.crnt_rec_flg=1 or pplmt.del_flg=1)))\r\n"
        + "order by pplmt.last_upd_dt", nativeQuery = true)
    public List<MwPrdPpalLmt> findUpdatedMwPrdPpalLmtForBrnch(@Param("brnchSeq") Long brnchSeq,
                                                              @Param("syncDate") Instant syncDate);

}
