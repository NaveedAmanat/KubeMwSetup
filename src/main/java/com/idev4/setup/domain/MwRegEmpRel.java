package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwRegEmpRel.
 */
@Entity
@Table(name = "mw_reg_emp_rel")
//@IdClass(MwRegEmpRelId.class)
public class MwRegEmpRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "reg_emp_seq")
    private Long regEmpSeq;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "emp_seq")
    private Long empSeq;

    @Column(name = "covering_emp_seq")
    private Long coveringEmpSeq;

    @Column(name = "covering_emp_from_dt")
    private Instant coveringEmpFromDt;

    @Column(name = "covering_emp_to_dt")
    private Instant coveringEmpToDt;

    @Column(name = "crtd_by")
    private String crtdBy;

    @Column(name = "crtd_dt")
    private Instant crtdDt;

    @Column(name = "last_upd_by")
    private String lastUpdBy;

    @Column(name = "last_upd_dt")
    private Instant lastUpdDt;

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;


    public Long getRegEmpSeq() {
        return regEmpSeq;
    }

    public void setRegEmpSeq(Long regEmpSeq) {
        this.regEmpSeq = regEmpSeq;
    }

    public MwRegEmpRel regEmpSeq(Long regEmpSeq) {
        this.regEmpSeq = regEmpSeq;
        return this;
    }

    public Long getRegSeq() {
        return regSeq;
    }

    public void setRegSeq(Long regSeq) {
        this.regSeq = regSeq;
    }

    public MwRegEmpRel regSeq(Long regSeq) {
        this.regSeq = regSeq;
        return this;
    }

    public Long getEmpSeq() {
        return empSeq;
    }

    public void setEmpSeq(Long empSeq) {
        this.empSeq = empSeq;
    }

    public MwRegEmpRel empSeq(Long empSeq) {
        this.empSeq = empSeq;
        return this;
    }

    public Long getCoveringEmpSeq() {
        return coveringEmpSeq;
    }

    public void setCoveringEmpSeq(Long coveringEmpSeq) {
        this.coveringEmpSeq = coveringEmpSeq;
    }

    public MwRegEmpRel coveringEmpSeq(Long coveringEmpSeq) {
        this.coveringEmpSeq = coveringEmpSeq;
        return this;
    }

    public Instant getCoveringEmpFromDt() {
        return coveringEmpFromDt;
    }

    public void setCoveringEmpFromDt(Instant coveringEmpFromDt) {
        this.coveringEmpFromDt = coveringEmpFromDt;
    }

    public MwRegEmpRel coveringEmpFromDt(Instant coveringEmpFromDt) {
        this.coveringEmpFromDt = coveringEmpFromDt;
        return this;
    }

    public Instant getCoveringEmpToDt() {
        return coveringEmpToDt;
    }

    public void setCoveringEmpToDt(Instant coveringEmpToDt) {
        this.coveringEmpToDt = coveringEmpToDt;
    }

    public MwRegEmpRel coveringEmpToDt(Instant coveringEmpToDt) {
        this.coveringEmpToDt = coveringEmpToDt;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwRegEmpRel crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwRegEmpRel crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwRegEmpRel lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwRegEmpRel lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwRegEmpRel effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwRegEmpRel effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwRegEmpRel crntRecFlg(Boolean crntRecFlg) {
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
        MwRegEmpRel mwRegEmpRel = (MwRegEmpRel) o;
        if (mwRegEmpRel.getRegEmpSeq() == null || getRegEmpSeq() == null) {
            return false;
        }
        return Objects.equals(getRegEmpSeq(), mwRegEmpRel.getRegEmpSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRegEmpSeq());
    }

    @Override
    public String toString() {
        return "MwRegEmpRel{" +
            "id=" + getRegEmpSeq() +
            ", regEmpSeq=" + getRegEmpSeq() +
            ", regSeq=" + getRegSeq() +
            ", empSeq=" + getEmpSeq() +
            ", coveringEmpSeq=" + getCoveringEmpSeq() +
            ", coveringEmpFromDt='" + getCoveringEmpFromDt() + "'" +
            ", coveringEmpToDt='" + getCoveringEmpToDt() + "'" +
            ", crtdBy='" + getCrtdBy() + "'" +
            ", crtdDt='" + getCrtdDt() + "'" +
            ", lastUpdBy='" + getLastUpdBy() + "'" +
            ", lastUpdDt='" + getLastUpdDt() + "'" +
            ", effStartDt='" + getEffStartDt() + "'" +
            ", effEndDt='" + getEffEndDt() + "'" +
            ", crntRecFlg='" + isCrntRecFlg() + "'" +
            "}";
    }
}
