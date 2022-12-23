package com.idev4.setup.repository;

import com.idev4.setup.domain.MwAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the MwAddr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwAddrRepository extends JpaRepository<MwAddr, Long> {

    public List<MwAddr> findAllByCrntRecFlg(boolean flag);

    public MwAddr findOneByAddrSeqAndCrntRecFlg(long seq, boolean flag);

    @Query(value = "select * from mw_addr ma\n" +
        "join mw_addr_rel mar on ma.ADDR_SEQ = mar.ADDR_SEQ and mar.crnt_rec_flg = 1 and mar.enty_typ = 'Branch'\n" +
        "where ma.crnt_rec_flg = 1 and ma.city_seq = :cityuc", nativeQuery = true)
    public List<MwAddr> findAllBrnchAddrForCityUcRel(@Param("cityuc") Long cityuc);
}
