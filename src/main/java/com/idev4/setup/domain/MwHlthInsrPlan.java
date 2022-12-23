package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "Mw_Hlth_Insr_Plan")
// @IdClass(MwHlthInsrPlanId.class)
public class MwHlthInsrPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hlth_insr_plan_SEQ")
    private Long hlthInsrPlanSeq;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "plan_nm")
    private String planNm;

    @Column(name = "plan_sts_Key")
    private Long planStsKey;

    @Column(name = "anl_prem_Amt")
    private Long anlPremAmt;

    @Column(name = "Max_plcy_Amt")
    private Long maxPlcyAmt;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "gl_acct_num")
    private String glAcctNum;

    @Column(name = "dfrd_acct_num")
    private String dfrdAcctNum;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    //Added by Areeba - 15-9-2022
    @Column(name = "pln_dscr")
    private String plnDscr;

    @Column(name = "hlth_card_flg")
    private Boolean hlthCardFlg;

    @Column(name = "bddt_acct_num")
    private String bddtAcctNum;

    @Column(name = "mnth_flg")
    private Boolean mnthFlg;
    //Ended by Areeba

    public String getGlAcctNum() {
        return glAcctNum;
    }

    public void setGlAcctNum(String glAcctNum) {
        this.glAcctNum = glAcctNum;
    }

    public String getDfrdAcctNum() {
        return dfrdAcctNum;
    }

    public void setDfrdAcctNum(String dfrdAcctNum) {
        this.dfrdAcctNum = dfrdAcctNum;
    }

    public Long getHlthInsrPlanSeq() {
        return hlthInsrPlanSeq;
    }

    public void setHlthInsrPlanSeq(Long hlthInsrPlanSeq) {
        this.hlthInsrPlanSeq = hlthInsrPlanSeq;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanNm() {
        return planNm;
    }

    public void setPlanNm(String planNm) {
        this.planNm = planNm;
    }

    public Long getPlanStsKey() {
        return planStsKey;
    }

    public void setPlanStsKey(Long planStsKey) {
        this.planStsKey = planStsKey;
    }

    public Long getAnlPremAmt() {
        return anlPremAmt;
    }

    public void setAnlPremAmt(Long anlPremAmt) {
        this.anlPremAmt = anlPremAmt;
    }

    public Long getMaxPlcyAmt() {
        return maxPlcyAmt;
    }

    public void setMaxPlcyAmt(Long maxPlcyAmt) {
        this.maxPlcyAmt = maxPlcyAmt;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public String getPlnDscr() {
        return plnDscr;
    }

    public void setPlnDscr(String plnDscr) {
        this.plnDscr = plnDscr;
    }

    public Boolean getHlthCardFlg() {
        return hlthCardFlg;
    }

    public void setHlthCardFlg(Boolean hlthCardFlg) {
        this.hlthCardFlg = hlthCardFlg;
    }

    public String getBddtAcctNum() {
        return bddtAcctNum;
    }

    public void setBddtAcctNum(String bddtAcctNum) {
        this.bddtAcctNum = bddtAcctNum;
    }

    public Boolean getMnthFlg() {
        return mnthFlg;
    }

    public void setMnthFlg(Boolean mnthFlg) {
        this.mnthFlg = mnthFlg;
    }
}
