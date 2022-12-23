package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdSgrtInst;
import com.idev4.setup.dto.ProductSegregateInstallmentDto;
import com.idev4.setup.repository.MwPrdSgrtInstRepository;
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
public class MwPrdSgrtInstService {

    private final Logger log = LoggerFactory.getLogger(MwPrdSgrtInstService.class);

    private final MwPrdSgrtInstRepository mwPrdSgrtInstRepository;

    public MwPrdSgrtInstService(MwPrdSgrtInstRepository mwPrdSgrtInstRepository) {
        this.mwPrdSgrtInstRepository = mwPrdSgrtInstRepository;
    }

    public MwPrdSgrtInst save(MwPrdSgrtInst mwPrdSgrtInst) {
        log.debug("Request to save MwPrdSgrtInst : {}", mwPrdSgrtInst);
        return mwPrdSgrtInstRepository.save(mwPrdSgrtInst);
    }

    @Transactional(readOnly = true)
    public MwPrdSgrtInst findOne(Long prdSgrtInstSeq) {
        log.debug("Request to get MwPrdSgrtInst : {}", prdSgrtInstSeq);
        return mwPrdSgrtInstRepository.findOneByPrdSgrtInstSeqAndCrntRecFlg(prdSgrtInstSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdSgrtInst> findAllByPrdSgrtInstSeq(Long prdSgrtInstSeq) {
        log.debug("Request to get MwPrdSgrtInst : {}", prdSgrtInstSeq);
        return mwPrdSgrtInstRepository.findAllByPrdSgrtInstSeq(prdSgrtInstSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdSgrtInst> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdSgrtInst : {}");
        return mwPrdSgrtInstRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdSgrtInst> findAllBySgrtEntySeqAndEntyTyp(Long sgrtEntySeq, String entyTypStr) {
        log.debug("Request to get All MwPrdSgrtInst by SgrtEntySeq");
        return mwPrdSgrtInstRepository.findAllBySgrtEntySeqAndEntyTypStrAndCrntRecFlg(sgrtEntySeq, entyTypStr, true);
    }

    public boolean delete(Long prdSgrtInstSeq) {

        MwPrdSgrtInst prdSgrtInst = mwPrdSgrtInstRepository.findOneByPrdSgrtInstSeqAndCrntRecFlg(prdSgrtInstSeq, true);
        prdSgrtInst.setCrntRecFlg(false);
        prdSgrtInst.setDelFlg(true);
        prdSgrtInst.setLastUpdDt(Instant.now());
        mwPrdSgrtInstRepository.save(prdSgrtInst);
        return true;
    }

    public MwPrdSgrtInst addNewPrdSgrtInst(ProductSegregateInstallmentDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_SGRT_INST_SEQ);
        MwPrdSgrtInst prdSgrtInst = new MwPrdSgrtInst();
        Instant currIns = Instant.now();

        prdSgrtInst.setPrdSgrtInstSeq(seq);
        prdSgrtInst.setSgrtEntySeq(dto.getSgrtEntySeq());
        prdSgrtInst.setEntyTypStr(dto.getEntyTypStr());
        prdSgrtInst.setInstNum(dto.getInstNum());
        prdSgrtInst.setCrtdBy(currUser);
        prdSgrtInst.setCrtdDt(currIns);
        prdSgrtInst.setLastUpdBy(currUser);
        prdSgrtInst.setLastUpdDt(currIns);
        prdSgrtInst.setDelFlg(false);
        prdSgrtInst.setEffStartDt(currIns);
        prdSgrtInst.setCrntRecFlg(true);

        return mwPrdSgrtInstRepository.save(prdSgrtInst);

    }

    @Transactional
    public MwPrdSgrtInst updateExistingPrdSgrtInst(ProductSegregateInstallmentDto dto, String currUser) {
        MwPrdSgrtInst exPrdSgrtInst = mwPrdSgrtInstRepository.findOneByPrdSgrtInstSeqAndCrntRecFlg(dto.getPrdSgrtInstSeq(), true);
        Instant currIns = Instant.now();
        if (exPrdSgrtInst == null) {
            return null;
        }

        exPrdSgrtInst.setLastUpdBy(currUser);
        exPrdSgrtInst.setLastUpdDt(currIns);
        exPrdSgrtInst.setPrdSgrtInstSeq(dto.getPrdSgrtInstSeq());
        exPrdSgrtInst.setSgrtEntySeq(dto.getSgrtEntySeq());
        exPrdSgrtInst.setEntyTypStr(dto.getEntyTypStr());
        exPrdSgrtInst.setInstNum(dto.getInstNum());
        return mwPrdSgrtInstRepository.save(exPrdSgrtInst);


    }
}
