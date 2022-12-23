package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mw_sch_atnd")
//@IdClass(MwSchAtndId.class)

public class MwSchAtnd {

    @Id
    @Column(name = "sch_atnd_seq")
    private Long schAtndSeq;

    @Column(name = "sch_aprsl_seq")
    private Long schAprslSeq;

    @Column(name = "tot_male_tchrs")
    private Long totMaleTchrs;

    @Column(name = "tot_fem_tchrs")
    private Long totFemTchrs;

    @Column(name = "last_yr_drop")
    private Long lastYrDrop;


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

    //	@Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    public Long getSchAtndSeq() {
        return schAtndSeq;
    }

    public void setSchAtndSeq(Long schAtndSeq) {
        this.schAtndSeq = schAtndSeq;
    }

    public Long getSchAprslSeq() {
        return schAprslSeq;
    }

    public void setSchAprslSeq(Long schAprslSeq) {
        this.schAprslSeq = schAprslSeq;
    }

    public Long getTotMaleTchrs() {
        return totMaleTchrs;
    }

    public void setTotMaleTchrs(Long totMaleTchrs) {
        this.totMaleTchrs = totMaleTchrs;
    }

    public Long getTotFemTchrs() {
        return totFemTchrs;
    }

    public void setTotFemTchrs(Long totFemTchrs) {
        this.totFemTchrs = totFemTchrs;
    }

    public Long getLastYrDrop() {
        return lastYrDrop;
    }

    public void setLastYrDrop(Long lastYrDrop) {
        this.lastYrDrop = lastYrDrop;
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
