package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.Charges;
import com.idev4.setup.dto.GlAccount;
import com.idev4.setup.dto.ProductDto;
import com.idev4.setup.repository.*;
import com.idev4.setup.web.rest.util.Queries;
import com.idev4.setup.web.rest.util.SequenceFinder;
import com.idev4.setup.web.rest.util.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing MwPrd.
 */
@Service
@Transactional
public class MwPrdService {

    private final Logger log = LoggerFactory.getLogger(MwPrdService.class);

    private final MwPrdRepository mwPrdRepository;

    private final MwPortRepository mwPortRepository;

    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;

    private final MwBrnchPrdRelRepository mwBrnchPrdRelRepository;

    private final MwRulRepository mwRulRepository;

    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;

    private final MwRefCdValRepository mwRefCdValRepository;

    private final MwPrdChrgRepository mwPrdChrgRepository;

    private final MwPrdChrgSlbRepository mwPrdChrgSlbRepository;

    private final EntityManager em;

    public MwPrdService(MwPrdRepository mwPrdRepository, EntityManager em, MwPortRepository mwPortRepository,
                        MwPrdPpalLmtRepository mwPrdPpalLmtRepository, MwPrdLoanTrmRepository mwPrdLoanTrmRepository,
                        MwBrnchPrdRelRepository mwBrnchPrdRelRepository, MwRulRepository mwRulRepository, MwRefCdValRepository mwRefCdValRepository,
                        MwPrdChrgRepository mwPrdChrgRepository, MwPrdChrgSlbRepository mwPrdChrgSlbRepository) {
        this.mwPrdRepository = mwPrdRepository;
        this.em = em;
        this.mwPortRepository = mwPortRepository;
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
        this.mwBrnchPrdRelRepository = mwBrnchPrdRelRepository;
        this.mwRulRepository = mwRulRepository;
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwPrdChrgRepository = mwPrdChrgRepository;
        this.mwPrdChrgSlbRepository = mwPrdChrgSlbRepository;
    }

    public MwPrd save(ProductDto dto, String currUser) {
        Long seq = SequenceFinder.findNextVal(Sequences.PRD_SEQ);
        Instant currIns = Instant.now();
        MwPrd mwPrd = new MwPrd();

        mwPrd.setPrdSeq(seq);
        mwPrd.setPrdGrpSeq(dto.prdGrpSeq);
        mwPrd.setPrdNm(dto.productName);
        mwPrd.setPrdId(dto.prdId);
        // mwPrd.setPrdId( String.format( "%04d", seq ) );
        mwPrd.setPrdCmnt(dto.prdCmnt);
        mwPrd.setPrdStsKey(dto.prdStsKey);
        mwPrd.setPrdTypKey(dto.prdTypKey);
        mwPrd.setIrrFlg(dto.irrFlg);
        mwPrd.setRndngScl(dto.rndngScl);
        mwPrd.setRndngAdj(dto.rndngAdj);
        mwPrd.setDailyAccuralFlg(dto.dailyAccuralFlg);
        // mwPrd.setFndByKey(dto.fndByKey);
        // mwPrd.setCrncyCdKey(dto.crncyCdKey);
        mwPrd.setIrrVal(dto.irrVal);
        mwPrd.setMltLoanFlg(dto.mltLoanFlg);
        mwPrd.setCrtdBy(currUser);
        mwPrd.setCrtdDt(currIns);
        mwPrd.setLastUpdBy(currUser);
        mwPrd.setLastUpdDt(currIns);
        mwPrd.setDelFlg(false);
        mwPrd.setEffStartDt(currIns);
        mwPrd.setCrntRecFlg(true);
        mwPrd.setPdcNum(dto.pdcNum);
        return mwPrdRepository.save(mwPrd);
    }

    @Transactional(readOnly = true)
    public List<MwPrd> findAllProductsByGroup(Long prdGrpSeq) {
        log.debug("Request to get all MwPrds");
        return mwPrdRepository.findAllByPrdGrpSeqAndCrntRecFlgOrderByPrdSeq(prdGrpSeq, true);
    }

    @Transactional(readOnly = true)
    public List<MwPrd> findAllProducts() {
        log.debug("Request to get all MwPrds");
        return mwPrdRepository.findAllByCrntRecFlgOrderByPrdNm(true);
    }

