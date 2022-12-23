package com.idev4.setup.repository;

import com.idev4.setup.domain.MwDvcRgstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MwAddr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwDvcRgstryRepository extends JpaRepository<MwDvcRgstr, Long> {

    public MwDvcRgstr findOneByDvcRgstrSeqAndCrntRecFlg(long seq, boolean flag);

    public MwDvcRgstr findOneByDvcAddrAndCrntRecFlg(String seq, boolean flag);

    public MwDvcRgstr findOneByEntyTypFlgAndEntyTypSeqAndCrntRecFlg(Long typFlg, Long typSeq, boolean flag);

    public MwDvcRgstr findOneByPhNumAndCrntRecFlg(String phNum, boolean flag);

    // Added by Areeba - 29-06-2022
    @Query(value = " SELECT * FROM MW_DVC_RGSTR " +
        " WHERE ENTY_TYP_SEQ = :enty " +
        " ORDER BY LAST_UPD_DT desc " +
        " FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    public MwDvcRgstr findLastActiveByEntyTypSeq(@Param("enty") Long entyTypSeq);
    // Ended by Areeba
}
