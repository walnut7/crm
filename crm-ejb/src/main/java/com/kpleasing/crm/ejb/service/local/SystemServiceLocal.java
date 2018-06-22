package com.kpleasing.crm.ejb.service.local;

import java.util.List;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.entity.TbParameterConfig;

@Local
public interface SystemServiceLocal {

	public List<TbParameterConfig> getParamList();
	
	public List<TbParameterConfig> getCombParamList(int parentNodeId);

	public void saveParameter(TbParameterConfig parameterConfig);

	public void updateParameter(TbParameterConfig parameterConfig);

	public void updateParameterDelFlag(TbParameterConfig parameterConfig);

	public String getParamValueByCode(int parentNodeId, String nodeCode);

	public String getComboTreeList(int parentNodeId);

}
