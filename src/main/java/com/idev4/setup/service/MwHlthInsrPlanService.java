package com.idev4.setup.service;

import com.idev4.setup.domain.MwHlthInsrPlan;
import com.idev4.setup.dto.HlthInsrPlanDto;
import com.idev4.setup.repository.MwHlthInsrPlanRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class MwHlthInsrPlanService {

    private final Logger log = LoggerFactory.getLogger(MwHlthInsrPlanService.class);

    private final MwHlthInsrPlanRepository mwHlthInsrPlanRepository;

    public MwHlthInsrPlanService(MwHlthInsrPlanRepository mwHlthInsrPlanRepository) {
        this.mwHlthInsrPlanRepository = mwHlthInsrPlanRepository;
    }

    public MwHlthInsrPlan save(MwHlthInsrPlan mwHlthInsrPlan) {
        log.debug("Request to save MwHlthInsrPlan : {}", mwHlthInsrPlan);
        return mwHlthInsrPlanRepository.save(mwHlthInsrPlan);
    }

    @Transactional(readOnly = true)
    public MwHlthInsrPlan findOne(Long id) {
        log.debug("Request to get MwHlthInsrPlan : {}", id);
        return mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwHlthInsrPlan> findAllHistory(Long id) {
        log.debug("Request to get MwHlthInsrPlan : {}", id);
        return mwHlthInsrPlanRepository.findAllByHlthInsrPlanSeq(id);
    }

    public boolean delete(Long id) {

        MwHlthInsrPlan hlthInsrPlan = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(id, true);
        hlthInsrPlan.setCrntRecFlg(false);
        hlthInsrPlan.setDelFlg(true);
        hlthInsrPlan.setLastUpdDt(Instant.now());
        mwHlthInsrPlanRepository.save(hlthInsrPlan);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwHlthInsrPlan> findAllByCurrentRecord() {
        log.debug("Request to get all MwHlthInsrPlan");
        return mwHlthInsrPlanRepository.findAllByCrntRecFlg(true);
    }

    public MwHlthInsrPlan addNewHlthInsrPlan(HlthInsrPlanDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.HLTH_INSR_PLAN_SEQ);
        MwHlthInsrPlan mwHlthInsrPlan = new MwHlthInsrPlan();
        Instant currIns = Instant.now();

        mwHlthInsrPlan.setHlthInsrPlanSeq(seq);
        mwHlthInsrPlan.setPlanId(String.format("%04d", seq));
        mwHlthInsrPlan.setPlanNm(dto.getPlanNm());
        mwHlthInsrPlan.setPlanStsKey(dto.getPlanStsKey());
        mwHlthInsrPlan.setAnlPremAmt(dto.getAnlPremAmt());
        mwHlthInsrPlan.setMaxPlcyAmt(dto.getMaxPlcyAmt());
        mwHlthInsrPlan.setCrtdBy(currUser);
        mwHlthInsrPlan.setCrtdDt(currIns);
        mwHlthInsrPlan.setLastUpdBy(currUser);
        mwHlthInsrPlan.setLastUpdDt(currIns);
        mwHlthInsrPlan.setDelFlg(false);
        mwHlthInsrPlan.setEffStartDt(currIns);
        mwHlthInsrPlan.setCrntRecFlg(true);
        mwHlthInsrPlan.setGlAcctNum(dto.getGlAcctNum());
        mwHlthInsrPlan.setDfrdAcctNum(dto.getDfrdAcctNum());

        //Added by Areeba - 15-9-2022
        mwHlthInsrPlan.setPlnDscr(dto.getPlnDscr());
        mwHlthInsrPlan.setHlthCardFlg(dto.getHlthCardFlg());
        mwHlthInsrPlan.setMnthFlg(dto.getMnthFlg());
        mwHlthInsrPlan.setBddtAcctNum(dto.getDfrdAcctNum());
        //Ended by Areeba

        return mwHlthInsrPlanRepository.save(mwHlthInsrPlan);

    }

    @Transactional
    public MwHlthInsrPlan updateExistingExpnsTyp(HlthInsrPlanDto dto, String currUser) {
        MwHlthInsrPlan hlthInsrPlan = mwHlthInsrPlanRepository.findOneByHlthInsrPlanSeqAndCrntRecFlg(dto.getHlthInsrPlanSeq(), true);
        Instant currIns = Instant.now();
        if (hlthInsrPlan == null) {
            return null;
        }

        hlthInsrPlan.setLastUpdDt(currIns);
        hlthInsrPlan.setLastUpdBy(currUser);
        hlthInsrPlan.setPlanNm(dto.getPlanNm());
        hlthInsrPlan.setPlanStsKey(dto.getPlanStsKey());
        hlthInsrPlan.setAnlPremAmt(dto.getAnlPremAmt());
        hlthInsrPlan.setMaxPlcyAmt(dto.getMaxPlcyAmt());
        hlthInsrPlan.setGlAcctNum(dto.getGlAcctNum());
        hlthInsrPlan.setDfrdAcctNum(dto.getDfrdAcctNum());

        //Added by Areeba - 15-9-2022
        hlthInsrPlan.setBddtAcctNum(dto.getBddtAcctNum());
        hlthInsrPlan.setPlnDscr(dto.getPlnDscr());
        hlthInsrPlan.setHlthCardFlg(dto.getHlthCardFlg());
        hlthInsrPlan.setMnthFlg(dto.getMnthFlg());
        //Ended by Areeba

        return mwHlthInsrPlanRepository.save(hlthInsrPlan);


    }
}
