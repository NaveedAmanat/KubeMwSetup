package com.idev4.setup.repository;

import com.idev4.setup.domain.MwReg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwReg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwRegRepository extends JpaRepository<MwReg, Long> {

    public List<MwReg> findAllByCrntRecFlgOrderByRegSeqDesc(boolean flag);

    public MwReg findTopByOrderByRegSeqDesc();

    public MwReg findOneByRegSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwReg> findAllByRegSeq(long seq);

    public List<MwReg> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                     boolean delFlg);

    @Query(value = "select distinct mr.* from mw_brnch mb  \r\n" + "join mw_area ma on ma.area_seq=mb.area_seq\r\n"
        + "join mw_reg mr on mr.reg_seq=ma.reg_seq\r\n" + "where mb.brnch_seq=:brnchSeq \r\n"
        + "and ((mb.last_upd_dt > :syncDate and (mb.crnt_rec_flg=1 or mb.del_flg=1)) or\r\n"
        + "(ma.last_upd_dt > :syncDate and (ma.crnt_rec_flg=1 or ma.del_flg=1))) or\r\n"
        + "(mr.last_upd_dt > :syncDate and (mr.crnt_rec_flg=1 or mr.del_flg=1))\r\n" + "order by mr.last_upd_dt", nativeQuery = true)
    public List<MwReg> findUpdatedMwRegForBrnch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);
}
