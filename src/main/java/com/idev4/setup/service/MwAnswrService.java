package com.idev4.setup.service;


import com.idev4.setup.domain.MwAnswr;
import com.idev4.setup.repository.MwAnswrRepository;
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


@Service
@Transactional
public class MwAnswrService {

    private final Logger log = LoggerFactory.getLogger(MwAnswrService.class);

    private final MwAnswrRepository mwAnswrRepository;

    public MwAnswrService(MwAnswrRepository mwAnswrRepository) {
        this.mwAnswrRepository = mwAnswrRepository;
    }

    /**
     * Save a mwAnswr.
     *
     * @param mwAnswr the entity to save
     * @return the persisted entity
     */
    public MwAnswr save(MwAnswr mwAnswr) {
        log.debug("Request to save MwAnswr : {}", mwAnswr);
        return mwAnswrRepository.save(mwAnswr);
    }

    /**
     * Get all the mwAnswrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwAnswr> findAll(Pageable pageable) {
        log.debug("Request to get all MwAnswrs");
        return mwAnswrRepository.findAll(pageable);
    }


    /**
     * Get one mwAnswr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwAnswr findOne(Long id) {
        log.debug("Request to get MwAnswr : {}", id);
        return (MwAnswr) mwAnswrRepository.findOneByAnswrSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwAnswr by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long answrSeq) {
        log.debug("Request to delete MwAnswr : {}", answrSeq);
        MwAnswr mwAnswr = (MwAnswr) mwAnswrRepository.findOneByAnswrSeqAndCrntRecFlg(answrSeq, true);
        mwAnswr.setCrntRecFlg(false);
        mwAnswr.setDelFlg(true);
        mwAnswrRepository.save(mwAnswr);
    }

    @Transactional(readOnly = true)
    public List<MwAnswr> findAllByQstSeq(Long qstSeq) {
        return mwAnswrRepository.findAllByQstSeqAndCrntRecFlg(qstSeq, true);
    }

    public MwAnswr addNewAnswr(MwAnswr mwAnswr, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.ANSWR_SEQ);
        MwAnswr answr = new MwAnswr();
        Instant currIns = Instant.now();
        answr.setCrntRecFlg(true);
        answr.setCrtdBy(currUser);
        answr.setCrtdDt(currIns);
        answr.setDelFlg(false);
        answr.setEffStartDt(currIns);
        answr.setLastUpdBy(currUser);
        answr.setLastUpdDt(currIns);
        answr.setAnswrSeq(seq);
        answr.setAnswrId(String.format("%04d", seq));
        answr.setAnswrStr(mwAnswr.getAnswrStr());
        answr.setAnswrScore(mwAnswr.getAnswrScore());
        answr.setAnswrStsKey(mwAnswr.getAnswrStsKey());
        answr.setQstSeq(mwAnswr.getQstSeq());

        return mwAnswrRepository.save(answr);
    }

    @Transactional
    public MwAnswr UpdateExistingAnswr(MwAnswr mwAnswr, String currUser) {
        MwAnswr mwAnswer = mwAnswrRepository.findOneByAnswrSeqAndCrntRecFlg(mwAnswr.getAnswrSeq(), true);
        Instant currIns = Instant.now();
        if (mwAnswer == null) {
            return null;
        }

        mwAnswer.setLastUpdDt(currIns);
        mwAnswer.setLastUpdBy(currUser);
        mwAnswer.setAnswrStr(mwAnswr.getAnswrStr());
        mwAnswer.setAnswrScore(mwAnswr.getAnswrScore());
        mwAnswer.setAnswrStsKey(mwAnswr.getAnswrStsKey());

        return mwAnswrRepository.save(mwAnswer);


    }


    /*public List<QuestionDto> getAllActiveQuestions(){
    	return mwAnswrRepository.findAllByDelFlgAndCrntRecFlg(false, true).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public QuestionDto mapToDto(MwAnswr ans) {
    	QuestionDto dto = new QuestionDto();
    	dto.answerString = ans.getAnswrStr();
    	dto.answerKey = ans.getAnswrSeq();
    	return dto;
    }*/
}
