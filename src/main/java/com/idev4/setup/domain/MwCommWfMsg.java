package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwCommWfMsg.
 */
@Entity
@Table(name = "mw_comm_wf_msg")
//@IdClass(MwCommWfMsgId.class)
public class MwCommWfMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comm_wf_msg_seq")
    private Long commWfMsgSeq;

    @Column(name = "msg_title")
    private String msgTitle;

    @Column(name = "msg_str")
    private String msgStr;

    @Column(name = "msg_due_dt")
    private String msgDueDt;

    @Column(name = "msg_typ_key")
    private Long msgTypKey;

    @Column(name = "comm_wf_seq")
    private Long commWfSeq;

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

    public Long getCommWfMsgSeq() {
        return commWfMsgSeq;
    }

    public void setCommWfMsgSeq(Long commWfMsgSeq) {
        this.commWfMsgSeq = commWfMsgSeq;
    }

    public MwCommWfMsg commWfMsgSeq(Long commWfMsgSeq) {
        this.commWfMsgSeq = commWfMsgSeq;
        return this;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public MwCommWfMsg msgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
        return this;
    }

    public String getMsgStr() {
        return msgStr;
    }

    public void setMsgStr(String msgStr) {
        this.msgStr = msgStr;
    }

    public MwCommWfMsg msgStr(String msgStr) {
        this.msgStr = msgStr;
        return this;
    }

    public String getMsgDueDt() {
        return msgDueDt;
    }

    public void setMsgDueDt(String msgDueDt) {
        this.msgDueDt = msgDueDt;
    }

    public MwCommWfMsg msgDueDt(String msgDueDt) {
        this.msgDueDt = msgDueDt;
        return this;
    }

    public Long getMsgTypKey() {
        return msgTypKey;
    }

    public void setMsgTypKey(Long msgTypKey) {
        this.msgTypKey = msgTypKey;
    }

    public MwCommWfMsg msgTypKey(Long msgTypKey) {
        this.msgTypKey = msgTypKey;
        return this;
    }

    public Long getCommWfSeq() {
        return commWfSeq;
    }

    public void setCommWfSeq(Long commWfSeq) {
        this.commWfSeq = commWfSeq;
    }

    public MwCommWfMsg commWfSeq(Long commWfSeq) {
        this.commWfSeq = commWfSeq;
        return this;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public MwCommWfMsg crtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
        return this;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public MwCommWfMsg crtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
        return this;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public MwCommWfMsg lastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
        return this;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public MwCommWfMsg lastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
        return this;
    }

    public Boolean isDelFlg() {
        return delFlg;
    }

    public MwCommWfMsg delFlg(Boolean delFlg) {
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

    public MwCommWfMsg effStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
        return this;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public MwCommWfMsg effEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
        return this;
    }

    public Boolean isCrntRecFlg() {
        return crntRecFlg;
    }

    public MwCommWfMsg crntRecFlg(Boolean crntRecFlg) {
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
        MwCommWfMsg mwCommWfMsg = (MwCommWfMsg) o;
        if (mwCommWfMsg.getCommWfMsgSeq() == null || getCommWfMsgSeq() == null) {
            return false;
        }
        return Objects.equals(getCommWfMsgSeq(), mwCommWfMsg.getCommWfMsgSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCommWfMsgSeq());
    }

    @Override
    public String toString() {
        return "MwCommWfMsg{" +
            "id=" + getCommWfMsgSeq() +
            ", commWfMsgSeq=" + getCommWfMsgSeq() +
            ", msgTitle='" + getMsgTitle() + "'" +
            ", msgStr='" + getMsgStr() + "'" +
            ", msgDueDt='" + getMsgDueDt() + "'" +
            ", msgTypKey=" + getMsgTypKey() +
            ", commWfSeq=" + getCommWfSeq() +
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
