package com.idev4.setup.dto;

public class ProductDocumentRelationDto {

    private Long prdDocRelSeq;
    private Long docSeq;
    private Long prdSeq;
    private Boolean mndtryFlg;


    public Boolean getMndtryFlg() {
        return mndtryFlg;
    }

    public void setMndtryFlg(Boolean mndtryFlg) {
        this.mndtryFlg = mndtryFlg;
    }

    public Long getPrdDocRelSeq() {
        return prdDocRelSeq;
    }

    public void setPrdDocRelSeq(Long prdDocRelSeq) {
        this.prdDocRelSeq = prdDocRelSeq;
    }

    public Long getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Long docSeq) {
        this.docSeq = docSeq;
    }

    public Long getPrdSeq() {
        return prdSeq;
    }

    public void setPrdSeq(Long prdSeq) {
        this.prdSeq = prdSeq;
    }

}
