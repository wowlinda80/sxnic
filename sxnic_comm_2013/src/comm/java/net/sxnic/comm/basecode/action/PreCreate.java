package net.sxnic.comm.basecode.action;


import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;

import net.sxnic.comm.basecode.BaseCode;

@SuppressWarnings("serial")
public class PreCreate extends BaseCodeAction  {
	


    public String execute() throws Exception {
    	 
        basecode = new BaseCode();
        return SUCCESS;
    }

}

