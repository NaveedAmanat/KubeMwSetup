package com.idev4.setup.dto;

public class CommunityDto {

    public long comSeq;
    public String cmntyName;
    public String cmntyDescription;
    public String cmntyCode;
    public long cmntyStatus;
    public long portfolioSeq;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (comSeq ^ (comSeq >>> 32));
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
        CommunityDto other = (CommunityDto) obj;
        if (comSeq != other.comSeq)
            return false;
        return true;
    }


}
