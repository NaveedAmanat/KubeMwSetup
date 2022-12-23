package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnch;
import com.idev4.setup.domain.MwClntHlthInsrClm;
import com.idev4.setup.domain.MwExp;
import com.idev4.setup.domain.MwTyps;
import com.idev4.setup.dto.ExpenseDto;
import com.idev4.setup.feignclients.ServiceClient;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwExp.
 */
@Service
@Transactional
public class MwExpService {

    private final Logger log = LoggerFactory.getLogger(MwExpService.class);

    private final MwExpRepository mwExpRepository;
    private final MwBrnchBdgtRepository mwBrnchBdgtRepository;
    private final MwBrnchRepository mwBrnchRepository;
    private final MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository;
    @Autowired
    MwTypsRepository mwTypsRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    HttpServletRequest context;

    @Autowired
    ServiceClient serviceClient;

    public MwExpService(MwExpRepository mwExpRepository, MwBrnchBdgtRepository mwBrnchBdgtRepository, MwBrnchRepository mwBrnchRepository,
                        MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository) {
        this.mwExpRepository = mwExpRepository;
        this.mwBrnchBdgtRepository = mwBrnchBdgtRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwClntHlthInsrClmRepository = mwClntHlthInsrClmRepository;
    }

    public MwExp save(MwExp mwExp) {
        log.debug("Request to save MwExp : {}", mwExp);
        return mwExpRepository.save(mwExp);
    }

    @Transactional(readOnly = true)
    public MwExp findOne(Long id) {
        log.debug("Request to get MwExp : {}", id);
        return mwExpRepository.findOneByExpSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwExp> findAllHistory(Long id) {
        log.debug("Request to get MwExp : {}", id);
        return mwExpRepository.findAllByExpSeq(id);
    }

    public boolean delete(Long id) {

        MwExp exp = mwExpRepository.findOneByExpSeqAndCrntRecFlg(id, true);
        exp.setCrntRecFlg(false);
        exp.setDelFlg(true);
        exp.setLastUpdDt(Instant.now());
        mwExpRepository.save(exp);
        return true;
    }

    public List<MwExp> findAllByBrnchSeqAndCrntRecFlg(String role) {
        if (!role.equals("bm")) {
            return mwExpRepository.findAllByCrntRecFlgAndDelFlgOrCrntRecFlgAndDelFlgOrderByExpSeqDesc(true, false, true, true);
        }
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        MwBrnch branch = mwBrnchRepository.findOneByEmpSeqAndCrntRecFlg(currUser);
        return mwExpRepository.findExpByBranchSeq(branch.getBrnchSeq());
    }

//    public List< MwExp > findAllByBrnchSeqAndCrntRecFlg( Long seq ) {
//        return mwExpRepository.findExpByBranchSeq( seq );
//    }

    public Map<String, Object> findAllByBrnchSeqAndCrntRecFlg(Long brnchSeq, Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String expensescript = mwExpRepository.findByExpBrnchSeqQuery;
        String countScript = mwExpRepository.findByExpBrnchSeqCountQuery;

        if (filter != null && filter.length() > 0) {

            String search = " and (lower(e.expns_id) like '%?%')"
                .replace("?", filter.toLowerCase());

            expensescript += search;
            countScript += search;
        }

        Query expByBrnchSeqQuery = entityManager.createNativeQuery(expensescript + "\r\norder by 1 desc", MwExp.class)
            .setParameter("brnchSeq", brnchSeq);

        List<MwExp> expByBrnchSeq = expByBrnchSeqQuery.setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> resp = new HashMap<>();
        resp.put("exp", expByBrnchSeq);

        Long totalCountResult = 0L;
        if (isCount.booleanValue()) {
            totalCountResult = new BigDecimal(entityManager.createNativeQuery(countScript)
                .setParameter("brnchSeq", brnchSeq)
                .getSingleResult().toString()).longValue();
        }

        resp.put("count", totalCountResult);
        return resp;
    }

    public MwExp addNewExp(ExpenseDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.EXP_SEQ);
        MwExp exp = new MwExp();
        Instant currIns = Instant.now();
        exp.setExpSeq(seq);
        exp.setBrnchSeq(dto.getBrnchSeq());
        exp.setExpnsStsKey(dto.getExpnsStsKey());
        exp.setExpnsId(String.format("%04d", seq));
        exp.setExpnsDscr(dto.getExpnsDscr());
        exp.setInstrNum(dto.getInstrNum());
        exp.setExpnsAmt(dto.getExpnsAmt());
        exp.setExpnsTypSeq(dto.getExpnsTypSeq());
        exp.setPymtTypSeq(dto.getPymtTypSeq());
        exp.setExpRef(dto.getExpRef());
        exp.setPymtRctFlg(dto.getPymtRctFlg());
        exp.setCrtdBy(currUser);
        exp.setCrtdDt(currIns);
        exp.setLastUpdBy(currUser);
        exp.setLastUpdDt(currIns);
        exp.setDelFlg(false);
        exp.setEffStartDt(currIns);
        exp.setCrntRecFlg(true);
        return mwExpRepository.save(exp);

    }

