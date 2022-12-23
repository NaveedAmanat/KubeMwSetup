package com.idev4.setup.dto;

import java.util.List;

public class AreaDto {

    public long areaSeq;
    public String areaId;
    public String areaName;
    public long areaType;
    public long areaStatus;
    public String areaDescription;
    public String areaAddress;
    public long regionSeq;

    public List<BranchDto> branches;

    public String houseNum;
    public String sreet_area;
    public long community;
    public String village;
    public String otherDetails;
    public long city;

    public long tehsil;
    public long district;
    public long country;
    public long province;
    public long uc;
    public String countryName;
    public String provinceName;
    public String districtName;
    public String tehsilName;
    public String ucName;
    public String cityName;

    public long addrSeq;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (areaSeq ^ (areaSeq >>> 32));
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
        AreaDto other = (AreaDto) obj;
        if (areaSeq != other.areaSeq)
            return false;
        return true;
    }


}
