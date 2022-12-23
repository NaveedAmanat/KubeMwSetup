package com.idev4.setup.repository;

import com.idev4.setup.domain.MwUc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwUc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwUcRepository extends JpaRepository<MwUc, Long> {

    public List<MwUc> findAllByThslSeqAndCrntRecFlg(long thslSeq, boolean flag);

    public List<MwUc> findAllByCrntRecFlg(boolean flag);

    public List<MwUc> findAllByCrntRecFlgOrderByUcNm(boolean flag);

    public MwUc findOneByUcSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwUc> findAllByUcSeq(long seq);

    public MwUc findTopByOrderByUcSeqDesc();

    public List<MwUc> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                    boolean delFlg);

    @Query(value = " select distinct uc.* from mw_uc uc " + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (uc.crnt_rec_flg=1) and locRel.brnch_seq =:brnchSeq order by thsl_seq, uc_nm, uc_commnets ", nativeQuery = true)
    public List<MwUc> findAllMwUcForBranch(@Param("brnchSeq") Long branchSeq);

    @Query(value = "select distinct uc.* from mw_uc uc\r\n" + "        join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + "             join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + "             where locRel.brnch_seq = :brnchSeq and\r\n"
        + "              (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "              ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "              ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate )) order by uc.last_upd_dt", nativeQuery = true)
    public List<MwUc> findUpdatedMwUcForBranch(@Param("brnchSeq") Long branchSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select uc.* from mw_uc uc " + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (uc.crnt_rec_flg=1 or uc.del_flg=1) and locRel.port_seq =:portSeq ", nativeQuery = true)
    public List<MwUc> findAllMwUcForPort(@Param("portSeq") Long portSeq);

    @Query(value = " select distinct uc.* from mw_uc uc         join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + " join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + " where locRel.port_seq = :portSeq and\r\n"
        + "  (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "  ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "  ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate )) order by uc.last_upd_dt", nativeQuery = true)
    public List<MwUc> findUpdatedMwUcForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);
}
