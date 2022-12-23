package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnch;
import com.idev4.setup.dto.AddressDto;
import com.idev4.setup.dto.BranchDto;
import com.idev4.setup.dto.BranchOpeningDto;
import com.idev4.setup.service.MwAddrService;
import com.idev4.setup.service.MwBrnchService;
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
 * REST controller for managing MwBrnch.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchResource {

    private static final String ENTITY_NAME = "mwBrnch";
    private final Logger log = LoggerFactory.getLogger(MwBrnchResource.class);
    private final MwBrnchService mwBrnchService;

    private final MwAddrService mwAddrService;

    public MwBrnchResource(MwBrnchService mwBrnchService, MwAddrService mwAddrService) {
        this.mwBrnchService = mwBrnchService;
        this.mwAddrService = mwAddrService;
    }

    /**
     * POST /mw-brnches : Create a new mwBrnch.
     *
     * @param mwBrnch the mwBrnch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBrnch, or with status 400 (Bad Request) if the mwBrnch
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-brnches")
    @Timed
    public ResponseEntity<Map> createMwBrnch(@RequestBody BranchDto branchDto) throws URISyntaxException {
        log.debug("REST request to save MwBrnch : {}", branchDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if(branchDto.branchCode == null || branchDto.branchCode.isEmpty()) {
        // resp.put("error", "branch Id or branch Code Missing !!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (branchDto.branchName == null || branchDto.branchName.isEmpty()) {
            resp.put("error", "branch Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (branchDto.branchDescription == null || branchDto.branchDescription.isEmpty()) {
            resp.put("error", "branch Description Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (branchDto.branchStatus < 0 || branchDto.branchType < 0) {
            resp.put("error", "branch Type or branch Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, MwBrnch> respData = new HashMap<String, MwBrnch>();
        MwBrnch branch = mwBrnchService.addNewBranch(branchDto, currUser);

        AddressDto addrDto = new AddressDto();
        addrDto.brnchSeq = branch.getBrnchSeq();
        addrDto.houseNum = branchDto.houseNum;
        addrDto.sreet_area = branchDto.sreet_area;
        addrDto.community = branchDto.community;
        addrDto.village = branchDto.village;
        addrDto.otherDetails = branchDto.otherDetails;
        addrDto.city = branchDto.city;

        mwAddrService.saveAddress(addrDto, currUser, "Branch");

        respData.put("branch", branch);
        return ResponseEntity.ok().body(respData);

    }

    //Added by Areeba - Branch Setup
    @PostMapping("/add-new-brnches")
    @Timed
    public ResponseEntity<Map> createNewMwBrnch(@RequestBody BranchOpeningDto branchDto) throws URISyntaxException {
        log.debug("REST request to save MwBrnch : {}", branchDto);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, MwBrnch> respData = new HashMap<String, MwBrnch>();
        MwBrnch branch = mwBrnchService.addNewBranchSetup(branchDto, currUser);

        respData.put("branch", branch);
        return ResponseEntity.ok().body(respData);

    }
    //Ended by Areeba

    /**
     * PUT /mw-brnches : Updates an existing mwBrnch.
     *
     * @param mwBrnch the mwBrnch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBrnch, or with status 400 (Bad Request) if the mwBrnch is
     * not valid, or with status 500 (Internal Server Error) if the mwBrnch couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-brnches")
    @Timed
    public ResponseEntity<Map> updateMwBrnch(@RequestBody BranchDto branchDto) throws URISyntaxException {
        log.debug("REST request to update MwBrnch : {}", branchDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if(branchDto.branchCode == null || branchDto.branchCode.isEmpty()) {
        // resp.put("error", "branch Id or branch Code Missing !!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (branchDto.branchName == null || branchDto.branchName.isEmpty()) {
            resp.put("error", "branch Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (branchDto.branchDescription == null || branchDto.branchDescription.isEmpty()) {
            resp.put("error", "branch Description Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (branchDto.branchStatus < 0 || branchDto.branchType < 0) {
            resp.put("error", "branch Type or branch Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, MwBrnch> respData = new HashMap<String, MwBrnch>();
        MwBrnch branch = mwBrnchService.updateExistingBranch(branchDto, currUser);
        if (branch == null) {
            resp.put("error", "Branch Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("branch", branch);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * GET /mw-brnches : get all the mwBrnches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBrnches in body
     */
    @GetMapping("/mw-brnches")
    @Timed
    public ResponseEntity<List<MwBrnch>> getAllMwBrnches(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnches");
        List<MwBrnch> branches = mwBrnchService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(branches);
    }

    // Added by Areeba - 27-05-2022
    @GetMapping("/mw-branches-stp")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllMwBranchesStp(
        @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
        @RequestParam(required = false) String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get a page of MwBranches");
        Map<String, Object> brnchs = mwBrnchService.findAllMwBranches(pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(brnchs);
    }
    // Ended by Areeba

    /**
     * GET /mw-brnches/:id : get the "id" mwBrnch.
     *
     * @param id the id of the mwBrnch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBrnch, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnches/{id}")
    @Timed
    public ResponseEntity<BranchDto> getMwBrnchDto(@PathVariable Long id) {
        log.debug("REST request to get MwBrnch : {}", id);
        BranchDto mwBrnch = mwBrnchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnch));
    }

    @GetMapping("/mw-brnch/{id}")
    @Timed
    public ResponseEntity<MwBrnch> getMwBrnch(@PathVariable Long id) {
        log.debug("REST request to get MwBrnch : {}", id);
        MwBrnch mwBrnch = mwBrnchService.findOnedomain(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnch));
    }

    @GetMapping("/branch-history/{id}")
    @Timed
    public ResponseEntity<List> getMwBrnchHistory(@PathVariable Long id) {
        log.debug("REST request to get MwBrnch : {}", id);
        List<MwBrnch> mwBrnch = mwBrnchService.findAllHistory(id);
        return ResponseEntity.ok().body(mwBrnch);
    }

    /**
     * DELETE /mw-brnches/:id : delete the "id" mwBrnch.
     *
     * @param id the id of the mwBrnch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-brnches/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwBrnch(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnch : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwBrnchService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            // Edited by Areeba - 27-05-2022
            resp.put("error", "Delete Portfolios First !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    @GetMapping("/brnches-by-area/{areaSeq}")
    @Timed
    public ResponseEntity<List<MwBrnch>> getMwBrnchByArea(@PathVariable Long areaSeq) {
        log.debug("REST request to get MwBrnch : {}", areaSeq);
        List<MwBrnch> branches = mwBrnchService.getBranchesByArea(areaSeq);
        return ResponseEntity.ok().body(branches);
    }

    @PutMapping("/update-brnch-status/{branchSeq}")
    @Timed
    public ResponseEntity<Map> updateMwBranchStatus(@PathVariable long branchSeq) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", branchSeq);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwBrnch> respData = new HashMap<String, MwBrnch>();
        MwBrnch reg = mwBrnchService.updateStatus(branchSeq, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("branch", reg);
        return ResponseEntity.ok().body(respData);
    }

    // BRNCH TRGT //
    @GetMapping("/brnch-perd-by-prd-grp/{brnchSeq}/{prdGrpSeq}")
    @Timed
    public ResponseEntity<List> getMwBrnchTrgt(@PathVariable Long brnchSeq, @PathVariable Long prdGrpSeq) {
        log.debug("REST request to get MwBrnchPerd : {}", brnchSeq + " prdGrpSeq" + prdGrpSeq);
        return ResponseEntity.ok().body(mwBrnchService.getPerdForBrnch(brnchSeq, prdGrpSeq));
    }

    @GetMapping("/brnch-trgt-by-prd-grp/{brnchSeq}/{brnchTrgtSeq}")
    @Timed
    public ResponseEntity<List> getPortTrgtForBrnch(@PathVariable Long brnchSeq, @PathVariable Long brnchTrgtSeq) {
        log.debug("REST request to get MwBrnchPerd : {}", brnchSeq + " brnchTrgtSeq" + brnchTrgtSeq);
        return ResponseEntity.ok().body(mwBrnchService.getPortTrgtForBrnch(brnchSeq, brnchTrgtSeq));
    }

    // @PostMapping ( "/add-brnches" )
    // @Timed
    // public ResponseEntity< Map > addPortTrgts( @RequestBody BranchDto branchDto ) throws URISyntaxException {
    //
    // }

    @GetMapping("/mw-brnches-by-user")
    @Timed
    public ResponseEntity<List<MwBrnch>> getAllMwBrnchesByUser() {
        log.debug("REST request to get a page of MwBrnches");
        List<MwBrnch> branches = mwBrnchService.findAllBrnchForUsr();
        return ResponseEntity.ok().body(branches);
    }

    @GetMapping("/get-branch-by-region/{regSeq}")
    @Timed
    public ResponseEntity<List<MwBrnch>> getAllBranchByRegion(@PathVariable long regSeq) {
        List<MwBrnch> branches = mwBrnchService.getAllBrnchByRegion(regSeq);
        return ResponseEntity.ok().body(branches);
    }

    //Added by Areeba - Branch Setup
    @PostMapping("/add-brnch-addr-info")
    @Timed
    public ResponseEntity<Map> addBranchAddrInfo(@RequestBody BranchOpeningDto branchDto) throws URISyntaxException {
        log.debug("REST request to save MwBrnch : {}", branchDto);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwBrnchService.addNewBranchAddrInfo(branchDto, currUser);

        if (info == 1) {
            respData.put("branch", "Successfully added.");
        } else if (info == 0) {
            respData.put("branch", "Could not be added.");
        }
        return ResponseEntity.ok().body(respData);

    }

    @GetMapping("/get-mw-brnch-data/{brnchSeq}")
    @Timed
    public ResponseEntity<BranchOpeningDto> getMwBrnchData(@PathVariable Long brnchSeq) {
        log.debug("REST request to get MwBrnch : {}", brnchSeq);
        BranchOpeningDto mwBrnch = mwBrnchService.getMwBrnchData(brnchSeq);
        return ResponseEntity.ok().body(mwBrnch);
    }
    //Ended by Areeba

    @DeleteMapping("/mw-ucs-brnch/{id}/{citySeq}/{brnchSeq}")
    @Timed
    public ResponseEntity<Void> removeMwUc(@PathVariable Long id, @PathVariable Long citySeq, @PathVariable Long brnchSeq) {
        log.debug("REST request to remove MwUc : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        mwBrnchService.removeFromBrnch(id, currUser, citySeq, brnchSeq);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    //Added by Areeba - Add Ucs to Branch - 1-12-2022
    @PostMapping("/add-uc-to-brnch")
    @Timed
    public ResponseEntity<Map> addUCsToBrnch(@RequestBody BranchOpeningDto branchDto) throws URISyntaxException {
        log.debug("REST request to save BrnchLocRels : {}", branchDto);

        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, String> respData = new HashMap<String, String>();
        Integer info = mwBrnchService.addUCsToBrnch(branchDto, currUser);

        if (info == 1) {
            respData.put("ucs", "Successfully added.");
            return ResponseEntity.ok().body(respData);
        } else if (info == 0) {
            respData.put("error", "Incomplete Data.");
            return ResponseEntity.badRequest().body(respData);
        } else {
            respData.put("error", "Could not be added.");
            return ResponseEntity.badRequest().body(respData);
        }

    }

}
