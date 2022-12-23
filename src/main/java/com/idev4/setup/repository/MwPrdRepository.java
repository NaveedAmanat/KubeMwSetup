package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwPrd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdRepository extends JpaRepository<MwPrd, Long> {

    public MwPrd findOneByPrdSeqAndCrntRecFlg(Long prdSeq, boolean flag);

    public List<MwPrd> findAllByPrdSeq(Long prdSeq);

    public List<MwPrd> findAllByPrdTypKeyAndCrntRecFlg(Long prdTypKey, boolean flag);

    public List<MwPrd> findAllByPrdGrpSeqAndCrntRecFlg(Long prdGrpSeq, boolean flag);

    public List<MwPrd> findAllByPrdGrpSeqAndCrntRecFlgOrderByPrdSeq(Long prdGrpSeq, boolean flag);

    public List<MwPrd> findAllByCrntRecFlg(boolean flag);

    public List<MwPrd> findAllByCrntRecFlgOrderByPrdNm(boolean flag);

    public List<MwPrd> findAllByCrntRecFlgOrderByPrdSeq(boolean flag);

    public List<MwPrd> findAllByCrntRecFlgAndPrdStsKey(boolean flag, long sts);

    public List<MwPrd> findAllByPrdTypKeyAndPrdStsKeyAndCrntRecFlg(long typKey, long stsKey, boolean flag);

    public List<MwPrd> findAllByPrdTypKeyAndCrntRecFlg(long typKey, boolean flag);

    public List<MwPrd> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                     boolean delFlg);

    @Query(value = "select distinct  bprel.crnt_rec_flg, bprel.del_flg, prd.* " + "from mw_prd prd \r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "where bprel.brnch_seq=:brnchSeq \r\n"
        + "and ((prd.last_upd_dt > :syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1)) or (bprel.last_upd_dt >  :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1))) order by prd.last_upd_dt", nativeQuery = true)
    public List<MwPrd> findUpdatedMwPrdForBrnch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);

    // Added by Areeba - 27-05-2022
    @Query(value = " select prd.* from mw_prd prd \n" +
        "            join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq and bprel.crnt_rec_flg=1 \n" +
        "            where prd.crnt_rec_flg=1 \n" +
        "            --and prd.prd_sts_key=(select val.ref_cd_seq from mw_ref_cd_val val join mw_ref_cd_grp grp on grp.ref_cd_grp_seq=val.ref_cd_grp_key and grp.crnt_rec_flg=1 where val.crnt_rec_flg=1 and val.ref_cd='0200' and grp.ref_cd_grp='0016') \n" +
        "            and bprel.brnch_seq= :brnchSeq order by prd_nm ", nativeQuery = true)
    public List<MwPrd> findAllMwPrdForBranch(@Param("brnchSeq") Long branchSeq);
    // Ended by Areeba
}
