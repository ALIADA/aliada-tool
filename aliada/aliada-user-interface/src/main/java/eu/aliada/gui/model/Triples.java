package eu.aliada.gui.model;

public class Triples {
	
	private String value;
	private String subject;
	private String object;
	
	public Triples() {
		
	}
	
	public Triples(String value, String subject, String object) {
		super();
		this.value = value;
		this.subject = subject;
		this.object = object;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

}
