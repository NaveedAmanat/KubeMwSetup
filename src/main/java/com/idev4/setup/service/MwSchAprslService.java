package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.*;
import com.idev4.setup.repository.MwSchAprslRepository;
import com.idev4.setup.repository.MwSchAtndRepository;
import com.idev4.setup.repository.MwSchGrdRepository;
import com.idev4.setup.repository.MwSchQltyChkRepository;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class MwSchAprslService {

    private final Logger log = LoggerFactory.getLogger(MwSchAprslService.class);

    private final MwSchAprslRepository mwSchAprslRepository;
    private final MwSchAtndRepository mwSchAtndRepository;
    private final MwSchGrdRepository mwSchGrdRepository;
    private final MwAddrService mwAddrService;
    private final MwAddrRelService mwAddrRelService;
    private final MwSchQltyChkRepository mwSchQltyChkRepository;
    private final MwQstService mwQstService;
    private final MwRefCdValService mwRefCdValService;

    public MwSchAprslService(MwSchAprslRepository mwSchAprslRepository, MwSchGrdRepository mwSchGrdRepository,
                             MwSchAtndRepository mwSchAtndRepository,
                             MwSchQltyChkRepository mwSchQltyChkRepository,
                             MwAddrRelService mwAddrRelService,
                             MwAddrService mwAddrService,
                             MwAnswrService mwAnswrService,
                             MwQstService mwQstService,
                             MwRefCdValService mwRefCdValService
    ) {

        this.mwSchAprslRepository = mwSchAprslRepository;
        this.mwSchAtndRepository = mwSchAtndRepository;
        this.mwSchGrdRepository = mwSchGrdRepository;
        this.mwAddrService = mwAddrService;
        this.mwAddrRelService = mwAddrRelService;
        this.mwSchQltyChkRepository = mwSchQltyChkRepository;
        this.mwQstService = mwQstService;
        this.mwRefCdValService = mwRefCdValService;
    }

    @Transactional
    public SchoolAppraisalDto addNewSchAprsl(SchoolAppraisalDto dto, String currUser) {

        Instant currIns = Instant.now();
        long schoolAppraisalSeq = SequenceFinder.findNextVal(Sequences.SCH_APRSL_SEQ);
        dto.setSchAprslSeq(schoolAppraisalSeq);
        MwSchAprsl mwSchAprsl = new MwSchAprsl();

        mwSchAprsl.setSchAprslSeq(schoolAppraisalSeq);
        mwSchAprsl.setSchNm(dto.getSchNm());
        mwSchAprsl.setSchRegdFlg(dto.getSchRegdFlg());
        mwSchAprsl.setPefSptFlg(dto.getPefSptFlg());
        mwSchAprsl.setSchArea(dto.getSchArea());
        mwSchAprsl.setSchAge(dto.getSchAge());
        mwSchAprsl.setSchOwnTypKey(dto.getSchOwnTypKey());
        mwSchAprsl.setRelWthOwnKey(dto.getRelWthOwnKey());
        mwSchAprsl.setSchPpalKey(dto.getSchPpalKey());
        mwSchAprsl.setBldngOwnKey(dto.getBldngOwnKey());
        mwSchAprsl.setSchTypKey(dto.getSchTypKey());
        mwSchAprsl.setSchLvlKey(dto.getSchLvlKey());
        mwSchAprsl.setSchMedmKey(dto.getSchMedmKey());
        mwSchAprsl.setSchAreaUntKey(dto.getSchAreaUntKey());
        mwSchAprsl.setCrtdBy(currUser);
        mwSchAprsl.setCrtdDt(currIns);
        mwSchAprsl.setLastUpdBy(currUser);
        mwSchAprsl.setLastUpdDt(currIns);
        mwSchAprsl.setDelFlg(false);
        mwSchAprsl.setEffStartDt(currIns);
        mwSchAprsl.setCrntRecFlg(true);
        mwSchAprsl.setLoanAppSeq(dto.getLoanAppSeq());
        ;

        mwSchAprslRepository.save(mwSchAprsl);

        //address
        AddressDto addressDto = new AddressDto();

        addressDto.schAprslSeq = schoolAppraisalSeq;
        addressDto.houseNum = dto.getAddressDto().houseNum;
        addressDto.sreet_area = dto.getAddressDto().sreet_area;
        addressDto.community = dto.getAddressDto().community;
        addressDto.village = dto.getAddressDto().village;
        addressDto.otherDetails = dto.getAddressDto().otherDetails;
        addressDto.city = dto.getAddressDto().city;
        addressDto.district = dto.getAddressDto().district;
        addressDto.tehsil = dto.getAddressDto().tehsil;
        addressDto.city = dto.getAddressDto().city;
        addressDto.uc = dto.getAddressDto().uc;

        mwAddrService.saveAddress(addressDto, currUser, "SchoolAppraisal");

        //Attendance
        MwSchAtnd mwSchAtnd = new MwSchAtnd();

        long schoolAtndSeq = SequenceFinder.findNextVal(Sequences.SCH_ATND_SEQ);

        mwSchAtnd.setSchAtndSeq(schoolAtndSeq);
        mwSchAtnd.setSchAprslSeq(schoolAppraisalSeq);
        mwSchAtnd.setTotMaleTchrs(dto.getTotMaleTchrs());
        mwSchAtnd.setTotFemTchrs(dto.getTotFemTchrs());
        mwSchAtnd.setLastYrDrop(dto.getLastYrDrop());
        mwSchAtnd.setCrtdBy(currUser);
        mwSchAtnd.setCrtdDt(currIns);
        mwSchAtnd.setLastUpdBy(currUser);
        mwSchAtnd.setLastUpdDt(currIns);
        mwSchAtnd.setDelFlg(false);
        mwSchAtnd.setEffStartDt(currIns);
        mwSchAtnd.setCrntRecFlg(true);

        mwSchAtndRepository.save(mwSchAtnd);


        //Grade
        for (SchoolGradeDto schoolGradeDto : dto.getSchoolGradeDtos()) {
            MwSchGrd mwSchGrd = new MwSchGrd();

            long schoolGrdSeq = SequenceFinder.findNextVal(Sequences.SCH_GRD_SEQ);

            mwSchGrd.setSchGrdSeq(schoolGrdSeq);
            mwSchGrd.setTotFemStdnt(schoolGradeDto.getTotFemStdnt());
            mwSchGrd.setTotMaleStdnt(schoolGradeDto.getTotMaleStdnt());
            mwSchGrd.setAvgFee(schoolGradeDto.getAvgFee());
            mwSchGrd.setNoFeeStdnt(schoolGradeDto.getNoFeeStdnt());
            mwSchGrd.setFemStdntPrsnt(schoolGradeDto.getFemStdntPrsnt());
            mwSchGrd.setMaleStdntPrsnt(schoolGradeDto.getMaleStdntPrsnt());
            mwSchGrd.setGrdKey(schoolGradeDto.getGrdKey());
            mwSchGrd.setCrtdBy(currUser);
            mwSchGrd.setCrtdDt(currIns);
            mwSchGrd.setLastUpdBy(currUser);
            mwSchGrd.setLastUpdDt(currIns);
            mwSchGrd.setDelFlg(false);
            mwSchGrd.setEffStartDt(currIns);
            mwSchGrd.setCrntRecFlg(true);
            mwSchGrd.setSchAprslSeq(schoolAppraisalSeq);

            mwSchGrdRepository.save(mwSchGrd);

        }

        //School Quality Check
        for (SchoolQualityCheckDto schoolQualityCheckDto : dto.getSchoolQualityCheckDtos()) {
            MwSchQltyChk mwSchQltyChk = new MwSchQltyChk();

            long schoolQltyChkSeq = SequenceFinder.findNextVal(Sequences.SCH_QLTY_CHK_SEQ);

            mwSchQltyChk.setSchQltyChkSeq(schoolQltyChkSeq);
            mwSchQltyChk.setSchAprslSeq(schoolAppraisalSeq);
            mwSchQltyChk.setQstSeq(schoolQualityCheckDto.getQstSeq());
            mwSchQltyChk.setAnswrSeq(schoolQualityCheckDto.getAnswrSeq());
            mwSchQltyChk.setCrtdBy(currUser);
            mwSchQltyChk.setCrtdDt(currIns);
            mwSchQltyChk.setLastUpdBy(currUser);
            mwSchQltyChk.setLastUpdDt(currIns);
            mwSchQltyChk.setDelFlg(false);
            mwSchQltyChk.setEffStartDt(currIns);
            mwSchQltyChk.setCrntRecFlg(true);

            mwSchQltyChkRepository.save(mwSchQltyChk);
        }

        for (SchoolQuestionsDto questionDto : dto.SchoolQAArray) {
            for (QuestionDto question : questionDto.questions) {
                MwSchQltyChk chk = new MwSchQltyChk();
                chk.setSchQltyChkSeq(SequenceFinder.findNextVal(Sequences.SCH_QLTY_CHK_SEQ));
                chk.setSchAprslSeq(schoolAppraisalSeq);
                chk.setQstSeq(question.questionKey);
                chk.setAnswrSeq(question.answerSeq);
                chk.setCrtdBy(currUser);
                chk.setCrtdDt(currIns);
                chk.setLastUpdBy(currUser);
                chk.setLastUpdDt(currIns);
                chk.setDelFlg(false);
                chk.setEffStartDt(currIns);
                chk.setCrntRecFlg(true);
                mwSchQltyChkRepository.save(chk);
            }
        }
        return dto;
    }

    public SchoolGradeDto schoolGradeDomainToDto(MwSchGrd mwSchGrd) {
        SchoolGradeDto schoolGradeDto = new SchoolGradeDto();
        schoolGradeDto.setSchGrdSeq(mwSchGrd.getSchGrdSeq());
        schoolGradeDto.setTotFemStdnt(mwSchGrd.getTotFemStdnt());
        schoolGradeDto.setTotMaleStdnt(mwSchGrd.getTotMaleStdnt());
        schoolGradeDto.setAvgFee(mwSchGrd.getAvgFee());
        schoolGradeDto.setNoFeeStdnt(mwSchGrd.getNoFeeStdnt());
        schoolGradeDto.setFemStdntPrsnt(mwSchGrd.getFemStdntPrsnt());
        schoolGradeDto.setMaleStdntPrsnt(mwSchGrd.getMaleStdntPrsnt());
        schoolGradeDto.setGrdKey(mwSchGrd.getGrdKey());
        schoolGradeDto.setSchAprslSeq(mwSchGrd.getSchAprslSeq());

        return schoolGradeDto;
    }

    public SchoolQualityCheckDto schoolQualityCheckDomainToDto(MwSchQltyChk mwSchQltyChk) {
        SchoolQualityCheckDto schoolQualityCheckDto = new SchoolQualityCheckDto();
        schoolQualityCheckDto.setSchQltyChkSeq(mwSchQltyChk.getSchQltyChkSeq());
        schoolQualityCheckDto.setSchAprslSeq(mwSchQltyChk.getSchAprslSeq());
        schoolQualityCheckDto.setQstSeq(mwSchQltyChk.getQstSeq());
        schoolQualityCheckDto.setQstCtgryKey(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstCtgryKey());
        schoolQualityCheckDto.setQstCtgry((mwRefCdValService.findOne(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstCtgryKey())).getRefCdDscr());
        schoolQualityCheckDto.setQstStr(mwQstService.findOne(mwSchQltyChk.getQstSeq()).getQstStr());
        schoolQualityCheckDto.setAnswrSeq(mwSchQltyChk.getAnswrSeq());
        return schoolQualityCheckDto;
    }


    @Transactional
    public SchoolAppraisalDto getSchoolAppraisal(long seq) {
        SchoolAppraisalDto dto = new SchoolAppraisalDto();
        MwSchAprsl mwSchAprsl = mwSchAprslRepository.findOneByLoanAppSeqAndDelFlgAndCrntRecFlg(seq, false, true);
        if (mwSchAprsl != null) {
            dto.setSchAprslSeq(mwSchAprsl.getSchAprslSeq());
            dto.setSchNm(mwSchAprsl.getSchNm());
            dto.setSchRegdFlg(mwSchAprsl.getSchRegdFlg());
            dto.setPefSptFlg(mwSchAprsl.getPefSptFlg());
            dto.setSchArea(mwSchAprsl.getSchArea());
            dto.setSchAge(mwSchAprsl.getSchAge());
            dto.setSchOwnTypKey(mwSchAprsl.getSchOwnTypKey());
            dto.setRelWthOwnKey(mwSchAprsl.getRelWthOwnKey());
            dto.setSchPpalKey(mwSchAprsl.getSchPpalKey());
            dto.setBldngOwnKey(mwSchAprsl.getBldngOwnKey());
            dto.setSchTypKey(mwSchAprsl.getSchTypKey());
            dto.setSchLvlKey(mwSchAprsl.getSchLvlKey());
            dto.setSchMedmKey(mwSchAprsl.getSchMedmKey());
            dto.setSchAreaUntKey(mwSchAprsl.getSchAreaUntKey());
            dto.setLoanAppSeq(mwSchAprsl.getLoanAppSeq());

            MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntity(mwSchAprsl.getSchAprslSeq());
            if (mwAddrRel != null) {
                MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());

                if (mwAddr != null) {
                    dto.getAddressDto().addrSeq = mwAddr.getAddrSeq();
                    dto.getAddressDto().houseNum = mwAddr.getHseNum();
                    dto.getAddressDto().sreet_area = mwAddr.getStrt();
                    dto.getAddressDto().otherDetails = mwAddr.getOthDtl();
                    dto.getAddressDto().city = mwAddr.getCitySeq();
                    dto.getAddressDto().community = mwAddr.getCmntySeq();
                    dto.getAddressDto().village = mwAddr.getVlg();

                    MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                    if (mwSchAtnd != null) {
                        dto.setSchAtndSeq(mwSchAtnd.getSchAtndSeq());
                        dto.setSchAprslSeq(mwSchAtnd.getSchAprslSeq());
                        dto.setTotMaleTchrs(mwSchAtnd.getTotMaleTchrs());
                        dto.setTotFemTchrs(mwSchAtnd.getTotFemTchrs());
                        dto.setLastYrDrop(mwSchAtnd.getLastYrDrop());

                        List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                        List<SchoolGradeDto> schoolGradeDtos = new ArrayList<SchoolGradeDto>();
                        if (mwSchGrds != null) {
                            for (MwSchGrd mwSchGrd : mwSchGrds) {
                                schoolGradeDtos.add(schoolGradeDomainToDto(mwSchGrd));
                            }
                            List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                            List<SchoolQualityCheckDto> schoolQualityCheckDtos = new ArrayList<SchoolQualityCheckDto>();
                            if (mwSchQltyChks != null) {
                                for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                                    schoolQualityCheckDtos.add(schoolQualityCheckDomainToDto(mwSchQltyChk));
                                }
                                dto.setSchoolQualityCheckDtos(schoolQualityCheckDtos);
                            } else {
                                return null;
                            }
                            dto.setSchoolGradeDtos(schoolGradeDtos);
                            return dto;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public List<SchoolAppraisalDto> getAllSchoolAppraisal() {
        List<SchoolAppraisalDto> dtos = new ArrayList<>();
        List<MwSchAprsl> mwSchAprsls = mwSchAprslRepository.findAllByCrntRecFlg(true);
        if (mwSchAprsls != null) {
            for (MwSchAprsl mwSchAprsl : mwSchAprsls) {

                SchoolAppraisalDto dto = new SchoolAppraisalDto();
                dto.setSchAprslSeq(mwSchAprsl.getSchAprslSeq());
                dto.setSchNm(mwSchAprsl.getSchNm());
                dto.setSchRegdFlg(mwSchAprsl.getSchRegdFlg());
                dto.setPefSptFlg(mwSchAprsl.getPefSptFlg());
                dto.setSchArea(mwSchAprsl.getSchArea());
                dto.setSchAge(mwSchAprsl.getSchAge());
                dto.setSchOwnTypKey(mwSchAprsl.getSchOwnTypKey());
                dto.setRelWthOwnKey(mwSchAprsl.getRelWthOwnKey());
                dto.setSchPpalKey(mwSchAprsl.getSchPpalKey());
                dto.setBldngOwnKey(mwSchAprsl.getBldngOwnKey());
                dto.setSchTypKey(mwSchAprsl.getSchTypKey());
                dto.setSchLvlKey(mwSchAprsl.getSchLvlKey());
                dto.setSchMedmKey(mwSchAprsl.getSchMedmKey());
                dto.setSchAreaUntKey(mwSchAprsl.getSchAreaUntKey());
                dto.setLoanAppSeq(mwSchAprsl.getLoanAppSeq());

                MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntity(mwSchAprsl.getSchAprslSeq());
                if (mwAddrRel != null) {
                    MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());

                    if (mwAddr != null) {
                        dto.getAddressDto().addrSeq = mwAddr.getAddrSeq();
                        dto.getAddressDto().houseNum = mwAddr.getHseNum();
                        dto.getAddressDto().sreet_area = mwAddr.getStrt();
                        dto.getAddressDto().otherDetails = mwAddr.getOthDtl();
                        dto.getAddressDto().city = mwAddr.getCitySeq();
                        dto.getAddressDto().community = mwAddr.getCmntySeq();
                        dto.getAddressDto().village = mwAddr.getVlg();

                        MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                        if (mwSchAtnd != null) {
                            dto.setSchAtndSeq(mwSchAtnd.getSchAtndSeq());
                            dto.setSchAprslSeq(mwSchAtnd.getSchAprslSeq());
                            dto.setTotMaleTchrs(mwSchAtnd.getTotMaleTchrs());
                            dto.setTotFemTchrs(mwSchAtnd.getTotFemTchrs());
                            dto.setLastYrDrop(mwSchAtnd.getLastYrDrop());

                            List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                            List<SchoolGradeDto> schoolGradeDtos = new ArrayList<SchoolGradeDto>();
                            if (mwSchGrds != null) {
                                for (MwSchGrd mwSchGrd : mwSchGrds) {
                                    schoolGradeDtos.add(this.schoolGradeDomainToDto(mwSchGrd));
                                }
                                List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(mwSchAprsl.getSchAprslSeq(), true);
                                List<SchoolQualityCheckDto> schoolQualityCheckDtos = new ArrayList<SchoolQualityCheckDto>();
                                if (mwSchQltyChks != null) {
                                    for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                                        schoolQualityCheckDtos.add(this.schoolQualityCheckDomainToDto(mwSchQltyChk));
                                    }
                                    dto.setSchoolQualityCheckDtos(schoolQualityCheckDtos);
                                } else {
                                    return null;
                                }
                                dto.setSchoolGradeDtos(schoolGradeDtos);
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
                dtos.add(dto);
            }
        } else {
            return null;
        }
        return dtos;
    }

    @Transactional
    public boolean delete(Long seq) {

        MwSchAprsl mwSchAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(seq, true);
        if (mwSchAprsl != null) {
            MwAddrRel mwAddrRel = mwAddrRelService.getAddressRelationByEntity(seq);
            if (mwAddrRel != null) {
                mwAddrRelService.deleteByEntity(mwAddrRel);

                MwAddr mwAddr = mwAddrService.findOneByAddrSeq(mwAddrRel.getAddrSeq());
                if (mwAddr != null) {
                    mwAddrService.deleteByEntity(mwAddr);

                    MwSchAtnd mwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(seq, true);
                    if (mwSchAtnd != null) {
                        List<MwSchGrd> mwSchGrds = mwSchGrdRepository.findAllBySchAprslSeqAndCrntRecFlg(seq, true);
                        if (mwSchGrds != null) {
                            List<MwSchQltyChk> mwSchQltyChks = mwSchQltyChkRepository.findAllBySchAprslSeqAndCrntRecFlg(seq, true);
                            if (mwSchQltyChks != null) {
                                for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                                    mwSchQltyChkRepository.delete(mwSchQltyChk);
                                }
                                for (MwSchGrd mwSchGrd : mwSchGrds) {
                                    mwSchGrdRepository.delete(mwSchGrd);
                                }
                                mwSchAtndRepository.delete(mwSchAtnd);
                                mwSchAprslRepository.delete(mwSchAprsl);

                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    @Transactional
    public long updateSchoolAppraisal(SchoolAppraisalDto dto, String currUser) {

        Instant currIns = Instant.now();
        new MwSchAprsl();
        AddressDto addressDto = new AddressDto();
        new MwSchAtnd();
        MwSchAtnd exMwSchAtnd = new MwSchAtnd();
        new ArrayList<>();
        List<MwSchGrd> mwSchGrds = new ArrayList<>();
        new ArrayList<>();
        List<MwSchQltyChk> mwSchQltyChks = new ArrayList<>();


        MwSchAprsl exMwSchAprsl = mwSchAprslRepository.findOneBySchAprslSeqAndCrntRecFlg(dto.getSchAprslSeq(), true);
        if (exMwSchAprsl != null) {
            exMwSchAprsl.setSchAprslSeq(exMwSchAprsl.getSchAprslSeq());
            exMwSchAprsl.setSchNm(dto.getSchNm());
            exMwSchAprsl.setSchRegdFlg(dto.getSchRegdFlg());
            exMwSchAprsl.setPefSptFlg(dto.getPefSptFlg());
            exMwSchAprsl.setSchArea(dto.getSchArea());
            exMwSchAprsl.setSchAge(dto.getSchAge());
            exMwSchAprsl.setSchOwnTypKey(dto.getSchOwnTypKey());
            exMwSchAprsl.setRelWthOwnKey(dto.getRelWthOwnKey());
            exMwSchAprsl.setSchPpalKey(dto.getSchPpalKey());
            exMwSchAprsl.setBldngOwnKey(dto.getBldngOwnKey());
            exMwSchAprsl.setSchTypKey(dto.getSchTypKey());
            exMwSchAprsl.setSchLvlKey(dto.getSchLvlKey());
            exMwSchAprsl.setSchMedmKey(dto.getSchMedmKey());
            exMwSchAprsl.setSchAreaUntKey(dto.getSchAreaUntKey());
            exMwSchAprsl.setLoanAppSeq(dto.getLoanAppSeq());
            exMwSchAprsl.setCrtdBy(exMwSchAprsl.getCrtdBy());
            exMwSchAprsl.setCrtdDt(exMwSchAprsl.getCrtdDt());
            exMwSchAprsl.setLastUpdBy(currUser);
            exMwSchAprsl.setLastUpdDt(currIns);
            exMwSchAprsl.setDelFlg(false);
            exMwSchAprsl.setEffStartDt(exMwSchAprsl.getEffStartDt());
            exMwSchAprsl.setCrntRecFlg(true);

            MwAddr exMwAddr = mwAddrService.findOneByAddrSeq(dto.getAddressDto().addrSeq);
            if (exMwAddr != null) {
                exMwAddr.setAddrSeq(dto.getAddressDto().addrSeq);
                exMwAddr.setCitySeq(dto.getAddressDto().city);
                exMwAddr.setCmntySeq(dto.getAddressDto().community);
                exMwAddr.setHseNum(dto.getAddressDto().houseNum);
                exMwAddr.setOthDtl(dto.getAddressDto().otherDetails);
                exMwAddr.setStrt(dto.getAddressDto().sreet_area);
                exMwAddr.setVlg(dto.getAddressDto().village);
                exMwAddr.setLongitude(dto.getAddressDto().lon);
                exMwAddr.setLatitude(dto.getAddressDto().lat);

                exMwSchAtnd = mwSchAtndRepository.findOneBySchAprslSeqAndCrntRecFlg(exMwSchAprsl.getSchAprslSeq(), true);
                if (exMwSchAtnd != null) {
                    exMwSchAtnd.setSchAtndSeq(exMwSchAtnd.getSchAtndSeq());
                    exMwSchAtnd.setSchAprslSeq(exMwSchAtnd.getSchAprslSeq());
                    exMwSchAtnd.setTotMaleTchrs(dto.getTotMaleTchrs());
                    exMwSchAtnd.setTotFemTchrs(dto.getTotFemTchrs());
                    exMwSchAtnd.setLastYrDrop(dto.getLastYrDrop());
                    exMwSchAtnd.setCrtdBy(exMwSchAtnd.getCrtdBy());
                    exMwSchAtnd.setCrtdDt(exMwSchAtnd.getCrtdDt());
                    exMwSchAtnd.setLastUpdBy(currUser);
                    exMwSchAtnd.setLastUpdDt(currIns);
                    exMwSchAtnd.setDelFlg(false);
                    exMwSchAtnd.setEffStartDt(exMwSchAtnd.getEffStartDt());
                    exMwSchAtnd.setCrntRecFlg(true);

                    for (SchoolGradeDto schoolGradeDto : dto.getSchoolGradeDtos()) {
                        if (schoolGradeDto.getGradeFlag() != null && schoolGradeDto.getGradeFlag().equals("u")) {
                            MwSchGrd mwSchGrd = mwSchGrdRepository.findOneBySchGrdSeqAndCrntRecFlg(schoolGradeDto.getSchGrdSeq(), true);

                            mwSchGrd.setTotFemStdnt(schoolGradeDto.getTotFemStdnt());
                            mwSchGrd.setTotMaleStdnt(schoolGradeDto.getTotMaleStdnt());
                            mwSchGrd.setAvgFee(schoolGradeDto.getAvgFee());
                            mwSchGrd.setNoFeeStdnt(schoolGradeDto.getNoFeeStdnt());
                            mwSchGrd.setFemStdntPrsnt(schoolGradeDto.getFemStdntPrsnt());
                            mwSchGrd.setMaleStdntPrsnt(schoolGradeDto.getMaleStdntPrsnt());
                            mwSchGrd.setGrdKey(schoolGradeDto.getGrdKey());
                            mwSchGrd.setSchAprslSeq(dto.getSchAprslSeq());
                            mwSchGrd.setCrtdBy(mwSchGrd.getCrtdBy());
                            mwSchGrd.setCrtdDt(mwSchGrd.getCrtdDt());
                            mwSchGrd.setLastUpdBy(currUser);
                            mwSchGrd.setLastUpdDt(currIns);
                            mwSchGrd.setDelFlg(false);
                            mwSchGrd.setEffStartDt(mwSchGrd.getEffStartDt());
                            mwSchGrd.setCrntRecFlg(true);
                            mwSchGrds.add(mwSchGrd);
                        } else if (schoolGradeDto.getGradeFlag() != null && schoolGradeDto.getGradeFlag().equals("a")) {
                            MwSchGrd mwSchGrd = new MwSchGrd();
                            long grdSeq = SequenceFinder.findNextVal(Sequences.SCH_GRD_SEQ);

                            mwSchGrd.setSchGrdSeq(grdSeq);
                            mwSchGrd.setTotFemStdnt(schoolGradeDto.getTotFemStdnt());
                            mwSchGrd.setTotMaleStdnt(schoolGradeDto.getTotMaleStdnt());
                            mwSchGrd.setAvgFee(schoolGradeDto.getAvgFee());
                            mwSchGrd.setNoFeeStdnt(schoolGradeDto.getNoFeeStdnt());
                            mwSchGrd.setFemStdntPrsnt(schoolGradeDto.getFemStdntPrsnt());
                            mwSchGrd.setMaleStdntPrsnt(schoolGradeDto.getMaleStdntPrsnt());
                            mwSchGrd.setGrdKey(schoolGradeDto.getGrdKey());
                            mwSchGrd.setSchAprslSeq(dto.getSchAprslSeq());
                            mwSchGrd.setCrtdBy(currUser);
                            mwSchGrd.setCrtdDt(currIns);
                            mwSchGrd.setLastUpdBy(currUser);
                            mwSchGrd.setLastUpdDt(currIns);
                            mwSchGrd.setDelFlg(false);
                            mwSchGrd.setEffStartDt(currIns);
                            mwSchGrd.setCrntRecFlg(true);
                            mwSchGrds.add(mwSchGrd);
                        } else if (schoolGradeDto.getGradeFlag() != null && schoolGradeDto.getGradeFlag().equals("d")) {
                            mwSchGrdRepository.delete(mwSchGrdRepository.findOneBySchGrdSeqAndCrntRecFlg(schoolGradeDto.getSchGrdSeq(), true));
                        }
                    }
                    for (MwSchGrd mwSchGrd : mwSchGrds) {
                        mwSchGrdRepository.save(mwSchGrd);
                    }

                    for (SchoolQualityCheckDto schoolQualityCheckDto : dto.getSchoolQualityCheckDtos()) {
                        if (schoolQualityCheckDto.getSchQltyChkFlag() != null && schoolQualityCheckDto.getSchQltyChkFlag().equals("u")) {
                            MwSchQltyChk mwSchQltyChk = mwSchQltyChkRepository.findOneBySchQltyChkSeqAndCrntRecFlg(schoolQualityCheckDto.getSchQltyChkSeq(), true);
                            mwSchQltyChk.setSchAprslSeq(schoolQualityCheckDto.getSchAprslSeq());
                            mwSchQltyChk.setQstSeq(schoolQualityCheckDto.getQstSeq());
                            mwSchQltyChk.setAnswrSeq(schoolQualityCheckDto.getAnswrSeq());
                            mwSchQltyChk.setCrtdBy(mwSchQltyChk.getCrtdBy());
                            mwSchQltyChk.setCrtdDt(mwSchQltyChk.getCrtdDt());
                            mwSchQltyChk.setLastUpdBy(currUser);
                            mwSchQltyChk.setLastUpdDt(currIns);
                            mwSchQltyChk.setDelFlg(false);
                            mwSchQltyChk.setEffStartDt(mwSchQltyChk.getEffStartDt());
                            mwSchQltyChk.setCrntRecFlg(true);
                            mwSchQltyChks.add(mwSchQltyChk);
                        }
                    }

                    for (MwSchQltyChk mwSchQltyChk : mwSchQltyChks) {
                        mwSchQltyChkRepository.save(mwSchQltyChk);
                    }

                    mwSchAtndRepository.save(exMwSchAtnd);
                    mwAddrService.save(exMwAddr);
                    mwSchAprslRepository.save(exMwSchAprsl);

                    return dto.getSchAprslSeq();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
