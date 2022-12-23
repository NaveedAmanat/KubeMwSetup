package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdFormRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwPrdFormRelRepository extends JpaRepository<MwPrdFormRel, Long> {

    public MwPrdFormRel findOneByPrdFormRelSeqAndCrntRecFlg(Long prdFormRelSeq, boolean flag);

    public List<MwPrdFormRel> findAllByPrdFormRelSeq(Long prdFormRelSeq);

    public List<MwPrdFormRel> findAllByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrdFormRel> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdFormRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                            Instant ldate, boolean delFlg);

    @Query(value = "select distinct  pfrel.* from mw_prd_form_rel pfrel  \r\n" + "join mw_prd prd on prd.prd_seq=pfrel.prd_seq \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate  and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate  and (bprel.crnt_rec_flg=1 or bprel.del_flg=1)) or\r\n"
        + "(pfrel.last_upd_dt > :syncDate  and (pfrel.crnt_rec_flg=1 or pfrel.del_flg=1)))\r\n"
        + "order by pfrel.last_upd_dt", nativeQuery = true)
    public List<MwPrdFormRel> findUpdatedMwPrdFormRelForBrnch(@Param("brnchSeq") Long brnchSeq,
                                                              @Param("syncDate") Instant syncDate);
}
