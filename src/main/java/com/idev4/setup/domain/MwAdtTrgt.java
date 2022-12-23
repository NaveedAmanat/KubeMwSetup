package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "MW_adt_trgt")
//@IdClass ( MwAdtTrgtId.class )
public class MwAdtTrgt {

    @Id
    @Column(name = "ADT_TRGT_SEQ")
    private Long adtTrgtSeq;

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    @Column(name = "TRGT")
    private Long trgt;

    @Column(name = "TRGT_YR")
    private Long trgtYr;

    @Column(name = "CRTD_BY")
    private String crtdBy;

    @Column(name = "CRTD_DT")
    private Instant crtdDt;

    @Column(name = "LAST_UPD_BY")
    private String lastUpdBy;

    @Column(name = "LAST_UPD_DT")
    private Instant lastUpdDt;

    @Column(name = "EFF_END_DT")
    private Instant effEndDt;

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "CRNT_REC_FLG")
    private Boolean crntRecFlg;

    public Long getAdtTrgtSeq() {
        return adtTrgtSeq;
    }

    public void setAdtTrgtSeq(Long adtTrgtSeq) {
        this.adtTrgtSeq = adtTrgtSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getTrgt() {
        return trgt;
    }

    public void setTrgt(Long trgt) {
        this.trgt = trgt;
    }

    public Long getTrgtYr() {
        return trgtYr;
    }

    public void setTrgtYr(Long trgtYr) {
        this.trgtYr = trgtYr;
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

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

}
