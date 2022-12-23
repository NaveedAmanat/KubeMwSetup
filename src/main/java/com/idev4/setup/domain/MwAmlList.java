package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwAddr.
 */
@Entity
@Table(name = "MW_AML_LIST")
// @IdClass ( MwAmlListId.class )
public class MwAmlList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AML_LIST_SEQ")
    private Long amlListSeq;

    // @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "AML_DATAID")
    private String amlDataId;

    @Column(name = "FRST_NM")
    private String frstNm;

    @Column(name = "SCND_NM")
    private String scndNm;

    @Column(name = "THRD_NM")
    private String thrdNm;

    @Column(name = "CMNTS")
    private String cmnts;

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

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "CNTRY")
    private String cntry;

    public Long getAmlListSeq() {
        return amlListSeq;
    }

    public void setAmlListSeq(Long amlListSeq) {
        this.amlListSeq = amlListSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public String getAmlDataId() {
        return amlDataId;
    }

    public void setAmlDataId(String amlDataId) {
        this.amlDataId = amlDataId;
    }

    public String getFrstNm() {
        return frstNm;
    }

    public void setFrstNm(String frstNm) {
        this.frstNm = frstNm;
    }

    public String getScndNm() {
        return scndNm;
    }

    public void setScndNm(String scndNm) {
        this.scndNm = scndNm;
    }

    public String getThrdNm() {
        return thrdNm;
    }

    public void setThrdNm(String thrdNm) {
        this.thrdNm = thrdNm;
    }

    public String getCmnts() {
        return cmnts;
    }

    public void setCmnts(String cmnts) {
        this.cmnts = cmnts;
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

    public String getCntry() {
        return cntry;
    }

    public void setCntry(String cntry) {
        this.cntry = cntry;
    }

}
