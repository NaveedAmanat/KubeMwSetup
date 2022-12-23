package com.idev4.setup.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A MwReg.
 */
@Entity
@Table(name = "mw_reg")
//@IdClass(MwRegId.class)
public class MwReg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "reg_Cd")
    private String regCd;

    @Column(name = "reg_nm")
    private String regNm;

    @Column(name = "reg_dscr")
    private String regDscr;

    @Column(name = "reg_sts_Key")
    private Long regStsKey;

    @Column(name = "reg_PH_num")
    private Long regPhNum;

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
//	@JoinColumn(name = "reg_seq") ,
//    @JoinColumn(name = "eff_start_dt") })
//    @LazyCollection(LazyCollectionOption.FALSE)
//	private List<MwArea> areas = new LinkedList<MwArea>();
//
//
//
//
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    public List<MwArea> getAreas() {
//		return areas;
//	}
//
//	public void setAreas(List<MwArea> areas) {
//		this.areas = areas;
//	}

    public Long getRegSeq() {
        return regSeq;
    }

    public void setRegSeq(Long regSeq) {
        this.regSeq = regSeq;
    }

    public String getRegCd() {
        return regCd;
    }

    public void setRegCd(String regCd) {
        this.regCd = regCd;
    }

    public String getRegNm() {
        return regNm;
    }

    public void setRegNm(String regNm) {
        this.regNm = regNm;
    }

    public String getRegDscr() {
        return regDscr;
    }

    public void setRegDscr(String regDscr) {
        this.regDscr = regDscr;
    }

    public Long getRegStsKey() {
        return regStsKey;
    }

    public void setRegStsKey(Long regStsKey) {
        this.regStsKey = regStsKey;
    }

    public Long getRegPhNum() {
        return regPhNum;
    }

    public void setRegPhNum(Long regPhNum) {
        this.regPhNum = regPhNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MwReg mwReg = (MwReg) o;
        if (mwReg.getRegSeq() == null || getRegSeq() == null) {
            return false;
        }
        return Objects.equals(getRegSeq(), mwReg.getRegSeq());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getRegSeq());
    }


}
