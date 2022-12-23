package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCommWfMsgRcpt.
 */
@Entity
@Table(name = "mw_comm_wf_msg_rcpt")
//@IdClass(MwCommWfMsgRcptId.class)
public class MwCommWfMsgRcpt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comm_wf_msg_rcpt_seq")
    private Long commWfMsgRcptSeq;

    @Column(name = "rcpt_addr")
    private String rcptAddr;

    @Column(name = "rcpt_typ_key")
    private Long rcptTypKey;

    @Column(name = "rcpt_addr_typ_key")
    private Long rcptAddrTypKey;

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

    public Long getCommWfMsgRcptSeq() {
        return commWfMsgRcptSeq;
    }

    public void setCommWfMsgRcptSeq(Long commWfMsgRcptSeq) {
        this.commWfMsgRcptSeq = commWfMsgRcptSeq;
    }

    public MwCommWfMsgRcpt commWfMsgRcptSeq(Long commWfMsgRcptSeq) {
        this.commWfMsgRcptSeq = commWfMsgRcptSeq;
        return this;
    }

    public String getRcptAddr() {
        return rcptAddr;
    }

    public void setRcptAddr(String rcptAddr) {
        this.rcptAddr = rcptAddr;
    }

    public MwCommWfMsgRcpt rcptAddr(String rcptAddr) {
        this.rcptAddr = rcptAddr;
        return this;
    }

    public Long getRcptTypKey() {
        return rcptTypKey;
    }

    public void setRcptTypKey(Long rcptTypKey) {
        this.rcptTypKey = rcptTypKey;
    }

    public MwCommWfMsgRcpt rcptTypKey(Long rcptTypKey) {
        this.rcptTypKey = rcptTypKey;
        return this;
    }

    public Long getRcptAddrTypKey() {
        return rcptAddrTypKey;
    }

    public void setRcptAddrTypKey(Long rcptAddrTypKey) {
        this.rcptAddrTypKey = rcptAddrTypKey;
    }

    public MwCommWfMsgRcpt rcptAddrTypKey(Long rcptAddrTypKey) {
        this.rcptAddrTypKey = rcptAddrTypKey;
        return this;
    }

    public Long getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(Long msgKey) {
        this.msgKey = msgKey;
    }

    public MwCommWfMsgRcpt msgKey(Long msgKey) {
        this.msgKey = msgKey;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCommWfMsgRcpt crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCommWfMsgRcpt crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCommWfMsgRcpt lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCommWfMsgRcpt lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCommWfMsgRcpt delFlg(Boolean delFlg) {
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

    public MwCommWfMsgRcpt effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCommWfMsgRcpt effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCommWfMsgRcpt crntRecFlg(Boolean crntRecFlg) {
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
        MwCommWfMsgRcpt mwCommWfMsgRcpt = (MwCommWfMsgRcpt) o;
        if (mwCommWfMsgRcpt.getCommWfMsgRcptSeq() == null || getCommWfMsgRcptSeq() == null) {
            return false;
        }
        return Objects.equals(getCommWfMsgRcptSeq(), mwCommWfMsgRcpt.getCommWfMsgRcptSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCommWfMsgRcptSeq());
    }

    @Override
    public String toString() {
        return "MwCommWfMsgRcpt{" +
            "id=" + getCommWfMsgRcptSeq() +
            ", commWfMsgRcptSEq=" + getCommWfMsgRcptSeq() +
            ", rcptAddr='" + getRcptAddr() + "'" +
            ", rcptTypKey=" + getRcptTypKey() +
            ", rcptAddrTypKey=" + getRcptAddrTypKey() +
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
