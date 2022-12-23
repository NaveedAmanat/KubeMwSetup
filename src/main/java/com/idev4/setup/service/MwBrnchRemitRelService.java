package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnchRemitRel;
import com.idev4.setup.dto.BranchRemitRelationDto;
import com.idev4.setup.repository.MwBrnchRemitRelRepository;
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
public class MwBrnchRemitRelService {

    private final Logger log = LoggerFactory.getLogger(MwBrnchRemitRelService.class);

    private final MwBrnchRemitRelRepository mwBrnchRemitRelRepository;

    public MwBrnchRemitRelService(MwBrnchRemitRelRepository mwBrnchRemitRelRepository) {
        this.mwBrnchRemitRelRepository = mwBrnchRemitRelRepository;
    }

    public MwBrnchRemitRel save(MwBrnchRemitRel mwBrnchRemitRel) {
        log.debug("Request to save MwBrnchRemitRel : {}", mwBrnchRemitRel);
        return mwBrnchRemitRelRepository.save(mwBrnchRemitRel);
    }

    @Transactional(readOnly = true)
    public MwBrnchRemitRel findOne(Long id) {
        log.debug("Request to get MwBrnchRemitRel : {}", id);
        return mwBrnchRemitRelRepository.findOneByBrnchRemitSeqAndCrntRecFlg(id, true);
    }

    public boolean delete(Long id) {

        MwBrnchRemitRel mwBrnchRemitRel = mwBrnchRemitRelRepository.findOneByBrnchRemitSeqAndCrntRecFlg(id, true);
        mwBrnchRemitRel.setCrntRecFlg(false);
        mwBrnchRemitRel.setDelFlg(true);
        mwBrnchRemitRel.setLastUpdDt(Instant.now());
        mwBrnchRemitRelRepository.save(mwBrnchRemitRel);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwBrnchRemitRel> findAllByBranchSeqCurrentRecord(Long brnchSeq) {
        log.debug("Request to get all MwBrnchRemitRel");
        return mwBrnchRemitRelRepository.findAllByBrnchSeqAndCrntRecFlg(brnchSeq, true);
    }

    public MwBrnchRemitRel addNewMwBrnchRemitRel(BranchRemitRelationDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BRNCH_REMIT_SEQ);
        MwBrnchRemitRel mwBrnchRemitRel = new MwBrnchRemitRel();
        Instant currIns = Instant.now();

        mwBrnchRemitRel.setBrnchRemitSeq(seq);
        mwBrnchRemitRel.setBrnchSeq(dto.getBrnchSeq());
        mwBrnchRemitRel.setPymtTypSeq(dto.getPymtTypSeq());
        mwBrnchRemitRel.setCrtdBy(currUser);
        mwBrnchRemitRel.setCrtdDt(currIns);
        mwBrnchRemitRel.setLastUpdBy(currUser);
        mwBrnchRemitRel.setLastUpdDt(currIns);
        mwBrnchRemitRel.setDelFlg(false);
        mwBrnchRemitRel.setEffStartDt(currIns);
        mwBrnchRemitRel.setCrntRecFlg(true);
        //Added by Areeba
        mwBrnchRemitRel.setRemitFlg(true);
        mwBrnchRemitRel.setBankBrnch(dto.getRemitBankBrnch());
        mwBrnchRemitRel.setIban(dto.getRemitIban());
        //Ended by Areeba

        return mwBrnchRemitRelRepository.save(mwBrnchRemitRel);

    }

    @Transactional
    public MwBrnchRemitRel updateExistingMwBrnchRemitRel(BranchRemitRelationDto dto, String currUser) {

        // Edited by Areeba - 27-05-2022
        MwBrnchRemitRel mwBrnchRemitRel = mwBrnchRemitRelRepository.findOneByBrnchSeqAndCrntRecFlg(dto.getBrnchSeq(), true);
        Instant currIns = Instant.now();
        if (mwBrnchRemitRel == null) {
            return null;
        }

        mwBrnchRemitRel.setLastUpdDt(currIns);
        mwBrnchRemitRel.setLastUpdBy(currUser);
        mwBrnchRemitRel.setBrnchSeq(dto.getBrnchSeq());
        //Added by Areeba
        mwBrnchRemitRel.setPymtTypSeq(dto.getPymtTypSeq());
        mwBrnchRemitRel.setBankBrnch(dto.getRemitBankBrnch());
        mwBrnchRemitRel.setIban(dto.getRemitIban());
        //Ended by Areeba

        return mwBrnchRemitRelRepository.save(mwBrnchRemitRel);

    }
}
