
package com.tunyun.product.ebc.web.impl;

import com.tunyun.product.ebc.bgmanager.serv.*;
import com.tunyun.product.ebc.web.idl.DataServer.Iface;
import com.tunyun.product.ebc.web.server.*;
import com.tunyun.product.ebc.web.server.system.SystemServ;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口实现类
 *
 * @author czm
 */
public class IRuleFace implements Iface {

    // log
    private Logger log = LoggerFactory.getLogger(IRuleFace.class);
    // server
    private SystemServ system;
    private EbcapiServer server;
    private ChannelServ channelServ;
    private IndustryInfoServ industry;
    private CategoryServ categoryServ;
    private GoodsDetailServ goodsDetailServ;
    private IndustryAnalysisServ induanalysis;
    private CompeteAnalysisServ companlysis;
    private ProductServ productServ;
    private AttrServ attrServ;
    private AttrValueServ attrValueServ;
    private AttrRuleServ attrRuleServ;
    private TrWordServ trWordServ;
    private NewProdServ newProdServ;
    private NewProdConfigServ newProdConfigServ;
    private CommonReqServ commonReqServ;
    /**
     * 首页
     *
     * @param item
     * @param input
     * @return
     */
    private String ebcWebIndex(String item, String input) {
        switch (item) {
            case "10010001": {
                return TestServ.mainData(input);
            }
        }
        return "";
    }

    /**
     * 产业前沿
     *
     * @param item
     * @param input
     * @return
     */
    private String ebcWebInduForefront(String item, String input) {
        switch (item) {
            case "10020101": {
                return industry.industryDyna(input);
            }
            case "10020201": {
                return server.getNetworkAppr(input);
            }
            case "10020001": {
                return industry.industryDynaDetail(input);
            }
            case "10020203": {
                // 钻取
                return server.getPolicySentimentDrill(input);
            }
            case "10020204": {
                // 钻取详情
                return server.getPolicySentimentDrillDetail(input);
            }
        }
        return "";
    }

    /**
     * 产业分析
     *
     * @param item
     * @param input
     * @return
     */
    private String ebcWebInduAnalysis(String item, String input) {
        switch (item) {
            case "10030101": {
                return induanalysis.comAnalysis(input);
            }
            case "10030201": {
                return induanalysis.catAnalysis(input);
            }
            case "10030301": {
                return induanalysis.chanAnalysis(input);
            }
        }
        return "";
    }

    /**
     * 竞争分析
     *
     * @param item
     * @param input
     * @return
     */
    private String ebcWebCompetitionAnalysis(String item, String input) {
        switch (item) {
            case "10050101": {
                return companlysis.brandAnalysis(input);
            }
            case "10050201": {
                return companlysis.shopAnalysis(input);
            }
            case "10050301": {
                return companlysis.prodAnalysis(input);
            }
        }
        return "";
    }
    /**
     * 新品研发
     *
     * @param item
     * @param input
     * @return
     */
    private String ebcWebNewProd(String item, String input) {
        switch (item) {
            case "10100101": {
                return newProdServ.queryProgrammes(input);
            }case "10100102": {
                return newProdServ.queryProgrammeAttrs(input);
            }case "10100103": {
                return newProdServ.queryTrConfigTypes(input);
            }case "10100104": {
                return newProdServ.queryTimeList(input);
            }case "10100105": {
                return newProdServ.queryProgrammeTypes(input);
            }
        }
        return "";
    }


    /**
     * 后台管理
     *
     * @param item
     * @param input
     * @return
     */
    private String bgManager(String item, String input) {
        switch (item) {
            case "99990000": {
                return categoryServ.getCategoryJson();
            }
            case "99990001": {
                // return attributeAduitServ.getAttributeAduit(input);
                return attrServ.queryApproveAttrs(input);
            }
            // case "99990002": {
            // return attributeAduitServ.saveAttributeAduitData(input);
            // }
            case "99990003": {
                return attrValueServ.queryAttrs(input);
            }
            case "99990004": {
                // return attributeValueAduitServ.getAttributeValueAduit(input);
                return attrValueServ.queryApproveAttrValues(input);
            }
            // case "99990005": {
            // return attributeValueAduitServ.saveAttributeValueAduitData(input);
            // }
            case "99990006": {
                return goodsDetailServ.goodsDetail(input);
            }
            case "99990007": {
                return channelServ.getChannelJson();
            }
            // case "99990008": {
            // return attrValueServ.downLoadAttributeValueAduit(input);
            // }
            case "99990009": {
                return productServ.searachCategs(input);
            }
            case "99990010": {
                return productServ.searachProduct(input);
            }
            case "99990011": {
                return attrValueServ.delAttrValue(input);
            }
            case "99990012": {
                return attrValueServ.mergeAttrValue(input);
            }
            case "99990013": {
                return attrValueServ.splitAttrValue(input);
            }
            case "99990014": {
                return attrValueServ.addAttrValue(input);
            }
            case "99990015": {
                return attrValueServ.queryAttrValueDetails(input);
            }
            case "99990016": {
                return attrServ.delAttrs(input);
            }
            case "99990017": {
                return attrServ.mergeAttrs(input);
            }
            case "99990018": {
                return attrServ.splitAttrs(input);
            }
            case "99990019": {
                return attrServ.addAttrs(input);
            }
            case "99990020": {
                return attrServ.queryAttrsDetails(input);
            }
            case "99990021": {
                return attrRuleServ.queryAttrRules(input);
            }
            case "99990022": {
                return attrRuleServ.saveAttrRules(input);
            }
            case "99990023": {
                return attrRuleServ.clearAttrRules(input);
            }
            case "99990024": {
                return attrRuleServ.runShell(input);
            }
            case "99990025": {
                return attrRuleServ.testQueryAttrRules(input);
            }
            case "99990026": {
                return attrRuleServ.configQueryAttrRules(input);
            }
            case "99990027": {
                return attrRuleServ.configSaveAttrRules(input);
            }
            case "99990028": {
                return attrRuleServ.regtestQueryAttrRules(input);
            }
            case "99990029": {
                return attrRuleServ.queryAttrs(input);
            }
            case "99990030": {
                return attrValueServ.queryApprovedAttrValues(input);
            }
            case "99990031": {
                return attrValueServ.queryNotApprovedAttrValues(input);
            }
            case "99990032": {
                return trWordServ.queryWordFeatures(input);
            }
            case "99990033": {
                return trWordServ.saveWordFeatures(input);
            }
            case "99990034": {
                return trWordServ.delWordFeatures(input);
            }
            case "99990035": {
                return trWordServ.updateWordFeatures(input);
            }case "99990036": {
                return trWordServ.shell(input);
            }case "99990037": {
                return newProdConfigServ.query(input);
            }case "99990038": {
                return newProdConfigServ.save(input);
            }
        }
        return "";
    }

