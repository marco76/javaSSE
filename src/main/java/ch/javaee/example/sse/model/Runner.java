package ch.javaee.example.sse.model;

public class Runner {
	
	
	private String name;
	private String number;
	private String resultTime;
	
	public Runner() {
		// TODO Auto-generated constructor stub
	}
	
	public Runner(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public String getResultTime() {
		return resultTime;
	}

	public void setResultTime(String resultTime) {
		this.resultTime = resultTime;
	}
	
	
	
	
	

}
