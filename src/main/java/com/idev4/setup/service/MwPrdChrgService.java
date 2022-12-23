package com.idev4.setup.service;

import com.idev4.setup.domain.MwPrdChrg;
import com.idev4.setup.domain.MwPrdChrgSlb;
import com.idev4.setup.dto.PrdChrgSlbDto;
import com.idev4.setup.dto.ProductChargesDto;
import com.idev4.setup.repository.MwPrdChrgRepository;
import com.idev4.setup.repository.MwPrdChrgSlbRepository;
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
public class MwPrdChrgService {

    private final Logger log = LoggerFactory.getLogger(MwPrdChrgService.class);

    private final MwPrdChrgRepository mwPrdChrgRepository;

    private final MwTypsService mwTypsService;

    private final MwPrdChrgSlbRepository mwPrdChrgSlbRepository;

    public MwPrdChrgService(MwPrdChrgRepository mwPrdChrgRepository, MwTypsService mwTypsService,
                            MwPrdChrgSlbRepository mwPrdChrgSlbRepository) {
        this.mwPrdChrgRepository = mwPrdChrgRepository;
        this.mwTypsService = mwTypsService;
        this.mwPrdChrgSlbRepository = mwPrdChrgSlbRepository;
    }

    public MwPrdChrg save(MwPrdChrg mwPrdChrg) {
        log.debug("Request to save MwPrdChrg : {}", mwPrdChrg);
        return mwPrdChrgRepository.save(mwPrdChrg);
    }

