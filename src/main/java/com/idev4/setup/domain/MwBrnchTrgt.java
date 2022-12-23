package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_brnch_trgt")
//@IdClass ( MwBrnchTrgtId.class )
public class MwBrnchTrgt {

    @Id
    @Column(name = "brnch_Targets_SEQ")
    private Long brnchTargetsSeq;

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "trgt_yr")
    private Long trgtYr;

    @Column(name = "trgt_perd")
    private Long trgtPerd;

    @Column(name = "trgt_Clients")
    private Long trgtClients;

    @Column(name = "trgt_Amt")
    private Double trgtAmt;

    @Column(name = "brnch_SEQ")
    private Long brnchSeq;

    @Column(name = "prd_SEQ")
    private Long prdSeq;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "del_flg")
    private boolean delFlg;

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBrnchTargetsSeq() {
        return brnchTargetsSeq;
    }

    public void setBrnchTargetsSeq(Long brnchTargetsSeq) {
        this.brnchTargetsSeq = brnchTargetsSeq;
    }

    public Long getTrgtYr() {
        return trgtYr;
    }

    public void setTrgtYr(Long trgtYr) {
        this.trgtYr = trgtYr;
    }

    public Long getTrgtPerd() {
        return trgtPerd;
    }

    public void setTrgtPerd(Long trgtPerd) {
        this.trgtPerd = trgtPerd;
    }

    public Long getTrgtClients() {
        return trgtClients;
    }

    public void setTrgtClients(Long trgtClients) {
        this.trgtClients = trgtClients;
    }

    public Double getTrgtAmt() {
        return trgtAmt;
    }

    public void setTrgtAmt(Double trgtAmt) {
        this.trgtAmt = trgtAmt;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
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

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
