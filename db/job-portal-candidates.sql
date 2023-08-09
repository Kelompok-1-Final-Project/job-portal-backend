
--DROP TABLE IF EXISTS t_organization;
--DROP TABLE IF EXISTS t_family;
--DROP TABLE IF EXISTS t_relationship;
--DROP TABLE IF EXISTS t_blacklist;
--DROP TABLE IF EXISTS t_save_job;
--DROP TABLE IF EXISTS t_level;
--DROP TABLE IF EXISTS t_work_experience;
--DROP TABLE IF EXISTS t_education;
--DROP TABLE IF EXISTS t_job_benefit;
--DROP TABLE IF EXISTS t_job;
--DROP TABLE IF EXISTS t_employment_type;
--DROP TABLE IF EXISTS t_job_status;
--DROP TABLE IF EXISTS t_user_skill;
--DROP TABLE IF EXISTS t_user;
--DROP TABLE IF EXISTS t_benefit;
--DROP TABLE IF EXISTS t_profile;
--DROP TABLE IF EXISTS t_skill;
--DROP TABLE IF EXISTS t_level;
--DROP TABLE IF EXISTS t_company;
--DROP TABLE IF EXISTS t_industry;
--DROP TABLE IF EXISTS t_city;
--DROP TABLE IF EXISTS t_person_type;
--DROP TABLE IF EXISTS t_gender;
--DROP TABLE IF EXISTS t_marital_status;
--DROP TABLE IF EXISTS t_file;


