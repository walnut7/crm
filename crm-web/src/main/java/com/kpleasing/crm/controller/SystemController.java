package com.kpleasing.crm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kpleasing.crm.ejb.entity.TbParameterConfig;
import com.kpleasing.crm.ejb.pojo.UserProfile;
import com.kpleasing.crm.service.EjbService;

@Controller
@RequestMapping(value = "/sys")
public class SystemController {
	
	private static Logger logger = Logger.getLogger(SystemController.class);
	
	@Autowired
	private EjbService ejbService;
	
	/**
	 * 新增参数配置
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/save_param", method = RequestMethod.POST)
	public @ResponseBody String saveParamConfig(HttpServletRequest request, TbParameterConfig parameterConfig) throws IOException {
		HttpSession session = request.getSession(false);
		UserProfile loginUser = (UserProfile) session.getAttribute("LOGIN_USER");
		parameterConfig.setOperator(loginUser.getUsername());
		
		ejbService.getSysServ().saveParameter(parameterConfig);
		
		return parameterConfig.getId()+"";
	}
	
	/**
	 * 更新参数配置
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/update_param", method = RequestMethod.POST)
	public @ResponseBody String updateBaseInfo(HttpServletRequest request, TbParameterConfig parameterConfig) throws IOException {
		HttpSession session = request.getSession(false);
		UserProfile loginUser = (UserProfile) session.getAttribute("LOGIN_USER");
		parameterConfig.setOperator(loginUser.getUsername());
		
		ejbService.getSysServ().updateParameter(parameterConfig);
		
		StringBuilder sb = new StringBuilder();
		sb.append("{\"id\":"+parameterConfig.getId());
		sb.append(",\"parentNodeId\":"+parameterConfig.getParentNodeId());
		sb.append(",\"nodeCode\": \""+parameterConfig.getNodeCode()+"\"");
		sb.append(",\"nodeValue\": \""+parameterConfig.getNodeValue()+"\"");
		sb.append(",\"sort\":"+parameterConfig.getSort());
		sb.append(",\"memo\": \""+parameterConfig.getMemo()+"\"");
		sb.append(",\"status\":"+parameterConfig.getStatus()+"}");
		
		logger.debug("更新结果：" + sb.toString());
		return sb.toString();
	}
	/**
	 * 软删除参数配置
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/delete_param", method = RequestMethod.POST)
	public @ResponseBody String deleteBaseInfo(HttpServletRequest request, TbParameterConfig parameterConfig) throws IOException {
		HttpSession session = request.getSession(false);
		UserProfile loginUser = (UserProfile) session.getAttribute("LOGIN_USER");
		parameterConfig.setOperator(loginUser.getUsername());
		
		ejbService.getSysServ().updateParameterDelFlag(parameterConfig);
		
		return "";
	}

	
	/**
	 * 加载参数配置列表
	 * @param 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/param_list")
	public @ResponseBody String getParamList(TbParameterConfig parameterConfig) throws IOException {
		List<TbParameterConfig>  list = ejbService.getSysServ().getParamList();
		if(null == list || list.size()== 0 ){
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\"total\":"+ list.size() + ",\"rows\":[");
		for (TbParameterConfig pc : list) {
			sb.append("{\"id\":"+pc.getId());
			sb.append(",\"parentNodeId\":"+pc.getParentNodeId());
			sb.append(",\"nodeCode\": \""+pc.getNodeCode()+"\"");
			sb.append(",\"nodeValue\": \""+pc.getNodeValue()+"\"");
			sb.append(",\"sort\":"+pc.getSort());
			sb.append(",\"memo\": \""+pc.getMemo() +"\"");
			sb.append(",\"status\":"+pc.getStatus());
			
			 if (pc.getId() == 1)
             {
				 sb.append(",\"state\":\"open\"");
             }
             else
             {
            	 sb.append(",\"_parentId\":"+pc.getParentNodeId());
            	 if(pc.getParentNodeId() == 1){
            		 sb.append(",\"state\":\"closed\"");
            	 }
             }
             sb.append("},");
		}
		sb = sb.deleteCharAt(sb.length()-1);
        sb.append("],\"footer\":[]}");
		
        logger.debug("参数配置列表：" + sb.toString());
		return sb.toString();
	}
	
	

}
