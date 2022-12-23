package com.idev4.setup.service;

import com.idev4.setup.domain.MwCity;
import com.idev4.setup.dto.CityDto;
import com.idev4.setup.repository.MwCityRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing MwCity.
 */
@Service
@Transactional
public class MwCityService {

    private final Logger log = LoggerFactory.getLogger(MwCityService.class);
    private final MwCityRepository mwCityRepository;
    @Autowired
    private EntityManager entityManager;

    public MwCityService(MwCityRepository mwCityRepository) {
        this.mwCityRepository = mwCityRepository;
    }

    /**
     * Save a mwCity.
     *
     * @param mwCity the entity to save
     * @return the persisted entity
     */
    public MwCity save(MwCity mwCity) {
        log.debug("Request to save MwCity : {}", mwCity);
        return mwCityRepository.save(mwCity);
    }

    /**
     * Get all the mwCitys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwCity> findAll(Pageable pageable) {
        log.debug("Request to get all MwCities");
        return mwCityRepository.findAll(pageable);
    }

    /**
     * Get one mwCity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwCity findOne(Long citySeq) {
        log.debug("Request to get MwCity : {}", citySeq);
        return mwCityRepository.findOneByCitySeqAndCrntRecFlg(citySeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwCity> findAllHistory(Long citySeq) {
        log.debug("Request to get MwCity : {}", citySeq);
        return mwCityRepository.findAllByCitySeq(citySeq);
    }

    /**
     * Delete the mwCity by id.
     *
     * @param id the id of the entity
     */
    public boolean delete(Long citySeq) {
        log.debug("Request to delete MwCity : {}", citySeq);
        MwCity city = mwCityRepository.findOneByCitySeqAndCrntRecFlg(citySeq, true);
        city.setCrntRecFlg(false);
        city.setDelFlg(true);
        city.setLastUpdDt(Instant.now());
        mwCityRepository.save(city);
        return true;
    }

//    @Transactional ( readOnly = true )
//    public List< MwCity > findAllByCurrentRecord() {
//        log.debug( "Request to get all MwCities" );
//        return mwCityRepository.findAllByCrntRecFlg( true );
//    }

    @Transactional(readOnly = true)
    public List<MwCity> findAllCitiesByCurrentRecord() {
        log.debug("Request to get all MwCities");
        return mwCityRepository.findAllByCrntRecFlgOrderByCityNm(true);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findAllByCurrentRecord(Integer pageIndex, Integer pageSize, String filter, Boolean isCount) {
        log.debug("Request to get all MwCities");
        // return mwCityRepository.findAllByCrntRecFlg( true );

        String cityScript = "SELECT c.* FROM mw_city c where c.crnt_rec_flg =1";
        String cityCountScript = "SELECT count(*) FROM mw_city c where c.crnt_rec_flg =1";

        if (filter != null && filter.length() > 0) {
            String search = " and( lower(c.city_nm) LIKE '%?%' )"
                .replace("?", filter.toLowerCase());

            cityScript += search;
            cityCountScript += search;
        }

        List<MwCity> allCitiesList = entityManager.createNativeQuery(cityScript + "\r\norder by 1 desc", MwCity.class)
            .setFirstResult((pageIndex) * pageSize).setMaxResults(pageSize).getResultList();

        Map<String, Object> response = new HashMap<>();
        response.put("cities", allCitiesList);

        Long totalCityCount = 0L;
        if (isCount.booleanValue()) {
            totalCityCount = new BigDecimal(
                entityManager.createNativeQuery(cityCountScript)
                    .getSingleResult().toString()).longValue();
        }
        response.put("count", totalCityCount);

        return response;
    }

    public MwCity addNewCity(CityDto dto, String currUser) {

        long seq = SequenceFinder.findNextVal(Sequences.CITY_SEQ);
        MwCity city = new MwCity();
        Instant currIns = Instant.now();
        city.setCitySeq(seq);
        city.setCrntRecFlg(true);
        city.setCrtdBy(currUser);
        city.setCrtdDt(currIns);
        city.setDelFlg(false);
        city.setEffStartDt(currIns);
        city.setLastUpdBy(currUser);
        city.setLastUpdDt(currIns);
        city.setCityCd(String.format("%04d", seq));
        city.setCityNm(dto.getCityNm());
        city.setCityCmnt(dto.getCityCmnt());
        city.setCityStsKey(200L);

        return mwCityRepository.save(city);
    }

    @Transactional
    public MwCity UpdateExistingCity(CityDto dto, String currUser) {
        MwCity mwCity = mwCityRepository.findOneByCitySeqAndCrntRecFlg(dto.getCitySeq(), true);
        Instant currIns = Instant.now();
        if (mwCity == null) {
            return null;
        }
        mwCity.setLastUpdDt(currIns);
        mwCity.setLastUpdBy(currUser);
        mwCity.setCityNm(dto.getCityNm());
        mwCity.setCityCmnt(dto.getCityCmnt());
//         mwCity.setCityStsKey(dto.getCityStsKey());

        return mwCityRepository.save(mwCity);
    }

    // public List<MwCity> getAllCityByUcSeq(long ucSeq){
    // return mwCityRepository.findAllByUcSeqAndCrntRecFlg(ucSeq,true);
    // }
}
