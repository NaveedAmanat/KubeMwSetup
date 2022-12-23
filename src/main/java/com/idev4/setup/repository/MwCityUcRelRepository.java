package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCityUcRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwCityUcRelRepository extends JpaRepository<MwCityUcRel, Long> {

    public MwCityUcRel findOneByCityUcRelSeqAndCrntRecFlg(Long cityUcRelSeq, boolean flag);

    public List<MwCityUcRel> findAllByCitySeqAndCrntRecFlg(Long ucSeq, boolean flag);

    public List<MwCityUcRel> findAllByUcSeqAndCrntRecFlg(Long ucSeq, boolean flag);

    public List<MwCityUcRel> findAllByCrntRecFlg(boolean flag);

    // Added by Areeba - 27-05-2022
    public MwCityUcRel findOneByCitySeqAndUcSeqAndCrntRecFlg(Long citySeq, Long ucSeq, boolean flg);

    public List<MwCityUcRel> findAllByCitySeqAndUcSeqAndCrntRecFlg(Long citySeq, Long ucSeq, boolean flg);

    public List<MwCityUcRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                           Instant ldate, boolean delFlg);

    @Query(value = "select rel.* from mw_city_uc_rel rel "
        + "join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq and locrel.crnt_rec_flg = 1 "
        + "where REL.crnt_rec_flg = 1 " + "and locRel.brnch_seq = :brnchSeq", nativeQuery = true)
    public List<MwCityUcRel> findAllMwCityUcRelForBranch(@Param("brnchSeq") Long branchSeq);

    @Query(value = " select distinct locrel.crnt_rec_flg, locrel.del_flg, rel.* from mw_city_uc_rel rel \r\n"
        + "join mw_brnch_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n"
        + "where locRel.brnch_seq = :brnchSeq and\r\n"
        + "(((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt > :syncDate) or\r\n"
        + "((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt > :syncDate)) order by rel.last_upd_dt", nativeQuery = true)
    public List<MwCityUcRel> findUpdatedMwCityUcRelForBranch(@Param("brnchSeq") Long branchSeq,
                                                             @Param("syncDate") Instant syncDate);

    @Query(value = "select rel.* from mw_city_uc_rel rel "
        + "join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq and locrel.crnt_rec_flg = 1 "
        + "where REL.crnt_rec_flg = 1 " + "and locrel.port_seq = :portSeq", nativeQuery = true)
    public List<MwCityUcRel> findAllMwCityUcRelForPort(@Param("portSeq") Long portSeq);

    @Query(value = " select distinct locrel.crnt_rec_flg, locrel.del_flg, rel.* from mw_city_uc_rel rel \r\n"
        + "join mw_port_location_rel locRel on locrel.city_seq = REL.city_uc_rel_seq  \r\n" + "where locRel.port_seq = :portSeq and\r\n"
        + "(((locRel.crnt_rec_flg=1 or locRel.del_flg=1)  and locRel.last_upd_dt > :syncDate) or\r\n"
        + "((rel.crnt_rec_flg=1 or rel.del_flg=1)  and rel.last_upd_dt > :syncDate)) order by rel.last_upd_dt", nativeQuery = true)
    public List<MwCityUcRel> findUpdatedMwCityUcRelForPort(@Param("portSeq") Long portSeq, @Param("syncDate") Instant syncDate);

}
