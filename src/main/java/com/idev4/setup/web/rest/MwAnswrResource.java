package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAnswr;
import com.idev4.setup.dto.QuestionDto;
import com.idev4.setup.service.MwAnswrService;
import com.idev4.setup.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing MwAnswr.
 */
@RestController
@RequestMapping("/api")
public class MwAnswrResource {

    private static final String ENTITY_NAME = "mwAnswr";
    private final Logger log = LoggerFactory.getLogger(MwAnswrResource.class);
    private final MwAnswrService mwAnswrService;

    public MwAnswrResource(MwAnswrService mwAnswrService) {
        this.mwAnswrService = mwAnswrService;
    }

    /**
     * POST  /mw-answrs : Create a new mwAnswr.
     *
     * @param mwAnswr the mwAnswr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwAnswr, or with status 400 (Bad Request) if the mwAnswr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-answrs")
    @Timed
    public ResponseEntity<MwAnswr> createMwAnswr(@RequestBody MwAnswr mwAnswr) throws URISyntaxException {
        log.debug("REST request to save MwAnswr : {}", mwAnswr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwAnswr result = mwAnswrService.addNewAnswr(mwAnswr, currUser);
        return ResponseEntity.created(new URI("/api/mw-answrs/" + result.getAnswrSeq()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, ((Object) result.getAnswrSeq()).toString()))
            .body(result);
    }

    /**
     * PUT  /mw-answrs : Updates an existing mwAnswr.
     *
     * @param mwAnswr the mwAnswr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwAnswr,
     * or with status 400 (Bad Request) if the mwAnswr is not valid,
     * or with status 500 (Internal Server Error) if the mwAnswr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-answrs")
    @Timed
    public ResponseEntity<MwAnswr> updateMwAnswr(@RequestBody MwAnswr mwAnswr) throws URISyntaxException {
        log.debug("REST request to update MwAnswr : {}", mwAnswr);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwAnswr result = mwAnswrService.UpdateExistingAnswr(mwAnswr, currUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ((Object) mwAnswr.getAnswrSeq()).toString()))
            .body(result);
    }

    /**
     * GET  /mw-answrs : get all the mwAnswrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwAnswrs in body
     */
    @GetMapping("/get-answrs-list")
    @Timed
    public ResponseEntity<List<QuestionDto>> getAllMwAnswrs(Pageable pageable) {
        log.debug("REST request to get a page of MwAnswrs");
        //List<QuestionDto> anss = mwAnswrService.getAllActiveQuestions();
        //return ResponseEntity.ok().body(anss);
        return null;
    }

    /**
     * GET  /mw-answrs/:id : get the "id" mwAnswr.
     *
     * @param id the id of the mwAnswr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwAnswr, or with status 404 (Not Found)
     */
    @GetMapping("/mw-answrs/{id}")
    @Timed
    public ResponseEntity<MwAnswr> getMwAnswr(@PathVariable Long id) {
        log.debug("REST request to get MwAnswr : {}", id);
        MwAnswr mwAnswr = mwAnswrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwAnswr));
    }

    @GetMapping("/mw-answrs-by-qst-seq/{qstSeq}")
    @Timed
    public ResponseEntity<List<MwAnswr>> getMwAnswrByQstSeq(@PathVariable Long qstSeq) {
        log.debug("REST request to get MwAnswr : {}", qstSeq);
        List<MwAnswr> mwAnswrs = mwAnswrService.findAllByQstSeq(qstSeq);
        return ResponseEntity.ok().body(mwAnswrs);
    }

    /**
     * DELETE  /mw-answrs/:id : delete the "id" mwAnswr.
     *
     * @param id the id of the mwAnswr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-answrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwAnswr(@PathVariable Long id) {
        log.debug("REST request to delete MwAnswr : {}", id);
        mwAnswrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
