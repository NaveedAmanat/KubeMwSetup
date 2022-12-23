package com.idev4.setup.service;

import com.idev4.setup.domain.MwCityUcRel;
import com.idev4.setup.repository.MwCityUcRelRepository;
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
public class MwCityUcRelService {

    private final Logger log = LoggerFactory.getLogger(MwCityUcRelService.class);

    private final MwCityUcRelRepository mwCityUcRelRepository;

    public MwCityUcRelService(MwCityUcRelRepository mwCityUcRelRepository) {
        this.mwCityUcRelRepository = mwCityUcRelRepository;
    }

    public MwCityUcRel save(MwCityUcRel MwCityUcRel) {
        log.debug("Request to save MwCityUcRel : {}", MwCityUcRel);
        return mwCityUcRelRepository.save(MwCityUcRel);
    }

    @Transactional(readOnly = true)
    public MwCityUcRel findOne(Long cityUcRelSeq) {
        log.debug("Request to get MwCityUcRel : {}", cityUcRelSeq);
        return mwCityUcRelRepository.findOneByCityUcRelSeqAndCrntRecFlg(cityUcRelSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwCityUcRel> findAllByCitySeq(Long citySeq) {
        log.debug("Request to get MwCityUcRel : {}", citySeq);
        return mwCityUcRelRepository.findAllByCitySeqAndCrntRecFlg(citySeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwCityUcRel> findAllByUcSeq(Long ucSeq) {
        log.debug("Request to get MwCityUcRel : {}", ucSeq);
        return mwCityUcRelRepository.findAllByUcSeqAndCrntRecFlg(ucSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwCityUcRel> findAllByCrntRecFlg() {
        log.debug("Request to get All MwCityUcRels : {}");
        return mwCityUcRelRepository.findAllByCrntRecFlg(true);
    }

    public boolean delete(Long cityUcRelSeq) {

        MwCityUcRel rel = mwCityUcRelRepository.findOneByCityUcRelSeqAndCrntRecFlg(cityUcRelSeq, true);
        rel.setCrntRecFlg(false);
        rel.setDelFlg(true);
        rel.setLastUpdDt(Instant.now());
        mwCityUcRelRepository.save(rel);
        return true;
    }

    public MwCityUcRel addNewCityUcRel(MwCityUcRel dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.City_UC_REL_SEQ);
        MwCityUcRel bizActy = new MwCityUcRel();
        Instant currIns = Instant.now();

        bizActy.setCityUcRelSeq(seq);
        bizActy.setCitySeq(dto.getCitySeq());
        bizActy.setUcSeq(dto.getUcSeq());
        bizActy.setCrtdBy(currUser);
        bizActy.setCrtdDt(currIns);
        bizActy.setLastUpdBy(currUser);
        bizActy.setLastUpdDt(currIns);
        bizActy.setDelFlg(false);
        bizActy.setEffStartDt(currIns);
        bizActy.setCrntRecFlg(true);

        return mwCityUcRelRepository.save(bizActy);

    }

    @Transactional
    public MwCityUcRel updateCityUcRel(MwCityUcRel dto, String currUser) {


        MwCityUcRel mwRel = mwCityUcRelRepository.findOneByCityUcRelSeqAndCrntRecFlg(dto.getCityUcRelSeq(), true);
        Instant currIns = Instant.now();
        if (mwRel == null) {
            return null;
        }

        mwRel.setLastUpdDt(currIns);
        mwRel.setLastUpdBy(currUser);
        mwRel.setCitySeq(dto.getCitySeq());
        mwRel.setUcSeq(dto.getUcSeq());
        return mwCityUcRelRepository.save(mwRel);


    }
}
