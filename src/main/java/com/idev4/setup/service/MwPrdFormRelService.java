package com.idev4.setup.service;

import com.idev4.setup.domain.MwForm;
import com.idev4.setup.domain.MwPrdFormRel;
import com.idev4.setup.dto.ProductFormRelationDto;
import com.idev4.setup.repository.MwFormRepository;
import com.idev4.setup.repository.MwPrdFormRelRepository;
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
public class MwPrdFormRelService {

    private final Logger log = LoggerFactory.getLogger(MwPrdFormRelService.class);

    private final MwPrdFormRelRepository mwPrdFormRelRepository;

    private final MwFormRepository mwFormRepository;

    public MwPrdFormRelService(MwPrdFormRelRepository mwPrdFormRelRepository, MwFormRepository mwFormRepository) {
        this.mwPrdFormRelRepository = mwPrdFormRelRepository;
        this.mwFormRepository = mwFormRepository;
    }

    public MwPrdFormRel save(MwPrdFormRel mwPrdFormRel) {
        log.debug("Request to save MwPrdFormRel : {}", mwPrdFormRel);
        return mwPrdFormRelRepository.save(mwPrdFormRel);
    }

    @Transactional(readOnly = true)
    public MwPrdFormRel findOne(Long prdFormRelSeq) {
        log.debug("Request to get MwPrdFormRel : {}", prdFormRelSeq);
        return mwPrdFormRelRepository.findOneByPrdFormRelSeqAndCrntRecFlg(prdFormRelSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdFormRel> findAllByPrdFormRelSeq(Long prdFormRelSeq) {
        log.debug("Request to get MwPrdFormRel : {}", prdFormRelSeq);
        return mwPrdFormRelRepository.findAllByPrdFormRelSeq(prdFormRelSeq);
    }

    @Transactional(readOnly = true)
    public List<MwPrdFormRel> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdFormRels : {}");
        return mwPrdFormRelRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrdFormRel> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdFormRel : {}");
        List<MwPrdFormRel> list = mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
        list.forEach(form -> {
            MwForm des = mwFormRepository.findOneByFormSeqAndCrntRecFlg(form.getFormSeq(), true);
            if (des != null)
                form.formName = des.getFormNm();
        });

        return list;
    }

    public boolean delete(Long prdFormRelSeq, String user) {
        MwPrdFormRel prdFormRel = mwPrdFormRelRepository.findOneByPrdFormRelSeqAndCrntRecFlg(prdFormRelSeq, true);
        prdFormRel.setCrntRecFlg(false);
        prdFormRel.setLastUpdBy(user);
        prdFormRel.setLastUpdDt(Instant.now());
        prdFormRel.setDelFlg(true);
        mwPrdFormRelRepository.save(prdFormRel);
        return true;
    }

    public MwPrdFormRel addNewPrdFormRel(ProductFormRelationDto dto, String currUser) {

        MwPrdFormRel exRel = mwPrdFormRelRepository.findOneByPrdFormRelSeqAndCrntRecFlg(dto.getPrdFormRelSeq(), true);

        MwPrdFormRel prdFormRel = new MwPrdFormRel();
        if (exRel == null) {
            long seq = SequenceFinder.findNextVal(Sequences.PRD_FORM_REL_SEQ);
            prdFormRel.setPrdFormRelSeq(seq);
        } else {
            exRel.setCrntRecFlg(true);
            exRel.setLastUpdBy(currUser);
            exRel.setLastUpdDt(Instant.now());
            prdFormRel.setPrdFormRelSeq(exRel.getPrdFormRelSeq());
        }
        Instant currIns = Instant.now();
        prdFormRel.setFormSeq(dto.getFormSeq());
        prdFormRel.setPrdSeq(dto.getPrdSeq());
        prdFormRel.setEffStartDt(currIns);
        prdFormRel.setCrtdBy(currUser);
        prdFormRel.setCrtdDt(currIns);
        prdFormRel.setLastUpdBy(currUser);
        prdFormRel.setLastUpdDt(currIns);
        prdFormRel.setDelFlg(false);
        prdFormRel.setCrntRecFlg(true);
        prdFormRel.setFormSortOrdr(dto.getFormSortOrdr());

        return mwPrdFormRelRepository.save(prdFormRel);
    }

    @Transactional
    public MwPrdFormRel updateExistingProductFormRelation(ProductFormRelationDto dto, String currUser) {
        MwPrdFormRel exPrdFormRel = mwPrdFormRelRepository.findOneByPrdFormRelSeqAndCrntRecFlg(dto.getPrdFormRelSeq(), true);
        Instant currIns = Instant.now();

        if (exPrdFormRel == null) {
            return null;
        }
        exPrdFormRel.setLastUpdBy(currUser);
        exPrdFormRel.setLastUpdDt(currIns);
        exPrdFormRel.setFormSeq(dto.getFormSeq());
        exPrdFormRel.setPrdSeq(dto.getPrdSeq());
        return mwPrdFormRelRepository.save(exPrdFormRel);

    }
}
