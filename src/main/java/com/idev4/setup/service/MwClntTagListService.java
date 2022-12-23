package com.idev4.setup.service;

import com.idev4.setup.domain.MwClntTagList;
import com.idev4.setup.domain.MwTags;
import com.idev4.setup.repository.MwClntTagListRepository;
import com.idev4.setup.repository.MwTagsRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.Instant;

@Service
@Transactional
public class MwClntTagListService {

    private final Logger log = LoggerFactory.getLogger(MwClntHlthInsrClmService.class);

    @Autowired
    MwClntTagListRepository mwClntTagListRepository;

    @Autowired
    MwTagsRepository mwTagsRepository;

    @Autowired
    EntityManager em;

    public MwClntTagList addClntTagList(Long cnic, Long loanAppSeq, String user) {
        Instant currIns = Instant.now();
        long seq = SequenceFinder.findNextVal(Sequences.CLNT_TAG_LIST_SEQ);

        MwTags tag = mwTagsRepository.findOneByTagIdAndCrntRecFlg("0004", true);

        MwClntTagList ctl = new MwClntTagList();
        ctl.setClntTagListSeq(seq);
        ctl.setCnicNum(cnic);
        ctl.setCrtdBy(user);
        ctl.setCrtdDt(currIns);
        ctl.setDelFlg(false);
        ctl.setEffEndDt(null);
        ctl.setEffStartDt(currIns);
        ctl.setLastUpdBy(user);
        ctl.setLastUpdDt(currIns);
        ctl.setRmks("Death");
        ctl.setTagFromDt(currIns);
        ctl.setTagsSeq(tag.getTagsSeq());
        ctl.setTagToDt(null);
        ctl.setLoanAppSeq(loanAppSeq);
        ctl.setCrntRecFlg(true);

        return mwClntTagListRepository.save(ctl);
    }

    public MwClntTagList getClntTag(Long cnic) {
        return mwClntTagListRepository.findOneByCnicNumAndDelFlg(cnic, false);
    }

    public MwClntTagList addClntToTagList(Long cnic, Long tagSeq, String user) {
        Instant currIns = Instant.now();

        MwClntTagList exTag = getClntTag(cnic);
        Long seq = 0L;
        if (exTag == null) {
            seq = SequenceFinder.findNextVal(Sequences.CLNT_TAG_LIST_SEQ);
        } else {
            seq = exTag.getClntTagListSeq();
            exTag.setDelFlg(true);
            mwClntTagListRepository.save(exTag);
        }

        // MwClntTagList ctl = new MwClntTagList();
        // ctl.setClntTagListSeq( seq );
        // ctl.setCnicNum( cnic );
        // ctl.setCrtdBy( user );
        // ctl.setCrtdDt( currIns );
        // ctl.setDelFlg( false );
        // ctl.setEffEndDt( null );
        // ctl.setEffStartDt( currIns );
        // ctl.setLastUpdBy( user );
        // ctl.setLastUpdDt( currIns );
        // // ctl.setRmks( "Death" );
        // ctl.setTagFromDt( currIns );
        // ctl.setTagsSeq( tagSeq );
        // ctl.setTagToDt( null );
        // mwClntTagListRepository.save( ctl );

        MwTags tag = mwTagsRepository.findOneByTagsSeqAndCrntRecFlg(tagSeq, true);
        if (tag != null && tag.getTagId().equals("0005")) {

            String query = "select clnt_seq from mw_clnt where crnt_rec_flg=1 and cnic_num=" + cnic;
            Query qt = em.createNativeQuery(query);
            Long clntSeq = (new BigDecimal(qt.getSingleResult().toString())).longValue();

            StoredProcedureQuery tagProc = em.createStoredProcedureQuery("WO_PROVISION_FOR_LOAN_LOSS");

            tagProc.registerStoredProcedureParameter("p_CLNT_SEQ", Long.class, ParameterMode.IN);
            tagProc.registerStoredProcedureParameter("p_user", String.class, ParameterMode.IN);

            tagProc.setParameter("p_CLNT_SEQ", clntSeq);
            tagProc.setParameter("p_user", SecurityContextHolder.getContext().getAuthentication().getName());

            tagProc.execute();
            log.info("Executing WO_PROVISION_FOR_LOAN_LOSS for Client : " + clntSeq);
        }

        return getClntTag(cnic);
    }
}
