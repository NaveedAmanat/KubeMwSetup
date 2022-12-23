package com.idev4.setup.service;

import com.idev4.setup.domain.MwRefCdGrp;
import com.idev4.setup.domain.MwRefCdVal;
import com.idev4.setup.dto.CodeValueDto;
import com.idev4.setup.dto.CommonCodeDto;
import com.idev4.setup.repository.MwRefCdGrpRepository;
import com.idev4.setup.repository.MwRefCdValRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwRefCdVal.
 */
@Service
@Transactional
public class MwRefCdValService {

    private final Logger log = LoggerFactory.getLogger(MwRefCdValService.class);

    private final MwRefCdValRepository mwRefCdValRepository;

    private final MwRefCdGrpRepository mwRefCdGrpRepository;

    public MwRefCdValService(MwRefCdValRepository mwRefCdValRepository, MwRefCdGrpRepository mwRefCdGrpRepository) {
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwRefCdGrpRepository = mwRefCdGrpRepository;
    }

    /**
     * Save a mwRefCdVal.
     *
     * @param mwRefCdVal the entity to save
     * @return the persisted entity
     */
    public MwRefCdVal save(MwRefCdVal mwRefCdVal) {
        log.debug("Request to save MwRefCdVal : {}", mwRefCdVal);
        return mwRefCdValRepository.save(mwRefCdVal);
    }

