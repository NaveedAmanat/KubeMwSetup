package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCity;
import com.idev4.setup.dto.CityDto;
import com.idev4.setup.service.MwCityService;
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
 * REST controller for managing MwCity.
 */
@RestController
@RequestMapping("/api")
public class MwCityResource {

    private static final String ENTITY_NAME = "mwCity";
    private final Logger log = LoggerFactory.getLogger(MwCityResource.class);
    private final MwCityService mwCityService;

    public MwCityResource(MwCityService mwCityService) {
        this.mwCityService = mwCityService;
    }

    @PostMapping("/add-new-city")
    @Timed
    public ResponseEntity<Map> createMwCity(@RequestBody CityDto cityDto) throws URISyntaxException {
        log.debug("REST request to save MwCity : {}", cityDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwCity city = mwCityService.addNewCity(cityDto, currUser);
        Map<String, MwCity> respData = new HashMap<String, MwCity>();
        respData.put("city", city);
        return ResponseEntity.ok().body(respData);
    }


    @PutMapping("/update-city")
    @Timed
    public ResponseEntity<Map> updateMwCity(@RequestBody CityDto cityDto) throws URISyntaxException {
        log.debug("REST request to update MwCity : {}", cityDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwCity city = mwCityService.UpdateExistingCity(cityDto, currUser);
        if (city == null) {
            resp.put("error", "CITY Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwCity> respData = new HashMap<String, MwCity>();
        respData.put("city", city);
        return ResponseEntity.ok().body(respData);
    }

    @DeleteMapping("/delete-city/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwCity(@PathVariable Long id) {
        log.debug("REST request to delete MwCity : {}", id);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwCityService.delete(id)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "There was a problem");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/mw-cities")
    @Timed
    public ResponseEntity<Map<String, Object>> getAllMwCities(
        @RequestParam Integer pageIndex, @RequestParam Integer pageSize,
        @RequestParam(required = false) String filter, @RequestParam Boolean isCount
    ) {
        log.debug("REST request to get a page of MwCities");
        Map<String, Object> citys = mwCityService.findAllByCurrentRecord(pageIndex, pageSize, filter, isCount);
        return ResponseEntity.ok().body(citys);
    }

    @GetMapping("/all-mw-cities")
    @Timed
    public ResponseEntity<List<MwCity>> getMwCities(Pageable pageable) {
        log.debug("REST request to get all MwCities");
        List<MwCity> citys = mwCityService.findAllCitiesByCurrentRecord();

        return ResponseEntity.ok().body(citys);
    }

    @GetMapping("/mw-city/{citySeq}")
    @Timed
    public ResponseEntity<MwCity> getMwCity(@PathVariable Long citySeq) {
        log.debug("REST request to get MwCity : {}", citySeq);
        MwCity mwCity = mwCityService.findOne(citySeq);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwCity));
    }

    @GetMapping("/city-history/{citySeq}")
    @Timed
    public ResponseEntity<List> getMwCityHistory(@PathVariable Long citySeq) {
        log.debug("REST request to get MwCity : {}", citySeq);
        List<MwCity> mwCity = mwCityService.findAllHistory(citySeq);
        return ResponseEntity.ok().body(mwCity);
    }

//    @GetMapping("/get-cities-by-uc/{ucSeq}")
//    @Timed
//    public ResponseEntity<List<MwCity>> getMwCityByUcSeq(@PathVariable Long ucSeq) {
//        log.debug("REST request to get MwCity : {}", ucSeq);
//        List<MwCity> cities = mwCityService.getAllCityByUcSeq(ucSeq);
//        return ResponseEntity.ok().body(cities);
//    }
}
