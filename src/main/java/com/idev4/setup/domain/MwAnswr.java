package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_answr")
//@IdClass(MwAnswrId.class)
public class MwAnswr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "answr_seq")
    private long answrSeq;

    @Column(name = "answr_id")
    private String answrId;

    @Column(name = "answr_str")
    private String answrStr;

    @Column(name = "answr_sts_key")
    private long answrStsKey;

    @Column(name = "qst_seq")
    private long qstSeq;

    @Column(name = "answr_score")
    private long answrScore;


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

    public long getAnswrSeq() {
        return answrSeq;
    }

    public void setAnswrSeq(long answrSeq) {
        this.answrSeq = answrSeq;
    }

    public String getAnswrId() {
        return answrId;
    }

    public void setAnswrId(String answrId) {
        this.answrId = answrId;
    }

    public String getAnswrStr() {
        return answrStr;
    }

    public void setAnswrStr(String answrStr) {
        this.answrStr = answrStr;
    }

    public long getAnswrStsKey() {
        return answrStsKey;
    }

    public void setAnswrStsKey(long answrStsKey) {
        this.answrStsKey = answrStsKey;
    }

    public long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public long getAnswrScore() {
        return answrScore;
    }

    public void setAnswrScore(long answrScore) {
        this.answrScore = answrScore;
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
