package com.idev4.setup.service;

import com.idev4.setup.domain.MwBrnchLocationRel;
import com.idev4.setup.domain.MwPortLocationRel;
import com.idev4.setup.dto.AddressCombinationDto;
import com.idev4.setup.dto.PortLocationRelDto;
import com.idev4.setup.repository.MwBrnchLocationRelRepository;
import com.idev4.setup.repository.MwPortLocationRelRepository;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing MwPortLocationRel.
 */
@Service
@Transactional
public class MwPortLocationRelService {

    private final Logger log = LoggerFactory.getLogger(MwPortLocationRelService.class);

    private final MwPortLocationRelRepository mwPortLocationRelRepository;
    private final MwBrnchLocationRelRepository mwBrnchLocationRelRepository;
    private final EntityManager em;

    public MwPortLocationRelService(MwPortLocationRelRepository mwPortLocationRelRepository, EntityManager em,
                                    MwBrnchLocationRelRepository mwBrnchLocationRelRepository) {
        this.mwPortLocationRelRepository = mwPortLocationRelRepository;
        this.em = em;
        this.mwBrnchLocationRelRepository = mwBrnchLocationRelRepository;
    }

    /**
     * Save a mwPortLocationRel.
     *
     * @param mwPortLocationRel the entity to save
     * @return the persisted entity
     */
    public MwPortLocationRel save(MwPortLocationRel mwPortLocationRel) {
        log.debug("Request to save MwPortLocationRel : {}", mwPortLocationRel);
        return mwPortLocationRelRepository.save(mwPortLocationRel);
    }

