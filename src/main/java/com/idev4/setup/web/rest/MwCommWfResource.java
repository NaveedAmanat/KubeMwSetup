package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCommWf;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.service.MwCommWfService;
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
 * REST controller for managing MwCommWf.
 */
@RestController
@RequestMapping("/api")
public class MwCommWfResource {

    private static final String ENTITY_NAME = "mwCommWf";
    private final Logger log = LoggerFactory.getLogger(MwCommWfResource.class);
    private final MwCommWfService mwCommWfService;

    public MwCommWfResource(MwCommWfService mwCommWfService) {
        this.mwCommWfService = mwCommWfService;
    }

    /**
     * POST  /mw-comm-wfs : Create a new mwCommWf.
     *
     * @param mwCommWf the mwCommWf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCommWf, or with status 400 (Bad Request) if the mwCommWf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-new-comm-wfs")
    @Timed
    public ResponseEntity<Map> createMwCommWf(@RequestBody CommWorkflowDto commDto) throws URISyntaxException {
        log.debug("REST request to save MwCommWf : {}", commDto);


        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (commDto.functionKey <= 0) {
            resp.put("error", "Seems Incorrect Function Key !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.ruleName == null || commDto.ruleName.isEmpty()) {
            resp.put("error", "Rule Name is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.workflowComments == null || commDto.workflowComments.isEmpty()) {
            resp.put("error", "Workflow Comments Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.ruleCriteria == null || commDto.ruleCriteria.isEmpty()) {
            resp.put("error", "Rule Criteria Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        long commSeq = mwCommWfService.addNewCommWorkFlow(commDto, currUser);
        resp.put("commSeq", String.valueOf(commSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * PUT  /mw-comm-wfs : Updates an existing mwCommWf.
     *
     * @param mwCommWf the mwCommWf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCommWf,
     * or with status 400 (Bad Request) if the mwCommWf is not valid,
     * or with status 500 (Internal Server Error) if the mwCommWf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-comm-wfs")
    @Timed
    public ResponseEntity<Map> updateMwCommWf(@RequestBody CommWorkflowDto commDto) throws URISyntaxException {
        log.debug("REST request to update MwCommWf : {}", commDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (commDto.commWorkflowSeq <= 0) {
            resp.put("error", "Communication workflow SEQ is missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.functionKey <= 0) {
            resp.put("error", "Seems Incorrect Function Key !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.ruleName == null || commDto.ruleName.isEmpty()) {
            resp.put("error", "Rule Name is Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.workflowComments == null || commDto.workflowComments.isEmpty()) {
            resp.put("error", "Workflow Comments Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (commDto.ruleCriteria == null || commDto.ruleCriteria.isEmpty()) {
            resp.put("error", "Rule Criteria Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        long commSeq = mwCommWfService.updateExsitingCommWorkFlow(commDto, currUser);
        if (commSeq == 0) {
            resp.put("error", "Communication Workflow Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        resp.put("commSeq", String.valueOf(commSeq));
        return ResponseEntity.ok().body(resp);
    }

    /**
     * GET  /mw-comm-wfs : get all the mwCommWfs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCommWfs in body
     */
    @GetMapping("/mw-comm-wfs")
    @Timed
    public ResponseEntity<List<MwCommWf>> getAllMwCommWfs(Pageable pageable) {
        log.debug("REST request to get a page of MwCommWfs");
        List<MwCommWf> page = mwCommWfService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /mw-comm-wfs/:id : get the "id" mwCommWf.
     *
     * @param id the id of the mwCommWf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCommWf, or with status 404 (Not Found)
     */
    @GetMapping("/mw-comm-wfs/{id}")
    @Timed
    public ResponseEntity<CommWorkflowDto> getMwCommWf(@PathVariable Long id) {
        log.debug("REST request to get MwCommWf : {}", id);
        CommWorkflowDto mwCommWf = mwCommWfService.getWorkAction(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCommWf));
    }

    /**
     * DELETE  /mw-comm-wfs/:id : delete the "id" mwCommWf.
     *
     * @param id the id of the mwCommWf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-comm-wfs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwCommWf(@PathVariable Long id) {
        log.debug("REST request to delete MwCommWf : {}", id);
        mwCommWfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
