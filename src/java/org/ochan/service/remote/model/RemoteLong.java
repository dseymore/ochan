package org.ochan.service.remote.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "RemoteLong")
public class RemoteLong {

	private Long value;

	public RemoteLong(){
		
	}
	
	public RemoteLong(Long value) {
		super();
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
