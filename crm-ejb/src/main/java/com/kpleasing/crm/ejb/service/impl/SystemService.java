package com.kpleasing.crm.ejb.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.kpleasing.crm.ejb.eao.local.TbParameterConfigEAOLocal;
import com.kpleasing.crm.ejb.entity.TbParameterConfig;
import com.kpleasing.crm.ejb.service.local.SystemServiceLocal;
import com.kpleasing.crm.ejb.util.DateUtil;

@Stateless
@LocalBean
public class SystemService implements Serializable, SystemServiceLocal {

	/**	 * 	 */
	private static final long serialVersionUID = 4098936761251480547L;
	
	@EJB
	private TbParameterConfigEAOLocal paramConfigEao;

	@Override
	public List<TbParameterConfig> getParamList() {
		return paramConfigEao.findByProperty("delFlag", (byte)0);
		
	}
	
	@Override
	public List<TbParameterConfig> getCombParamList(int parentNodeId) {
		String[] fields ={"parentNodeId","delFlag","status"};
		return paramConfigEao.findByProperty(fields, parentNodeId,(byte)0,(byte)0);
		
	}
	
	@Override
	public String getComboTreeList(int parentNodeId) {
		TbParameterConfig parameterConfig = paramConfigEao.findById(parentNodeId);
		List<TbParameterConfig> list = paramConfigEao.findByProperty("parentNodeId", parentNodeId);
		if(parameterConfig == null || list == null || list.size() == 0){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[{\"id\": \""+parameterConfig.getNodeCode()+"\",\"text\": \""+parameterConfig.getNodeValue() +"\",\"state\": \"open\",\"children\": [");
		for(TbParameterConfig pc : list){
			sb.append("{\"id\":\""+pc.getNodeCode()+"\"");
			sb.append(",\"text\": \""+pc.getNodeValue()+"\"");
			sb.append(",\"state\": \"closed\"");
			List<TbParameterConfig> childrenList = paramConfigEao.findByProperty("parentNodeId", pc.getId());
			if(childrenList !=null && childrenList.size()>0){
				StringBuilder tmp = new StringBuilder();
				tmp.append(",\"children\": [");
				for(TbParameterConfig cpc : childrenList){
					tmp.append("{\"id\":\""+ cpc.getNodeCode() +"\"");
					tmp.append(",\"text\":\""+ cpc.getNodeValue() +"\"},");
				}
				tmp = tmp.deleteCharAt(tmp.length()-1);
				tmp.append("]");
				sb.append(tmp.toString());
			}
			sb.append("},");
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append("]}]");
		
		return sb.toString();
	}
	
	@Override
	public String getParamValueByCode(int parentNodeId,String nodeCode){
		String[] fields ={"parentNodeId","nodeCode"};
		String nodeValue ="";
		List<TbParameterConfig> list = paramConfigEao.findByProperty(fields, parentNodeId,nodeCode);
		if(list!=null && list.size()==1){
			for(TbParameterConfig pc : list){
				nodeValue = pc.getNodeValue();
				break;
			}
		}
		return nodeValue;
	}


	@Override
	public void saveParameter(TbParameterConfig parameterConfig) {
		parameterConfig.setCreateAt(DateUtil.getDate());
		parameterConfig.setUpdateAt(DateUtil.getDate());
		
		paramConfigEao.save(parameterConfig);
	}
	
	@Override
	public void updateParameter(TbParameterConfig parameterConfig) {
		TbParameterConfig org_parameterconfig = paramConfigEao.findById(parameterConfig.getId());
		org_parameterconfig.setNodeCode(parameterConfig.getNodeCode());
		org_parameterconfig.setNodeValue(parameterConfig.getNodeValue());
		org_parameterconfig.setSort(parameterConfig.getSort());
		org_parameterconfig.setMemo(parameterConfig.getMemo());
		org_parameterconfig.setStatus(parameterConfig.getStatus());
		org_parameterconfig.setOperator(parameterConfig.getOperator());
		org_parameterconfig.setUpdateAt(DateUtil.getDate());
		
		parameterConfig = org_parameterconfig;
		paramConfigEao.update(parameterConfig);
	}
	
	@Override
	public void updateParameterDelFlag(TbParameterConfig parameterConfig) {
		TbParameterConfig org_parameterconfig = paramConfigEao.findById(parameterConfig.getId());
		org_parameterconfig.setDelFlag((byte)1);
		org_parameterconfig.setOperator(parameterConfig.getOperator());
		org_parameterconfig.setUpdateAt(DateUtil.getDate());
		
		paramConfigEao.update(org_parameterconfig);
	}


}
