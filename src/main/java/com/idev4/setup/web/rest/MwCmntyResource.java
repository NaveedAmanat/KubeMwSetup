package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCmnty;
import com.idev4.setup.dto.CommunityDto;
import com.idev4.setup.service.MwCmntyService;
import com.idev4.setup.web.rest.util.HeaderUtil;
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
 * REST controller for managing MwCmnty.
 */
@RestController
@RequestMapping("/api")
public class MwCmntyResource {

    private static final String ENTITY_NAME = "mwCmnty";
    private final Logger log = LoggerFactory.getLogger(MwCmntyResource.class);
    private final MwCmntyService mwCmntyService;

    public MwCmntyResource(MwCmntyService mwCmntyService) {
        this.mwCmntyService = mwCmntyService;
    }

    /**
     * POST  /mw-cmnties : Create a new mwCmnty.
     *
     * @param mwCmnty the mwCmnty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCmnty, or with status 400 (Bad Request) if the mwCmnty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-cmnty")
    @Timed
    public ResponseEntity<Map> createMwCmnty(@RequestBody CommunityDto cmntyDto) throws URISyntaxException {
        log.debug("REST request to save MwCmnty : {}", cmntyDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(cmntyDto.cmntyCode == null || cmntyDto.cmntyCode.isEmpty()) {
//        	resp.put("error", "Community Id or Community Code Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (cmntyDto.cmntyName == null || cmntyDto.cmntyName.isEmpty()) {
            resp.put("error", "Community Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (cmntyDto.portfolioSeq < 0) {
            resp.put("error", "Portfolio Id Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, MwCmnty> respData = new HashMap<String, MwCmnty>();
        MwCmnty cmnty = mwCmntyService.addNewCommunity(cmntyDto, currUser);
        respData.put("cmnty", cmnty);
        return ResponseEntity.ok().body(respData);

    }

    /**
     * PUT  /mw-cmnties : Updates an existing mwCmnty.
     *
     * @param mwCmnty the mwCmnty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCmnty,
     * or with status 400 (Bad Request) if the mwCmnty is not valid,
     * or with status 500 (Internal Server Error) if the mwCmnty couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-cmnties")
    @Timed
    public ResponseEntity<Map> updateMwCmnty(@RequestBody CommunityDto cmntyDto) throws URISyntaxException {
        log.debug("REST request to update MwCmnty : {}", cmntyDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

//        if(cmntyDto.cmntyCode == null || cmntyDto.cmntyCode.isEmpty()) {
//        	resp.put("error", "Community Id or Community Code Missing !!");
//        	return ResponseEntity.badRequest().body(resp);
//        }
        if (cmntyDto.cmntyName == null || cmntyDto.cmntyName.isEmpty()) {
            resp.put("error", "Community Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (cmntyDto.portfolioSeq < 0) {
            resp.put("error", "Portfolio Id Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, MwCmnty> respData = new HashMap<String, MwCmnty>();
        MwCmnty cmnty = mwCmntyService.updateExistingCommunity(cmntyDto, currUser);
        if (cmnty == null) {
            resp.put("error", "Community Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("cmnty", cmnty);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET  /mw-cmnties : get all the mwCmnties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCmnties in body
     */
    @GetMapping("/mw-cmnties")
    @Timed
    public ResponseEntity<List<MwCmnty>> getAllMwCmnties(Pageable pageable) {
        log.debug("REST request to get a page of MwCmnties");
        List<MwCmnty> cmts = mwCmntyService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(cmts);
    }

    /**
     * GET  /mw-cmnties/:id : get the "id" mwCmnty.
     *
     * @param id the id of the mwCmnty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCmnty, or with status 404 (Not Found)
     */
    @GetMapping("/mw-cmnties/{id}")
    @Timed
    public ResponseEntity<MwCmnty> getMwCmnty(@PathVariable Long id) {
        log.debug("REST request to get MwCmnty : {}", id);
        MwCmnty mwCmnty = mwCmntyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCmnty));
    }

    @GetMapping("/cmnty-history/{id}")
    @Timed
    public ResponseEntity<List> getMwCmntyHistory(@PathVariable Long id) {
        log.debug("REST request to get MwCmnty : {}", id);
        List<MwCmnty> mwCmnty = mwCmntyService.findAllHistory(id);
        return ResponseEntity.ok().body(mwCmnty);
    }

    /**
     * DELETE  /mw-cmnties/:id : delete the "id" mwCmnty.
     *
     * @param id the id of the mwCmnty to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    // Edited by Areeba - 27-05-2022
    @DeleteMapping("/mw-cmnties/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwCmnty(@PathVariable Long id) {
        log.debug("REST request to delete MwCmnty : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        mwCmntyService.delete(id, currUser);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/get-cmnties-by-portfolio/{portfolioSeq}")
    @Timed
    public ResponseEntity<List<MwCmnty>> getMwCmntyByPortfolio(@PathVariable Long portfolioSeq) {
        log.debug("REST request to get MwCmnty : {}", portfolioSeq);
        List<MwCmnty> cmnties = mwCmntyService.findCommunitesByPortfolioSeq(portfolioSeq);

        return ResponseEntity.ok().body(cmnties);
    }

    @GetMapping("/get-employee-cmnties")
    @Timed
    public ResponseEntity<List<MwCmnty>> getAllMwCmntiesOfEmployeeArea() {
        log.debug("REST request to get a page of MwCmnties");
        List<MwCmnty> cmts = mwCmntyService.getCommunitiesOfAllowedArea();
        return ResponseEntity.ok().body(cmts);
    }

    @PutMapping("/update-community-status/{communitySeq}")
    @Timed
    public ResponseEntity<Map> updateMwCommunityStatus(@PathVariable long communitySeq) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", communitySeq);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwCmnty> respData = new HashMap<String, MwCmnty>();
        MwCmnty reg = mwCmntyService.updateStatus(communitySeq, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("community", reg);
        return ResponseEntity.ok().body(respData);
    }

    //Added by Areeba - Branch Setup
    @PostMapping("/upload-cmnty")
    @Timed
    public ResponseEntity<Map> uploadCmnty(@RequestBody List<CommunityDto> dto) {

        Map<String, Object> resp = mwCmntyService.makeDataSet(dto);

        return ResponseEntity.ok().body(resp);
    }
    //Ended by Areeba
}
