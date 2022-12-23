package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdGrp;
import com.idev4.setup.dto.ProductGroupDto;
import com.idev4.setup.repository.MwPrdGrpRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwPrdGrp.
 */
@Service
@Transactional
public class MwPrdGrpService {

    private final Logger log = LoggerFactory.getLogger(MwPrdGrpService.class);

    private final MwPrdGrpRepository mwPrdGrpRepository;

    public MwPrdGrpService(MwPrdGrpRepository mwPrdGrpRepository) {
        this.mwPrdGrpRepository = mwPrdGrpRepository;
    }

    public MwPrdGrp save(ProductGroupDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_GRP_SEQ);
        Instant currIns = Instant.now();
        MwPrdGrp mwPrdGrp = new MwPrdGrp();

        mwPrdGrp.setPrdGrpSeq(seq);
        mwPrdGrp.setPrdGrpId(String.format("%04d", seq));
        mwPrdGrp.setPrdGrpNm(dto.getPrdGrpNm());
        mwPrdGrp.setPrdGrpSts(dto.getPrdGrpSts());
        mwPrdGrp.setCrtdBy(currUser);
        mwPrdGrp.setCrtdDt(currIns);
        mwPrdGrp.setLastUpdBy(currUser);
        mwPrdGrp.setLastUpdDt(currIns);
        mwPrdGrp.setDelFlg(false);
        mwPrdGrp.setEffStartDt(currIns);
        mwPrdGrp.setCrntRecFlg(true);

        return mwPrdGrpRepository.save(mwPrdGrp);
    }

    @Transactional(readOnly = true)
    public List<MwPrdGrp> findAllProductGroups() {
        log.debug("Request to get all MwPrdGrps");
        return mwPrdGrpRepository.findAllByCrntRecFlgOrderByPrdGrpSeq(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdGrp> findAllByPrdGrpSeq(Long prdGrpSeq) {
        log.debug("Request to get all MwPrdGrps");
        return mwPrdGrpRepository.findAllByPrdGrpSeq(prdGrpSeq);
    }

    @Transactional(readOnly = true)
    public MwPrdGrp findOne(Long prdGrpSeq) {
        log.debug("Request to get MwPrdGrp : {}", prdGrpSeq);
        return mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(prdGrpSeq, true);
    }

    public Boolean delete(Long prdGrpSeq) {
        log.debug("Request to delete MwGrpPrd : {}", prdGrpSeq);
        MwPrdGrp exMwPrdGrp = mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(prdGrpSeq, true);
        if (exMwPrdGrp == null) {
            return false;
        }
        exMwPrdGrp.setCrntRecFlg(false);
        exMwPrdGrp.setDelFlg(true);
        exMwPrdGrp.setLastUpdDt(Instant.now());

        mwPrdGrpRepository.save(exMwPrdGrp);
        return true;
    }

    @Transactional
    public MwPrdGrp updateExistingProductGroup(ProductGroupDto dto, String currUser) {

        MwPrdGrp prdGrp = mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(dto.getPrdGrpSeq(), true);
        Instant currIns = Instant.now();
        if (prdGrp == null) {
            return null;
        }

        prdGrp.setLastUpdDt(currIns);
        prdGrp.setLastUpdBy(currUser);
        prdGrp.setPrdGrpNm(dto.getPrdGrpNm());
        prdGrp.setPrdGrpSts(dto.getPrdGrpSts());

        return mwPrdGrpRepository.save(prdGrp);

    }

    @Transactional(readOnly = true)
    public List<MwPrdGrp> findActivePrdGrpForBrnch(Long brnchSeq) {
        log.debug("Request to get all Active MwPrdGrps");
        return mwPrdGrpRepository.findActiveMwPrdGrpForBranch(brnchSeq);
    }
}