    @Transactional
    public MwExp updateExistingExp(ExpenseDto dto, String currUser) {
        MwExp exExp = mwExpRepository.findOneByExpSeq(dto.getExpSeq());
        Instant currIns = Instant.now();
        if (exExp == null || (exExp.getPostFlg() != null && exExp.getPostFlg())) {
            return null;
        }

        exExp.setLastUpdBy(currUser);
        exExp.setLastUpdDt(currIns);
        exExp.setBrnchSeq(dto.getBrnchSeq());
        exExp.setExpnsStsKey(dto.getExpnsStsKey());
        exExp.setExpnsDscr(dto.getExpnsDscr());
        exExp.setInstrNum(dto.getInstrNum());
        exExp.setExpnsAmt(dto.getExpnsAmt());
        exExp.setExpnsTypSeq(dto.getExpnsTypSeq());
        exExp.setPymtTypSeq(dto.getPymtTypSeq());
        exExp.setExpRef(dto.getExpRef());
        exExp.setPymtRctFlg(dto.getPymtRctFlg());
        return mwExpRepository.save(exExp);

    }

    @Transactional
    public MwExp reverseExistingExp(ExpenseDto dto, String currUser, String token) {
        MwExp exExp = mwExpRepository.findOneByExpSeqAndCrntRecFlg(dto.getExpSeq(), true);
        Instant currIns = Instant.now();
        //Added by Areeba
        MwTyps dsbltyClaim = mwTypsRepository.findOneByTypIdAndTypCtgryKeyAndCrntRecFlgAndBrnchSeq("0423", 2L, true, 0L);
        if (exExp.getPostFlg() != null && exExp.getPostFlg() && exExp.getExpnsTypSeq().longValue() == dsbltyClaim.getTypSeq()) {
            serviceClient.reversExpence(dto.getExpSeq(), (dto.rmrks == null) ? "Disability Receivable Reversal" : dto.rmrks, token);
        } else {
            serviceClient.reversExpence(dto.getExpSeq(), (dto.rmrks == null) ? "Funeral Charge Reversal" : dto.rmrks, token);
        }
        //Ended by Areeba
        exExp.setCrntRecFlg(true);
        exExp.setEffEndDt(currIns);
        exExp.setLastUpdBy(currUser);
        exExp.setLastUpdDt(currIns);
        exExp.setDelFlg(true);

        if (exExp.getExpnsTypSeq().longValue() == 343) {
            List<MwClntHlthInsrClm> claims = mwClntHlthInsrClmRepository
                .findAllByClntSeqAndCrntRecFlgOrderByEffStartDtDesc(Long.parseLong(exExp.getExpRef()), true);
            if (claims != null && claims.size() > 0) {
                MwClntHlthInsrClm exClaim = claims.get(0);
                exClaim.setCrntRecFlg(false);
                exClaim.setEffEndDt(currIns);
                exClaim.setLastUpdBy(currUser);
                exClaim.setLastUpdDt(currIns);
                exClaim.setDelFlg(true);
                mwClntHlthInsrClmRepository.save(exClaim);
                MwClntHlthInsrClm claim = new MwClntHlthInsrClm();
                claim.setBrnchSeq(exClaim.getBrnchSeq());
                claim.setClmAmt(exClaim.getClmAmt());
                claim.setClmStsKey(0L);
                claim.setClmTyp(exClaim.getClmTyp());
                claim.setClntHlthClmSeq(exClaim.getClntHlthClmSeq());
                claim.setClntSeq(exClaim.getClntSeq());
                claim.setCrntRecFlg(true);
                claim.setCrtdBy(currUser);
                claim.setCrtdDt(currIns);
                claim.setEffStartDt(currIns);
                claim.setLastUpdBy(currUser);
                claim.setLastUpdDt(currIns);
                claim.setTransactionId(exClaim.getTransactionId());

                // Modified by Zohaib Asim - Dated 16-08-2021 - Branch Expense Reversal Issue
                // Health Claim Type cannot be empty, Update with Zero
                claim.setHlthClmTyp(0L);
                // End

                claim.setPostFlg(false);
                mwClntHlthInsrClmRepository.save(claim);
            }
        }

        return mwExpRepository.save(exExp);

    }

    /*public boolean aviblBdgt(long brnchSeq,Double amt) {
     Long crntMnthExp=mwExpRepository.findSumByCrntMnthAndCrntYear(brnchSeq);
     Calendar now = Calendar.getInstance();
     long month=now.get(Calendar.YEAR);
     MwBrnchBdgt crntMnthBdgt=mwBrnchBdgtRepository.findOneByBrnchSeqAndPredAndBdgtYr(brnchSeq,(long)now.get(Calendar.MONTH) + 1,(long)now.get(Calendar.YEAR));
     if((crntMnthExp+amt)<=crntMnthBdgt.getBdgtAmt())
        {
         return true;
        }

        return false;
    }*/

}
