package com.idev4.setup.repository;

import com.idev4.setup.domain.MwClntHlthInsrClm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface MwClntHlthInsrClmRepository extends JpaRepository<MwClntHlthInsrClm, Long> {

    public MwClntHlthInsrClm findOneByClntHlthClmSeqAndCrntRecFlg(Long seq, boolean flag);

    public List<MwClntHlthInsrClm> findAllByClntHlthClmSeq(Long seq);

    public List<MwClntHlthInsrClm> findAllByClntSeqAndCrntRecFlgOrderByEffStartDtDesc(long seq, boolean flag);

    public List<MwClntHlthInsrClm> findAllByCrntRecFlg(boolean flag);

    public MwClntHlthInsrClm findOneByTransactionIdAndCrntRecFlg(String id, boolean flag);

    @Query(value = "SELECT chic.* \r\n" + "                FROM mw_clnt_hlth_insr_clm  chic   \r\n"
        + "                WHERE     NVL (chic.clm_sts_key, 0) != 1  AND chic.crnt_rec_flg = 1  \r\n"
        + "                AND  not exists ( select EXP_REF from mw_exp e\r\n"
        + "                join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG = 1\r\n"
        + "                where e.crnt_rec_flg = 1 and e.DEL_FLG=0 and t.TYP_ID='0343' and e.exp_ref = to_char(chic.clnt_hlth_clm_seq)) and chic.clnt_hlth_clm_seq=:clmSeq", nativeQuery = true)
    public MwClntHlthInsrClm findIfClaimUnpaid(@Param("clmSeq") Long clmSeq);

}
