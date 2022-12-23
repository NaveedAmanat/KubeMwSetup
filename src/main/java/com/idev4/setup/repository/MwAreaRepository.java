package com.idev4.setup.repository;

import com.idev4.setup.domain.MwArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAreaRepository extends JpaRepository<MwArea, Long> {

    public List<MwArea> findAllByRegSeqAndCrntRecFlgOrderByAreaSeqDesc(long regSeq, boolean flag);

    public List<MwArea> findAllByCrntRecFlg(boolean flag);

    public List<MwArea> findAllByCrntRecFlgOrderByAreaNm(boolean flag);

    public MwArea findTopByOrderByAreaSeqDesc();

    public MwArea findOneByAreaSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwArea> findAllByAreaSeq(long seq);

    public List<MwArea> findAllByRegSeqAndCrntRecFlg(Long id, boolean b);

    public List<MwArea> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg, Instant ldate,
                                                                                      boolean delFlg);

    @Query(value = "select distinct ma.* from mw_brnch mb  \r\n" + "join mw_area ma on ma.area_seq=mb.area_seq\r\n"
        + "where mb.brnch_seq=:brnchSeq \r\n" + "and ((mb.last_upd_dt > :syncDate  and (mb.crnt_rec_flg=1 or mb.del_flg=1)) or\r\n"
        + "(ma.last_upd_dt > :syncDate  and (ma.crnt_rec_flg=1 or ma.del_flg=1)))\r\n" + "order by ma.last_upd_dt", nativeQuery = true)
    public List<MwArea> findUpdatedMwAreaForBrnch(@Param("brnchSeq") Long brnchSeq, @Param("syncDate") Instant syncDate);
}
