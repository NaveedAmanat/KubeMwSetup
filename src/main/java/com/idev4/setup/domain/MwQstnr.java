package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_qstnr")
//@IdClass(MwQstnrId.class)
public class MwQstnr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "qstnr_seq")
    private long qstnrSeq;

    @Column(name = "qstnr_nm")
    private String qstnrNm;

    @Column(name = "qstnr_id")
    private String qstnrId;

    @Column(name = "qstnr_sts_key")
    private long qstnrStsKey;

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

    public long getQstnrSeq() {
        return qstnrSeq;
    }

    public void setQstnrSeq(long qstnrSeq) {
        this.qstnrSeq = qstnrSeq;
    }

    public String getQstnrNm() {
        return qstnrNm;
    }

    public void setQstnrNm(String qstnrNm) {
        this.qstnrNm = qstnrNm;
    }

    public String getQstnrId() {
        return qstnrId;
    }

    public void setQstnrId(String qstnrId) {
        this.qstnrId = qstnrId;
    }

    public long getQstnrStsKey() {
        return qstnrStsKey;
    }

    public void setQstnrStsKey(long qstnrStsKey) {
        this.qstnrStsKey = qstnrStsKey;
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
}
