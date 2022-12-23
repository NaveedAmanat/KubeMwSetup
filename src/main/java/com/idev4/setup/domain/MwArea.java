package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * A MwArea.
 */
@Entity
@Table(name = "mw_area")
//@IdClass(MwAreaId.class)
public class MwArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "area_seq")
    private Long areaSeq;

    @Column(name = "area_cd")
    private String areaCd;

    @Column(name = "area_nm")
    private String areaNm;

    @Column(name = "area_dscr")
    private String areaDscr;

    @Column(name = "area_PH_num")
    private String areaPhNum;

    @Column(name = "area_sts_Key")
    private Long areaStsKey;

    @Column(name = "reg_seq")
    private Long regSeq;

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

//    @OneToMany
//    @JoinColumns({
//	@JoinColumn(name = "area_seq") ,
//    @JoinColumn(name = "eff_start_dt") })
//    @LazyCollection(LazyCollectionOption.FALSE)
//	private List<MwBrnch> branches = new LinkedList<MwBrnch>();
//
//
//
//	public List<MwBrnch> getBranches() {
//		return branches;
//	}
//
//	public void setBranches(List<MwBrnch> branches) {
//		this.branches = branches;
//	}

    public Long getAreaSeq() {
        return areaSeq;
    }

    public void setAreaSeq(Long areaSeq) {
        this.areaSeq = areaSeq;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getAreaNm() {
        return areaNm;
    }

    public void setAreaNm(String areaNm) {
        this.areaNm = areaNm;
    }

    public String getAreaDscr() {
        return areaDscr;
    }

    public void setAreaDscr(String areaDscr) {
        this.areaDscr = areaDscr;
    }

    public String getAreaPhNum() {
        return areaPhNum;
    }

    public void setAreaPhNum(String areaPhNum) {
        this.areaPhNum = areaPhNum;
    }

    public Long getAreaStsKey() {
        return areaStsKey;
    }

    public void setAreaStsKey(Long areaStsKey) {
        this.areaStsKey = areaStsKey;
    }

    public Long getRegSeq() {
        return regSeq;
    }

    public void setRegSeq(Long regSeq) {
        this.regSeq = regSeq;
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
