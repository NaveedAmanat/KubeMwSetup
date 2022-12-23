package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCommWfMsgDueDt.
 */
@Entity
@Table(name = "mw_comm_wf_msg_due_dt")
//@IdClass(MwCommWfMsgDueDtId.class)
public class MwCommWfMsgDueDt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comm_wf_msg_due_dt_seq")
    private Long commWfMsgDueDtSeq;

    @Column(name = "num_of_days")
    private Integer numOfDays;

    @Column(name = "bef_and_aftr_flg")
    private Boolean befAndAftrFlg;

    @Column(name = "dt")
    private Instant dt;

    @Column(name = "msg_key")
    private Long msgKey;

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

    public Long getCommWfMsgDueDtSeq() {
        return commWfMsgDueDtSeq;
    }

    public void setCommWfMsgDueDtSeq(Long commWfMsgDueDtSeq) {
        this.commWfMsgDueDtSeq = commWfMsgDueDtSeq;
    }

    public MwCommWfMsgDueDt commWfMsgDueDtSeq(Long commWfMsgDueDtSeq) {
        this.commWfMsgDueDtSeq = commWfMsgDueDtSeq;
        return this;
    }

    public Integer getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(Integer numOfDays) {
        this.numOfDays = numOfDays;
    }

    public MwCommWfMsgDueDt numOfDays(Integer numOfDays) {
        this.numOfDays = numOfDays;
        return this;
    }

    public Boolean isBefAndAftrFlg() {
        return befAndAftrFlg;
    }

    public MwCommWfMsgDueDt befAndAftrFlg(Boolean befAndAftrFlg) {
        this.befAndAftrFlg = befAndAftrFlg;
        return this;
    }

    public void setBefAndAftrFlg(Boolean befAndAftrFlg) {
        this.befAndAftrFlg = befAndAftrFlg;
    }

    public Instant getDt() {
        return dt;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public MwCommWfMsgDueDt dt(Instant dt) {
        this.dt = dt;
        return this;
    }

    public Long getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(Long msgKey) {
        this.msgKey = msgKey;
    }

    public MwCommWfMsgDueDt msgKey(Long msgKey) {
        this.msgKey = msgKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCommWfMsgDueDt crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCommWfMsgDueDt crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCommWfMsgDueDt lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCommWfMsgDueDt lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCommWfMsgDueDt delFlg(Boolean delFlg) {
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

    public MwCommWfMsgDueDt effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCommWfMsgDueDt effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCommWfMsgDueDt crntRecFlg(Boolean crntRecFlg) {
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
        MwCommWfMsgDueDt mwCommWfMsgDueDt = (MwCommWfMsgDueDt) o;
        if (mwCommWfMsgDueDt.getCommWfMsgDueDtSeq() == null || getCommWfMsgDueDtSeq() == null) {
            return false;
        }
        return Objects.equals(getCommWfMsgDueDtSeq(), mwCommWfMsgDueDt.getCommWfMsgDueDtSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCommWfMsgDueDtSeq());
    }

    @Override
    public String toString() {
        return "MwCommWfMsgDueDt{" +
            "id=" + getCommWfMsgDueDtSeq() +
            ", commWfMsgDueDtSEq=" + getCommWfMsgDueDtSeq() +
            ", numOfDays=" + getNumOfDays() +
            ", befAndAftrFlg='" + isBefAndAftrFlg() + "'" +
            ", dt='" + getDt() + "'" +
            ", msgKey=" + getMsgKey() +
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
