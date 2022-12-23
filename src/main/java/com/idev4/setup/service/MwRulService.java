package com.idev4.setup.service;

import com.idev4.setup.domain.MwCity;
import com.idev4.setup.domain.MwRul;
import com.idev4.setup.dto.RuleDto;
import com.idev4.setup.repository.MwRulRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MwRulService {

    private final Logger log = LoggerFactory.getLogger(MwRulService.class);

    private final MwRulRepository mwRulRepository;

    private final EntityManager em;

    public MwRulService(MwRulRepository mwRulRepository, EntityManager em) {
        this.mwRulRepository = mwRulRepository;
        this.em = em;
    }

    public MwRul save(MwRul mwRul) {
        log.debug("Request to save MwRul : {}", mwRul);
        return mwRulRepository.save(mwRul);
    }

    @Transactional(readOnly = true)
    public MwRul findOne(Long rulSeq) {
        log.debug("Request to get MwRul : {}", rulSeq);
        return mwRulRepository.findOneByRulSeqAndCrntRecFlg(rulSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwRul> findAllByRulSeq(Long rulSeq) {
        log.debug("Request to get MwRul : {}", rulSeq);
        return mwRulRepository.findAllByRulSeq(rulSeq);
    }

    @Transactional(readOnly = true)
    public List<MwRul> findAllByCrntRecFlg() {
        log.debug("Request to get All MwRuls : {}");
        return mwRulRepository.findAllByCrntRecFlg(true);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findAllByCrntRecFlg(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get All MwRuls : {}");

        String ruleScript = "SELECT r.* FROM mw_rul r  where r.crnt_rec_flg =1";
        String ruleCountScript = "SELECT count(*) FROM mw_rul r where r.crnt_rec_flg =1";

        if (filter != null && filter.length() > 0) {
            String search = " and( lower(r.rul_nm) LIKE '%?%' )"
                .replace("?", filter.toLowerCase());

            ruleScript += search;
            ruleCountScript += search;
        }

        List<MwCity> allRulesList = em.createNativeQuery(ruleScript + "\r\norder by 1 desc", MwRul.class)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("rules", allRulesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                em.createNativeQuery(ruleCountScript)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    public boolean delete(Long rulSeq) {

        MwRul rul = mwRulRepository.findOneByRulSeqAndCrntRecFlg(rulSeq, true);
        rul.setCrntRecFlg(false);
        rul.setDelFlg(true);
        rul.setLastUpdDt(Instant.now());
        mwRulRepository.save(rul);
        return true;
    }

    public MwRul addNewRul(RuleDto dto, String currUser) {
        if (dto.getRulCtgryKey() == 1) {
            String query = Queries.checkRule + " " + dto.getRulCrtraStr();
            Query qr = em.createNativeQuery(query);
            List<Object[]> resultSet = qr.getResultList();
        } else if (dto.getRulCtgryKey() == 2) {
            String query = Queries.advRule + "1 and" + dto.getRulCrtraStr();
            Query qr = em.createNativeQuery(query);
            List<Object[]> resultSet = qr.getResultList();
        }
        long seq = SequenceFinder.findNextVal(Sequences.RUL_SEQ);
        MwRul rul = new MwRul();
        Instant currIns = Instant.now();

        rul.setRulSeq(seq);
        rul.setRulId(String.format("%04d", seq));
        rul.setRulCtgryKey(dto.getRulCtgryKey());
        rul.setRulNm(dto.getRulNm());
        rul.setRulCmnt(dto.getRulCmnt());
        rul.setRulCrtraStr(dto.getRulCrtraStr());
        rul.setCrtdBy(currUser);
        rul.setCrtdDt(currIns);
        rul.setLastUpdBy(currUser);
        rul.setLastUpdDt(currIns);
        rul.setDelFlg(false);
        rul.setEffStartDt(currIns);
        rul.setCrntRecFlg(true);

        return mwRulRepository.save(rul);

    }

    @Transactional
    public MwRul updateExistingRule(RuleDto dto, String currUser) {
        if (dto.getRulCtgryKey() == 1) {
            String query = Queries.checkRule + " " + dto.getRulCrtraStr();
            Query qr = em.createNativeQuery(query);
            List<Object[]> resultSet = qr.getResultList();
        } else if (dto.getRulCtgryKey() == 2) {
            String query = Queries.advRule + "1 and" + dto.getRulCrtraStr();
            Query qr = em.createNativeQuery(query);
            List<Object[]> resultSet = qr.getResultList();
        }
        MwRul rul = mwRulRepository.findOneByRulSeqAndCrntRecFlg(dto.getRulSeq(), true);
        Instant currIns = Instant.now();
        if (rul == null) {
            return null;
        }

        rul.setLastUpdDt(currIns);
        rul.setLastUpdBy(currUser);
        rul.setRulNm(dto.getRulNm());
        rul.setRulCmnt(dto.getRulCmnt());
        rul.setRulCtgryKey(dto.getRulCtgryKey());
        rul.setRulCrtraStr(dto.getRulCrtraStr());

        return mwRulRepository.save(rul);

    }
}
