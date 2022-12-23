package com.idev4.setup.dto;

import java.time.Instant;

public class HlthInsrPlanDto {

    private Long hlthInsrPlanSeq;

    private String planId;

    private String planNm;

    private Long planStsKey;

    private Long anlPremAmt;

    private Long maxPlcyAmt;

    private String crtdBy;

    private Instant crtdDt;

    private String lastUpdBy;

    private Instant lastUpdDt;

    private Boolean delFlg;

    private Instant effStartDt;

    private Instant effEndDt;

    private Boolean crntRecFlg;

    private String dfrdAcctNum;

    private String glAcctNum;

    //Added by Areeba - 15-9-2022
    private String plnDscr;

    private Boolean hlthCardFlg;

    private String bddtAcctNum;

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
