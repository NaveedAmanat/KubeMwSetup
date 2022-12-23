package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCommWfMsg;
import com.idev4.setup.dto.CommWorkflowDto;
import com.idev4.setup.service.MwCommWfMsgService;
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
 * REST controller for managing MwCommWfMsg.
 */
@RestController
@RequestMapping("/api")
public class MwCommWfMsgResource {

    private static final String ENTITY_NAME = "mwCommWfMsg";
    private final Logger log = LoggerFactory.getLogger(MwCommWfMsgResource.class);
    private final MwCommWfMsgService mwCommWfMsgService;

    public MwCommWfMsgResource(MwCommWfMsgService mwCommWfMsgService) {
        this.mwCommWfMsgService = mwCommWfMsgService;
    }

    /**
     * POST  /mw-comm-wf-msgs : Create a new mwCommWfMsg.
     *
     * @param mwCommWfMsg the mwCommWfMsg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwCommWfMsg, or with status 400 (Bad Request) if the mwCommWfMsg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/add-comm-wf-msgs")
    @Timed
    public ResponseEntity<Map> createMwCommWfMsg(@RequestBody CommWorkflowDto workflowDto) throws URISyntaxException {
        log.debug("REST request to save MwCommWfMsg : {}", workflowDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (workflowDto.workflowSeq <= 0) {
            resp.put("error", "Invalid workflow Seq Selected !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (workflowDto.action.equals("New Task")) {

            if (workflowDto.taskAssignTo == null || workflowDto.taskAssignTo.isEmpty()) {
                resp.put("error", "Missing Task Assign To !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskActionComments == null || workflowDto.taskActionComments.isEmpty()) {
                resp.put("error", "Missing Task Comments !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskDate == null) {
                resp.put("error", "Missing Task Task Date !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskDueable == null || workflowDto.taskDueable.isEmpty()) {
                resp.put("error", "Missing Task Task Dueable !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long dueDtSeq = mwCommWfMsgService.addNewWorkFlowActionTask(workflowDto, currUser);
            resp.put("dueDtSeq", String.valueOf(dueDtSeq));
            return ResponseEntity.ok().body(resp);
        } else if (workflowDto.action.equals("New SMS")) {

            if (workflowDto.messageRecpientType.toLowerCase().contains("indi")) {
                if (workflowDto.individualPhone == null || workflowDto.individualPhone.isEmpty()) {
                    resp.put("error", "Individual Phone Missing !!");
                    return ResponseEntity.badRequest().body(resp);
                }
            }
            if (workflowDto.messageText == null || workflowDto.messageText.isEmpty()) {
                resp.put("error", "Message Text Missing !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long seq = mwCommWfMsgService.addNewWorkFlowActionSms(workflowDto, currUser);
            resp.put("recpSeq", String.valueOf(seq));
            return ResponseEntity.ok().body(resp);
        } else if (workflowDto.action.equals("Email Alert")) {

            if (workflowDto.emailRecepient.toLowerCase().contains("indi")) {
                if (workflowDto.emailAddress == null || workflowDto.emailAddress.isEmpty()) {
                    resp.put("error", "Individual Email Missing !!");
                    return ResponseEntity.badRequest().body(resp);
                }
            }

            if (workflowDto.emailSubject == null || workflowDto.emailSubject.isEmpty()) {
                resp.put("error", "Missing Email Subject !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.emailText == null || workflowDto.emailText.isEmpty()) {
                resp.put("error", "Missing Email Text !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long seq = mwCommWfMsgService.addNewWorkFlowActionEmail(workflowDto, currUser);
            resp.put("recpSeq", String.valueOf(seq));
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Missing Task Type !!");
            return ResponseEntity.badRequest().body(resp);
        }

    }

    /**
     * PUT  /mw-comm-wf-msgs : Updates an existing mwCommWfMsg.
     *
     * @param mwCommWfMsg the mwCommWfMsg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwCommWfMsg,
     * or with status 400 (Bad Request) if the mwCommWfMsg is not valid,
     * or with status 500 (Internal Server Error) if the mwCommWfMsg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-comm-wf-msgs")
    @Timed
    public ResponseEntity<Map> updateMwCommWfMsg(@RequestBody CommWorkflowDto workflowDto) throws URISyntaxException {
        log.debug("REST request to update MwCommWfMsg : {}", workflowDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (workflowDto.workflowSeq <= 0) {
            resp.put("error", "Invalid workflow Seq Selected !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (workflowDto.action.equals("New Task")) {

            if (workflowDto.taskAssignTo == null || workflowDto.taskAssignTo.isEmpty()) {
                resp.put("error", "Missing Task Assign To !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskActionComments == null || workflowDto.taskActionComments.isEmpty()) {
                resp.put("error", "Missing Task Comments !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskDate == null) {
                resp.put("error", "Missing Task Task Date !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.taskDueable == null || workflowDto.taskDueable.isEmpty()) {
                resp.put("error", "Missing Task Task Dueable !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long dueDtSeq = mwCommWfMsgService.updateExistingWorkFlowActionTask(workflowDto, currUser);
            resp.put("dueDtSeq", String.valueOf(dueDtSeq));
            return ResponseEntity.ok().body(resp);
        } else if (workflowDto.action.equals("New SMS")) {

            if (workflowDto.messageRecpientType.toLowerCase().contains("indi")) {
                if (workflowDto.individualPhone == null || workflowDto.individualPhone.isEmpty()) {
                    resp.put("error", "Individual Phone Missing !!");
                    return ResponseEntity.badRequest().body(resp);
                }
            }
            if (workflowDto.messageText == null || workflowDto.messageText.isEmpty()) {
                resp.put("error", "Message Text Missing !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long seq = mwCommWfMsgService.updateExistingWorkFlowActionSms(workflowDto, currUser);
            resp.put("recpSeq", String.valueOf(seq));
            return ResponseEntity.ok().body(resp);
        } else if (workflowDto.action.equals("Email Alert")) {

            if (workflowDto.emailRecepient != null && workflowDto.emailRecepient.toLowerCase().contains("indi")) {
                if (workflowDto.emailAddress == null || workflowDto.emailAddress.isEmpty()) {
                    resp.put("error", "Individual Email Missing !!");
                    return ResponseEntity.badRequest().body(resp);
                }
            }

            if (workflowDto.emailSubject == null || workflowDto.emailSubject.isEmpty()) {
                resp.put("error", "Missing Task Assign To !!");
                return ResponseEntity.badRequest().body(resp);
            }
            if (workflowDto.emailText == null || workflowDto.emailText.isEmpty()) {
                resp.put("error", "Missing Task Assign To !!");
                return ResponseEntity.badRequest().body(resp);
            }

            long seq = mwCommWfMsgService.updateExistingWorkFlowActionEmail(workflowDto, currUser);
            if (seq == 0) {
                resp.put("error", "Missing Task Assign To !!");
                return ResponseEntity.badRequest().body(resp);
            }
            resp.put("recpSeq", String.valueOf(seq));
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Missing Task Type !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    /**
     * GET  /mw-comm-wf-msgs : get all the mwCommWfMsgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwCommWfMsgs in body
     */
    @GetMapping("/mw-comm-wf-msgs")
    @Timed
    public ResponseEntity<List<MwCommWfMsg>> getAllMwCommWfMsgs(Pageable pageable) {
        log.debug("REST request to get a page of MwCommWfMsgs");
        List<MwCommWfMsg> msgs = mwCommWfMsgService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(msgs);
    }

    /**
     * GET  /mw-comm-wf-msgs/:id : get the "id" mwCommWfMsg.
     *
     * @param id the id of the mwCommWfMsg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwCommWfMsg, or with status 404 (Not Found)
     */
    @GetMapping("/mw-comm-wf-msgs/{id}")
    @Timed
    public ResponseEntity<MwCommWfMsg> getMwCommWfMsg(@PathVariable Long id) {
        log.debug("REST request to get MwCommWfMsg : {}", id);
        MwCommWfMsg mwCommWfMsg = mwCommWfMsgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCommWfMsg));
    }

    /**
     * DELETE  /mw-comm-wf-msgs/:id : delete the "id" mwCommWfMsg.
     *
     * @param id the id of the mwCommWfMsg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mw-comm-wf-msgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMwCommWfMsg(@PathVariable Long id) {
        log.debug("REST request to delete MwCommWfMsg : {}", id);
        mwCommWfMsgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