    @Transactional(readOnly = true)
    public ProductChargesDto findOne(Long prdChrgSeq) {
        log.debug("Request to get MwPrdChrg : {}", prdChrgSeq);

        MwPrdChrg mwPrdChrg = mwPrdChrgRepository.findOneByPrdChrgSeqAndCrntRecFlg(prdChrgSeq, true);

        ProductChargesDto dto = new ProductChargesDto();

        dto.setPrdChrgSeq(mwPrdChrg.getPrdChrgSeq());
        dto.setPrdSeq(mwPrdChrg.getPrdSeq());
        dto.setRulSeq(mwPrdChrg.getRulSeq());
        dto.setChrgCalcTypKey(mwPrdChrg.getChrgCalcTypKey());
        dto.setChrgVal(mwPrdChrg.getChrgVal());
        dto.setUpfrontFlg(mwPrdChrg.getUpfrontFlg());
        dto.setSgrtInstNum(mwPrdChrg.getSgrtInstNum());
        dto.setAdjustRoundingFlg(mwPrdChrg.getAdjustRoundingFlg());
        dto.setChrgTypSeq(mwPrdChrg.getChrgTypSeq());
        dto.setChargeName((mwTypsService.findOne(mwPrdChrg.getChrgTypSeq())).getTypStr());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProductChargesDto> findAllByPrdChrgSeq(Long prdChrgSeq) {
        log.debug("Request to get MwPrdChrg : {}", prdChrgSeq);
        List<ProductChargesDto> productChargesDtos = new ArrayList<ProductChargesDto>();

        List<MwPrdChrg> mwPrdChrgs = mwPrdChrgRepository.findAllByPrdChrgSeq(prdChrgSeq);

        for (MwPrdChrg mwPrdChrg : mwPrdChrgs) {
            ProductChargesDto dto = new ProductChargesDto();
            dto.setPrdChrgSeq(mwPrdChrg.getPrdChrgSeq());
            dto.setPrdSeq(mwPrdChrg.getPrdSeq());
            dto.setRulSeq(mwPrdChrg.getRulSeq());
            dto.setChrgCalcTypKey(mwPrdChrg.getChrgCalcTypKey());
            dto.setChrgVal(mwPrdChrg.getChrgVal());
            dto.setUpfrontFlg(mwPrdChrg.getUpfrontFlg());
            dto.setSgrtInstNum(mwPrdChrg.getSgrtInstNum());
            dto.setAdjustRoundingFlg(mwPrdChrg.getAdjustRoundingFlg());
            dto.setChrgTypSeq(mwPrdChrg.getChrgTypSeq());
            dto.setChargeName((mwTypsService.findOne(mwPrdChrg.getChrgTypSeq())).getTypStr());

            productChargesDtos.add(dto);

        }
        return productChargesDtos;
    }

    @Transactional(readOnly = true)
    public List<ProductChargesDto> findAllByCrntRecFlg() {
        log.debug("Request to get All MwPrdChrg : {}");

        List<ProductChargesDto> productChargesDtos = new ArrayList<ProductChargesDto>();

        List<MwPrdChrg> mwPrdChrgs = mwPrdChrgRepository.findAllByCrntRecFlg(true);

        for (MwPrdChrg mwPrdChrg : mwPrdChrgs) {
            ProductChargesDto dto = new ProductChargesDto();
            dto.setPrdChrgSeq(mwPrdChrg.getPrdChrgSeq());
            dto.setPrdSeq(mwPrdChrg.getPrdSeq());
            dto.setRulSeq(mwPrdChrg.getRulSeq());
            dto.setChrgCalcTypKey(mwPrdChrg.getChrgCalcTypKey());
            dto.setChrgVal(mwPrdChrg.getChrgVal());
            dto.setUpfrontFlg(mwPrdChrg.getUpfrontFlg());
            dto.setSgrtInstNum(mwPrdChrg.getSgrtInstNum());
            dto.setAdjustRoundingFlg(mwPrdChrg.getAdjustRoundingFlg());
            dto.setChrgTypSeq(mwPrdChrg.getChrgTypSeq());
            dto.setChargeName((mwTypsService.findOne(mwPrdChrg.getChrgTypSeq())).getTypStr());

            productChargesDtos.add(dto);

        }
        return productChargesDtos;
    }

    @Transactional(readOnly = true)
    public List<ProductChargesDto> findAllByPrdSeqAndCrntRecFlg(Long prdSeq) {
        log.debug("Request to get All MwPrdChrg : {}");
        List<ProductChargesDto> productChargesDtos = new ArrayList<ProductChargesDto>();

        List<MwPrdChrg> mwPrdChrgs = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(prdSeq, true, false);

        for (MwPrdChrg mwPrdChrg : mwPrdChrgs) {
            ProductChargesDto dto = new ProductChargesDto();
            dto.setPrdChrgSeq(mwPrdChrg.getPrdChrgSeq());
            dto.setPrdSeq(mwPrdChrg.getPrdSeq());
            dto.setRulSeq(mwPrdChrg.getRulSeq());
            dto.setChrgCalcTypKey(mwPrdChrg.getChrgCalcTypKey());
            dto.setChrgVal(mwPrdChrg.getChrgVal());
            dto.setUpfrontFlg(mwPrdChrg.getUpfrontFlg());
            dto.setSgrtInstNum(mwPrdChrg.getSgrtInstNum());
            dto.setAdjustRoundingFlg(mwPrdChrg.getAdjustRoundingFlg());
            dto.setChrgTypSeq(mwPrdChrg.getChrgTypSeq());
            if (mwTypsService.findOne(mwPrdChrg.getChrgTypSeq()) != null)
                dto.setChargeName((mwTypsService.findOne(mwPrdChrg.getChrgTypSeq())).getTypStr());

            List<MwPrdChrgSlb> slbs = mwPrdChrgSlbRepository.findAllByPrdChrgSeqAndCrntRecFlg(mwPrdChrg.getPrdChrgSeq(), true);
            slbs.forEach(slb -> {
                PrdChrgSlbDto sdto = new PrdChrgSlbDto();
                if (dto.slbs == null)
                    dto.slbs = new ArrayList<>();
                sdto.domainToDto(slb);
                dto.slbs.add(sdto);
            });
            productChargesDtos.add(dto);

        }
        return productChargesDtos;
    }

    public boolean delete(Long prdChrgSeq) {

        MwPrdChrg prdChrg = mwPrdChrgRepository.findOneByPrdChrgSeqAndCrntRecFlg(prdChrgSeq, true);
        prdChrg.setCrntRecFlg(false);
        prdChrg.setDelFlg(true);
        prdChrg.setLastUpdDt(Instant.now());
        mwPrdChrgRepository.save(prdChrg);
        return true;
    }

    public MwPrdChrg addNewPrdChrg(ProductChargesDto dto, String currUser) {
        long seq = SequenceFinder.findNextVal(Sequences.PRD_CHRG_SEQ);
        MwPrdChrg prdChrg = new MwPrdChrg();
        Instant currIns = Instant.now();

        prdChrg.setPrdChrgSeq(seq);
        prdChrg.setPrdSeq(dto.getPrdSeq());
        prdChrg.setRulSeq(dto.getRulSeq());
        prdChrg.setChrgCalcTypKey(dto.getChrgTypSeq());
        prdChrg.setChrgCalcTypKey(dto.getChrgCalcTypKey());
        prdChrg.setChrgVal(dto.getChrgVal());
        prdChrg.setUpfrontFlg(dto.getUpfrontFlg());
        prdChrg.setSgrtInstNum(dto.getSgrtInstNum());
        prdChrg.setAdjustRoundingFlg(dto.getAdjustRoundingFlg());
        prdChrg.setChrgTypSeq(dto.getChrgTypSeq());
        prdChrg.setCrtdBy(currUser);
        prdChrg.setCrtdDt(currIns);
        prdChrg.setLastUpdBy(currUser);
        prdChrg.setLastUpdDt(currIns);
        prdChrg.setDelFlg(false);
        prdChrg.setEffStartDt(currIns);
        prdChrg.setCrntRecFlg(true);

        return mwPrdChrgRepository.save(prdChrg);

    }

    @Transactional
    public MwPrdChrg updateExistingProductCharges(ProductChargesDto dto, String currUser) {
        MwPrdChrg prdChrg = mwPrdChrgRepository.findOneByPrdChrgSeqAndCrntRecFlg(dto.getPrdChrgSeq(), true);
        Instant currIns = Instant.now();
        if (prdChrg == null) {
            return null;
        }

        prdChrg.setLastUpdDt(currIns);
        prdChrg.setLastUpdBy(currUser);
        prdChrg.setChrgTypSeq(dto.getChrgTypSeq());
        prdChrg.setChrgCalcTypKey(dto.getChrgCalcTypKey());
        prdChrg.setChrgVal(dto.getChrgVal());
        prdChrg.setSgrtInstNum(dto.getSgrtInstNum());
        prdChrg.setUpfrontFlg(dto.getUpfrontFlg());
        prdChrg.setAdjustRoundingFlg(dto.getAdjustRoundingFlg());

        return mwPrdChrgRepository.save(prdChrg);

    }

    public PrdChrgSlbDto addNewPrdChrgSlb(PrdChrgSlbDto dto, String user) {
        MwPrdChrgSlb slb = new MwPrdChrgSlb();
        slb.setCrntRecFlg(true);
        slb.setCrtdBy(user);
        slb.setCrtdDt(Instant.now());
        slb.setDelFlg(false);
        slb.setEffStartDt(Instant.now());
        slb.setLastUpdDt(Instant.now());
        slb.setLastUpdBy(user);
        slb.setPrdChrgSeq(dto.getPrdChrgSeq());
        slb.setPrdChrgSlbSeq(SequenceFinder.findNextVal(Sequences.PRD_CHRG_SLB_SEQ));
        slb.setStartLmt(dto.getStartLmt());
        slb.setEndLmt(dto.getEndLmt());
        slb.setVal(dto.getVal());
        dto.setPrdChrgSlbSeq(slb.getPrdChrgSlbSeq());
        mwPrdChrgSlbRepository.save(slb);
        return dto;
    }

    public PrdChrgSlbDto editPrdChrgSlb(PrdChrgSlbDto dto, String user) {

        MwPrdChrgSlb exSlb = mwPrdChrgSlbRepository.findOneByPrdChrgSlbSeqAndCrntRecFlg(dto.getPrdChrgSlbSeq(), true);
        if (exSlb == null)
            return null;

        exSlb.setLastUpdBy(user);
        exSlb.setLastUpdDt(Instant.now());
        exSlb.setPrdChrgSeq(dto.getPrdChrgSeq());
        exSlb.setStartLmt(dto.getStartLmt());
        exSlb.setEndLmt(dto.getEndLmt());
        exSlb.setVal(dto.getVal());
        mwPrdChrgSlbRepository.save(exSlb);
        return dto;


    }

    public PrdChrgSlbDto deletePrdChrgSlb(PrdChrgSlbDto dto, String user) {
        MwPrdChrgSlb exSlb = mwPrdChrgSlbRepository.findOneByPrdChrgSlbSeqAndCrntRecFlg(dto.getPrdChrgSlbSeq(), true);
        if (exSlb == null)
            return null;
        exSlb.setCrntRecFlg(false);
        exSlb.setDelFlg(true);
        exSlb.setLastUpdBy(user);
        exSlb.setLastUpdDt(Instant.now());
        exSlb.setEffEndDt(Instant.now());

        mwPrdChrgSlbRepository.save(exSlb);
        return dto;
    }

}
