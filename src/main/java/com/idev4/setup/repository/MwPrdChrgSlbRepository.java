package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdChrgSlb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdChrgSlbRepository extends JpaRepository<MwPrdChrgSlb, Long> {

    public MwPrdChrgSlb findOneByPrdChrgSlbSeqAndCrntRecFlg(Long prdChrgSeq, boolean flag);

    public List<MwPrdChrgSlb> findAllByPrdChrgSeqAndCrntRecFlg(Long prdChrgSeq, boolean flag);

    @Query(value = "select distinct  slb.* from MW_PRD_CHRG_SLB slb  \r\n"
        + "join mw_prd_chrg chrg on chrg.PRD_CHRG_SEQ = slb.PRD_CHRG_SEQ \r\n" + "join mw_prd prd on prd.prd_seq=chrg.prd_seq \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(chrg.last_upd_dt >  :syncDate and (chrg.crnt_rec_flg=1 or chrg.del_flg=1)) or\r\n"
        + "(slb.last_upd_dt > :syncDate and (slb.crnt_rec_flg=1 or slb.del_flg=1)))\r\n"
        + "order by slb.last_upd_dt", nativeQuery = true)
    public List<MwPrdChrgSlb> findUpdatedMwPrdChrgSlbForBrnch(@Param("brnchSeq") Long brnchSeq,
                                                              @Param("syncDate") Instant syncDate);
}
