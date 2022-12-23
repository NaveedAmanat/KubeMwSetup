package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdAcctSet;
import com.idev4.setup.dto.ProductAccountSetupDto;
import com.idev4.setup.repository.MwPrdAcctSetRepository;
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
public class MwPrdAcctSetService {

    private final Logger log = LoggerFactory.getLogger(MwPrdAcctSetService.class);

    private final MwPrdAcctSetRepository mwPrdAcctSetRepository;

    public MwPrdAcctSetService(MwPrdAcctSetRepository mwPrdAcctSetRepository) {
        this.mwPrdAcctSetRepository = mwPrdAcctSetRepository;
    }

    public MwPrdAcctSet save(MwPrdAcctSet mwPrdAcctSet) {
        log.debug("Request to save MwPrdAcctSet : {}", mwPrdAcctSet);
        return mwPrdAcctSetRepository.save(mwPrdAcctSet);
    }

    @Transactional(readOnly = true)
    public MwPrdAcctSet findOne(Long prdAcctSetSeq) {
        log.debug("Request to get MwPrdAcctSet : {}", prdAcctSetSeq);
        return mwPrdAcctSetRepository.findOneByPrdAcctSetSeqAndCrntRecFlg(prdAcctSetSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdAcctSet> findAllByPrdAcctSetSeq(Long prdAcctSetSeq) {
        log.debug("Request to get MwPrdAcctSet : {}", prdAcctSetSeq);
        return mwPrdAcctSetRepository.findAllByPrdAcctSetSeq(prdAcctSetSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdAcctSet> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdAcctSets : {}");
        return mwPrdAcctSetRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdAcctSet> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdAcctSet : {}");
        return mwPrdAcctSetRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public boolean delete(Long prdAcctSetSeq) {

        MwPrdAcctSet prdAcctSet = mwPrdAcctSetRepository.findOneByPrdAcctSetSeqAndCrntRecFlg(prdAcctSetSeq, true);
        prdAcctSet.setCrntRecFlg(false);
        prdAcctSet.setDelFlg(true);
        prdAcctSet.setLastUpdDt(Instant.now());
        mwPrdAcctSetRepository.save(prdAcctSet);
        return true;
    }

    public MwPrdAcctSet addNewPrdAcctSet(ProductAccountSetupDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_ACCT_SET_SEQ);
        MwPrdAcctSet prdAcctSet = new MwPrdAcctSet();
        Instant currIns = Instant.now();

        prdAcctSet.setPrdAcctSetSeq(seq);
        prdAcctSet.setAcctCtgryKey(dto.getAcctCtgryKey());
        prdAcctSet.setGlAcctNum(dto.getGlAcctNum());
        prdAcctSet.setPrdSeq(dto.getPrdSeq());
        prdAcctSet.setCrtdBy(currUser);
        prdAcctSet.setCrtdDt(currIns);
        prdAcctSet.setLastUpdBy(currUser);
        prdAcctSet.setLastUpdDt(currIns);
        prdAcctSet.setDelFlg(false);
        prdAcctSet.setEffStartDt(currIns);
        prdAcctSet.setCrntRecFlg(true);

        return mwPrdAcctSetRepository.save(prdAcctSet);

    }

    @Transactional
    public MwPrdAcctSet updateExistingProductAccountSetup(ProductAccountSetupDto dto, String currUser) {
        MwPrdAcctSet mwPrdAcctSet = mwPrdAcctSetRepository.findOneByPrdAcctSetSeqAndCrntRecFlg(dto.getPrdAcctSetSeq(), true);

        Instant currIns = Instant.now();
        if (mwPrdAcctSet == null) {
            return null;
        }

        mwPrdAcctSet.setLastUpdDt(currIns);
        mwPrdAcctSet.setLastUpdBy(currUser);
        mwPrdAcctSet.setGlAcctNum(dto.getGlAcctNum());
        mwPrdAcctSet.setAcctCtgryKey(dto.getAcctCtgryKey());
        return mwPrdAcctSetRepository.save(mwPrdAcctSet);


    }
}
