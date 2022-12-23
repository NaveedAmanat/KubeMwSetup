package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdAdvRul;
import com.idev4.setup.domain.MwRul;
import com.idev4.setup.dto.ProductAdvanceRuleDto;
import com.idev4.setup.repository.MwPrdAdvRulRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MwPrdAdvRulService {

    private final Logger log = LoggerFactory.getLogger(MwPrdAdvRulService.class);

    private final MwPrdAdvRulRepository mwPrdAdvRulRepository;

    private final MwRulService mwRulService;

    public MwPrdAdvRulService(MwPrdAdvRulRepository mwPrdAdvRulRepository, MwRulService mwRulService) {
        this.mwPrdAdvRulRepository = mwPrdAdvRulRepository;
        this.mwRulService = mwRulService;
    }

    public MwPrdAdvRul save(MwPrdAdvRul mwPrdAdvRul) {
        log.debug("Request to save MwPrdAdvRul : {}", mwPrdAdvRul);
        return mwPrdAdvRulRepository.save(mwPrdAdvRul);
    }

    @Transactional(readOnly = true)
    public MwPrdAdvRul findOne(Long prdAdvRulSeq) {
        log.debug("Request to get MwPrdAdvRul : {}", prdAdvRulSeq);
        return mwPrdAdvRulRepository.findOneByPrdAdvRulSeqAndCrntRecFlg(prdAdvRulSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdAdvRul> findAllByPrdAdvRulSeq(Long prdAdvRulSeq) {
        log.debug("Request to get MwPrdAdvRul : {}", prdAdvRulSeq);
        return mwPrdAdvRulRepository.findAllByPrdAdvRulSeq(prdAdvRulSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdAdvRul> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdAdvRuls : {}");
        return mwPrdAdvRulRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<ProductAdvanceRuleDto> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdAdvRul : {}");
        List<ProductAdvanceRuleDto> productAdvanceRuleDtos = new ArrayList<>();
        List<MwPrdAdvRul> mwPrdAdvRuls = mwPrdAdvRulRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
        for (MwPrdAdvRul mwPrdAdvRul : mwPrdAdvRuls) {
            ProductAdvanceRuleDto dto = new ProductAdvanceRuleDto();
            MwRul mwRul = new MwRul();
            mwRul = mwRulService.findOne(mwPrdAdvRul.getRulSeq());
            dto.setPrdAdvRulSeq(mwPrdAdvRul.getPrdAdvRulSeq());
            dto.setRulSeq(mwRul.getRulSeq());
            dto.setRulId(mwRul.getRulId());
            dto.setRulNm(mwRul.getRulNm());
            dto.setRulCtgryKey(mwRul.getRulCtgryKey());
            dto.setRulCrtraStr(mwRul.getRulCrtraStr());
            dto.setRulCmnt(mwRul.getRulCmnt());

            productAdvanceRuleDtos.add(dto);
        }
        return productAdvanceRuleDtos;
    }

    public boolean delete(Long prdAdvRulSeq) {

        MwPrdAdvRul prdAdvRul = mwPrdAdvRulRepository.findOneByPrdAdvRulSeqAndCrntRecFlg(prdAdvRulSeq, true);
        prdAdvRul.setCrntRecFlg(false);
        prdAdvRul.setDelFlg(true);
        prdAdvRul.setLastUpdDt(Instant.now());
        mwPrdAdvRulRepository.save(prdAdvRul);
        return true;
    }

    public MwPrdAdvRul addNewPrdAdvRul(ProductAdvanceRuleDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_ADV_RUL_SEQ);
        MwPrdAdvRul prdAdvRul = new MwPrdAdvRul();
        Instant currIns = Instant.now();

        prdAdvRul.setPrdAdvRulSeq(seq);
        prdAdvRul.setRulSeq(dto.getRulSeq());
        prdAdvRul.setPrdSeq(dto.getPrdSeq());
        prdAdvRul.setCrtdBy(currUser);
        prdAdvRul.setCrtdDt(currIns);
        prdAdvRul.setLastUpdBy(currUser);
        prdAdvRul.setLastUpdDt(currIns);
        prdAdvRul.setDelFlg(false);
        prdAdvRul.setEffStartDt(currIns);
        prdAdvRul.setCrntRecFlg(true);

        return mwPrdAdvRulRepository.save(prdAdvRul);

    }

    @Transactional
    public MwPrdAdvRul updateExistingProductAdvanceRule(ProductAdvanceRuleDto dto, String currUser) {
        MwPrdAdvRul exPrdAdvRul = mwPrdAdvRulRepository.findOneByPrdAdvRulSeqAndCrntRecFlg(dto.getPrdAdvRulSeq(), true);
        Instant currIns = Instant.now();
        if (exPrdAdvRul == null) {
            return null;
        }

        exPrdAdvRul.setPrdAdvRulSeq(dto.getPrdAdvRulSeq());
        exPrdAdvRul.setLastUpdBy(currUser);
        exPrdAdvRul.setLastUpdDt(currIns);
        exPrdAdvRul.setRulSeq(dto.getRulSeq());
        exPrdAdvRul.setPrdSeq(dto.getPrdSeq());

        return mwPrdAdvRulRepository.save(exPrdAdvRul);

    }
}
