package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "Mw_Form")
//	@IdClass(MwFormId.class)
public class MwForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "form_SEQ")
    private Long formSeq;

    @Column(name = "form_id")
    private String formId;

    @Column(name = "form_nm")
    private String formNm;

    @Column(name = "form_url")
    private String formUrl;

    @Column(name = "form_cls")
    private String formCls;

    @Column(name = "form_sts_key")
    private Long formStsKey;

//	    @Column(name = "form_sort_ordr")
//	    private Long formSortOrdr;

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

    //	    @Id
    @Column(name = "eff_start_dt")
    private Instant effStartDt;

    @Column(name = "eff_end_dt")
    private Instant effEndDt;

    @Column(name = "crnt_rec_flg")
    private Boolean crntRecFlg;

//	    public Long getFormSortOrdr() {
//			return formSortOrdr;
//		}
//
//		public void setFormSortOrdr(Long formSortOrdr) {
//			this.formSortOrdr = formSortOrdr;
//		}

    public Long getFormSeq() {
        return formSeq;
    }

    public void setFormSeq(Long formSeq) {
        this.formSeq = formSeq;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormNm() {
        return formNm;
    }

    public void setFormNm(String formNm) {
        this.formNm = formNm;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getFormCls() {
        return formCls;
    }

    public void setFormCls(String formCls) {
        this.formCls = formCls;
    }

    public Long getFormStsKey() {
        return formStsKey;
    }

    public void setFormStsKey(Long formStsKey) {
        this.formStsKey = formStsKey;
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
}
