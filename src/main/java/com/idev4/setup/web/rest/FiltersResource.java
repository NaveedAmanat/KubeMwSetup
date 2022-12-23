package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.service.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class FiltersResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FilterService filterService;

    public FiltersResource(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping("/get-filters/{portSeq}")
    @Timed
    public ResponseEntity<Map> getAllFitlers(@PathVariable long portSeq) {
        log.debug("REST request get All Filters");
        return ResponseEntity.ok().body(filterService.getQueryFiltersForOrigination(portSeq));
    }
}
