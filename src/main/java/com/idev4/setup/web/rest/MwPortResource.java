package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPort;
import com.idev4.setup.dto.PortDto;
import com.idev4.setup.dto.PortTrgtReqDto;
import com.idev4.setup.dto.RegionDto;
import com.idev4.setup.service.MwPortService;
import com.idev4.setup.service.MwPortTrgtService;
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
 * REST controller for managing MwPort.
 */
@RestController
@RequestMapping("/api")
public class MwPortResource {

    private static final String ENTITY_NAME = "mwPort";
    private final Logger log = LoggerFactory.getLogger(MwPortResource.class);
    private final MwPortService mwPortService;

    private final MwPortTrgtService mwPortTrgtService;

    public MwPortResource(MwPortService mwPortService, MwPortTrgtService mwPortTrgtService) {
        this.mwPortService = mwPortService;
        this.mwPortTrgtService = mwPortTrgtService;
    }

    /**
     * POST /mw-ports : Create a new mwPort.
     *
     * @param mwPort the mwPort to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mwPort, or with status 400 (Bad Request) if the mwPort has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/add-new-portfolio")
    @Timed
    public ResponseEntity<Map> createMwPort(@RequestBody PortDto portDto) throws URISyntaxException {
        log.debug("REST request to save MwPort : {}", portDto);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Edited by Areeba - 27-05-2022
//        if(portDto.portfolioId == null || portDto.portfolioId.isEmpty()) {
//            resp.put("error", "Port Id or Port Code Missing !!");
//            return ResponseEntity.badRequest().body(resp);
//        }
//        if ( portDto.portfolioName == null || portDto.portfolioName.isEmpty() ) {
//            resp.put( "error", "Port Name Missing !!" );
//            return ResponseEntity.badRequest().body( resp );
//        }
//        if ( portDto.portfolioStatus < 0 || portDto.branchSeq < 0 ) {
//            resp.put( "error", "Port Type or Port Status Missing !!" );
//            return ResponseEntity.badRequest().body( resp );
//        }

        Map<String, MwPort> respData = new HashMap<String, MwPort>();
        MwPort port = mwPortService.addNewPortfolioToBranch(portDto, currUser);
        respData.put("port", port);
        return ResponseEntity.ok().body(respData);
    }

    /**
     * PUT /mw-ports : Updates an existing mwPort.
     *
     * @param mwPort the mwPort to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mwPort, or with status 400 (Bad Request) if the mwPort is
     * not valid, or with status 500 (Internal Server Error) if the mwPort couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/update-portfolio")
    @Timed
    public ResponseEntity<Map> updateMwPort(@RequestBody PortDto portDto) throws URISyntaxException {
        log.debug("REST request to update MwPort : {}", portDto);
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // if(portDto.portfolioSeq == null || portDto.portfolioId.isEmpty()) {
        // resp.put("error", "Port Id or Port Code Missing !!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (portDto.portfolioName == null || portDto.portfolioName.isEmpty()) {
            resp.put("error", "Port Name Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (portDto.portfolioStatus < 0 || portDto.branchSeq < 0) {
            resp.put("error", "Port Type or Port Status Missing !!");
            return ResponseEntity.badRequest().body(resp);
        }

        Map<String, String> respData = new HashMap<String, String>();
        String port = mwPortService.updateExistingPortfolioToBranch(portDto, currUser);
        if (port == null || port.equals("")) {
            resp.put("error", "Portfolio issue, Contact Admin !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("port", port);
        return ResponseEntity.ok().body(respData);

    }

    /**
     * GET /mw-ports : get all the mwPorts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mwPorts in body
     */
    @GetMapping("/mw-ports")
    @Timed
    public ResponseEntity<List<MwPort>> getAllMwPorts(Pageable pageable) {
        log.debug("REST request to get a page of MwPorts");
        List<MwPort> ports = mwPortService.findAllByCurrentRecord();

        return ResponseEntity.ok().body(ports);
    }

    /**
     * GET /mw-ports/:id : get the "id" mwPort.
     *
     * @param id the id of the mwPort to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mwPort, or with status 404 (Not Found)
     */
    @GetMapping("/mw-ports/{id}")
    @Timed
    public ResponseEntity<MwPort> getMwPort(@PathVariable Long id) {
        log.debug("REST request to get MwPort : {}", id);
        MwPort mwPort = mwPortService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwPort));
    }

    @GetMapping("/mw-ports-for-user/{id}")
    @Timed
    public ResponseEntity<List<RegionDto>> getMwPort(@PathVariable String id) {
        log.debug("REST request to get MwPort : {}", id);
        List<RegionDto> mwPort = mwPortService.findPortsForUser(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mwPort));
    }

    @GetMapping("/port-hisotry/{id}")
    @Timed
    public ResponseEntity<List> getMwPortHistory(@PathVariable Long id) {
        log.debug("REST request to get MwPort : {}", id);
        List<MwPort> mwPort = mwPortService.findAllHistory(id);
        return ResponseEntity.ok().body(mwPort);
    }

    /**
     * DELETE /mw-ports/:id : delete the "id" mwPort.
     *
     * @param id the id of the mwPort to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    // Edited by Areeba - 27-05-2022
    @DeleteMapping("/mw-ports/{id}")
    @Timed
    public ResponseEntity<Map> deleteMwPort(@PathVariable Long id) {
        log.debug("REST request to delete MwPort : {}", id);
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPortService.delete(id, currUser)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child Communities First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/mw-ports-by-branch/{branchSeq}")
    @Timed
    public ResponseEntity<List<MwPort>> getMwPortByBranch(@PathVariable Long branchSeq) {
        log.debug("REST request to get MwPort : {}", branchSeq);

        List<MwPort> portfolios = mwPortService.getPortfolioByBranch(branchSeq);
        return ResponseEntity.ok().body(portfolios);
    }

    @PutMapping("/update-portfolio-status/{portSeq}")
    @Timed
    public ResponseEntity<Map> updateMwPortStatus(@PathVariable long portSeq) throws URISyntaxException {
        log.debug("REST request to update MwReg : {}", portSeq);

        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, MwPort> respData = new HashMap<String, MwPort>();
        MwPort reg = mwPortService.updateStatus(portSeq, currUser);
        if (reg == null) {
            resp.put("error", "Region Not Found !!");
            return ResponseEntity.badRequest().body(resp);
        }
        respData.put("branch", reg);
        return ResponseEntity.ok().body(respData);
    }

    @PostMapping("/add-port-trgt")
    @Timed
    public ResponseEntity<List> addPortTrgt(@RequestBody List<PortTrgtReqDto> dtos) throws URISyntaxException {
        log.debug("REST request to save MwPortTrgt : {}", dtos);
        Map<String, MwPort> respData = new HashMap<String, MwPort>();
        List portTrgt = mwPortTrgtService.savePortTrgtArray(dtos);
        return ResponseEntity.ok().body(portTrgt);
    }
}
