package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdLoanTrm;
import com.idev4.setup.dto.ProductLoanTermsDto;
import com.idev4.setup.repository.MwPrdLoanTrmRepository;
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
public class MwPrdLoanTrmService {

    private final Logger log = LoggerFactory.getLogger(MwPrdLoanTrmService.class);

    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;

    public MwPrdLoanTrmService(MwPrdLoanTrmRepository mwPrdLoanTrmRepository) {
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
    }

    public MwPrdLoanTrm save(MwPrdLoanTrm mwPrdLoanTrm) {
        log.debug("Request to save MwPrdLoanTrm : {}", mwPrdLoanTrm);
        return mwPrdLoanTrmRepository.save(mwPrdLoanTrm);
    }

    @Transactional(readOnly = true)
    public MwPrdLoanTrm findOne(Long prdTrmSeq) {
        log.debug("Request to get MwPrdLoanTrm : {}", prdTrmSeq);
        return mwPrdLoanTrmRepository.findOneByPrdTrmSeqAndCrntRecFlg(prdTrmSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdLoanTrm> findAllByPrdTrmSeq(Long prdTrmSeq) {
        log.debug("Request to get MwPrdLoanTrm : {}", prdTrmSeq);
        return mwPrdLoanTrmRepository.findAllByPrdTrmSeq(prdTrmSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdLoanTrm> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdLoanTrms : {}");
        return mwPrdLoanTrmRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdLoanTrm> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdLoanTrm : {}");
        return mwPrdLoanTrmRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public boolean delete(Long prdTrmSeq) {

        MwPrdLoanTrm prdLoanTrm = mwPrdLoanTrmRepository.findOneByPrdTrmSeqAndCrntRecFlg(prdTrmSeq, true);
        prdLoanTrm.setCrntRecFlg(false);
        prdLoanTrm.setDelFlg(true);
        prdLoanTrm.setLastUpdDt(Instant.now());
        mwPrdLoanTrmRepository.save(prdLoanTrm);
        return true;
    }

    public MwPrdLoanTrm addNewPrdLoanTrm(ProductLoanTermsDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_TRM_SEQ);
        MwPrdLoanTrm prdLoanTrm = new MwPrdLoanTrm();
        Instant currIns = Instant.now();

        prdLoanTrm.setPrdTrmSeq(seq);
        prdLoanTrm.setPrdSeq(dto.getPrdSeq());
        prdLoanTrm.setRulSeq(dto.getRulSeq());
        prdLoanTrm.setTrmKey(dto.getTrmKey());
        prdLoanTrm.setPymtFreqKey(dto.getPymtFreqKey());
        prdLoanTrm.setCrtdBy(currUser);
        prdLoanTrm.setCrtdDt(currIns);
        prdLoanTrm.setLastUpdBy(currUser);
        prdLoanTrm.setLastUpdDt(currIns);
        prdLoanTrm.setDelFlg(false);
        prdLoanTrm.setEffStartDt(currIns);
        prdLoanTrm.setCrntRecFlg(true);

        return mwPrdLoanTrmRepository.save(prdLoanTrm);

    }

    @Transactional
    public MwPrdLoanTrm updateExistingProductLoanTerms(ProductLoanTermsDto dto, String currUser) {
        MwPrdLoanTrm prdLoanTrm = mwPrdLoanTrmRepository.findOneByPrdTrmSeqAndCrntRecFlg(dto.getPrdTrmSeq(), true);
        Instant currIns = Instant.now();
        if (prdLoanTrm == null) {
            return null;
        }

        prdLoanTrm.setLastUpdDt(currIns);
        prdLoanTrm.setLastUpdBy(currUser);
        prdLoanTrm.setTrmKey(dto.getTrmKey());
        prdLoanTrm.setPymtFreqKey(dto.getPymtFreqKey());
        prdLoanTrm.setRulSeq(dto.getRulSeq());
        return mwPrdLoanTrmRepository.save(prdLoanTrm);


    }
}
