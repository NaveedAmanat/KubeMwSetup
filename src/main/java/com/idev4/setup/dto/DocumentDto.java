package com.idev4.setup.dto;

public class DocumentDto {

    private Long docSeq;
    private String docId;
    private String docNm;
    private Long docCtgryKey;
    private Long docTypKey;

    public Long getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Long docSeq) {
        this.docSeq = docSeq;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocNm() {
        return docNm;
    }

    public void setDocNm(String docNm) {
        this.docNm = docNm;
    }

    public Long getDocCtgryKey() {
        return docCtgryKey;
    }

    public void setDocCtgryKey(Long docCtgryKey) {
        this.docCtgryKey = docCtgryKey;
    }

    public Long getDocTypKey() {
        return docTypKey;
    }

    public void setDocTypKey(Long docTypKey) {
        this.docTypKey = docTypKey;
    }
}
