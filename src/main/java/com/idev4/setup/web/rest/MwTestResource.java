package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwTest;
import com.idev4.setup.service.MwTestService;
import com.idev4.setup.web.rest.errors.BadRequestAlertException;
import com.idev4.setup.web.rest.util.HeaderUtil;
import com.idev4.setup.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing MwTest.
 */
@RestController
@RequestMapping("/api")
public class MwTestResource {

    private static final String ENTITY_NAME = "mwTest";
    private final Logger log = LoggerFactory.getLogger(MwTestResource.class);
    private final MwTestService mwTestService;

    public MwTestResource(MwTestService mwTestService) {
        this.mwTestService = mwTestService;
    }

    /**
     * POST  /mw-tests : Create a new mwTest.
     *
     * @param mwTest the mwTest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwTest, or with status 400 (Bad Request) if the mwTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mw-tests")
    @Timed
    public ResponseEntity<MwTest> createMwTest(@RequestBody MwTest mwTest) throws URISyntaxException {
        log.debug("===============* REST request to save MwTest : {}", mwTest);
        if (mwTest.getRefCdGrpSeq() != null) {
            throw new BadRequestAlertException("A new mwTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MwTest result = mwTestService.addNewTestGroup(mwTest);
        return ResponseEntity.created(new URI("/api/mw-tests/" + result.getRefCdGrpSeq()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getRefCdGrpSeq().toString()))
            .body(result);
    }

    /**
     * PUT  /mw-tests : Updates an existing mwTest.
     *
     * @param mwTest the mwTest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwTest,
     * or with status 400 (Bad Request) if the mwTest is not valid,
     * or with status 500 (Internal Server Error) if the mwTest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mw-tests")
    @Timed
    public ResponseEntity<MwTest> updateMwTest(@RequestBody MwTest mwTest) throws URISyntaxException {
        log.debug("************** REST request to update MwTest : {}", mwTest);
        if (mwTest.getRefCdGrpSeq() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MwTest result = mwTestService.updateTestGroup(mwTest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mwTest.getRefCdGrpSeq().toString()))
            .body(result);
    }

    /**
     * GET  /mw-tests : get all the mwTests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwTests in body
     */
    @GetMapping("/mw-tests")
    @Timed
    public ResponseEntity<List<MwTest>> getAllMwTests(Pageable pageable) {
        log.debug("REST request to get a page of MwTests");
        Page<MwTest> page = mwTestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mw-tests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mw-tests/:id : get the "id" mwTest.
     *
     * @param id the id of the mwTest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwTest, or with status 404 (Not Found)
     */
    @GetMapping("/mw-tests/{id}")
    @Timed
    public ResponseEntity<MwTest> getMwTest(@PathVariable Long id) {
        log.debug("REST request to get MwTest : {}", id);
        MwTest mwTest = mwTestService.findOne(id);
        return ResponseEntity.ok().body(mwTest);
    }

    /**
     * DELETE  /mw-tests/:id : delete the "id" mwTest.
     *
     * @param id the id of the mwTest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-tests/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwTest(@PathVariable Long id) {
        log.debug("REST request to delete MwTest : {}", id);
        mwTestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
