package com.idev4.setup.web.rest;

import com.idev4.setup.domain.MwStpCnfigVal;
import com.idev4.setup.service.MwStpCnfigValService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Added By Naveed Date - 10-05-2022
 */

@RestController
@RequestMapping("/api")
public class MwStpCnfigValResource {

    private final Logger log = LoggerFactory.getLogger(MwStpCnfigValResource.class);
    @Autowired
    private MwStpCnfigValService mwStpCnfigValService;

    @GetMapping("/get-stp-val-by-grpCd/{grpCd}")
    public List<MwStpCnfigVal> findAllStpValByGrpCd(@PathVariable String grpCd) {
        log.info("get-stp-val-by-grpCd " + grpCd);
        return mwStpCnfigValService.findByStpGrpCd(grpCd);
    }
}
