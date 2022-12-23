package com.idev4.setup.service;

import com.idev4.setup.domain.MwDoc;
import com.idev4.setup.dto.DocumentDto;
import com.idev4.setup.repository.MwDocRepository;
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
public class MwDocService {

    private final Logger log = LoggerFactory.getLogger(MwDocService.class);

    private final MwDocRepository mwDocRepository;

    public MwDocService(MwDocRepository mwDocRepository) {
        this.mwDocRepository = mwDocRepository;
    }

    public MwDoc save(MwDoc mwDoc) {
        log.debug("Request to save MwDoc : {}", mwDoc);
        return mwDocRepository.save(mwDoc);
    }

    @Transactional(readOnly = true)
    public MwDoc findOne(Long docSeq) {
        log.debug("Request to get MwDoc : {}", docSeq);
        return mwDocRepository.findOneByDocSeqAndCrntRecFlg(docSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwDoc> findAllByDocSeq(Long docSeq) {
        log.debug("Request to get MwDoc : {}", docSeq);
        return mwDocRepository.findAllByDocSeq(docSeq);
    }

    @Transactional(readOnly = true)
    public List<MwDoc> findAllByCrntRecFlg() {
        log.debug("Request to get All MwDocs : {}");
        return mwDocRepository.findAllByCrntRecFlg(true);
    }

    public boolean delete(Long docSeq) {

        MwDoc doc = mwDocRepository.findOneByDocSeqAndCrntRecFlg(docSeq, true);
        doc.setCrntRecFlg(false);
        doc.setDelFlg(true);
        doc.setLastUpdDt(Instant.now());
        mwDocRepository.save(doc);
        return true;
    }

    public MwDoc addNewDoc(DocumentDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.DOC_SEQ);
        MwDoc doc = new MwDoc();
        Instant currIns = Instant.now();

        doc.setDocSeq(seq);
        doc.setDocId(String.format("%04d", seq));
        doc.setDocNm(dto.getDocNm());
        doc.setDocCtgryKey(dto.getDocCtgryKey());
        doc.setDocTypKey(dto.getDocTypKey());
        doc.setCrtdBy(currUser);
        doc.setCrtdDt(currIns);
        doc.setLastUpdBy(currUser);
        doc.setLastUpdDt(currIns);
        doc.setDelFlg(false);
        doc.setEffStartDt(currIns);
        doc.setCrntRecFlg(true);

        return mwDocRepository.save(doc);

    }

    @Transactional
    public MwDoc updateExistingDocument(DocumentDto dto, String currUser) {
        MwDoc mwDoc = mwDocRepository.findOneByDocSeqAndCrntRecFlg(dto.getDocSeq(), true);
        Instant currIns = Instant.now();
        if (mwDoc == null) {
            return null;
        }

        mwDoc.setLastUpdDt(currIns);
        mwDoc.setLastUpdBy(currUser);
        mwDoc.setDocNm(dto.getDocNm());
        mwDoc.setDocCtgryKey(dto.getDocCtgryKey());
        mwDoc.setDocTypKey(dto.getDocTypKey());

        return mwDocRepository.save(mwDoc);

    }
}
