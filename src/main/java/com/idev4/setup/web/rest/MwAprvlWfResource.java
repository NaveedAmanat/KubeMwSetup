package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAprvlWf;
import com.idev4.setup.dto.AprovalWorkflowDto;
import com.idev4.setup.service.MwAprvlWfService;
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
 * REST controller for managing MwAprvlWf.
 */
@RestController
@RequestMapping("/api")
public class MwAprvlWfResource {

    private static final String ENTITY_NAME = "mwAprvlWf";
    private final Logger log = LoggerFactory.getLogger(MwAprvlWfResource.class);
    private final MwAprvlWfService mwAprvlWfService;

    public MwAprvlWfResource(MwAprvlWfService mwAprvlWfService) {
        this.mwAprvlWfService = mwAprvlWfService;
    }

    /**
     * POST  /mw-aprvl-wfs : Create a new mwAprvlWf.
     *
     * @param mwAprvlWf the mwAprvlWf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwAprvlWf, or with status 400 (Bad Request) if the mwAprvlWf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-aprvl-wfs")
    @Timed
    public ResponseEntity<Map> createMwAprvlWf(@RequestBody AprovalWorkflowDto mwAprvlWf) throws URISyntaxException {
        log.debug("REST request to save MwAprvlWf : {}", mwAprvlWf);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        long aprvlSeq = mwAprvlWfService.addNewApprovalWorkflow(mwAprvlWf, currUser);
        resp.put("aprvlSeq", String.valueOf(aprvlSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-aprvl-wfs : Updates an existing mwAprvlWf.
     *
     * @param mwAprvlWf the mwAprvlWf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwAprvlWf,
     * or with status 400 (Bad Request) if the mwAprvlWf is not valid,
     * or with status 500 (Internal Server Error) if the mwAprvlWf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-aprvl-wfs")
    @Timed
    public ResponseEntity<MwAprvlWf> updateMwAprvlWf(@RequestBody MwAprvlWf mwAprvlWf) throws URISyntaxException {
        log.debug("REST request to update MwAprvlWf : {}", mwAprvlWf);
        return null;
    }

    /**
     * GET  /mw-aprvl-wfs : get all the mwAprvlWfs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAprvlWfs in body
     */
    @GetMapping("/mw-aprvl-wfs")
    @Timed
    public ResponseEntity<List<MwAprvlWf>> getAllMwAprvlWfs(Pageable pageable) {
        log.debug("REST request to get a page of MwAprvlWfs");
        List<MwAprvlWf> wfs = mwAprvlWfService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(wfs);
    }

    /**
     * GET  /mw-aprvl-wfs/:id : get the "id" mwAprvlWf.
     *
     * @param id the id of the mwAprvlWf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwAprvlWf, or with status 404 (Not Found)
     */
    @GetMapping("/mw-aprvl-wfs/{id}")
    @Timed
    public ResponseEntity<MwAprvlWf> getMwAprvlWf(@PathVariable Long id) {
        log.debug("REST request to get MwAprvlWf : {}", id);
        MwAprvlWf mwAprvlWf = mwAprvlWfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwAprvlWf));
    }

    /**
     * DELETE  /mw-aprvl-wfs/:id : delete the "id" mwAprvlWf.
     *
     * @param id the id of the mwAprvlWf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-aprvl-wfs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwAprvlWf(@PathVariable Long id) {
        log.debug("REST request to delete MwAprvlWf : {}", id);
        mwAprvlWfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
