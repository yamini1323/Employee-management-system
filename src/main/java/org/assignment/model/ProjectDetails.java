package org.assignment.model;

public class ProjectDetails {

	private int projectId;
	private String projectName;
	private int managerId;
	private int empId;
	private String empName;

	public ProjectDetails() {
	}

	public ProjectDetails(int projectId, String projectName, int managerId, int empId, String empName) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.managerId = managerId;
		this.empId = empId;
		this.empName = empName;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
