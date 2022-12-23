package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_RS_LOAN_RULE")
public class MwRsLoanRule {
    @Id
    @Column(name = "RULE_SEQ")
    private Long ruleSeq;

    @Column(name = "RULE_DESC")
    private String ruleDesc;

    @Column(name = "PRINCIPAL_AMOUNT")
    private Long principalAmount;

    @Column(name = "SERVICE_CHARGES")
    private Long serviceCharges;

    @Column(name = "SC_CHARGE_MORE")
    private Long scChargeMore;

    @Column(name = "SC_PER_INST")
    private Long scPerInst;

    @Column(name = "SC_NEW_IRR_RATE")
    private Long scNewIrrRate;

    @Column(name = "INSURANCE_CHARGES")
    private Long insuranceCharges;

    @Column(name = "INS_CHARGE_MORE")
    private Long insChargeMore;

    @Column(name = "INS_PER_INST")
    private Long insPerInst;

    @Column(name = "INS_NEW_AMOUNT")
    private Long insNewAmount;

    @Column(name = "JUMP_MONTHS")
    private Long jumpMonths;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "LOAN_RESCHD_CATEGORY")
    private Long loanReschdCategory;

    @Column(name = "LOAN_RECHD_TYPE")
    private Long loanRechdType;

    public Long getRuleSeq() {
        return this.ruleSeq;
    }

    public void setRuleSeq(Long ruleSeq) {
        this.ruleSeq = ruleSeq;
    }

    public String getRuleDesc() {
        return this.ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Long getPrincipalAmount() {
        return this.principalAmount;
    }

    public void setPrincipalAmount(Long principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Long getServiceCharges() {
        return this.serviceCharges;
    }

    public void setServiceCharges(Long serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Long getScChargeMore() {
        return this.scChargeMore;
    }

    public void setScChargeMore(Long scChargeMore) {
        this.scChargeMore = scChargeMore;
    }

    public Long getScPerInst() {
        return this.scPerInst;
    }

    public void setScPerInst(Long scPerInst) {
        this.scPerInst = scPerInst;
    }

    public Long getScNewIrrRate() {
        return this.scNewIrrRate;
    }

    public void setScNewIrrRate(Long scNewIrrRate) {
        this.scNewIrrRate = scNewIrrRate;
    }

    public Long getInsuranceCharges() {
        return this.insuranceCharges;
    }

    public void setInsuranceCharges(Long insuranceCharges) {
        this.insuranceCharges = insuranceCharges;
    }

    public Long getInsChargeMore() {
        return this.insChargeMore;
    }

    public void setInsChargeMore(Long insChargeMore) {
        this.insChargeMore = insChargeMore;
    }

    public Long getInsPerInst() {
        return this.insPerInst;
    }

    public void setInsPerInst(Long insPerInst) {
        this.insPerInst = insPerInst;
    }

    public Long getInsNewAmount() {
        return this.insNewAmount;
    }

    public void setInsNewAmount(Long insNewAmount) {
        this.insNewAmount = insNewAmount;
    }

    public Long getJumpMonths() {
        return this.jumpMonths;
    }

    public void setJumpMonths(Long jumpDays) {
        this.jumpMonths = jumpDays;
    }

    public String getCrtdBy() {
        return this.crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return this.crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return this.lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return this.lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getDelFlg() {
        return this.delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Boolean getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getLoanReschdCategory() {
        return loanReschdCategory;
    }

    public void setLoanReschdCategory(Long loanReschdCategory) {
        this.loanReschdCategory = loanReschdCategory;
    }

    public Long getLoanRechdType() {
        return loanRechdType;
    }

    public void setLoanRechdType(Long loanRechdType) {
        this.loanRechdType = loanRechdType;
    }
}
