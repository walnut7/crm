package com.kpleasing.crm.ejb.service.local;

import javax.ejb.Local;

import com.kpleasing.crm.ejb.pojo.Notify;

@Local
public interface NotifyServiceLocal {

	public void notifly(Notify notify);

}
