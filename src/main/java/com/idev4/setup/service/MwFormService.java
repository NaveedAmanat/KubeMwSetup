package com.idev4.setup.service;

import com.idev4.setup.domain.MwForm;
import com.idev4.setup.dto.FormDto;
import com.idev4.setup.repository.MwFormRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Service Implementation for managing MwForm.
 */
@Service
@Transactional
public class MwFormService {

    private final Logger log = LoggerFactory.getLogger(MwFormService.class);

    private final MwFormRepository mwFormRepository;

    public MwFormService(MwFormRepository mwFormRepository) {
        this.mwFormRepository = mwFormRepository;
    }

    public MwForm save(MwForm mwForm) {
        log.debug("Request to save MwForm : {}", mwForm);
        return mwFormRepository.save(mwForm);
    }

    @Transactional(readOnly = true)
    public MwForm findOne(Long id) {
        log.debug("Request to get MwForm : {}", id);
        return mwFormRepository.findOneByFormSeqAndCrntRecFlg(id, true);
    }

    @Transactional(readOnly = true)
    public List<MwForm> findAllHistory(Long id) {
        log.debug("Request to get MwForm : {}", id);
        return mwFormRepository.findAllByFormSeq(id);
    }

    public boolean delete(Long id) {

        MwForm form = mwFormRepository.findOneByFormSeqAndCrntRecFlg(id, true);
        form.setCrntRecFlg(false);
        form.setDelFlg(true);
        form.setLastUpdDt(Instant.now());
        mwFormRepository.save(form);
        return true;
    }

    @Transactional(readOnly = true)
    public List<MwForm> findAllByCurrentRecord() {
        log.debug("Request to get all MwForm");
        return mwFormRepository.findAllByCrntRecFlg(true);
    }

    public MwForm addNewFrom(FormDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.FORM_SEQ);
        MwForm form = new MwForm();
        Instant currIns = Instant.now();

        form.setFormSeq(seq);
        form.setFormId(String.format("%04d", seq));
        form.setFormNm(dto.getFormNm());
        form.setFormUrl(dto.getFormUrl());
        form.setFormCls(dto.getFormCls());
        form.setFormStsKey(dto.getFormStsKey());
        // form.setFormSortOrdr(dto.getFormSortOrdr());
        form.setCrtdBy(currUser);
        form.setCrtdDt(currIns);
        form.setLastUpdBy(currUser);
        form.setLastUpdDt(currIns);
        form.setDelFlg(false);
        form.setEffStartDt(currIns);
        form.setCrntRecFlg(true);

        return mwFormRepository.save(form);

    }

    @Transactional
    public MwForm updateExistingForm(FormDto dto, String currUser) {
        MwForm exForm = mwFormRepository.findOneByFormSeqAndCrntRecFlg(dto.getFormSeq(), true);
        Instant currIns = Instant.now();
        if (exForm == null) {
            return null;
        }

        exForm.setLastUpdBy(currUser);
        exForm.setLastUpdDt(currIns);
        exForm.setFormNm(dto.getFormNm());
        exForm.setFormUrl(dto.getFormUrl());
        exForm.setFormCls(dto.getFormCls());
        exForm.setFormStsKey(dto.getFormStsKey());
        return mwFormRepository.save(exForm);


    }
}
