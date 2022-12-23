package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "mw_biz_sect")
//@IdClass(MwBizSectId.class)
public class MwBizSect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "biz_sect_SEQ")
    private Long bizSectSeq;

    @Column(name = "biz_sect_id")
    private String bizSectId;

    @Column(name = "biz_sect_nm")
    private String bizSectNm;

    @Column(name = "biz_sect_sts_Key")
    private Long bizSectStsKey;


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

    @Column(name = "biz_Sect_Sort_Ordr")
    private Long bizSectSortOrdr;


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getBizSectSeq() {
        return bizSectSeq;
    }

    public void setBizSectSeq(Long bizSectSeq) {
        this.bizSectSeq = bizSectSeq;
    }

    public String getBizSectId() {
        return bizSectId;
    }

    public void setBizSectId(String bizSectId) {
        this.bizSectId = bizSectId;
    }

    public String getBizSectNm() {
        return bizSectNm;
    }

    public void setBizSectNm(String bizSectNm) {
        this.bizSectNm = bizSectNm;
    }

    public Long getBizSectStsKey() {
        return bizSectStsKey;
    }

    public void setBizSectStsKey(Long bizSectStsKey) {
        this.bizSectStsKey = bizSectStsKey;
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

    public Long getBizSectSortOrdr() {
        return bizSectSortOrdr;
    }

    public void setBizSectSortOrdr(Long bizSectSortOrdr) {
        this.bizSectSortOrdr = bizSectSortOrdr;
    }

}
