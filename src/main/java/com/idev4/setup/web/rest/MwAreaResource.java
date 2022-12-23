package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwArea;
import com.idev4.setup.dto.AddressDto;
import com.idev4.setup.dto.AreaDto;
import com.idev4.setup.service.MwAddrService;
import com.idev4.setup.service.MwAreaService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwArea.
 */
@RestController
@RequestMapping("/api")
public class MwAreaResource {

    private static final String ENTITY_NAME = "mwArea";
    private final Logger log = LoggerFactory.getLogger(MwAreaResource.class);
    private final MwAreaService mwAreaService;
    private final MwAddrService mwAddrService;

    public MwAreaResource(MwAreaService mwAreaService, MwAddrService mwAddrService) {
        this.mwAreaService = mwAreaService;
        this.mwAddrService = mwAddrService;
    }

    /**
     * POST  /mw-areas : Create a new mwArea.
     *
     * @param mwArea the mwArea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwArea, or with status 400 (Bad Request) if the mwArea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-areas")
    @Timed
    public ResponseEntity<Map> createMwArea(@RequestBody AreaDto areaDto) throws URISyntaxException {
        log.debug("REST request to save MwArea : {}", areaDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(areaDto.areaId == null || areaDto.areaId.isEmpty()) {
//        	resp.put("error", "Area Id Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (areaDto.areaName == null || areaDto.areaName.isEmpty()) {
            resp.put("error", "Area Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (areaDto.areaDescription == null || areaDto.areaDescription.isEmpty()) {
            resp.put("error", "Area Description Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (areaDto.areaStatus < 0 || areaDto.areaType < 0) {
            resp.put("error", "Area Type or Area Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwArea> respData = new HashMap<String, MwArea>();
        MwArea area = mwAreaService.addNewArea(areaDto, currUser);

        AddressDto addrDto = new AddressDto();
        addrDto.areaSeq = area.getAreaSeq();
        addrDto.houseNum = areaDto.houseNum;
        addrDto.sreet_area = areaDto.sreet_area;
        addrDto.community = areaDto.community;
        addrDto.village = areaDto.village;
        addrDto.otherDetails = areaDto.otherDetails;
        addrDto.city = areaDto.city;

        mwAddrService.saveAddress(addrDto, currUser, "Area");

        respData.put("area", area);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-areas : Updates an existing mwArea.
     *
     * @param mwArea the mwArea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwArea,
     * or with status 400 (Bad Request) if the mwArea is not valid,
     * or with status 500 (Internal Server Error) if the mwArea couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-areas")
    @Timed
    public ResponseEntity<Map> updateMwArea(@RequestBody AreaDto areaDto) throws URISyntaxException {
        log.debug("REST request to update MwArea : {}", areaDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(areaDto.areaId == null || areaDto.areaId.isEmpty()) {
//        	resp.put("error", "Area Id Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (areaDto.areaName == null || areaDto.areaName.isEmpty()) {
            resp.put("error", "Area Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (areaDto.areaDescription == null || areaDto.areaDescription.isEmpty()) {
            resp.put("error", "Area Description Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (areaDto.areaStatus < 0 || areaDto.areaType < 0) {
            resp.put("error", "Area Type or Area Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwArea> respData = new HashMap<String, MwArea>();
        MwArea area = mwAreaService.updateExistingNewArea(areaDto, currUser);
        if (area == null) {
            resp.put("error", "Area Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }

        respData.put("area", area);
        return ResponseEntity.ok().body(respData);

    }

    /**
     * GET  /mw-areas : get all the mwAreas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAreas in body
     */
    @GetMapping("/mw-areas")
    @Timed
    public ResponseEntity<List<MwArea>> getAllMwAreas(Pageable pageable) {
        log.debug("REST request to get a page of MwAreas");
        List<MwArea> areas = mwAreaService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(areas);
    }

    /**
     * GET  /mw-areas/:id : get the "id" mwArea.
     *
     * @param id the id of the mwArea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwArea, or with status 404 (Not Found)
     */
    @GetMapping("/mw-areas/{id}")
    @Timed
    public ResponseEntity<AreaDto> getMwArea(@PathVariable Long id) {
        log.debug("REST request to get MwArea : {}", id);
        AreaDto mwArea = mwAreaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwArea));
    }

    @GetMapping("/area-history/{id}")
    @Timed
    public ResponseEntity<List> getMwAreaHistory(@PathVariable Long id) {
        log.debug("REST request to get MwArea : {}", id);
        List<MwArea> mwArea = mwAreaService.findAllHistory(id);
        return ResponseEntity.ok().body(mwArea);
    }

    /**
     * DELETE  /mw-areas/:id : delete the "id" mwArea.
     *
     * @param id the id of the mwArea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-areas/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwArea(@PathVariable Long id) {
        log.debug("REST request to delete MwArea : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwAreaService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/areas-by-organization/{regSeq}")
    @Timed
    public ResponseEntity<List<MwArea>> getMwAreaByOrganization(@PathVariable Long regSeq) {
        log.debug("REST request to get MwArea : {}", regSeq);
        List<MwArea> areas = mwAreaService.getAreasByOrganization(regSeq);
        return ResponseEntity.ok().body(areas);
    }

    @PutMapping("/update-area-status/{areaSeq}")
    @Timed
    public ResponseEntity<Map> updateMwAreaStatus(@PathVariable long areaSeq) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", areaSeq);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwArea> respData = new HashMap<String, MwArea>();
        MwArea reg = mwAreaService.updateStatus(areaSeq, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("area", reg);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-area-names")
    @Timed
    public ResponseEntity<List<Object[]>> getAllMwAreaNames(
    ) {
        log.debug("REST request to get a page of MwArea");
        List<Object[]> area = mwAreaService.findAllMwAreaNames();
        return ResponseEntity.ok().body(area);
    }


}
