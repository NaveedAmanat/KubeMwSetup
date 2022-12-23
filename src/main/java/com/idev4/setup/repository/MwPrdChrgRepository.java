package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdChrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdChrgRepository extends JpaRepository<MwPrdChrg, Long> {

    public MwPrdChrg findOneByPrdChrgSeqAndCrntRecFlg(Long prdChrgSeq, boolean flag);

    public MwPrdChrg findOneByPrdChrgSeq(Long prdChrgSeq);

    public List<MwPrdChrg> findAllByPrdChrgSeq(Long prdChrgSeq);

    public List<MwPrdChrg> findAllByPrdSeqAndCrntRecFlgAndDelFlg(Long prdSeq, boolean flag, boolean delFlg);

    public List<MwPrdChrg> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdChrg> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgOrderByCrntRecFlgAsc(Instant date,
                                                                                                             boolean crntRecFlg, Instant ldate, boolean delFlg);

    @Query(value = "select distinct pchrg.* from mw_prd_chrg pchrg  \r\n" + "join mw_prd prd on prd.prd_seq=pchrg.prd_seq \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(pchrg.last_upd_dt > :syncDate and (pchrg.crnt_rec_flg=1 or pchrg.del_flg=1)))\r\n"
        + "order by pchrg.last_upd_dt", nativeQuery = true)
    public List<MwPrdChrg> findUpdatedMwPrdChrgForBrnch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);

}
