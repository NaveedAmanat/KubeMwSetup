package com.idev4.setup.repository;

import com.idev4.setup.domain.MwSt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwSt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwStRepository extends JpaRepository<MwSt, Long> {

    public List<MwSt> findAllByCntrySeqAndCrntRecFlg(long countrySeq, boolean flag);

    public MwSt findOneByStSeqAndCrntRecFlg(long stSeq, boolean flag);

    public List<MwSt> findAllByStSeq(long stSeq);

    public List<MwSt> findAllByCrntRecFlg(boolean flag);

    public MwSt findTopByOrderByStSeqDesc();

    public List<MwSt> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                    boolean delFlg);

    @Query(value = " select DISTINCT st.* from mw_st st " + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1"
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (st.crnt_rec_flg=1 or st.del_flg=1) and locRel.port_seq =:portSeq ", nativeQuery = true)
    public List<MwSt> findAllMwStForPort(@Param("portSeq") Long portSeq);

    @Query(value = "select DISTINCT st.* from mw_st st        join mw_dist dst on dst.st_seq = st.st_seq\r\n"
        + "                   join mw_thsl th on th.dist_seq = dst.dist_seq         join mw_uc uc on uc.thsl_seq=th.thsl_seq\r\n"
        + "                    join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + " join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n" + " where locRel.port_seq = :portSeq \r\n"
        + " and (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "  ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "  ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate ) or\r\n"
        + "  ((th.crnt_rec_flg=1 or th.del_flg=1)  and th.last_upd_dt >:syncDate )  or\r\n"
        + "  ((dst.crnt_rec_flg=1 or dst.del_flg=1)  and dst.last_upd_dt >:syncDate ) or \r\n"
        + "  ((st.crnt_rec_flg=1 or st.del_flg=1)  and st.last_upd_dt >:syncDate ) ) \r\n"
        + "  order by st.last_upd_dt", nativeQuery = true)
    public List<MwSt> findUpdatedMwStForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select DISTINCT st.* from mw_st st " + " join mw_dist dst on dst.st_seq = st.st_seq and dst.crnt_rec_flg=1 "
        + " join mw_thsl th on th.dist_seq = dst.dist_seq and th.crnt_rec_flg=1 "
        + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1"
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (st.crnt_rec_flg=1 or st.del_flg=1) and locRel.brnch_seq =:brnchSeq ", nativeQuery = true)
    public List<MwSt> findAllMwStForBranch(@Param("brnchSeq") Long brnchSeq);

    @Query(value = "select DISTINCT st.* from mw_st st\r\n" + "       join mw_dist dst on dst.st_seq = st.st_seq\r\n"
        + "       join mw_thsl th on th.dist_seq = dst.dist_seq \r\n" + "       join mw_uc uc on uc.thsl_seq=th.thsl_seq\r\n"
        + "        join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + "             join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + "             where locRel.brnch_seq = :brnchSeq \r\n"
        + "             and (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "              ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "              ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate ) or\r\n"
        + "              ((th.crnt_rec_flg=1 or th.del_flg=1)  and th.last_upd_dt >:syncDate )  or\r\n"
        + "              ((dst.crnt_rec_flg=1 or dst.del_flg=1)  and dst.last_upd_dt >:syncDate ) or \r\n"
        + "              ((st.crnt_rec_flg=1 or st.del_flg=1)  and st.last_upd_dt >:syncDate ) ) \r\n"
        + "              order by st.last_upd_dt", nativeQuery = true)
    public List<MwSt> findUpdatedMwStForBranch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);
}
