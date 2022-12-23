package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPrdGrp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwPrdGrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPrdGrpRepository extends JpaRepository<MwPrdGrp, Long> {

    public MwPrdGrp findOneByPrdGrpSeqAndCrntRecFlg(Long prdGrpSeq, boolean flag);

    public List<MwPrdGrp> findAllByPrdGrpSeq(Long prdGrpSeq);

    public List<MwPrdGrp> findAllByCrntRecFlg(boolean flag);

    public List<MwPrdGrp> findAllByCrntRecFlgOrderByPrdGrpSeq(boolean flag);

    @Query(value = "select distinct pgrp.* from mw_prd prd  join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq \r\n"
        + "            join mw_prd_grp pgrp on pgrp.prd_grp_seq=prd.prd_grp_seq\r\n"
        + "            join mw_ref_cd_val val on val.ref_cd_seq=prd.prd_typ_key and val.ref_cd!='1165' and val.crnt_rec_flg=1\r\n"
        + "            where bprel.brnch_seq=:brnchSeq \r\n"
        + "            and ((prd.last_upd_dt >:syncDate and (prd.crnt_rec_flg=1 or prd.del_flg=1))  or (bprel.last_upd_dt > :syncDate and (bprel.crnt_rec_flg=1 or bprel.del_flg=1))\r\n"
        + "             or (pgrp.last_upd_dt > :syncDate and (pgrp.crnt_rec_flg=1 or pgrp.del_flg=1)) ) order by pgrp.last_upd_dt", nativeQuery = true)
    public List<MwPrdGrp> findUpdatedMwPrdGrpForBranch(@Param("brnchSeq") Long branchSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select unique grp.* from mw_prd_grp grp "
        + "join mw_prd prd on prd.prd_grp_seq=grp.prd_grp_seq and prd.crnt_rec_flg=1 "
        + "join mw_brnch_prd_rel bpr on bpr.prd_seq=prd.prd_seq and bpr.crnt_rec_flg=1 "
        + "join mw_port port on port.brnch_seq=bpr.brnch_seq and port.crnt_rec_flg=1 " + " where port.port_seq in :ports"
        + "  and grp.last_upd_dt > :syncDate", nativeQuery = true)
    public List<MwPrdGrp> findUpdatedMwPrdGrpForPort(@Param("ports") List<Long> ports, @Param("syncDate") Instant syncDate);

    @Query(value = "select Distinct * from (select grp.* from mw_prd_grp grp\r\n"
        + "join mw_prd prd on prd.prd_grp_seq=grp.prd_grp_seq and prd.crnt_rec_flg=1\r\n"
        + "join mw_brnch_prd_rel bprel on bprel.prd_seq=prd.prd_seq and bprel.crnt_rec_flg=1 \r\n"
        + "where grp.crnt_rec_flg=1 and grp.prd_grp_sts=(select val.ref_cd_seq from mw_ref_cd_val val join mw_ref_cd_grp grp on grp.ref_cd_grp_seq=val.ref_cd_grp_key and grp.crnt_rec_flg=1 where val.crnt_rec_flg=1 and val.ref_cd='0200' and grp.ref_cd_grp='0016') \r\n"
        + "and prd.prd_sts_key=(select val.ref_cd_seq from mw_ref_cd_val val join mw_ref_cd_grp grp on grp.ref_cd_grp_seq=val.ref_cd_grp_key and grp.crnt_rec_flg=1 where val.crnt_rec_flg=1 and val.ref_cd='0200' and grp.ref_cd_grp='0016') \r\n"
        + "and bprel.brnch_seq= :brnchSeq order by prd_grp_nm)", nativeQuery = true)
    public List<MwPrdGrp> findActiveMwPrdGrpForBranch(@Param("brnchSeq") Long branchSeq);

}
