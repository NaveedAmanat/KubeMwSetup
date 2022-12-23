package com.idev4.setup.repository;

import com.idev4.setup.domain.MwRefCdVal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwRefCdVal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwRefCdValRepository extends JpaRepository<MwRefCdVal, Long> {

    public MwRefCdVal findOneByRefCdSeqAndRefCdGrpKeyAndCrntRecFlg(long seq, long refCdGrpKey, boolean flag);

    public MwRefCdVal findOneByRefCdSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwRefCdVal> findAllByRefCdGrpKeyAndCrntRecFlg(Long groupKey, boolean flag);

    public List<MwRefCdVal> findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(Long groupKey, boolean status,
                                                                                             boolean flag);

    public List<MwRefCdVal> findAllByCrntRecFlgOrderByRefCdSeq(boolean flag);

    public List<MwRefCdVal> findAllByCrntRecFlgOrderByRefCdSeqDesc(boolean flag);

    public List<MwRefCdVal> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                          Instant ldate, boolean delFlg);

    @Query(value = "select val.ref_cd_seq, val.eff_start_dt, val.ref_cd_grp_key, val.ref_cd, val.ref_cd_dscr, val.ref_cd_sort_ordr, val.ref_cd_active_flg, val.crtd_by, val.crtd_dt, val.last_upd_by, val.last_upd_dt, val.del_flg, val.eff_end_dt, val.crnt_rec_flg\n"
        + "from mw_ref_cd_val val\n"
        + "join mw_ref_cd_grp grp on grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.CRNT_REC_FLG=1 and grp.ref_cd_grp = :refCdGrp\n"
        + "where val.CRNT_REC_FLG=1 and val.ref_cd =:refCd", nativeQuery = true)
    public MwRefCdVal findRefCdByGrpAndVal(@Param("refCdGrp") String refCdGrp, @Param("refCd") String refCd);

    // Added by Zohaib Asim - Dated 16-12-2020
    // Get Health Claim Types
    public List<MwRefCdVal> findMwRefCdValByRefCdGrpKeyAndCrntRecFlg(Long refCdGrpKey, Boolean crntRecFlg);
    // End by Zohaib Asim
}
