package com.idev4.setup.service;

import com.idev4.setup.domain.MwAnswr;
import com.idev4.setup.domain.MwQst;
import com.idev4.setup.dto.AnswerDto;
import com.idev4.setup.dto.MwQstDto;
import com.idev4.setup.dto.QuestionDto;
import com.idev4.setup.repository.MwAnswrRepository;
import com.idev4.setup.repository.MwQstRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MwQst.
 */
@Service
@Transactional
public class MwQstService {

    private final Logger log = LoggerFactory.getLogger(MwQstService.class);

    private final MwQstRepository mwQstRepository;

    private final MwAnswrRepository mwAnswrRepository;

    private final MwRefCdValService mwRefCdValService;

    public MwQstService(MwQstRepository mwQstRepository, MwAnswrRepository mwAnswrRepository, MwRefCdValService mwRefCdValService) {
        this.mwQstRepository = mwQstRepository;
        this.mwAnswrRepository = mwAnswrRepository;
        this.mwRefCdValService = mwRefCdValService;
    }

    /**
     * Save a mwQst.
     *
     * @param mwQst the entity to save
     * @return the persisted entity
     */
    public MwQst save(MwQst mwQst) {
        log.debug("Request to save MwQst : {}", mwQst);
        return mwQstRepository.save(mwQst);
    }

    /**
     * Get all the mwQsts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwQst> findAll(Pageable pageable) {
        log.debug("Request to get all MwQsts");
        return mwQstRepository.findAll(pageable);
    }

    /**
     * Get one mwQst by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwQst findOne(Long id) {
        log.debug("Request to get MwQst : {}", id);
        return mwQstRepository.findOneByQstSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwQst by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long qstSeq) {
        log.debug("Request to delete MwQst : {}", qstSeq);
        MwQst mwQst = (MwQst) mwQstRepository.findOneByQstSeqAndCrntRecFlg(qstSeq, true);
        mwQst.setCrntRecFlg(false);
        mwQst.setDelFlg(true);
        mwQst.setLastUpdDt(Instant.now());
        mwQstRepository.save(mwQst);
    }

    public List<QuestionDto> getAllActiveQuestions() {

        List<MwQst> questions = mwQstRepository.findAllByDelFlgAndCrntRecFlg(false, true);
        List<Long> qstSeqs = questions.stream().map(qst -> qst.getQstSeq()).collect(Collectors.toList());
        List<MwAnswr> answers = mwAnswrRepository.findByQstSeqInAndCrntRecFlg(qstSeqs, true);
        List<QuestionDto> dtos = new ArrayList<QuestionDto>();
        questions.forEach(q -> {
            QuestionDto dto = new QuestionDto();
            dto.questionKey = q.getQstSeq();
            dto.questionString = q.getQstStr();
            List<AnswerDto> ansDtos = new ArrayList<>();
            answers.forEach(a -> {
                if (a.getQstSeq() == q.getQstSeq()) {
                    AnswerDto ansDto = new AnswerDto();
                    ansDto.answerKey = a.getAnswrSeq();
                    ansDto.answerString = a.getAnswrStr();
                    ansDto.answerScore = a.getAnswrScore();
                    ansDtos.add(ansDto);
                }
            });
            dto.answers = ansDtos;
            dtos.add(dto);
        });

        return dtos;
    }

    public List<QuestionDto> getAllActiveQuestionsByType(long type) {

        List<MwQst> questions = mwQstRepository.findAllByDelFlgAndCrntRecFlgAndQstTypKey(false, true, type);
        List<Long> qstSeqs = questions.stream().map(qst -> qst.getQstSeq()).collect(Collectors.toList());
        List<QuestionDto> dtos = new ArrayList<QuestionDto>();

        if (type == 202) {
            List<MwAnswr> answers = mwAnswrRepository.findByQstSeqInAndCrntRecFlg(qstSeqs, true);
            questions.forEach(q -> {
                QuestionDto dto = new QuestionDto();
                dto.questionCategoryKey = q.getQstCtgryKey();
                dto.questionCategory = (mwRefCdValService.findOne(q.getQstCtgryKey())).getRefCdDscr();
                dto.questionKey = q.getQstSeq();
                dto.questionString = q.getQstStr();
                List<AnswerDto> ansDtos = new ArrayList<>();
                answers.forEach(a -> {
                    if (a.getQstSeq() == q.getQstSeq()) {
                        AnswerDto ansDto = new AnswerDto();
                        ansDto.answerKey = a.getAnswrSeq();
                        ansDto.answerString = a.getAnswrStr();
                        ansDto.answerScore = a.getAnswrScore();
                        ansDtos.add(ansDto);
                    }
                });
                dto.answers = ansDtos;
                dtos.add(dto);
            });
        } else if (type == 204) {
            questions.forEach(q -> {
                QuestionDto dto = new QuestionDto();
                dto.questionKey = q.getQstSeq();
                dto.questionCategoryKey = q.getQstCtgryKey();
                dto.questionCategory = (mwRefCdValService.findOne(q.getQstCtgryKey())).getRefCdDscr();
                dto.questionString = q.getQstStr();
                dtos.add(dto);
            });
        }
        return dtos;
    }

    public QuestionDto mapToDto(MwQst qst) {
        QuestionDto dto = new QuestionDto();
        dto.questionString = qst.getQstStr();
        dto.questionKey = qst.getQstSeq();
        return dto;
    }

    @Transactional(readOnly = true)
    public List<MwQst> findAllByQstnrSeq(Long qstnrSeq) {
        log.debug("Request to get all MwQst by qstnrSeq");
        return mwQstRepository.findAllByQstnrSeqAndCrntRecFlg(qstnrSeq, true);
    }

    public MwQst addNewQst(MwQstDto dto, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.QST_SEQ);
        MwQst qst = new MwQst();
        Instant currIns = Instant.now();
        qst.setCrntRecFlg(true);
        qst.setCrtdBy(currUser);
        qst.setCrtdDt(currIns);
        qst.setDelFlg(false);
        qst.setEffStartDt(currIns);
        qst.setLastUpdBy(currUser);
        qst.setLastUpdDt(currIns);
        qst.setQstSeq(seq);
        qst.setQstId(String.format("%04d", seq));
        qst.setQstStr(dto.getQstStr());
        qst.setQstSortOrdr(dto.getQstSortOrdr());
        qst.setQstTypKey(dto.getQstTypKey());
        qst.setQstStsKey(dto.getQstStsKey());
        qst.setQstCtgryKey(dto.getQstCtgryKey());
        qst.setQstnrSeq(dto.getQstnrSeq());

        return mwQstRepository.save(qst);
    }

    @Transactional
    public MwQst UpdateExistingQst(MwQstDto dto, String currUser) {
        MwQst qst = mwQstRepository.findOneByQstSeqAndCrntRecFlg(dto.getQstSeq(), true);
        Instant currIns = Instant.now();
        if (qst == null) {
            return null;
        }

        qst.setLastUpdDt(currIns);
        qst.setLastUpdBy(currUser);
        qst.setQstTypKey(dto.getQstTypKey());
        qst.setQstCtgryKey(dto.getQstCtgryKey());
        qst.setQstStsKey(dto.getQstStsKey());
        qst.setQstSortOrdr(dto.getQstSortOrdr());
        qst.setQstStr(dto.getQstStr());

        return mwQstRepository.save(qst);

    }

}
