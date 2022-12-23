package com.idev4.setup.dto;

public class TagsDto {

    private Long tagsSeq;
    private String tagId;
    private String tagNm;
    private String tagDscr;
    private Long svrtyFlgKey;

    public Long getTagsSeq() {
        return tagsSeq;
    }

    public void setTagsSeq(Long tagsSeq) {
        this.tagsSeq = tagsSeq;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagNm() {
        return tagNm;
    }

    public void setTagNm(String tagNm) {
        this.tagNm = tagNm;
    }

    public String getTagDscr() {
        return tagDscr;
    }

    public void setTagDscr(String tagDscr) {
        this.tagDscr = tagDscr;
    }

    public Long getSvrtyFlgKey() {
        return svrtyFlgKey;
    }

    public void setSvrtyFlgKey(Long svrtyFlgKey) {
        this.svrtyFlgKey = svrtyFlgKey;
    }
}
