package com.kpleasing.crm.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kpleasing.crm.ejb.config.Configurate;
import com.kpleasing.crm.ejb.entity.RCustAttachment;
import com.kpleasing.crm.ejb.entity.TbAttachmentInfo;
import com.kpleasing.crm.ejb.entity.TbAuditingPlan;
import com.kpleasing.crm.ejb.entity.TbParameterConfig;
import com.kpleasing.crm.ejb.entity.TbPersonalAccountInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalContactRelation;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerDetailInfo;
import com.kpleasing.crm.ejb.entity.TbPersonalCustomerInfo;
import com.kpleasing.crm.ejb.entity.TbReserverCustomer;
import com.kpleasing.crm.ejb.exception.CRMException;
import com.kpleasing.crm.ejb.util.StringUtil;
import com.kpleasing.crm.service.EjbService;
import com.kpleasing.crm.service.SyncCustomerInfoService;
import com.kpleasing.crm.util.FileUtil;
import com.kpleasing.crm.util.POIUtil;

@Controller
@RequestMapping(value = "/crm")
public class CRMController {

	private static Logger logger = Logger.getLogger(CRMController.class);

	@Autowired
	private EjbService ejbService;

	@Autowired
	private SyncCustomerInfoService syncCustomerInfoService;

	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String doTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "main";
	}

	@RequestMapping(value = "customer_info")
	public String customerInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "customer/customer_info";
	}

	@RequestMapping(value = "customer_add")
	public ModelAndView customerAddPage(TbPersonalCustomerInfo customerInfo, String operator) {
		ModelMap model = new ModelMap();
		try {
			if (null == customerInfo || customerInfo.getCustId() == 0) {
				return new ModelAndView("customer/customer_add");
			} else {
				Map<String, Object> map = ejbService.getCustServ().getCustAndDetailInfoMap(customerInfo);
				model.addAllAttributes(map);
				if (operator != null && "view".equals(operator)) {
					return new ModelAndView("customer/customer_add_readonly", model);
				} else {
					return new ModelAndView("customer/customer_add", model);
				}
			}
		} catch (Exception e) {
			logger.error("用户新增修改异常：" + e.getMessage(), e);
			return new ModelAndView("customer_info");
		}
	}

	@RequestMapping(value = "video_auth")
	public String videoAuthPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "customer/video_auth";
	}
	
	@RequestMapping(value = "param_config")
	public String paraManagePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "sys/param_config";
	}

	@RequestMapping(value = "vender_info")
	public String venderInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "vender/vender_info";
	}

	@RequestMapping(value = "import_customer")
	public String custInfoExcelUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "customer/import_customer";
	}

	@RequestMapping(value = "upload_attach")
	public String custInfoAttachUpload(String attachmentType, int custId, int id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("attachmentType", attachmentType);
		request.setAttribute("custId", custId);
		request.setAttribute("id", id);
		return "customer/upload_attach";
	}

	@RequestMapping(value = "reserver_info")
	public String reserverInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "customer/reserver_info";
	}
	
	/**
	 * 获取ComboBox参数
	 * 
	 * @param parentNodeId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_comb_param", method = RequestMethod.GET)
	public @ResponseBody String getCombParamList(int parentNodeId, String valueField, String textField) {
		if (parentNodeId == 0) {
			return "[]";
		}

		List<TbParameterConfig> list = ejbService.getSysServ().getCombParamList(parentNodeId);
		if (null == list || list.size() == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		sb.append("{\"" + valueField + "\":\"\",\"" + textField + "\":\"--请选择--\"}");
		for (TbParameterConfig pc : list) {
			sb.append(",{\"" + valueField + "\": \"" + pc.getNodeCode() + "\"");
			sb.append(",\"" + textField + "\": \"" + pc.getNodeValue() + "\"");
			sb.append("}");
		}
		sb.append("]");

		logger.debug(sb.toString());
		return sb.toString();
	}

	@RequestMapping(value = "/get_industry_combo_tree", method = RequestMethod.GET)
	public @ResponseBody String getIndustryComboTree(int parentNodeId) {

		String JsonStr = ejbService.getSysServ().getComboTreeList(parentNodeId);

		logger.debug("combo tree json：" + JsonStr);
		return JsonStr;
	}

	/**
	 * 视频面签信息列表
	 * @param tbAuditingPlan
	 * @return
	 */
	@RequestMapping(value = "/video_auth_list", method = RequestMethod.POST)
	public @ResponseBody String getVideoAuthList(TbAuditingPlan tbAuditingPlan) {
		try {
			logger.info("分页显示：第" + tbAuditingPlan.getPage() + "页，每页" + tbAuditingPlan.getRows() + "条。");
			List<TbAuditingPlan> list = ejbService.getCustServ().getAuditingPlanListByPorperty(tbAuditingPlan);

			if (null == list || list.size() == 0) {
				return "[]";
			}
			int count = ejbService.getCustServ().getAuditingPlanListCount(tbAuditingPlan);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total\":" + count + ",\"page\":" + tbAuditingPlan.getPage() + ",\"rows\":[");
			for (TbAuditingPlan ap : list) {
				String tmp = "";
				if (ap != null) {
					TbPersonalCustomerInfo ci = ejbService.getCustServ().getCustInfoByID(ap.getCustId());
					sb.append("{\"id\":" + ap.getId());
					sb.append(",\"custId\":" + ap.getCustId());
					sb.append(",\"custName\":\"" + (ci.getCustName() == null ? "" : ci.getCustName()) + "\"");
					sb.append(",\"certCode\":\"" + (ci.getCertCode() == null ? "" : ci.getCertCode()) + "\"");
					sb.append(",\"phone\":\"" + (ci.getPhone() == null ? "" : ci.getPhone()) + "\"");
					sb.append(",\"firstDate\":\"" + (ap.getFirstDate() == null ? "" : ap.getFirstDate()) + "\"");
					sb.append(",\"secondDate\":\"" + (ap.getSecondDate() == null ? "" : ap.getSecondDate()) + "\"");
					sb.append(",\"audiFlag\":\"" + ap.getAudiFlag() + "\"");
					tmp = ejbService.getSysServ().getParamValueByCode(246, ap.getAudiFlag()+"");
					sb.append(",\"audiFlag_n\":\"" + tmp + "\"");
					sb.append(",\"remark\":\"" + (ap.getRemark() == null ? "" : ap.getRemark()) + "\"},");
				}
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");

			//logger.info("video_auth list : " + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}
	
	/**
	 * 更新视频面签信息
	 * @param tbAuditingPlan
	 * @return
	 */
	@RequestMapping(value = "/update_video_auth", method = RequestMethod.POST)
	public @ResponseBody String updateVideoAuthInfo(TbAuditingPlan tbAuditingPlan){
		StringBuffer sb = new StringBuffer();
		try {
			if (null == tbAuditingPlan) {
				return "";
			}
			ejbService.getCustServ().saveVideoAuthInfo(tbAuditingPlan);
			sb.append("{\"result\":\"SUCCESS\",\"message\":\"更新成功！\"");
			sb.append("}");
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sb.append("{\"result\":\"FAILED\",\"message\":\"更新失败!\"}");
			return sb.toString();
		}
	}
	
	/**
	 * 
	 * @param customerInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/customer_list", method = RequestMethod.POST)
	public @ResponseBody String getCustomerList(TbPersonalCustomerInfo customerInfo) {
		try {
			logger.info("分页显示：第" + customerInfo.getPage() + "页，每页" + customerInfo.getRows() + "条。");
			List<TbPersonalCustomerInfo> list = ejbService.getCustServ().getCustInfoListByPorperty(customerInfo);
			
			if (null == list || list.size() == 0) {
				logger.info("查询用户信息不存在！");
				return "[]";
			}
			int count = ejbService.getCustServ().getCustInfoListCount(customerInfo);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total\":" + count + ",\"page\":" + customerInfo.getPage() + ",\"rows\":[");
			for (TbPersonalCustomerInfo ci : list) {
				if (ci != null) {
					String tmp = "";
					sb.append("{\"custId\":" + ci.getCustId());
					sb.append(",\"custName\":\"" + (ci.getCustName() == null ? "" : ci.getCustName()) + "\"");
					sb.append(",\"certCode\":\"" + (ci.getCertCode() == null ? "" : ci.getCertCode()) + "\"");
					sb.append(",\"phone\":\"" + (ci.getPhone() == null ? "" : ci.getPhone()) + "\"");
					sb.append(",\"createAt\":\"" + (ci.getCreateAt() == null ? "" : ci.getCreateAt()) + "\"");
					sb.append(",\"custType\":\"" + ci.getCustType() + "\"");
					if (ci.getCustType() != null && ci.getCustType() != "") {
						tmp = ejbService.getSysServ().getParamValueByCode(69, ci.getCustType());
						sb.append(",\"custType_n\":\"" + tmp + "\"");
					}
					sb.append(",\"channel\":\"" + ci.getChannel() + "\"");
					if (ci.getChannel() != null && ci.getChannel() != "") {
						tmp = ejbService.getSysServ().getParamValueByCode(76, ci.getChannel());
						sb.append(",\"channel_n\":\"" + tmp + "\"");
					}
					if (ci.getCustStatus() != null && ci.getCustStatus() != "") {
						tmp = ejbService.getSysServ().getParamValueByCode(73, ci.getCustStatus());
						sb.append(",\"custStatus_n\":\"" + tmp + "\"");
					}
					sb.append(",\"custStatus\":\"" + ci.getCustStatus() + "\"},");
				}
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");
			
			logger.debug("customer list : " + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}

	/**
	 * 保存客户基本信息
	 * 
	 * @param request
	 * @param customerInfo
	 * @param detailCustomerInfo
	 * @return
	 * @throws IOException
	 * @throws CRMException
	 */
	@RequestMapping(value = "/save_base_info")
	public @ResponseBody String saveBaseInfo(TbPersonalCustomerInfo customerInfo,
			TbPersonalCustomerDetailInfo detailCustomerInfo) {
		StringBuffer sb = new StringBuffer();
		try {
			int count = ejbService.getCustServ().getCustCountByPhone(customerInfo.getPhone());
			// 判断新增或修改操作
			if (customerInfo.getCustId() == 0) {
				if (count > 0) {
					sb.append("{\"result\":\"FAILED\",\"message\":\"手机号在系统中已存在！\"}");
					return sb.toString();
				}
				ejbService.getCustServ().saveCustInfoAndDetail(customerInfo, detailCustomerInfo);

			} else {
				if (count > 1) {
					sb.append("{\"result\":\"FAILED\",\"message\":\"手机号在系统中已存在！\"}");
					return sb.toString();
				}
				ejbService.getCustServ().updateCustInfoAndDetail(customerInfo, detailCustomerInfo);
			}
			sb.append("{\"result\":\"SUCCESS\",\"message\":\"保存成功！\"").append(",\"custId\":" + customerInfo.getCustId())
					.append("}");

			logger.debug("保存客户基本信息结果：" + sb.toString());
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sb.append("{\"result\":\"FAILED\",\"message\":\"客户信息保存失败!\"}");
			return sb.toString();
		}
	}

	/**
	 * 获取联系人
	 * 
	 * @param customerInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_contactrel_list", method = RequestMethod.POST)
	public @ResponseBody String getContactInfo(TbPersonalContactRelation contactInfo) {
		try {
			if (contactInfo == null || contactInfo.getCustId() == 0) {
				return "[]";
			}
			List<TbPersonalContactRelation> list = ejbService.getCustServ().getCustomerContactList(contactInfo);
			if (null == list || list.size() == 0) {
				return "[]";
			}
			int count = ejbService.getCustServ().getCustomerContactCount(contactInfo);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total\":" + count + ",\"page\":" + contactInfo.getPage() + ",\"rows\":[");
			for (TbPersonalContactRelation cr : list) {
				String tmp = "";
				sb.append("{\"id\":" + cr.getId());
				sb.append(",\"contactName\":\"" + (cr.getContactName() == null ? "" : cr.getContactName()) + "\"");
				sb.append(",\"isImportantContact\":\""
						+ (cr.getIsImportantContact() == null ? "" : cr.getIsImportantContact()) + "\"");
				sb.append(",\"relation\":\"" + (cr.getRelation() == null ? "" : cr.getRelation()) + "\"");
				if (cr.getRelation() != null && cr.getRelation() != "") {
					tmp = ejbService.getSysServ().getParamValueByCode(4, cr.getRelation());
					sb.append(",\"relation_n\":\"" + tmp + "\"");
				}
				sb.append(",\"contactCertType\":\"" + (cr.getContactCertType() == null ? "" : cr.getContactCertType())
						+ "\"");
				if (cr.getContactCertType() != null && cr.getContactCertType() != "") {
					tmp = ejbService.getSysServ().getParamValueByCode(13, cr.getContactCertType());
					sb.append(",\"contactCertType_n\":\"" + tmp + "\"");
				}
				sb.append(",\"contactCertCode\":\"" + (cr.getContactCertCode() == null ? "" : cr.getContactCertCode())
						+ "\"");
				sb.append(",\"contactPhone\":\"" + (cr.getContactPhone() == null ? "" : cr.getContactPhone()) + "\"");
				sb.append(",\"contactFax\":\"" + (cr.getContactFax() == null ? "" : cr.getContactFax()) + "\"");
				sb.append(",\"contactEmail\":\"" + (cr.getContactEmail() == null ? "" : cr.getContactEmail()) + "\"");
				sb.append(",\"contactAddr\":\"" + (cr.getContactAddr() == null ? "" : cr.getContactAddr()) + "\"");
				sb.append(",\"contactWorkUnit\":\"" + (cr.getContactWorkUnit() == null ? "" : cr.getContactWorkUnit())
						+ "\"");
				sb.append(",\"isSendSms\":\"" + (cr.getIsSendSms() == null ? "" : cr.getIsSendSms()) + "\"},");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");
			logger.debug("联系人信息：" + sb.toString());
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}

	/**
	 * 保存联系人
	 * 
	 * @param contactRelation
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save_contact_info")
	public @ResponseBody String saveContactInfo(TbPersonalContactRelation contactRelation) {
		StringBuffer sb = new StringBuffer();
		try {
			if (null == contactRelation) {
				return "";
			}
			ejbService.getCustServ().saveContactRelation(contactRelation);
			sb.append("{\"result\":\"SUCCESS\",\"message\":\"保存成功！\"");
			sb.append(",\"id\":" + contactRelation.getId());
			sb.append("}");
			logger.debug("保存联系人结果：" + sb.toString());
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sb.append("{\"result\":\"FAILED\",\"message\":\"联系人信息保存失败!\"}");
			return sb.toString();
		}
	}

	/**
	 * 删除联系人
	 * 
	 * @param contactRelation
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete_contact_info")
	public @ResponseBody String deleteContactInfo(TbPersonalContactRelation contactRelation) {
		String status = "";
		try {
			if (contactRelation.getId() == 0) {
				status = "ERROR";
			} else {
				ejbService.getCustServ().deleteContactRelation(contactRelation);
				status = "SUCCESS";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = "ERROR";
		}
		return status;
	}

	/**
	 * 获取银行账户信息
	 * 
	 * @param customerInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_bankacc_list", method = RequestMethod.POST)
	public @ResponseBody String getBankAccountList(TbPersonalAccountInfo accountInfo) {
		try {
			if (accountInfo == null || accountInfo.getCustId() == 0) {
				return "[]";
			}
			List<TbPersonalAccountInfo> list = ejbService.getCustServ().getAccountInfoList(accountInfo);
			if (null == list || list.size() == 0) {
				return "[]";
			}
			int count = ejbService.getCustServ().getAccountInfoCount(accountInfo);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total\":" + count + ",\"page\":" + accountInfo.getPage() + ",\"rows\":[");
			for (TbPersonalAccountInfo ai : list) {
				String tmp = "";

				sb.append("{\"id\":" + ai.getId());
				sb.append(",\"accName\":\"" + (ai.getAccName() == null ? "" : ai.getAccName()) + "\"");
				sb.append(",\"bankCode\":\"" + (ai.getBankCode() == null ? "" : ai.getBankCode()) + "\"");
				if (ai.getBankCode() != null && ai.getBankCode() != "") {
					tmp = ejbService.getSysServ().getParamValueByCode(86, ai.getBankCode());
					sb.append(",\"bankCode_n\":\"" + tmp + "\"");
				}
				sb.append(",\"bankFullName\":\"" + (ai.getBankFullName() == null ? "" : ai.getBankFullName()) + "\"");
				sb.append(",\"branchBankName\":\"" + (ai.getBranchBankName() == null ? "" : ai.getBranchBankName())
						+ "\"");
				sb.append(",\"accNo\":\"" + (ai.getAccNo() == null ? "" : ai.getAccNo()) + "\"");
				sb.append(",\"bankPhone\":\"" + (ai.getBankPhone() == null ? "" : ai.getBankPhone()) + "\"");
				sb.append(",\"abbreviationCardNo\":\"" + (ai.getAbbreviationCardNo() == null ? "" : ai.getAbbreviationCardNo()) + "\"");
				sb.append(",\"isYjzfBind\":\"" + ai.getIsYjzfBind() + "\"");
				sb.append(",\"currency\":\"" + (ai.getCurrency() == null ? "" : ai.getCurrency()) + "\"");
				if (ai.getCurrency() != null && ai.getCurrency() != "") {
					tmp = ejbService.getSysServ().getParamValueByCode(103, ai.getCurrency());
					sb.append(",\"currency_n\":\"" + tmp + "\"");
				}
				sb.append(
						",\"isWithholdAcc\":\"" + (ai.getIsWithholdAcc() == null ? "" : ai.getIsWithholdAcc()) + "\"");
				sb.append(",\"withholdUnit\":\"" + (ai.getWithholdUnit() == null ? "" : ai.getWithholdUnit()) + "\"");
				if (ai.getWithholdUnit() != null && ai.getWithholdUnit() != "") {
					tmp = ejbService.getSysServ().getParamValueByCode(106, ai.getWithholdUnit());
					sb.append(",\"withholdUnit_n\":\"" + tmp + "\"");
				}
				sb.append(",\"accStatus\":\"" + (ai.getAccStatus() == null ? "" : ai.getAccStatus()) + "\"},");
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");
			logger.debug("银行账户信息：" + sb.toString());
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}

	/**
	 * 保存银行账户
	 * 
	 * @param accountInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/save_bankacc_info", method = RequestMethod.POST)
	public @ResponseBody String saveBankAccountInfo(TbPersonalAccountInfo accountInfo) {
		StringBuffer sb = new StringBuffer();
		try {
			if (null == accountInfo) {
				return "";
			}
			ejbService.getCustServ().saveAccountInfo(accountInfo);
			sb.append("{\"result\":\"SUCCESS\",\"message\":\"保存成功！\"");
			sb.append(",\"id\":" + accountInfo.getId());
			sb.append("}");
			logger.debug("保存银行账户结果：" + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sb.append("{\"result\":\"FAILED\",\"message\":\"银行账户保存失败!\"}");
			return sb.toString();
		}

	}

	/**
	 * 删除银行账户
	 * 
	 * @param accountInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/delete_bankacc_info", method = RequestMethod.POST)
	public @ResponseBody String deleteBankAccountInfo(TbPersonalAccountInfo accountInfo) {
		String status = "";
		try {
			if (accountInfo.getId() == 0) {
				status = "ERROR";
			} else {
				ejbService.getCustServ().deleteAccountInfo(accountInfo);
				status = "SUCCESS";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = "ERROR";
		}
		return status;

	}

	/**
	 * 提交客户信息
	 * 
	 * @param customerInfo
	 * @return
	 */
	@RequestMapping(value = "/submit_cust_info", method = RequestMethod.POST)
	public @ResponseBody String submitCustInfo(TbPersonalCustomerInfo customerInfo) {
		try {
			String result = syncCustomerInfoService.syncCustomerInfo(customerInfo.getCustId(), "");
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "ERROR";
		}
	}

	/**
	 * Excel导入客户信息
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/excel_import_customer")
	public ModelAndView excelImportCustInfo(HttpServletRequest request, HttpServletResponse response,
			MultipartFile file) {
		List<String[]> list = new ArrayList<String[]>();
		ModelMap model = new ModelMap();
		String result = "";
		try {
			logger.info("获取导入excel内客户List....");
			list = POIUtil.readExcel(file);
			if (list != null && list.size() > 1) {
				String[] str = (String[]) list.get(0);
				if (!"姓名".equals(str[0]) && str.length != 23) {
					model.put("result", "导入模板有误，请确认后重新导入");
					return new ModelAndView("customer/import_customer", model);
				}
				ejbService.getCustServ().saveExcelImportCustInfo(list);
			} else {
				model.put("result", "请导入至少一条数据");
				return new ModelAndView("customer/import_customer", model);
			}
			result = "SUCCESS";

		} catch (IOException e) {
			result = e.getMessage();
			logger.error("附件导入异常：" + e.getMessage(), e);
			e.printStackTrace();
		} catch (CRMException e) {
			result = e.getDescription();
			logger.error("客户信息保存失败：" + e.getDescription(), e);
			e.printStackTrace();
		}
		model.put("result", result);
		return new ModelAndView("customer/import_customer", model);
	}

	/**
	 * Excel模板下载
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/excel_templete_download")
	public void excelTempleteDownload(HttpServletRequest request, HttpServletResponse response) {
		Configurate config = ejbService.getConfigServ().getConfig();
		String path = config.MODEL_FILE_PATH;
		response.setContentType("application/vnd.ms-excel");
		String title = POIUtil.processFileName(request, "客户信息收集表导入模板.xlsx");
		response.setHeader("Content-Disposition", "attachment;fileName=" + title);

		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(new File(path));
			os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length = -1;
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传客户信息附件
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/cust_upload_attach")
	public ModelAndView uploadCustInfoAttach(MultipartFile file, HttpServletRequest request,
			RCustAttachment rCustAttachment, TbAttachmentInfo tbAttachmentInfo) {
		Configurate config = ejbService.getConfigServ().getConfig();
		ModelMap model = new ModelMap();
		String result = "";
		try {
			int custId = rCustAttachment.getCustId();
			if (!file.isEmpty()) {
				String path = config.LOCAL_ATTACH_PATH;
				String fileName = file.getOriginalFilename();
				String fileTypeCode = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				String newFileName = StringUtil.getDateSerialNo6() + "." + fileTypeCode;

				File f = new File(path + File.separator + custId);
				if (!f.exists() && !f.isDirectory()) {
					f.mkdirs();
				}

				String localPath = path + File.separator + custId + File.separator + newFileName;
				File file1 = new File(localPath); // 新建一个文件
				FileUtils.copyInputStreamToFile(file.getInputStream(), file1);

				String attachmentType_n = ejbService.getSysServ().getParamValueByCode(239,
						tbAttachmentInfo.getAttachmentType());
				tbAttachmentInfo.setFileName(attachmentType_n);
				tbAttachmentInfo.setFileTypeCode(fileTypeCode);
				tbAttachmentInfo.setAttachmentName(newFileName);
				tbAttachmentInfo.setLocalPath(localPath);
				String remotePath = config.REMOTE_ATTACH_PATH + File.separator + custId + File.separator + newFileName;
				tbAttachmentInfo.setRemotePath(remotePath);
				ejbService.getCustServ().saveOrUpdateCustAttachment(tbAttachmentInfo, rCustAttachment);
				result = "SUCCESS";
			} else {
				result = "上传文件不能为空";
			}
		} catch (Exception e) {
			result = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		model.put("result", result);
		return new ModelAndView("customer/upload_attach", model);
	}

	/**
	 * 查询附件信息
	 * 
	 * @param rCustAttachment
	 * @return
	 */
	@RequestMapping(value = "/search_attach_info", method = RequestMethod.POST)
	public @ResponseBody String searchAttachmentInfo(RCustAttachment rCustAttachment) {
		StringBuffer sb = new StringBuffer();
		try {
			Map<String, Object> resultMap = ejbService.getCustServ().getAttachmentInfoMap(rCustAttachment.getCustId());
			if (resultMap != null) {
				sb.append("{");
				for (Entry<String, Object> entry : resultMap.entrySet()) {
					sb.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",");
				}
				sb = sb.deleteCharAt(sb.length() - 1);
				sb.append("}");
			} else {
				sb.append("[]");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(sb.toString());
		return sb.toString();
	}

	/**
	 * 附件下载
	 * 
	 * @param attachmentId
	 * @return
	 */
	@RequestMapping(value = "/download_cust_attach", method = RequestMethod.GET)
	public void downloadCustAttachment(HttpServletRequest request, HttpServletResponse response,
			RCustAttachment rCustAttachment) {
		TbAttachmentInfo attach = ejbService.getCustServ().findAttachEntityById(rCustAttachment.getId());
		if (attach == null) {
			return;
		}
		String fileName = attach.getFileName() + "." + attach.getFileTypeCode();
		String fileUrl = attach.getRemotePath();

		BufferedOutputStream bf = null;
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

			bf = new BufferedOutputStream(response.getOutputStream());

			if (fileUrl.startsWith("http://")) {
				bf.write(FileUtil.httpConverBytes(fileUrl));
			} else if (fileUrl.startsWith("https://")) {
				bf.write(FileUtil.httpsConverBytes(fileUrl));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bf != null) {
					bf.close();
					bf.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除附件
	 * 
	 * @param rCustAttachment
	 * @return
	 */
	@RequestMapping(value = "/delete_cust_attach", method = RequestMethod.POST)
	public @ResponseBody String deleteCustAttach(RCustAttachment rCustAttachment) {
		String status = "";
		try {
			ejbService.getCustServ().deleteRCustAttachmentById(rCustAttachment.getId());
			status = "SUCCESS";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			status = e.getMessage();
		}
		return status;
	}

	@RequestMapping(value = "/get_reserver_list", method = RequestMethod.POST)
	public @ResponseBody String getReserverList(TbReserverCustomer tbReserverCustomer) {
		try {
			logger.info("预约到店分页显示：第" + tbReserverCustomer.getPage() + "页，每页" + tbReserverCustomer.getRows() + "条。");
			List<TbReserverCustomer> list = ejbService.getCustServ().getReserverListByPorperty(tbReserverCustomer);

			if (null == list || list.size() == 0) {
				return "[]";
			}
			int count = ejbService.getCustServ().getReserverListCount(tbReserverCustomer);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"total\":" + count + ",\"page\":" + tbReserverCustomer.getPage() + ",\"rows\":[");
			for (TbReserverCustomer rc : list) {
				if (rc != null) {
					//logger.info("获取客户【" + rc.getCustId() + "】信息");
					TbPersonalCustomerInfo ci = ejbService.getCustServ().getCustInfoByID(rc.getCustId());
					sb.append("{\"id\":" + rc.getId());
					sb.append(",\"custId\":" + rc.getCustId());
					sb.append(",\"custName\":\"" + (rc.getCustName() == null ? "" : rc.getCustName()) + "\"");
					if(ci != null){
						sb.append(",\"certCode\":\"" + (ci.getCertCode() == null ? "" : ci.getCertCode()) + "\"");
					}
					sb.append(",\"phone\":\"" + (rc.getPhone() == null ? "" : rc.getPhone()) + "\"");
					sb.append(",\"gender\":\"" + (rc.getGender() == null ? "" : rc.getGender()) + "\"");
					sb.append(",\"productTitle\":\"" + (rc.getProductTitle() == null ? "" : rc.getProductTitle()) + "\"");
					sb.append(",\"productDesc\":\"" + (rc.getProductDesc() == null ? "" : rc.getProductDesc()) + "\"");
					sb.append(",\"reserverStore\":\"" + (rc.getReserverStore() == null ? "" : rc.getReserverStore()) + "\"");
					sb.append(",\"reserverTime\":\"" + (rc.getReserverTime() == null ? "" : rc.getReserverTime()) + "\"},");
					
				}
			}
			sb = sb.deleteCharAt(sb.length() - 1);
			sb.append("]}");

			//logger.info("reserver list : " + sb.toString());
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "[]";
	}
	
}
