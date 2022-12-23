package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdDocRel;
import com.idev4.setup.dto.ProductDocumentRelationDto;
import com.idev4.setup.repository.MwPrdDocRelRepository;
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
public class MwPrdDocRelService {

    private final Logger log = LoggerFactory.getLogger(MwPrdDocRelService.class);

    private final MwPrdDocRelRepository mwPrdDocRelRepository;

    public MwPrdDocRelService(MwPrdDocRelRepository mwPrdDocRelRepository) {
        this.mwPrdDocRelRepository = mwPrdDocRelRepository;
    }

    public MwPrdDocRel save(MwPrdDocRel mwPrdDocRel) {
        log.debug("Request to save MwPrdDocRel : {}", mwPrdDocRel);
        return mwPrdDocRelRepository.save(mwPrdDocRel);
    }

    @Transactional(readOnly = true)
    public MwPrdDocRel findOne(Long prdDocRelSeq) {
        log.debug("Request to get MwPrdDocRel : {}", prdDocRelSeq);
        return mwPrdDocRelRepository.findOneByPrdDocRelSeqAndCrntRecFlg(prdDocRelSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdDocRel> findAllByPrdDocRelSeq(Long prdDocRelSeq) {
        log.debug("Request to get MwPrdDocRel : {}", prdDocRelSeq);
        return mwPrdDocRelRepository.findAllByPrdDocRelSeq(prdDocRelSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdDocRel> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdDocRels : {}");
        return mwPrdDocRelRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdDocRel> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdDocRel : {}");
        return mwPrdDocRelRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public boolean delete(Long prdDocRelSeq, String user) {

        MwPrdDocRel prdDocRel = mwPrdDocRelRepository.findOneByPrdDocRelSeqAndCrntRecFlg(prdDocRelSeq, true);
        prdDocRel.setCrntRecFlg(false);
        prdDocRel.setLastUpdBy(user);
        prdDocRel.setLastUpdDt(Instant.now());
        prdDocRel.setDelFlg(true);
        mwPrdDocRelRepository.save(prdDocRel);
        return true;
    }

    public MwPrdDocRel addNewPrdDocRel(ProductDocumentRelationDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_DOC_REL_SEQ);
        MwPrdDocRel prdDocRel = new MwPrdDocRel();
        Instant currIns = Instant.now();

        prdDocRel.setPrdDocRelSeq(seq);
        prdDocRel.setDocSeq(dto.getDocSeq());
        prdDocRel.setPrdSeq(dto.getPrdSeq());
        prdDocRel.setEffStartDt(currIns);
        prdDocRel.setCrntRecFlg(true);
        prdDocRel.setMndtryFlg(false);
        prdDocRel.setCrtdBy(currUser);
        prdDocRel.setCrtdDt(currIns);
        prdDocRel.setLastUpdBy(currUser);
        prdDocRel.setLastUpdDt(currIns);
        prdDocRel.setDelFlg(false);
        return mwPrdDocRelRepository.save(prdDocRel);

    }

    @Transactional
    public MwPrdDocRel updateExistingProductDocumentRelation(ProductDocumentRelationDto dto, String currUser) {
        MwPrdDocRel exPrdDocRel = mwPrdDocRelRepository.findOneByPrdDocRelSeqAndCrntRecFlg(dto.getPrdDocRelSeq(), true);

        if (exPrdDocRel == null) {
            return null;
        }
        Instant currIns = Instant.now();

        exPrdDocRel.setLastUpdDt(currIns);
        exPrdDocRel.setLastUpdBy(currUser);
        exPrdDocRel.setDocSeq(dto.getDocSeq());
        exPrdDocRel.setPrdSeq(dto.getPrdSeq());
        exPrdDocRel.setMndtryFlg(dto.getMndtryFlg());
        return mwPrdDocRelRepository.save(exPrdDocRel);

    }
}
