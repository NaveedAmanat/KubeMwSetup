package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwTags;
import com.idev4.setup.dto.TagsDto;
import com.idev4.setup.service.MwTagsService;
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
 * REST controller for managing MwTags.
 */
@RestController
@RequestMapping("/api")
public class MwTagsResource {

    private static final String ENTITY_NAME = "mwTags";
    private final Logger log = LoggerFactory.getLogger(MwTagsResource.class);
    private final MwTagsService mwTagsService;

    public MwTagsResource(MwTagsService mwTagsService) {
        this.mwTagsService = mwTagsService;
    }

    @PostMapping("/add-new-tags")
    @Timed
    public ResponseEntity<Map> createMwTags(@RequestBody TagsDto tagsDto) throws URISyntaxException {
        log.debug("REST request to save MwTags : {}", tagsDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwTags tags = mwTagsService.addNewTags(tagsDto, currUser);
        Map<String, MwTags> respData = new HashMap<String, MwTags>();
        respData.put("tags", tags);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-tags")
    @Timed
    public ResponseEntity<Map> updateMwTags(@RequestBody TagsDto tagsDto) throws URISyntaxException {
        log.debug("REST request to update MwTags : {}", tagsDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwTags tags = mwTagsService.UpdateExistingTags(tagsDto, currUser);
        if (tags == null) {
            resp.put("error", "TAGS Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwTags> respData = new HashMap<String, MwTags>();
        respData.put("tags", tags);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-tags")
    @Timed
    public ResponseEntity<List<MwTags>> getAllMwTags(Pageable pageable) {
        log.debug("REST request to get a page of MwTags");
        List<MwTags> tags = mwTagsService.findAllByCurrentRecord();
        return ResponseEntity.ok().body(tags);
    }

    @GetMapping("/mw-tags/{tagSeq}")
    @Timed
    public ResponseEntity<MwTags> getMwTags(@PathVariable Long tagSeq) {
        log.debug("REST request to get MwTags : {}", tagSeq);
        MwTags mwTags = mwTagsService.findOne(tagSeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwTags));
    }

    @GetMapping("/tags-history/{tagSeq}")
    @Timed
    public ResponseEntity<List> getMwTagsHistory(@PathVariable Long tagSeq) {
        log.debug("REST request to get MwTags : {}", tagSeq);
        List<MwTags> mwTags = mwTagsService.findAllHistory(tagSeq);
        return ResponseEntity.ok().body(mwTags);
    }

    @DeleteMapping("/mw-tags/{tagSeq}")
    @Timed
    public ResponseEntity<Void> deleteMwTags(@PathVariable Long tagSeq) {
        log.debug("REST request to delete MwTags : {}", tagSeq);
        mwTagsService.delete(tagSeq);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, tagSeq.toString())).build();
    }
}
