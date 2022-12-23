package com.idev4.setup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idev4.setup.domain.*;
import com.idev4.setup.dto.tab.MwTblIndxDto;
import com.idev4.setup.dto.tab.SyncDto;
import com.idev4.setup.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class MwTabService {

    private final Logger log = LoggerFactory.getLogger(MwTabService.class);
    private final EntityManager em;
    private final MwAnswrRepository mwAnswrRepository;
    private final MwPortRepository mwPortRepository;
    private final MwDocRepository mwDocRepository;
    private final MwFormRepository mwFormRepository;
    private final MwHlthInsrPlanRepository mwHlthInsrPlanRepository;
    private final MwQstnrRepository mwQstnrRepository;
    private final MwQstRepository mwQstRepository;
    private final MwRefCdGrpRepository mwRefCdGrpRepository;
    private final MwRefCdValRepository mwRefCdValRepository;
    private final MwRulRepository mwRulRepository;
    private final MwDvcRgstryRepository mwDvcRgstryRepository;
    private final MwCmntyRepository mwCmntyRepository;
    private final MwBrnchRepository mwBrnchRepository;
    private final MwAreaRepository mwAreaRepository;
    private final MwRegRepository mwRegRepository;
    private final MwCntryRepository mwCntryRepository;
    private final MwStRepository mwStRepository;
    private final MwDistRepository mwDistRepository;
    private final MwThslRepository mwThslRepository;
    private final MwUcRepository mwUcRepository;
    private final MwBrnchLocationRelRepository mwBrnchLocationRelRepository;
    private final MwCityUcRelRepository mwCityUcRelRepository;
    private final MwCityRepository mwCityRepository;
    private final MwBrnchPrdRelRepository mwBrnchPrdRelRepository;
    private final MwPrdRepository mwPrdRepository;
    private final MwPrdRulRepository mwPrdRulRepository;
    private final MwPrdAdvRulRepository mwPrdAdvRulRepository;
    private final MwPrdBizSectRelRepository mwPrdBizSectRelRepository;
    private final MwPrdChrgRepository mwPrdChrgRepository;
    private final MwPrdDocRelRepository mwPrdDocRelRepository;
    private final MwPrdFormRelRepository mwPrdFormRelRepository;
    private final MwPrdLoanTrmRepository mwPrdLoanTrmRepository;
    private final MwPrdPpalLmtRepository mwPrdPpalLmtRepository;
    private final MwBizSectRepository mwBizSectRepository;
    private final MwBizActyRepository mwBizActyRepository;
    private final MwPortLocationRelRepository mwPortLocationRelRepository;
    private final MwTypsRepository mwTypsRepository;
    private final MwPrdChrgSlbRepository mwPrdChrgSlbRepository;
    private final MwPortEmpRelRepository mwPortEmpRelRepository;
    private final MwEmpRepository mwEmpRepository;
    private final MwPrdGrpRepository mwPrdGrpRepository;
    private final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private final DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
    @Autowired
    ServletContext context;

    public MwTabService(EntityManager em, MwAnswrRepository mwAnswrRepository, MwPortRepository mwPortRepository,
                        MwDocRepository mwDocRepository, MwFormRepository mwFormRepository, MwHlthInsrPlanRepository mwHlthInsrPlanRepository,
                        MwQstnrRepository mwQstnrRepository, MwQstRepository mwQstRepository, MwRefCdGrpRepository mwRefCdGrpRepository,
                        MwRefCdValRepository mwRefCdValRepository, MwRulRepository mwRulRepository, MwDvcRgstryRepository mwDvcRgstryRepository,
                        MwCmntyRepository mwCmntyRepository, MwBrnchRepository mwBrnchRepository, MwAreaRepository mwAreaRepository,
                        MwRegRepository mwRegRepository, MwCntryRepository mwCntryRepository, MwStRepository mwStRepository,
                        MwDistRepository mwDistRepository, MwThslRepository mwThslRepository, MwUcRepository mwUcRepository,
                        MwBrnchLocationRelRepository mwBrnchLocationRelRepository, MwCityUcRelRepository mwCityUcRelRepository,
                        MwCityRepository mwCityRepository, MwBrnchPrdRelRepository mwBrnchPrdRelRepository, MwPrdRepository mwPrdRepository,
                        MwPrdRulRepository mwPrdRulRepository, MwPrdAdvRulRepository mwPrdAdvRulRepository,
                        MwPrdBizSectRelRepository mwPrdBizSectRelRepository, MwPrdChrgRepository mwPrdChrgRepository,
                        MwPrdDocRelRepository mwPrdDocRelRepository, MwPrdFormRelRepository mwPrdFormRelRepository,
                        MwPrdLoanTrmRepository mwPrdLoanTrmRepository, MwPrdPpalLmtRepository mwPrdPpalLmtRepository,
                        MwBizSectRepository mwBizSectRepository, MwBizActyRepository mwBizActyRepository,
                        MwPortLocationRelRepository mwPortLocationRelRepository, MwTypsRepository mwTypsRepository,
                        MwPrdChrgSlbRepository mwPrdChrgSlbRepository, MwPortEmpRelRepository mwPortEmpRelRepository, MwEmpRepository mwEmpRepository,
                        MwPrdGrpRepository mwPrdGrpRepository) {
        this.em = em;
        this.mwAnswrRepository = mwAnswrRepository;
        this.mwPortRepository = mwPortRepository;
        this.mwDocRepository = mwDocRepository;
        this.mwFormRepository = mwFormRepository;
        this.mwHlthInsrPlanRepository = mwHlthInsrPlanRepository;
        this.mwQstnrRepository = mwQstnrRepository;
        this.mwQstRepository = mwQstRepository;
        this.mwRefCdGrpRepository = mwRefCdGrpRepository;
        this.mwRefCdValRepository = mwRefCdValRepository;
        this.mwRulRepository = mwRulRepository;
        this.mwDvcRgstryRepository = mwDvcRgstryRepository;
        this.mwCmntyRepository = mwCmntyRepository;
        this.mwBrnchRepository = mwBrnchRepository;
        this.mwAreaRepository = mwAreaRepository;
        this.mwRegRepository = mwRegRepository;
        this.mwCntryRepository = mwCntryRepository;
        this.mwStRepository = mwStRepository;
        this.mwDistRepository = mwDistRepository;
        this.mwThslRepository = mwThslRepository;
        this.mwUcRepository = mwUcRepository;
        this.mwBrnchLocationRelRepository = mwBrnchLocationRelRepository;
        this.mwCityUcRelRepository = mwCityUcRelRepository;
        this.mwCityRepository = mwCityRepository;
        this.mwBrnchPrdRelRepository = mwBrnchPrdRelRepository;
        this.mwPrdRepository = mwPrdRepository;
        this.mwPrdRulRepository = mwPrdRulRepository;
        this.mwPrdAdvRulRepository = mwPrdAdvRulRepository;
        this.mwPrdBizSectRelRepository = mwPrdBizSectRelRepository;
        this.mwPrdChrgRepository = mwPrdChrgRepository;
        this.mwPrdDocRelRepository = mwPrdDocRelRepository;
        this.mwPrdFormRelRepository = mwPrdFormRelRepository;
        this.mwPrdLoanTrmRepository = mwPrdLoanTrmRepository;
        this.mwPrdPpalLmtRepository = mwPrdPpalLmtRepository;
        this.mwBizSectRepository = mwBizSectRepository;
        this.mwBizActyRepository = mwBizActyRepository;
        this.mwPortLocationRelRepository = mwPortLocationRelRepository;
        this.mwTypsRepository = mwTypsRepository;
        this.mwPrdChrgSlbRepository = mwPrdChrgSlbRepository;
        this.mwPortEmpRelRepository = mwPortEmpRelRepository;
        this.mwEmpRepository = mwEmpRepository;
        this.mwPrdGrpRepository = mwPrdGrpRepository;
    }

    public String markSyncDate(String mac) {
        MwDvcRgstr dvc = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(mac, true);
        if (dvc == null)
            return null;
        dvc.setStpSyncDt(dvc.getStpSyncTmpDt());
        mwDvcRgstryRepository.save(dvc);
        return "Date Updated";
    }

    public SyncDto getDataForDeviceRegistered(String mac) throws ParseException {
        MwDvcRgstr dvc = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(mac, true);
        if (dvc == null)
            return null;
        Instant syncDate = (dvc.getStpSyncDt() == null) ? formatter.parse("01-01-2018 00:00:01").toInstant() : dvc.getStpSyncDt();
        if (dvc.getEntyTypFlg() == 1) {
            List<Long> ports = new ArrayList<>();
            ports.add(dvc.getEntyTypSeq());
            SyncDto dto = getDataForTab(ports, syncDate);
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (port != null) {
                if (port.getBrnchSeq() != null) {
                    if (port.getLastUpdDt().isAfter(syncDate)) {
                        dto = getCompleteOrganizationForBranch(dto, port.getBrnchSeq());
                        dto = getCompleteProductForBranch(dto, port.getBrnchSeq());
                    } else {
                        MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);
                        if (branch != null) {
                            if (branch.getLastUpdDt().isAfter(syncDate)) {
                                dto = getCompleteOrganizationForBranch(dto, port.getBrnchSeq());
                            }
                            dto = traverseTables(dto, port.getBrnchSeq(), dvc.getStpSyncDt());
                        }
                    }
                    dto = traverseLocationsForPort(dto, port.getPortSeq(), syncDate);
                }
            }
            dvc.setStpSyncDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);
            try {
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                FileOutputStream f = new FileOutputStream(new File(context.getRealPath("") + "WEB-INF" + File.separator + "classes"
                    + File.separator + "sync" + File.separator + SecurityContextHolder.getContext().getAuthentication().getName()
                    + File.separator + date + "-SETUP-SYNC.txt"));
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(dto.toString());

                o.close();
                f.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dto;
        } else if (dvc.getEntyTypFlg() == 2) {
            //
            List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            List<Long> prts = new ArrayList<>();
            ports.forEach(port -> {
                prts.add(port.getPortSeq());
            });
            SyncDto dto = getDataForTab(prts, syncDate);
            MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (branch != null) {
                if (branch.getLastUpdDt().isAfter(syncDate)) {
                    dto = getCompleteOrganizationForBranch(dto, branch.getBrnchSeq());
                }
                dto = traverseTables(dto, branch.getBrnchSeq(), syncDate);
                dto = traverseLocationForBranch(dto, syncDate, branch.getBrnchSeq());
            }
            dvc.setStpSyncDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);

            try {
                FileOutputStream f = new FileOutputStream(new File(context.getRealPath("") + "WEB-INF" + File.separator + "classes"
                    + File.separator + "sync" + File.separator + mac + "-SETUP-SYNC.txt"));
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(dto.toString());

                o.close();
                f.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dto;
        }
        return new SyncDto();
    }


    // @Transactional ( readOnly = true )
    public SyncDto getDataForDeviceRegistered(String mac, Boolean completeDataRequest) throws ParseException, SQLException, IOException {
        MwDvcRgstr dvc = mwDvcRgstryRepository.findOneByDvcAddrAndCrntRecFlg(mac, true);
        if (dvc == null)
            return null;
        Instant syncDate = (dvc.getStpSyncDt() == null || completeDataRequest) ? formatter.parse("01-01-2018 00:00:01").toInstant()
            : dvc.getStpSyncDt();
        if (dvc.getEntyTypFlg() == 1) {
            List<Long> ports = new ArrayList<>();
            ports.add(dvc.getEntyTypSeq());
            SyncDto dto = getDataForTab(ports, syncDate);
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (port != null) {
                if (port.getBrnchSeq() != null) {
                    if (port.getLastUpdDt().isAfter(syncDate)) {
                        dto = getCompleteOrganizationForBranch(dto, port.getBrnchSeq());
                        dto = getCompleteProductForBranch(dto, port.getBrnchSeq());
                    } else {
                        MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(port.getBrnchSeq(), true);
                        if (branch != null) {
                            if (completeDataRequest) {
                                dto = getCompleteProductForBranch(dto, branch.getBrnchSeq());
                                dto = getCompleteOrganizationForBranch(dto, branch.getBrnchSeq());
                            } else {
                                dto = traverseTables(dto, branch.getBrnchSeq(), syncDate);
                                if (branch.getLastUpdDt().isAfter(syncDate)) {
                                    dto = getCompleteOrganizationForBranch(dto, branch.getBrnchSeq());
                                }
                            }
                        }
                    }
                    if (completeDataRequest)
                        dto = getCompleteGeographyForPort(dto, port.getPortSeq());
                    else
                        dto = traverseLocationsForPort(dto, port.getPortSeq(), syncDate);
                }
            }
            dvc.setStpSyncTmpDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);
            // saveFile( dto, SecurityContextHolder.getContext().getAuthentication().getName() );
            return dto;
        } else if (dvc.getEntyTypFlg() == 2) {
            //
            List<MwPort> ports = mwPortRepository.findAllByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            List<Long> prts = new ArrayList<>();
            ports.forEach(port -> {
                prts.add(port.getPortSeq());
            });
            SyncDto dto = getDataForTab(prts, syncDate);
            MwBrnch branch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(dvc.getEntyTypSeq(), true);
            if (branch != null) {
                if (completeDataRequest) {
                    dto = getCompleteProductForBranch(dto, branch.getBrnchSeq());
                    dto = getCompleteOrganizationForBranch(dto, branch.getBrnchSeq());
                    dto = getCompleteGeographyForBranch(dto, branch.getBrnchSeq());
                } else {
                    dto = traverseTables(dto, branch.getBrnchSeq(), syncDate);
                    dto = traverseLocationForBranch(dto, syncDate, branch.getBrnchSeq());
                    if (branch.getLastUpdDt().isAfter(syncDate)) {
                        dto = getCompleteOrganizationForBranch(dto, branch.getBrnchSeq());
                    }
                }
            }
            dvc.setStpSyncTmpDt(Instant.now());
            mwDvcRgstryRepository.save(dvc);
            // saveFile( dto, SecurityContextHolder.getContext().getAuthentication().getName() );
            return dto;
        }
        return new SyncDto();
    }

    @Async
    public void saveFile(SyncDto dto, String user) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            FileOutputStream f = new FileOutputStream(new File(context.getRealPath("") + "WEB-INF" + File.separator + "classes"
                + File.separator + "sync" + File.separator + user + "-" + date + "-SETUP-SYNC.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(dto);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public SyncDto getDataForTab(List<Long> portKey, Instant syncDate) {
        SyncDto dto = new SyncDto();
        // -------------- MW_ANSWR ------------- //
        List<MwAnswr> answrs = mwAnswrRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (answrs.size() > 0) {
            dto.mw_answr = new ArrayList<MwAnswr>(answrs);
        }

        // -------------- MW_PORT ------------- //
        List<MwPort> ports = new ArrayList<>();
        portKey.forEach(p -> {
            MwPort port = mwPortRepository.findOneByPortSeqAndCrntRecFlg(p, true);
            if (port != null) {
                // ------------ MW_CMNTY ----------- //

                if (port.getLastUpdDt().isAfter(syncDate)) {
                    if (dto.mw_port == null)
                        dto.mw_port = new ArrayList<>();
                    dto.mw_port.add(port);
                    ports.add(port);
                }

            }
        });

        // -------------- MW-CMNTY --------//
        List<MwCmnty> cmntys = mwCmntyRepository.findAllByLastUpdDtAfterAndCrntRecFlgAndPortSeqInOrLastUpdDtAfterAndDelFlgAndPortSeqIn(
            syncDate, true, portKey, syncDate, true, portKey);
        if (cmntys.size() > 0) {
            if (dto.mw_cmnty == null)
                dto.mw_cmnty = new ArrayList<>();
            dto.mw_cmnty.addAll(cmntys);
        }
        // --------------- MW_DOC ------------- //
        List<MwDoc> docs = mwDocRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (docs.size() > 0) {
            dto.mw_doc = new ArrayList<MwDoc>(docs);
        }

        // -------------- MW_FORM ----------- //
        List<MwForm> forms = mwFormRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (forms.size() > 0) {
            dto.mw_form = new ArrayList<MwForm>(forms);
        }

        // -------------- MW_HLTH_INSR_PLAN -------- //
        List<MwHlthInsrPlan> plans = mwHlthInsrPlanRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate,
            true, syncDate, true);
        if (plans.size() > 0) {
            dto.mw_hlth_insr_plan = new ArrayList<MwHlthInsrPlan>(plans);
        }

        // -------------- MW_QST --------------- //
        List<MwQst> qsts = mwQstRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (qsts.size() > 0) {
            dto.mw_qst = new ArrayList<MwQst>(qsts);
        }

        // -------------- MW_QSTNR -------------- //
        List<MwQstnr> qstnrs = mwQstnrRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (qstnrs.size() > 0) {
            dto.mw_qstnr = new ArrayList<MwQstnr>(qstnrs);
        }

        // -------------- MW_REF_CD_GRP ---------- //
        List<MwRefCdGrp> grps = mwRefCdGrpRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true,
            syncDate, true);
        if (grps.size() > 0) {
            dto.mw_ref_cd_grp = new ArrayList<MwRefCdGrp>(grps);
        }

        // -------------- MW_REF_CD_VAL ---------- //
        List<MwRefCdVal> vals = mwRefCdValRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true,
            syncDate, true);
        if (vals.size() > 0) {
            dto.mw_ref_cd_val = new ArrayList<MwRefCdVal>(vals);
        }
        // -------------- MW_RUL ---------- //
        List<MwRul> ruls = mwRulRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        if (ruls.size() > 0) {
            dto.mw_rul = new ArrayList<MwRul>(ruls);
        }

        // ------------- MW_TYP -------- //
        List<MwTyps> typs = mwTypsRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndTypCtgryKey(syncDate, true,
            syncDate, true, 1L);
        if (typs.size() > 0) {
            dto.mw_typs = new ArrayList<MwTyps>(typs);
        }

        // ------------ MW_TBL_INDX ---------- //
        Query q = em.createNativeQuery("select * from mw_tbl_indx");
        List<Object[]> tblIndxes = q.getResultList();

        if (tblIndxes.size() > 0) {
            dto.mw_tbl_indx = new ArrayList<>();
        }
        for (Object[] tblIndx : tblIndxes) {
            dto.mw_tbl_indx.add(new MwTblIndxDto(tblIndx[0].toString(), tblIndx[1].toString(), tblIndx[2].toString()));
        }

        // -------- MW_PORT_EMP_REL ------- //
        List<MwPortEmpRel> portEmpRels = mwPortEmpRelRepository.findAllByLastUpdDtAfterAndPortSeqInOrderByCrntRecFlg(syncDate, portKey);
        if (portEmpRels.size() > 0) {
            dto.mw_port_emp_rel = new ArrayList<MwPortEmpRel>(portEmpRels);
        }
        portEmpRels.forEach(rel -> {
            MwEmp emp = mwEmpRepository.findOneByEmpSeq(rel.getEmpSeq());
            if (emp != null) {
                if (dto.mw_emp == null)
                    dto.mw_emp = new ArrayList<>();
                dto.mw_emp.add(emp);
            }
        });
        return dto;
    }

    public SyncDto getBrnchData(SyncDto dto, long brnchSeq) {
        // ----------
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto getCompleteGeographyForPort(SyncDto dto, long portSeq) {
        if (portSeq == 0)
            return dto;

        // ------ MW_BRNCH_LOCATION_REL ------- //
        List<MwPortLocationRel> locRels = mwPortLocationRelRepository.findAllByPortSeqAndCrntRecFlg(portSeq, true);
        locRels.forEach(rel -> {
            if (dto.mw_port_location_rel == null)
                dto.mw_port_location_rel = new ArrayList<>();
            if (!dto.mw_port_location_rel.contains(rel))
                dto.mw_port_location_rel.add(rel);
        });

        List<MwCityUcRel> cityUcRels = mwCityUcRelRepository.findAllMwCityUcRelForPort(portSeq);
        cityUcRels.forEach(cityUcRel -> {
            if (dto.mw_city_uc_rel == null)
                dto.mw_city_uc_rel = new ArrayList<>();
            if (!dto.mw_city_uc_rel.contains(cityUcRel))
                dto.mw_city_uc_rel.add(cityUcRel);
        });
        // ---------- MW_CITY --------- //
        List<MwCity> cities = mwCityRepository.findAllMwCityForPort(portSeq);
        cities.forEach(city -> {
            if (dto.mw_city == null)
                dto.mw_city = new ArrayList<>();
            dto.mw_city.add(city);
        });

        // --------- MW_UC --------- //
        List<MwUc> ucs = mwUcRepository.findAllMwUcForPort(portSeq);
        ucs.forEach(uc -> {
            if (dto.mw_uc == null)
                dto.mw_uc = new ArrayList<>();
            dto.mw_uc.add(uc);
        });
        // --------- MW_THSL ----------- //
        List<MwThsl> thsls = mwThslRepository.findAllMwThslForPort(portSeq);
        thsls.forEach(thsl -> {
            if (dto.mw_thsl == null)
                dto.mw_thsl = new ArrayList<>();
            dto.mw_thsl.add(thsl);
        });

        // --------- MW_DIST -------- //
        List<MwDist> dists = mwDistRepository.findAllMwDistForPort(portSeq);
        dists.forEach(dist -> {
            if (dto.mw_dist == null)
                dto.mw_dist = new ArrayList<>();
            dto.mw_dist.add(dist);
        });
        // --------- MW_ST ------ //
        List<MwSt> states = mwStRepository.findAllMwStForPort(portSeq);
        states.forEach(state -> {
            if (dto.mw_st == null)
                dto.mw_st = new ArrayList<>();
            dto.mw_st.add(state);
        });
        // --------- MW_CNTRY ------- //
        List<MwCntry> cntries = mwCntryRepository.findAllMwCntryForPort(portSeq);
        cntries.forEach(cntry -> {
            if (dto.mw_cntry == null)
                dto.mw_cntry = new ArrayList<>();
            dto.mw_cntry.add(cntry);
        });
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto getCompleteGeographyForBranch(SyncDto dto, long brnchSeq) {
        if (brnchSeq == 0L)
            return dto;
        // ------ MW_BRNCH_LOCATION_REL ------- //
        List<MwBrnchLocationRel> locationRels = mwBrnchLocationRelRepository.findAllByBrnchSeqAndCrntRecFlg(brnchSeq, true);
        List<Long> locRels = new ArrayList<>();

        locationRels.forEach(rel -> {
            if (dto.mw_brnch_location_rel == null)
                dto.mw_brnch_location_rel = new ArrayList<>();
            if (!dto.mw_brnch_location_rel.contains(rel))
                dto.mw_brnch_location_rel.add(rel);
            locRels.add(rel.getCitySeq());
        });

        List<MwCityUcRel> cityUcRels = mwCityUcRelRepository.findAllMwCityUcRelForBranch(brnchSeq);
        cityUcRels.forEach(cityUcRel -> {
            if (dto.mw_city_uc_rel == null)
                dto.mw_city_uc_rel = new ArrayList<>();
            if (!dto.mw_city_uc_rel.contains(cityUcRel))
                dto.mw_city_uc_rel.add(cityUcRel);
        });
        // ---------- MW_CITY --------- //
        List<MwCity> cities = mwCityRepository.findAllMwCityForBranch(brnchSeq);
        cities.forEach(city -> {
            if (dto.mw_city == null)
                dto.mw_city = new ArrayList<>();
            dto.mw_city.add(city);
        });

        // --------- MW_UC --------- //
        List<MwUc> ucs = mwUcRepository.findAllMwUcForBranch(brnchSeq);
        ucs.forEach(uc -> {
            if (dto.mw_uc == null)
                dto.mw_uc = new ArrayList<>();
            dto.mw_uc.add(uc);
        });
        // --------- MW_THSL ----------- //
        List<MwThsl> thsls = mwThslRepository.findAllMwThslForBranch(brnchSeq);
        thsls.forEach(thsl -> {
            if (dto.mw_thsl == null)
                dto.mw_thsl = new ArrayList<>();
            dto.mw_thsl.add(thsl);
        });

        // --------- MW_DIST -------- //
        List<MwDist> dists = mwDistRepository.findAllMwDistForBranch(brnchSeq);
        dists.forEach(dist -> {
            if (dto.mw_dist == null)
                dto.mw_dist = new ArrayList<>();
            dto.mw_dist.add(dist);
        });
        // --------- MW_ST ------ //
        List<MwSt> states = mwStRepository.findAllMwStForBranch(brnchSeq);
        states.forEach(state -> {
            if (dto.mw_st == null)
                dto.mw_st = new ArrayList<>();
            dto.mw_st.add(state);
        });
        // --------- MW_CNTRY ------- //
        List<MwCntry> cntries = mwCntryRepository.findAllMwCntryForBranch(brnchSeq);
        cntries.forEach(cntry -> {
            if (dto.mw_cntry == null)
                dto.mw_cntry = new ArrayList<>();
            dto.mw_cntry.add(cntry);
        });
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto getCompleteOrganizationForBranch(SyncDto dto, long branchSeq) {
        if (branchSeq == 0)
            return dto;
        // ---------- MW_BRNCH ------- //
        MwBrnch brnch = mwBrnchRepository.findOneByBrnchSeqAndCrntRecFlg(branchSeq, true);
        if (brnch != null) {
            if (dto.mw_brnch == null)
                dto.mw_brnch = new ArrayList<>();
            dto.mw_brnch.add(brnch);

            // -------- MW_AREA -------- //
            MwArea area = mwAreaRepository.findOneByAreaSeqAndCrntRecFlg(brnch.getAreaSeq(), true);
            if (area != null) {
                if (dto.mw_area == null)
                    dto.mw_area = new ArrayList<>();
                dto.mw_area.add(area);
                // -------- MW_REG --------- //
                MwReg reg = mwRegRepository.findOneByRegSeqAndCrntRecFlg(area.getRegSeq(), true);
                if (reg != null) {
                    if (dto.mw_reg == null)
                        dto.mw_reg = new ArrayList<>();
                    dto.mw_reg.add(reg);
                }
            }
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto getCompleteProductForBranch(SyncDto dto, long branchSeq) {
        if (branchSeq == 0)
            return dto;

        List<MwBrnchPrdRel> rels = mwBrnchPrdRelRepository.findAllByBrnchSeqAndCrntRecFlg(branchSeq, true);
        rels.forEach(rel -> {
            // ------------ MW_PRD ----------- //
            MwPrd prd = mwPrdRepository.findOneByPrdSeqAndCrntRecFlg(rel.getPrdSeq(), true);
            if (prd != null) {
                MwRefCdVal val = mwRefCdValRepository.findOneByRefCdSeqAndCrntRecFlg(prd.getPrdTypKey(), true);
                if (val != null && !val.getRefCd().equals("1165")) {

                    if (dto.mw_prd == null)
                        dto.mw_prd = new ArrayList<>();
                    dto.mw_prd.add(prd);
                    // ---------- MW_PRD_GRP -------- //
                    MwPrdGrp grp = mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(prd.getPrdGrpSeq(), true);
                    if (grp != null) {
                        if (dto.mw_prd_grp == null) {
                            dto.mw_prd_grp = new ArrayList<>();
                            dto.mw_prd_grp.add(grp);
                        } else if (!dto.mw_prd_grp.contains(grp)) {
                            dto.mw_prd_grp.add(grp);
                        }
                    }
                    // ----------- MW_PRD_LOAN_TRM ----- //
                    List<MwPrdLoanTrm> trms = mwPrdLoanTrmRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    trms.forEach(trm -> {
                        if (dto.mw_prd_loan_trm == null)
                            dto.mw_prd_loan_trm = new ArrayList<>();
                        dto.mw_prd_loan_trm.add(trm);
                    });

                    // ----------- MW_PRD_PPAL_LMTS ----- //
                    List<MwPrdPpalLmt> lmts = mwPrdPpalLmtRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    lmts.forEach(lmt -> {
                        if (dto.mw_prd_ppal_lmts == null)
                            dto.mw_prd_ppal_lmts = new ArrayList<>();
                        dto.mw_prd_ppal_lmts.add(lmt);
                    });
                    // ----------- MW_PRD_FORM_REL ----- //
                    List<MwPrdFormRel> formRels = mwPrdFormRelRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    formRels.forEach(formRel -> {
                        if (dto.mw_prd_form_rel == null)
                            dto.mw_prd_form_rel = new ArrayList<>();
                        dto.mw_prd_form_rel.add(formRel);
                    });
                    // ----------- MW_PRD_DOC_REL ----- //
                    List<MwPrdDocRel> docRels = mwPrdDocRelRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    docRels.forEach(docRel -> {
                        if (dto.mw_prd_doc_rel == null)
                            dto.mw_prd_doc_rel = new ArrayList<>();
                        dto.mw_prd_doc_rel.add(docRel);
                    });

                    // ---------- MW_PRD_CHRG --------- //
                    List<MwPrdChrg> chrgs = mwPrdChrgRepository.findAllByPrdSeqAndCrntRecFlgAndDelFlg(prd.getPrdSeq(), true, false);
                    chrgs.forEach(chrg -> {
                        if (dto.mw_prd_chrg == null)
                            dto.mw_prd_chrg = new ArrayList<>();
                        dto.mw_prd_chrg.add(chrg);

                        List<MwPrdChrgSlb> slbs = mwPrdChrgSlbRepository.findAllByPrdChrgSeqAndCrntRecFlg(chrg.getPrdChrgSeq(), true);
                        slbs.forEach(slb -> {
                            if (dto.mw_prd_chrg_slb == null)
                                dto.mw_prd_chrg_slb = new ArrayList<>();
                            dto.mw_prd_chrg_slb.add(slb);
                        });
                    });
                    // ----------- MW_BIZ_SECT_REL ----------- //
                    List<MwPrdBizSectRel> sectRels = mwPrdBizSectRelRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    sectRels.forEach(sectRel -> {
                        if (dto.mw_prd_biz_sect_rel == null)
                            dto.mw_prd_biz_sect_rel = new ArrayList<>();
                        dto.mw_prd_biz_sect_rel.add(sectRel);
                        // ----------- MW_BIZ_SECT ----------- //
                        MwBizSect sect = mwBizSectRepository.findOneByBizSectSeqAndCrntRecFlg(sectRel.getBizSectSeq(), true);
                        if (sect != null) {
                            if (dto.mw_biz_sect == null)
                                dto.mw_biz_sect = new ArrayList<>();
                            if (!dto.mw_biz_sect.contains(sect))
                                dto.mw_biz_sect.add(sect);
                            // ----------- MW_BIZ_ACTY ----------- //
                            List<MwBizActy> acts = mwBizActyRepository.findAllByBizSectSeqAndCrntRecFlg(sect.getBizSectSeq(), true);
                            acts.forEach(act -> {
                                if (dto.mw_biz_acty == null)
                                    dto.mw_biz_acty = new ArrayList<>();
                                if (!dto.mw_biz_acty.contains(act))
                                    dto.mw_biz_acty.add(act);
                            });
                        }
                    });

                    // --------- MW_PRD_ADV_RUL -------- //
                    List<MwPrdAdvRul> advRuls = mwPrdAdvRulRepository.findAllByPrdSeqAndCrntRecFlg(prd.getPrdSeq(), true);
                    advRuls.forEach(advRul -> {
                        if (dto.mw_prd_adv_rul == null)
                            dto.mw_prd_adv_rul = new ArrayList<>();
                        dto.mw_prd_adv_rul.add(advRul);
                    });
                }
            }
        });

        if (dto.mw_prd != null) {
            dto.mw_prd.forEach(prd -> {
                if (!prd.getCrntRecFlg()) {
                    int i = 0;
                    for (MwPrd p : dto.mw_prd) {
                        if (p.getPrdSeq() != prd.getPrdSeq() && p.getPrdGrpSeq() == prd.getPrdGrpSeq() && p.getCrntRecFlg()) {
                            i = 1;
                        }
                    }
                    if (i == 0) {
                        // MW_PRD_GRP_SEQ //
                        MwPrdGrp grp = mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(prd.getPrdGrpSeq(), true);
                        if (grp != null) {
                            em.detach(grp);
                            if (dto.mw_prd_grp == null)
                                dto.mw_prd_grp = new ArrayList<>();
                            grp.setCrntRecFlg(prd.getCrntRecFlg());
                            grp.setDelFlg(prd.getDelFlg());
                            dto.mw_prd_grp.add(grp);
                        }

                    }
                } else {
                    // MW_PRD_GRP_SEQ //
                    MwPrdGrp grp = mwPrdGrpRepository.findOneByPrdGrpSeqAndCrntRecFlg(prd.getPrdGrpSeq(), true);
                    if (grp != null) {
                        if (dto.mw_prd_grp == null) {
                            dto.mw_prd_grp = new ArrayList<>();
                            dto.mw_prd_grp.add(grp);
                        } else if (!dto.mw_prd_grp.contains(grp)) {
                            dto.mw_prd_grp.add(grp);
                        }
                    }
                }
            });
        }
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto traverseTables(SyncDto dto, long branchSeq, Instant syncDate) {
        if (branchSeq == 0)
            return dto;
        // ================================== PRODUCT ============================== //
        // // --------- MW_PRD_GRP --------- //
        List<MwPrdGrp> grps = mwPrdGrpRepository.findUpdatedMwPrdGrpForBranch(branchSeq, syncDate);
        if (grps != null) {
            grps.forEach(grp -> {
                if (dto.mw_prd_grp == null)
                    dto.mw_prd_grp = new ArrayList<>();
                dto.mw_prd_grp.add(grp);
            });
        }

        // ---------- MW_PRD ----------- //
        List<MwPrd> prds = mwPrdRepository.findUpdatedMwPrdForBrnch(branchSeq, syncDate);
        prds.forEach(prd -> {
            if (dto.mw_prd == null)
                dto.mw_prd = new ArrayList<>();
            if (!dto.mw_prd.contains(prd))
                dto.mw_prd.add(prd);
        });
        // --------- MW_PRD_ADV_RUL -------- //
        List<MwPrdAdvRul> advRuls = mwPrdAdvRulRepository.findUpdatedMwPrdAdvRulForBrnch(branchSeq, syncDate);
        advRuls.forEach(advRul -> {
            if (dto.mw_prd_adv_rul == null)
                dto.mw_prd_adv_rul = new ArrayList<>();
            if (!dto.mw_prd_adv_rul.contains(advRul))
                dto.mw_prd_adv_rul.add(advRul);

        });

        // ------- MW_PRD_LOAN_TRM ------ //
        List<MwPrdLoanTrm> trms = mwPrdLoanTrmRepository.findUpdatedMwPrdLoanTrmForBrnch(branchSeq, syncDate);
        trms.forEach(trm -> {
            if (dto.mw_prd_loan_trm == null)
                dto.mw_prd_loan_trm = new ArrayList<>();
            if (!dto.mw_prd_loan_trm.contains(trm))
                dto.mw_prd_loan_trm.add(trm);
        });
        // --------- MW_PRD_BIZ_SECT_REL -------- //
        List<MwPrdBizSectRel> bizSectRels = mwPrdBizSectRelRepository.findUpdatedMwPrdBizSectRelForBrnch(branchSeq, syncDate);
        bizSectRels.forEach(bizSectRel -> {
            if (dto.mw_prd_biz_sect_rel == null)
                dto.mw_prd_biz_sect_rel = new ArrayList<>();
            if (!dto.mw_prd_biz_sect_rel.contains(bizSectRel))
                dto.mw_prd_biz_sect_rel.add(bizSectRel);
        });
        // ---------- MW_PRD_CHRG ---------- //
        List<MwPrdChrg> chrgs = mwPrdChrgRepository.findUpdatedMwPrdChrgForBrnch(branchSeq, syncDate);
        chrgs.forEach(chrg -> {
            if (dto.mw_prd_chrg == null)
                dto.mw_prd_chrg = new ArrayList<>();
            if (!dto.mw_prd_chrg.contains(chrg))
                dto.mw_prd_chrg.add(chrg);
        });

        List<MwPrdChrgSlb> slbs = mwPrdChrgSlbRepository.findUpdatedMwPrdChrgSlbForBrnch(branchSeq, syncDate);
        slbs.forEach(slb -> {
            if (dto.mw_prd_chrg_slb == null)
                dto.mw_prd_chrg_slb = new ArrayList<>();
            if (!dto.mw_prd_chrg_slb.contains(slb))
                dto.mw_prd_chrg_slb.add(slb);
        });
        // --------- MW_PRD_DOC_REL -------- //
        List<MwPrdDocRel> docRels = mwPrdDocRelRepository.findUpdatedMwPrdDocRelForBrnch(branchSeq, syncDate);
        docRels.forEach(rel -> {
            if (dto.mw_prd_doc_rel == null)
                dto.mw_prd_doc_rel = new ArrayList<>();
            if (!dto.mw_prd_doc_rel.contains(rel))
                dto.mw_prd_doc_rel.add(rel);
        });

        // --------- MW_PRD_FORM_REL --------- //
        List<MwPrdFormRel> formRels = mwPrdFormRelRepository.findUpdatedMwPrdFormRelForBrnch(branchSeq, syncDate);
        formRels.forEach(rel -> {
            if (dto.mw_prd_form_rel == null)
                dto.mw_prd_form_rel = new ArrayList<>();
            if (!dto.mw_prd_form_rel.contains(rel))
                dto.mw_prd_form_rel.add(rel);
        });

        // -------- MW_PRD_PPAL_LMTS ------- //
        List<MwPrdPpalLmt> lmts = mwPrdPpalLmtRepository.findUpdatedMwPrdPpalLmtForBrnch(branchSeq, syncDate);
        lmts.forEach(lmt -> {
            if (dto.mw_prd_ppal_lmts == null)
                dto.mw_prd_ppal_lmts = new ArrayList<>();
            if (!dto.mw_prd_ppal_lmts.contains(lmt))
                dto.mw_prd_ppal_lmts.add(lmt);
        });

        // ================================== ORGANIZATION ============================== //
        // ---------- MW_AREA ------- //
        List<MwArea> areas = mwAreaRepository.findUpdatedMwAreaForBrnch(branchSeq, syncDate);
        areas.forEach(area -> {
            if (dto.mw_area == null)
                dto.mw_area = new ArrayList<>();
            if (!dto.mw_area.contains(area))
                dto.mw_area.add(area);
        });

        // ----------- MW_REG ------- //
        List<MwReg> regs = mwRegRepository.findUpdatedMwRegForBrnch(branchSeq, syncDate);
        regs.forEach(reg -> {
            if (dto.mw_reg == null)
                dto.mw_reg = new ArrayList<>();
            if (!dto.mw_reg.contains(reg))
                dto.mw_reg.add(reg);
        });

        // ------- MW-SECT --------- //
        List<MwBizSect> sects = mwBizSectRepository.findUpdatedMwBizSectForBranch(branchSeq, syncDate);
        sects.forEach(sect -> {
            if (dto.mw_biz_sect == null)
                dto.mw_biz_sect = new ArrayList<>();
            dto.mw_biz_sect.add(sect);
        });

        // -------- MW_BIZ_ACTY -------- //
        List<MwBizActy> acties = mwBizActyRepository.findUpdatedMwBizActyForBranch(branchSeq, syncDate);
        acties.forEach(acty -> {
            if (dto.mw_biz_acty == null)
                dto.mw_biz_acty = new ArrayList<>();
            dto.mw_biz_acty.add(acty);
        });
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto traverseLocationForBranch(SyncDto dto, Instant syncDate, long branchSeq) {
        // ================================== GEOGRAPHY ============================== //
        // --------- MW_BRNCH_LOC_REL ------- //
        List<MwBrnchLocationRel> locRels = mwBrnchLocationRelRepository
            .findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndBrnchSeq(syncDate, true, syncDate, true, branchSeq);
        locRels.forEach(locRel -> {
            if (dto.mw_brnch_location_rel == null)
                dto.mw_brnch_location_rel = new ArrayList<>();
            if (!dto.mw_brnch_location_rel.contains(locRel))
                dto.mw_brnch_location_rel.add(locRel);
        });

        // -------- MW_CITY_UC_REL ------- //
        List<MwCityUcRel> cuRels = mwCityUcRelRepository.findUpdatedMwCityUcRelForBranch(branchSeq, syncDate);
        cuRels.forEach(cuRel -> {
            if (dto.mw_city_uc_rel == null)
                dto.mw_city_uc_rel = new ArrayList<>();
            if (!dto.mw_city_uc_rel.contains(cuRel))
                dto.mw_city_uc_rel.add(cuRel);
        });

        // -------- MW_CITY --------- //
        List<MwCity> cities = mwCityRepository.findUpdatedMwCityForBranch(branchSeq, syncDate);
        cities.forEach(city -> {
            if (dto.mw_city == null)
                dto.mw_city = new ArrayList<>();
            if (!dto.mw_city.contains(city))
                dto.mw_city.add(city);
        });

        // ---------- MW_UC ------- //
        List<MwUc> ucs = mwUcRepository.findUpdatedMwUcForBranch(branchSeq, syncDate);
        ucs.forEach(uc -> {
            if (dto.mw_uc == null)
                dto.mw_uc = new ArrayList<>();
            if (!dto.mw_uc.contains(uc))
                dto.mw_uc.add(uc);
        });

        // ---------- MW_THSL ------- //
        List<MwThsl> thsls = mwThslRepository.findUpdatedMwThslForBranch(branchSeq, syncDate);
        thsls.forEach(thsl -> {
            em.detach(thsl);
            if (dto.mw_thsl == null)
                dto.mw_thsl = new ArrayList<>();
            if (!dto.mw_thsl.contains(thsl))
                dto.mw_thsl.add(thsl);
        });

        List<MwDist> dists = mwDistRepository.findUpdatedMwDistForBranch(branchSeq, syncDate);
        dists.forEach(dist -> {
            em.detach(dist);
            if (dto.mw_dist == null)
                dto.mw_dist = new ArrayList<>();
            if (!dto.mw_dist.contains(dist))
                dto.mw_dist.add(dist);
        });

        List<MwSt> states = mwStRepository.findUpdatedMwStForBranch(branchSeq, syncDate);
        states.forEach(st -> {
            em.detach(st);
            if (dto.mw_st == null)
                dto.mw_st = new ArrayList<>();
            if (!dto.mw_st.contains(st))
                dto.mw_st.add(st);
        });

        // --------- MW_CNTRY --------- //
        List<MwCntry> cntries = mwCntryRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        cntries.forEach(cntry -> {
            em.detach(cntry);
            if (dto.mw_cntry == null)
                dto.mw_cntry = new ArrayList<>();
            if (!dto.mw_cntry.contains(cntry))
                dto.mw_cntry.add(cntry);
        });
        return dto;
    }

    @Transactional(readOnly = true)
    public SyncDto traverseLocationsForPort(SyncDto dto, long portKey, Instant syncDate) {
        if (portKey == 0)
            return dto;
        // -------- MW_PORT_LOC_REL --------- //
        List<MwPortLocationRel> locRels = mwPortLocationRelRepository
            .findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlgAndPortSeq(syncDate, true, syncDate, true, portKey);
        locRels.forEach(locRel -> {
            em.detach(locRel);
            if (dto.mw_port_location_rel == null)
                dto.mw_port_location_rel = new ArrayList<>();
            if (!dto.mw_port_location_rel.contains(locRel))
                dto.mw_port_location_rel.add(locRel);
        });

        // -------- MW_CITY_UC_REL ------- //
        List<MwCityUcRel> cuRels = mwCityUcRelRepository.findUpdatedMwCityUcRelForPort(portKey, syncDate);
        cuRels.forEach(cuRel -> {
            if (dto.mw_city_uc_rel == null)
                dto.mw_city_uc_rel = new ArrayList<>();
            if (!dto.mw_city_uc_rel.contains(cuRel))
                dto.mw_city_uc_rel.add(cuRel);
        });

        // -------- MW_CITY --------- //
        List<MwCity> cities = mwCityRepository.findUpdatedMwCityForPort(portKey, syncDate);
        cities.forEach(city -> {
            em.detach(city);
            if (dto.mw_city == null)
                dto.mw_city = new ArrayList<>();
            if (!dto.mw_city.contains(city))
                dto.mw_city.add(city);
        });

        // ---------- MW_UC ------- //
        List<MwUc> ucs = mwUcRepository.findUpdatedMwUcForPort(portKey, syncDate);
        ucs.forEach(uc -> {
            em.detach(uc);
            if (dto.mw_uc == null)
                dto.mw_uc = new ArrayList<>();
            if (!dto.mw_uc.contains(uc))
                dto.mw_uc.add(uc);

        });

        // ---------- MW_THSL ------- //
        List<MwThsl> thsls = mwThslRepository.findUpdatedMwThslForPort(portKey, syncDate);
        thsls.forEach(thsl -> {
            em.detach(thsl);
            if (dto.mw_thsl == null)
                dto.mw_thsl = new ArrayList<>();
            if (!dto.mw_thsl.contains(thsl))
                dto.mw_thsl.add(thsl);
        });

        List<MwDist> dists = mwDistRepository.findUpdatedMwDistForPort(portKey, syncDate);
        dists.forEach(dist -> {
            em.detach(dist);
            if (dto.mw_dist == null)
                dto.mw_dist = new ArrayList<>();
            if (!dto.mw_dist.contains(dist))
                dto.mw_dist.add(dist);
        });

        List<MwSt> states = mwStRepository.findUpdatedMwStForPort(portKey, syncDate);
        states.forEach(st -> {
            em.detach(st);
            if (dto.mw_st == null)
                dto.mw_st = new ArrayList<>();
            if (!dto.mw_st.contains(st))
                dto.mw_st.add(st);
        });

        // --------- MW_CNTRY --------- //
        List<MwCntry> cntries = mwCntryRepository.findAllByLastUpdDtAfterAndCrntRecFlgOrLastUpdDtAfterAndDelFlg(syncDate, true, syncDate,
            true);
        cntries.forEach(cntry -> {
            em.detach(cntry);
            if (dto.mw_cntry == null)
                dto.mw_cntry = new ArrayList<>();
            if (!dto.mw_cntry.contains(cntry))
                dto.mw_cntry.add(cntry);
        });

        return dto;
    }

    @Transactional(readOnly = true)
    public boolean isProductAssociatedToBranch(long productSeq, long branchSeq) {
        if (productSeq == 0 || branchSeq == 0)
            return false;
        MwBrnchPrdRel rel = mwBrnchPrdRelRepository.findOneByBrnchSeqAndPrdSeqAndCrntRecFlg(branchSeq, productSeq, true);
        if (rel != null)
            return true;
        return false;
    }

    @Transactional(readOnly = true)
    public boolean isCURelAssociatedToBranch(long cityUcRelSeq, long brnchSeq) {
        if (cityUcRelSeq == 0 || brnchSeq == 0)
            return false;
        MwBrnchLocationRel rel = mwBrnchLocationRelRepository.findOneByBrnchSeqAndCitySeqAndCrntRecFlg(brnchSeq, cityUcRelSeq, true);
        if (rel != null)
            return true;
        return false;
    }

    @Transactional(readOnly = true)
    public boolean isCURelAssociatedToPort(long cityUcRelSeq, long portkey) {
        if (cityUcRelSeq == 0 || portkey == 0)
            return false;
        MwPortLocationRel rel = mwPortLocationRelRepository.findOneByPortSeqAndCitySeqAndCrntRecFlg(portkey, cityUcRelSeq, true);
        if (rel != null)
            return true;
        return false;
    }

    /*Added By Rizwan Mahfooz - Dated 26-1-2022
     * DB FUNCTION TO PERFORM SETUP SYNC*/
    @Transactional
    public Map<String, Object> getDataForDeviceRegisteredTablet(String mac, Boolean completeDataRequest) throws ParseException, SQLException, IOException {
        log.info("SetupSync-> mac: " + mac + ", CompleteData: " + completeDataRequest);
        ObjectMapper mapper = new ObjectMapper();
        String request = "";
        Map<String, Object> mapRespObj = new HashMap<>();
        if (completeDataRequest) {
            request = "1";
        } else {
            request = "0";
        }
        try {
            Query qry = em.createNativeQuery("select FN_SETUP_SYNC (" + mac + "," + request + ") from dual");
            Object fnResp = qry.getSingleResult();

            if (fnResp != null) {
                Clob clob = (Clob) fnResp;
                mapRespObj = (Map<String, Object>) mapper.readValue(clob.getSubString(1, (int) clob.length()), Object.class);
            }
        } catch (Exception ex) {
            log.error("DataSyncException: " + ex.getMessage());
            mapRespObj.put("error", ex.getMessage());
            //ex.printStackTrace();
        }
        return mapRespObj;
    }
    //End

}
