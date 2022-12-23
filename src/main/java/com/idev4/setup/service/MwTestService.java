package com.idev4.setup.service;

import com.idev4.setup.domain.MwTest;
import com.idev4.setup.repository.MwTestRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing MwTest.
 */
@Service
@Transactional
public class MwTestService {

    private final Logger log = LoggerFactory.getLogger(MwTestService.class);

    private final MwTestRepository mwTestRepository;

    public MwTestService(MwTestRepository mwTestRepository) {
        this.mwTestRepository = mwTestRepository;
    }

    /**
     * Save a mwTest.
     *
     * @param mwTest the entity to save
     * @return the persisted entity
     */
    public MwTest save(MwTest mwTest) {
        log.debug("Request to save MwTest : {}", mwTest);
        return mwTestRepository.save(mwTest);
    }

    /**
     * Get all the mwTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwTest> findAll(Pageable pageable) {
        log.debug("Request to get all MwTests");
        return mwTestRepository.findAll(pageable);
    }


    /**
     * Get one mwTest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwTest findOne(Long id) {
        log.debug("Request to get MwTest : {}", id);
        return mwTestRepository.findOne(id);
    }

    /**
     * Delete the mwTest by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwTest : {}", id);
        mwTestRepository.delete(id);
    }

    public MwTest addNewTestGroup(MwTest test) {
        long seq = SequenceFinder.findNextVal(Sequences.TEST_SEQ);
        test.setRefCdGrpSeq(seq);
        test.setEffStartDt(Instant.now());
        test.setCrntRecFlg(true);
        return mwTestRepository.save(test);
    }

    public MwTest updateTestGroup(MwTest test) {
        MwTest exTest = mwTestRepository.findOneByRefCdGrpSeqAndCrntRecFlg(test.getRefCdGrpSeq(), true);
        if (exTest == null)
            return null;

        exTest.setEffEndDt(Instant.now());
        exTest.setCrntRecFlg(false);

        test.setEffStartDt(Instant.now());
        test.setCrntRecFlg(true);

        mwTestRepository.save(exTest);
        return mwTestRepository.save(test);
    }
}
