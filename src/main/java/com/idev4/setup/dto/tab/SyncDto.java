package com.idev4.setup.dto.tab;

import com.idev4.setup.domain.*;

import java.io.Serializable;
import java.util.List;

public class SyncDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public List<MwAnswr> mw_answr;

    public List<MwArea> mw_area;

    public List<MwReg> mw_reg;

    public List<MwBizActy> mw_biz_acty;

    public List<MwBizSect> mw_biz_sect;

    public List<MwBrnch> mw_brnch;

    public List<MwBrnchLocationRel> mw_brnch_location_rel;

    public List<MwBrnchPrdRel> mw_brnch_prd_rel;

    public List<MwPort> mw_port;

    public List<MwPortLocationRel> mw_port_location_rel;

    public List<MwCityUcRel> mw_city_uc_rel;

    public List<MwCity> mw_city;

    public List<MwCmnty> mw_cmnty;

    public List<MwCntry> mw_cntry;

    public List<MwSt> mw_st;

    public List<MwDist> mw_dist;

    public List<MwThsl> mw_thsl;

    public List<MwUc> mw_uc;

    public List<MwDoc> mw_doc;

    public List<MwForm> mw_form;

    public List<MwHlthInsrPlan> mw_hlth_insr_plan;

    public List<MwPrdGrp> mw_prd_grp;

    public List<MwPrd> mw_prd;

    public List<MwPrdAdvRul> mw_prd_adv_rul;

    public List<MwPrdBizSectRel> mw_prd_biz_sect_rel;

    public List<MwPrdChrg> mw_prd_chrg;

    public List<MwPrdChrgSlb> mw_prd_chrg_slb;

    public List<MwPrdDocRel> mw_prd_doc_rel;

    public List<MwPrdFormRel> mw_prd_form_rel;

    public List<MwPrdLoanTrm> mw_prd_loan_trm;

    public List<MwPrdPpalLmt> mw_prd_ppal_lmts;

    public List<MwQst> mw_qst;

    public List<MwQstnr> mw_qstnr;

    public List<MwRefCdVal> mw_ref_cd_val;

    public List<MwRefCdGrp> mw_ref_cd_grp;

    public List<MwRul> mw_rul;

    public List<MwTyps> mw_typs;

    public List<MwTblIndxDto> mw_tbl_indx;

    public List<MwPortEmpRel> mw_port_emp_rel;

    public List<MwEmp> mw_emp;

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
