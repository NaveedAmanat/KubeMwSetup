package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCommWfMsgDueDt;
import com.idev4.setup.service.MwCommWfMsgDueDtService;
import com.idev4.setup.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MwCommWfMsgDueDt.
 */
@RestController
@RequestMapping("/api")
public class MwCommWfMsgDueDtResource {

    private static final String ENTITY_NAME = "mwCommWfMsgDueDt";
    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgDueDtResource.class);
    private final MwCommWfMsgDueDtService mwCommWfMsgDueDtService;

    public MwCommWfMsgDueDtResource(MwCommWfMsgDueDtService mwCommWfMsgDueDtService) {
        this.mwCommWfMsgDueDtService = mwCommWfMsgDueDtService;
    }

    /**
     * POST  /mw-comm-wf-msg-due-dts : Create a new mwCommWfMsgDueDt.
     *
     * @param mwCommWfMsgDueDt the mwCommWfMsgDueDt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCommWfMsgDueDt, or with status 400 (Bad Request) if the mwCommWfMsgDueDt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-comm-wf-msg-due-dts")
    @Timed
    public ResponseEntity<MwCommWfMsgDueDt> createMwCommWfMsgDueDt(@RequestBody MwCommWfMsgDueDt mwCommWfMsgDueDt) throws URISyntaxException {
        log.debug("REST request to save MwCommWfMsgDueDt : {}", mwCommWfMsgDueDt);
        return null;
    }

    /**
     * PUT  /mw-comm-wf-msg-due-dts : Updates an existing mwCommWfMsgDueDt.
     *
     * @param mwCommWfMsgDueDt the mwCommWfMsgDueDt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCommWfMsgDueDt,
     * or with status 400 (Bad Request) if the mwCommWfMsgDueDt is not valid,
     * or with status 500 (Internal Server Error) if the mwCommWfMsgDueDt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-comm-wf-msg-due-dts")
    @Timed
    public ResponseEntity<MwCommWfMsgDueDt> updateMwCommWfMsgDueDt(@RequestBody MwCommWfMsgDueDt mwCommWfMsgDueDt) throws URISyntaxException {
        log.debug("REST request to update MwCommWfMsgDueDt : {}", mwCommWfMsgDueDt);
        return null;
    }

    /**
     * GET  /mw-comm-wf-msg-due-dts : get all the mwCommWfMsgDueDts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCommWfMsgDueDts in body
     */
    @GetMapping("/mw-comm-wf-msg-due-dts")
    @Timed
    public ResponseEntity<List<MwCommWfMsgDueDt>> getAllMwCommWfMsgDueDts(Pageable pageable) {
        log.debug("REST request to get a page of MwCommWfMsgDueDts");
        List<MwCommWfMsgDueDt> page = mwCommWfMsgDueDtService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /mw-comm-wf-msg-due-dts/:id : get the "id" mwCommWfMsgDueDt.
     *
     * @param id the id of the mwCommWfMsgDueDt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCommWfMsgDueDt, or with status 404 (Not Found)
     */
    @GetMapping("/mw-comm-wf-msg-due-dts/{id}")
    @Timed
    public ResponseEntity<MwCommWfMsgDueDt> getMwCommWfMsgDueDt(@PathVariable Long id) {
        log.debug("REST request to get MwCommWfMsgDueDt : {}", id);
        MwCommWfMsgDueDt mwCommWfMsgDueDt = mwCommWfMsgDueDtService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCommWfMsgDueDt));
    }

    /**
     * DELETE  /mw-comm-wf-msg-due-dts/:id : delete the "id" mwCommWfMsgDueDt.
     *
     * @param id the id of the mwCommWfMsgDueDt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-comm-wf-msg-due-dts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwCommWfMsgDueDt(@PathVariable Long id) {
        log.debug("REST request to delete MwCommWfMsgDueDt : {}", id);
        mwCommWfMsgDueDtService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
