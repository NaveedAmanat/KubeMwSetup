package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwReg;
import com.idev4.setup.dto.AddressDto;
import com.idev4.setup.dto.RegionDto;
import com.idev4.setup.service.MwAddrService;
import com.idev4.setup.service.MwRegService;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing MwReg.
 */
@RestController
@RequestMapping("/api")
public class MwRegResource {

    private static final String ENTITY_NAME = "mwReg";
    private final Logger log = LoggerFactory.getLogger(MwRegResource.class);
    private final MwRegService mwRegService;
    private final MwAddrService mwAddrService;

    public MwRegResource(MwRegService mwRegService, MwAddrService mwAddrService) {
        this.mwRegService = mwRegService;
        this.mwAddrService = mwAddrService;
    }

    /**
     * POST  /mw-regs : Create a new mwReg.
     *
     * @param mwReg the mwReg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwReg, or with status 400 (Bad Request) if the mwReg has already an ID
     * @throws URISyntaxException       if the Location URI syntax is incorrect
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @PostMapping("/add-region")
    @Timed
    public ResponseEntity<Map> createMwReg(@RequestBody RegionDto mwReg) throws URISyntaxException, IllegalArgumentException, IllegalAccessException {
        log.debug("REST request to save MwReg : {}", mwReg);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwReg> respData = new HashMap<String, MwReg>();
        MwReg reg = mwRegService.addNewRegion(mwReg, currUser);

        AddressDto addrDto = new AddressDto();
        addrDto.regSeq = reg.getRegSeq();
        addrDto.houseNum = mwReg.houseNum;
        addrDto.sreet_area = mwReg.sreet_area;
        addrDto.community = mwReg.community;
        addrDto.village = mwReg.village;
        addrDto.otherDetails = mwReg.otherDetails;
        addrDto.city = mwReg.city;

        mwAddrService.saveAddress(addrDto, currUser, "Region");

        respData.put("region", reg);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT  /mw-regs : Updates an existing mwReg.
     *
     * @param mwReg the mwReg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwReg,
     * or with status 400 (Bad Request) if the mwReg is not valid,
     * or with status 500 (Internal Server Error) if the mwReg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-mw-regs")
    @Timed
    public ResponseEntity<Map> updateMwReg(@RequestBody RegionDto mwReg) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", mwReg);

        Map<String, String> resp = new HashMap<String, String>();


        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwReg> respData = new HashMap<String, MwReg>();
        MwReg reg = mwRegService.updateExitingRegion(mwReg, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("region", reg);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-regs : get all the mwRegs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwRegs in body
     */
    @GetMapping("/mw-regs")
    @Timed
    public ResponseEntity<List<MwReg>> getAllMwRegs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MwRegs");
        List<MwReg> regs = mwRegService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(regs);
    }

    /**
     * GET  /mw-regs/:id : get the "id" mwReg.
     *
     * @param id the id of the mwReg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwReg, or with status 404 (Not Found)
     */
    @GetMapping("/mw-regs/{id}")
    @Timed
    public ResponseEntity<RegionDto> getMwReg(@PathVariable Long id) {
        log.debug("REST request to get MwReg : {}", id);
        RegionDto mwReg = mwRegService.findOne(id);
        return ResponseEntity.ok().body(mwReg);
    }

    @GetMapping("/reg-history/{id}")
    @Timed
    public ResponseEntity<List> getMwRegHistory(@PathVariable Long id) {
        log.debug("REST request to get MwReg : {}", id);
        List<MwReg> mwReg = mwRegService.findAllHistory(id);
        return ResponseEntity.ok().body(mwReg);
    }

    /**
     * DELETE  /mw-regs/:id : delete the "id" mwReg.
     *
     * @param id the id of the mwReg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-regs/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwReg(@PathVariable Long id) {
        log.debug("REST request to delete MwReg : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwRegService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    @PutMapping("/update-region-status/{regSeq}")
    @Timed
    public ResponseEntity<Map> updateMwRegStatus(@PathVariable long regSeq) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", regSeq);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwReg> respData = new HashMap<String, MwReg>();
        MwReg reg = mwRegService.updateStatus(regSeq, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("region", reg);
        return ResponseEntity.ok().body(respData);
    }
}
