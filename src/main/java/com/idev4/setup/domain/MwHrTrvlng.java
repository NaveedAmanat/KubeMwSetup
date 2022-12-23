package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_HR_TRVLNG")
public class MwHrTrvlng {
    @Id
    @Column(name = "HR_TRVLNG_SEQ")
    private Long hrTrvlngSeq;

    @Column(name = "REF_CD_TRVLNG_ROL")
    private Long refCdTrvlngRol;

    @Column(name = "REF_CD_CALC_TYP")
    private Long refCdCalcTyp;

    @Column(name = "REF_CD_PORT_TYP_SEQ")
    private Long refCdPortTypSeq;

    @Column(name = "FROM_DT")
    private java.util.Date fromDt;

    @Column(name = "TO_DT")
    private java.util.Date toDt;

    @Column(name = "AMT")
    private Long amt;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    public Long getHrTrvlngSeq() {
        return this.hrTrvlngSeq;
    }

    public void setHrTrvlngSeq(Long hrTrvlngSeq) {
        this.hrTrvlngSeq = hrTrvlngSeq;
    }

    public Long getRefCdTrvlngRol() {
        return refCdTrvlngRol;
    }

    public void setRefCdTrvlngRol(Long refCdTrvlngRol) {
        this.refCdTrvlngRol = refCdTrvlngRol;
    }

    public Long getRefCdCalcTyp() {
        return this.refCdCalcTyp;
    }

    public void setRefCdCalcTyp(Long refCdCalcTyp) {
        this.refCdCalcTyp = refCdCalcTyp;
    }

    public Long getRefCdPortTypSeq() {
        return this.refCdPortTypSeq;
    }

    public void setRefCdPortTypSeq(Long refCdPortTypSeq) {
        this.refCdPortTypSeq = refCdPortTypSeq;
    }

    public java.util.Date getFromDt() {
        return this.fromDt;
    }

    public void setFromDt(java.util.Date fromDt) {
        this.fromDt = fromDt;
    }

    public java.util.Date getToDt() {
        return this.toDt;
    }

    public void setToDt(java.util.Date toDt) {
        this.toDt = toDt;
    }

    public Long getAmt() {
        return this.amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public Boolean getCrntRecFlg() {
        return this.crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
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
}
