package com.idev4.setup.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.idev4.setup.domain.MwAmlList;
import com.idev4.setup.domain.MwExp;
import com.idev4.setup.domain.MwFndsLodr;
import com.idev4.setup.domain.MwRcvryLoadStg;
import com.idev4.setup.dto.AmlCheckDto;
import com.idev4.setup.service.DonorTaggingRequest;
import com.idev4.setup.service.FileLoaderService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileLoaderResource {

    private final Logger log = LoggerFactory.getLogger(FileLoaderResource.class);

    private final FileLoaderService fileLoaderService;

    private final DonorTaggingRequest donorTaggingRequest;

    @Autowired
    RestTemplate restTemplate;

    public FileLoaderResource(FileLoaderService fileLoaderService, DonorTaggingRequest donorTaggingRequest) {
        this.fileLoaderService = fileLoaderService;
        this.donorTaggingRequest = donorTaggingRequest;
    }

    @GetMapping("/get-donor-tagging-file")
    @Timed
    public ResponseEntity<String> getDonorData() {
        log.debug("REST request to get a page of MwAreas");
        return ResponseEntity.ok().body(donorTaggingRequest.getDonorTaggingReport());
    }

    @GetMapping("/get-file-data/{fileType}")
    @Timed
    public ResponseEntity<List<MwRcvryLoadStg>> getFileData(Pageable pageable) {
        log.debug("REST request to get a page of MwAreas");
        List<MwRcvryLoadStg> mwRcvryLoadStgs = fileLoaderService.getFileData();
        return ResponseEntity.ok().body(mwRcvryLoadStgs);
    }

    @GetMapping("/load-file-path/{fileType}")
    @Timed
    public ResponseEntity<String> loadFilePath(@PathVariable String fileType) throws URISyntaxException {
        File sp = null;
        if (fileType.equals("Budget")) {

            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\Budget_Data.csv"))

            if (fileLoaderService.checkFileExist("/srv/samba/Budgets/Budget_Data.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\Budget_Data.csv");
                return ResponseEntity.ok().body("/srv/samba/Budgets/Budget_Data.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        }
        if (fileType.equals("Recovery")) {
            if (fileLoaderService.checkFileExist("/srv/samba/RecoveryFile/ADC_FILE.csv"))
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\ADC_FILE.csv"))
            {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\ADC_FILE.csv");
                return ResponseEntity.ok().body("/opt/RecoveryFile/ADC_FILE.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("Target")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\Target_Data.csv"))

            if (fileLoaderService.checkFileExist("/srv/samba/Targets/Targets_Data.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\Target_Data.csv");

                return ResponseEntity.ok().body("/srv/samba/Targets/Targets_Data.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }

        } else if (fileType.equals("Tagging")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\Tagging_Data.csv"))
            if (fileLoaderService.checkFileExist("/srv/samba/Taggings/Tagging_Data.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\Tagging_Data.csv");
                return ResponseEntity.ok().body("/srv/samba/Taggings/Tagging_Data.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("InsuranceClaim")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\InsuranceClaim_Data.csv")) /srv/samba/

            if (fileLoaderService.checkFileExist(sp.separator + "srv" + sp.separator + "samba" + sp.separator + "InsuranceClaims"
                + sp.separator + "InsuranceClaims_Data.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\InsuranceClaim_Data.csv");

                return ResponseEntity.ok().body("/srv/samba/InsuranceClaims/InsuranceClaims_Data.csv");
                // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\InsuranceClaim_Data.csv"))

            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("WriteOff")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv"))

            if (fileLoaderService.checkFileExist("/srv/samba/WriteOff/WriteOff_Data.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv");

                return ResponseEntity.ok().body("/srv/samba/WriteOff/WriteOff_Data.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("Funds")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv"))

            if (fileLoaderService.checkFileExist("/srv/samba/Funds/Funds.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv");

                return ResponseEntity.ok().body("/srv/samba/Funds/Funds.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("Defer")) {
            // if(fileLoaderService.checkFileExist("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv"))

            if (fileLoaderService.checkFileExist("/srv/samba/Defer/defer.csv")) {
                // return ResponseEntity.ok().body("C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv");

                return ResponseEntity.ok().body("/srv/samba/Defer/defer.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        }
        // Added By Naveed, Dated- 07-10-2021
        // load outreach.csv file
        else if (fileType.equals("outreach")) {
            if (fileLoaderService.checkFileExist("/srv/samba/Targets/outreach.csv")) {
                return ResponseEntity.ok().body("/srv/samba/Targets/outreach.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("BranchFundsRequest")) {
            if (fileLoaderService.checkFileExist("/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv")) {
                return ResponseEntity.ok().body("/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("BranchFundsRequest")) {
            if (fileLoaderService.checkFileExist("/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv")) {
                return ResponseEntity.ok().body("/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("lifeInsuranceClaim")) {
            if (fileLoaderService.checkFileExist("/srv/samba/lifeInsuranceClaim/LIFE_INSURANCE_CLAIM.csv")) {
                return ResponseEntity.ok().body("/srv/samba/lifeInsuranceClaim/LIFE_INSURANCE_CLAIM.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else if (fileType.equals("branchWiseCMSFundsTransfer")) {
            if (fileLoaderService.checkFileExist("/srv/samba/BranchCmsFunds/BRANCH_WISE_CMS_FUNDS.csv")) {
                return ResponseEntity.ok().body("/srv/samba/BranchCmsFunds/BRANCH_WISE_CMS_FUNDS.csv");
            } else {
                return ResponseEntity.ok().body("File not found");
            }
        } else {
            return ResponseEntity.ok().body("File not found");
        }
    }

    @GetMapping("/validate-budget-file")
    @Timed
    public ResponseEntity<Map> validateBudgetFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateBudgetData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-budget-file")
    @Timed
    public ResponseEntity<String> loadBudgetFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveBudgetData());
    }

    @GetMapping("/validate-recovery-file")
    @Timed
    public ResponseEntity<Map> validateRecoveryFile() {
        return ResponseEntity.ok().body(fileLoaderService.validateRecoveryDataNew());
    }

    @GetMapping("/process-recovery-file")
    @Timed
    public ResponseEntity<Map> processRecoveryFile() throws URISyntaxException {
        Map resp = new HashMap();
        resp.put("success", fileLoaderService.postRecoveryData());
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/load-recovery-file")
    @Timed
    public ResponseEntity<String> loadRecoveryFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.loadRecoveryData());
    }

    @GetMapping("/validate-target-file")
    @Timed
    public ResponseEntity<Map> validateTargetFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateTargetData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-target-file")
    @Timed
    public ResponseEntity<String> loadTargetFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveTargetData());
    }

    @GetMapping("/load-tagging-file")
    @Timed
    public ResponseEntity<String> loadTaggingFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveTaggingData());
    }

    @GetMapping("/validate-tagging-file")
    @Timed
    public ResponseEntity<Map> validateTaggingFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateTaggingData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-insurance-claim-file")
    @Timed
    public ResponseEntity<String> loadInsuranceClaimFile() throws URISyntaxException {
        String resp = fileLoaderService.saveClaimData();
        boolean flg = fileLoaderService.postClaimsData();
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/validate-insurance-claim-file")
    @Timed
    public ResponseEntity<Map> validateClaimData() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateClaimsData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-write-off-file")
    @Timed
    public ResponseEntity<String> loadWriteOffFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveWriteOffData());
    }

    @GetMapping("/validate-write-off-file")
    @Timed
    public ResponseEntity<Map> validateWriteOffData() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateWriteOffData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-funds-file")
    @Timed
    public ResponseEntity<List<MwFndsLodr>> loadFundsFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.loadFundsData());
    }

    @GetMapping("/validate-funds-file")
    @Timed
    public ResponseEntity<Map> validateFundsData() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateFundsData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/post-funds-file")
    @Timed
    public ResponseEntity<Boolean> postFundsFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.postFundsData());
    }

    @GetMapping("/get-client-and-branch-name/{clntId}")
    @Timed
    public ResponseEntity<String> getClientAndBranchName(@PathVariable String clntId) throws URISyntaxException {

        return ResponseEntity.ok().body(fileLoaderService.getClientAndBranchName(clntId));

    }

    @GetMapping("/process-aml-list")
    @Timed
    public ResponseEntity<?> testTaggging() throws JSONException {

        List<MwAmlList> list = fileLoaderService.loanAmlList();
        // if ( list )
        return ResponseEntity.ok().body(list);
        // else
        // return ResponseEntity.badRequest().body( "\"{\"error\":\"Something Wen Wrong\"" );
    }

    @PostMapping("/verify-aml")
    @Timed
    public ResponseEntity<Map> verifyAml(@RequestBody AmlCheckDto dto) {
        log.debug("REST request to verifyAml : ", dto);
        Map resp = new HashMap<>();
        boolean found = false;
        String reason = "";
        MwAmlList aml = null;
        if (dto.clnt != null) {
            log.debug("REST request to verifyAml : ", dto.clnt.firstName + " " + dto.clnt.lastName);
            aml = fileLoaderService.checkAmlForUserName(dto.clnt.firstName, dto.clnt.lastName);
            if (aml != null) {
                found = true;
                reason = "Client Tagged by AML";
            }
        }
        if (!found && dto.nom != null) {
            log.debug("REST request to verifyAml : ", dto.nom.firstName + " " + dto.nom.lastName);
            aml = fileLoaderService.checkAmlForUserName(dto.nom.firstName, dto.nom.lastName);
            if (aml != null) {
                found = true;
                reason = "Nomminee match found in AML";
            }
        }
        if (!found && dto.cob != null) {
            log.debug("REST request to verifyAml : ", dto.cob.firstName + " " + dto.cob.lastName);
            aml = fileLoaderService.checkAmlForUserName(dto.cob.firstName, dto.cob.lastName);
            if (aml != null) {
                found = true;
                reason = "Co Borrower match found in AML";
            }
        }
        if (!found && dto.clntRel != null) {
            log.debug("REST request to verifyAml : ", dto.clntRel.firstName + " " + dto.clntRel.lastName);
            aml = fileLoaderService.checkAmlForUserName(dto.clntRel.firstName, dto.clntRel.lastName);
            if (aml != null) {
                found = true;
                reason = "Client Relative match found in AML";
            }
        }
        if (!found && dto.kin != null) {
            log.debug("REST request to verifyAml : ", dto.kin.firstName + " " + dto.kin.lastName);
            aml = fileLoaderService.checkAmlForUserName(dto.kin.firstName, dto.kin.lastName);
            if (aml != null) {
                found = true;
                reason = "Next of Kin match found in AML";
            }
        }
        resp.put("canProceed", !found);
        resp.put("reason", reason);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/validate-defer-file")
    @Timed
    public ResponseEntity<Map> validateDeferLoan() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateDeferLoan());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/post-defer-file")
    @Timed
    public ResponseEntity<Boolean> postDeferFile(@RequestHeader(value = "Authorization") String token)
        throws URISyntaxException, ParseException {
        fileLoaderService.processDeferLoan(token);
        return ResponseEntity.ok().body(true);
    }

    // Added By Naveed, Dated- 07-10-2021
    // for Validate outreach.csv file
    @GetMapping("/validate-outreach-file")
    @Timed
    public ResponseEntity<Map> validateOutreachFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateOutreachData());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    // Added By Naveed, Dated- 07-10-2021
    // load outreach.csv file
    @GetMapping("/load-outreach-file")
    @Timed
    public ResponseEntity<String> loadOutreachFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveOutreachData());
    }

    /**
     * @Added, Naveed
     * @Date, 14-06-2022
     * @Description, SCR - systemization Funds Request (uploader)
     */

    @GetMapping("/validate-branch-fund-request-file")
    @Timed
    public ResponseEntity<Map> validateBranchFundRequestFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateBranchFundRequestDate());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @GetMapping("/load-branch-fund-request-file")
    @Timed
    public ResponseEntity<String> loadBranchFundRequestFile() throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.saveBranchFundRequestFile());
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Life Insurance Claim uploader (Loan Adjustment )
     */

    @GetMapping("/validate-life-insurance-claim-file")
    @Timed
    public ResponseEntity<Map> validateLifeInsuranceClaimFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateLifeInsuranceClaimFile());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Life Insurance Claim uploader (Loan Adjustment )
     */
    @GetMapping("/load-life-insurance-claim-file")
    @Timed
    public ResponseEntity<String> loadLifeInsuranceClaimFile(@RequestHeader(value = "Authorization") String token) throws URISyntaxException {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("load-life-insurance-claim-file ");
        return ResponseEntity.ok().body(fileLoaderService.loadLifeInsuranceClaimFile(token, user));
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader (validate funds file)
     */

    @GetMapping("/validate-branch-cms-funds-transfer-file")
    @Timed
    public ResponseEntity<Map> validateBranchCMSFundsTransferFile() throws URISyntaxException {
        try {
            return ResponseEntity.ok().body(fileLoaderService.validateBranchCMSFundsTransferFile());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Map resp = new HashMap();
            resp.put("error", "Unable To Parse Values");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader (load funds)
     */
    @GetMapping("/load-branch-cms-funds-transfer-file")
    @Timed
    public ResponseEntity<List<MwExp>> loadBranchCMSFundsTransferFile(@RequestHeader(value = "Authorization") String token) throws URISyntaxException {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("load-branch-cms-funds-transfer-file ");
        return ResponseEntity.ok().body(fileLoaderService.loadBranchCMSFundsTransferFile(token, user));
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader (post funds)
     */
    @PostMapping("/post-branch-cms-funds-transfer-file")
    @Timed
    public ResponseEntity<?> postBranchCMSFundsTransferFile(@RequestHeader(value = "Authorization") String token, @RequestBody List<MwExp> expList) throws URISyntaxException {
        return ResponseEntity.ok().body(fileLoaderService.postBranchCMSFundsTransferFile(token, expList));
    }
}
