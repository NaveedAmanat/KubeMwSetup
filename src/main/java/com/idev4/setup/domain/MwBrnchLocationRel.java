package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwBrnchLocationRel.
 */
@Entity
@Table(name = "mw_brnch_location_rel")
//@IdClass(MwBrnchLocationRelId.class)
public class MwBrnchLocationRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "brnch_location_rel_seq")
    private Long brnchLocationRelSeq;

    @Column(name = "brnch_seq")
    private Long brnchSeq;

    @Column(name = "city_seq")
    private Long citySeq;

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

    @Column(name = "del_flg")
    private Boolean delFlg;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    public Long getBrnchLocationRelSeq() {
        return brnchLocationRelSeq;
    }

    public void setBrnchLocationRelSeq(Long brnchLocationRelSeq) {
        this.brnchLocationRelSeq = brnchLocationRelSeq;
    }

    public MwBrnchLocationRel brnchLocationRelSeq(Long brnchLocationRelSeq) {
        this.brnchLocationRelSeq = brnchLocationRelSeq;
        return this;
    }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public MwBrnchLocationRel brnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
        return this;
    }

    public Long getCitySeq() {
        return citySeq;
    }

    public void setCitySeq(Long citySeq) {
        this.citySeq = citySeq;
    }

    public MwBrnchLocationRel citySeq(Long citySeq) {
        this.citySeq = citySeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwBrnchLocationRel crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwBrnchLocationRel crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwBrnchLocationRel lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwBrnchLocationRel lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public MwBrnchLocationRel effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwBrnchLocationRel effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwBrnchLocationRel delFlg(Boolean delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwBrnchLocationRel crntRecFlg(Boolean crntRecFlg) {
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
        MwBrnchLocationRel mwBrnchLocationRel = (MwBrnchLocationRel) o;
        if (mwBrnchLocationRel.getBrnchLocationRelSeq() == null || getBrnchLocationRelSeq() == null) {
            return false;
        }
        return Objects.equals(getBrnchLocationRelSeq(), mwBrnchLocationRel.getBrnchLocationRelSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBrnchLocationRelSeq());
    }

    @Override
    public String toString() {
        return "MwBrnchLocationRel{" +
            "id=" + getBrnchLocationRelSeq() +
            ", brnchLocationRelSeq=" + getBrnchLocationRelSeq() +
            ", brnchSeq=" + getBrnchSeq() +
            ", citySeq=" + getCitySeq() +
            ", crtdBy='" + getCrtdBy() + "'" +
            ", crtdDt='" + getCrtdDt() + "'" +
            ", lastUpdBy='" + getLastUpdBy() + "'" +
            ", lastUpdDt='" + getLastUpdDt() + "'" +
            ", effStartDt='" + getEffStartDt() + "'" +
            ", effEndDt='" + getEffEndDt() + "'" +
            ", delFlg='" + isDelFlg() + "'" +
            ", crntRecFlg='" + isCrntRecFlg() + "'" +
            "}";
    }
}
