package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwCityUcRel;
import com.idev4.setup.service.MwCityUcRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MwCityUcRelResource {

    private final Logger log = LoggerFactory.getLogger(MwCityUcRelResource.class);

    private final MwCityUcRelService mwCityUcRelService;

    public MwCityUcRelResource(MwCityUcRelService mwCityUcRelService) {
        this.mwCityUcRelService = mwCityUcRelService;
    }

    @PostMapping("/add-new-city-uc-rel")
    @Timed
    public ResponseEntity<MwCityUcRel> createMwCityUcRel(@RequestBody MwCityUcRel dto) throws URISyntaxException {
        log.debug("REST request to save MwCityUcRel : {}", dto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwCityUcRel rul = mwCityUcRelService.addNewCityUcRel(dto, currUser);
        return ResponseEntity.ok().body(rul);
    }

    @PutMapping("/update-new-city-uc-rel")
    @Timed
    public ResponseEntity<Map> updateCityUcRel(@RequestBody MwCityUcRel dto) throws URISyntaxException {
        log.debug("REST request to update MwCityUcRel : {}", dto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        MwCityUcRel mwCityUcRel = mwCityUcRelService.updateCityUcRel(dto, currUser);
        if (mwCityUcRel == null) {
            resp.put("error", "CityUcRel Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        Map<String, MwCityUcRel> respData = new HashMap<String, MwCityUcRel>();
        respData.put("MwCityUcRel", mwCityUcRel);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-city-uc-rel-by-city/{citySeq}")
    @Timed
    public ResponseEntity<List<MwCityUcRel>> getCityUcRelBySeq(@PathVariable Long citySeq) {
        log.debug("REST request to get All MwCityUcRel : {}", citySeq);
        List<MwCityUcRel> mwCityUcRel = mwCityUcRelService.findAllByCitySeq(citySeq);
        return ResponseEntity.ok().body(mwCityUcRel);
    }

    @GetMapping("/mw-all-city-uc-rel")
    @Timed
    public ResponseEntity<List> getCityUcRel() {
        log.debug("REST request to get All MwCityUcRel : {}");
        List<MwCityUcRel> mwCityUcRel = mwCityUcRelService.findAllByCrntRecFlg();
        return ResponseEntity.ok().body(mwCityUcRel);
    }


//    @GetMapping("/mw-biz-acty-history/{CityUcRelSeq}")
//    @Timed
//    public ResponseEntity<List> getCityUcRelHistory(@PathVariable Long CityUcRelSeq) {
//        log.debug("REST request to get MwCityUcRel : {}", CityUcRelSeq);
//        List<MwCityUcRel> mwCityUcRel = mwCityUcRelService.findAllByCityUcRelSeq(CityUcRelSeq);
//        return ResponseEntity.ok().body(mwCityUcRel);
//    }

    @DeleteMapping("/mw-city-uc-rel/{CityUcRelSeq}")
    @Timed
    public ResponseEntity<Map> deleteCityUcRel(@PathVariable Long CityUcRelSeq) {
        log.debug("REST request to delete MwCityUcRel : {}", CityUcRelSeq);
        Map<String, String> resp = new HashMap<String, String>();
        mwCityUcRelService.delete(CityUcRelSeq);
        resp.put("data", "Deleted Successfully !!");
        return ResponseEntity.ok().body(resp);
    }

//    @GetMapping("/mw-biz-acty-sect/{bizSectSeq}")
//    @Timed
//    public ResponseEntity<List> getCityUcRelBySect(@PathVariable Long bizSectSeq) {
//        log.debug("REST request to get bizSectSeq : {}", bizSectSeq);
//        List<MwCityUcRel> mwCityUcRel = mwCityUcRelService.findAllBySectSeq(bizSectSeq);
//        return ResponseEntity.ok().body(mwCityUcRel);
//    }
}