    @Transactional(readOnly = true)
    public List<MwPrd> findAllByPrdSeq(Long prdSeq) {
        log.debug("Request to get all MwPrds");
        return mwPrdRepository.findAllByPrdSeq(prdSeq);
    }

    @Transactional(readOnly = true)
    public MwPrd findOne(Long prdSeq) {
        log.debug("Request to get MwPrd : {}", prdSeq);
        return mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(prdSeq, true);
    }

    public Boolean delete(Long prdSeq) {
        log.debug("Request to delete MwPrd : {}", prdSeq);
        MwPrd exMwPrd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(prdSeq, true);
        if (exMwPrd == null) {
            return false;
        }
        exMwPrd.setCrntRecFlg(false);
        exMwPrd.setDelFlg(true);
        exMwPrd.setLastUpdDt(Instant.now());
        mwPrdRepository.save(exMwPrd);
        return true;
    }

    @Transactional
    public MwPrd updateExistingProduct(ProductDto dto, String currUser) {
        MwPrd mwPrd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(dto.productSeq, true);
        Instant currIns = Instant.now();
        if (mwPrd == null) {
            return null;
        }

        mwPrd.setLastUpdDt(currIns);
        mwPrd.setLastUpdBy(currUser);
        mwPrd.setPrdNm(dto.productName);
        mwPrd.setPrdSeq(dto.productSeq);
        mwPrd.setPrdId(dto.prdId);
        mwPrd.setPrdCmnt(dto.prdCmnt);
        mwPrd.setIrrFlg(dto.irrFlg);
        mwPrd.setIrrVal(dto.irrVal);
        mwPrd.setMltLoanFlg(dto.mltLoanFlg);
        mwPrd.setDailyAccuralFlg(dto.dailyAccuralFlg);
        mwPrd.setPrdTypKey(dto.prdTypKey);
        mwPrd.setRndngScl(dto.rndngScl);
        mwPrd.setRndngAdj(dto.rndngAdj);
        mwPrd.setPdcNum(dto.pdcNum);
        mwPrd.setPrdStsKey(dto.prdStsKey);

        return mwPrdRepository.save(mwPrd);

    }

    public List<ProductDto> getProductsListing() {

        List<MwPrd> products = mwPrdRepository.findAllByCrntRecFlgOrderByPrdSeq(true);

        List<ProductDto> dtos = new ArrayList<>();

        for (MwPrd obj : products) {
            // MwRefCdVal val =
            // mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(obj.getPrdStsKey(),
            // true);

            ProductDto dto = new ProductDto();
            dto.productSeq = obj.getPrdSeq();
            dto.productName = obj.getPrdNm();
            dto.fndByKey = obj.getFndByKey();
            dto.dailyAccuralFlg = obj.getDailyAccuralFlg();
            dto.irrFlg = obj.getIrrFlg();
            dto.irrVal = obj.getIrrVal();
            dto.mltLoanFlg = obj.getMltLoanFlg();
            dto.prdCmnt = obj.getPrdCmnt();
            dto.prdStsKey = obj.getPrdStsKey();
            dto.prdTypKey = obj.getPrdTypKey();
            // dto.productStatus = val ==null ? val.getRefCdDscr(): "";;
            dto.rndngAdj = obj.getRndngAdj();
            dto.rndngScl = obj.getRndngScl();
            dtos.add(dto);
        }
        // String query = Queries.productsListing;
        // // log.debug(query);
        // Query q = em.createNativeQuery(query);
        // List<Object[]> result = q.getResultList();
        // List<ProductDto> dtos = new ArrayList<>();
        // for(Object[] obj: result) {
        // ProductDto dto = new ProductDto();
        // dto.productSeq = Long.valueOf(obj[0].toString());
        // dto.productName = obj[1].toString();
        // dto.installments = Integer.valueOf(obj[2].toString());
        // dto.calcType = obj[3].toString();
        // dto.serviceCharges = Double.valueOf(obj[4].toString());
        // dto.minAmount = Double.valueOf(obj[5].toString());
        // dto.maxAmount = Double.valueOf(obj[6].toString());
        // dto.condition = obj[7].toString();
        // dtos.add(dto);
        // }
        return dtos;
    }

    public List<ProductDto> getProductsListingForClient(ProductDto dto) {

        List<ProductDto> products = new ArrayList<>();

        MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dto.portSeq, true);

        if (port == null)
            return products;

