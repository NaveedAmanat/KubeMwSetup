package com.idev4.setup.service;

import com.idev4.setup.domain.MwTags;
import com.idev4.setup.dto.TagsDto;
import com.idev4.setup.repository.MwTagsRepository;
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
 * Service Implementation for managing MwTags.
 */
@Service
@Transactional
public class MwTagsService {

    private final Logger log = LoggerFactory.getLogger(MwTagsService.class);

    private final MwTagsRepository mwTagsRepository;

    public MwTagsService(MwTagsRepository mwTagsRepository) {
        this.mwTagsRepository = mwTagsRepository;
    }

    /**
     * Save a mwTags.
     *
     * @param mwTags the entity to save
     * @return the persisted entity
     */
    public MwTags save(MwTags mwTags) {
        log.debug("Request to save MwTags : {}", mwTags);
        return mwTagsRepository.save(mwTags);
    }

    /**
     * Get all the mwTagss.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwTags> findAll(Pageable pageable) {
        log.debug("Request to get all MwTags");
        return mwTagsRepository.findAll(pageable);
    }

    /**
     * Get one mwTags by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwTags findOne(Long tagsSeq) {
        log.debug("Request to get MwTags : {}", tagsSeq);
        return mwTagsRepository.findOneByTagsSeqAndCrntRecFlg(tagsSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwTags> findAllHistory(Long tagsSeq) {
        log.debug("Request to get MwTags : {}", tagsSeq);
        return mwTagsRepository.findAllByTagsSeq(tagsSeq);
    }

    /**
     * Delete the mwTags by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long tagsSeq) {
        log.debug("Request to delete MwTags : {}", tagsSeq);
        MwTags tags = mwTagsRepository.findOneByTagsSeqAndCrntRecFlg(tagsSeq, true);
        tags.setCrntRecFlg(false);
        tags.setDelFlg(true);
        tags.setLastUpdDt(Instant.now());
        mwTagsRepository.save(tags);
    }

    @Transactional(readOnly = true)
    public List<MwTags> findAllByCurrentRecord() {
        log.debug("Request to get all MwTags");
        return mwTagsRepository.findAllByCrntRecFlg(true);
    }

    public MwTags addNewTags(TagsDto dto, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.TAGS_SEQ);
        MwTags tags = new MwTags();
        Instant currIns = Instant.now();
        tags.setTagsSeq(seq);
        tags.setCrntRecFlg(true);
        tags.setCrtdBy(currUser);
        tags.setCrtdDt(currIns);
        tags.setDelFlg(false);
        tags.setEffStartDt(currIns);
        tags.setLastUpdBy(currUser);
        tags.setLastUpdDt(currIns);
        tags.setTagId(String.format("%04d", seq));
        tags.setTagNm(dto.getTagNm());
        tags.setTagDscr(dto.getTagDscr());
        tags.setSvrtyFlgKey(dto.getSvrtyFlgKey());

        return mwTagsRepository.save(tags);
    }

    @Transactional
    public MwTags UpdateExistingTags(TagsDto dto, String currUser) {
        log.debug("Request to Update MwTags : {}", dto);

        MwTags tags = mwTagsRepository.findOneByTagsSeqAndCrntRecFlg(dto.getTagsSeq(), true);
        Instant currIns = Instant.now();
        if (tags == null) {
            return null;
        }
        tags.setLastUpdDt(currIns);
        tags.setLastUpdBy(currUser);
        tags.setTagNm(dto.getTagNm());
        tags.setTagDscr(dto.getTagDscr());
        tags.setSvrtyFlgKey(dto.getSvrtyFlgKey());
        return mwTagsRepository.save(tags);

    }
}
