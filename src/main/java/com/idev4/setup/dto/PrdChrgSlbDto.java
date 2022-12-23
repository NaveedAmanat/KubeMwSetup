package com.idev4.setup.dto;

import com.idev4.setup.domain.MwPrdChrgSlb;

public class PrdChrgSlbDto {

    public Long prdChrgSlbSeq;

    public Long prdChrgSeq;

    public Double startLmt;

    public Double endLmt;

    public Double val;

    public Boolean delFlg;

    public Boolean crntRecFlg;

    public void domainToDto(MwPrdChrgSlb slb) {
        this.prdChrgSlbSeq = slb.getPrdChrgSlbSeq();
        this.prdChrgSeq = slb.getPrdChrgSeq();
        this.startLmt = slb.getStartLmt();
        this.val = slb.getVal();
        this.delFlg = slb.getDelFlg();
        this.crntRecFlg = slb.getCrntRecFlg();
    }

    public Long getPrdChrgSlbSeq() {
        return prdChrgSlbSeq;
    }

    public void setPrdChrgSlbSeq(Long prdChrgSlbSeq) {
        this.prdChrgSlbSeq = prdChrgSlbSeq;
    }

    public Long getPrdChrgSeq() {
        return prdChrgSeq;
    }

    public void setPrdChrgSeq(Long prdChrgSeq) {
        this.prdChrgSeq = prdChrgSeq;
    }

    public Double getStartLmt() {
        return startLmt;
    }

    public void setStartLmt(Double startLmt) {
        this.startLmt = startLmt;
    }

    public Double getEndLmt() {
        return endLmt;
    }

    public void setEndLmt(Double endLmt) {
        this.endLmt = endLmt;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }
}
