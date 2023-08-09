--DROP TABLE IF EXISTS t_job_benefit;
--DROP TABLE IF EXISTS t_benefit;
--DROP TABLE IF EXISTS t_blacklist;
--DROP TABLE IF EXISTS t_result;
--DROP TABLE IF EXISTS t_question_option;
--DROP TABLE IF EXISTS t_skill_test_question;
--DROP TABLE IF EXISTS t_question;
--DROP TABLE IF EXISTS t_skill_test;
--DROP TABLE IF EXISTS t_hired;
--DROP TABLE IF EXISTS t_offering;
--DROP TABLE IF EXISTS t_medical_checkup;
--DROP TABLE IF EXISTS t_interview;
--DROP TABLE IF EXISTS t_assessment;
--DROP TABLE IF EXISTS t_application;
--DROP TABLE IF EXISTS t_job_candidate_status;
--DROP TABLE IF EXISTS t_user_skill;
--DROP TABLE IF EXISTS t_candidate;
--DROP TABLE IF EXISTS t_candidate_profile;
--DROP TABLE IF EXISTS t_job;
--DROP TABLE IF EXISTS t_candidate;
--DROP TABLE IF EXISTS t_user;
--DROP TABLE IF EXISTS t_profile;
--DROP TABLE IF EXISTS t_status_process;
--DROP TABLE IF EXISTS t_skill;
--DROP TABLE IF EXISTS t_employement_type;
--DROP TABLE IF EXISTS t_job_position;
--DROP TABLE IF EXISTS t_job_status;
--DROP TABLE IF EXISTS t_company;
--DROP TABLE IF EXISTS t_city;
--DROP TABLE IF EXISTS t_industry;
--DROP TABLE IF EXISTS t_person_type;
--DROP TABLE IF EXISTS t_role;
--DROP TABLE IF EXISTS t_file;
--DROP TABLE IF EXISTS t_gender;
--DROP TABLE IF EXISTS t_marital_status;

