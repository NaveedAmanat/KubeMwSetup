package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdBizSectRel;
import com.idev4.setup.dto.ProductBusinessSectorRelationDto;
import com.idev4.setup.repository.MwPrdBizSectRelRepository;
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
public class MwPrdBizSectRelService {

    private final Logger log = LoggerFactory.getLogger(MwPrdBizSectRelService.class);

    private final MwPrdBizSectRelRepository mwPrdBizSectRelRepository;

    public MwPrdBizSectRelService(MwPrdBizSectRelRepository mwPrdBizSectRelRepository) {
        this.mwPrdBizSectRelRepository = mwPrdBizSectRelRepository;
    }

    public MwPrdBizSectRel save(MwPrdBizSectRel mwPrdBizSectRel) {
        log.debug("Request to save MwPrdBizSectRel : {}", mwPrdBizSectRel);
        return mwPrdBizSectRelRepository.save(mwPrdBizSectRel);
    }

    @Transactional(readOnly = true)
    public MwPrdBizSectRel findOne(Long prdBizSectRelSeq) {
        log.debug("Request to get MwPrdBizSectRel : {}", prdBizSectRelSeq);
        return mwPrdBizSectRelRepository.findOneByPrdBizSectRelSeqAndCrntRecFlg(prdBizSectRelSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdBizSectRel> findAllByPrdBizSectRelSeq(Long prdBizSectRelSeq) {
        log.debug("Request to get MwPrdBizSectRel : {}", prdBizSectRelSeq);
        return mwPrdBizSectRelRepository.findAllByPrdBizSectRelSeq(prdBizSectRelSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdBizSectRel> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdBizSectRels : {}");
        return mwPrdBizSectRelRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdBizSectRel> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdBizSectRel : {}");
        return mwPrdBizSectRelRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public boolean delete(Long prdBizSectRelSeq) {

        MwPrdBizSectRel prdBizSectRel = mwPrdBizSectRelRepository.findOneByPrdBizSectRelSeqAndCrntRecFlg(prdBizSectRelSeq, true);
        prdBizSectRel.setCrntRecFlg(false);
        prdBizSectRel.setDelFlg(true);
        prdBizSectRel.setLastUpdDt(Instant.now());
        mwPrdBizSectRelRepository.save(prdBizSectRel);
        return true;
    }

    public MwPrdBizSectRel addNewPrdBizSectRel(ProductBusinessSectorRelationDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_BIZ_SECT_REL_SEQ);
        MwPrdBizSectRel prdBizSectRel = new MwPrdBizSectRel();
        Instant currIns = Instant.now();

        prdBizSectRel.setPrdBizSectRelSeq(seq);
        prdBizSectRel.setBizSectSeq(dto.getBizSectSeq());
        prdBizSectRel.setPrdSeq(dto.getPrdSeq());
        prdBizSectRel.setCrtdBy(currUser);
        prdBizSectRel.setCrtdDt(currIns);
        prdBizSectRel.setLastUpdBy(currUser);
        prdBizSectRel.setLastUpdDt(currIns);
        prdBizSectRel.setDelFlg(false);
        prdBizSectRel.setEffStartDt(currIns);
        prdBizSectRel.setCrntRecFlg(true);

        return mwPrdBizSectRelRepository.save(prdBizSectRel);

    }

    @Transactional
    public MwPrdBizSectRel updateExistingProductBusinessSectorRelation(ProductBusinessSectorRelationDto dto, String currUser) {
        MwPrdBizSectRel exPrdBizSectRel = mwPrdBizSectRelRepository.findOneByPrdBizSectRelSeqAndCrntRecFlg(dto.getPrdBizSectRelSeq(), true);
        Instant currIns = Instant.now();
        if (exPrdBizSectRel == null) {
            return null;
        }

        exPrdBizSectRel.setLastUpdBy(currUser);
        exPrdBizSectRel.setLastUpdDt(currIns);
        exPrdBizSectRel.setBizSectSeq(dto.getBizSectSeq());
        exPrdBizSectRel.setPrdSeq(dto.getPrdSeq());

        return mwPrdBizSectRelRepository.save(exPrdBizSectRel);


    }
}
