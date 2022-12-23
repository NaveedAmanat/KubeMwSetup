package com.idev4.setup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwBrnch.
 */
@Entity
@Table(name = "mw_brnch")
//@IdClass ( MwBrnchId.class )
public class MwBrnch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "brnch_seq")
    private Long brnchSeq;

    @Column(name = "brnch_cd")
    private String brnchCd;

    @Column(name = "brnch_nm")
    private String brnchNm;

    @Column(name = "brnch_dscr")
    private String brnchDscr;

    @Column(name = "brnch_sts_Key")
    private Long brnchStsKey;

    @Column(name = "brnch_typ_Key")
    private Long brnchTypKey;

    @Column(name = "brnch_PH_num")
    private String brnchPhNum;

    @Column(name = "area_seq")
    private Long areaSeq;

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

    @Column(name = "cs_flg")
    private Boolean csFlg;

    // Added by Areeba
    // Branch Setup
    @Column(name = "app_vrsn_cd")
    private Long appVrsnCd;

    @Column(name = "email")
    private String email;

    @Column(name = "mob_strt_dt")
    private java.util.Date mobStrtDt;

    @Column(name = "mob_end_dt")
    private java.util.Date mobEndDt;
    // Ended by Areeba

    // Added By Naveed - Dated 24-11-2021
    // MFCIB Region wise Distribution
    @Column(name = "MFCIB_CMPNY_SEQ")
    private Long mfcibCmpnySeq;
    // Ended By Naveed

    // Added by Areeba
    // Branch Setup
    @Column(name = "hr_loc_cd")
    private String hrLocCd;
    // Ended by Areeba

    public Boolean getCsFlg() {
        return csFlg;
    }

    public void setCsFlg(Boolean csFlg) {
        this.csFlg = csFlg;
    }
    // @OneToMany
    // @JoinColumns({
    // @JoinColumn(name = "brnch_seq") ,
    // @JoinColumn(name = "eff_start_dt") })
    // @LazyCollection(LazyCollectionOption.FALSE)
    // private List<MwPort> ports = new LinkedList<MwPort>();
    //
    //
    //
    // public List<MwPort> getPorts() {
    // return ports;
    // }
    //
    // public void setPorts(List<MwPort> ports) {
    // this.ports = ports;
    // }

    public Long getBrnchSeq() {
        return brnchSeq;
    }

    public void setBrnchSeq(Long brnchSeq) {
        this.brnchSeq = brnchSeq;
    }

    public String getBrnchCd() {
        return brnchCd;
    }

    public void setBrnchCd(String brnchCd) {
        this.brnchCd = brnchCd;
    }

    public String getBrnchNm() {
        return brnchNm;
    }

    public void setBrnchNm(String brnchNm) {
        this.brnchNm = brnchNm;
    }

    public String getBrnchDscr() {
        return brnchDscr;
    }

    public void setBrnchDscr(String brnchDscr) {
        this.brnchDscr = brnchDscr;
    }

    public Long getBrnchStsKey() {
        return brnchStsKey;
    }

    public void setBrnchStsKey(Long brnchStsKey) {
        this.brnchStsKey = brnchStsKey;
    }

    public Long getBrnchTypKey() {
        return brnchTypKey;
    }

    public void setBrnchTypKey(Long brnchTypKey) {
        this.brnchTypKey = brnchTypKey;
    }

    public String getBrnchPhNum() {
        return brnchPhNum;
    }

    public void setBrnchPhNum(String brnchPhNum) {
        this.brnchPhNum = brnchPhNum;
    }

    public Long getAreaSeq() {
        return areaSeq;
    }

    public void setAreaSeq(Long areaSeq) {
        this.areaSeq = areaSeq;
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

    public Long getMfcibCmpnySeq() {
        return mfcibCmpnySeq;
    }

    public void setMfcibCmpnySeq(Long mfcibCmpnySeq) {
        this.mfcibCmpnySeq = mfcibCmpnySeq;
    }

    public Long getAppVrsnCd() {
        return appVrsnCd;
    }

    public void setAppVrsnCd(Long appVrsnCd) {
        this.appVrsnCd = appVrsnCd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHrLocCd() {
        return hrLocCd;
    }

    public void setHrLocCd(String hrLocCd) {
        this.hrLocCd = hrLocCd;
    }

    public java.util.Date getMobStrtDt() {
        return mobStrtDt;
    }

    public void setMobStrtDt(java.util.Date mobStrtDt) {
        this.mobStrtDt = mobStrtDt;
    }

    public java.util.Date getMobEndDt() {
        return mobEndDt;
    }

    public void setMobEndDt(java.util.Date mobEndDt) {
        this.mobEndDt = mobEndDt;
    }
}
