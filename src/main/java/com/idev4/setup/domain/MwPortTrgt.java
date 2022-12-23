package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_PORT_TRGT")
//@IdClass ( MwPortTrgtId.class )
public class MwPortTrgt {

    @Id
    @Column(name = "PORT_TRGT_SEQ")
    private Long portTrgtSeq;

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "BRNCH_TRGTS_SEQ")
    private Long brnchTrgtsSeq;

    @Column(name = "PORT_SEQ")
    private Long portSeq;

    @Column(name = "TRGT_CLNTS")
    private Long trgtClnts;

    @Column(name = "TRGT_AMT")
    private Double trgtAmt;

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

    @Column(name = "EFF_END_DT")
    private Instant effEndDt;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    public Long getPortTrgtSeq() {
        return portTrgtSeq;
    }

    public void setPortTrgtSeq(Long portTrgtSeq) {
        this.portTrgtSeq = portTrgtSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBrnchTrgtsSeq() {
        return brnchTrgtsSeq;
    }

    public void setBrnchTrgtsSeq(Long brnchTrgtsSeq) {
        this.brnchTrgtsSeq = brnchTrgtsSeq;
    }

    public Long getPortSeq() {
        return portSeq;
    }

    public void setPortSeq(Long portSeq) {
        this.portSeq = portSeq;
    }

    public Long getTrgtClnts() {
        return trgtClnts;
    }

    public void setTrgtClnts(Long trgtClnts) {
        this.trgtClnts = trgtClnts;
    }

    public Double getTrgtAmt() {
        return trgtAmt;
    }

    public void setTrgtAmt(Double trgtAmt) {
        this.trgtAmt = trgtAmt;
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

}
