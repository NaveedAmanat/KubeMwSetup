package com.idev4.setup.domain;

import com.idev4.setup.ids.RegionWiseOutreachId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "REGION_WISE_OUTREACH")
@IdClass(RegionWiseOutreachId.class)
public class MwRegionWiseOutreach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "OUTREACH_MONTH")
    private Instant outreachMonth;

    @Column(name = "OPENING")
    private Long opening;

    @Column(name = "TARGETS")
    private Long targets;

    @Column(name = "MATURING_LOANS")
    private Long maturingLoans;

    @Column(name = "CLOSING")
    private Long closing;

    @Column(name = "TRANS_DATE")
    private Instant transDate;

    @Id
    @Column(name = "REGION_CD")
    private Long regionCd;

    public MwRegionWiseOutreach() {
    }

    public MwRegionWiseOutreach(Instant outreachMonth, Long opening, Long targets, Long maturingLoans, Long closing, Instant transDate, Long regionCd) {
        this.outreachMonth = outreachMonth;
        this.opening = opening;
        this.targets = targets;
        this.maturingLoans = maturingLoans;
        this.closing = closing;
        this.transDate = transDate;
        this.regionCd = regionCd;
    }

    public Instant getOutreachMonth() {
        return outreachMonth;
    }

    public void setOutreachMonth(Instant outreachMonth) {
        this.outreachMonth = outreachMonth;
    }

    public Long getOpening() {
        return opening;
    }

    public void setOpening(Long opening) {
        this.opening = opening;
    }

    public Long getTargets() {
        return targets;
    }

    public void setTargets(Long targets) {
        this.targets = targets;
    }

    public Long getMaturingLoans() {
        return maturingLoans;
    }

    public void setMaturingLoans(Long maturingLoans) {
        this.maturingLoans = maturingLoans;
    }

    public Long getClosing() {
        return closing;
    }

    public void setClosing(Long closing) {
        this.closing = closing;
    }

    public Instant getTransDate() {
        return transDate;
    }

    public void setTransDate(Instant transDate) {
        this.transDate = transDate;
    }

    public Long getRegionCd() {
        return regionCd;
    }

    public void setRegionCd(Long regionCd) {
        this.regionCd = regionCd;
    }
}
