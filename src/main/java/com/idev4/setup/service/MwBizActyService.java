package com.idev4.setup.service;

import com.idev4.setup.domain.MwBizActy;
import com.idev4.setup.dto.BusinessActyDto;
import com.idev4.setup.repository.MwBizActyRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class MwBizActyService {

    private final Logger log = LoggerFactory.getLogger(MwBizActyService.class);

    private final MwBizActyRepository mwBizActyRepository;

    private final EntityManager em;

    public MwBizActyService(MwBizActyRepository mwBizActyRepository, EntityManager em) {
        this.mwBizActyRepository = mwBizActyRepository;
        this.em = em;
    }

    public MwBizActy save(MwBizActy mwBizActy) {
        log.debug("Request to save MwBizActy : {}", mwBizActy);
        return mwBizActyRepository.save(mwBizActy);
    }

    @Transactional(readOnly = true)
    public MwBizActy findOne(Long bizActySeq) {
        log.debug("Request to get MwBizActy : {}", bizActySeq);
        return mwBizActyRepository.findOneByBizActySeqAndCrntRecFlg(bizActySeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllByBizActySeq(Long bizActySeq) {
        log.debug("Request to get MwBizActy : {}", bizActySeq);
        return mwBizActyRepository.findAllByBizActySeq(bizActySeq);
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllByBizSectSeq(Long bizSectSeq) {
        log.debug("Request to get MwBizActy : {}", bizSectSeq);
        return mwBizActyRepository.findAllByBizSectSeqAndCrntRecFlg(bizSectSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllBySectSeq(Long bizSectSeq) {
        log.debug("Request to get MwBizActy : {}", bizSectSeq);
        String query = Queries.statusSeq;
        Query q = em.createNativeQuery(query);
        List<Object[]> statuses = q.getResultList();
        long actSeq = 0;
        long inactSeq = 0;
        for (Object[] st : statuses) {
            if (st[1].toString().toLowerCase().equals("active")) {
                actSeq = Long.valueOf(st[2].toString());
            } else {
                inactSeq = Long.valueOf(st[2].toString());
            }
        }
        return mwBizActyRepository.findAllByBizSectSeqAndCrntRecFlgAndBizActyStsKey(bizSectSeq, true, actSeq);
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllBySectSeqComplete(Long bizActySeq) {
        log.debug("Request to get MwBizActy : {}", bizActySeq);
        return mwBizActyRepository.findAllByBizSectSeqAndCrntRecFlg(bizActySeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllByActySeq(Long bizActySeq) {
        log.debug("Request to get MwBizActy : {}", bizActySeq);

        MwBizActy acty = mwBizActyRepository.findOneByBizActySeqAndCrntRecFlg(bizActySeq, true);
        if (acty != null)
            return findAllBySectSeq(acty.getBizSectSeq());
        return null;
    }

    @Transactional(readOnly = true)
    public List<MwBizActy> findAllByCrntRecFlg() {
        log.debug("Request to get All MwBizActys : {}");
        return mwBizActyRepository.findAllByCrntRecFlg(true);
    }

    public boolean delete(Long bizActySeq) {

        MwBizActy bizActy = mwBizActyRepository.findOneByBizActySeqAndCrntRecFlg(bizActySeq, true);
        bizActy.setCrntRecFlg(false);
        bizActy.setDelFlg(true);
        bizActy.setLastUpdDt(Instant.now());
        mwBizActyRepository.save(bizActy);
        return true;
    }

    public MwBizActy addNewBizActy(BusinessActyDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BIZ_ACTY_SEQ);
        MwBizActy bizActy = new MwBizActy();
        Instant currIns = Instant.now();

        bizActy.setBizActySeq(seq);
        bizActy.setBizActyId(String.format("%04d", seq));
        bizActy.setBizActyNm(dto.getBizActyNm());
        bizActy.setBizActyStsKey(dto.getBizActyStsKey());
        bizActy.setBizSectSeq(dto.getBizSectSeq());
        bizActy.setBizActySortOrdr(dto.getBizActySortOrdr());
        bizActy.setCrtdBy(currUser);
        bizActy.setCrtdDt(currIns);
        bizActy.setLastUpdBy(currUser);
        bizActy.setLastUpdDt(currIns);
        bizActy.setDelFlg(false);
        bizActy.setEffStartDt(currIns);
        bizActy.setCrntRecFlg(true);

        return mwBizActyRepository.save(bizActy);

    }

    @Transactional
    public MwBizActy updateExistingBusinessActy(BusinessActyDto dto, String currUser) {
        MwBizActy bizActy = mwBizActyRepository.findOneByBizActySeqAndCrntRecFlg(dto.getBizActySeq(), true);
        Instant currIns = Instant.now();
        if (bizActy == null) {
            return null;
        }

        bizActy.setLastUpdDt(currIns);
        bizActy.setLastUpdBy(currUser);
        bizActy.setBizActyNm(dto.getBizActyNm());
        bizActy.setBizActySortOrdr(dto.getBizActySortOrdr());
        bizActy.setBizActyStsKey(dto.getBizActyStsKey());
        bizActy.setBizSectSeq(dto.getBizSectSeq());

        return mwBizActyRepository.save(bizActy);

    }
}
