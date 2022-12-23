package com.idev4.setup.dto;

import java.time.Instant;


public class MwQstDto {
    private long qstSeq;
    private String qstId;
    private String qstStr;
    private long qstSortOrdr;
    private long qstTypKey;
    private long qstStsKey;
    private long qstCtgryKey;
    private long qstnrSeq;
    private String crtdBy;
    private Instant crtdDt;
    private String lastUpdBy;
    private Instant lastUpdDt;
    private Boolean delFlg;
    private Instant effStartDt;
    private Instant effEndDt;
    private Boolean crntRecFlg;

    public long getQstSeq() {
        return qstSeq;
    }

    public void setQstSeq(long qstSeq) {
        this.qstSeq = qstSeq;
    }

    public String getQstId() {
        return qstId;
    }

    public void setQstId(String qstId) {
        this.qstId = qstId;
    }

    public String getQstStr() {
        return qstStr;
    }

    public void setQstStr(String qstStr) {
        this.qstStr = qstStr;
    }

    public long getQstSortOrdr() {
        return qstSortOrdr;
    }

    public void setQstSortOrdr(long qstSortOrdr) {
        this.qstSortOrdr = qstSortOrdr;
    }

    public long getQstTypKey() {
        return qstTypKey;
    }

    public void setQstTypKey(long qstTypKey) {
        this.qstTypKey = qstTypKey;
    }

    public long getQstStsKey() {
        return qstStsKey;
    }

    public void setQstStsKey(long qstStsKey) {
        this.qstStsKey = qstStsKey;
    }

    public long getQstCtgryKey() {
        return qstCtgryKey;
    }

    public void setQstCtgryKey(long qstCtgryKey) {
        this.qstCtgryKey = qstCtgryKey;
    }

    public long getQstnrSeq() {
        return qstnrSeq;
    }

    public void setQstnrSeq(long qstnrSeq) {
        this.qstnrSeq = qstnrSeq;
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
