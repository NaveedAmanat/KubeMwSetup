package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdPpalLmt;
import com.idev4.setup.dto.ProductPrincipalLimitDto;
import com.idev4.setup.repository.MwPrdPpalLmtRepository;
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
public class MwPrdPpalLmtService {

    private final Logger log = LoggerFactory.getLogger(MwPrdPpalLmtService.class);

    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;

    public MwPrdPpalLmtService(MwPrdPpalLmtRepository mwPrdPpalLmtRepository) {
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
    }

    public MwPrdPpalLmt save(MwPrdPpalLmt mwPrdPpalLmt) {
        log.debug("Request to save MwPrdPpalLmt : {}", mwPrdPpalLmt);
        return mwPrdPpalLmtRepository.save(mwPrdPpalLmt);
    }

    @Transactional(readOnly = true)
    public MwPrdPpalLmt findOne(Long prdPpalLmtSeq) {
        log.debug("Request to get MwPrdPpalLmt : {}", prdPpalLmtSeq);
        return mwPrdPpalLmtRepository.findOneByPrdPpalLmtSeqAndCrntRecFlg(prdPpalLmtSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdPpalLmt> findAllByPrdPpalLmtSeq(Long prdPpalLmtSeq) {
        log.debug("Request to get MwPrdPpalLmt : {}", prdPpalLmtSeq);
        return mwPrdPpalLmtRepository.findAllByPrdPpalLmtSeq(prdPpalLmtSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdPpalLmt> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdPpalLmts : {}");
        return mwPrdPpalLmtRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdPpalLmt> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdPpalLmt : {}");
        return mwPrdPpalLmtRepository.findAllByPrdSeqAndCrntRecFlgOrderByPrdPpalLmtSeq(prdSeq, true);
    }

    public boolean delete(Long prdPpalLmtSeq) {

        MwPrdPpalLmt prdPpalLmt = mwPrdPpalLmtRepository.findOneByPrdPpalLmtSeqAndCrntRecFlg(prdPpalLmtSeq, true);
        prdPpalLmt.setCrntRecFlg(false);
        prdPpalLmt.setDelFlg(true);
        prdPpalLmt.setLastUpdDt(Instant.now());
        mwPrdPpalLmtRepository.save(prdPpalLmt);
        return true;
    }

    public MwPrdPpalLmt addNewPrdPpalLmt(ProductPrincipalLimitDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_PPAL_LMT_SEQ);
        MwPrdPpalLmt prdPpalLmt = new MwPrdPpalLmt();
        Instant currIns = Instant.now();

        prdPpalLmt.setPrdPpalLmtSeq(seq);
        prdPpalLmt.setPrdSeq(dto.getPrdSeq());
        prdPpalLmt.setRulSeq(dto.getRulSeq());
        prdPpalLmt.setMinAmt(dto.getMinAmt());
        prdPpalLmt.setMaxAmt(dto.getMaxAmt());
        prdPpalLmt.setSgrtInstNum(dto.getSgrtInstNum());
        prdPpalLmt.setCrtdBy(currUser);
        prdPpalLmt.setCrtdDt(currIns);
        prdPpalLmt.setLastUpdBy(currUser);
        prdPpalLmt.setLastUpdDt(currIns);
        prdPpalLmt.setDelFlg(false);
        prdPpalLmt.setEffStartDt(currIns);
        prdPpalLmt.setCrntRecFlg(true);

        return mwPrdPpalLmtRepository.save(prdPpalLmt);

    }

    @Transactional
    public MwPrdPpalLmt updateExistingProductPrincipalLimits(ProductPrincipalLimitDto dto, String currUser) {
        MwPrdPpalLmt prdPpalLmt = mwPrdPpalLmtRepository.findOneByPrdPpalLmtSeqAndCrntRecFlg(dto.getPrdPpalLmtSeq(), true);
        Instant currIns = Instant.now();
        if (prdPpalLmt == null) {
            return null;
        }

        prdPpalLmt.setLastUpdDt(currIns);
        prdPpalLmt.setLastUpdBy(currUser);
        prdPpalLmt.setMinAmt(dto.getMinAmt());
        prdPpalLmt.setMaxAmt(dto.getMaxAmt());
        prdPpalLmt.setSgrtInstNum(dto.getSgrtInstNum());
        prdPpalLmt.setPrdPpalLmtSeq(dto.getPrdPpalLmtSeq());
        prdPpalLmt.setRulSeq(dto.getRulSeq());

        return mwPrdPpalLmtRepository.save(prdPpalLmt);


    }
}
