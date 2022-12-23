package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnchAcctSet;
import com.idev4.setup.dto.BranchAcctDto;
import com.idev4.setup.repository.MwBrnchAcctSetRepository;
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
 * Service Implementation for managing MwBrnchAcctSet.
 */
@Service
@Transactional
public class MwBrnchAcctSetService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchAcctSetService.class);

    private final MwBrnchAcctSetRepository mwBrnchAcctSetRepository;

    public MwBrnchAcctSetService(MwBrnchAcctSetRepository mwBrnchAcctSetRepository) {
        this.mwBrnchAcctSetRepository = mwBrnchAcctSetRepository;
    }

    /**
     * Save a mwBrnchAcctSet.
     *
     * @param mwBrnchAcctSet the entity to save
     * @return the persisted entity
     */
    public MwBrnchAcctSet save(MwBrnchAcctSet mwBrnchAcctSet) {
        log.debug("Request to save MwBrnchAcctSet : {}", mwBrnchAcctSet);
        return mwBrnchAcctSetRepository.save(mwBrnchAcctSet);
    }

    /**
     * Get all the mwBrnchAcctSets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwBrnchAcctSet> findAll(Pageable pageable) {
        log.debug("Request to get all MwBrnchAcctSets");
        return mwBrnchAcctSetRepository.findAll(pageable);
    }

    /**
     * Get one mwBrnchAcctSet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwBrnchAcctSet findOne(Long id) {
        log.debug("Request to get MwBrnchAcctSet : {}", id);
        return mwBrnchAcctSetRepository.findOne(id);
    }

    /**
     * Delete the mwBrnchAcctSet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwBrnchAcctSet : {}", id);
        mwBrnchAcctSetRepository.delete(id);
    }

    public MwBrnchAcctSet findOneByBranchSeq(long branchSeq) {
        return mwBrnchAcctSetRepository.findOneByBrnchSeqAndCrntRecFlg(branchSeq, true);
    }

    public long addNewBankAccToBranch(BranchAcctDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwBrnchAcctSet acc = new MwBrnchAcctSet();
        long seq = SequenceFinder.findNextVal(Sequences.BRNCH_ACC_SET_SEQ);
        acc.brnchAcctSetSeq(seq);
        acc.setAcctNm(dto.accTitle);
        acc.setAcctNum(dto.accNo);
        acc.setBankBrnch(dto.bankBranch);
        acc.setBankName(dto.bankName);
        acc.setBrnchSeq(dto.branchSeq);
        acc.setCrntRecFlg(true);
        acc.setCrtdBy(currUser);
        acc.setCrtdDt(currIns);
        acc.setDelFlg(false);
        acc.setEffStartDt(currIns);
        acc.setIban(dto.ibanNo);
        acc.setLastUpdBy(currUser);
        acc.setLastUpdDt(currIns);
        //Added by Areeba
        acc.setOnlnFlg(true);
        acc.setBankCode(dto.bankCode);
        acc.setIbftBankCode(dto.ibftBankCode);
        //Ended by Areeba

        return mwBrnchAcctSetRepository.save(acc).getBrnchAcctSetSeq();
    }

    public long updateBankAccToBranch(BranchAcctDto dto, String currUser) {
        MwBrnchAcctSet mwAcc = mwBrnchAcctSetRepository.findOneByBrnchSeqAndCrntRecFlg(dto.branchSeq, true);
        Instant currIns = Instant.now();
        if (mwAcc == null)
            return 0;

        mwAcc.setLastUpdDt(currIns);
        mwAcc.setLastUpdBy(currUser);
        mwAcc.setAcctNm(dto.accTitle);
        mwAcc.setAcctNum(dto.accNo);
        mwAcc.setBankBrnch(dto.bankBranch);
        mwAcc.setBankName(dto.bankName);
        mwAcc.setBrnchSeq(dto.branchSeq);
        mwAcc.setIban(dto.ibanNo);
        //Added by Areeba
        mwAcc.setBankCode(dto.bankCode);
        mwAcc.setIbftBankCode(dto.ibftBankCode);
        //Ended by Areeba

        return mwBrnchAcctSetRepository.save(mwAcc).getBrnchAcctSetSeq();

    }
}
