package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdChrgAdjOrdr;
import com.idev4.setup.dto.ProductChargesAdjustmentOrderDto;
import com.idev4.setup.repository.MwPrdChrgAdjOrdrRepository;
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
public class MwPrdChrgAdjOrdrService {

    private final Logger log = LoggerFactory.getLogger(MwPrdChrgAdjOrdrService.class);

    private final MwPrdChrgAdjOrdrRepository mwPrdChrgAdjOrdrRepository;

    public MwPrdChrgAdjOrdrService(MwPrdChrgAdjOrdrRepository mwPrdChrgAdjOrdrRepository) {
        this.mwPrdChrgAdjOrdrRepository = mwPrdChrgAdjOrdrRepository;
    }

    public MwPrdChrgAdjOrdr save(MwPrdChrgAdjOrdr mwPrdChrgAdjOrdr) {
        log.debug("Request to save MwPrdChrgAdjOrdr : {}", mwPrdChrgAdjOrdr);
        return mwPrdChrgAdjOrdrRepository.save(mwPrdChrgAdjOrdr);
    }

    @Transactional(readOnly = true)
    public MwPrdChrgAdjOrdr findOne(Long prdChrgAdjOrdrSeq) {
        log.debug("Request to get MwPrdChrgAdjOrdr : {}", prdChrgAdjOrdrSeq);
        return mwPrdChrgAdjOrdrRepository.findOneByPrdChrgAdjOrdrSeqAndCrntRecFlg(prdChrgAdjOrdrSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdChrgAdjOrdr> findAllByPrdChrgAdjOrdrSeq(Long prdChrgAdjOrdrSeq) {
        log.debug("Request to get MwPrdChrgAdjOrdr : {}", prdChrgAdjOrdrSeq);
        return mwPrdChrgAdjOrdrRepository.findAllByPrdChrgAdjOrdrSeq(prdChrgAdjOrdrSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdChrgAdjOrdr> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdChrgAdjOrdrs : {}");
        return mwPrdChrgAdjOrdrRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdChrgAdjOrdr> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdChrgAdjOrdr by product seq: {}");
        return mwPrdChrgAdjOrdrRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public boolean delete(Long prdChrgAdjOrdrSeq) {

        MwPrdChrgAdjOrdr prdChrgAdjOrdr = mwPrdChrgAdjOrdrRepository.findOneByPrdChrgAdjOrdrSeqAndCrntRecFlg(prdChrgAdjOrdrSeq, true);
        prdChrgAdjOrdr.setCrntRecFlg(false);
        prdChrgAdjOrdr.setDelFlg(true);
        prdChrgAdjOrdr.setLastUpdDt(Instant.now());
        mwPrdChrgAdjOrdrRepository.save(prdChrgAdjOrdr);
        return true;
    }

    public MwPrdChrgAdjOrdr addNewPrdChrgAdjOrdr(ProductChargesAdjustmentOrderDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_CHRG_ADJ_ORDR_SEQ);
        MwPrdChrgAdjOrdr prdChrgAdjOrdr = new MwPrdChrgAdjOrdr();
        Instant currIns = Instant.now();

        prdChrgAdjOrdr.setPrdChrgAdjOrdrSeq(seq);
        prdChrgAdjOrdr.setAdjOrdr(dto.getAdjOrdr());
        prdChrgAdjOrdr.setPrdChrgSeq(dto.getPrdChrgSeq());
        prdChrgAdjOrdr.setPrdSeq(dto.getPrdSeq());
        prdChrgAdjOrdr.setCrtdBy(currUser);
        prdChrgAdjOrdr.setCrtdDt(currIns);
        prdChrgAdjOrdr.setLastUpdBy(currUser);
        prdChrgAdjOrdr.setLastUpdDt(currIns);
        prdChrgAdjOrdr.setDelFlg(false);
        prdChrgAdjOrdr.setEffStartDt(currIns);
        prdChrgAdjOrdr.setCrntRecFlg(true);

        return mwPrdChrgAdjOrdrRepository.save(prdChrgAdjOrdr);

    }

    @Transactional
    public MwPrdChrgAdjOrdr updateExistingProductPrincipalLimis(ProductChargesAdjustmentOrderDto dto, String currUser) {
        MwPrdChrgAdjOrdr exPrdChrgAdjOrdr = mwPrdChrgAdjOrdrRepository.findOneByPrdChrgAdjOrdrSeqAndCrntRecFlg(dto.getPrdChrgAdjOrdrSeq(), true);
        Instant currIns = Instant.now();
        if (exPrdChrgAdjOrdr == null) {
            return null;
        }

        exPrdChrgAdjOrdr.setLastUpdBy(currUser);
        exPrdChrgAdjOrdr.setLastUpdDt(currIns);
        exPrdChrgAdjOrdr.setPrdSeq(dto.getPrdSeq());
        exPrdChrgAdjOrdr.setAdjOrdr(dto.getAdjOrdr());
        exPrdChrgAdjOrdr.setPrdChrgSeq(dto.getPrdChrgSeq());
        exPrdChrgAdjOrdr.setPrdSeq(dto.getPrdSeq());
        return mwPrdChrgAdjOrdrRepository.save(exPrdChrgAdjOrdr);

    }
}
