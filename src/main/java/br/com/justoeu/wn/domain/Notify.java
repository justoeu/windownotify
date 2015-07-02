package br.com.justoeu.wn.domain;

import java.io.Serializable;

public class Notify implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sessionClientID;
	private String nameFile;
	
	public String getSessionClientID() 	{ return sessionClientID; 	}
	public String getNameFile() 		{ return nameFile; 			}
	
	public void setSessionClientID(final String sessionClientID) 	{ this.sessionClientID = sessionClientID; 	}
	public void setNameFile(final String nameFile) 					{ this.nameFile = nameFile; 				}
	 
}