    /**
     * 系统管理
     *
     * @param item
     * @param input
     * @return
     */
    private String sysManager(String item, String input) {
        switch (item) {
            case "00000001": {
                // 系统登录验证
                return system.loginAuthentication(input);
            }
            case "00000002": {
                // 获取用户访问权限
                return system.accessPermission(input);
            }
            case "00000003": {
                // 用户密码修改
                return system.changePasswd(input);
            }
        }
        return "";
    }

    @Override
    public String execute(String model, String item, String inputStr) throws TException {
        log.info("接口调用     model=" + model + "  item=" + item + "  inputStr=" + inputStr);
        switch (model) {
            case "0000": {
                // 系统登录验证
                return this.sysManager(item, inputStr);
            }
            case "1001": {
                // 首页
                return this.ebcWebIndex(item, inputStr);
            }
            case "1002": {
                // 产业前沿
                return this.ebcWebInduForefront(item, inputStr);
            }
            case "1003": {
                // 产业分析
                return this.ebcWebInduAnalysis(item, inputStr);
            }
            case "1005": {
                // 竞争分析
                return this.ebcWebCompetitionAnalysis(item, inputStr);
            }
            case "1010": {
                // 新品研发
                return this.ebcWebNewProd(item, inputStr);
            }case "8888": {
                // 通用查询
                return this.commonReq(item, inputStr);
            }
            case "9999": {
                // 后台管理
                return this.bgManager(item, inputStr);
            }
        }
        log.error("interface is not found!");
        return null;
    }

    private String commonReq(String item, String inputStr) {
        String result="";
        switch (item) {
            case "0"://查询行业
                result=commonReqServ.queryTrIndu(inputStr);
                break;
            case "1"://根据行业查询品类
                result=commonReqServ.queryCatesByIndu(inputStr);
                break;
        }
        return result;
    }

    public void setServer(EbcapiServer server) {
        this.server = server;
    }

    public void setIndustry(IndustryInfoServ industry) {
        this.industry = industry;
    }

    public void setInduanalysis(IndustryAnalysisServ induanalysis) {
        this.induanalysis = induanalysis;
    }

    public void setCompanlysis(CompeteAnalysisServ companlysis) {
        this.companlysis = companlysis;
    }

    public void setSystem(SystemServ system) {
        this.system = system;
    }

    public void setCategoryServ(CategoryServ categoryServ) {
        this.categoryServ = categoryServ;
    }

    public void setChannelServ(ChannelServ channelServ) {
        this.channelServ = channelServ;
    }

    public void setGoodsDetailServ(GoodsDetailServ goodsDetailServ) {
        this.goodsDetailServ = goodsDetailServ;
    }

    public void setProductServ(ProductServ productServ) {
        this.productServ = productServ;
    }

    public void setAttrValueServ(AttrValueServ attrValueServ) {
        this.attrValueServ = attrValueServ;
    }

    public void setAttrServ(AttrServ attrServ) {
        this.attrServ = attrServ;
    }

    public void setAttrRuleServ(AttrRuleServ attrRuleServ) {
        this.attrRuleServ = attrRuleServ;
    }

    public void setTrWordServ(TrWordServ trWordServ) {
        this.trWordServ = trWordServ;
    }

    public void setNewProdServ(NewProdServ newProdServ) {
        this.newProdServ = newProdServ;
    }

    public void setNewProdConfigServ(NewProdConfigServ newProdConfigServ) {
        this.newProdConfigServ = newProdConfigServ;
    }

    public void setCommonReqServ(CommonReqServ commonReqServ) {
        this.commonReqServ = commonReqServ;
    }
}