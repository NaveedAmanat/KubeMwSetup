package com.idev4.setup.service;

import com.idev4.setup.domain.MwRsLoanRule;
import com.idev4.setup.dto.MwRsLoanRuleDTO;
import com.idev4.setup.repository.MwRsLoanRuleRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MwRsLoanRuleService {

    private final Logger log = LoggerFactory.getLogger(MwRsLoanRuleService.class);

    @Autowired
    MwRsLoanRuleRepository mwRsLoanRuleRepository;

    @Autowired
    EntityManager em;

    @Transactional(readOnly = true)
    public Map<String, Object> getMwRsLoanRule(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {

        String rsLoanRuleScript = " select rule_seq, rule_desc, principal_amount, service_charges, sc_charge_more,\n" +
            " sc_per_inst, sc_new_irr_rate, insurance_charges, ins_charge_more, ins_per_inst, \n" +
            " ins_new_amount, jump_months, loan_reschd_category, loan_rechd_type, crnt_rec_flg from mw_rs_loan_rule ? " +
            " order by rule_seq desc ";
        String rsLoanRuleCountScript = "SELECT COUNT(*) FROM ( " + rsLoanRuleScript + " ) ";
        String search = " ";
        if (filter != null && filter.length() > 0) {
            search = (" WHERE ((LOWER(rule_desc) LIKE '%?%' )) ")
                .replace("?", filter.toLowerCase());
        }
        rsLoanRuleScript = rsLoanRuleScript.replace("?", search);
        rsLoanRuleCountScript = rsLoanRuleCountScript.replace("?", search);

        List<Object[]> rsLoanRuleList = em.createNativeQuery(rsLoanRuleScript)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        List<MwRsLoanRuleDTO> dtoList = new ArrayList();
        rsLoanRuleList.forEach(l -> {
            MwRsLoanRuleDTO dto = new MwRsLoanRuleDTO();
            dto.ruleSeq = l[0] == null ? 0 : new BigDecimal(l[0].toString()).longValue();
            dto.ruleDesc = l[1] == null ? "" : l[1].toString();
            dto.principalAmount = l[2] == null ? 0 : new BigDecimal(l[2].toString()).longValue();
            dto.serviceCharges = l[3] == null ? 0 : new BigDecimal(l[3].toString()).longValue();
            dto.scChargeMore = l[4] == null ? 0 : new BigDecimal(l[4].toString()).longValue();
            dto.scPerInst = l[5] == null ? 0 : new BigDecimal(l[5].toString()).longValue();
            dto.scNewIrrRate = l[6] == null ? 0 : new BigDecimal(l[6].toString()).longValue();
            dto.insuranceCharges = l[7] == null ? 0 : new BigDecimal(l[7].toString()).longValue();
            dto.insChargeMore = l[8] == null ? 0 : new BigDecimal(l[8].toString()).longValue();
            dto.insPerInst = l[9] == null ? 0 : new BigDecimal(l[9].toString()).longValue();
            dto.insNewAmount = l[10] == null ? 0 : new BigDecimal(l[10].toString()).longValue();
            dto.jumpMonths = l[11] == null ? 0 : new BigDecimal(l[11].toString()).longValue();
            dto.loanReschdCategory = l[12] == null ? 0 : new BigDecimal(l[12].toString()).longValue();
            dto.loanRechdType = l[13] == null ? 0 : new BigDecimal(l[13].toString()).longValue();
            dto.crntRecFlg = l[14] == null ? 0 : new BigDecimal(l[14].toString()).longValue();

            dtoList.add(dto);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("LoanRule", dtoList);

        Long totalCount = 0L;
        if (isCount.booleanValue()) {
            totalCount = new BigDecimal(
                em.createNativeQuery(rsLoanRuleCountScript)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCount);

        return response;
    }

    public Integer addMwRsLoanRule(MwRsLoanRuleDTO dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto == null) {
            return 0;
        }

        MwRsLoanRule mwRsLoanRule = new MwRsLoanRule();
        long ruleSeq = SequenceFinder.findNextVal(Sequences.RULE_SEQ);

        mwRsLoanRule.setRuleSeq(ruleSeq);
        mwRsLoanRule.setRuleDesc(dto.ruleDesc.toUpperCase());
        mwRsLoanRule.setPrincipalAmount(dto.principalAmount);
        mwRsLoanRule.setServiceCharges(dto.serviceCharges);
        mwRsLoanRule.setScChargeMore(dto.scChargeMore);
        mwRsLoanRule.setScPerInst(dto.scPerInst);
        mwRsLoanRule.setScNewIrrRate(dto.scNewIrrRate);
        mwRsLoanRule.setInsuranceCharges(dto.insuranceCharges);
        mwRsLoanRule.setInsChargeMore(dto.insChargeMore);
        mwRsLoanRule.setInsPerInst(dto.insPerInst);
        mwRsLoanRule.setInsNewAmount(dto.insNewAmount);
        mwRsLoanRule.setJumpMonths(dto.jumpMonths);
        mwRsLoanRule.setCrtdBy(currUser);
        mwRsLoanRule.setCrtdDt(currIns);
        mwRsLoanRule.setLastUpdBy(currUser);
        mwRsLoanRule.setLastUpdDt(currIns);
        mwRsLoanRule.setDelFlg(false);
        mwRsLoanRule.setCrntRecFlg(true);
        mwRsLoanRule.setLoanReschdCategory(dto.loanReschdCategory);
        mwRsLoanRule.setLoanRechdType(dto.loanRechdType);

        mwRsLoanRuleRepository.save(mwRsLoanRule);

        return 1;
    }

    public Integer deleteMwRsLoanRule(Long seq, String currUser) {
        Instant currIns = Instant.now();

        if (seq != null) {
            MwRsLoanRule mwRsLoanRule = mwRsLoanRuleRepository.findOneByRuleSeqAndDelFlg(seq, false);
            if (mwRsLoanRule != null) {
                mwRsLoanRule.setDelFlg(true);
                mwRsLoanRule.setCrntRecFlg(false);

                mwRsLoanRuleRepository.save(mwRsLoanRule);
                return 1;
            }
        }
        return 0;
    }

    public Integer disableMwRsLoanRule(Long seq, String currUser) {
        Instant currIns = Instant.now();

        if (seq != null) {
            MwRsLoanRule mwRsLoanRule = mwRsLoanRuleRepository.findOneByRuleSeq(seq);
            if (mwRsLoanRule != null && mwRsLoanRule.getCrntRecFlg() == true) {
                mwRsLoanRule.setCrntRecFlg(false);
                mwRsLoanRule.setDelFlg(true);
                mwRsLoanRule.setLastUpdBy(currUser);
                mwRsLoanRule.setLastUpdDt(currIns);

                mwRsLoanRuleRepository.save(mwRsLoanRule);
                return 1;
            } else if (mwRsLoanRule != null && mwRsLoanRule.getCrntRecFlg() == false) {
                mwRsLoanRule.setCrntRecFlg(true);
                mwRsLoanRule.setDelFlg(false);
                mwRsLoanRule.setLastUpdBy(currUser);
                mwRsLoanRule.setLastUpdDt(currIns);

                mwRsLoanRuleRepository.save(mwRsLoanRule);
                return 2;
            }
        }
        return 0;
    }

    public Integer updateMwRsLoanRule(MwRsLoanRuleDTO dto, String currUser) {
        Instant currIns = Instant.now();

        if (dto != null) {
            MwRsLoanRule mwRsLoanRule = mwRsLoanRuleRepository.findOneByRuleSeq(dto.ruleSeq);
            if (mwRsLoanRule != null) {
                mwRsLoanRule.setRuleDesc(dto.ruleDesc.toUpperCase());
                mwRsLoanRule.setPrincipalAmount(dto.principalAmount);
                mwRsLoanRule.setServiceCharges(dto.serviceCharges);
                mwRsLoanRule.setScChargeMore(dto.scChargeMore);
                mwRsLoanRule.setScPerInst(dto.scPerInst);
                mwRsLoanRule.setScNewIrrRate(dto.scNewIrrRate);
                mwRsLoanRule.setInsuranceCharges(dto.insuranceCharges);
                mwRsLoanRule.setInsChargeMore(dto.insChargeMore);
                mwRsLoanRule.setInsPerInst(dto.insPerInst);
                mwRsLoanRule.setInsNewAmount(dto.insNewAmount);
                mwRsLoanRule.setJumpMonths(dto.jumpMonths);
                mwRsLoanRule.setLastUpdBy(currUser);
                mwRsLoanRule.setLastUpdDt(currIns);
                mwRsLoanRule.setLoanReschdCategory(dto.loanReschdCategory);
                mwRsLoanRule.setLoanRechdType(dto.loanRechdType);

                mwRsLoanRuleRepository.save(mwRsLoanRule);
                return 1;
            }
        }
        return 0;
    }
}
