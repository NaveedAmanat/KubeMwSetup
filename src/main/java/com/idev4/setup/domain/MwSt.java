package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwSt.
 */
@Entity
@Table(name = "mw_st")
//@IdClass(MwStId.class)
public class MwSt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "st_seq")
    private Long stSeq;

    @Column(name = "st_cd")
    private String stCd;

    @Column(name = "st_nm")
    private String stNm;

    @Column(name = "st_dscr")
    private String stCmnt;

    @Column(name = "cntry_seq")
    private Long cntrySeq;

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

    @Column(name = "st_sts_key")
    private Long stStsKey;


    public Long getStSeq() {
        return stSeq;
    }

    public void setStSeq(Long stSeq) {
        this.stSeq = stSeq;
    }

    public MwSt stSeq(Long stSeq) {
        this.stSeq = stSeq;
        return this;
    }

    public String getStCd() {
        return stCd;
    }

    public void setStCd(String stCd) {
        this.stCd = stCd;
    }

    public MwSt stCd(String stCd) {
        this.stCd = stCd;
        return this;
    }

    public String getStNm() {
        return stNm;
    }

    public void setStNm(String stNm) {
        this.stNm = stNm;
    }

    public MwSt stNm(String stNm) {
        this.stNm = stNm;
        return this;
    }

    public String getStCmnt() {
        return stCmnt;
    }

    public void setStCmnt(String stCmnt) {
        this.stCmnt = stCmnt;
    }

    public MwSt stCmnt(String stCmnt) {
        this.stCmnt = stCmnt;
        return this;
    }

    public Long getCntrySeq() {
        return cntrySeq;
    }

    public void setCntrySeq(Long cntrySeq) {
        this.cntrySeq = cntrySeq;
    }

    public MwSt cntrySeq(Long cntrySeq) {
        this.cntrySeq = cntrySeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwSt crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwSt crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwSt lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwSt lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwSt delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwSt effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwSt effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwSt crntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
        return this;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public Long getStStsKey() {
        return stStsKey;
    }

    public void setStStsKey(Long stStsKey) {
        this.stStsKey = stStsKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwSt mwSt = (MwSt) o;
        if (mwSt.getStSeq() == null || getStSeq() == null) {
            return false;
        }
        return Objects.equals(getStSeq(), mwSt.getStSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getStSeq());
    }

    @Override
    public String toString() {
        return "MwSt{" +
            "id=" + getStSeq() +
            ", stSeq=" + getStSeq() +
            ", stCd='" + getStCd() + "'" +
            ", stNm='" + getStNm() + "'" +
            ", stCmnt='" + getStCmnt() + "'" +
            ", cntrySeq=" + getCntrySeq() +
            ", stStsKey=" + getStStsKey() +
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
