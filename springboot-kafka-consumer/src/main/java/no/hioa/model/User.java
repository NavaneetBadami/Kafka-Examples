package no.hioa.model;

public class User {

	private String name;
	private String company;
	private double salary;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String company, double salary) {
		
		this.name = name;
		this.company = company;
		this.salary = salary;
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
		return this.name+" "+ this.company+" "+this.salary;
	}
}
