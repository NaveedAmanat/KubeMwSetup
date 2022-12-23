package com.idev4.setup.dto;

import java.util.List;

public class RegionDto {

    public long regionSeq;
    public String regionCode;
    public String regionName;
    public long regionType;
    public long regionStatus;
    public String regionDescription;
    public String regionAddress;

    public List<AreaDto> areas;

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
        result = prime * result + (int) (regionSeq ^ (regionSeq >>> 32));
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
        RegionDto other = (RegionDto) obj;
        if (regionSeq != other.regionSeq)
            return false;
        return true;
    }


}
