package com.idev4.setup.service;

import com.idev4.setup.domain.MwClntHlthInsrClm;
import com.idev4.setup.domain.MwExp;
import com.idev4.setup.dto.ClientHealthInsuranceClaimDto;
import com.idev4.setup.dto.ClntHlthInsrClmsDto;
import com.idev4.setup.feignclients.ServiceClient;
import com.idev4.setup.repository.MwClntHlthInsrClmRepository;
import com.idev4.setup.repository.MwExpRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwClntHlthInsrClm.
 */
@Service
@Transactional
public class MwClntHlthInsrClmService {

    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrClmService.class);

    private final MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository;

    private final EntityManager em;
    @Autowired
    ServiceClient serviceClient;
    private MwExpRepository mwExpRepository;

    public MwClntHlthInsrClmService(MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository, EntityManager em,
                                    MwExpRepository mwExpRepository) {
        this.mwClntHlthInsrClmRepository = mwClntHlthInsrClmRepository;
        this.mwExpRepository = mwExpRepository;
        this.em = em;
    }

    /**
     * Save a mwClntHlthInsrClm.
     *
     * @param mwClntHlthInsrClm the entity to save
     * @return the persisted entity
     */
    public MwClntHlthInsrClm save(MwClntHlthInsrClm mwClntHlthInsrClm) {
        log.debug("Request to save MwClntHlthInsrClm : {}", mwClntHlthInsrClm);
        return mwClntHlthInsrClmRepository.save(mwClntHlthInsrClm);
    }

    /**
     * Get all the mwClntHlthInsrClms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwClntHlthInsrClm> findAll(Pageable pageable) {
        log.debug("Request to get all MwClntHlthInsrClm");
        return mwClntHlthInsrClmRepository.findAll(pageable);
    }

    /**
     * Get one mwClntHlthInsrClm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwClntHlthInsrClm findOne(Long clntHlthInsrClmSeq) {
        log.debug("Request to get MwClntHlthInsrClm : {}", clntHlthInsrClmSeq);
        return mwClntHlthInsrClmRepository.findOneByClntHlthClmSeqAndCrntRecFlg(clntHlthInsrClmSeq, true);
    }

    @Transactional(readOnly = true)
    public String findClientName(Long clntSeq) {
        Query query = em.createNativeQuery("Select concat (FRST_NM,LAST_NM) from mw_clnt where crnt_rec_flg=1 and clnt_Seq=" + clntSeq);
        List<Object> o = query.getResultList();
        if (o == null)
            return null;
        String name = (o.get(0)).toString();

        return name;
    }

    @Transactional(readOnly = true)
    public List<String> findAllClientName() {
        Query query = em.createNativeQuery("Select concat (FRST_NM,LAST_NM) from mw_clnt where crnt_rec_flg=1");
        List<String> names = query.getResultList();

        return names;
    }

    @Transactional(readOnly = true)
    public List<MwClntHlthInsrClm> findAllHistory(Long clntHlthInsrClmSeq) {
        log.debug("Request to get MwClntHlthInsrClm : {}", clntHlthInsrClmSeq);
        return mwClntHlthInsrClmRepository.findAllByClntHlthClmSeq(clntHlthInsrClmSeq);
    }

    /**
     * Delete the mwClntHlthInsrClm by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long clntHlthInsrClmSeq, String token) {
        log.debug("Request to delete MwClntHlthInsrClm : {}", clntHlthInsrClmSeq);
        MwClntHlthInsrClm clntHlthInsrClm = mwClntHlthInsrClmRepository.findOneByClntHlthClmSeqAndCrntRecFlg(clntHlthInsrClmSeq, true);
        clntHlthInsrClm.setCrntRecFlg(false);
        clntHlthInsrClm.setDelFlg(true);
        clntHlthInsrClm.setLastUpdDt(Instant.now());
        clntHlthInsrClm.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        clntHlthInsrClm.setEffEndDt(Instant.now());
        MwExp exp = mwExpRepository.findInsExpForClient(clntHlthInsrClm.getClntSeq());
        serviceClient.reversExpence(exp.getExpSeq(), "deleted by " + SecurityContextHolder.getContext().getAuthentication().getName(),
            token);
        exp.setCrntRecFlg(false);
        exp.setDelFlg(true);
        exp.setLastUpdDt(Instant.now());
        exp.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
        exp.setEffEndDt(Instant.now());
        mwExpRepository.save(exp);
        mwClntHlthInsrClmRepository.save(clntHlthInsrClm);
    }

    // public List< ClntHlthInsrClmsDto > findAllByCurrentRecord( String user ) {
    //
    // String query = "SELECT chic.clnt_hlth_clm_seq,b.brnch_seq,b.brnch_nm,c.clnt_seq, \r\n"
    // + " c.clnt_id,c.frst_nm||' '||c.last_nm ,clm_amt, transaction_id \r\n"
    // + " FROM mw_clnt_hlth_insr_clm chic \r\n"
    // + " JOIN mw_clnt c ON c.clnt_seq = chic.clnt_seq AND c.crnt_rec_flg = 1 \r\n"
    // + " join mw_acl acl on acl.port_seq=c.port_key \r\n"
    // + " Join mw_port po on po.port_seq=acl.port_seq \r\n"
    // + " JOIN mw_brnch b ON b.brnch_seq = po.brnch_seq AND b.crnt_rec_flg = 1 \r\n"
    // + " WHERE NVL (chic.clm_sts_key, 0) != 1 AND chic.crnt_rec_flg = 1 \r\n"
    // + " AND acl.user_id =:userId\r\n" + " and not exists ( select EXP_REF from mw_exp e\r\n"
    // + " join mw_typs t on t.TYP_SEQ=e.EXPNS_TYP_SEQ and t.CRNT_REC_FLG = 1\r\n"
    // + " where e.crnt_rec_flg = 1 and e.DEL_FLG=0 and t.TYP_ID='0343' and e.exp_ref=chic.clnt_hlth_clm_seq)";
    // Query qt = em.createNativeQuery( query ).setParameter( "userId", user );
    // List< Object[] > results = qt.getResultList();
    // List< ClntHlthInsrClmsDto > list = new ArrayList();
    // results.forEach( r -> {
    // ClntHlthInsrClmsDto dto = new ClntHlthInsrClmsDto();
    // dto.clntHlthClmSeq = r[ 0 ].toString();
    // dto.brnchSeq = r[ 1 ].toString();
    // dto.brnchNm = r[ 2 ].toString();
    // dto.clntSeq = r[ 3 ].toString();
    // dto.clntId = r[ 4 ].toString();
    // dto.clntName = r[ 5 ] == null ? "" : r[ 5 ].toString();
    // dto.clmAmt = r[ 6 ].toString();
    // dto.transactionId = r[ 7 ] == null ? "" : r[ 7 ].toString();
    // list.add( dto );
    // } );
    //
    // return list;
    // }

    public boolean isKSZBPaid(long claimSeq) {
        MwClntHlthInsrClm clntHlthInsrClm = mwClntHlthInsrClmRepository.findIfClaimUnpaid(claimSeq);
        return clntHlthInsrClm == null ? true : false;
    }

    public Map<String, Object> findAllByCurrentRecord(String userId, Long brnchSeq, Long hlthClmTyp, Integer pageIndex, Integer pageSize, String filter,
                                                      Boolean isCount) {

        // Modified by Zohaib Asim - Dated 31-12-2020
        // Health Claim Type added as parameter

        /**
         * @Modified, Naveed
         * @Date, 14-06-2022
         * @Description, SCR - Shifted Clients
         */

        String ClntHlthInsrClmsScript = "/* FileName: ClientHealthInsuranceClaimScreen\n" +
            "        * UPDATED BY: AISHA KHAN\n" +
            "        * Date: 30-Nov-2022\n" +
            "        * Reason: OPTIMIZATION*/\n" +
            "SELECT chic.clnt_hlth_clm_seq,\n" +
            "       la.brnch_seq,\n" +
            "       b.brnch_nm,\n" +
            "       c.clnt_seq,\n" +
            "       c.clnt_id,\n" +
            "       c.frst_nm || ' ' || c.last_nm,\n" +
            "       clm_amt,\n" +
            "       transaction_id\n" +
            "  FROM mw_clnt_hlth_insr_clm  chic\n" +
            "       JOIN mw_clnt c ON c.clnt_seq = chic.clnt_seq AND c.crnt_rec_flg = 1\n" +
            "       JOIN mw_loan_app la\n" +
            "           ON     la.CLNT_SEQ = chic.CLNT_SEQ\n" +
            "              AND la.CRNT_REC_FLG = 1\n" +
            "              AND la.LOAN_CYCL_NUM =\n" +
            "                  (SELECT MAX (ap1.LOAN_CYCL_NUM)\n" +
            "                     FROM mw_loan_app ap1\n" +
            "                    WHERE     ap1.CLNT_SEQ = la.clnt_seq\n" +
            "                          AND ap1.CRNT_REC_FLG = 1\n" +
            "                          AND ap1.brnch_seq = :brnch_seq)\n" +
            "       JOIN mw_brnch b\n" +
            "           ON     b.brnch_seq = la.brnch_seq\n" +
            "              AND b.crnt_rec_flg = 1\n" +
            "              AND chic.brnch_seq = :brnch_seq\n" +
            "WHERE     NVL (chic.clm_sts_key, 0) != 1\n" +
            "       AND chic.crnt_rec_flg = 1\n" +
            "       AND LA.PRD_SEQ <> 29\n" +
            "       AND chic.HLTH_CLM_TYP = :hlthClmTyp\n" +
            "       AND NOT EXISTS\n" +
            "               (SELECT EXP_REF\n" +
            "                  FROM mw_exp e\n" +
            "                 WHERE     e.EXPNS_TYP_SEQ IN (343, 16226, 16227)\n" +
            "                       AND e.crnt_rec_flg = 1\n" +
            "                       AND e.DEL_FLG = 0\n" +
            "                       AND TO_NUMBER (e.exp_ref) = chic.clnt_hlth_clm_seq)\n" +
            "       AND TRUNC (CHIC.CRTD_DT) <= ('06-JUL-2022')  %% \n" +
            "UNION ALL\n" +
            "SELECT CHIC.CLNT_HLTH_CLM_SEQ,\n" +
            "       la.BRNCH_SEQ,\n" +
            "       b.brnch_nm,\n" +
            "       C.CLNT_SEQ,\n" +
            "       C.CLNT_ID,\n" +
            "       C.FRST_NM || ' ' || C.LAST_NM,\n" +
            "       CLM_AMT,\n" +
            "       TRANSACTION_ID\n" +
            "  FROM MW_CLNT_HLTH_INSR_CLM  CHIC\n" +
            "       JOIN MW_CLNT_HLTH_INSR_CARD CRD\n" +
            "           ON CRD.CARD_NUM = CHIC.CARD_NUM AND CRD.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_LOAN_APP LA\n" +
            "           ON LA.LOAN_APP_SEQ = CRD.LOAN_APP_SEQ AND LA.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_CLNT C ON C.CLNT_SEQ = CHIC.CLNT_SEQ AND C.CRNT_REC_FLG = 1\n" +
            "       JOIN MW_BRNCH B\n" +
            "           ON     B.BRNCH_SEQ = CHIC.BRNCH_SEQ\n" +
            "              AND B.CRNT_REC_FLG = 1\n" +
            "              AND CHIC.BRNCH_SEQ = :brnch_seq\n" +
            "WHERE     NVL (CHIC.CLM_STS_KEY, 0) != 1\n" +
            "       AND CHIC.CRNT_REC_FLG = 1\n" +
            "       AND CHIC.HLTH_CLM_TYP = :hlthClmTyp\n" +
            "       AND LA.PRD_SEQ <> 29\n" +
            "       AND NOT EXISTS\n" +
            "               (SELECT EXP_REF\n" +
            "                  FROM mw_exp e\n" +
            "                 WHERE     e.EXPNS_TYP_SEQ IN (343, 16226, 16227)\n" +
            "                       AND e.crnt_rec_flg = 1\n" +
            "                       AND e.DEL_FLG = 0\n" +
            "                       AND TO_NUMBER (e.exp_ref) = chic.clnt_hlth_clm_seq)\n" +
            "       AND TRUNC (CHIC.CRTD_DT) > ('06-JUL-2022')  %%";

        // Modified by Zohaib Asim - Dated 31-12-2020
        // Health Claim Type added as parameter
        String ClntHlthInsrClmsCountScript = "SELECT COUNT (clnt_hlth_clm_seq)\n" +
            "  FROM (SELECT chic.clnt_hlth_clm_seq,\n" +
            "               la.brnch_seq,\n" +
            "               b.brnch_nm,\n" +
            "               c.clnt_seq,\n" +
            "               c.clnt_id,\n" +
            "               c.frst_nm || ' ' || c.last_nm,\n" +
            "               clm_amt,\n" +
            "               transaction_id\n" +
            "          FROM mw_clnt_hlth_insr_clm  chic\n" +
            "               JOIN mw_clnt c\n" +
            "                   ON c.clnt_seq = chic.clnt_seq AND c.crnt_rec_flg = 1\n" +
            "               JOIN mw_loan_app la\n" +
            "                   ON     la.CLNT_SEQ = chic.CLNT_SEQ\n" +
            "                      AND la.CRNT_REC_FLG = 1\n" +
            "                      AND la.LOAN_CYCL_NUM =\n" +
            "                          (SELECT MAX (ap1.LOAN_CYCL_NUM)\n" +
            "                             FROM mw_loan_app ap1\n" +
            "                            WHERE     ap1.CLNT_SEQ = la.clnt_seq\n" +
            "                                  AND ap1.CRNT_REC_FLG = 1\n" +
            "                                  AND ap1.brnch_seq = :brnch_seq)\n" +
            "               JOIN mw_brnch b\n" +
            "                   ON     b.brnch_seq = la.brnch_seq\n" +
            "                      AND b.crnt_rec_flg = 1\n" +
            "                      AND chic.brnch_seq = :brnch_seq\n" +
            "         WHERE     NVL (chic.clm_sts_key, 0) != 1\n" +
            "               AND chic.crnt_rec_flg = 1\n" +
            "               AND LA.PRD_SEQ <> 29\n" +
            "               AND chic.HLTH_CLM_TYP = :hlthClmTyp\n" +
            "               AND NOT EXISTS\n" +
            "                       (SELECT EXP_REF\n" +
            "                          FROM mw_exp e\n" +
            "                         WHERE     e.EXPNS_TYP_SEQ IN (343, 16226, 16227)\n" +
            "                               AND e.crnt_rec_flg = 1\n" +
            "                               AND e.DEL_FLG = 0\n" +
            "                               AND TO_NUMBER (e.exp_ref) =\n" +
            "                                   chic.clnt_hlth_clm_seq)\n" +
            "               AND TRUNC (CHIC.CRTD_DT) <= ('06-JUL-2022')  %%\n" +
            "        UNION ALL\n" +
            "        SELECT CHIC.CLNT_HLTH_CLM_SEQ,\n" +
            "               la.BRNCH_SEQ,\n" +
            "               b.brnch_nm,\n" +
            "               C.CLNT_SEQ,\n" +
            "               C.CLNT_ID,\n" +
            "               C.FRST_NM || ' ' || C.LAST_NM,\n" +
            "               CLM_AMT,\n" +
            "               TRANSACTION_ID\n" +
            "          FROM MW_CLNT_HLTH_INSR_CLM  CHIC\n" +
            "               JOIN MW_CLNT_HLTH_INSR_CARD CRD\n" +
            "                   ON CRD.CARD_NUM = CHIC.CARD_NUM AND CRD.CRNT_REC_FLG = 1\n" +
            "               JOIN MW_LOAN_APP LA\n" +
            "                   ON     LA.LOAN_APP_SEQ = CRD.LOAN_APP_SEQ\n" +
            "                      AND LA.CRNT_REC_FLG = 1\n" +
            "               JOIN MW_CLNT C\n" +
            "                   ON C.CLNT_SEQ = CHIC.CLNT_SEQ AND C.CRNT_REC_FLG = 1\n" +
            "               JOIN MW_BRNCH B\n" +
            "                   ON     B.BRNCH_SEQ = CHIC.BRNCH_SEQ\n" +
            "                      AND B.CRNT_REC_FLG = 1\n" +
            "                      AND CHIC.BRNCH_SEQ = :brnch_seq\n" +
            "         WHERE     NVL (CHIC.CLM_STS_KEY, 0) != 1\n" +
            "               AND CHIC.CRNT_REC_FLG = 1\n" +
            "               AND CHIC.HLTH_CLM_TYP = :hlthClmTyp\n" +
            "               AND LA.PRD_SEQ <> 29\n" +
            "               AND NOT EXISTS\n" +
            "                       (SELECT EXP_REF\n" +
            "                          FROM mw_exp e\n" +
            "                         WHERE     e.EXPNS_TYP_SEQ IN (343, 16226, 16227)\n" +
            "                               AND e.crnt_rec_flg = 1\n" +
            "                               AND e.DEL_FLG = 0\n" +
            "                               AND TO_NUMBER (e.exp_ref) =\n" +
            "                                   chic.clnt_hlth_clm_seq)\n" +
            "               AND TRUNC (CHIC.CRTD_DT) > ('06-JUL-2022') %% )";

        String search = "";
        if (filter != null && filter.length() > 0) {
            search = " and (lower(c.clnt_id) like '%?%') ".replace("?", filter.toLowerCase());
        }

        ClntHlthInsrClmsScript = ClntHlthInsrClmsScript.replaceAll("%%", search);
        ClntHlthInsrClmsCountScript = ClntHlthInsrClmsCountScript.replaceAll("%%", search);

        // Modified by Zohaib Asim - Dated 16-12-2020
        // Health Claim Type added as parameter
        List<Object[]> ClntHlthInsrClmsRecords = em.createNativeQuery(ClntHlthInsrClmsScript + "\r\norder by 1 desc")
            .setParameter("brnch_seq", brnchSeq).setParameter("hlthClmTyp", hlthClmTyp)
            .setFirstResult((pageIndex) * pageSize)
            .setMaxResults(pageSize).getResultList();

        List<ClntHlthInsrClmsDto> insrClmslist = new ArrayList();

        ClntHlthInsrClmsRecords.forEach(r -> {
            ClntHlthInsrClmsDto dto = new ClntHlthInsrClmsDto();
            dto.clntHlthClmSeq = r[0].toString();
            dto.brnchSeq = r[1].toString();
            dto.brnchNm = r[2].toString();
            dto.clntSeq = r[3].toString();
            dto.clntId = r[4].toString();
            dto.clntName = r[5] == null ? "" : r[5].toString();
            dto.clmAmt = r[6].toString();
            dto.transactionId = r[7] == null ? "" : r[7].toString();
            insrClmslist.add(dto);
        });

        Map<String, Object> resp = new HashMap<>();
        resp.put("clnts", insrClmslist);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            // Modified by Zohaib Asim - Dated 16-12-2020
            // Health Claim Type added as parameter
            totalCountResult = new BigDecimal(em.createNativeQuery(ClntHlthInsrClmsCountScript)
                .setParameter("brnch_seq", brnchSeq).setParameter("hlthClmTyp", hlthClmTyp)
                .getSingleResult().toString()).longValue();
        }
        resp.put("count", totalCountResult);
        return resp;
    }

    @Transactional(readOnly = true)
    public List<MwClntHlthInsrClm> findAllByCurrentRecord() {
        log.debug("Request to get all MwClntHlthInsrClm");
        return mwClntHlthInsrClmRepository.findAllByCrntRecFlg(true);
    }

    public MwClntHlthInsrClm addNewClntHlthInsrClm(ClientHealthInsuranceClaimDto dto, String currUser) {

        Instant currIns = Instant.now();

        long seq = SequenceFinder.findNextVal(Sequences.CLNT_HLTH_CLM_SEQ);
        MwClntHlthInsrClm clntHlthInsrClm = new MwClntHlthInsrClm();
        clntHlthInsrClm.setClntHlthClmSeq(seq);
        clntHlthInsrClm.setBrnchSeq(dto.getBrnchSeq());
        clntHlthInsrClm.setClntSeq(dto.getClntSeq());
        clntHlthInsrClm.setClmAmt(dto.getClmAmt());
        clntHlthInsrClm.setTransactionId(dto.getTransactionId());
        clntHlthInsrClm.setCrtdBy(currUser);
        clntHlthInsrClm.setCrtdDt(currIns);
        clntHlthInsrClm.setLastUpdBy(currUser);
        clntHlthInsrClm.setLastUpdDt(currIns);
        clntHlthInsrClm.setDelFlg(false);
        clntHlthInsrClm.setCrntRecFlg(true);
        clntHlthInsrClm.setEffStartDt(currIns);
        clntHlthInsrClm.setClmStsKey(dto.getClmStsKey());

        return mwClntHlthInsrClmRepository.save(clntHlthInsrClm);
    }

    @Transactional
    public MwClntHlthInsrClm UpdateExistingClntHlthInsrClm(ClientHealthInsuranceClaimDto dto, String currUser) {
        MwClntHlthInsrClm mwClntHlthInsrClm = mwClntHlthInsrClmRepository.findOneByClntHlthClmSeqAndCrntRecFlg(dto.getClnHlthClmSeq(), true);
        Instant currIns = Instant.now();
        if (mwClntHlthInsrClm == null) {
            return null;
        }

        mwClntHlthInsrClm.setLastUpdDt(currIns);
        mwClntHlthInsrClm.setLastUpdBy(currUser);
        mwClntHlthInsrClm.setClmStsKey(dto.getClmStsKey());
        mwClntHlthInsrClm.setTransactionId(dto.getTransactionId());
        mwClntHlthInsrClm.setClntSeq(dto.getClntSeq());
        mwClntHlthInsrClm.setBrnchSeq(dto.getBrnchSeq());
        mwClntHlthInsrClm.setClmAmt(dto.getClmAmt());

        return mwClntHlthInsrClmRepository.save(mwClntHlthInsrClm);


    }
}
