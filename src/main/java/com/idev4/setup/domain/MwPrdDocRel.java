package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_prd_doc_rel")
//@IdClass ( MwPrdDocRelId.class )

public class MwPrdDocRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prd_doc_rel_seq")
    private Long prdDocRelSeq;

    @Column(name = "doc_seq")
    private Long docSeq;

    @Column(name = "prd_seq")
    private Long prdSeq;

    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

    @Column(name = "MNDTRY_FLG")
    private Boolean mndtryFlg;

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

    public Boolean getMndtryFlg() {
        return mndtryFlg;
    }

    public void setMndtryFlg(Boolean mndtryFlg) {
        this.mndtryFlg = mndtryFlg;
    }

    public Long getPrdDocRelSeq() {
        return prdDocRelSeq;
    }

    public void setPrdDocRelSeq(Long prdDocRelSeq) {
        this.prdDocRelSeq = prdDocRelSeq;
    }

    public Long getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Long docSeq) {
        this.docSeq = docSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

    public Instant getEffStartDt() {
        return effStartDt;
    }

    public void setEffStartDt(Instant effStartDt) {
        this.effStartDt = effStartDt;
    }

    public Instant getEffEndDt() {
        return effEndDt;
    }

    public void setEffEndDt(Instant effEndDt) {
        this.effEndDt = effEndDt;
    }

    public Boolean getCrntRecFlg() {
        return crntRecFlg;
    }

    public void setCrntRecFlg(Boolean crntRecFlg) {
        this.crntRecFlg = crntRecFlg;
    }

    public String getCrtdBy() {
        return crtdBy;
    }

    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }

    public Instant getCrtdDt() {
        return crtdDt;
    }

    public void setCrtdDt(Instant crtdDt) {
        this.crtdDt = crtdDt;
    }

    public String getLastUpdBy() {
        return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
        this.lastUpdBy = lastUpdBy;
    }

    public Instant getLastUpdDt() {
        return lastUpdDt;
    }

    public void setLastUpdDt(Instant lastUpdDt) {
        this.lastUpdDt = lastUpdDt;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }
}
