package com.idev4.setup.service;

import com.idev4.setup.domain.*;
import com.idev4.setup.dto.Charges;
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
public class MwAsocPrdRelService {

    private final Logger log = LoggerFactory.getLogger(MwAsocPrdRelService.class);

    private final MwRefCdValRepository mwRefCdValRepository;

    private final MwAsocPrdRelRepository mwAsocPrdRelRepository;

    private final MwPrdRepository mwPrdRepository;

    private final EntityManager em;

    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;

    private final MwRulRepository mwRulRepository;

    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;

    private final MwPrdChrgRepository mwPrdChrgRepository;

    public MwAsocPrdRelService(EntityManager em, MwRefCdValRepository mwRefCdValRepository, MwAsocPrdRelRepository mwAsocPrdRelRepository,
                               MwPrdRepository mwPrdRepository, MwPrdPpalLmtRepository mwPrdPpalLmtRepository, MwRulRepository mwRulRepository,
                               MwPrdLoanTrmRepository mwPrdLoanTrmRepository, MwPrdChrgRepository mwPrdChrgRepository) {
        this.em = em;
        this.mwAsocPrdRelRepository = mwAsocPrdRelRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwPrdRepository = mwPrdRepository;
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
        this.mwRulRepository = mwRulRepository;
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
        this.mwPrdChrgRepository = mwPrdChrgRepository;
    }

    public List<MwAsocPrdRel> getAllRelForProduct(long productSeq) {
        return mwAsocPrdRelRepository.findAllByPrdSeqAndCrntRecFlg(productSeq, true);
    }

    public MwAsocPrdRel addNewRel(MwAsocPrdRel rel, String curUser) {
        rel.setAsocPrdRelSeq(SequenceFinder.findNextVal(Sequences.ASOC_PRD_REL_SEQ));
        rel.setCrntRecFlg(true);
        rel.setCrtdBy(curUser);
        rel.setCrtdDt(Instant.now());
        rel.setDelFlg(false);
        rel.setEffStartDt(Instant.now());
        rel.setLastUpdDt(Instant.now());
        rel.setLastUpdBy(curUser);
        return mwAsocPrdRelRepository.save(rel);
    }

    public Boolean deleteRel(long relSeq, String curUser) {
        MwAsocPrdRel rel = mwAsocPrdRelRepository.findOneByAsocPrdRelSeqAndCrntRecFlg(relSeq, true);
        if (rel == null)
            return false;
        rel.setCrntRecFlg(false);
        rel.setDelFlg(true);
        rel.setLastUpdBy(curUser);
        rel.setLastUpdDt(Instant.now());
        mwAsocPrdRelRepository.save(rel);
        return true;
    }

    public List<MwPrd> getAssocProductsForProduct(long seq) {
        final List<MwPrd> list = new ArrayList<>();
        List<MwAsocPrdRel> rels = getAllRelForProduct(seq);
        rels.forEach(rel -> {
            MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(rel.getAsocPrdSeq(), true);
            if (prd != null)
                list.add(prd);
        });
        return list;
    }

    public List<ProductDto> getAssocProductsForClient(ProductDto dto) {
        List<ProductDto> products = new ArrayList<>();
        List<MwPrd> mwPrds = getAssocProductsForProduct(dto.productSeq);
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
                    if (charge.getChrgTypSeq() == 17) {
                        if (charge.getRulSeq() != null) {
                            MwRul rule = mwRulRepository.findOneByRulSeqAndCrntRecFlg(charge.getRulSeq(), true);
                            if (rule != null) {
                                String ruleQuery = Queries.checkRule + rule.getRulCrtraStr();
                                Query qr = em.createNativeQuery(ruleQuery);
                                List<Object[]> rulResult = qr.getResultList();
                                if (rulResult.size() > 0) {
                                    BigDecimal bd = new BigDecimal("" + rulResult.get(0));
                                    if ((bd.longValue()) == 1) {
                                        productCharges.add(new Charges(charge.getChrgCalcTypKey(), charge.getChrgVal().longValue()));
                                    }
                                }
                            }
                        } else {
                            productCharges.add(new Charges(charge.getChrgCalcTypKey(), charge.getChrgVal().longValue()));
                        }
                    }
                });
            }
            product.charges = productCharges;
        });

        return products;
    }
}
