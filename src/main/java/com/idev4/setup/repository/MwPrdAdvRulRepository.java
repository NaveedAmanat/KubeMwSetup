package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdAdvRul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwPrdAdvRul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdAdvRulRepository extends JpaRepository<MwPrdAdvRul, Long> {

    public List<MwPrdAdvRul> findAllByCrntRecFlg(boolean flag);

    public MwPrdAdvRul findOneByPrdAdvRulSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwPrdAdvRul> findAllByPrdAdvRulSeq(long seq);

    public List<MwPrdAdvRul> findAllByPrdSeqAndCrntRecFlg(long prdSeq, boolean falg);

    public List<MwPrdAdvRul> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                           Instant ldate, boolean delFlg);

    @Query(value = "select distinct arul.* from mw_prd_adv_rul arul \r\n" + "join mw_prd prd on prd.prd_seq=arul.prd_seq \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(arul.last_upd_dt > :syncDate and (arul.crnt_rec_flg=1 or arul.del_flg=1))) order by arul.last_upd_dt", nativeQuery = true)
    public List<MwPrdAdvRul> findUpdatedMwPrdAdvRulForBrnch(@Param("brnchSeq") Long brnchSeq,
                                                            @Param("syncDate") Instant syncDate);
}
