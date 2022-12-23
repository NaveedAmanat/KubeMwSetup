package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_clnt_hlth_insr_clm")
//@IdClass ( MwClntHlthInsrClmId.class )
public class MwClntHlthInsrClm {

    @Id
    @Column(name = "clnt_hlth_clm_SEQ")
    private Long clntHlthClmSeq;

    @Column(name = "brnch_SEQ")
    private Long brnchSeq;

    @Column(name = "clnt_SEQ")
    private Long clntSeq;

    @Column(name = "clm_Amt")
    private Double clmAmt;

    @Column(name = "Transaction_id")
    private String transactionId;

    @Column(name = "clm_sts_Key")
    private Long clmStsKey;

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

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "CLM_TYP")
    private Long clmTyp;

    @Column(name = "POST_FLG")
    private Boolean postFlg;

    @Column(name = "CARD_NUM")
    private String cardNum;

    // Added by Zohaib Asim - Dated 16-12-2020
    // New Columns added in table for Health Claim Type
    @Column(name = "HLTH_CLM_TYP")
    private Long hlthClmTyp;

    public Long getHlthClmTyp() {
        return hlthClmTyp;
    }

    public void setHlthClmTyp(Long hlthClmTyp) {
        this.hlthClmTyp = hlthClmTyp;
    }
    // End by Zohaib Asim

    public Boolean getPostFlg() {
        return postFlg;
    }

    public void setPostFlg(Boolean postFlg) {
        this.postFlg = postFlg;
    }

    public Long getClmTyp() {
        return clmTyp;
    }

    public void setClmTyp(Long clmTyp) {
        this.clmTyp = clmTyp;
    }

    public Long getClntHlthClmSeq() {
        return clntHlthClmSeq;
    }

    public void setClntHlthClmSeq(Long clntHlthClmSeq) {
        this.clntHlthClmSeq = clntHlthClmSeq;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getClntSeq() {
        return clntSeq;
    }

    public void setClntSeq(Long clntSeq) {
        this.clntSeq = clntSeq;
    }

    public Double getClmAmt() {
        return clmAmt;
    }

    public void setClmAmt(Double clmAmt) {
        this.clmAmt = clmAmt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getClmStsKey() {
        return clmStsKey;
    }

    public void setClmStsKey(Long clmStsKey) {
        this.clmStsKey = clmStsKey;
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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
