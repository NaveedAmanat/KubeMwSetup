package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_qst")
//@IdClass(MwQstId.class)
public class MwQst implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "qst_seq")
    private long qstSeq;

    @Column(name = "qst_id")
    private String qstId;

    @Column(name = "qst_str")
    private String qstStr;

    @Column(name = "qst_sort_ordr")
    private long qstSortOrdr;

    @Column(name = "qst_typ_key")
    private long qstTypKey;

    @Column(name = "qst_sts_key")
    private long qstStsKey;

    @Column(name = "qst_ctgry_key")
    private long qstCtgryKey;

    @Column(name = "qstnr_seq")
    private long qstnrSeq;

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

    public long getQstSortOrdr() {
        return qstSortOrdr;
    }

    public void setQstSortOrdr(long qstSortOrdr) {
        this.qstSortOrdr = qstSortOrdr;
    }

    public long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public String getQstId() {
        return qstId;
    }

    public void setQstId(String qstId) {
        this.qstId = qstId;
    }

    public String getQstStr() {
        return qstStr;
    }

    public void setQstStr(String qstStr) {
        this.qstStr = qstStr;
    }

    public long getQstTypKey() {
        return qstTypKey;
    }

    public void setQstTypKey(long qstTypKey) {
        this.qstTypKey = qstTypKey;
    }

    public long getQstStsKey() {
        return qstStsKey;
    }

    public void setQstStsKey(long qstStsKey) {
        this.qstStsKey = qstStsKey;
    }

    public long getQstCtgryKey() {
        return qstCtgryKey;
    }

    public void setQstCtgryKey(long qstCtgryKey) {
        this.qstCtgryKey = qstCtgryKey;
    }

    public long getQstnrSeq() {
        return qstnrSeq;
    }

    public void setQstnrSeq(long qstnrSeq) {
        this.qstnrSeq = qstnrSeq;
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
