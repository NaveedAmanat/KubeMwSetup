package com.idev4.setup.repository;

import com.idev4.setup.domain.MwCmnty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwCmnty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwCmntyRepository extends JpaRepository<MwCmnty, Long> {

    public List<MwCmnty> findAllByPortSeqAndCrntRecFlg(long portSeq, boolean flag);

    public List<MwCmnty> findAllByCrntRecFlg(boolean flag);

    public MwCmnty findTopByOrderByCmntySeqDesc();

    public MwCmnty findOneByCmntySeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwCmnty> findAllByCmntySeq(long id);

    public List<MwCmnty> findAllByLastUpdDtAfterAndCrntRecFlgAndPortSeqInOrLastUpdDtAfterAndDelFlgAndPortSeqIn(Instant date,
                                                                                                               boolean crntRecFlg, List<Long> port, Instant ldate, boolean delFlg, List<Long> ports);

    // Added by Areeba - 27-05-2022
    @Query(value = " select cmnty.* from mw_cmnty cmnty   join mw_port port on port.port_seq = cmnty.port_seq and port.crnt_rec_flg = 1 " +
        "         where cmnty.crnt_rec_flg=1 and port.brnch_seq = :brnchSeq order by cmnty.port_seq, cmnty.cmnty_seq, cmnty.cmnty_nm ", nativeQuery = true)
    public List<MwCmnty> findAllMwCmntyForBranch(@Param("brnchSeq") Long branchSeq);
    // Ended by Areeba
}
