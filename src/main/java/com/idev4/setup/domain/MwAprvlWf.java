package com.idev4.setup.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwAprvlWf.
 */
@Entity
@Table(name = "mw_aprvl_wf")
public class MwAprvlWf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aprvl_wf_seq")
    private Long aprvlWfSeq;

    @Column(name = "aprvl_wf_nm")
    private String aprvlWfNm;

    @Column(name = "aprvl_wf_id")
    private String aprvlWfId;

    @Column(name = "aprvl_wf_rul_str")
    private String aprvlWfRulStr;

    @Column(name = "aprvl_wf_sts_key")
    private Long aprvlWfStsKey;

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

    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;


    public Long getAprvlWfSeq() {
        return aprvlWfSeq;
    }

    public void setAprvlWfSeq(Long aprvlWfSeq) {
        this.aprvlWfSeq = aprvlWfSeq;
    }

    public MwAprvlWf aprvlWfSeq(Long aprvlWfSeq) {
        this.aprvlWfSeq = aprvlWfSeq;
        return this;
    }

    public String getAprvlWfNm() {
        return aprvlWfNm;
    }

    public void setAprvlWfNm(String aprvlWfNm) {
        this.aprvlWfNm = aprvlWfNm;
    }

    public MwAprvlWf aprvlWfNm(String aprvlWfNm) {
        this.aprvlWfNm = aprvlWfNm;
        return this;
    }

    public String getAprvlWfId() {
        return aprvlWfId;
    }

    public void setAprvlWfId(String aprvlWfId) {
        this.aprvlWfId = aprvlWfId;
    }

    public MwAprvlWf aprvlWfId(String aprvlWfId) {
        this.aprvlWfId = aprvlWfId;
        return this;
    }

    public String getAprvlWfRulStr() {
        return aprvlWfRulStr;
    }

    public void setAprvlWfRulStr(String aprvlWfRulStr) {
        this.aprvlWfRulStr = aprvlWfRulStr;
    }

    public MwAprvlWf aprvlWfRulStr(String aprvlWfRulStr) {
        this.aprvlWfRulStr = aprvlWfRulStr;
        return this;
    }

    public Long getAprvlWfStsKey() {
        return aprvlWfStsKey;
    }

    public void setAprvlWfStsKey(Long aprvlWfStsKey) {
        this.aprvlWfStsKey = aprvlWfStsKey;
    }

    public MwAprvlWf aprvlWfStsKey(Long aprvlWfStsKey) {
        this.aprvlWfStsKey = aprvlWfStsKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwAprvlWf crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwAprvlWf crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwAprvlWf lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwAprvlWf lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwAprvlWf delFlg(Boolean delFlg) {
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

    public MwAprvlWf effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwAprvlWf effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwAprvlWf crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
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
        MwAprvlWf mwAprvlWf = (MwAprvlWf) o;
        if (mwAprvlWf.getAprvlWfSeq() == null || getAprvlWfSeq() == null) {
            return false;
        }
        return Objects.equals(getAprvlWfSeq(), mwAprvlWf.getAprvlWfSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAprvlWfSeq());
    }

    @Override
    public String toString() {
        return "MwAprvlWf{" +
            "id=" + getAprvlWfSeq() +
            ", aprvlWfSeq=" + getAprvlWfSeq() +
            ", aprvlWfNm='" + getAprvlWfNm() + "'" +
            ", aprvlWfId='" + getAprvlWfId() + "'" +
            ", aprvlWfRulStr='" + getAprvlWfRulStr() + "'" +
            ", aprvlWfStsKey=" + getAprvlWfStsKey() +
            ", crtdBy='" + getCrtdBy() + "'" +
            ", crtdDt='" + getCrtdDt() + "'" +
            ", lastUpdBy='" + getLastUpdBy() + "'" +
            ", lastUpdDt='" + getLastUpdDt() + "'" +
            ", delFlg='" + isDelFlg() + "'" +
            ", effStartDt='" + getEffStartDt() + "'" +
            ", effEndDt='" + getEffEndDt() + "'" +
            ", crntRecFlg='" + isCrntRecFlg() + "'" +
            "}";
    }
}
