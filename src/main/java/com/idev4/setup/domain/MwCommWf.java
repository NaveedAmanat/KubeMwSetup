package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCommWf.
 */
@Entity
@Table(name = "mw_comm_wf")
//@IdClass(MwCommWfId.class)
public class MwCommWf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comm_wf_seq")
    private Long commWfSeq;

    @Column(name = "comm_wf_id")
    private String commWfId;

    @Column(name = "comm_wf_nm")
    private String commWfNm;

    @Column(name = "comm_wf_rul_str")
    private String commWfRulStr;

    @Column(name = "comm_wf_cmnt")
    private String commWfCmnt;

    @Column(name = "comm_wf_sts_key")
    private Long commWfStsKey;

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

    @Column(name = "mw_func_key")
    private Long mwFuncKey;

    public Long getCommWfSeq() {
        return commWfSeq;
    }

    public void setCommWfSeq(Long commWfSeq) {
        this.commWfSeq = commWfSeq;
    }

    public MwCommWf commWfSeq(Long commWfSeq) {
        this.commWfSeq = commWfSeq;
        return this;
    }

    public String getCommWfId() {
        return commWfId;
    }

    public void setCommWfId(String commWfId) {
        this.commWfId = commWfId;
    }

    public MwCommWf commWfId(String commWfId) {
        this.commWfId = commWfId;
        return this;
    }

    public String getCommWfNm() {
        return commWfNm;
    }

    public void setCommWfNm(String commWfNm) {
        this.commWfNm = commWfNm;
    }

    public MwCommWf commWfNm(String commWfNm) {
        this.commWfNm = commWfNm;
        return this;
    }

    public String getCommWfRulStr() {
        return commWfRulStr;
    }

    public void setCommWfRulStr(String commWfRulStr) {
        this.commWfRulStr = commWfRulStr;
    }

    public MwCommWf commWfRulStr(String commWfRulStr) {
        this.commWfRulStr = commWfRulStr;
        return this;
    }

    public String getCommWfCmnt() {
        return commWfCmnt;
    }

    public void setCommWfCmnt(String commWfCmnt) {
        this.commWfCmnt = commWfCmnt;
    }

    public Long getCommWfStsKey() {
        return commWfStsKey;
    }

    public void setCommWfStsKey(Long commWfStsKey) {
        this.commWfStsKey = commWfStsKey;
    }

    public MwCommWf commWfStsKey(Long commWfStsKey) {
        this.commWfStsKey = commWfStsKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCommWf crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCommWf crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCommWf lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCommWf lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCommWf delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
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

    public MwCommWf effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCommWf effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCommWf crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getMwFuncKey() {
        return mwFuncKey;
    }

    public void setMwFuncKey(Long mwFuncKey) {
        this.mwFuncKey = mwFuncKey;
    }

    public MwCommWf mwFuncKey(Long mwFuncKey) {
        this.mwFuncKey = mwFuncKey;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwCommWf mwCommWf = (MwCommWf) o;
        if (mwCommWf.getCommWfSeq() == null || getCommWfSeq() == null) {
            return false;
        }
        return Objects.equals(getCommWfSeq(), mwCommWf.getCommWfSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCommWfSeq());
    }

    @Override
    public String toString() {
        return "MwCommWf{" +
            "id=" + getCommWfSeq() +
            ", commWfSeq=" + getCommWfSeq() +
            ", commWfId='" + getCommWfId() + "'" +
            ", commWfNm='" + getCommWfNm() + "'" +
            ", commWfRulStr='" + getCommWfRulStr() + "'" +
            ", commWfStsKey=" + getCommWfStsKey() +
            ", crtdBy='" + getCrtdBy() + "'" +
            ", crtdDt='" + getCrtdDt() + "'" +
            ", lastUpdBy='" + getLastUpdBy() + "'" +
            ", lastUpdDt='" + getLastUpdDt() + "'" +
            ", delFlg='" + isDelFlg() + "'" +
            ", effStartDt='" + getEffStartDt() + "'" +
            ", effEndDt='" + getEffEndDt() + "'" +
            ", crntRecFlg='" + isCrntRecFlg() + "'" +
            ", mwFuncKey=" + getMwFuncKey() +
            "}";
    }
}
