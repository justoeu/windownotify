package br.com.justoeu.wn.domain;

import java.io.Serializable;

public class NotifyConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nameParameter;
	private long timeoutRegister;
	
	public String getNameParameter() { return nameParameter; 	}
	public long getTimeoutRegister() { return timeoutRegister; 	}

	public void setNameParameter(final String nameParameter) 	{ this.nameParameter = nameParameter; 		}
	public void setTimeoutRegister(final long timeoutRegister) 	{ this.timeoutRegister = timeoutRegister; 	}
}