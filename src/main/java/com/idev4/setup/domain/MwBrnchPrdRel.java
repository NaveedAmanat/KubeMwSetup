package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBrnchPrdRel.
 */
@Entity
@Table(name = "MW_brnch_prd_rel")
//@IdClass ( MwBrnchPrdRelId.class )
public class MwBrnchPrdRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "brnch_prd_rel_seq")
    private Long brnchPrdRelSeq;

    @Column(name = "brnch_seq")
    private Long brnchSeq;

    @Column(name = "prd_seq")
    private Long prdSeq;

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

    @Column(name = "DEL_FLG")
    private Boolean delFlg;

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Long getBrnchPrdRelSeq() {
        return brnchPrdRelSeq;
    }

    public void setBrnchPrdRelSeq(Long brnchPrdRelSeq) {
        this.brnchPrdRelSeq = brnchPrdRelSeq;
    }

    public MwBrnchPrdRel brnchPrdRelSeq(Long brnchPrdRelSeq) {
        this.brnchPrdRelSeq = brnchPrdRelSeq;
        return this;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public MwBrnchPrdRel brnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
        return this;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public MwBrnchPrdRel prdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBrnchPrdRel crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBrnchPrdRel crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBrnchPrdRel lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBrnchPrdRel lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwBrnchPrdRel effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBrnchPrdRel effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBrnchPrdRel crntRecFlg(Boolean crntRecFlg) {
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
        MwBrnchPrdRel mwBrnchPrdRel = (MwBrnchPrdRel) o;
        if (mwBrnchPrdRel.getBrnchPrdRelSeq() == null || getBrnchPrdRelSeq() == null) {
            return false;
        }
        return Objects.equals(getBrnchPrdRelSeq(), mwBrnchPrdRel.getBrnchPrdRelSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBrnchPrdRelSeq());
    }

    @Override
    public String toString() {
        return "MwBrnchPrdRel{" + "id=" + getBrnchPrdRelSeq() + ", brnchPrdRelSeq=" + getBrnchPrdRelSeq() + ", brnchSeq=" + getBrnchSeq()
            + ", prdSeq=" + getPrdSeq() + ", crtdBy='" + getCrtdBy() + "'" + ", crtdDt='" + getCrtdDt() + "'" + ", lastUpdBy='"
            + getLastUpdBy() + "'" + ", lastUpdDt='" + getLastUpdDt() + "'" + ", effStartDt='" + getEffStartDt() + "'" + ", effEndDt='"
            + getEffEndDt() + "'" + ", crntRecFlg='" + isCrntRecFlg() + "'" + "}";
    }
}
