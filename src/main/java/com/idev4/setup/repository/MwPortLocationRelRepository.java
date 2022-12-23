package com.idev4.setup.repository;

import com.idev4.setup.domain.MwPortLocationRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data repository for the MwPortLocationRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwPortLocationRelRepository extends JpaRepository<MwPortLocationRel, Long> {

    public List<MwPortLocationRel> findAllByPortSeqAndCrntRecFlg(long portSeq, boolean flag);

    public MwPortLocationRel findOneByPortSeqAndCitySeqAndCrntRecFlg(long portSeq, long citySeq, boolean flag);

    public List<MwPortLocationRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(Instant date, boolean crntRecFlg,
                                                                                                 Instant ldate, boolean delFlg);

    public List<MwPortLocationRel> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndPortSeq(Instant date,
                                                                                                           boolean crntRecFlg, Instant ldate, boolean delFlg, long portSeq);

    public List<MwPortLocationRel> findAllByCitySeqAndCrntRecFlg(long seq, boolean recFlag);

    @Query(value = "select plrel.* from MW_port_Location_rel plrel\r\n"
        + "       join mw_port p on p.port_seq=plrel.port_seq and p.crnt_rec_flg=1\r\n"
        + "       where plrel.crnt_rec_flg=1 and p.brnch_seq=:brnchSeq and plrel.city_seq=:cityUcRelSeq", nativeQuery = true)
    public List<MwPortLocationRel> findAllRelForBrnchAndCityUcRel(@Param("brnchSeq") Long portSeq,
                                                                  @Param("cityUcRelSeq") Long cityUcRelSeq);
}
