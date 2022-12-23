package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "Mw_Brnch_Bdgt")

public class MwBrnchBdgt {

    @Id
    @Column(name = "brnch_bdgt_SEQ")
    private Long brnchBdgtSeq;

    @Column(name = "GL_Acct_num")
    private String gLAcctNum;

    @Column(name = "bdgt_Amt")
    private Double bdgtAmt;

    @Column(name = "bdgt_perd")
    private Long pred;

    @Column(name = "brnch_SEQ")
    private Long brnchSeq;

    @Column(name = "bdgt_typ_Key")
    private Long bdgtTypKey;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "bdgt_ctgry_Key")
    private Long bdgtCtgryKey;

    @Column(name = "bdgt_yr")
    private Long bdgtYr;


    public Long getPred() {
        return pred;
    }

    public void setPred(Long pred) {
        this.pred = pred;
    }

    public Long getBrnchBdgtSeq() {
        return brnchBdgtSeq;
    }

    public void setBrnchBdgtSeq(Long brnchBdgtSeq) {
        this.brnchBdgtSeq = brnchBdgtSeq;
    }

    public String getgLAcctNum() {
        return gLAcctNum;
    }

    public void setgLAcctNum(String gLAcctNum) {
        this.gLAcctNum = gLAcctNum;
    }

    public Double getBdgtAmt() {
        return bdgtAmt;
    }

    public void setBdgtAmt(Double bdgtAmt) {
        this.bdgtAmt = bdgtAmt;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getBdgtTypKey() {
        return bdgtTypKey;
    }

    public void setBdgtTypKey(Long bdgtTypKey) {
        this.bdgtTypKey = bdgtTypKey;
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

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Long getBdgtCtgryKey() {
        return bdgtCtgryKey;
    }

    public void setBdgtCtgryKey(Long bdgtCtgryKey) {
        this.bdgtCtgryKey = bdgtCtgryKey;
    }

    public Long getBdgtYr() {
        return bdgtYr;
    }

    public void setBdgtYr(Long bdgtYr) {
        this.bdgtYr = bdgtYr;
    }

}
