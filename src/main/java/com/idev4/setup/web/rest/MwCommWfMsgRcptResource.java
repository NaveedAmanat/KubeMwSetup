package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCommWfMsgRcpt;
import com.idev4.setup.service.MwCommWfMsgRcptService;
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
 * REST controller for managing MwCommWfMsgRcpt.
 */
@RestController
@RequestMapping("/api")
public class MwCommWfMsgRcptResource {

    private static final String ENTITY_NAME = "mwCommWfMsgRcpt";
    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgRcptResource.class);
    private final MwCommWfMsgRcptService mwCommWfMsgRcptService;

    public MwCommWfMsgRcptResource(MwCommWfMsgRcptService mwCommWfMsgRcptService) {
        this.mwCommWfMsgRcptService = mwCommWfMsgRcptService;
    }

    /**
     * POST  /mw-comm-wf-msg-rcpts : Create a new mwCommWfMsgRcpt.
     *
     * @param mwCommWfMsgRcpt the mwCommWfMsgRcpt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCommWfMsgRcpt, or with status 400 (Bad Request) if the mwCommWfMsgRcpt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-comm-wf-msg-rcpts")
    @Timed
    public ResponseEntity<MwCommWfMsgRcpt> createMwCommWfMsgRcpt(@RequestBody MwCommWfMsgRcpt mwCommWfMsgRcpt) throws URISyntaxException {
        log.debug("REST request to save MwCommWfMsgRcpt : {}", mwCommWfMsgRcpt);
        return null;
    }

    /**
     * PUT  /mw-comm-wf-msg-rcpts : Updates an existing mwCommWfMsgRcpt.
     *
     * @param mwCommWfMsgRcpt the mwCommWfMsgRcpt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCommWfMsgRcpt,
     * or with status 400 (Bad Request) if the mwCommWfMsgRcpt is not valid,
     * or with status 500 (Internal Server Error) if the mwCommWfMsgRcpt couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-comm-wf-msg-rcpts")
    @Timed
    public ResponseEntity<MwCommWfMsgRcpt> updateMwCommWfMsgRcpt(@RequestBody MwCommWfMsgRcpt mwCommWfMsgRcpt) throws URISyntaxException {
        log.debug("REST request to update MwCommWfMsgRcpt : {}", mwCommWfMsgRcpt);
        return null;
    }

    /**
     * GET  /mw-comm-wf-msg-rcpts : get all the mwCommWfMsgRcpts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCommWfMsgRcpts in body
     */
    @GetMapping("/mw-comm-wf-msg-rcpts")
    @Timed
    public ResponseEntity<List<MwCommWfMsgRcpt>> getAllMwCommWfMsgRcpts(Pageable pageable) {
        log.debug("REST request to get a page of MwCommWfMsgRcpts");
        List<MwCommWfMsgRcpt> rcps = mwCommWfMsgRcptService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(rcps);
    }

    /**
     * GET  /mw-comm-wf-msg-rcpts/:id : get the "id" mwCommWfMsgRcpt.
     *
     * @param id the id of the mwCommWfMsgRcpt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCommWfMsgRcpt, or with status 404 (Not Found)
     */
    @GetMapping("/mw-comm-wf-msg-rcpts/{id}")
    @Timed
    public ResponseEntity<MwCommWfMsgRcpt> getMwCommWfMsgRcpt(@PathVariable Long id) {
        log.debug("REST request to get MwCommWfMsgRcpt : {}", id);
        MwCommWfMsgRcpt mwCommWfMsgRcpt = mwCommWfMsgRcptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCommWfMsgRcpt));
    }

    /**
     * DELETE  /mw-comm-wf-msg-rcpts/:id : delete the "id" mwCommWfMsgRcpt.
     *
     * @param id the id of the mwCommWfMsgRcpt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-comm-wf-msg-rcpts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwCommWfMsgRcpt(@PathVariable Long id) {
        log.debug("REST request to delete MwCommWfMsgRcpt : {}", id);
        mwCommWfMsgRcptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