CREATE TABLE t_file(
	id VARCHAR(36) NOT NULL ,
	ext VARCHAR(5) NOT NULL,
	file TEXT NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_file ADD CONSTRAINT file_pk
	PRIMARY KEY(id);

CREATE TABLE t_role(
	id VARCHAR(36) NOT NULL ,
	role_code VARCHAR(5) NOT NULL,
	role_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_role ADD CONSTRAINT role_pk
	PRIMARY KEY(id);
ALTER TABLE t_role ADD CONSTRAINT role_code_bk
	UNIQUE(role_code);


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
	id VARCHAR(36) NOT NULL ,
	company_code VARCHAR(5) NOT NULL,
	company_name VARCHAR(30) NOT NULL,
	city_id VARCHAR(36) NOT NULL,
	file_id VARCHAR(36) NOT NULL,
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

CREATE TABLE t_job_status(
	id VARCHAR(36) NOT NULL ,
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
	
CREATE TABLE t_job_position(
	id VARCHAR(36) NOT NULL ,
	position_code VARCHAR(5) NOT NULL,
	position_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_job_position ADD CONSTRAINT job_position_pk
	PRIMARY KEY(id);
ALTER TABLE t_job_position ADD CONSTRAINT job_position_bk
	UNIQUE(position_code);
	
CREATE TABLE t_employement_type(
	id VARCHAR(36) NOT NULL ,
	employement_code VARCHAR(5) NOT NULL,
	employement_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_employement_type ADD CONSTRAINT employement_pk
	PRIMARY KEY(id);
ALTER TABLE t_employement_type ADD CONSTRAINT employement_bk
	UNIQUE(employement_code);

CREATE TABLE t_skill(
	id VARCHAR(36) NOT NULL ,
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

CREATE TABLE t_status_process(
	id VARCHAR(36) NOT NULL ,
	process_code VARCHAR(5) NOT NULL,
	process_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_status_process ADD CONSTRAINT status_process_pk
	PRIMARY KEY(id);
ALTER TABLE t_status_process ADD CONSTRAINT status_process_bk
	UNIQUE(process_code);

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


CREATE TABLE t_profile(
	id VARCHAR(36) NOT NULL ,
	id_number VARCHAR(16),
	full_name VARCHAR(30),
	mobile_number VARCHAR(15),
	gender_id VARCHAR(36),
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
ALTER TABLE t_profile ADD CONSTRAINT profile_gender_fk
	FOREIGN KEY(gender_id)
	REFERENCES t_gender(id);

CREATE TABLE t_user(
	id VARCHAR(36) NOT NULL ,
	email VARCHAR(30) NOT NULL,
	pass text NOT NULL,
	profile_id VARCHAR(36) NOT NULL ,
	role_id VARCHAR(36) NOT NULL ,
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
ALTER TABLE t_user ADD CONSTRAINT user_role_fk
	FOREIGN KEY(role_id)
	REFERENCES t_role(id);

CREATE TABLE t_candidate_profile(
	id VARCHAR(36) NOT NULL,
	id_number VARCHAR(15),
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

ALTER TABLE t_candidate_profile ADD CONSTRAINT candidate_profile_pk
	PRIMARY KEY(id);
ALTER TABLE t_candidate_profile ADD CONSTRAINT candidate_profile_bk
	UNIQUE(id_number);
ALTER TABLE t_candidate_profile ADD CONSTRAINT candidate_profile_gender_fk
	FOREIGN KEY(gender_id)
	REFERENCES t_gender(id);
ALTER TABLE t_candidate_profile ADD CONSTRAINT candidate_profile_photo_fk
	FOREIGN KEY(photo_id)
	REFERENCES t_file(id);
ALTER TABLE t_candidate_profile ADD CONSTRAINT candidate_profile_cv_fk
	FOREIGN KEY(cv_id)
	REFERENCES t_file(id);
ALTER TABLE t_candidate_profile ADD CONSTRAINT profile_marital_status_fk
	FOREIGN KEY(marital_status_id)
	REFERENCES t_marital_status(id);
ALTER TABLE t_candidate_profile ADD CONSTRAINT profile_person_type_fk
	FOREIGN KEY(person_type_id)
	REFERENCES t_person_type(id);


CREATE TABLE t_candidate(
	id VARCHAR(36) NOT NULL ,
	email VARCHAR(30) NOT NULL,
	pass text NOT NULL,
	profile_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_candidate ADD CONSTRAINT candidate_pk
	PRIMARY KEY(id);
ALTER TABLE t_candidate ADD CONSTRAINT candidate_bk
	UNIQUE(email);
ALTER TABLE t_candidate ADD CONSTRAINT candidate_profile_fk
	FOREIGN KEY(profile_id)
	REFERENCES t_candidate_profile(id);

CREATE TABLE t_job(
	id VARCHAR(36) NOT NULL ,
	job_title VARCHAR(30) NOT NULL,
	salary_start BIGINT NOT NULL,
	salary_end BIGINT NOT NULL,
	description text NOT NULL,
	end_date DATE NOT NULL,
	company_id VARCHAR(36) NOT NULL ,
	job_position_id VARCHAR(36) NOT NULL ,
	job_status_id VARCHAR(36) NOT NULL ,
	employement_type_id VARCHAR(36) NOT NULL ,
	hr_id VARCHAR(36) NOT NULL ,
	interviewer_id VARCHAR(36) NOT NULL ,
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
ALTER TABLE t_job ADD CONSTRAINT job_employement_fk
	FOREIGN KEY(employement_type_id)
	REFERENCES t_employement_type(id);
ALTER TABLE t_job ADD CONSTRAINT job_hr_id 
	FOREIGN KEY(hr_id)
	REFERENCES t_user(id);
ALTER TABLE t_job ADD CONSTRAINT job_interviewer_id
	FOREIGN KEY(interviewer_id)
	REFERENCES t_user(id);
ALTER TABLE t_job ADD CONSTRAINT job_position_id
	FOREIGN KEY(job_position_id)
	REFERENCES t_job_position(id);

CREATE TABLE t_user_skill(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	skill_id VARCHAR(36) NOT NULL ,
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
	REFERENCES t_candidate(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_skill_fk
	FOREIGN KEY(skill_id)
	REFERENCES t_skill(id);

CREATE TABLE t_job_candidate_status(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	status_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_job_candidate_status ADD CONSTRAINT job_candidate_status_pk
	PRIMARY KEY(id);
ALTER TABLE t_job_candidate_status ADD CONSTRAINT job_candidate_status_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_job_candidate_status ADD CONSTRAINT job_candidate_status_job_fk
	FOREIGN KEY(status_id)
	REFERENCES t_status_process(id);
ALTER TABLE t_job_candidate_status ADD CONSTRAINT job_candidate_status_job_status_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_application(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_application ADD CONSTRAINT application_pk
	PRIMARY KEY(id);
ALTER TABLE t_application ADD CONSTRAINT application_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_application ADD CONSTRAINT application_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_assessment(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	hr_id VARCHAR(36) NOT NULL ,
	schedule timestamp NOT NULL,
	notes TEXT,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_assessment ADD CONSTRAINT assessment_pk
	PRIMARY KEY(id);
ALTER TABLE t_assessment ADD CONSTRAINT assessment_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_assessment ADD CONSTRAINT assessment_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);
ALTER TABLE t_assessment ADD CONSTRAINT assessment_hr_fk
	FOREIGN KEY(hr_id)
	REFERENCES t_user(id);

CREATE TABLE t_interview(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	interviewer_id VARCHAR(36) NOT NULL ,
	schedule timestamp NOT NULL,
	notes text NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_interview ADD CONSTRAINT interview_pk
	PRIMARY KEY(id);
ALTER TABLE t_interview ADD CONSTRAINT interview_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_interview ADD CONSTRAINT interview_interviewer_fk
	FOREIGN KEY(interviewer_id)
	REFERENCES t_user(id);
ALTER TABLE t_interview ADD CONSTRAINT interview_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_medical_checkup(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	file_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_medical_checkup ADD CONSTRAINT medical_pk
	PRIMARY KEY(id);
ALTER TABLE t_medical_checkup ADD CONSTRAINT medical_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_medical_checkup ADD CONSTRAINT medical_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);
ALTER TABLE t_medical_checkup ADD CONSTRAINT medical_file_fk
	FOREIGN KEY(file_id)
	REFERENCES t_file(id);

CREATE TABLE t_offering(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_offering ADD CONSTRAINT offering_pk
	PRIMARY KEY(id);
ALTER TABLE t_offering ADD CONSTRAINT offering_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_offering ADD CONSTRAINT offering_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_hired(
	id VARCHAR(36) NOT NULL ,
	candidate_id VARCHAR(36) NOT NULL ,
	job_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_hired ADD CONSTRAINT hired_pk
	PRIMARY KEY(id);
ALTER TABLE t_hired ADD CONSTRAINT hired_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);
ALTER TABLE t_hired ADD CONSTRAINT hired_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);

CREATE TABLE t_skill_test(
	id VARCHAR(36) NOT NULL ,
	test_name VARCHAR(30) NOT NULL,
	job_id VARCHAR(36) NOT NULL,
	grade float NOT NULL,
	notes text NOT NULL,
	candidate_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);
	
ALTER TABLE t_skill_test ADD CONSTRAINT skill_test_pk
	PRIMARY KEY(id);
ALTER TABLE t_skill_test ADD CONSTRAINT skill_test_job_fk
	FOREIGN KEY(job_id)
	REFERENCES t_job(id);
ALTER TABLE t_skill_test ADD CONSTRAINT skill_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_candidate(id);


CREATE TABLE t_question(
	id VARCHAR(36) NOT NULL ,
	question text NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_question ADD CONSTRAINT question_pk
	PRIMARY KEY(id);

CREATE TABLE t_skill_test_question(
	id VARCHAR(36) NOT NULL,
	question_id VARCHAR(36) NOT NULL,
	skill_test_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_skill_test_question ADD CONSTRAINT skill_test_question_pk
	PRIMARY KEY(id);
ALTER TABLE t_skill_test_question ADD CONSTRAINT skill_test_question_question_fk
	FOREIGN KEY(skill_test_id)
	REFERENCES t_skill_test(id);
ALTER TABLE t_skill_test_question ADD CONSTRAINT skill_test_question_skill_fk
	FOREIGN KEY(question_id)
	REFERENCES t_question(id);

CREATE TABLE t_question_option(
	id VARCHAR(36) NOT NULL ,
	labels VARCHAR(30) NOT NULL,
	is_answer boolean NOT NULL,
	question_id VARCHAR(36) NOT NULL ,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_question_option ADD CONSTRAINT question_option_pk
	PRIMARY KEY(id);
ALTER TABLE t_question_option ADD CONSTRAINT question_option_question_fk
	FOREIGN KEY(question_id)
	REFERENCES t_question(id);

CREATE TABLE t_blacklist(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	company_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_blacklist ADD CONSTRAINT blacklist_pk
	PRIMARY KEY(id);
ALTER TABLE t_blacklist ADD CONSTRAINT blacklist_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);
ALTER TABLE t_blacklist ADD CONSTRAINT blacklist_company_fk
	FOREIGN KEY(company_id)
	REFERENCES t_company(id);
	

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
	

--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--SELECT uuid_generate_v4();

INSERT INTO t_employement_type (id, employement_code, employement_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'ET001', 'Internship', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET002', 'Contract', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET003', 'Full Time', uuid_generate_v4(), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET004', 'Part Time', uuid_generate_v4(), NOW(),TRUE,0);

INSERT INTO t_gender(id, gender_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Male', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Female', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_industry (id, industry_code, industry_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'ID001', 'Technology', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'ID002', 'Finance', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_level(id, level_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Basic', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Intermediate', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Expert', uuid_generate_v4(), NOW(), TRUE, 0);
	
INSERT INTO t_marital_status(id, status_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Single', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Married', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Divorced', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_person_type(id, type_code, type_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'PT001', 'Candidate', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'PT002', 'Employee', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_skill(id, skill_code, skill_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES
	(uuid_generate_v4(), 'SK001', 'Data Visualization', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK002', 'Microsoft Office', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK003', 'Graphic Design', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK004', 'Problem Solving', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK005', 'Time Management', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK006', 'Communication', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK007', 'Programming', uuid_generate_v4(), NOW(), TRUE, 0);
	
INSERT INTO t_benefit(id, benefit_code, benefit_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'BN001', 'BPJS', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN002', 'Transportation Fee', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN003', 'Laptop', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN004', 'Gadget', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN005', 'THR', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN006', 'Vehicle', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN007', 'Insurance', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN008', 'Bonus', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_role(id, role_code, role_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'RL001', 'Admin', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL002', 'HR', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL003', 'Interviewer', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL004', 'System', uuid_generate_v4(), NOW(), TRUE, 0);
	
INSERT INTO t_status_process(id, process_code, process_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'SP001', 'Application', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP002', 'Assessment', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP003', 'Interview', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP004', 'Medical Check Up', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP005', 'Offering', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP006', 'Hired', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP007', 'Rejected', uuid_generate_v4(), NOW(), TRUE, 0);

INSERT INTO t_job_status(id, status_code, status_name, created_by, created_at, updated_by, updated_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'JS001', 'Open', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS002', 'Closed', uuid_generate_v4(), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS003', 'Draft', uuid_generate_v4(), NOW(), TRUE, 0);
	