    /**
     * Get all the mwRefCdVals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MwRefCdVal> findAll(Pageable pageable) {
        log.debug("Request to get all MwRefCdVals");
        return mwRefCdValRepository.findAll(pageable);
    }

    /**
     * Get one mwRefCdVal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MwRefCdVal findOne(Long id) {
        log.debug("Request to get MwRefCdVal : {}", id);
        return mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(id, true);
    }

    /**
     * Delete the mwRefCdVal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MwRefCdVal : {}", id);
        MwRefCdVal ref = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(id, true);
        ref.setCrntRecFlg(false);
        ref.setDelFlg(true);
        ref.setLastUpdDt(Instant.now());
        mwRefCdValRepository.save(ref);
    }

    @Transactional(readOnly = true)
    public List<MwRefCdVal> findAllByCurrentRecord() {
        log.debug("Request to get all MwRefCdVals");
        return mwRefCdValRepository.findAllByCrntRecFlgOrderByRefCdSeqDesc(true);
    }

    public MwRefCdVal addNewValueToGroup(CodeValueDto dto, String currUser) {
        if (null == mwRefCdGrpRepository.findOneByRefCdGrpSeqAndCrntRecFlg(dto.groupKey, true)) {
            return null;
        }
        long seq = SequenceFinder.findNextVal(Sequences.REF_VAL_SEQ);
        MwRefCdVal refCodeVal = new MwRefCdVal();
        Instant currIns = Instant.now();
        refCodeVal.setRefCdSeq(seq);
        refCodeVal.setCrntRecFlg(true);
        refCodeVal.setCrtdBy(currUser);
        refCodeVal.setCrtdDt(currIns);
        refCodeVal.setDelFlg(false);
        refCodeVal.setEffStartDt(currIns);
        refCodeVal.setLastUpdBy(currUser);
        refCodeVal.setLastUpdDt(currIns);
        refCodeVal.setRefCd(String.format("%04d", seq));
        refCodeVal.setRefCdDscr(dto.valueDescription);
        refCodeVal.setRefCdGrpKey(dto.groupKey);
        refCodeVal.setActiveFlg(dto.activeStatus);
        refCodeVal.setSortOrder(dto.sortOrder);
        return mwRefCdValRepository.save(refCodeVal);

    }

    public MwRefCdVal updateExistingValueToGroup(CodeValueDto dto, String currUser) {
        MwRefCdVal refCodeVal = mwRefCdValRepository.findOneByRefCdSeqAndRefCdGrpKeyAndCrntRecFlg(dto.codeValueSeq, dto.groupKey, true);
        Instant currIns = Instant.now();
        if (refCodeVal == null) {
            return null;
        }
        refCodeVal.setLastUpdDt(currIns);
        refCodeVal.setLastUpdBy(currUser);
        refCodeVal.setRefCdDscr(dto.valueDescription);
        refCodeVal.setSortOrder(dto.sortOrder);
        refCodeVal.setActiveFlg(dto.activeStatus);

        return mwRefCdValRepository.save(refCodeVal);

    }

    public List<MwRefCdVal> getAllValuesByGroupKey(Long groupKey) {

        return mwRefCdValRepository.findAllByRefCdGrpKeyAndCrntRecFlg(groupKey, true);
    }

    public List<CommonCodeDto> getAllRefCdValuesByGroupKey(Long groupKey) {

        MwRefCdGrp group = mwRefCdGrpRepository.findOneByRefCdGrpSeqAndCrntRecFlgOrderByRefCdGrpSeqDesc(groupKey, true);
        List<CommonCodeDto> vals = new ArrayList<>();
        if (group == null)
            return vals;
        List<MwRefCdVal> values = mwRefCdValRepository.findAllByRefCdGrpKeyAndActiveStatusAndCrntRecFlgOrderBySortOrder(groupKey, true,
            true);

        values.forEach((val) -> {
            CommonCodeDto dto = new CommonCodeDto();
            dto.codeKey = val.getRefCdSeq();
            dto.codeValue = val.getRefCdDscr();
            dto.activeStatus = val.getActiveFlg();
            dto.sortOrder = val.getSortOrder();
            dto.codeRefCd = val.getRefCd();
            vals.add(dto);
        });

        return vals;
    }

    public List<CommonCodeDto> getAllValuesByGroupName(String groupName) {
        groupName = groupName.toUpperCase();
        MwRefCdGrp group = mwRefCdGrpRepository.findOneByRefCdGrpNameAndCrntRecFlg(groupName, true);
        List<CommonCodeDto> vals = new ArrayList<>();
        if (group == null)
            return vals;
        List<MwRefCdVal> values = mwRefCdValRepository.findAllByRefCdGrpKeyAndCrntRecFlg(group.getRefCdGrpSeq(), true);

        values.forEach((val) -> {
            CommonCodeDto dto = new CommonCodeDto();
            dto.codeKey = val.getRefCdSeq();
            dto.codeValue = val.getRefCdDscr();
            dto.activeStatus = val.getActiveFlg();
            dto.sortOrder = val.getSortOrder();
            vals.add(dto);
        });

        return vals;
    }

    public List<CommonCodeDto> getAllValuesByRefCdGrp(String refCdGrp) {
        MwRefCdGrp group = mwRefCdGrpRepository.findOneByRefCdGrpAndCrntRecFlg(refCdGrp, true);
        List<CommonCodeDto> vals = new ArrayList<>();
        List<MwRefCdVal> values = mwRefCdValRepository.findAllByRefCdGrpKeyAndCrntRecFlg(group.getRefCdGrpSeq(), true);

        values.forEach((val) -> {
            CommonCodeDto dto = new CommonCodeDto();
            dto.codeKey = val.getRefCdSeq();
            dto.codeValue = val.getRefCdDscr();
            dto.activeStatus = val.getActiveFlg();
            dto.sortOrder = val.getSortOrder();
            vals.add(dto);
        });

        return vals;
    }

    // Added by Zohaib Asim - Dated 16-12-2020
    // Get Health Claim Types
    public List<MwRefCdVal> getHlthClmTypes() {
        // Health Insurance Type
        long refCdGrpKey = 2786;
        Boolean crntRecFlg = true;

        return mwRefCdValRepository.findMwRefCdValByRefCdGrpKeyAndCrntRecFlg(refCdGrpKey, crntRecFlg);
    }
    // End by Zohaib Asim
}
