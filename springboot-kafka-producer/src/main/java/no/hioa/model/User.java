package no.hioa.model;

public class User {

	private String custId;
	private String name;
	private String company;
	private double salary;
	
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String company, double salary, String custId) {
		this.custId = custId;
		this.name = name;
		this.company = company;
		this.salary = salary;
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		
		return this.name +" "+this.company+ this.salary;
	}
}