        List<MwBrnchPrdRel> prdRels = mwBrnchPrdRelRepository.findAllByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);

        if (prdRels.size() < 1)
            return products;
        List<MwPrd> mwPrds = new ArrayList<>();
        prdRels.forEach(rel -> {
            MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(rel.getPrdSeq(), true);
            if (prd != null) {
                if (prd.getPrdTypKey() != null) {
                    MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(prd.getPrdTypKey(), true);
                    if (!val.getRefCd().equals("1165"))
                        mwPrds.add(prd);
                }
            }
        });

        if (mwPrds.size() < 1)
            return products;

        String viewQuery = Queries.CLNT_LOAN_GTT;
        Query q = em.createNativeQuery(viewQuery).setParameter("clntSeq", dto.clientSeq).setParameter("loanAppSeq", dto.loanAppSeq);
        q.executeUpdate();
        List<MwRul> rules = mwRulRepository.findAllByRulCtgryKeyAndCrntRecFlg(1, true);
        MwRul defaultRule = new MwRul();
        rules.forEach(rle -> {
            if (rle.getRulNm().toLowerCase().equals("default")) {
                defaultRule.setRulSeq(rle.getRulSeq());
                defaultRule.setRulNm(rle.getRulNm());
                defaultRule.setRulCrtraStr(rle.getRulCrtraStr());
                defaultRule.setRulCmnt(rle.getRulCmnt());
            }
        });
        mwPrds.forEach(prd -> {
            List<MwPrdPpalLmt> ppalLimits = mwPrdPpalLmtRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
            if (ppalLimits.size() > 0) {
                int size = products.size();
                ProductDto defaultProductDto = new ProductDto();

                ppalLimits.forEach(limit -> {
                    MwRul rule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(limit.getRulSeq(), true);
                    if (rule != null) {
                        String ruleQuery = Queries.checkRule + rule.getRulCrtraStr();
                        Query qr = em.createNativeQuery(ruleQuery);
                        List<Object[]> rulResult = qr.getResultList();

                        if (rulResult.size() > 0) {
                            BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                            if ((bd.longValue()) == 1) {
                                ProductDto prod = new ProductDto();
                                prod.condition = rule.getRulCrtraStr();
                                prod.maxAmount = limit.getMaxAmt();
                                prod.minAmount = limit.getMinAmt();
                                prod.productName = prd.getPrdNm();
                                prod.productSeq = prd.getPrdSeq();
                                prod.prdGrpSeq = prd.getPrdGrpSeq();
                                prod.limitRule = rule.getRulSeq();
                                List<MwPrdLoanTrm> terms = mwPrdLoanTrmRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                                terms.forEach(term -> {
                                    MwRul termRule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(term.getRulSeq(), true);
                                    if (termRule != null) {
                                        String termRuleQuery = Queries.checkRule + termRule.getRulCrtraStr();
                                        Query qrt = em.createNativeQuery(termRuleQuery);
                                        List<Object[]> termRulResult = qrt.getResultList();

                                        if (termRulResult.size() > 0) {
                                            BigDecimal bdt = new BigDecimal("" + termRulResult.get(0));
                                            if ((bdt.longValue()) == 1) {
                                                prod.termRule = termRule.getRulSeq();
                                                MwRefCdVal refCdVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(term.getTrmKey(),
                                                    true);
                                                prod.installments = (refCdVal == null) ? 0 : Integer.parseInt(refCdVal.getRefCdDscr());
                                                return;
                                            }
                                        }
                                    }
                                });
                                if (prod.installments == 0 && defaultRule != null) {
                                    MwPrdLoanTrm term = mwPrdLoanTrmRepository.findOneByPrdSeqAndRulSeqAndCrntRecFlg(prd.getPrdSeq(),
                                        defaultRule.getRulSeq(), true);
                                    if (term != null) {
                                        MwRefCdVal refCdVal = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(term.getTrmKey(), true);
                                        prod.installments = (refCdVal == null) ? 0 : Integer.parseInt(refCdVal.getRefCdDscr());
                                        prod.termRule = defaultRule.getRulSeq();
                                    }
                                }

                                if (rule.getRulNm().equals("Default")) {
                                    defaultProductDto.condition = rule.getRulCrtraStr();
                                    defaultProductDto.maxAmount = limit.getMaxAmt();
                                    defaultProductDto.minAmount = limit.getMinAmt();
                                    defaultProductDto.productName = prd.getPrdNm();
                                    defaultProductDto.productSeq = prd.getPrdSeq();
                                    defaultProductDto.prdGrpSeq = prd.getPrdGrpSeq();
                                    defaultProductDto.prdRul = rule.getRulSeq();
                                    defaultProductDto.installments = prod.installments;
                                    defaultProductDto.limitRule = rule.getRulSeq();
                                    defaultProductDto.termRule = prod.termRule;
                                } else {
                                    products.add(prod);
                                }
                            }
                        }
                    }
                });
                if (products.size() == size) {
                    if (defaultProductDto.productSeq != null && defaultProductDto.productSeq > 0) {
                        products.add(defaultProductDto);
                    }
                }
            }
        });

        products.forEach(product -> {
            List<Charges> productCharges = new ArrayList<>();
            List<MwPrdChrg> charges = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(product.productSeq, true, false);
            if (charges.size() > 0) {
                charges.forEach(charge -> {
                    String typId = "";
                    String typQuery = "select typ_seq, typ_id from mw_typs where crnt_rec_flg=1 and typ_seq=  " + charge.getChrgTypSeq();
                    Query typnQuery = em.createNativeQuery(typQuery);
                    List<Object[]> resultSet = typnQuery.getResultList();
                    if (resultSet != null) {
                        Object[] obj = resultSet.get(0);
                        if (obj != null) {
                            typId = obj[1] != null ? obj[1].toString() : "";
                        }
                    }
                    if (typId.equals("0017")) {
                        if (charge.getRulSeq() != null) {
                            MwRul rule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(charge.getRulSeq(), true);
                            if (rule != null) {
                                String ruleQuery = Queries.checkRule + rule.getRulCrtraStr();
                                Query qr = em.createNativeQuery(ruleQuery);
                                List<Object[]> rulResult = qr.getResultList();
                                if (rulResult.size() > 0) {
                                    BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                                    if ((bd.longValue()) == 1) {
                                        productCharges.add(new Charges(charge.getChrgCalcTypKey(),
                                            (charge.getChrgVal() == null) ? 0 : charge.getChrgVal().longValue()));
                                    }
                                }
                            }
                        } else {
                            Charges chrgs = new Charges(charge.getChrgCalcTypKey(),
                                (charge.getChrgVal() == null) ? 0 : charge.getChrgVal().longValue());
                            List<MwPrdChrgSlb> slbs = mwPrdChrgSlbRepository.findAllByPrdChrgSeqAndCrntRecFlg(charge.getPrdChrgSeq(),
                                true);
                            chrgs.slbs = slbs;
                            productCharges.add(chrgs);

                        }
                    }
                });
            }
            product.charges = productCharges;
        });
        return products;
    }

    public List<ProductDto> getProductsByTypSeq(long typSeq) {
        List<MwPrd> products = mwPrdRepository.findAllByPrdTypKeyAndCrntRecFlg(typSeq, true);
        final List<ProductDto> productDtoList = new ArrayList<ProductDto>();
        products.forEach(product -> {
            ProductDto dto = new ProductDto();
            dto.productSeq = product.getPrdSeq();
            dto.prdGrpSeq = product.getPrdGrpSeq();
            dto.prdNm = product.getPrdNm();
            dto.productStatus = "" + product.getPrdStsKey();
            dto.prdTypKey = product.getPrdTypKey();
            dto.mltLoanFlg = product.getMltLoanFlg();
            productDtoList.add(dto);
        });
        return productDtoList;
    }

    public List<GlAccount> getAllGLAccounts() {
        Query q = em.createNativeQuery(Queries.glAccount);
        List<Object[]> results = q.getResultList();
        List<GlAccount> data = new ArrayList<>();
        if (results != null && results.size() > 0) {
            for (Object[] obj : results) {
                GlAccount acc = new GlAccount();
                acc.desc = (obj[0] == null) ? "" : obj[0].toString();
                acc.accNum = (obj[1] == null) ? "" : obj[1].toString();
                data.add(acc);
            }
        }
        return data;
    }

    @Transactional(readOnly = true)
    public List<MwPrd> findActivePrdForBrnch(Long brnchSeq) {
        log.debug("Request to get all Active MwPrds");
        return mwPrdRepository.findAllMwPrdForBranch(brnchSeq);
    }
}
