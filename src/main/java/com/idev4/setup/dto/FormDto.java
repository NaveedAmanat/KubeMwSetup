package com.idev4.setup.dto;

import java.time.Instant;

public class FormDto {

    private static final long serialVersionUID = 1L;

    private Long formSeq;
    private String formId;
    private String formNm;
    private String formUrl;
    private String formCls;
    private Long formStsKey;
    private Long formSortOrdr;
    private String crtdBy;
    private Instant crtdDt;
    private String lastUpdBy;
    private Instant lastUpdDt;
    private Boolean delFlg;
    private Instant effStartDt;
    private Instant effEndDt;
    private Boolean crntRecFlg;

    public Long getFormSortOrdr() {
        return formSortOrdr;
    }

    public void setFormSortOrdr(Long formSortOrdr) {
        this.formSortOrdr = formSortOrdr;
    }

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
