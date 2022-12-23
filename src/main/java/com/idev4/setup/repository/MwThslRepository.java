package com.idev4.setup.repository;

import com.idev4.setup.domain.MwThsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwThsl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwThslRepository extends JpaRepository<MwThsl, Long> {

    public List<MwThsl> findAllByDistSeqAndCrntRecFlg(long distSeq, boolean flag);

    public List<MwThsl> findAllByCrntRecFlg(boolean flag);

    public List<MwThsl> findAllByCrntRecFlgOrderByThslNm(boolean flag);

    public MwThsl findTopByOrderByThslSeqDesc();

    public MwThsl findOneByThslSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwThsl> findAllByThslSeq(long seq);

    public List<MwThsl> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                      boolean delFlg);

    @Query(value = " select DISTINCT th.* from mw_thsl th " + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1 "
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (th.crnt_rec_flg=1 or th.del_flg=1) and locRel.port_seq =:portSeq  ", nativeQuery = true)
    public List<MwThsl> findAllMwThslForPort(@Param("portSeq") Long portSeq);

    @Query(value = " select DISTINCT th.* from mw_thsl th        join mw_uc uc on uc.thsl_seq=th.thsl_seq\r\n"
        + "            join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + "            join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq   where locRel.port_seq = :portSeq  \r\n"
        + "            and (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "             ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "             ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate ) or\r\n"
        + "             ((th.crnt_rec_flg=1 or th.del_flg=1)  and th.last_upd_dt >:syncDate )  ) \r\n"
        + "             order by th.last_upd_dt", nativeQuery = true)
    public List<MwThsl> findUpdatedMwThslForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select DISTINCT th.* from mw_thsl th " + " join mw_uc uc on uc.thsl_seq=th.thsl_seq and uc.crnt_rec_flg=1"
        + " join mw_city_uc_rel rel on REL.uc_seq = uc.uc_seq and rel.crnt_rec_flg = 1 "
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where (th.crnt_rec_flg=1 or th.del_flg=1) and locRel.brnch_seq =:brnchSeq ", nativeQuery = true)
    public List<MwThsl> findAllMwThslForBranch(@Param("brnchSeq") Long brnchSeq);

    @Query(value = " select DISTINCT th.* from mw_thsl th\r\n" + "       join mw_uc uc on uc.thsl_seq=th.thsl_seq\r\n"
        + "join  mw_city_uc_rel rel on rel.uc_seq=uc.uc_seq\r\n"
        + "join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n" + "where locRel.brnch_seq = :brnchSeq \r\n"
        + "and (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + " ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + " ((uc.crnt_rec_flg=1 or uc.del_flg=1)  and uc.last_upd_dt >:syncDate ) or\r\n"
        + " ((th.crnt_rec_flg=1 or th.del_flg=1)  and th.last_upd_dt >:syncDate )  ) \r\n"
        + " order by th.last_upd_dt", nativeQuery = true)
    public List<MwThsl> findUpdatedMwThslForBranch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);
}
