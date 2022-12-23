package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwCity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCityRepository extends JpaRepository<MwCity, Long> {

    // public List<MwCity> findAllByUcSeqAndCrntRecFlg(long ucSeq,boolean flag);

    public List<MwCity> findAllByCrntRecFlg(boolean flag);

    public List<MwCity> findAllByCrntRecFlgOrderByCityNm(boolean flag);

    public MwCity findOneByCitySeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwCity> findAllByCitySeq(long seq);

    public List<MwCity> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                      boolean delFlg);

    @Query(value = " select DISTINCT  city.* from mw_city city  "
        + " join mw_city_uc_rel rel on REL.city_seq = city.city_seq and rel.crnt_rec_flg = 1 "
        + " join mw_brnch_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1 "
        + " where city.crnt_rec_flg=1 and locRel.brnch_seq =:brnchSeq", nativeQuery = true)
    public List<MwCity> findAllMwCityForBranch(@Param("brnchSeq") Long branchSeq);

    @Query(value = " select distinct city.* from mw_city city \r\n" + "        join  mw_city_uc_rel rel on rel.city_seq=city.city_seq\r\n"
        + "             join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + "             where locRel.brnch_seq = :brnchSeq and\r\n"
        + "              (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "              ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "              ((city.crnt_rec_flg=1 or city.del_flg=1)  and city.last_upd_dt >:syncDate )) order by city.last_upd_dt", nativeQuery = true)
    public List<MwCity> findUpdatedMwCityForBranch(@Param("brnchSeq") Long branchSeq, @Param("syncDate") Instant syncDate);

    @Query(value = " select DISTINCT  city.* from mw_city city  "
        + " join mw_city_uc_rel rel on REL.city_seq = city.city_seq and rel.crnt_rec_flg = 1 "
        + " join mw_port_location_rel locRel on locRel.city_seq = REL.city_uc_rel_seq and locRel.crnt_rec_flg=1"
        + " where city.crnt_rec_flg=1 and locRel.port_seq =:portSeq", nativeQuery = true)
    public List<MwCity> findAllMwCityForPort(@Param("portSeq") Long portSeq);

    @Query(value = " select distinct city.* from mw_city city \r\n" + "        join  mw_city_uc_rel rel on rel.city_seq=city.city_seq\r\n"
        + "             join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + "             where locRel.port_seq = :portSeq and\r\n"
        + "              (((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt >:syncDate ) or\r\n"
        + "              ((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt >:syncDate )  or\r\n"
        + "              ((city.crnt_rec_flg=1 or city.del_flg=1)  and city.last_upd_dt >:syncDate )) order by city.last_upd_dt", nativeQuery = true)
    public List<MwCity> findUpdatedMwCityForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);
}
