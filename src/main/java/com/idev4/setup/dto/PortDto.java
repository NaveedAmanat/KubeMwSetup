package com.idev4.setup.dto;

import java.util.List;

public class PortDto {

    public long portfolioSeq;
    public long portSeq;
    public String portfolioId;
    public String portfolioName;
    public long portfolioStatus;
    //Added by Areeba - 07-06-2022
    public long portfolioType;
    public String portfolioComment;
    public long branchSeq;

    public List<CommunityDto> communities;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (portfolioSeq ^ (portfolioSeq >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PortDto other = (PortDto) obj;
        if (portfolioSeq != other.portfolioSeq)
            return false;
        return true;
    }

    public long getPortfolioSeq() {
        return portfolioSeq;
    }

    public void setPortfolioSeq(long portfolioSeq) {
        this.portfolioSeq = portfolioSeq;
    }

    public long getPortSeq() {
        return portSeq;
    }

    public void setPortSeq(long portSeq) {
        this.portSeq = portSeq;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public long getPortfolioStatus() {
        return portfolioStatus;
    }

    public void setPortfolioStatus(long portfolioStatus) {
        this.portfolioStatus = portfolioStatus;
    }

    public String getPortfolioComment() {
        return portfolioComment;
    }

    public void setPortfolioComment(String portfolioComment) {
        this.portfolioComment = portfolioComment;
    }

    public long getBranchSeq() {
        return branchSeq;
    }

    public void setBranchSeq(long branchSeq) {
        this.branchSeq = branchSeq;
    }

    public List<CommunityDto> getCommunities() {
        return communities;
    }

    public void setCommunities(List<CommunityDto> communities) {
        this.communities = communities;
    }

    public long getPortfolioType() {
        return portfolioType;
    }

    public void setPortfolioType(long portfolioType) {
        this.portfolioType = portfolioType;
    }
}
