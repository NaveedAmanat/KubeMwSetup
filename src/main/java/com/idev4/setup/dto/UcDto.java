package com.idev4.setup.dto;

public class UcDto {

    public long ucSeq;
    public String ucCode;
    public String ucName;
    public String ucDescription;
    public long statusKey;
    public long thslSeq;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (ucSeq ^ (ucSeq >>> 32));
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
        UcDto other = (UcDto) obj;
        if (ucSeq != other.ucSeq)
            return false;
        return true;
    }


}
