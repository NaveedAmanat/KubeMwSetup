package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwPrd;
import com.idev4.setup.dto.GlAccount;
import com.idev4.setup.dto.ProductDto;
import com.idev4.setup.service.MwPrdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing MwPrd.
 */
@RestController
@RequestMapping("/api")
public class MwPrdResource {

    private final Logger log = LoggerFactory.getLogger(MwPrdResource.class);

    private final MwPrdService mwPrdService;

    public MwPrdResource(MwPrdService mwPrdService) {
        this.mwPrdService = mwPrdService;
    }

    @PostMapping("/add-new-prd")
    @Timed
    public ResponseEntity<Map> createMwPrd(@RequestBody ProductDto productDto) throws URISyntaxException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();

        if (productDto.prdGrpSeq == null) {
            resp.put("error", "Missing Product Group Seq!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.productName == null) {
            resp.put("error", "Missing Product Name!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.prdId == null) {
            resp.put("error", "Missing Product Id!!");
            return ResponseEntity.badRequest().body(resp);
        }
        // if(productDto.prdCmnt == null) {
        // resp.put("error", "Missing Product Comments!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (productDto.prdStsKey == null) {
            resp.put("error", "Missing Product Status Key!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.prdTypKey == null) {
            resp.put("error", "Missing Product Type Key!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.irrFlg == null) {
            resp.put("error", "Missing IRR Flag!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.rndngScl == null) {
            resp.put("error", "Missing Roungding Scale!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.rndngAdj == null) {
            resp.put("error", "Missing Rounding Adjustment!!");
            return ResponseEntity.badRequest().body(resp);
        }
        // if(productDto.dailyAccuralFlg == null) {
        // resp.put("error", "Missing Daily Accural Flag!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.fndByKey == null) {
        // resp.put("error", "Missing Funded by Key!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.crncyCdKey == null) {
        // resp.put("error", "Missing Currency Code Key!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.irrVal == null) {
        // resp.put("error", "Missing IRR Value!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.mltLoanFlg == null) {
        // resp.put("error", "Missing Multiloan Flag!!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        MwPrd mwPrd = mwPrdService.save(productDto, currUser);
        Map<String, MwPrd> respData = new HashMap<String, MwPrd>();
        respData.put("Product", mwPrd);
        return ResponseEntity.ok().body(respData);
    }

    @PutMapping("/update-mw-prd")
    @Timed
    public ResponseEntity<Map> updateMwPrd(@RequestBody ProductDto productDto) throws URISyntaxException {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> resp = new HashMap<String, String>();

        if (productDto.productSeq == null) {
            resp.put("error", "Missing Product Seq!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.prdGrpSeq == null) {
            resp.put("error", "Missing Product Group Seq!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.productName == null) {
            resp.put("error", "Missing Product Name!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.prdId == null) {
            resp.put("error", "Missing Product Id!!");
            return ResponseEntity.badRequest().body(resp);
        }
        // if(productDto.prdCmnt == null) {
        // resp.put("error", "Missing Product Comments!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (productDto.prdStsKey == null) {
            resp.put("error", "Missing Product Status Key!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.prdTypKey == null) {
            resp.put("error", "Missing Product Type Key!!");
            return ResponseEntity.badRequest().body(resp);
        }
        // if(productDto.irrFlg == null) {
        // resp.put("error", "Missing IRR Flag!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        if (productDto.rndngScl == null) {
            resp.put("error", "Missing Roungding Scale!!");
            return ResponseEntity.badRequest().body(resp);
        }
        if (productDto.rndngAdj == null) {
            resp.put("error", "Missing Rounding Adjustment!!");
            return ResponseEntity.badRequest().body(resp);
        }
        // if(productDto.dailyAccuralFlg == null) {
        // resp.put("error", "Missing Daily Accural Flag!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.fndByKey == null) {
        // resp.put("error", "Missing Funded by Key!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.crncyCdKey == null) {
        // resp.put("error", "Missing Currency Code Key!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.irrVal == null) {
        // resp.put("error", "Missing IRR Value!!");
        // return ResponseEntity.badRequest().body(resp);
        // }
        // if(productDto.mltLoanFlg == null) {
        // resp.put("error", "Missing Multiloan Flag!!");
        // return ResponseEntity.badRequest().body(resp);
        // }

        MwPrd mwPrd = mwPrdService.updateExistingProduct(productDto, currUser);
        Map<String, MwPrd> respData = new HashMap<String, MwPrd>();
        respData.put("Product", mwPrd);
        return ResponseEntity.ok().body(respData);
    }

    @GetMapping("/mw-prd")
    @Timed
    public ResponseEntity<List> getMwPrds() {
        log.debug("REST request to get all MwPrd : {}");
        List<MwPrd> mwPrds = mwPrdService.findAllProducts();
        return ResponseEntity.ok().body(mwPrds);
    }

    @GetMapping("/mw-prd/{prdSeq}")
    @Timed
    public ResponseEntity<MwPrd> getMwPrdBySeq(@PathVariable Long prdSeq) {
        log.debug("REST request to get MwPrd : {}", prdSeq);
        MwPrd mwPrd = mwPrdService.findOne(prdSeq);
        return ResponseEntity.ok().body(mwPrd);
    }

    @GetMapping("/mw-prd-by-grp-seq/{prdGrpSeq}")
    @Timed
    public ResponseEntity<List<MwPrd>> getMwPrdByPrdGrpSeq(@PathVariable Long prdGrpSeq) {
        log.debug("REST request to get MwPrd : {}", prdGrpSeq);
        List<MwPrd> mwPrd = mwPrdService.findAllProductsByGroup(prdGrpSeq);
        return ResponseEntity.ok().body(mwPrd);
    }

    @GetMapping("/mw-prd-history/{prdSeq}")
    @Timed
    public ResponseEntity<List> getMwPrdHistory(@PathVariable Long prdSeq) {
        log.debug("REST request to get MwPrd : {}", prdSeq);
        List<MwPrd> mwPrds = mwPrdService.findAllByPrdSeq(prdSeq);
        return ResponseEntity.ok().body(mwPrds);
    }

    @DeleteMapping("/mw-prd/{prdSeq}")
    @Timed
    public ResponseEntity<Map> deleteMwPrd(@PathVariable Long prdSeq) {
        log.debug("REST request to delete MwPrd : {}", prdSeq);
        Map<String, String> resp = new HashMap<String, String>();
        if (mwPrdService.delete(prdSeq)) {
            resp.put("data", "Deleted Successfully !!");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.put("error", "Delete Child First !!");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/get-products-listing")
    @Timed
    public ResponseEntity<List<ProductDto>> getMwProductsWithTerms() {
        log.debug("REST request to get a page of MwPrds");
        List<ProductDto> res = mwPrdService.getProductsListing();
        return ResponseEntity.ok().body(res);
    }

    // @GetMapping("/get-products-listing-for-client/{clientSeq}")
    // @Timed
    // public ResponseEntity<List<ProductDto>> getMwProductsWithTerms(@PathVariable Long clientSeq) {
    // log.debug("REST request to get a page of MwPrds");
    // List<ProductDto> res = mwPrdService.getProductsListingForClient(clientSeq);
    // return ResponseEntity.ok().body(res);
    // }

    @PostMapping("/get-products-listing-for-client")
    @Timed
    public ResponseEntity<List<ProductDto>> getMwProductsWithTerms(@RequestBody ProductDto dto) {
        log.debug("REST request to get a page of MwPrds");
        List<ProductDto> res = mwPrdService.getProductsListingForClient(dto);
        return ResponseEntity.ok().body(res);
    }

    /* @GetMapping("/mw-asoc-prd/{prdTypSeq}")
    @Timed
    public ResponseEntity <List<MwPrd>> getAssociateProducts(@PathVariable Long prdTypSeq) {
        log.debug("REST request to get MwPrd : {}" );
        List<MwPrd> mwPrd = mwPrdService.findAllAssociateProducts(prdTypSeq);
        return ResponseEntity.ok().body(mwPrd);
    }*/
    @GetMapping("/mw-prd-by-typ/{typSeq}")
    @Timed
    public ResponseEntity<List> getProductByTypSeq(@PathVariable Long typSeq) {
        log.debug("REST request to get MwPrd : {}", typSeq);
        List<ProductDto> mwPrds = mwPrdService.getProductsByTypSeq(typSeq);
        return ResponseEntity.ok().body(mwPrds);
    }

    @GetMapping("/gl-accounts")
    @Timed
    public ResponseEntity<List> getGlAccounts() {
        log.debug("REST request to get all GL_ACCOUNTS : {}");
        List<GlAccount> accounts = mwPrdService.getAllGLAccounts();
        return ResponseEntity.ok().body(accounts);
    }

    @GetMapping("/mw-prd-fr-brnch/{brnchSeq}")
    @Timed
    public ResponseEntity<List<MwPrd>> getActiveMwPrdByBrnch(@PathVariable Long brnchSeq) {
        log.debug("REST request to get Active MwPrd for Branch: {}", brnchSeq);
        List<MwPrd> mwPrd = mwPrdService.findActivePrdForBrnch(brnchSeq);
        return ResponseEntity.ok().body(mwPrd);
    }
}
