package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwBrnchAcctSet;
import com.idev4.setup.dto.BranchAcctDto;
import com.idev4.setup.service.MwBrnchAcctSetService;
import com.idev4.setup.web.rest.util.HeaderUtil;
import com.idev4.setup.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwBrnchAcctSet.
 */
@RestController
@RequestMapping("/api")
public class MwBrnchAcctSetResource {

    private static final String ENTITY_NAME = "mwBrnchAcctSet";
    private final Logger log = LoggerFactory.getLogger(MwBrnchAcctSetResource.class);
    private final MwBrnchAcctSetService mwBrnchAcctSetService;

    public MwBrnchAcctSetResource(MwBrnchAcctSetService mwBrnchAcctSetService) {
        this.mwBrnchAcctSetService = mwBrnchAcctSetService;
    }

    /**
     * POST  /mw-brnch-acct-sets : Create a new mwBrnchAcctSet.
     *
     * @param mwBrnchAcctSet the mwBrnchAcctSet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwBrnchAcctSet, or with status 400 (Bad Request) if the mwBrnchAcctSet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-brnch-acct-sets")
    @Timed
    public ResponseEntity<Map> createMwBrnchAcctSet(@RequestBody BranchAcctDto dto) throws URISyntaxException {
        log.debug("REST request to save MwBrnchAcctSet : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.branchSeq <= 0) {
            resp.put("error", "Branch Sequence Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.bankBranch == null || dto.bankBranch.isEmpty()) {
            resp.put("error", "Bank Branch Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.bankName == null || dto.bankName.isEmpty()) {
            resp.put("error", "Branch Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.accNo == null || dto.accNo.isEmpty()) {
            resp.put("error", "Account No Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.accTitle == null || dto.accTitle.isEmpty()) {
            resp.put("error", "Account Title Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.ibanNo == null || dto.ibanNo.isEmpty()) {
            resp.put("error", "IBAN Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long accSeq = mwBrnchAcctSetService.addNewBankAccToBranch(dto, currUser);
        resp.put("accSeq", String.valueOf(accSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-brnch-acct-sets : Updates an existing mwBrnchAcctSet.
     *
     * @param mwBrnchAcctSet the mwBrnchAcctSet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwBrnchAcctSet,
     * or with status 400 (Bad Request) if the mwBrnchAcctSet is not valid,
     * or with status 500 (Internal Server Error) if the mwBrnchAcctSet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-brnch-acct-set")
    @Timed
    public ResponseEntity<Map> updateMwBrnchAcctSet(@RequestBody BranchAcctDto dto) throws URISyntaxException {
        log.debug("REST request to update MwBrnchAcctSet : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (dto.branchSeq <= 0) {
            resp.put("error", "Branch Sequence Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.bankBranch == null || dto.bankBranch.isEmpty()) {
            resp.put("error", "Bank Branch Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.bankName == null || dto.bankName.isEmpty()) {
            resp.put("error", "Branch Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.accNo == null || dto.accNo.isEmpty()) {
            resp.put("error", "Account No Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.accTitle == null || dto.accTitle.isEmpty()) {
            resp.put("error", "Account Title Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        if (dto.ibanNo == null || dto.ibanNo.isEmpty()) {
            resp.put("error", "IBAN Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        long accSeq = mwBrnchAcctSetService.updateBankAccToBranch(dto, currUser);
        if (accSeq == 0) {
            resp.put("error", "No Account Setup Found to Update !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("accSeq", String.valueOf(accSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-brnch-acct-sets : get all the mwBrnchAcctSets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwBrnchAcctSets in body
     */
    @GetMapping("/mw-brnch-acct-sets")
    @Timed
    public ResponseEntity<List<MwBrnchAcctSet>> getAllMwBrnchAcctSets(Pageable pageable) {
        log.debug("REST request to get a page of MwBrnchAcctSets");
        Page<MwBrnchAcctSet> page = mwBrnchAcctSetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-brnch-acct-sets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-brnch-acct-sets/:id : get the "id" mwBrnchAcctSet.
     *
     * @param id the id of the mwBrnchAcctSet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwBrnchAcctSet, or with status 404 (Not Found)
     */
    @GetMapping("/mw-brnch-acct-sets/{id}")
    @Timed
    public ResponseEntity<MwBrnchAcctSet> getMwBrnchAcctSet(@PathVariable Long id) {
        log.debug("REST request to get MwBrnchAcctSet : {}", id);
        MwBrnchAcctSet mwBrnchAcctSet = mwBrnchAcctSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnchAcctSet));
    }

    /**
     * DELETE  /mw-brnch-acct-sets/:id : delete the "id" mwBrnchAcctSet.
     *
     * @param id the id of the mwBrnchAcctSet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-brnch-acct-sets/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwBrnchAcctSet(@PathVariable Long id) {
        log.debug("REST request to delete MwBrnchAcctSet : {}", id);
        mwBrnchAcctSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/brnch-acct-set-by-branch/{branchSeq}")
    @Timed
    public ResponseEntity<MwBrnchAcctSet> getMwBrnchAcctSetByBranchSeq(@PathVariable Long branchSeq) {
        log.debug("REST request to get MwBrnchAcctSet : {}", branchSeq);
        MwBrnchAcctSet mwBrnchAcctSet = mwBrnchAcctSetService.findOneByBranchSeq(branchSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwBrnchAcctSet));
    }
}