CREATE TABLE t_file(
	id VARCHAR(36) NOT NULL,
	ext VARCHAR(5) NOT NULL,
	file text NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_file ADD CONSTRAINT file_pk
	PRIMARY KEY(id);

CREATE TABLE t_gender(
	id VARCHAR(36) NOT NULL,
	gender_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_gender ADD CONSTRAINT gender_pk
	PRIMARY KEY(id);

CREATE TABLE t_marital_status(
	id VARCHAR(36) NOT NULL,
	status_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_marital_status ADD CONSTRAINT marital_status_pk
	PRIMARY KEY(id);

CREATE TABLE t_level(
	id VARCHAR(36) NOT NULL,
	level_name VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_level ADD CONSTRAINT level_pk
	PRIMARY KEY(id);

CREATE TABLE t_person_type(
	id VARCHAR(36) NOT NULL,
	type_code VARCHAR(5) NOT NULL,
	type_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_person_type ADD CONSTRAINT person_type_pk
	PRIMARY KEY(id);
ALTER TABLE t_person_type ADD CONSTRAINT person_type_code_bk
	UNIQUE(type_code);

CREATE TABLE t_industry(
	id VARCHAR(36) NOT NULL,
	industry_code VARCHAR(5) NOT NULL,
	industry_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_industry ADD CONSTRAINT industry_pk
	PRIMARY KEY(id);

CREATE TABLE t_city(
	id VARCHAR(36) NOT NULL,
	city_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_city ADD CONSTRAINT city_pk
	PRIMARY KEY(id);

CREATE TABLE t_company(
	id VARCHAR(36) NOT NULL,
	company_code VARCHAR(5) NOT NULL,
	company_name VARCHAR(30) NOT NULL,
	file_id VARCHAR(36) NOT NULL,
	city_id VARCHAR(36) NOT NULL,
	industry_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_company ADD CONSTRAINT company_pk
	PRIMARY KEY(id);
ALTER TABLE t_company ADD CONSTRAINT company_code_bk
	UNIQUE(company_code);
ALTER TABLE t_company ADD CONSTRAINT company_file_fk
	FOREIGN KEY(file_id)
	REFERENCES t_file(id);
ALTER TABLE t_company ADD CONSTRAINT company_industry_fk
	FOREIGN KEY(industry_id)
	REFERENCES t_industry(id);
ALTER TABLE t_company ADD CONSTRAINT company_city_fk
	FOREIGN KEY(city_id)
	REFERENCES t_city(id);

CREATE TABLE t_skill(
	id VARCHAR(36) NOT NULL,
	skill_code VARCHAR(5) NOT NULL,
	skill_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_skill ADD CONSTRAINT skill_pk
	PRIMARY KEY(id);
ALTER TABLE t_skill ADD CONSTRAINT skill_bk
	UNIQUE(skill_code);

CREATE TABLE t_profile(
	id VARCHAR(36) NOT NULL,
	id_number VARCHAR(16),
	full_name VARCHAR(30) NOT NULL,
	summary TEXT,
	birthdate DATE,
	mobile_number VARCHAR(15),
	photo_id VARCHAR(36),
	cv_id VARCHAR(36),
	expected_salary BIGINT,
	gender_id VARCHAR(36),
	marital_status_id VARCHAR(36),
	person_type_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_profile ADD CONSTRAINT profile_pk
	PRIMARY KEY(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_bk
	UNIQUE(id_number);
ALTER TABLE t_profile ADD CONSTRAINT profile_photo_fk
	FOREIGN KEY(photo_id)
	REFERENCES t_file(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_cv_fk
	FOREIGN KEY(cv_id)
	REFERENCES t_file(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_gender_fk
	FOREIGN KEY(gender_id)
	REFERENCES t_gender(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_marital_status_fk
	FOREIGN KEY(marital_status_id)
	REFERENCES t_marital_status(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_person_type_fk
	FOREIGN KEY(person_type_id)
	REFERENCES t_person_type(id);

CREATE TABLE t_user(
	id VARCHAR(36) NOT NULL,
	email VARCHAR(30) NOT NULL,
	pass text NOT NULL,
	profile_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_user ADD CONSTRAINT user_pk
	PRIMARY KEY(id);
ALTER TABLE t_user ADD CONSTRAINT user_bk
	UNIQUE(email);
ALTER TABLE t_user ADD CONSTRAINT user_profile_fk
	FOREIGN KEY(profile_id)
	REFERENCES t_profile(id);


CREATE TABLE t_user_skill(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	skill_id VARCHAR(36) NOT NULL,
	level_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_pk
	PRIMARY KEY(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_skill_fk
	FOREIGN KEY(skill_id)
	REFERENCES t_skill(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_level_fk
	FOREIGN KEY(level_id)
	REFERENCES t_level(id);

CREATE TABLE t_job_status(
	id VARCHAR(36) NOT NULL,
	status_code VARCHAR(5) NOT NULL,
	status_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_job_status ADD CONSTRAINT job_status_pk
	PRIMARY KEY(id);
ALTER TABLE t_job_status ADD CONSTRAINT job_status_bk
	UNIQUE(status_code);

CREATE TABLE t_employment_type(
	id VARCHAR(36) NOT NULL,
	employment_code VARCHAR(5) NOT NULL,
	employment_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_employment_type ADD CONSTRAINT employment_pk
	PRIMARY KEY(id);
ALTER TABLE t_employment_type ADD CONSTRAINT employment_bk
	UNIQUE(employment_code);

CREATE TABLE t_job(
	id VARCHAR(36) NOT NULL,
	job_title VARCHAR(30) NOT NULL,
	salary_start BIGINT NOT NULL,
	salary_end BIGINT NOT NULL,
	description text NOT NULL,
	end_date DATE NOT NULL,
	company_id VARCHAR(36) NOT NULL,
	job_status_id VARCHAR(36) NOT NULL,
	employment_type_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_job ADD CONSTRAINT job_pk
	PRIMARY KEY(id);
ALTER TABLE t_job ADD CONSTRAINT job_company_fk
	FOREIGN KEY(company_id)
	REFERENCES t_company(id);
ALTER TABLE t_job ADD CONSTRAINT job_status_fk
	FOREIGN KEY(job_status_id)
	REFERENCES t_job_status(id);
ALTER TABLE t_job ADD CONSTRAINT job_employment_fk
	FOREIGN KEY(employment_type_id)
	REFERENCES t_employment_type(id);

CREATE TABLE t_education(
	id VARCHAR(36) NOT NULL,
	education_name VARCHAR(30) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
	candidate_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_education ADD CONSTRAINT education_pk
	PRIMARY KEY(id);
ALTER TABLE t_education ADD CONSTRAINT education_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);

CREATE TABLE t_work_experience(
	id VARCHAR(36) NOT NULL,
	position_name VARCHAR(30) NOT NULL,
	company_name VARCHAR(30) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_work_experience ADD CONSTRAINT work_experience_pk
	PRIMARY KEY(id);
ALTER TABLE t_work_experience ADD CONSTRAINT work_experience_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);

CREATE TABLE t_save_job(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	job_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_save_job ADD CONSTRAINT save_job_pk
	PRIMARY KEY(id);
ALTER TABLE t_save_job ADD CONSTRAINT save_job_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);
ALTER TABLE t_save_job ADD CONSTRAINT save_job_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_benefit(
	id VARCHAR(36) NOT NULL,
	benefit_code VARCHAR(5) NOT NULL,
	benefit_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_benefit ADD CONSTRAINT benefit_pk
	PRIMARY KEY(id);
ALTER TABLE t_benefit ADD CONSTRAINT benefit_bk
	UNIQUE(benefit_code);
	
CREATE TABLE t_job_benefit(
	id VARCHAR(36) NOT NULL,
	benefit_id VARCHAR(36) NOT NULL,
	job_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_job_benefit ADD CONSTRAINT job_benefit_pk
	PRIMARY KEY(id);
ALTER TABLE t_job_benefit ADD CONSTRAINT job_benefit_benefit_fk
	FOREIGN KEY(benefit_id)
	REFERENCES t_benefit(id);
ALTER TABLE t_job_benefit ADD CONSTRAINT job_benefit_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);
	
CREATE TABLE t_relationship(
	id VARCHAR(36) NOT NULL,
	relationship_code VARCHAR(5) NOT NULL,
	relationship_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_relationship ADD CONSTRAINT relationship_pk
	PRIMARY KEY(id);
ALTER TABLE t_relationship ADD CONSTRAINT relationship_bk
	UNIQUE(relationship_code);
	
CREATE TABLE t_family(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	family_name VARCHAR(30) NOT NULL,
	relationship_id VARCHAR(36) NOT NULL,
	family_degree VARCHAR(36) NOT NULL,
	family_birthdate VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_family ADD CONSTRAINT family_pk
	PRIMARY KEY(id);
ALTER TABLE t_family ADD CONSTRAINT family_relationship_fk
	FOREIGN KEY(relationship_id)
	REFERENCES t_relationship(id);
ALTER TABLE t_family ADD CONSTRAINT family_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);
	
CREATE TABLE t_organization(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	organization_name VARCHAR(30) NOT NULL,
	position_name VARCHAR(30) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
	description TEXT,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_organization ADD CONSTRAINT organization_pk
	PRIMARY KEY(id);
ALTER TABLE t_organization ADD CONSTRAINT organization_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);
	
--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--SELECT uuid_generate_v4();

INSERT INTO t_employment_type (id, employment_code, employment_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'ET001', 'Internship', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET002', 'Contract', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET003', 'Full Time', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET004', 'Part Time', uuid_generate_v4(), NOW(),TRUE,0);

INSERT INTO t_gender(id, gender_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Male', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Female', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_industry (id, industry_code, industry_name, created_by, created_at,  is_active, ver) VALUES 
	(uuid_generate_v4(), 'ID001', 'Technology', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'ID002', 'Finance', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_level(id, level_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Basic', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Intermediate', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Expert', uuid_generate_v4(), NOW(), TRUE, 0);
	
INSERT INTO t_marital_status(id, status_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Single', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Married', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Divorced', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_person_type(id, type_code, type_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'PT001', 'Candidate', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'PT002', 'Employee', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_skill(id, skill_code, skill_name, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), 'SK001', 'Data Visualization', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK002', 'Microsoft Office', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK003', 'Graphic Design', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK004', 'Problem Solving', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK005', 'Time Management', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK006', 'Communication', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK007', 'Programming', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_job_status(id, status_code, status_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'JS001', 'Open', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS002', 'Closed', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS003', 'Draft', uuid_generate_v4(), NOW(), TRUE, 0);

