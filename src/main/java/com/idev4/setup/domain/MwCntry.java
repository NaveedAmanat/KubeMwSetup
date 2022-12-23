package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCntry.
 */
@Entity
@Table(name = "mw_cntry")
//@IdClass(MwCntryId.class)
public class MwCntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cntry_seq")
    private Long cntrySeq;

    @Column(name = "cntry_cd")
    private String cntryCd;

    @Column(name = "cntry_nm")
    private String cntryNm;

    @Column(name = "cntry_cmnt")
    private String cntryCmnt;

    @Column(name = "cntry_sts_key")
    private Long cntryStsKey;

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

    public Long getCntrySeq() {
        return cntrySeq;
    }

    public void setCntrySeq(Long cntrySeq) {
        this.cntrySeq = cntrySeq;
    }

    public MwCntry cntrySeq(Long cntrySeq) {
        this.cntrySeq = cntrySeq;
        return this;
    }

    public String getCntryCd() {
        return cntryCd;
    }

    public void setCntryCd(String cntryCd) {
        this.cntryCd = cntryCd;
    }

    public MwCntry cntryCd(String cntryCd) {
        this.cntryCd = cntryCd;
        return this;
    }

    public String getCntryNm() {
        return cntryNm;
    }

    public void setCntryNm(String cntryNm) {
        this.cntryNm = cntryNm;
    }

    public MwCntry cntryNm(String cntryNm) {
        this.cntryNm = cntryNm;
        return this;
    }

    public String getCntryCmnt() {
        return cntryCmnt;
    }

    public void setCntryCmnt(String cntryCmnt) {
        this.cntryCmnt = cntryCmnt;
    }

    public MwCntry cntryCmnt(String cntryCmnt) {
        this.cntryCmnt = cntryCmnt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCntry crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCntry crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCntry lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCntry lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCntry delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwCntry effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCntry effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCntry crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Long getCntryStsKey() {
        return cntryStsKey;
    }

    public void setCntryStsKey(Long cntryStsKey) {
        this.cntryStsKey = cntryStsKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwCntry mwCntry = (MwCntry) o;
        if (mwCntry.getCntrySeq() == null || getCntrySeq() == null) {
            return false;
        }
        return Objects.equals(getCntrySeq(), mwCntry.getCntrySeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCntrySeq());
    }

    @Override
    public String toString() {
        return "MwCntry{" +
            "id=" + getCntrySeq() +
            ", cntrySeq=" + getCntrySeq() +
            ", cntryCd='" + getCntryCd() + "'" +
            ", cntryNm='" + getCntryNm() + "'" +
            ", cntryCmnt='" + getCntryCmnt() + "'" +
            ", cntryStsKey=" + getCntryStsKey() +
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
