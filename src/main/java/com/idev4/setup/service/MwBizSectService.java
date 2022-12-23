package com.idev4.setup.service;

import com.idev4.setup.domain.MwBizSect;
import com.idev4.setup.domain.MwPrdBizSectRel;
import com.idev4.setup.dto.BusinessSectorDto;
import com.idev4.setup.repository.MwBizActyRepository;
import com.idev4.setup.repository.MwBizSectRepository;
import com.idev4.setup.repository.MwPrdBizSectRelRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MwBizSectService {

    public final MwBizActyRepository mwBizActyRepository;
    private final Logger log = LoggerFactory.getLogger(MwBizSectService.class);
    private final MwBizSectRepository mwBizSectRepository;
    private final MwPrdBizSectRelRepository mwPrdBizSectRelRepository;
    private final EntityManager em;

    public MwBizSectService(MwBizSectRepository mwBizSectRepository, EntityManager em, MwPrdBizSectRelRepository mwPrdBizSectRelRepository,
                            MwBizActyRepository mwBizActyRepository) {
        this.mwBizSectRepository = mwBizSectRepository;
        this.mwPrdBizSectRelRepository = mwPrdBizSectRelRepository;
        this.em = em;
        this.mwBizActyRepository = mwBizActyRepository;
    }

    public MwBizSect save(MwBizSect mwBizSect) {
        log.debug("Request to save MwBizSect : {}", mwBizSect);
        return mwBizSectRepository.save(mwBizSect);
    }

    @Transactional(readOnly = true)
    public MwBizSect findOne(Long bizSectSeq) {
        log.debug("Request to get MwBizSect : {}", bizSectSeq);
        return mwBizSectRepository.findOneByBizSectSeqAndCrntRecFlg(bizSectSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwBizSect> findAllByBizSectSeq(Long bizSectSeq) {
        log.debug("Request to get MwBizSect : {}", bizSectSeq);
        return mwBizSectRepository.findAllByBizSectSeq(bizSectSeq);
    }

    // @Transactional ( readOnly = true )
    // public List< MwBizSect > findAllByBizActySeq( Long bizActySeq ) {
    // log.debug( "Request to get BizActySeq : {}", bizActySeq );
    // MwBizActy acty = mwBizActyRepository.findOneByBizActySeqAndCrntRecFlg( bizActySeq, true );
    // if(acty!=null)
    // return mwBizSectRepository.findAllByBizSectSeq( bizSectSeq );
    // return mwBizSectRepository.findAllByBizSectSeq( bizSectSeq );
    // }

    @Transactional(readOnly = true)
    public List<MwBizSect> findAllByPrdSeq(Long prdSeq) {
        log.debug("Request to get MwBizSect by prdSeq: {}", prdSeq);
        List<MwPrdBizSectRel> rels = mwPrdBizSectRelRepository.findAllByPrdSeqAndCrntRecFlg(prdSeq, true);
        List<Long> ids = rels.stream().map(rel -> rel.getBizSectSeq()).collect(Collectors.toList());
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
        List<MwBizSect> list = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            MwBizSect rel = mwBizSectRepository.findOneByBizSectSeqAndCrntRecFlg(ids.get(i), true);
            if (rel != null) {
                if (rel.getBizSectStsKey() == actSeq) {
                    list.add(rel);
                }
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<MwBizSect> findAllByCrntRecFlg() {
        log.debug("Request to get All MwBizSects : {}");
        return mwBizSectRepository.findAllByCrntRecFlg(true);
    }

    public boolean delete(Long bizSectSeq) {

        MwBizSect bizSect = mwBizSectRepository.findOneByBizSectSeqAndCrntRecFlg(bizSectSeq, true);
        bizSect.setCrntRecFlg(false);
        bizSect.setDelFlg(true);
        bizSect.setLastUpdDt(Instant.now());
        mwBizSectRepository.save(bizSect);
        return true;
    }

    public MwBizSect addNewBizSect(BusinessSectorDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.BIZ_SECT_SEQ);
        MwBizSect bizSect = new MwBizSect();
        Instant currIns = Instant.now();

        bizSect.setBizSectSeq(seq);
        bizSect.setBizSectId(String.format("%04d", seq));
        bizSect.setBizSectNm(dto.getBizSectNm());
        bizSect.setBizSectStsKey(dto.getBizSectStsKey());
        bizSect.setBizSectSortOrdr(dto.getBizSectSortOrdr());
        bizSect.setCrtdBy(currUser);
        bizSect.setCrtdDt(currIns);
        bizSect.setLastUpdBy(currUser);
        bizSect.setLastUpdDt(currIns);
        bizSect.setDelFlg(false);
        bizSect.setEffStartDt(currIns);
        bizSect.setCrntRecFlg(true);

        return mwBizSectRepository.save(bizSect);

    }

    @Transactional
    public MwBizSect updateExistingBusinessSector(BusinessSectorDto dto, String currUser) {
        MwBizSect bizSect = mwBizSectRepository.findOneByBizSectSeq(dto.getBizSectSeq());
        Instant currIns = Instant.now();
        if (bizSect == null) {
            return null;
        }

        bizSect.setLastUpdDt(currIns);
        bizSect.setLastUpdBy(currUser);
        bizSect.setBizSectNm(dto.getBizSectNm());
        bizSect.setBizSectStsKey(dto.getBizSectStsKey());
        bizSect.setBizSectSortOrdr(dto.getBizSectSortOrdr());

        return mwBizSectRepository.save(bizSect);

    }
}
