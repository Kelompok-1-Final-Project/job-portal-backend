package com.lawencon.jobportal.candidate.dto.job;

public class JobGetResDto {
	private String id;
	private String jobTitle;
	private Integer salaryStart;
	private Integer salaryEnd;
	private String description;
	private String endDate;
	private String companyId;
	private String companyName;
	private String companyPhoto;
	private String industryName;
	private String cityName;
	private String positionName;
	private String statusName;
	private String employmentName;
	private String createdAt;
	private String updatedAt;
	private Integer ver;
	private Integer totalJob;
	private Boolean isBookmark;
	private Boolean isApply;
	private String saveJobId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Integer getSalaryStart() {
		return salaryStart;
	}

	public void setSalaryStart(Integer salaryStart) {
		this.salaryStart = salaryStart;
	}

	public Integer getSalaryEnd() {
		return salaryEnd;
	}

	public void setSalaryEnd(Integer salaryEnd) {
		this.salaryEnd = salaryEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhoto() {
		return companyPhoto;
	}

	public void setCompanyPhoto(String companyPhoto) {
		this.companyPhoto = companyPhoto;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getEmploymentName() {
		return employmentName;
	}

	public void setEmploymentName(String employmentName) {
		this.employmentName = employmentName;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getTotalJob() {
		return totalJob;
	}

	public void setTotalJob(Integer totalJob) {
		this.totalJob = totalJob;
	}

	public Boolean getIsBookmark() {
		return isBookmark;
	}

	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

	public Boolean getIsApply() {
		return isApply;
	}

	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}

	public String getSaveJobId() {
		return saveJobId;
	}

	public void setSaveJobId(String saveJobId) {
		this.saveJobId = saveJobId;
	}

}
