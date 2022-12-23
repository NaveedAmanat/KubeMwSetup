package com.idev4.setup.service;

import com.idev4.setup.domain.MwQstnr;
import com.idev4.setup.dto.QuestionnaireDto;
import com.idev4.setup.repository.MwQstnrRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwQstnr.
 */
@Service
@Transactional
public class MwQstnrService {

    private final Logger log = LoggerFactory.getLogger(MwQstnrService.class);

    private final MwQstnrRepository mwQstnrRepository;

    public MwQstnrService(MwQstnrRepository mwQstnrRepository) {
        this.mwQstnrRepository = mwQstnrRepository;
    }

    /**
     * Save a mwQstnr.
     *
     * @param mwQstnr the entity to save
     * @return the persisted entity
     */
    public MwQstnr save(MwQstnr mwQstnr) {
        log.debug("Request to save MwQstnr : {}", mwQstnr);
        return mwQstnrRepository.save(mwQstnr);
    }

    /**
     * Get all the mwQstnrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwQstnr> findAll(Pageable pageable) {
        log.debug("Request to get all MwQstnr");
        return mwQstnrRepository.findAll(pageable);
    }

    /**
     * Get one mwQstnr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwQstnr findOne(Long qstnrSeq) {
        log.debug("Request to get MwQstnr : {}", qstnrSeq);
        return mwQstnrRepository.findOneByQstnrSeqAndCrntRecFlg(qstnrSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwQstnr> findAllHistory(Long qstnrSeq) {
        log.debug("Request to get MwQstnr : {}", qstnrSeq);
        return mwQstnrRepository.findAllByQstnrSeq(qstnrSeq);
    }

    /**
     * Delete the mwQstnr by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long qstnrSeq) {
        log.debug("Request to delete MwQstnr : {}", qstnrSeq);
        MwQstnr qstnr = mwQstnrRepository.findOneByQstnrSeqAndCrntRecFlg(qstnrSeq, true);
        qstnr.setCrntRecFlg(false);
        qstnr.setDelFlg(true);
        qstnr.setLastUpdDt(Instant.now());
        mwQstnrRepository.save(qstnr);
    }

    @Transactional(readOnly = true)
    public List<MwQstnr> findAllByCurrentRecord() {
        log.debug("Request to get all MwQstnr");
        return mwQstnrRepository.findAllByCrntRecFlg(true);
    }

    public MwQstnr addNewQstnr(QuestionnaireDto dto, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.QSTNR_SEQ);
        MwQstnr qstnr = new MwQstnr();
        Instant currIns = Instant.now();
        qstnr.setQstnrSeq(seq);
        qstnr.setCrntRecFlg(true);
        qstnr.setCrtdBy(currUser);
        qstnr.setCrtdDt(currIns);
        qstnr.setDelFlg(false);
        qstnr.setEffStartDt(currIns);
        qstnr.setLastUpdBy(currUser);
        qstnr.setLastUpdDt(currIns);
        qstnr.setQstnrId(String.format("%04d", seq));
        qstnr.setQstnrNm(dto.getQstnrNm());
        qstnr.setQstnrStsKey(dto.getQstnrStsKey());

        return mwQstnrRepository.save(qstnr);
    }

    @Transactional
    public MwQstnr UpdateExistingQstnr(QuestionnaireDto dto, String currUser) {
        MwQstnr qstnr = mwQstnrRepository.findOneByQstnrSeqAndCrntRecFlg(dto.getQstnrSeq(), true);
        Instant currIns = Instant.now();
        if (qstnr == null) {
            return null;
        }

        qstnr.setLastUpdDt(currIns);
        qstnr.setLastUpdBy(currUser);
        qstnr.setQstnrNm(dto.getQstnrNm());
        qstnr.setQstnrStsKey(dto.getQstnrStsKey());

        return mwQstnrRepository.save(qstnr);

    }
}
