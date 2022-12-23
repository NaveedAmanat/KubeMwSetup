package com.idev4.setup.dto;

import java.util.List;

public class BranchDto {

    public long branchSeq;
    public String branchCode;
    public String branchName;
    public long branchType;
    public long branchCategory;
    public long branchStatus;
    public String branchDescription;
    public String branchAddress;
    public long areaSeq;
    public Long brnchTypKey;

    public List<PortDto> portfolio;

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
        result = prime * result + (int) (branchSeq ^ (branchSeq >>> 32));
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
        BranchDto other = (BranchDto) obj;
        if (branchSeq != other.branchSeq)
            return false;
        return true;
    }


}
