package com.idev4.setup.repository;

import com.idev4.setup.domain.MwLoanApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the MwLoanApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MwLoanAppRepository extends JpaRepository<MwLoanApp, Long> {

    public List<MwLoanApp> findAllByClntSeq(Long clnt);

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlg(Long clnt, boolean flag);

    public List<MwLoanApp> findAllByCrntRecFlg(boolean flag);

    public MwLoanApp findOneByLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public MwLoanApp findOneByLoanIdAndCrntRecFlg(String seq, boolean flag);

    public MwLoanApp findOneByClntSeqAndCrntRecFlg(long clientSeq, boolean flag);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppSts(Long clnt, long status);

    public List<MwLoanApp> findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc(Long clnt, long status);

    // public List< MwLoanApp > findAllByClntSeqAndLoanAppStsOrderByLoanCyclNumDesc( Long clnt, long status );

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(Long clnt, boolean flag);

    public MwLoanApp findOneByClntSeqAndSyncFlgAndCrntRecFlg(long clientSeq, boolean sflag, boolean flag);

    public List<MwLoanApp> findAllBySyncFlgAndCrntRecFlg(boolean sflag, boolean flag);

    public List<MwLoanApp> findAllByPrntLoanAppSeqAndCrntRecFlg(long seq, boolean flag);

    public List<MwLoanApp> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlgAndPortSeqIn(Instant lastUpdDt, String lastUpdBy,
                                                                                                     boolean flag, List<Long> portKey);

    public List<MwLoanApp> findAllByLastUpdDtAfterAndLastUpdByIgnoreCaseNotAndCrntRecFlg(Instant lastUpdDt, String lastUpdBy,
                                                                                         boolean flag);

    @Query(value = "select app.*  from  mw_loan_app app" + " join mw_prd prd on prd.PRD_SEQ=app.PRD_SEQ and prd.CRNT_REC_FLG=1"
        + " join mw_ref_cd_val val on val.ref_cd_seq = prd.prd_typ_key and val.crnt_rec_flg=1 and val.ref_cd!='1165'"
        + " where app.crnt_rec_flg = 1"
        + " and app.loan_cycl_num = (select max(loan_cycl_num) from mw_loan_app ap where ap.clnt_seq=app.clnt_seq and ap.crnt_rec_flg=1)"
        + " and app.port_seq IN :ports", nativeQuery = true)
    public List<MwLoanApp> findAllLoansForPorts(@Param("ports") List<Long> ports);

    public List<MwLoanApp> findAllByClntSeqAndCrntRecFlgOrderByLoanAppStsDtDesc(Long clntSeq, boolean flag);
}
