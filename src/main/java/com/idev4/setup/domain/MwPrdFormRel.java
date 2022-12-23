package com.idev4.setup.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_prd_form_rel")
//@IdClass ( MwPrdFormRelId.class )

public class MwPrdFormRel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Transient
    public String formName;
    @Id
    @Column(name = "prd_form_rel_seq")
    private Long prdFormRelSeq;
    @Column(name = "form_seq")
    private Long formSeq;
    @Column(name = "prd_seq")
    private Long prdSeq;
    //    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;
    @Column(name = "eff_end_dt")
    private Instant effEndDt;
    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;
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
    @Column(name = "FORM_SORT_ORDR")
    private Long formSortOrdr;
    @Column(name = "FORM_MNDTRY_FLG")
    private Boolean formMndtryFlg;

    public Long getPrdFormRelSeq() {
        return prdFormRelSeq;
    }

    public void setPrdFormRelSeq(Long prdFormRelSeq) {
        this.prdFormRelSeq = prdFormRelSeq;
    }

    public Long getFormSeq() {
        return formSeq;
    }

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Long getFormSortOrdr() {
        return formSortOrdr;
    }

    public void setFormSortOrdr(Long formSortOrdr) {
        this.formSortOrdr = formSortOrdr;
    }

    public Boolean getFormMndtryFlg() {
        return formMndtryFlg;
    }

    public void setFormMndtryFlg(Boolean formMndtryFlg) {
        this.formMndtryFlg = formMndtryFlg;
    }

}