    /**
     * Get all the mwPortLocationRels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwPortLocationRel> findAll(Pageable pageable) {
        log.debug("Request to get all MwPortLocationRels");
        return mwPortLocationRelRepository.findAll(pageable);
    }


    /**
     * Get one mwPortLocationRel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MwPortLocationRel> findOne(Long id) {
        log.debug("Request to get MwPortLocationRel : {}", id);
        return Optional.ofNullable(mwPortLocationRelRepository.findOne(id));
    }

    /**
     * Delete the mwPortLocationRel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwPortLocationRel : {}", id);
        mwPortLocationRelRepository.delete(id);
    }

    public List getAddressCombinationsByBrnch(long branchSeq) {
        List<MwBrnchLocationRel> locs = mwBrnchLocationRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
        List<Long> cities = locs.stream().map(loc -> loc.getCitySeq()).collect(Collectors.toList());
        Query q = em.createNativeQuery(Queries.addressCombs);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {
            if (cities.contains(Long.valueOf(comb[11].toString()))) {
                AddressCombinationDto dto = new AddressCombinationDto();
                dto.country = Long.valueOf(comb[0].toString());
                dto.countryName = comb[1].toString();
                dto.province = Long.valueOf(comb[2].toString());
                dto.provinceName = comb[3].toString();
                dto.district = Long.valueOf(comb[4].toString());
                dto.districtName = comb[5].toString();
                dto.tehsil = Long.valueOf(comb[6].toString());
                dto.tehsilName = comb[7].toString();
                dto.uc = Long.valueOf(comb[8].toString());
                dto.ucName = comb[9].toString() + " " + comb[10].toString();
                dto.city = Long.valueOf(comb[11].toString());
                dto.cityName = comb[12].toString();
                combs.add(dto);
            }
        }

        log.debug("Result set is: {}", q.getMaxResults());
        // return q.getResultList();
        return combs;
    }

    public void addNewPortRels(List<PortLocationRelDto> dtos, String currUser) {
        Instant currIns = Instant.now();
        List<MwPortLocationRel> rels = new ArrayList<>();
        dtos.forEach(dto -> {
            MwPortLocationRel rel = new MwPortLocationRel();
            long seq = SequenceFinder.findNextVal(Sequences.PORT_LOCATION_REL_SEQ);
            rel.setPortLocationRelSeq(seq);
            rel.setPortSeq(dto.portSeq);
            rel.setCitySeq(dto.citySeq);
            rel.setCrntRecFlg(true);
            rel.setCrtdBy(currUser);
            rel.setCrtdDt(currIns);
            rel.setDelFlg(false);
            rel.setEffStartDt(currIns);
            rel.setLastUpdBy(currUser);
            rel.setLastUpdDt(currIns);
            rels.add(rel);
        });
        mwPortLocationRelRepository.save(rels);
    }

    public void addNewPortRel(PortLocationRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwPortLocationRel rel = new MwPortLocationRel();
        long seq = SequenceFinder.findNextVal(Sequences.PORT_LOCATION_REL_SEQ);
        rel.setPortLocationRelSeq(seq);
        rel.setPortSeq(dto.portSeq);
        rel.setCitySeq(dto.citySeq);
        rel.setCrntRecFlg(true);
        rel.setCrtdBy(currUser);
        rel.setCrtdDt(currIns);
        rel.setDelFlg(false);
        rel.setEffStartDt(currIns);
        rel.setLastUpdBy(currUser);
        rel.setLastUpdDt(currIns);
        mwPortLocationRelRepository.save(rel);
    }

    public void deletePortRel(PortLocationRelDto dto, String currUser) {
        Instant currIns = Instant.now();
        MwPortLocationRel rel = mwPortLocationRelRepository.findOneByPortSeqAndCitySeqAndCrntRecFlg(dto.portSeq, dto.citySeq, true);
        if (rel != null) {
            rel.setCrntRecFlg(false);
            rel.setDelFlg(true);
            rel.setEffEndDt(currIns);
            rel.setLastUpdBy(currUser);
            rel.setLastUpdDt(currIns);
            mwPortLocationRelRepository.save(rel);
        }
    }

    public List<MwPortLocationRel> getCurrentLocations(long portSeq) {
        return mwPortLocationRelRepository.findAllByPortSeqAndCrntRecFlg(portSeq, true);
    }

    public List getAddressCombinationsByBrnchAndFilter(long branchSeq, String filter) {
        String search = "";

        if (filter != null && filter.length() > 0) {
            search = (" and ( (lower(st.st_nm) LIKE '%?%') " +
                " or (lower(dst.dist_nm) LIKE '%?%') " +
                " or (lower(thsl.thsl_nm) LIKE '%?%') " +
                " or (lower(uc.uc_nm) LIKE '%?%') " +
                " or (lower(uc.uc_commnets) LIKE '%?%') " +
                " or (lower(cty.city_nm) LIKE '%?%') )")
                .replace("?", filter.toLowerCase());
        }

        List<MwBrnchLocationRel> locs = mwBrnchLocationRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
        List<Long> cities = locs.stream().map(loc -> loc.getCitySeq()).collect(Collectors.toList());
        String query = Queries.addressCombs2.replace("?", search);
        Query q = em.createNativeQuery(query);

        List<Object[]> cityCombinations = q.getResultList();
        List<AddressCombinationDto> combs = new ArrayList<AddressCombinationDto>();

        for (Object[] comb : cityCombinations) {
            if (cities.contains(Long.valueOf(comb[11].toString()))) {
                AddressCombinationDto dto = new AddressCombinationDto();
                dto.country = Long.valueOf(comb[0].toString());
                dto.countryName = comb[1].toString();
                dto.province = Long.valueOf(comb[2].toString());
                dto.provinceName = comb[3].toString();
                dto.district = Long.valueOf(comb[4].toString());
                dto.districtName = comb[5].toString();
                dto.tehsil = Long.valueOf(comb[6].toString());
                dto.tehsilName = comb[7].toString();
                dto.uc = Long.valueOf(comb[8].toString());
                dto.ucName = comb[9].toString() + " " + comb[10].toString();
                dto.city = Long.valueOf(comb[11].toString());
                dto.cityName = comb[12].toString();
                combs.add(dto);
            }
        }

        log.debug("Result set is: {}", q.getMaxResults());
        // return q.getResultList();
        return combs;
    }
}
