package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwTyps.
 */
@Entity
@Table(name = "Mw_Typs")
//@IdClass ( MwTypsId.class )
public class MwTyps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "typ_SEQ")
    private Long typSeq;

    @Column(name = "typ_id")
    private String typId;

    @Column(name = "typ_str")
    private String typStr;

    @Column(name = "GL_Acct_num")
    private String glAcctNum;

    @Column(name = "typ_ctgry_Key")
    private Long typCtgryKey;

    @Column(name = "typ_sts_Key")
    private Long typStsKey;

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

    @Column(name = "perd_flg")
    private Long perdFlg;

    @Column(name = "dfrd_acct_num")
    private String dfrdAcctNum;

    @Column(name = "BRNCH_SEQ")
    private Long brnchSeq;

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public Long getTypSeq() {
        return typSeq;
    }

    public void setTypSeq(Long typSeq) {
        this.typSeq = typSeq;
    }

    public String getTypId() {
        return typId;
    }

    public void setTypId(String typId) {
        this.typId = typId;
    }

    public String getTypStr() {
        return typStr;
    }

    public void setTypStr(String typStr) {
        this.typStr = typStr;
    }

    public String getGlAcctNum() {
        return glAcctNum;
    }

    public void setGlAcctNum(String glAcctNum) {
        this.glAcctNum = glAcctNum;
    }

    public Long getTypCtgryKey() {
        return typCtgryKey;
    }

    public void setTypCtgryKey(Long typCtgryKey) {
        this.typCtgryKey = typCtgryKey;
    }

    public Long getTypStsKey() {
        return typStsKey;
    }

    public void setTypStsKey(Long typStsKey) {
        this.typStsKey = typStsKey;
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

    public Long getPerdFlg() {
        return perdFlg;
    }

    public void setPerdFlg(Long perdFlg) {
        this.perdFlg = perdFlg;
    }

    public String getDfrdAcctNum() {
        return dfrdAcctNum;
    }

    public void setDfrdAcctNum(String dfrdAcctNum) {
        this.dfrdAcctNum = dfrdAcctNum;
    }

}
