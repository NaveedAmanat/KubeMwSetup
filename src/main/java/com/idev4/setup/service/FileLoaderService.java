package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.EmailDto;
import com.idev4.setup.feignclients.ServiceClient;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileLoaderService {

    private final Logger log = LoggerFactory.getLogger(FileLoaderService.class);
    private final MwBrnchBdgtRepository mwBrnchBdgtRepository;
    private final MwBrnchTrgtRepository mwBrnchTrgtRepository;
    private final MwLoanDnrRelRepository mwLoanDnrRelRepository;
    private final MwClntTagListRepository mwClntTagListRepository;
    private final MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository;
    private final MwRcvryLoadStgRepository mwRcvryLoadStgRepository;
    private final MwFndsLodrRepository mwFndsLodrRepository;
    private final MwLoanAppRepository mwLoanAppRepository;
    private final MwRefCdValRepository mwRefCdValRepository;
    private final MwBrnchRepository mwBrnchRepository;
    private final MwTypsRepository mwTypsRepository;
    private final MwTagsRepository mwTagsRepository;
    private final EntityManager em;
    private final MwRcvryLoadVldRepository mwRcvryLoadVldRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ServiceClient serviceClient;
    private DonorTaggingRequest donorTaggingRequest;
    private MwAmlListRepository mwAmlListRepository;
    // Added By Naveed, Dated - 07-10-2021
    // for Region_Wise_Outreach loader
    private MwRegRepository mwRegRepository;

    private RegionOutreachRepository regionOutreachRepository;

    private MwBrnchAcctSetRepository mwBrnchAcctSetRepository;

    private MwRegAcctSetRepository mwRegAcctSetRepository;

    private MwFundsRequestRepository mwFundsRequestRepository;

    private MwExpService mwExpService;

    private MwExpRepository mwExpRepository;

    private Email emailService;

    private MwAutoStatusRepository mwAutoStatusRepository;

    public FileLoaderService(MwRcvryLoadStgRepository mwRcvryLoadStgRepository, MwClntTagListRepository mwClntTagListRepository,
                             MwBrnchBdgtRepository mwBrnchBdgtRepository, MwBrnchTrgtRepository mwBrnchTrgtRepository,
                             MwLoanDnrRelRepository mwLoanDnrRelRepository, MwClntHlthInsrClmRepository mwClntHlthInsrClmRepository, EntityManager em,
                             MwFndsLodrRepository mwFndsLodrRepository, MwLoanAppRepository mwLoanAppRepository, MwRefCdValRepository mwRefCdValRepository,
                             MwBrnchRepository mwBrnchRepository, MwTypsRepository mwTypsRepository, MwTagsRepository mwTagsRepository,
                             DonorTaggingRequest donorTaggingRequest, MwAmlListRepository mwAmlListRepository,
                             MwRcvryLoadVldRepository mwRcvryLoadVldRepository, MwRegRepository mwRegRepository,
                             RegionOutreachRepository regionOutreachRepository, MwBrnchAcctSetRepository mwBrnchAcctSetRepository,
                             MwFundsRequestRepository mwFundsRequestRepository, MwExpService mwExpService, MwExpRepository mwExpRepository, Email emailService,
                             MwRegAcctSetRepository mwRegAcctSetRepository, MwAutoStatusRepository mwAutoStatusRepository) {
        this.mwBrnchBdgtRepository = mwBrnchBdgtRepository;
        this.mwBrnchTrgtRepository = mwBrnchTrgtRepository;
        this.mwLoanDnrRelRepository = mwLoanDnrRelRepository;
        this.mwClntHlthInsrClmRepository = mwClntHlthInsrClmRepository;
        this.em = em;
        this.mwClntTagListRepository = mwClntTagListRepository;
        this.mwRcvryLoadStgRepository = mwRcvryLoadStgRepository;
        this.mwFndsLodrRepository = mwFndsLodrRepository;
        this.mwLoanAppRepository = mwLoanAppRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwTypsRepository = mwTypsRepository;
        this.mwTagsRepository = mwTagsRepository;
        this.donorTaggingRequest = donorTaggingRequest;
        this.mwAmlListRepository = mwAmlListRepository;
        this.mwRcvryLoadVldRepository = mwRcvryLoadVldRepository;
        this.mwRegRepository = mwRegRepository;
        this.regionOutreachRepository = regionOutreachRepository;
        this.mwBrnchAcctSetRepository = mwBrnchAcctSetRepository;
        this.mwFundsRequestRepository = mwFundsRequestRepository;
        this.mwExpService = mwExpService;
        this.mwExpRepository = mwExpRepository;
        this.emailService = emailService;
        this.mwRegAcctSetRepository = mwRegAcctSetRepository;
        this.mwAutoStatusRepository = mwAutoStatusRepository;
    }

    public static boolean isStringOnlyDigit(String str) {
        return ((!str.equals(""))
            && (str != null)
            && (str.matches("^[0-9]*$")));
    }

    public List<MwRcvryLoadStg> getFileData() {
        return mwRcvryLoadStgRepository.findAll();
    }

    public boolean checkFileExist(String path) {
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    public Map validateBudgetData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        String csvFile = "/srv/samba/Budgets/Budget_Data.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    if (tempArr.length != 7) {
                        continue;
                    }
                    Map<String, Object> item = new HashMap();
                    item.put("header", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("type", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("category", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("period", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("year", tempArr[4] == null ? "" : tempArr[4]);
                    item.put("amount", tempArr[5] == null ? "" : tempArr[5]);
                    item.put("branch", tempArr[6] == null ? "" : tempArr[6]);
                    // Long type;

                    Long type = Long.parseLong(tempArr[1]);
                    if (type <= 3) {
                        String query = "select count(1) from MW_PERD where perd_key = " + tempArr[3];
                        // query = query + ( ( type == 1 ) ? "perd_key = " : ( type == 2 ) ? "QTR_KEY = " : "BI_ANL_KEY = " ) + tempArr[ 3
                        // ];
                        Query perdQuery = em.createNativeQuery(query);
                        BigDecimal dec = (BigDecimal) perdQuery.getSingleResult();
                        int count = dec.intValue();
                        if (count <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Period Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Budget Type (Can not be greater than 3).";
                    }
                    Long category = Long.parseLong(tempArr[2]);
                    if (category > 0) {
                        String query = "select count(1) from mw_typs where typ_seq=" + category + " and crnt_rec_flg=1";
                        Query typsQuery = em.createNativeQuery(query);
                        BigDecimal dec = (BigDecimal) typsQuery.getSingleResult();
                        int count = dec.intValue();
                        if (count <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Category Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Category Type.";
                    }
                    Long branch = Long.parseLong(tempArr[6]);
                    if (branch > 0) {
                        String query = "select count(1) from mw_brnch where brnch_seq=" + branch + " and crnt_rec_flg=1";
                        Query brnchQuery = em.createNativeQuery(query);
                        BigDecimal dec = (BigDecimal) brnchQuery.getSingleResult();
                        int count = dec.intValue();
                        if (count <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Branch Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Branch Seq Provided.";
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveBudgetData() {// period MW_PERD exists TYPE 1 match with perd_key, type 2 qtr_key, type 3 Bianual// BGT CAT - >
        // MW_TYPS
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        // String csvFile ="C:\\Users\\Admin\\Desktop\\Budget_Data.csv";

        String csvFile = "/srv/samba/Budgets/Budget_Data.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwBrnchBdgt> mwBrnchBdgts = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {

                    tempArr = line.split(",");
                    if (tempArr.length != 7) {
                        continue;
                    }

                    MwBrnchBdgt mwBrnchBdgt = new MwBrnchBdgt();
                    long seq = SequenceFinder.findNextVal(Sequences.BRNCH_BDGT_SEQ);
                    Long type = Long.parseLong(tempArr[1]);
                    Long perd = Long.parseLong(tempArr[3]);

                    if (type == 2) {
                        String qtrQuery = "select perd_key,qtr_key from mw_perd where perd_key =  " + perd;
                        Query qtrnQuery = em.createNativeQuery(qtrQuery);
                        List<Object[]> resultSet = qtrnQuery.getResultList();
                        if (resultSet != null) {
                            Object[] obj = resultSet.get(0);
                            if (obj != null) {
                                perd = obj[1] != null ? Long.parseLong(obj[1].toString()) : 0L;
                            }
                        }
                    } else if (type == 3) {
                        String bnlQuery = "select perd_key,bi_anl_key from mw_perd where perd_key =  " + perd;
                        Query bnlnQuery = em.createNativeQuery(bnlQuery);
                        List<Object[]> resultSet = bnlnQuery.getResultList();
                        if (resultSet != null) {
                            Object[] obj = resultSet.get(0);
                            if (obj != null) {
                                perd = obj[1] != null ? Long.parseLong(obj[1].toString()) : 0L;
                            }
                        }
                    }
                    mwBrnchBdgt.setBrnchBdgtSeq(seq);
                    mwBrnchBdgt.setgLAcctNum(tempArr[0]);
                    mwBrnchBdgt.setBdgtTypKey(Long.parseLong(tempArr[1]));
                    mwBrnchBdgt.setBdgtCtgryKey(Long.parseLong(tempArr[2]));
                    mwBrnchBdgt.setPred(perd);
                    mwBrnchBdgt.setBdgtYr(Long.parseLong(tempArr[4]));
                    mwBrnchBdgt.setBdgtAmt(Double.parseDouble(tempArr[5]));
                    mwBrnchBdgt.setBrnchSeq(Long.parseLong(tempArr[6]));
                    mwBrnchBdgt.setCrtdBy(currUser);
                    mwBrnchBdgt.setCrtdDt(currIns);
                    mwBrnchBdgt.setDelFlg(false);
                    mwBrnchBdgts.add(mwBrnchBdgt);

                    MwBrnchBdgt exBgt = mwBrnchBdgtRepository.findOneByBrnchSeqAndPredAndBdgtCtgryKeyAndDelFlg(
                        Long.parseLong(tempArr[6]), perd, Long.parseLong(tempArr[2]), false);
                    if (exBgt != null)
                        exBgt.setDelFlg(true);

                }
                flag = false;
            }
            br.close();
            // Query update = em.createNativeQuery( "update Mw_Brnch_Bdgt set DEL_FLG=1 where BDGT_PERD=:prd" ).setParameter( "prd",
            // mwBrnchBdgts.get( 0 ).getPred() );
            // update.executeUpdate();
            this.mwBrnchBdgtRepository.save(mwBrnchBdgts);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    public Map validateRecoveryDataNew() {
        Map resp = new HashMap();

        String jobQ = "select job_seq, job_dscr, strt_dt, end_dt, sts, tot_rec, rj_rec from bck_job where sts=1";
        Query jobQuery = em.createNativeQuery(jobQ);
        List<Object[]> jobQueryresultSet = jobQuery.getResultList();
        if (jobQueryresultSet != null && jobQueryresultSet.size() > 0) {
            Object[] jobobj = jobQueryresultSet.get(0);
            Map<String, Object> item = new HashMap();
            item.put("job_seq", jobobj[0]);
            item.put("job_dscr", jobobj[1]);
            item.put("strt_dt", jobobj[2]);
            item.put("end_dt", jobobj[3]);
            item.put("sts", jobobj[4]);
            item.put("tot_rec", jobobj[5]);
            item.put("rj_rec", jobobj[6]);
            resp.put("valid", false);
            resp.put("hasActiveJob", true);
            resp.put("job", item);
            return resp;
        }
        resp.put("hasActiveJob", false);

        String trxQuery = "truncate table MW_RCVY_LOAD_VLD";
        Query query = em.createNativeQuery(trxQuery);
        query.executeUpdate();

        String dropSeqQuery = "drop sequence RCVRY_LOAD_VLD_SEQ";
        Query dquery = em.createNativeQuery(dropSeqQuery);
        dquery.executeUpdate();

        String cSeqQuery = "create sequence RCVRY_LOAD_VLD_SEQ";
        Query cquery = em.createNativeQuery(cSeqQuery);
        cquery.executeUpdate();

        List list = new ArrayList();
        File sp = null;
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "RecoveryFile" + sp.separator + "ADC_FILE.csv";
        boolean fileValid = true;
        List<MwRcvryLoadVld> vlds = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("transNum", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("clntId", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("amt", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("date", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("agntId", tempArr[4] == null ? "" : tempArr[4]);
                    Long clntSeq = 0l;
                    try {
                        clntSeq = Long.valueOf(tempArr[1]);
                    } catch (NumberFormatException e) {
                        // It's OK to ignore "e" here because returning a default value is the documented behaviour on invalid input.
                        fileValid = false;
                    }
                    MwRcvryLoadVld vld = new MwRcvryLoadVld();
                    long seq = SequenceFinder.findNextVal("RCVRY_LOAD_VLD_SEQ");
                    vld.setRcvryLoadVldSeq(seq);
                    vld.setAgntId(tempArr[4] == null || tempArr[4].isEmpty() ? "0" : tempArr[4]);
                    vld.setAmt(tempArr[2] == null || tempArr[2].isEmpty() ? "0" : tempArr[2]);
                    vld.setClntSeq(clntSeq);
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(tempArr[3]);
                    Instant reqInstant = date.toInstant();
                    vld.setTrxDate(reqInstant);
                    vld.setTrxSeq(tempArr[0] == null || tempArr[0].isEmpty() ? "0" : tempArr[0]);
                    vlds.add(vld);
                }
                i++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.put("valid", false);
            return resp;
        }
        mwRcvryLoadVldRepository.save(vlds);

        // Modified by Farzeen Alam - Datea 03-02-2022 - Instrument No Criteria Changed
        // EMAIL: Recovery Uploader Change BY IMRAN HANIF

        try {

            String vldQueryStr = "SELECT STG.CLNT_SEQ,\n" +
                "       CL.FRST_NM,\n" +
                "       CL.LAST_NM,\n" +
                "       NVL ((SELECT 'Y'\n" +
                "               FROM MW_CLNT CL\n" +
                "              WHERE CL.CRNT_REC_FLG = 1 AND CL.CLNT_SEQ = STG.CLNT_SEQ),\n" +
                "            'N')\n" +
                "           VLD_CLNT_FLG,\n" +
                "       STG.TRX_SEQ,\n" +
                "       NVL (\n" +
                "           (SELECT DISTINCT 'Y'\n" +
                "              FROM MW_RCVRY_TRX  TRX\n" +
                "                   JOIN MW_TYPS TYP\n" +
                "                       ON     TYP.TYP_SEQ = TRX.RCVRY_TYP_SEQ\n" +
                "                          AND TYP.CRNT_REC_FLG = 1\n" +
                "             WHERE     TRX.CRNT_REC_FLG = 1\n" +
                "                   AND (   (   (    TRX.INSTR_NUM = STG.TRX_SEQ\n" +
                "                                     AND TRUNC (TRX.PYMT_DT) = STG.TRX_DATE)\n" +
                "                            AND TYP.TYP_ID = '0005'\n" +
                "                            AND LPAD (STG.AGNT_ID, 4, '0') = TYP.TYP_ID\n" +
                "                            )\n" +
                "                        OR (   (    TRX.INSTR_NUM = STG.TRX_SEQ\n" +
                "                                     AND TRX.PYMT_REF = STG.CLNT_SEQ \n" +
                "                                     AND TRUNC (TRX.PYMT_DT) != STG.TRX_DATE)\n" +
                "                            AND TYP.TYP_ID = '0005'\n" +
                "                            AND LPAD (STG.AGNT_ID, 4, '0') = TYP.TYP_ID\n" +
                "                            )\n" +
                "                        OR (    TRX.INSTR_NUM = STG.TRX_SEQ\n" +
                "                            AND LPAD (STG.AGNT_ID, 4, '0') = TYP.TYP_ID \n" +
                "                            AND TYP.TYP_ID != '0005'))),\n" +
                "           'N')\n" +
                "           TRX_EXIST_FLG,\n" +
                "       (SELECT BRNCH_SEQ\n" +
                "          FROM MW_PORT  PRT\n" +
                "               JOIN MW_CLNT CL\n" +
                "                   ON     CL.PORT_KEY = PRT.PORT_SEQ\n" +
                "                      AND CL.CRNT_REC_FLG = 1\n" +
                "                      AND CL.CLNT_SEQ = STG.CLNT_SEQ\n" +
                "         WHERE PRT.CRNT_REC_FLG = 1)\n" +
                "           BRNCH_SEQ,\n" +
                "       LPAD (STG.AGNT_ID, 4, '0')\n" +
                "           AGNT_ID,\n" +
                "       NVL (\n" +
                "           (SELECT 'Y'\n" +
                "              FROM MW_TYPS TYP\n" +
                "             WHERE     TYP.CRNT_REC_FLG = 1\n" +
                "                   AND TYP_CTGRY_KEY = 4\n" +
                "                   AND TYP_ID = LPAD (STG.AGNT_ID, 4, '0')\n" +
                "                   AND TYP.BRNCH_SEQ =\n" +
                "                       (SELECT BRNCH_SEQ\n" +
                "                          FROM MW_PORT  PRT\n" +
                "                               JOIN MW_CLNT CL\n" +
                "                                   ON     CL.PORT_KEY = PRT.PORT_SEQ\n" +
                "                                      AND CL.CRNT_REC_FLG = 1\n" +
                "                                      AND CL.CLNT_SEQ = STG.CLNT_SEQ\n" +
                "                         WHERE PRT.CRNT_REC_FLG = 1)),\n" +
                "           'N')\n" +
                "           AGNT_VLD_FLG,\n" +
                "       STG.TRX_DATE,\n" +
                "       CASE WHEN STG.TRX_DATE > SYSDATE THEN 'Y' ELSE 'N' END\n" +
                "           FTUR_DT_FLG,\n" +
                "       STG.AMT\n" +
                "  FROM MW_RCVY_LOAD_VLD  STG\n" +
                "       LEFT OUTER JOIN MW_CLNT CL\n" +
                "           ON CL.CLNT_SEQ = STG.CLNT_SEQ AND CL.CRNT_REC_FLG = 1";
                /* As per request of Shahzad Iqbal/Yasir Arjuman/Imran Hanif - Change has been commented

                "select stg.clnt_seq, cl.frst_nm, cl.last_nm,\r\n"
                + "nvl((select 'Y' from mw_clnt cl where cl.clnt_seq=stg.clnt_seq),'N') vld_clnt_flg,\r\n"
                + "stg.trx_seq,\r\n"
                + "nvl((select distinct 'Y' from mw_rcvry_trx trx join mw_typs typ on typ.typ_seq=trx.rcvry_typ_seq \r\n"
                + "where trx.instr_num=stg.trx_seq and TO_NUMBER(trx.pymt_ref)=stg.clnt_seq \r\n"
                + "and trx.crnt_rec_flg = 1 and TRUNC(trx.pymt_dt)=TRUNC(stg.trx_date) and \r\n"
                + "lpad(stg.agnt_id,4,'0')=typ.typ_id),'N') trx_exist_flg,\r\n"
                + "(select brnch_seq from mw_port prt\r\n"
                + "join mw_clnt cl on cl.port_key=prt.port_seq and cl.clnt_seq=stg.clnt_seq\r\n"
                + ") brnch_seq, lpad(stg.agnt_id,4,'0') agnt_id,\r\n"
                + "nvl((select 'Y' from mw_typs typ where typ_ctgry_key = 4 and typ_id=lpad(stg.agnt_id,4,'0') and typ.brnch_seq=(select brnch_seq\r\n"
                + "from mw_port prt\r\n"
                + "join mw_clnt cl on cl.port_key=prt.port_seq and cl.clnt_seq=stg.clnt_seq\r\n"
                + ")),'N') agnt_vld_flg, stg.trx_date,\r\n"
                + "case when stg.trx_date>sysdate then 'Y' else 'N' end ftur_dt_flg, stg.amt\r\n" + "from mw_rcvy_load_vld stg\r\n"
                + "left outer join mw_clnt cl on cl.clnt_seq=stg.clnt_seq";*/

            Query vldQuery = em.createNativeQuery(vldQueryStr);
            List<Object[]> resultSet = vldQuery.getResultList();
            for (Object[] obj : resultSet) {
                boolean validItem = true;
                String reason = "";
                Map<String, Object> item = new HashMap();
                item.put("transNum", obj[4] == null ? "" : obj[4]);
                item.put("clntId", obj[0] == null ? "" : obj[0]);
                item.put("amt", obj[11] == null ? "" : obj[11]);
                item.put("date", obj[9] == null ? "" : obj[9]);
                item.put("agntId", obj[7] == null ? "" : obj[7]);

                item.put("firstName", obj[1] == null ? "" : obj[1]);
                item.put("lastName", obj[2] == null ? "" : obj[2]);

                if (obj[0].equals("0")) {
                    validItem = false;
                    fileValid = false;
                    reason = "Can not parse Client Id.";
                }
                if (validItem && obj[3].toString().equals("N")) {
                    validItem = false;
                    fileValid = false;
                    reason = "Invalid Client";
                }

                if (validItem && obj[6] == null) {
                    validItem = false;
                    fileValid = false;
                    reason = "Branch Not Found";
                }

                if (validItem && obj[8].toString().equals("N")) {
                    validItem = false;
                    fileValid = false;
                    reason = "ADC not found for Branch";
                }

                if (validItem && obj[5].toString().equals("Y")) {
                    validItem = false;
                    fileValid = false;
                    reason = "Instrument Number For ADC already found";
                }

                if (validItem && obj[10].toString().equals("Y")) {
                    validItem = false;
                    fileValid = false;
                    reason = "Future Date Not allowed";
                }

                // Added by Zohaib Asim - Dated 11-02-2022 - Validation Checks
                if (validItem && (obj[4].toString().equals("0") || obj[4].toString().contains("E+"))) {
                    validItem = false;
                    fileValid = false;
                    reason = "Invalid Transaction No";
                }
                if (validItem && obj[11].toString().equals("0")) {
                    validItem = false;
                    fileValid = false;
                    reason = "Invalid Amount";
                }
                // End

                if (validItem) {
                    for (int z = 0; z < list.size(); z++) {
                        Map<String, Object> obj1 = (Map) list.get(z);
                        if (obj1.get("transNum").toString().equals(obj[4].toString())
                            && obj1.get("agntId").toString().equals(String.format("%04d", Long.parseLong(obj[7].toString())))) {
                            validItem = false;
                            fileValid = false;
                            reason = "Duplicate Entry In File.";
                        }
                    }
                }

                log.info("Valid Flg: " + validItem);
                log.info("Reason: " + reason);

                item.put("valid", validItem);
                item.put("reason", reason);
                if (validItem)
                    list.add(item);
                else
                    list.add(0, item);
            }

            if (resultSet.size() != vlds.size()) {
                fileValid = false;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.put("InvalidRow", false);
            return resp;
        }

        return resp;
    }

    public Map validateRecoveryData() throws ParseException {

        Map resp = new HashMap();

        String jobQ = "select job_seq, job_dscr, strt_dt, end_dt, sts, tot_rec, rj_rec from bck_job where sts=1";
        Query jobQuery = em.createNativeQuery(jobQ);
        List<Object[]> jobQueryresultSet = jobQuery.getResultList();
        if (jobQueryresultSet != null && jobQueryresultSet.size() > 0) {
            Object[] jobobj = jobQueryresultSet.get(0);
            Map<String, Object> item = new HashMap();
            item.put("job_seq", jobobj[0]);
            item.put("job_dscr", jobobj[1]);
            item.put("strt_dt", jobobj[2]);
            item.put("end_dt", jobobj[3]);
            item.put("sts", jobobj[4]);
            item.put("tot_rec", jobobj[5]);
            item.put("rj_rec", jobobj[6]);
            resp.put("valid", false);
            resp.put("hasActiveJob", true);
            resp.put("job", item);
            return resp;
        }

        resp.put("hasActiveJob", false);
        List list = new ArrayList();
        File sp = null;
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "RecoveryFile" + sp.separator + "ADC_FILE.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("transNum", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("clntId", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("amt", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("date", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("agntId", tempArr[4] == null ? "" : tempArr[4]);

                    String cQuery = "select mc.FRST_NM,  mc.LAST_NM as clnt_name, mb.brnch_seq ,mt.typ_seq, (select count(1) from MW_rcvry_trx where instr_num=:insr_num and crnt_rec_flg=1 and RCVRY_TYP_SEQ =mt.typ_seq) as rcvry_trx    \r\n"
                        + "from mw_clnt mc\r\n"
                        + "left outer join mw_loan_app ap on mc.clnt_seq=ap.clnt_seq and ap.crnt_rec_flg=1 and ap.loan_cycl_num=(select max(loan_cycl_num) from mw_loan_app where crnt_rec_flg=1 and clnt_seq=ap.clnt_seq and loan_app_sts!=765)  and ap.prd_seq!=29 and ap.loan_app_sts!=765\r\n"
                        + "left outer join mw_port mp on mp.port_seq=ap.port_seq and mp.crnt_rec_flg=1 \r\n"
                        + "left outer join mw_brnch mb on mb.brnch_seq=mp.brnch_seq and mb.crnt_rec_flg=1\r\n"
                        + "left outer join mw_typs mt on mt.brnch_seq=mb.brnch_seq and mt.typ_ctgry_key=4 and mt.crnt_rec_flg=1 and mt.typ_id = LPAD(:trx_id, 4,'0')\r\n"
                        + "--left outer join mw_rcvry_trx mtrx on mtrx.RCVRY_TYP_SEQ =mt.typ_seq and  mtrx.instr_num=:insr_num and mtrx.crnt_rec_flg=1 \r\n"
                        + "where mc.clnt_seq =:clnt_seq and mc.crnt_rec_flg=1";
                    Query cncQuery = em.createNativeQuery(cQuery).setParameter("clnt_seq", tempArr[1])
                        .setParameter("insr_num", tempArr[0]).setParameter("trx_id", tempArr[4]);
                    List<Object[]> resultSet = cncQuery.getResultList();
                    if (resultSet.size() <= 0) {
                        validItem = false;
                        fileValid = false;
                        reason = "Client Not Found.";
                    }
                    if (resultSet.size() > 1) {
                        validItem = false;
                        fileValid = false;
                        reason = "Data Duplication Found.";
                    }

                    if (validItem) {
                        Object[] obj = resultSet.get(0);
                        if (obj != null) {
                            item.put("firstName", obj[0] == null ? "" : obj[0]);
                            item.put("lastName", obj[1] == null ? "" : obj[1]);
                        }
                        if (validItem) {
                            if (obj[2] == null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Branch not found for client.";
                            }
                        }
                        if (validItem) {
                            if (obj[3] == null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Agent Id not found for Branch in Types.";
                            }
                        }
                        if (validItem) {
                            BigDecimal bd = new BigDecimal(obj[4].toString());
                            if (bd.intValue() >= 1) {
                                validItem = false;
                                fileValid = false;
                                reason = "Recovery Exists against this Transaction Id for ADC.";
                            }
                        }
                    }

                    if (validItem) {
                        for (int z = 0; z < list.size(); z++) {
                            Map<String, Object> obj1 = (Map) list.get(z);
                            if (obj1.get("transNum").toString().equals(tempArr[0])
                                && obj1.get("agntId").toString().equals(tempArr[4])) {
                                validItem = false;
                                fileValid = false;
                                reason = "Duplicate Entry In File.";
                            }
                        }
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    if (validItem)
                        list.add(item);
                    else
                        list.add(0, item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String postRecoveryData() {
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("recovery.BulkRecovery");
        storedProcedure.registerStoredProcedureParameter("userid", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_msg", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("userid", SecurityContextHolder.getContext().getAuthentication().getName());
        String resp = "";
        // execute SP
        storedProcedure.execute();
        // get result
        resp = (String) storedProcedure.getOutputParameterValue("p_msg");

        return resp;
    }

    public String loadRecoveryData() {
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();
        File sp = null;
        String trxQuery = "truncate table MW_rcvry_Load_stg";
        Query query = em.createNativeQuery(trxQuery);
        query.executeUpdate();
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "RecoveryFile" + sp.separator + "ADC_FILE.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwRcvryLoadStg> mwRcvryLoadStgs = new ArrayList<>();
            List<BigInteger> trxSeqs = new ArrayList<>();
            List<Long> agntId = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false && !line.contains(",,,,")) {
                    tempArr = line.split(",");
                    MwRcvryLoadStg mwRcvryLoadStg = new MwRcvryLoadStg();
                    long seq = SequenceFinder.findNextVal(Sequences.RCVRY_LOAD_STG_SEQ);

                    mwRcvryLoadStg.setRcvryLoadStgSeq(seq);
                    mwRcvryLoadStg.setTrxId(tempArr[0]);
                    mwRcvryLoadStg.setTrxStsKey(true);
                    mwRcvryLoadStg.setCmnt("");
                    mwRcvryLoadStg.setClntId(Long.parseLong(tempArr[1]) + "");
                    mwRcvryLoadStg.setAmt(Double.parseDouble(tempArr[2]));
                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(tempArr[3]);
                    Instant reqInstant = date.toInstant();
                    log.debug("actual    " + tempArr[3]);
                    log.debug("date    " + date);
                    log.debug("reqInstant    " + reqInstant);
                    mwRcvryLoadStg.setTrxDt((reqInstant));

                    MwBrnch brnch = mwBrnchRepository.findOneByClntSeq(Long.parseLong(tempArr[1]));
                    MwTyps typ = mwTypsRepository.findOneByTypIdAndCrntRecFlgAndBrnchSeqAndTypCtgryKey(
                        String.format("%04d", Long.parseLong(tempArr[4])), true, brnch.getBrnchSeq(), 4L);
                    mwRcvryLoadStg.setAgentId(typ.getTypSeq());
                    mwRcvryLoadStg.setLoadDt(Instant.now());
                    mwRcvryLoadStgs.add(mwRcvryLoadStg);
                    trxSeqs.add(new BigInteger(tempArr[0] + tempArr[4]));
                    // agntId.add( Long.parseLong( tempArr[ 4 ] ) );
                }
                flag = false;
            }
            mwRcvryLoadStgRepository.save(mwRcvryLoadStgs);
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Started Processing";
    }

    public String loadRecoveryDataOld() {
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();
        File sp = null;
        String trxQuery = "truncate table MW_rcvry_Load_stg";
        Query query = em.createNativeQuery(trxQuery);
        query.executeUpdate();
        // String csvFile = "D:" + sp.separator + "loaderfiles" + sp.separator + "ADC_FILE.csv";
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "RecoveryFile" + sp.separator + "ADC_FILE.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwRcvryLoadStg> mwRcvryLoadStgs = new ArrayList<>();
            List<BigInteger> trxSeqs = new ArrayList<>();
            List<Long> agntId = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false && !line.contains(",,,,")) {
                    tempArr = line.split(",");
                    if (tempArr.length != 5) {
                        continue;
                    }
                    MwRcvryLoadStg mwRcvryLoadStg = new MwRcvryLoadStg();
                    long seq = SequenceFinder.findNextVal(Sequences.RCVRY_LOAD_STG_SEQ);

                    mwRcvryLoadStg.setRcvryLoadStgSeq(seq);
                    mwRcvryLoadStg.setTrxId(tempArr[0]);
                    mwRcvryLoadStg.setTrxStsKey(true);
                    mwRcvryLoadStg.setCmnt("");

                    // trxQuery = "select count(1) from MW_rcvry_trx where instr_num='" + tempArr[ 0 ] + "' and RCVRY_TYP_SEQ = "
                    // + tempArr[ 4 ];
                    // log.debug( "trxQuery " + trxQuery );
                    // query = em.createNativeQuery( trxQuery );
                    // BigDecimal dec = ( BigDecimal ) query.getSingleResult();
                    // int count = dec.intValue();
                    // if ( count >= 1 ) {
                    // mwRcvryLoadStg.setCmnt( "Already exists" );
                    // mwRcvryLoadStg.setTrxStsKey( false );
                    // } else {
                    // trxQuery = "select count(1) from MW_rcvry_Load_stg where trx_id =" + tempArr[ 0 ];
                    // log.debug( "trxQuery " + trxQuery );
                    // query = em.createNativeQuery( trxQuery );
                    // dec = ( BigDecimal ) query.getSingleResult();
                    // count = dec.intValue();
                    // if ( count >= 1 ) {
                    // mwRcvryLoadStg.setCmnt( "Already Loaded" );
                    // mwRcvryLoadStg.setTrxStsKey( false );
                    // } else if ( trxSeqs.contains( new BigInteger( tempArr[ 0 ] + tempArr[ 4 ] ) ) ) {
                    // mwRcvryLoadStg.setCmnt( "Duplicate entry" );
                    // mwRcvryLoadStg.setTrxStsKey( false );
                    // }
                    // }

                    // String clntQuery = "select clnt_id from mw_clnt where clnt_id=" + Long.parseLong( tempArr[ 1 ] );
                    // log.debug( "clntQuery " + clntQuery );
                    // query = em.createNativeQuery( clntQuery );
                    // List< String > clntIds = query.getResultList();
                    // String clntId = "";
                    // if ( clntIds.size() > 0 ) {
                    // clntId = clntIds.get( 0 );
                    // log.debug( "clntId " + clntId );
                    //
                    // } else {
                    // if ( mwRcvryLoadStg.getCmnt().equals( "" ) && mwRcvryLoadStg.getCmnt() != null ) {
                    // mwRcvryLoadStg.setCmnt( "Client Id not found" );
                    // mwRcvryLoadStg.setTrxStsKey( false );
                    // }
                    // }

                    MwBrnch brnch = mwBrnchRepository.findOneByClntId(tempArr[1]);
                    MwTyps typ = mwTypsRepository.findOneByTypIdAndCrntRecFlgAndBrnchSeqAndTypCtgryKey(
                        String.format("%04d", Long.parseLong(tempArr[4])), true, brnch.getBrnchSeq(), 4L);

                    mwRcvryLoadStg.setClntId(tempArr[1]);
                    mwRcvryLoadStg.setAmt(Double.parseDouble(tempArr[2]));

                    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(tempArr[3]);
                    Instant reqInstant = date.toInstant();
                    log.debug("actual    " + tempArr[3]);
                    log.debug("date    " + date);
                    log.debug("reqInstant    " + reqInstant);
                    mwRcvryLoadStg.setTrxDt((reqInstant));
                    mwRcvryLoadStg.setAgentId(typ.getTypSeq());
                    mwRcvryLoadStg.setLoadDt(Instant.now());

                    mwRcvryLoadStgs.add(mwRcvryLoadStg);
                    trxSeqs.add(new BigInteger(tempArr[0] + tempArr[4]));
                    // agntId.add( Long.parseLong( tempArr[ 4 ] ) );
                }
                flag = false;
            }
            mwRcvryLoadStgRepository.save(mwRcvryLoadStgs);
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    public Map validateTargetData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        String csvFile = "/srv/samba/Targets/Targets_Data.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("year", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("period", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("branch", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("prdGrp", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("clients", tempArr[4] == null ? "" : tempArr[4]);
                    item.put("amount", tempArr[5] == null ? "" : tempArr[5]);
                    // Long type;

                    String query = "select count(1) from MW_PERD where perd_key = " + tempArr[1];
                    Query perdQuery = em.createNativeQuery(query);
                    BigDecimal dec = (BigDecimal) perdQuery.getSingleResult();
                    int count = dec.intValue();
                    if (count <= 0) {
                        validItem = false;
                        fileValid = false;
                        reason = "Period Not Found.";
                    }

                    Long prdGrp = Long.parseLong(tempArr[3]);
                    if (prdGrp > 0) {
                        String prdGrpquery = "select count(1) from mw_prd_grp where prd_grp_seq=" + prdGrp + " and crnt_rec_flg=1";
                        Query typsQuery = em.createNativeQuery(prdGrpquery);
                        BigDecimal prdGrpdec = (BigDecimal) typsQuery.getSingleResult();
                        int prdGrpcount = prdGrpdec.intValue();
                        if (prdGrpcount <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Product Group Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Product Group.";
                    }
                    Long branch = Long.parseLong(tempArr[2]);
                    if (branch > 0) {
                        String bquery = "select count(1) from mw_brnch where brnch_seq=" + branch + " and crnt_rec_flg=1";
                        Query brnchQuery = em.createNativeQuery(bquery);
                        BigDecimal bdec = (BigDecimal) brnchQuery.getSingleResult();
                        int bcount = bdec.intValue();
                        if (bcount <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Branch Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Branch Seq Provided.";
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveTargetData() { // TRGT Period exists? in MW_PERD // PRD GRP SEQ VALID // BRNCH SEQ VALID
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        // String csvFile ="C:\\Users\\Admin\\Desktop\\Target_Data.csv";

        String csvFile = "/srv/samba/Targets/Targets_Data.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwBrnchTrgt> mwBrnchTrgts = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 6) {
                        continue;
                    }
                    MwBrnchTrgt mwBrnchTrgt = new MwBrnchTrgt();
                    long seq = SequenceFinder.findNextVal(Sequences.BRNCH_TARGETS_SEQ);

                    mwBrnchTrgt.setBrnchTargetsSeq(seq);
                    mwBrnchTrgt.setTrgtYr(Long.parseLong(tempArr[0]));
                    mwBrnchTrgt.setTrgtPerd(Long.parseLong(tempArr[1]));
                    mwBrnchTrgt.setBrnchSeq(Long.parseLong(tempArr[2]));
                    mwBrnchTrgt.setPrdSeq(Long.parseLong(tempArr[3]));
                    mwBrnchTrgt.setTrgtClients(Long.parseLong(tempArr[4]));
                    mwBrnchTrgt.setTrgtAmt(Double.parseDouble(tempArr[5]));
                    mwBrnchTrgt.setCrtdBy(currUser);
                    mwBrnchTrgt.setCrtdDt(currIns);
                    mwBrnchTrgt.setDelFlg(false);

                    mwBrnchTrgts.add(mwBrnchTrgt);
                }
                flag = false;
            }
            br.close();
            Query update = em.createNativeQuery("update MW_brnch_trgt set DEL_FLG=1 where TRGT_PERD=:prd and TRGT_YR=:yr")
                .setParameter("prd", mwBrnchTrgts.get(0).getTrgtPerd()).setParameter("yr", mwBrnchTrgts.get(0).getTrgtYr());
            update.executeUpdate();
            this.mwBrnchTrgtRepository.save(mwBrnchTrgts);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    public List<MwRcvryLoadStg> getFileData(String type) {
        return this.mwRcvryLoadStgRepository.findAll();
    }

    public Map validateTaggingData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        String csvFile = "/srv/samba/Taggings/Tagging_Data.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("clientSeq", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("tagSeq", tempArr[1] == null ? "" : tempArr[1]);
                    // Added by Zohaib Asim - Dated 25-01-2022
                    // Tagging / Blacklist
                    if (!tempArr[1].equals("1") && !tempArr[1].equals("2")) {
                        validItem = false;
                        fileValid = false;
                        reason = "Only Allowed Blacklist/Opportuniest";
                    }
                    // End

                    String query = "select count(1) from mw_clnt where clnt_seq = " + Long.parseLong(tempArr[0]) + " and crnt_rec_flg=1";
                    Query perdQuery = em.createNativeQuery(query);
                    BigDecimal dec = (BigDecimal) perdQuery.getSingleResult();
                    int count = dec.intValue();
                    if (count <= 0) {
                        validItem = false;
                        fileValid = false;
                        reason = "Client Not Found.";
                    }
                    if (validItem) {
                        String prdGrpquery = "select count(1) from mw_tags where tags_seq=" + tempArr[1] + " and crnt_rec_flg=1";
                        Query typsQuery = em.createNativeQuery(prdGrpquery);
                        BigDecimal prdGrpdec = (BigDecimal) typsQuery.getSingleResult();
                        int prdGrpcount = prdGrpdec.intValue();
                        if (prdGrpcount <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Tag Not Found.";
                        }
                    }
                    if (validItem) {
                        String cnicquery = "select cnic_num from mw_clnt where crnt_rec_flg=1 and clnt_seq=" + Long.parseLong(tempArr[0]);
                        Query qt = em.createNativeQuery(cnicquery);
                        Object obj = qt.getSingleResult();
                        if (obj != null) {
                            Long result = (new BigDecimal(obj.toString())).longValue();
                            MwClntTagList exTag = mwClntTagListRepository.findOneByCnicNumAndDelFlg(result, false);
                            if (exTag != null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Client is already Tagged.";
                            }
                        } else {
                            validItem = false;
                            fileValid = false;
                            reason = "Client CNIC not found.";
                        }
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveTaggingData() { // CLIENT_ID should be an active client
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        // String csvFile ="C:\\Users\\Admin\\Desktop\\Tagging_Data.csv";

        String csvFile = "/srv/samba/Taggings/Tagging_Data.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwClntTagList> mwClntTagLists = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 2) {
                        continue;
                    }

                    String query = "select cnic_num from mw_clnt where crnt_rec_flg=1 and clnt_seq=" + Long.parseLong(tempArr[0]);

                    // Added by Zohaib Asim - Dated 04-05-2021 - Loan Info
                    List<MwLoanApp> loanAppList = mwLoanAppRepository.findAllByClntSeqAndCrntRecFlgOrderByLoanCyclNumDesc(Long.parseLong(tempArr[0]), true);
                    // End

                    Query qt = em.createNativeQuery(query);
                    Long result = (new BigDecimal(qt.getSingleResult().toString())).longValue();
                    MwClntTagList old = mwClntTagListRepository.findOneByCnicNumAndDelFlg(result, false);
                    if (old != null) {
                        old.setDelFlg(true);
                        mwClntTagLists.add(old);
                    } else {
                        long seq = SequenceFinder.findNextVal(Sequences.CLNT_TAG_LIST_SEQ);
                        MwClntTagList mwClntTagList = new MwClntTagList();

                        mwClntTagList.setClntTagListSeq(seq);
                        mwClntTagList.setEffStartDt(currIns);
                        mwClntTagList.setCnicNum(result);
                        mwClntTagList.setTagsSeq(Long.parseLong(tempArr[1]));
                        mwClntTagList.setCrtdBy(currUser);
                        mwClntTagList.setCrtdDt(currIns);
                        mwClntTagList.setDelFlg(false);
                        mwClntTagList.setLastUpdBy(currUser);
                        mwClntTagList.setLastUpdDt(currIns);
                        mwClntTagList.setTagFromDt(currIns);
                        mwClntTagList.setTagToDt(currIns.plusSeconds(94608000));
                        mwClntTagList.setRmks("");

                        // Added by Zohaib Asim - Dated 04-05-2021 - Loan App Seq
                        if (loanAppList.size() > 0) {
                            mwClntTagList.setLoanAppSeq(loanAppList.get(0).getLoanAppSeq());
                        } else {
                            mwClntTagList.setLoanAppSeq(-1L);
                        }
                        mwClntTagList.setCrntRecFlg(true);
                        // End

                        mwClntTagLists.add(mwClntTagList);

                        // MwTags tag = mwTagsRepository.findOneByTagsSeqAndCrntRecFlg( Long.parseLong( tempArr[ 1 ] ), true );
                        // if ( tag != null && tag.getTagId().equals( "0005" ) ) {
                        // StoredProcedureQuery tagProc = em.createStoredProcedureQuery( "WO_PROVISION_FOR_LOAN_LOSS" );
                        // tagProc.registerStoredProcedureParameter( "p_CLNT_SEQ", Long.class, ParameterMode.IN );
                        // tagProc.registerStoredProcedureParameter( "p_user", String.class, ParameterMode.IN );
                        // // tagProc.registerStoredProcedureParameter( "p_brnch_seq", Long.class, ParameterMode.IN );
                        //
                        // tagProc.setParameter( "p_CLNT_SEQ", Long.parseLong( tempArr[ 0 ] ) );
                        // tagProc.setParameter( "p_user", SecurityContextHolder.getContext().getAuthentication().getName() );
                        // // tagProc.setParameter( "p_brnch_seq", appRcvryDTO.pymtAmt );
                        //
                        // // call the stored procedure and get the result
                        // tagProc.execute();
                        // // Double sum = (Double) query.getOutputParameterValue("sum");
                        // log.info( "Executing WO_PROVISION_FOR_LOAN_LOSS for Client : " + tempArr[ 0 ] );
                        // }
                    }
                }
                flag = false;
            }
            br.close();
            this.mwClntTagListRepository.save(mwClntTagLists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    private boolean compareDates(Instant instant) {
        Date now = Date.from(Instant.now());
        String crruntDate = new SimpleDateFormat("dd-MM-yyyy").format(now);
        String crtDt = new SimpleDateFormat("dd-MM-yyyy").format(Date.from(instant));
        boolean flag = true;
        try {
            now = new SimpleDateFormat("dd-MM-yyyy").parse(crruntDate);
            Date crtDte = new SimpleDateFormat("dd-MM-yyyy").parse(crtDt);
            if (now.compareTo(crtDte) == 0) {
                flag = false;
            }
            flag = true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;

    }

    public Map validateClaimsData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        String csvFile = "/srv/samba/InsuranceClaims/InsuranceClaims_Data.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            List<String> claimIds = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("client", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("amount", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("claim", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("active", tempArr[4] == null ? "" : tempArr[4]);
                    item.put("cardNum", tempArr[6] == null ? "" : tempArr[6]);

                    // Added by Zohaib Asim - Dated 17-12-2020
                    // Health Claim Type
                    String healthClaimType = "";
                    if (tempArr[5] != null) {
                        if (tempArr[5].equals("1")) {
                            healthClaimType = "KSZB";
                        } else if (tempArr[5].equals("2")) {
                            healthClaimType = "KSZB Islamic";
                        } else if (tempArr[5].equals("3")) {
                            healthClaimType = "Kashf Care (KC)";
                        }
                        item.put("healthClaimType", tempArr[5] == null ? "" : healthClaimType);
                    }
                    // End by Zohaib Asim

                    // Long type;
                    String query = "select mc.FRST_NM, mc.LAST_NM,mb.BRNCH_NM,ma.AREA_NM, mr.REG_NM\r\n" + "from mw_clnt mc\r\n"
                        + "left outer join mw_port mp on mp.port_seq=mc.port_key and mp.crnt_rec_flg=1\r\n"
                        + "left outer join mw_brnch mb on mb.brnch_seq=mp.brnch_seq and mb.crnt_rec_flg=1\r\n"
                        + "left outer join mw_area ma on ma.area_seq=mb.area_seq and ma.crnt_rec_flg=1\r\n"
                        + "left outer join mw_reg mr on mr.reg_seq = ma.reg_seq and mr.crnt_rec_flg=1\r\n"
                        + "where mc.clnt_seq =:clntSeq and mc.crnt_rec_flg=1";
                    Query clntQuery = em.createNativeQuery(query).setParameter("clntSeq", tempArr[1]);
                    List<Object[]> resultSet = clntQuery.getResultList();
                    if (resultSet != null && resultSet.size() == 1) {
                        Object[] obj = resultSet.get(0);
                        if (obj != null) {
                            item.put("firstName", obj[0] == null ? "" : obj[0]);
                            item.put("lastName", obj[1] == null ? "" : obj[1]);
                            item.put("brnchName", obj[2] == null ? "" : obj[2]);
                            item.put("areaName", obj[3] == null ? "" : obj[3]);
                            item.put("regname", obj[4] == null ? "" : obj[4]);
                            if (obj[2] == null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Branch Not Found for Client.";
                            }
                        }
                    } else if (resultSet.size() > 1) {
                        validItem = false;
                        fileValid = false;
                        reason = "Duplicate Record In Mw Clnt.";
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Client Not Found.";
                    }

                    if (claimIds.contains(tempArr[3]) && validItem) {
                        validItem = false;
                        fileValid = false;
                        reason = "Duplicate Record In File.";
                    }
                    if (claimIds.contains(tempArr[6]) && validItem) {
                        validItem = false;
                        fileValid = false;
                        reason = "Duplicate Record In Card Number.";
                    }
                    // Added by Zohaib Asim - Dated 27-08-2021 - CR: Sanction List Phase 2
                    // KSZB Claim Restriction; if found in Tag List
                    Query qry = em.createNativeQuery("SELECT FN_FIND_CLNT_TAGGED('AML', " +
                        tempArr[1] + ", 0) FROM DUAL");
                    String qryResp = qry.getSingleResult().toString();
                    if (qryResp.contains("SUCCESS")) {
                        validItem = false;
                        fileValid = false;
                        reason = "AML Matched";
                    }
                    // End by Zohaib Asim

                    if (validItem) {
                        MwClntHlthInsrClm clm = mwClntHlthInsrClmRepository.findOneByTransactionIdAndCrntRecFlg(tempArr[3], true);
                        if (clm != null) {
                            validItem = false;
                            fileValid = false;
                            reason = "Claim Already Loaded (" + clm.getClntHlthClmSeq() + ").";
                        }
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                    claimIds.add(tempArr[3]);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveClaimData() { /// CLNT _SEQ , BRNCH SEQ ,
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        // String csvFile ="C:\\Users\\Admin\\Desktop\\InsuranceClaim_Data.csv";

        String csvFile = "/srv/samba/InsuranceClaims/InsuranceClaims_Data.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwClntHlthInsrClm> mwClntHlthInsrClms = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");

                    // Modified by Zohaib Asim - Dated 16-12-2020
                    // Length changed from 5 to 6
                    if (tempArr.length != 7) {
                        continue;
                    }
                    MwClntHlthInsrClm preClim = mwClntHlthInsrClmRepository.findOneByTransactionIdAndCrntRecFlg(tempArr[3], true);
                    if (preClim != null) {
                        preClim.setDelFlg(true);
                        preClim.setCrntRecFlg(false);
                        preClim.setLastUpdBy(currUser);
                        preClim.setLastUpdDt(Instant.now());
                        preClim.setEffEndDt(Instant.now());
                    }

                    MwClntHlthInsrClm mwClntHlthInsrClm = new MwClntHlthInsrClm();
                    long seq = SequenceFinder.findNextVal(Sequences.CLNT_HLTH_CLM_SEQ);

                    mwClntHlthInsrClm.setClntHlthClmSeq(seq);
                    mwClntHlthInsrClm.setBrnchSeq(Long.parseLong(tempArr[0]));
                    mwClntHlthInsrClm.setClntSeq(Long.parseLong(tempArr[1]));// Long.parseLong(tempArr[1]));
                    mwClntHlthInsrClm.setClmAmt(Double.parseDouble(tempArr[2]));
                    mwClntHlthInsrClm.setTransactionId(tempArr[3]);
                    mwClntHlthInsrClm.setClmStsKey(0L);
                    mwClntHlthInsrClm.setCrtdBy(currUser);
                    mwClntHlthInsrClm.setCrtdDt(currIns);
                    mwClntHlthInsrClm.setLastUpdBy(currUser);
                    mwClntHlthInsrClm.setLastUpdDt(currIns);
                    mwClntHlthInsrClm.setDelFlg(false);
                    mwClntHlthInsrClm.setEffStartDt(currIns);
                    mwClntHlthInsrClm.setCrntRecFlg(true);
                    mwClntHlthInsrClm.setClmTyp(Long.parseLong(tempArr[4]));
                    mwClntHlthInsrClm.setPostFlg(false);

                    // Added by Zohaib Asim - Dated 16-12-2020
                    // Health Claim Type added
                    if (tempArr[5] != null) {
                        mwClntHlthInsrClm.setHlthClmTyp(Long.parseLong(tempArr[5]));
                    }
                    // End

                    if (tempArr[6] != null) {
                        mwClntHlthInsrClm.setCardNum(tempArr[6]);
                    } else {
                        continue;
                    }

                    if (!mwClntHlthInsrClms.contains(mwClntHlthInsrClm)) {
                        mwClntHlthInsrClms.add(mwClntHlthInsrClm);
                    }

                }
                flag = false;
            }
            br.close();

            mwClntHlthInsrClmRepository.save(mwClntHlthInsrClms);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "file loaded";
    }

    public boolean postClaimsData() {

        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("KSZB_claim_jv");
        // execute SP
        boolean flag = storedProcedure.execute();
        // get result
        return flag;
    }

    public Map validateWriteOffData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        String csvFile = "/srv/samba/WriteOff/WriteOff_Data.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;

            MwTags tag = mwTagsRepository.findOneByTagIdAndCrntRecFlg("0005", true);
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("branch", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("product", tempArr[1] == null ? "" : tempArr[1]);
                    item.put("client", tempArr[2] == null ? "" : tempArr[2]);
                    item.put("loan", tempArr[3] == null ? "" : tempArr[3]);
                    item.put("disbDate", tempArr[4] == null ? "" : tempArr[4]);
                    item.put("recAmount", tempArr[5] == null ? "" : tempArr[5]);
                    item.put("odAmount", tempArr[6] == null ? "" : tempArr[6]);
                    item.put("odDays", tempArr[7] == null ? "" : tempArr[7]);
                    item.put("outstanding", tempArr[8] == null ? "" : tempArr[8]);
                    // Long type;
                    Long branch = Long.parseLong(tempArr[0]);
                    if (branch > 0) {
                        String bquery = "select count(1) from mw_brnch where brnch_seq=" + branch + " and crnt_rec_flg=1";
                        Query brnchQuery = em.createNativeQuery(bquery);
                        BigDecimal bdec = (BigDecimal) brnchQuery.getSingleResult();
                        int bcount = bdec.intValue();
                        if (bcount <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Branch Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Branch Seq Provided.";
                    }
                    String pquery = "select count(1) from mw_clnt where clnt_seq = " + tempArr[2] + " and crnt_rec_flg=1";
                    Query perdQuery = em.createNativeQuery(pquery);
                    BigDecimal dec = (BigDecimal) perdQuery.getSingleResult();
                    int count = dec.intValue();
                    if (count <= 0) {
                        validItem = false;
                        fileValid = false;
                        reason = "Client Not Found.";
                    } else {
                        String cnicquery = "select cnic_num from mw_clnt where clnt_seq = " + tempArr[2] + " and crnt_rec_flg=1";
                        Query cnicQuery = em.createNativeQuery(cnicquery);
                        Object cnicObject = cnicQuery.getSingleResult();
                        if (cnicObject == null || cnicObject.toString().equals("null")) {
                            // validItem = false;
                            // fileValid = false;
                            reason = "Client CNIC Not Found.";
                            MwLoanApp app = mwLoanAppRepository
                                .findOneByLoanAppSeqAndCrntRecFlg(new BigDecimal(tempArr[3]).longValue(), true);
                            if (app == null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Client CNIC Not Found / Loan App Not Found";
                            } else {
                                MwClntTagList tags = mwClntTagListRepository.findOneByLoanAppSeqAndTagsSeqAndDelFlg(app.getLoanAppSeq(),
                                    tag.getTagsSeq(), false);
                                if (tags != null) {
                                    validItem = false;
                                    fileValid = false;
                                    reason = "Client already tagged against this Loan ID.";
                                }
                            }
                        } else {
                            MwClntTagList tags = mwClntTagListRepository.findOneByCnicNumAndTagsSeqAndDelFlg(
                                Long.parseLong(cnicObject.toString()), tag.getTagsSeq(), false);
                            if (tags != null) {
                                validItem = false;
                                fileValid = false;
                                reason = "Client already tagged.";
                            }
                        }
                    }

                    String lquery = "select count(1) from mw_loan_app where loan_app_seq = " + tempArr[3] + " and crnt_rec_flg=1";
                    Query lQuery = em.createNativeQuery(lquery);
                    BigDecimal ldec = (BigDecimal) lQuery.getSingleResult();
                    int lcount = ldec.intValue();
                    if (lcount <= 0) {
                        validItem = false;
                        fileValid = false;
                        reason = "Loan Not Found.";
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveWriteOffData() {/// CLNT , LOAN, * ADD LOAN APP SEQ TO CLNT TAG LIST
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        // String csvFile ="C:\\Users\\Admin\\Desktop\\WriteOff_Data.csv";

        String csvFile = "/srv/samba/WriteOff/WriteOff_Data.csv";

        MwTags tag = mwTagsRepository.findOneByTagIdAndCrntRecFlg("0005", true);
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwClntTagList> mwClntTagLists = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 9) {
                        continue;
                    }
                    Query qt = em.createNativeQuery(
                        "select cnic_num from mw_clnt where crnt_rec_flg=1 and clnt_id=" + Long.parseLong(tempArr[2]));
                    Object queryResult = qt.getSingleResult();
                    Long result = null;
                    MwClntTagList tags = null;
                    if (queryResult != null && !queryResult.toString().equals("null")) {
                        result = (new BigDecimal(qt.getSingleResult().toString())).longValue();
                        tags = mwClntTagListRepository.findOneByCnicNumAndDelFlg(result, false);
                    } else {
                        tags = mwClntTagListRepository.findOneByLoanAppSeqAndDelFlg(new BigDecimal(tempArr[3]).longValue(), false);
                    }
                    if (tags != null) {
                        tags.setDelFlg(true);
                        mwClntTagLists.add(tags);
                    }

                    StoredProcedureQuery tagProc = em.createStoredProcedureQuery("WO_PROVISION_FOR_LOAN_LOSS");
                    tagProc.registerStoredProcedureParameter("p_CLNT_SEQ", Long.class, ParameterMode.IN);
                    tagProc.registerStoredProcedureParameter("p_user", String.class, ParameterMode.IN);
                    // tagProc.registerStoredProcedureParameter( "p_brnch_seq", Long.class, ParameterMode.IN );

                    tagProc.setParameter("p_CLNT_SEQ", Long.parseLong(tempArr[2]));
                    tagProc.setParameter("p_user", SecurityContextHolder.getContext().getAuthentication().getName());
                    // tagProc.setParameter( "p_brnch_seq", appRcvryDTO.pymtAmt );

                    // call the stored procedure and get the result
                    tagProc.execute();
                    // Double sum = (Double) query.getOutputParameterValue("sum");
                    log.info("Executing WO_PROVISION_FOR_LOAN_LOSS for Client : " + tempArr[2]);
                    // else {
                    // MwRefCdVal val = mwRefCdValRepository.findRefCdByGrpAndVal( "0106", "1245" );
                    // MwLoanApp exApp = mwLoanAppRepository.findOneByLoanAppSeqAndCrntRecFlg( new BigDecimal( tempArr[ 3 ] ).longValue(),
                    // true );
                    // if ( exApp != null && val != null ) {
                    // exApp.setCrntRecFlg( false );
                    // exApp.setDelFlg( true );
                    // exApp.setLastUpdDt( Instant.now() );
                    // exApp.setEffEndDt( Instant.now() );
                    // exApp.setLastUpdBy( currUser );
                    // mwLoanAppRepository.save( exApp );
                    // MwLoanApp app = new MwLoanApp();
                    // app.setAprvdLoanAmt( exApp.getAprvdLoanAmt() );
                    // app.setClntSeq( exApp.getClntSeq() );
                    // app.setCmnt( exApp.getCmnt() );
                    // app.setCoBwrAddrAsClntFlg( exApp.getCoBwrAddrAsClntFlg() );
                    // app.setCrdtBnd( exApp.getCrdtBnd() );
                    // app.setCrntRecFlg( true );
                    // app.setCrtdBy( exApp.getCrtdBy() );
                    // app.setCrtdDt( exApp.getCrtdDt() );
                    // app.setDelFlg( false );
                    // app.setEffStartDt( Instant.now() );
                    // app.setLastUpdBy( currUser );
                    // app.setLastUpdDt( Instant.now() );
                    // app.setLoanAppId( exApp.getLoanAppId() );
                    // app.setLoanAppSeq( exApp.getLoanAppSeq() );
                    // app.setLoanAppSts( val.getRefCdSeq() );
                    // app.setLoanAppStsDt( Instant.now() );
                    // app.setLoanCyclNum( exApp.getLoanCyclNum() );
                    // app.setLoanId( exApp.getLoanId() );
                    // app.setLoanUtlCmnt( exApp.getLoanUtlCmnt() );
                    // app.setLoanUtlStsSeq( exApp.getLoanUtlStsSeq() );
                    // app.setPortSeq( exApp.getPortSeq() );
                    // app.setPrntLoanAppSeq( exApp.getPrntLoanAppSeq() );
                    // app.setPscScore( exApp.getPscScore() );
                    // app.setRcmndLoanAmt( exApp.getRcmndLoanAmt() );
                    // app.setRejectionReasonCd( exApp.getRejectionReasonCd() );
                    // app.setRelAddrAsClntFLg( exApp.getRelAddrAsClntFLg() );
                    // app.setRqstdLoanAmt( exApp.getRqstdLoanAmt() );
                    // mwLoanAppRepository.save( app );
                    // }
                    // // String query = "update mw_loan_app set loan_app_sts=(select ref_cd_seq\r\n" + "from mw_ref_cd_val val\r\n"
                    // // + "join mw_ref_cd_grp grp on grp.ref_cd_grp_seq = val.ref_cd_grp_key and grp.CRNT_REC_FLG=1 and grp.ref_cd_grp =
                    // // '0106'\r\n"
                    // // + "where val.CRNT_REC_FLG=1 and val.ref_cd ='1245'), loan where loan_id=" + Long.parseLong( tempArr[ 3 ] );
                    // // qt = em.createNativeQuery( query );
                    // // qt.executeUpdate();
                    //
                    // }
                    // long seq = SequenceFinder.findNextVal( Sequences.CLNT_TAG_LIST_SEQ );
                    // MwClntTagList mwClntTagList = new MwClntTagList();
                    //
                    // mwClntTagList.setClntTagListSeq( seq );
                    // mwClntTagList.setEffStartDt( currIns );
                    // mwClntTagList.setCnicNum( result );
                    //
                    // qt = em.createNativeQuery( "select tags_Seq from mw_tags where crnt_rec_flg=1 and tag_id=0005" );
                    // result = ( new BigDecimal( qt.getSingleResult().toString() ) ).longValue();
                    //
                    // mwClntTagList.setTagsSeq( result );
                    // mwClntTagList.setCrtdBy( currUser );
                    // mwClntTagList.setCrtdDt( currIns );
                    // mwClntTagList.setDelFlg( false );
                    // mwClntTagList.setLastUpdBy( currUser );
                    // mwClntTagList.setLastUpdDt( currIns );
                    // mwClntTagList.setTagFromDt( currIns );
                    // mwClntTagList.setLoanAppSeq( new BigDecimal( tempArr[ 3 ] ).longValue() );
                    // mwClntTagList.setTagToDt( currIns.plusSeconds( 94608000 ) );
                    // mwClntTagList.setRmks( "Written Off" );
                    //
                    // mwClntTagLists.add( mwClntTagList );
                }
                flag = false;
            }
            br.close();
            this.mwClntTagListRepository.save(mwClntTagLists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    public Map validateFundsData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        File sp = null;
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "Funds" + sp.separator + "Funds.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("branch", tempArr[0] == null ? "" : tempArr[0]);
                    item.put("funds", tempArr[1] == null ? "" : tempArr[1]);
                    // Long type;
                    Long branch = Long.parseLong(tempArr[0]);
                    if (branch > 0) {
                        String bquery = "select count(1) from mw_brnch where brnch_seq=" + branch + " and crnt_rec_flg=1";
                        Query brnchQuery = em.createNativeQuery(bquery);
                        BigDecimal bdec = (BigDecimal) brnchQuery.getSingleResult();
                        int bcount = bdec.intValue();
                        if (bcount <= 0) {
                            validItem = false;
                            fileValid = false;
                            reason = "Branch Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Branch Seq Provided.";
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public List<MwFndsLodr> loadFundsData() { // BRNCH
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();
        File sp = null;
        String trxQuery = "truncate table MW_FNDS_LODR";
        Query query = em.createNativeQuery(trxQuery);
        query.executeUpdate();
        // String csvFile = "D:" + sp.separator + "loaderfiles" + sp.separator + "ADC_FILE.csv";
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "Funds" + sp.separator + "Funds.csv";
        List<MwFndsLodr> fundsList = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            while ((line = br.readLine()) != null) {
                if (flag == false && !line.contains(",,,,")) {
                    tempArr = line.split(",");
                    if (tempArr.length != 2) {
                        continue;
                    }
                    MwFndsLodr fund = new MwFndsLodr();

                    fund.setBranchSeq(Long.parseLong(tempArr[0]));
                    fund.setFunds(Long.parseLong(tempArr[1]));
                    fund.setCrtdBy(currUser);
                    fund.setCrtdDt(currIns);
                    fundsList.add(fund);

                }
                flag = false;
            }
            mwFndsLodrRepository.save(fundsList);
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fundsList;
    }

    public boolean postFundsData() {
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("ho_funds");
        // execute SP
        boolean flag = storedProcedure.execute();
        // get result
        return flag;
    }

    public String getClientAndBranchName(String clntId) {
        Map<String, String> resp = new HashMap<String, String>();
        String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Instant currIns = Instant.now();

        String query = "select c.frst_nm || c.last_nm ||'+'|| brnch_nm from mw_clnt c join mw_loan_app a on c.clnt_seq=a.clnt_seq join mw_port p on p.port_seq=a.port_seq join mw_brnch b on b.brnch_seq=p.brnch_seq where c.crnt_rec_flg=1 and c.clnt_id="
            + clntId;
        Query qt = em.createNativeQuery(query);
        List<String> result = qt.getResultList();

        log.debug("result    " + result.get(0));
        return result.get(0);
    }

    public List<MwAmlList> loanAmlList() throws JSONException {
        String trxQuery = "truncate table MW_AML_LIST";
        Query query = em.createNativeQuery(trxQuery);
        query.executeUpdate();

        String dropSeqQuery = "drop sequence AML_LIST_SEQ";
        Query dquery = em.createNativeQuery(dropSeqQuery);
        dquery.executeUpdate();

        String cSeqQuery = "create sequence AML_LIST_SEQ";
        Query cquery = em.createNativeQuery(cSeqQuery);
        cquery.executeUpdate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        System.out.println("CR JSON found is: " + map.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://scsanctions.un.org/resources/xml/en/consolidated.xml",
            request, String.class);

        JSONObject xmlJSONObj = XML.toJSONObject(response.getBody());
        // String jsonPrettyPrintString = xmlJSONObj.toString( 4 );

        JSONObject env = xmlJSONObj.getJSONObject("CONSOLIDATED_LIST");
        JSONObject indls = env.getJSONObject("INDIVIDUALS");
        JSONArray individuals = indls.getJSONArray("INDIVIDUAL");
        List<MwAmlList> list = new ArrayList<>();
        for (int i = 0; i < individuals.length(); i++) {
            JSONObject individual = individuals.getJSONObject(i);
            MwAmlList aml = new MwAmlList();
            long pseq = SequenceFinder.findNextVal("AML_LIST_SEQ");
            if (individual.has("DATAID"))
                aml.setAmlDataId(individual.getInt("DATAID") + "");
            aml.setAmlListSeq(pseq);
            if (individual.has("COMMENTS1"))
                aml.setCmnts(individual.getString("COMMENTS1"));
            aml.setCrntRecFlg(true);
            aml.setCrtdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            aml.setCrtdDt(Instant.now());
            aml.setDelFlg(false);
            aml.setEffStartDt(Instant.now());
            if (individual.has("FIRST_NAME"))
                aml.setFrstNm(individual.getString("FIRST_NAME"));
            if (individual.has("SECOND_NAME"))
                aml.setScndNm(individual.getString("SECOND_NAME"));
            if (individual.has("THIRD_NAME"))
                aml.setThrdNm(individual.getString("THIRD_NAME"));
            aml.setLastUpdBy(SecurityContextHolder.getContext().getAuthentication().getName());
            aml.setLastUpdDt(Instant.now());

            if (individual.has("NATIONALITY")) {
                String nationalityStr = "";
                JSONObject nationality = individual.getJSONObject("NATIONALITY");
                if (nationality.has("VALUE")) {
                    JSONObject valueObj = new JSONObject();
                    Object values = nationality.get("VALUE");
                    if (values instanceof JSONArray) {
                        // JSON Array
                        JSONArray arr = (JSONArray) values;
                        for (int y = 0; y < arr.length(); y++) {
                            String dtl = arr.getString(y);
                            nationalityStr = nationalityStr + dtl.toString();
                        }
                    } else if (values instanceof JSONObject) {
                        // JSON Object
                        nationalityStr = nationalityStr + values.toString();
                    }
                }
                aml.setCntry(nationalityStr);
            }

            list.add(aml);
        }
        mwAmlListRepository.save(list);
        return list;
    }

    public MwAmlList checkAmlForUserName(String firstName, String lastName) {
        String[] splited = firstName.split("\\s+");
        List<MwAmlList> list = mwAmlListRepository.findAllByFrstNmIgnoreCaseContainingAndCrntRecFlg(splited[0], true);
        String name = firstName.toLowerCase() + ((lastName != null) ? " " + lastName.toLowerCase() : "");
        for (MwAmlList aml : list) {
            String amlName = aml.getFrstNm().toLowerCase() + ((aml.getScndNm() == null) ? "" : " " + aml.getScndNm().toLowerCase())
                + ((aml.getThrdNm() == null) ? "" : " " + aml.getThrdNm().toLowerCase());
            if (name.trim().equalsIgnoreCase(amlName.trim())) {
                return aml;
            }
        }
        return null;

    }

    public Map validateDeferLoan() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        File sp = null;
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "Defer" + sp.separator + "defer.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("loan_seq", tempArr[0] == null ? "" : tempArr[0]);
                    // item.put( "status", tempArr[ 1 ] == null ? "" : tempArr[ 1 ] );
                    // Long type;
                    Long loanAppSeq = Long.parseLong(tempArr[0]);
                    if (loanAppSeq > 0) {
                        String bquery = "select mc.frst_nm || ' ' || mc.last_nm, mc.clnt_id, app.loan_app_seq,val.ref_cd, max_inst(app.loan_app_seq), val.ref_cd_dscr\r\n"
                            + "from mw_loan_app app\r\n" + "join mw_clnt mc on mc.clnt_seq=app.clnt_seq and mc.crnt_rec_flg=1\r\n"
                            + "join mw_ref_cd_val val on val.ref_cd_seq=app.loan_app_sts and val.crnt_rec_flg=1\r\n"
                            + "where app.loan_app_seq=:loanAppSeq and app.crnt_rec_flg=1";
                        Query loanQuery = em.createNativeQuery(bquery).setParameter("loanAppSeq", loanAppSeq);
                        System.out.println("\n\n\n\n\n>>>>>>>>>>>>>>>>>> : " + bquery + "\n\n\n" + loanAppSeq);

                        List<Object[]> resultSet = loanQuery.getResultList();
                        if (resultSet != null && resultSet.size() == 1) {
                            Object[] obj = resultSet.get(0);
                            if (obj != null) {
                                item.put("clntName", obj[0] == null ? "" : obj[0]);
                                item.put("clntId", obj[1] == null ? "" : obj[1]);
                                item.put("loanAppSeq", obj[2] == null ? "" : obj[2]);
                                item.put("stsCd", obj[3] == null ? "" : obj[3]);
                                item.put("instNum", obj[4] == null ? "" : obj[4]);
                                item.put("loanSts", obj[5] == null ? "" : obj[5]);
                                if (obj[4] != null) {
                                    Long instNum = Long.parseLong(obj[4].toString());
                                    if (instNum.longValue() > 0) {
                                        validItem = false;
                                        fileValid = false;
                                        reason = "Loan has paid Installments.";
                                    }
                                }
                                if (obj[3] != null) {
                                    if (!obj[3].toString().equals("0005")) {
                                        validItem = false;
                                        fileValid = false;
                                        reason = "Loan is not Active.";
                                    }
                                }
                            }
                        } else if (resultSet.size() > 1) {
                            validItem = false;
                            fileValid = false;
                            reason = "Duplicate Record Found.";
                        } else {
                            validItem = false;
                            fileValid = false;
                            reason = "Client/Loan Not Found.";
                        }
                    } else {
                        validItem = false;
                        fileValid = false;
                        reason = "Invalid Loan Application Seq Provided.";
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public Map processDeferLoan(String token) throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        File sp = null;
        String csvFile = sp.separator + "srv" + sp.separator + "samba" + sp.separator + "Defer" + sp.separator + "defer.csv";
        boolean fileValid = true;
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");
                    Map<String, Object> item = new HashMap();
                    item.put("loan_seq", tempArr[0] == null ? "" : tempArr[0]);
                    // item.put( "status", tempArr[ 1 ] == null ? "" : tempArr[ 1 ] );
                    // Long type;
                    Long loanAppSeq = Long.parseLong(tempArr[0]);
                    String currUser = SecurityContextHolder.getContext().getAuthentication().getName();
                    ResponseEntity response = serviceClient.defferLoan(loanAppSeq, "Loader", currUser, token);
                    list.add(item);
                }
                i++;
            }
            resp.put("list", list);
            resp.put("valid", fileValid);
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    // Added By Naveed, Dated - 07-10-2021
    // for Validate outreach.csv file
    // Region cd must be valid and present
    public Map validateOutreachData() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();
        List<Long> mwRegSeqList = mwRegRepository.findAllByCrntRecFlgOrderByRegSeqDesc(true)
            .stream().map(MwReg::getRegSeq).collect(Collectors.toList());

        String csvFile = "/srv/samba/Targets/outreach.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFile)));
            String line = "";
            boolean flag = true;
            String[] tempArr;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (lineCount != 0) {

                    lineCount++;
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");

                    Map<String, Object> item = new HashMap();

                    if (tempArr[0] != null && !tempArr[0].isEmpty()) {
                        if (!mwRegSeqList.contains(new BigDecimal(tempArr[0]).longValue())) {
                            reason = "please provide valid region seq";
                            validItem = false;
                            flag = false;
                        } else {
                            if (isInValid(tempArr)) {
                                reason = "please provide valid value";
                                validItem = false;
                                flag = false;
                            }
                        }
                    } else {
                        reason = "please provide valid region seq";
                        validItem = false;
                        flag = false;
                    }
                    if (tempArr.length == 6) {
                        item.put("region", tempArr[0] == null ? 0 : tempArr[0]);
                        item.put("opening", tempArr[1] == null ? 0 : tempArr[1]);
                        item.put("targets", tempArr[2] == null ? 0 : tempArr[2]);
                        item.put("client", tempArr[3] == null ? 0 : tempArr[3]);
                        item.put("outreach", tempArr[4] == null ? 0 : tempArr[4]);
                        item.put("month", tempArr[5] == null ? "" : tempArr[5]);
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                lineCount++;
            }
            resp.put("list", list);
            resp.put("valid", flag);
        } catch (ArrayIndexOutOfBoundsException iob) {
            iob.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }
    // end By Naveed 07-10-2021

    private boolean isInValid(String[] tempArr) {
        boolean flag = false;
        if (tempArr.length == 6) {
            for (int i = 1; i < tempArr.length; i++) {
                if (tempArr[i] == null || tempArr[i].isEmpty()) {
                    flag = true;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    // load outreach.csv file
    // Region cd must be valid and present
    // record update only if Region Cd ans Outreach Month not be change
    // other then Region Cd and Outreach Month must not be null
    public String saveOutreachData() {
        Instant currIns = Instant.now();

        String csvFile = "/srv/samba/Targets/outreach.csv";

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwRegionWiseOutreach> outreachList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 6) {
                        continue;
                    }

                    MwRegionWiseOutreach outreach = new MwRegionWiseOutreach();
                    outreach.setRegionCd(Long.parseLong(tempArr[0]));
                    outreach.setOpening(Long.parseLong(tempArr[1]));
                    outreach.setTargets(Long.parseLong(tempArr[2]));
                    outreach.setMaturingLoans(Long.parseLong(tempArr[3]));
                    outreach.setClosing(Long.parseLong(tempArr[4]));
                    outreach.setOutreachMonth((new SimpleDateFormat("MM/dd/yyyy").parse(tempArr[5]).toInstant()));
                    outreach.setTransDate(currIns);

                    outreachList.add(outreach);
                }
                flag = false;
            }
            br.close();
            regionOutreachRepository.save(outreachList);
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    /**
     * @Added, Naveed
     * @Date, 14-06-2022
     * @Description, SCR - systemization Funds Request
     */
    public Map validateBranchFundRequestDate() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();

        String csvFile = "/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFile)));
            String line = "";
            boolean flag = true;
            String[] tempArr;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (lineCount != 0) {

                    lineCount++;
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");

                    if (tempArr.length != 4)
                        continue;

                    Map<String, Object> item = new HashMap();

                    if (!(tempArr[1] == null || tempArr[1].isEmpty() || !StringUtils.isNumeric(tempArr[1]))) {
                        if (mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(Long.parseLong(tempArr[1]), true) == null) {
                            reason = "please provide valid Branch Sequence";
                            validItem = false;
                            flag = false;
                        }
                    } else {
                        if (!(tempArr[0] == null || tempArr[0].isEmpty() || !StringUtils.isNumeric(tempArr[0]))) {
                            if (mwRegRepository.findOneByRegSeqAndCrntRecFlg(Long.parseLong(tempArr[0]), true) == null) {
                                reason = "please provide valid Region Sequence";
                                validItem = false;
                                flag = false;
                            }
                        } else {
                            reason = "please provide valid Region Sequence";
                            validItem = false;
                            flag = false;
                        }
                    }

                    if (tempArr[2] == null || tempArr[2].isEmpty() || !StringUtils.isNumeric(tempArr[2]) || Long.parseLong(tempArr[2]) <= 0) {
                        reason = "please provide valid fund Amount";
                        validItem = false;
                        flag = false;
                    }
                    if (tempArr[3] == null || tempArr[3].isEmpty()) {
                        reason = "please provide valid fund description";
                        validItem = false;
                        flag = false;
                    }

                    if (tempArr.length == 4) {
                        item.put("regNm", tempArr[0] == null ? 0 : tempArr[0]);
                        item.put("brnchNm", tempArr[1] == null ? 0 : tempArr[1]);
                        item.put("expAmt", tempArr[2] == null ? 0 : tempArr[2]);
                        item.put("expDscr", tempArr[3] == null ? "" : tempArr[3]);
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                lineCount++;
            }
            resp.put("list", list);
            resp.put("valid", flag);
        } catch (ArrayIndexOutOfBoundsException iob) {
            iob.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String saveBranchFundRequestFile() {
        Instant currIns = Instant.now();

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        List<MwReg> regList = mwRegRepository.findAllByCrntRecFlgOrderByRegSeqDesc(true);
        List<MwBrnch> branchList = mwBrnchRepository.findAllByCrntRecFlg(true);

        String csvFile = "/srv/samba/BranchFundsRequest/BRANCH_FUNDS_REQUEST.csv";
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            List<MwFundsRequest> requestLists = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 4)
                        continue;

                    MwFundsRequest request = new MwFundsRequest();
                    long seq = SequenceFinder.findNextVal(Sequences.FUND_REQ_SEQ);

                    if (tempArr[1] == null || tempArr[1].isEmpty()) {
                        request.setEntySeq(Long.parseLong(tempArr[0]));
                        request.setEntyTyp("REG");
                    } else {
                        request.setEntySeq(Long.parseLong(tempArr[1]));
                        request.setEntyTyp("BR");
                    }
                    request.setFundReqSeq(seq);
                    request.setAmt(Long.parseLong(tempArr[2]));
                    request.setRemarks(tempArr[3]);
                    request.setCrtdDt(currIns);
                    request.setCrntRecFlg(true);
                    request.setCrdtBy(user);
                    requestLists.add(request);
                }
                flag = false;
            }
            br.close();
            mwFundsRequestRepository.save(requestLists);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "file loaded";
    }

    public boolean containsKeyIgnoreCase(Map<String, Long> map, String lookupKey) {
        return map.keySet()
            .stream()
            .map(key -> key.equalsIgnoreCase(lookupKey) || key.contains(lookupKey))
            .reduce(false, Boolean::logicalOr);
    }

    /**
     * @Ended, Naveed
     * @Date, 14-06-2022
     * @Description, SCR - systemization Funds Request
     */

    public boolean recordValid(Map<String, Long> map, String temp) {
        boolean flag = true;
        if (temp != null && !temp.isEmpty()) {
            if (!containsKeyIgnoreCase(map, temp)) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * @Added, Naveed
     * @Date, 14-06-2022
     * @Description, SCR - Life Insurance Claim uploader (Loan Adjustment )
     */
    public Map validateLifeInsuranceClaimFile() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();

        String csvFile = "/srv/samba/lifeInsuranceClaim/LIFE_INSURANCE_CLAIM.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFile)));
            String line = "";
            boolean flag = true;
            String[] tempArr;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (lineCount != 0) {

                    lineCount++;
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");

                    if (tempArr.length != 12)
                        continue;

                    Map<String, Object> item = new HashMap();

                    Long clntSeq = Long.parseLong(tempArr[0]);
                    if (clntSeq > 0) {
                        String clientScript = "  SELECT CLNT_SEQ,\n" +
                            "         CLNT_FRST_NM,\n" +
                            "         CLNT_LAST_NM,\n" +
                            "         CLNT_CNIC,\n" +
                            "         LISTAGG (PRD_SEQ, '-') WITHIN GROUP (ORDER BY PRD_SEQ)\n" +
                            "             PRD_SEQ,\n" +
                            "         LISTAGG (NOM_FRST_NM, '-') WITHIN GROUP (ORDER BY NOM_FRST_NM)\n" +
                            "             NOM_FRST_NM,\n" +
                            "         LISTAGG (NOM_LAST_NM, '-') WITHIN GROUP (ORDER BY NOM_LAST_NM)\n" +
                            "             NOM_LAST_NM,\n" +
                            "         LISTAGG (NOM_CNIC, '-') WITHIN GROUP (ORDER BY NOM_CNIC)\n" +
                            "             NOM_CNIC,\n" +
                            "         LISTAGG (PRD_CMNT, '-') WITHIN GROUP (ORDER BY PRD_CMNT)\n" +
                            "             PRD_CMNT, " +
                            "         LISTAGG (PRD_NM, '-') WITHIN GROUP (ORDER BY PRD_NM)\n" +
                            "             PRD_NM,\n" +
                            "         BRNCH_NM,\n" +
                            "         DT_OF_DTH,\n" +
                            "         SUM (ADJST_AMT)\n" +
                            "             ADJST_AMT\n" +
                            "    FROM (  SELECT CLNT_SEQ,\n" +
                            "                   CLNT_FRST_NM,\n" +
                            "                   CLNT_LAST_NM,\n" +
                            "                   CLNT_CNIC,\n" +
                            "                   PRD_SEQ,\n" +
                            "                   NOM_FRST_NM,\n" +
                            "                   NOM_LAST_NM,\n" +
                            "                   NOM_CNIC,\n" +
                            "                   PRD_CMNT,\n" +
                            "                   PRD_NM,\n" +
                            "                   BRNCH_NM,\n" +
                            "                   DT_OF_DTH,\n" +
                            "                     (SUM (PPAL_AMT_DUE) + SUM (TOT_CHRG_DUE))\n" +
                            "                   - NVL (MAX (RCVRY_AMT), 0)\n" +
                            "                       ADJST_AMT\n" +
                            "              FROM (SELECT CLNT.CLNT_SEQ,\n" +
                            "                           CLNT.FRST_NM\n" +
                            "                               CLNT_FRST_NM,\n" +
                            "                           CLNT.LAST_NM\n" +
                            "                               CLNT_LAST_NM,\n" +
                            "                           CLNT.CNIC_NUM\n" +
                            "                               CLNT_CNIC,\n" +
                            "                           APP.PRD_SEQ\n" +
                            "                               PRD_SEQ,\n" +
                            "                           REL.FRST_NM\n" +
                            "                               NOM_FRST_NM,\n" +
                            "                           REL.LAST_NM\n" +
                            "                               NOM_LAST_NM,\n" +
                            "                           REL.CNIC_NUM\n" +
                            "                               NOM_CNIC,\n" +
                            "                           (SELECT PRD.PRD_CMNT\n" +
                            "                              FROM MW_PRD PRD\n" +
                            "                             WHERE     PRD.PRD_SEQ = APP.PRD_SEQ\n" +
                            "                                   AND PRD.CRNT_REC_FLG = 1)\n" +
                            "                               PRD_CMNT,\n" +
                            "                           (SELECT PRD.PRD_NM\n" +
                            "                              FROM MW_PRD PRD\n" +
                            "                             WHERE     PRD.PRD_SEQ = APP.PRD_SEQ\n" +
                            "                                   AND PRD.CRNT_REC_FLG = 1)\n" +
                            "                               PRD_NM,\n" +
                            "                           (SELECT BR.BRNCH_NM\n" +
                            "                              FROM MW_BRNCH BR\n" +
                            "                             WHERE     BR.BRNCH_SEQ = APP.BRNCH_SEQ\n" +
                            "                                   AND BR.CRNT_REC_FLG = 1)\n" +
                            "                               BRNCH_NM,\n" +
                            "                           TO_CHAR (RPT.DT_OF_DTH, 'dd-MM-yyyy')\n" +
                            "                               DT_OF_DTH,\n" +
                            "                           PPAL_AMT_DUE\n" +
                            "                               PPAL_AMT_DUE,\n" +
                            "                           TOT_CHRG_DUE\n" +
                            "                               TOT_CHRG_DUE,\n" +
                            "                           RCVRY_AMT\n" +
                            "                      FROM MW_CLNT CLNT\n" +
                            "                           JOIN MW_LOAN_APP APP\n" +
                            "                               ON     APP.CLNT_SEQ = CLNT.CLNT_SEQ\n" +
                            "                                  AND CLNT.CRNT_REC_FLG = 1\n" +
                            "                                  AND APP.LOAN_APP_STS = 703\n" +
                            "                                  AND APP.LOAN_CYCL_NUM =\n" +
                            "                                      (SELECT MAX (APP.LOAN_CYCL_NUM)\n" +
                            "                                         FROM MW_LOAN_APP AP\n" +
                            "                                        WHERE     AP.CLNT_SEQ = AP.CLNT_SEQ\n" +
                            "                                              AND AP.CRNT_REC_FLG = 1)\n" +
                            "                           LEFT OUTER JOIN MW_CLNT_REL REL\n" +
                            "                               ON     REL.LOAN_APP_SEQ = APP.LOAN_APP_SEQ\n" +
                            "                                  AND REL.REL_TYP_FLG = 1\n" +
                            "                                  AND REL.CRNT_REC_FLG = 1\n" +
                            "                           LEFT OUTER JOIN MW_DTH_RPT RPT\n" +
                            "                               ON     RPT.CLNT_SEQ = CLNT.CLNT_SEQ\n" +
                            "                                  AND RPT.CRNT_REC_FLG = 1\n" +
                            "                           JOIN MW_PYMT_SCHED_HDR PSH\n" +
                            "                               ON     APP.LOAN_APP_SEQ = PSH.LOAN_APP_SEQ\n" +
                            "                                  AND PSH.CRNT_REC_FLG = 1\n" +
                            "                           JOIN MW_PYMT_SCHED_DTL PSD\n" +
                            "                               ON     PSH.PYMT_SCHED_HDR_SEQ =\n" +
                            "                                      PSD.PYMT_SCHED_HDR_SEQ\n" +
                            "                                  AND PSD.CRNT_REC_FLG = 1\n" +
                            "                           LEFT OUTER JOIN\n" +
                            "                           (  SELECT PSD.PYMT_SCHED_HDR_SEQ,\n" +
                            "                                     SUM (RD.PYMT_AMT)     RCVRY_AMT\n" +
                            "                                FROM MW_RCVRY_DTL RD\n" +
                            "                                     JOIN MW_PYMT_SCHED_DTL PSD\n" +
                            "                                         ON     RD.PYMT_SCHED_DTL_SEQ =\n" +
                            "                                                PSD.PYMT_SCHED_DTL_SEQ\n" +
                            "                                            AND PSD.CRNT_REC_FLG = 1\n" +
                            "                               WHERE     RD.CHRG_TYP_KEY IN\n" +
                            "                                             (-1,\n" +
                            "                                              (SELECT T.TYP_SEQ\n" +
                            "                                                 FROM MW_TYPS T\n" +
                            "                                                WHERE     T.TYP_SEQ =\n" +
                            "                                                          RD.CHRG_TYP_KEY\n" +
                            "                                                      AND T.TYP_ID = '0017'\n" +
                            "                                                      AND T.CRNT_REC_FLG = 1))\n" +
                            "                                     AND RD.CRNT_REC_FLG = 1\n" +
                            "                            GROUP BY PYMT_SCHED_HDR_SEQ) RCVRY\n" +
                            "                               ON RCVRY.PYMT_SCHED_HDR_SEQ =\n" +
                            "                                  PSD.PYMT_SCHED_HDR_SEQ\n" +
                            "                     WHERE CLNT.CLNT_SEQ = :P_CLNT_SEQ AND CLNT.CRNT_REC_FLG = 1)\n" +
                            "          GROUP BY CLNT_SEQ,\n" +
                            "                   CLNT_FRST_NM,\n" +
                            "                   CLNT_LAST_NM,\n" +
                            "                   CLNT_CNIC,\n" +
                            "                   PRD_SEQ,\n" +
                            "                   NOM_FRST_NM,\n" +
                            "                   NOM_LAST_NM,\n" +
                            "                   NOM_CNIC,\n" +
                            "                   DT_OF_DTH,\n" +
                            "                   PRD_CMNT,\n" +
                            "                   PRD_NM,\n" +
                            "                   BRNCH_NM)\n" +
                            "GROUP BY CLNT_SEQ,\n" +
                            "         CLNT_FRST_NM,\n" +
                            "         CLNT_LAST_NM,\n" +
                            "         CLNT_CNIC,\n" +
                            "         DT_OF_DTH,\n" +
                            "         BRNCH_NM ";
                        Query clntQuery = em.createNativeQuery(clientScript).setParameter("P_CLNT_SEQ", clntSeq);
                        List<Object[]> clntResult = clntQuery.getResultList();

                        if (clntResult.isEmpty()) {
                            validItem = false;
                            flag = false;
                            reason = "Client not Found (Already Adjusted or Completed)";
                        }

                        for (Object[] clnt : clntResult) {

                            if (!(tempArr[1] != null && tempArr[1].trim().toUpperCase().contains(clnt[1].toString().toUpperCase()))) {

                                if (!(clnt[2] != null && tempArr[1].trim().toUpperCase().contains(clnt[2].toString().toUpperCase()))) {
                                    validItem = false;
                                    flag = false;
                                    reason = "Client Name invalid";
                                } else {
                                    if (!(tempArr[1] != null && tempArr[1].trim().equalsIgnoreCase(clnt[1].toString()))) {
                                        validItem = false;
                                        flag = false;
                                        reason = "Client Name invalid";
                                    }
                                }
                            }

                            if (!((tempArr[2] != null && clnt[3] != null) &&
                                tempArr[2].trim().equals(clnt[3].toString()))) {

                                validItem = false;
                                flag = false;
                                reason = "Client Cnic invalid";
                            }

                            if (clnt[5] != null && !clnt[5].toString().equals("22")) {

                                if (!((tempArr[4] != null && clnt[7] != null) &&
                                    tempArr[4].trim().equals(clnt[7].toString()))) {

                                    validItem = false;
                                    flag = false;
                                    reason = "Nominee Cnic invalid";
                                }

                                if (!(tempArr[3] != null && tempArr[3].trim().toUpperCase().contains(clnt[5].toString().toUpperCase()))) {

                                    if (!(clnt[6] != null && tempArr[3].trim().toUpperCase().contains(clnt[6].toString().toUpperCase()))) {
                                        validItem = false;
                                        flag = false;
                                        reason = "Nominee Name invalid";
                                    } else {
                                        if (!(tempArr[3] != null && tempArr[3].trim().equalsIgnoreCase(clnt[5].toString()))) {
                                            validItem = false;
                                            flag = false;
                                            reason = "Nominee Name invalid";
                                        }
                                    }
                                }
                            }

                            if (!(tempArr[11] == null || tempArr[11].isEmpty() || Long.parseLong(tempArr[11]) <= 0)) {
                                long amt = clnt[12] == null ? 0 : new BigDecimal(clnt[12].toString()).longValue();
                                if (Long.parseLong(tempArr[11]) != amt) {
                                    reason = Long.parseLong(tempArr[11]) - amt + " Difference between Adjustment Amount";
                                    validItem = false;
                                    flag = false;
                                }
                            } else {
                                reason = "Adjustment Amount invalid";
                                validItem = false;
                                flag = false;
                            }

                            if (!((tempArr[5] != null && (clnt[8] != null && clnt[9] != null)) &&
                                (tempArr[5].trim().equalsIgnoreCase(clnt[8].toString()) ||
                                    tempArr[5].trim().equalsIgnoreCase(clnt[9].toString())))) {
                                validItem = false;
                                flag = false;
                                reason = "Product Name invalid";
                            }

                            if (!((tempArr[6] != null && clnt[10] != null) &&
                                tempArr[6].trim().equalsIgnoreCase(clnt[10].toString()))) {

                                validItem = false;
                                flag = false;
                                reason = "Branch Name invalid";
                            }

                            if (!((tempArr[7] != null && clnt[11] != null) &&
                                tempArr[7].trim().equals(clnt[11].toString()))) {

                                validItem = false;
                                flag = false;
                                reason = "Death Date invalid";
                            }

                            if (tempArr[8] == null || tempArr[8].isEmpty()) {
                                reason = "Insurance claim number invalid";
                                validItem = false;
                                flag = false;
                            }

                            if (tempArr[9] == null || tempArr[9].isEmpty()) {
                                reason = "Insurance claim date invalid";
                                validItem = false;
                                flag = false;
                            }

                            if (tempArr[10] == null || tempArr[10].isEmpty()) {
                                reason = "Approved Date invalid";
                                validItem = false;
                                flag = false;
                            }
                        }
                    } else {
                        validItem = false;
                        flag = false;
                        reason = "Client not found";
                    }

                    if (tempArr.length == 12) {
                        item.put("clientId", tempArr[0] == null ? "" : tempArr[0]);
                        item.put("clientName", tempArr[1] == null ? "" : tempArr[1]);
                        item.put("clientCnic", tempArr[2] == null ? "" : tempArr[2]);
                        item.put("nomineeName", tempArr[3] == null ? "" : tempArr[3]);
                        item.put("nomineeCnic", tempArr[4] == null ? "" : tempArr[4]);
                        item.put("productName", tempArr[5] == null ? "" : tempArr[5]);
                        item.put("branchName", tempArr[6] == null ? "" : tempArr[6]);
                        item.put("deathDate", tempArr[7] == null ? "" : tempArr[7]);
                        item.put("insuranceNum", tempArr[8] == null ? "" : tempArr[8]);
                        item.put("insuranceClaimDate", tempArr[9] == null ? "" : tempArr[9]);
                        item.put("approveDate", tempArr[10] == null ? "" : tempArr[10]);
                        item.put("adjusAmt", tempArr[11] == null ? "" : tempArr[11]);
                    }
                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }
                lineCount++;
            }
            resp.put("list", list);
            resp.put("valid", flag);
        } catch (ArrayIndexOutOfBoundsException iob) {
            iob.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    public String loadLifeInsuranceClaimFile(String token, String user) {
        String csvFile = "/srv/samba/lifeInsuranceClaim/LIFE_INSURANCE_CLAIM.csv";

        List<EmailDto> emailList = new ArrayList<>();
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 12)
                        continue;

                    serviceClient.adjustLoan(tempArr[0], token);

                    EmailDto emailDto = new EmailDto();
                    emailDto.subject = "Loan Adjustment Approval";
                    emailDto.emailCredentialCd = "0002";
                    emailDto.receiptType = 11;
                    emailDto.dateFormat = "dd-MM-yyyy";
                    emailDto.error = 0;
                    emailDto.body = emailBody(tempArr);
                    emailDto.to = getReceipt(emailDto.receiptType, emailDto.error, tempArr[0]);

                    emailList.add(emailDto);
                    updateStatus("Loan Adjustment of Client " + tempArr[0] + ", Amount " + tempArr[11]);
                }
                flag = false;
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            updateStatus(ExceptionUtils.getStackTrace(ex).substring(0, 495));
            EmailDto emailDto = new EmailDto();
            emailDto.subject = "Loan Adjustment Approval";
            emailDto.emailCredentialCd = "0002";
            emailDto.receiptType = 11;
            emailDto.dateFormat = "dd-MM-yyyy";
            emailDto.error = 1;
            emailDto.body = ExceptionUtils.getStackTrace(ex);
            try {
                emailDto.to = getReceipt(emailDto.receiptType, emailDto.error, "0");
                emailService.email(emailDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!emailList.isEmpty()) {
            for (EmailDto email : emailList) {
                try {
                    emailService.email(email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "file loaded";
    }

    private String emailBody(String[] tempArr) {
        return "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <style>\n" +
            "        table {\n" +
            "            border-collapse: collapse;\n" +
            "            width: 85%;\n" +
            "        }\n" +
            "\n" +
            "        table,\n" +
            "        th,\n" +
            "        td {\n" +
            "            border: 1px solid #000;\n" +
            "            font-size: 0.8em;\n" +
            "        }\n" +
            "\n" +
            "        th,\n" +
            "        td {\n" +
            "            text-align: left;\n" +
            "            padding: 8px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "    <h2>Dear Branch Manager,</h2>\n" +
            "    <p>you are being informed that loan adjustment against the below Client has been entered into MWX.\n" +
            " you may verify at your own end</p>\n" +
            "\n" +
            "    <div style=\"overflow-x: auto;\">\n" +
            "        <table>\n" +
            "            <tr>\n" +
            "                <th>DATE</th>\n" +
            "                <th>CLAIM #</th>\n" +
            "                <th>BRANCH_DESC</th>\n" +
            "                <th>CUSTOMER_ID</th>\n" +
            "                <th>CUSTOMER_NAME</th>\n" +
            "                <th>CUSTOMER_CNIC</th>\n" +
            "                <th>NOMINEE</th>\n" +
            "                <th>NOMINEE CNIC</th>\n" +
            "                <th>ADJUSTMENT</th>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td> " + tempArr[9] + "</td>\n" +
            "                <td> " + tempArr[8] + "</td>\n" +
            "                <td> " + tempArr[6] + "</td>\n" +
            "                <td> " + tempArr[0] + "</td>\n" +
            "                <td> " + tempArr[1] + "</td>\n" +
            "                <td> " + tempArr[2] + "</td>\n" +
            "                <td> " + tempArr[3] + "</td>\n" +
            "                <td> " + tempArr[4] + "</td>\n" +
            "                <td> " + tempArr[11] + "</td>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "    </div>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
    }

    private InternetAddress[] getReceipt(int receipt, int errorCode, String clientSeq) throws Exception {

        String emailListsScript = "";
        List emailLists = new ArrayList<>();
        if (errorCode == 0) {
            emailListsScript = "WITH\n" +
                "    EMAIL (BRNCH, AREA, REGION)\n" +
                "    AS\n" +
                "        (SELECT (SELECT LST.EMAIL_ADDR\n" +
                "                   FROM KASHF_REPORTING.MW_EMAIL_CNTCTS_RCPNTS_LST LST\n" +
                "                  WHERE     LST.CNTCT_NO = BR.BRNCH_SEQ\n" +
                "                        AND LST.EMAIL_CNTCTS_RCPNTS_TYP = 4\n" +
                "                        AND LST.CRNT_REC_FLG = 1)\n" +
                "                    BRNCH,\n" +
                "                (SELECT LST.EMAIL_ADDR\n" +
                "                   FROM KASHF_REPORTING.MW_EMAIL_CNTCTS_RCPNTS_LST LST\n" +
                "                  WHERE     LST.CNTCT_NO = AR.AREA_SEQ\n" +
                "                        AND LST.EMAIL_CNTCTS_RCPNTS_TYP = 5\n" +
                "                        AND LST.CRNT_REC_FLG = 1)\n" +
                "                    AREA,\n" +
                "                (SELECT LST.EMAIL_ADDR\n" +
                "                   FROM KASHF_REPORTING.MW_EMAIL_CNTCTS_RCPNTS_LST LST\n" +
                "                  WHERE     LST.CNTCT_NO = REG.REG_SEQ\n" +
                "                        AND LST.EMAIL_CNTCTS_RCPNTS_TYP = 6\n" +
                "                        AND LST.CRNT_REC_FLG = 1)\n" +
                "                    REGION\n" +
                "           FROM MW_LOAN_APP  APP\n" +
                "                JOIN MW_BRNCH BR\n" +
                "                    ON BR.BRNCH_SEQ = APP.BRNCH_SEQ AND BR.CRNT_REC_FLG = 1\n" +
                "                JOIN MW_AREA AR\n" +
                "                    ON AR.AREA_SEQ = BR.AREA_SEQ AND AR.CRNT_REC_FLG = 1\n" +
                "                JOIN MW_REG REG\n" +
                "                    ON REG.REG_SEQ = AR.REG_SEQ AND REG.CRNT_REC_FLG = 1\n" +
                "          WHERE APP.CRNT_REC_FLG = 1 AND APP.CLNT_SEQ = :P_CLNT_SEQ" +
                "          GROUP BY BR.BRNCH_SEQ, AR.AREA_SEQ, REG.REG_SEQ )\n" +
                "SELECT BRNCH TEXT FROM EMAIL\n" +
                "UNION ALL\n" +
                "SELECT AREA TEXT FROM EMAIL\n" +
                "UNION ALL\n" +
                "SELECT REGION TEXT FROM EMAIL\n" +
                "UNION ALL\n" +
                "SELECT LI.EMAIL_ADDR\n" +
                "  FROM KASHF_REPORTING.MW_EMAIL_CNTCTS_RCPNTS_LST LI\n" +
                " WHERE LI.CRNT_REC_FLG = 1 AND LI.EMAIL_CNTCTS_RCPNTS_TYP = :receiptType";

            emailLists = em.createNativeQuery(emailListsScript).setParameter("receiptType", receipt).setParameter("P_CLNT_SEQ", clientSeq).getResultList();
        } else {
            emailListsScript = "SELECT LI.EMAIL_ADDR FROM KASHF_REPORTING.MW_EMAIL_CNTCTS_RCPNTS_LST LI WHERE LI.SEND_ERROR = 1 AND LI.EMAIL_CNTCTS_RCPNTS_TYP = :receiptType";
            emailLists = em.createNativeQuery(emailListsScript).setParameter("receiptType", receipt).getResultList();
        }
        InternetAddress[] address = new InternetAddress[emailLists.size()];
        for (int i = 0; i < emailLists.size(); i++) {
            address[i] = new InternetAddress(emailLists.get(i).toString());
        }
        return address;
    }

    /**
     * @Ended, Naveed
     * @Date, 14-06-2022
     * @Description, SCR - Life Insurance Claim uploader (Loan Adjustment )
     */

    public void updateStatus(String status) {
        long seq = SequenceFinder.findNextVal(Sequences.AUTO_LIST_STATUS_SEQ);
        MwAutoListStatus autoListStatus = new MwAutoListStatus();
        autoListStatus.setAutoListStatusSeq(seq);
        autoListStatus.setCrntDate(Instant.now());
        autoListStatus.setStatus(status);
        autoListStatus.setType("loan-adj");
        mwAutoStatusRepository.save(autoListStatus);
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader
     */
    public Map validateBranchCMSFundsTransferFile() throws ParseException {
        Map resp = new HashMap();
        List list = new ArrayList();

        String csvFile = "/srv/samba/BranchCmsFunds/BRANCH_WISE_CMS_FUNDS.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFile)));
            String line = "";
            boolean flag = true;
            String[] tempArr;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                if (lineCount != 0) {

                    lineCount++;
                    String reason = "";
                    boolean validItem = true;
                    tempArr = line.split(",");

                    if (tempArr.length != 4) {
                        continue;
                    }

                    Map<String, Object> item = new HashMap();

                    if (isStringOnlyDigit(tempArr[0])) {
                        MwBrnch mwbrnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(Long.parseLong(tempArr[0].trim()), true);
                        if (mwbrnch == null) {
                            reason = "please provide valid Branch seq";
                            validItem = false;
                            flag = false;
                        }
//                       else{
//                           List<MwExp> mwExpList = mwExpRepository.findAllByBrnchSeqAndExpnsTypSeqAndPymtTypSeqAndCrntRecFlg(mwbrnch.getBrnchSeq());
//                           if(!mwExpList.isEmpty()){
//                               reason = "funds already posted";
//                               validItem = false;
//                               flag =  false;
//                           }
//                       }
                    } else {
                        reason = "please provide valid Branch seq";
                        validItem = false;
                        flag = false;
                    }

                    if (isStringOnlyDigit(tempArr[1])) {
                        if (Long.parseLong(tempArr[1]) <= 0) {
                            reason = "please provide valid Amount";
                            validItem = false;
                            flag = false;
                        }
                    } else {
                        reason = "please provide valid Amount";
                        validItem = false;
                        flag = false;
                    }

                    if (!isStringOnlyDigit(tempArr[2])) {
                        reason = "please provide valid instrument number";
                        validItem = false;
                        flag = false;
                    }

                    if (tempArr[3] == null || tempArr[3].isEmpty()) {
                        reason = "please provide valid Narration";
                        validItem = false;
                        flag = false;
                    }

                    item.put("branchSeq", tempArr[0] == null ? 0 : tempArr[0]);
                    item.put("amount", tempArr[1] == null ? 0 : tempArr[1]);
                    item.put("instNum", tempArr[2] == null ? 0 : tempArr[2]);
                    item.put("narration", tempArr[3] == null ? 0 : tempArr[3]);

                    item.put("valid", validItem);
                    item.put("reason", reason);
                    list.add(item);
                }

                lineCount++;
            }

            resp.put("list", list);
            resp.put("valid", flag);
        } catch (ArrayIndexOutOfBoundsException iob) {
            iob.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            resp.put("valid", false);
        }
        return resp;
    }

    /**
     * @Added, Naveed
     * @Date, 16-06-2022
     * @Description, SCR - Branch Wise CMS Funds Data Loader
     */
    public List<MwExp> loadBranchCMSFundsTransferFile(String token, String user) {
        Instant currIns = Instant.now();

        String csvFile = "/srv/samba/BranchCmsFunds/BRANCH_WISE_CMS_FUNDS.csv";
        List<MwExp> mwExpList = new ArrayList<>();

        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            boolean flag = true;
            String[] tempArr;

            while ((line = br.readLine()) != null) {
                if (flag == false) {
                    tempArr = line.split(",");
                    if (tempArr.length != 4) {
                        continue;
                    }
                    long seq = SequenceFinder.findNextVal(Sequences.EXP_SEQ);
                    MwExp exp = new MwExp();
                    exp.setExpSeq(seq);
                    exp.setBrnchSeq(Long.parseLong(tempArr[0].trim()));
                    exp.setExpnsStsKey(200L);
                    exp.setExpnsId(String.format("%04d", seq));
                    exp.setExpnsDscr(tempArr[3]);
                    exp.setInstrNum(tempArr[2]);
                    exp.setExpnsAmt(Double.parseDouble(tempArr[1].trim()));
                    exp.setExpnsTypSeq(mwTypsRepository.findOneByTypIdAndCrntRecFlgAndBrnchSeqAndTypCtgryKey("0420", true, Long.parseLong(tempArr[0].trim()), 2L).getTypSeq());
                    exp.setPymtTypSeq(mwTypsRepository.findOneByTypIdAndCrntRecFlgAndBrnchSeqAndTypCtgryKey("0008", true, Long.parseLong(tempArr[0].trim()), 6L).getTypSeq());
                    exp.setPymtRctFlg(1L);
                    exp.setCrtdBy(user);
                    exp.setCrtdDt(currIns);
                    exp.setLastUpdBy(user);
                    exp.setLastUpdDt(currIns);
                    exp.setDelFlg(false);
                    exp.setEffStartDt(currIns);
                    exp.setCrntRecFlg(true);

                    mwExpList.add(exp);
                }
                flag = false;
            }
            br.close();
            mwExpRepository.save(mwExpList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return mwExpList;
    }

    public List<Map<String, String>> postBranchCMSFundsTransferFile(String token, List<MwExp> expList) {

        List<Map<String, String>> responseList = new ArrayList<>();

        expList.forEach(exp -> {
            Map<String, String> response = new HashMap<String, String>();
            log.info("POSTING BRANCH CMS FUNDS, EXP_SEQ " + exp.getExpSeq());
            response = serviceClient.postExpense(exp.getExpSeq(), token).getBody();
            log.info("POSTED BRANCH CMS FUNDS, EXP_SEQ " + exp.getExpSeq(), response);
            responseList.add(response);
        });
        return responseList;
    }
}
