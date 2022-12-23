package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwCntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCntryRepository extends JpaRepository<MwCntry, Long> {

    public List<MwCntry> findAllByCrntRecFlg(boolean flag);

    public MwCntry findOneByCntrySeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwCntry> findAllByCntrySeq(long seq);

    public MwCntry findTopByOrderByCntrySeqDesc();

    public List<MwCntry> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                       boolean delFlg);

    @Query(value = " select DISTINCT cntry.* from mw_cntry cntry "
        + " join mw_st st on st.cntry_seq=cntry.cntry_seq and st.crnt_rec_flg = 1  "
        + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (cntry.crnt_rec_flg=1 or cntry.del_flg=1) and locRel.port_seq =:portSeq ", nativeQuery = true)
    public List<MwCntry> findAllMwCntryForPort(@Param("portSeq") Long portSeq);

    @Query(value = " select DISTINCT cntry.* from mw_cntry cntry "
        + " join mw_st st on st.cntry_seq=cntry.cntry_seq and st.crnt_rec_flg = 1  "
        + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (cntry.crnt_rec_flg=1 or cntry.del_flg=1) and locRel.port_seq =:portSeq  and cntry.last_upd_dt > :syncDate", nativeQuery = true)
    public List<MwCntry> findUpdatedMwCntryForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select DISTINCT cntry.* from mw_cntry cntry "
        + " join mw_st st on st.cntry_seq=cntry.cntry_seq and st.crnt_rec_flg = 1  "
        + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (cntry.crnt_rec_flg=1 or cntry.del_flg=1) and locRel.brnch_seq =:brnchSeq  and cntry.last_upd_dt > :syncDate", nativeQuery = true)
    public List<MwCntry> findUpdatedMwCntryForBranch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select DISTINCT cntry.* from mw_cntry cntry "
        + " join mw_st st on st.cntry_seq=cntry.cntry_seq and st.crnt_rec_flg = 1  "
        + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (cntry.crnt_rec_flg=1 or cntry.del_flg=1) and locRel.brnch_seq =:brnchSeq ", nativeQuery = true)
    public List<MwCntry> findAllMwCntryForBranch(@Param("brnchSeq") Long brnchSeq);
}
