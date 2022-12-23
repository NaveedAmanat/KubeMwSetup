package com.idev4.setup.repository;

import com.idev4.setup.domain.MwTyps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwTyps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwTypsRepository extends JpaRepository<MwTyps, Long> {

    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKey(boolean flag, long typCtgryKey);

    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeq(boolean flag, long typCtgryKey, long brnchSeq);

    public MwTyps findOneByTypSeqAndCrntRecFlg(Long typSeq, boolean flag);

    public MwTyps findOneByTypIdAndTypCtgryKeyAndCrntRecFlgAndBrnchSeq(String typId, Long typCtgryKey, boolean flag, Long brnchSeq);

    public MwTyps findOneByTypIdAndCrntRecFlgAndBrnchSeqAndDelFlgAndTypCtgryKey(String typId, boolean flag, Long brnchSeq, boolean delflg,
                                                                                Long typCat);

    public MwTyps findOneByTypIdAndCrntRecFlgAndBrnchSeqAndTypCtgryKey(String typId, boolean flag, Long brnchSeq, Long typCat);

    public List<MwTyps> findAllByTypSeq(Long typSeq);

    public List<MwTyps> findAllByCrntRecFlgAndTypCtgryKeyAndBrnchSeqIn(boolean flag, long typCtgryKey, List<Long> brnchs);

    public List<MwTyps> findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndTypCtgryKey(Instant date, boolean crntRecFlg,
                                                                                                    Instant ldate, boolean delFlg, Long typCtgryKey);

    // Added By Naveed - Date - 23-02-2022
    // SCR - Upaisa and HBL Konnect Mobile Wallet Payment Mode
    @Query(value = "select typ.TYP_STR, typ.TYP_ID\n" +
        "from mw_typs typ\n" +
        "where UPPER(typ.TYP_STR) like '%MOBILE WALLET%' AND typ.BRNCH_SEQ != -1 \n" +
        "GROUP BY typ.TYP_STR, typ.TYP_ID", nativeQuery = true)
    public List<Object[]> findAllByMobileWalletModes();
    // Ended By Naveed
}
