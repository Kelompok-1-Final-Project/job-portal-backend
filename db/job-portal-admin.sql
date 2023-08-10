--DROP TABLE IF EXISTS t_job_benefit;
--DROP TABLE IF EXISTS t_benefit;
--DROP TABLE IF EXISTS t_blacklist;
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
--DROP TABLE IF EXISTS t_level;
--DROP TABLE IF EXISTS t_candidate;
--DROP TABLE IF EXISTS t_candidate_profile;
--DROP TABLE IF EXISTS t_job;
--DROP TABLE IF EXISTS t_user;
--DROP TABLE IF EXISTS t_profile;
--DROP TABLE IF EXISTS t_status_process;
--DROP TABLE IF EXISTS t_skill;
--DROP TABLE IF EXISTS t_employment_type;
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

CREATE TABLE t_level(
	id VARCHAR(36) NOT NULL ,
	level_code VARCHAR(5) NOT NULL,
	level_name VARCHAR(30) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_level ADD CONSTRAINT level_pk
	PRIMARY KEY(id);
ALTER TABLE t_level ADD CONSTRAINT level_code_bk
	UNIQUE(level_code);

CREATE TABLE t_marital_status(
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

ALTER TABLE t_marital_status ADD CONSTRAINT marital_status_pk
	PRIMARY KEY(id);
ALTER TABLE t_marital_status ADD CONSTRAINT marital_status_bk
	UNIQUE(status_code);

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
	city_code VARCHAR(5) NOT NULL,
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
ALTER TABLE t_city ADD CONSTRAINT city_bk
	UNIQUE(city_code);

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
	
CREATE TABLE t_employment_type(
	id VARCHAR(36) NOT NULL ,
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
	gender_code VARCHAR(5) NOT NULL,
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
ALTER TABLE t_gender ADD CONSTRAINT gender_bk
	UNIQUE(gender_code);

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
	employment_type_id VARCHAR(36) NOT NULL ,
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
ALTER TABLE t_job ADD CONSTRAINT job_employment_fk
	FOREIGN KEY(employment_type_id)
	REFERENCES t_employment_type(id);
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
	REFERENCES t_candidate(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_skill_fk
	FOREIGN KEY(skill_id)
	REFERENCES t_skill(id);
ALTER TABLE t_user_skill ADD CONSTRAINT user_skill_level_fk
	FOREIGN KEY(level_id)
	REFERENCES t_level(id);

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
	REFERENCES t_candidate(id);
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

INSERT INTO t_profile(id, full_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'system', 0, NOW(), TRUE, 0);

INSERT INTO t_role(id, role_code, role_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'RL001', 'Admin', 0, NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL002', 'HR', 0, NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL003', 'Interviewer', 0, NOW(), TRUE, 0),
	(uuid_generate_v4(), 'RL004', 'System', 0, NOW(), TRUE, 0);

INSERT INTO t_user(id, email, pass, profile_id, role_id, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), 'system', 'system', (SELECT id FROM t_profile  WHERE full_name = 'system'), (SELECT id FROM t_role WHERE role_name = 'System') , 0, NOW(), TRUE, 0);

INSERT INTO t_person_type(id, type_code, type_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'PT001', 'Candidate', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'PT002', 'Employee', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_employment_type (id, employment_code, employment_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'ET001', 'Internship', (SELECT id FROM t_user  WHERE email = 'system'), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET002', 'Contract', (SELECT id FROM t_user  WHERE email = 'system'), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET003', 'Full Time', (SELECT id FROM t_user  WHERE email = 'system'), NOW(),TRUE,0),
	(uuid_generate_v4(), 'ET004', 'Part Time', (SELECT id FROM t_user  WHERE email = 'system'), NOW(),TRUE,0);

INSERT INTO t_gender(id, gender_code, gender_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),'GD001', 'Male', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(),'GD002', 'Female', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_industry (id, industry_code, industry_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'ID001', 'Technology', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'ID002', 'Finance', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_level(id, level_code, level_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'LV001', 'Basic', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'LV002', 'Intermediate', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'LV003', 'Expert', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);
	
INSERT INTO t_marital_status(id, status_code, status_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'MS001', 'Single', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'MS002', 'Married', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'MS003', 'Divorced', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_skill(id, skill_code, skill_name, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), 'SK001', 'Data Visualization', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK002', 'Microsoft Office', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK003', 'Graphic Design', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK004', 'Problem Solving', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK005', 'Time Management', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK006', 'Communication', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SK007', 'Programming', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);
	
INSERT INTO t_benefit(id, benefit_code, benefit_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'BN001', 'BPJS', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN002', 'Transportation Fee', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN003', 'Laptop', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN004', 'Gadget',(SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN005', 'THR', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN006', 'Vehicle', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN007', 'Insurance', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'BN008', 'Bonus', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);
	
INSERT INTO t_status_process(id, process_code, process_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'SP001', 'Application', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP002', 'Assessment', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP003', 'Interview', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP004', 'Medical Check Up', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP005', 'Offering', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP006', 'Hired', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'SP007', 'Rejected', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_job_status(id, status_code, status_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'JS001', 'Open', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS002', 'Closed', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'JS003', 'Draft', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0);

INSERT INTO t_file(id, ext, file, created_by, created_at, is_active, ver ) VALUES 
	(uuid_generate_v4(), '.jpg', 'system',(SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE,0 );
	
INSERT INTO t_city(id, city_code, city_name, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),'CT001', 'Jakarta', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0 ),
	(uuid_generate_v4(),'CT002', 'Bandung', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0 ),
	(uuid_generate_v4(),'CT003', 'Surabaya', (SELECT id FROM t_user  WHERE email = 'system'), NOW(), TRUE, 0 );

INSERT INTO t_company(id, company_code, company_name, city_id, file_id, industry_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'CP001', 'Lawencon', (SELECT id FROM t_city WHERE city_name='Jakarta'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_industry WHERE industry_code='ID001'), (SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'CP002', 'Linov', (SELECT id FROM t_city WHERE city_name='Jakarta'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_industry WHERE industry_code='ID002'), (SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'CP003', 'Rocket', (SELECT id FROM t_city WHERE city_name='Jakarta'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_industry WHERE industry_code='ID001'), (SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0);
	
INSERT INTO t_job_position(id,position_code, position_name, created_by, created_at,is_active,ver) VALUES 
	(uuid_generate_v4(), 'JP001', 'Fullstack Developer', (SELECT id FROM t_user WHERE email='system'),NOW(),TRUE, 0),
	(uuid_generate_v4(), 'JP002', 'Backend Developer', (SELECT id FROM t_user WHERE email='system'),NOW(),TRUE, 0),
	(uuid_generate_v4(), 'JP003', 'Frontend Developer', (SELECT id FROM t_user WHERE email='system'),NOW(),TRUE, 0);

INSERT INTO t_profile(id, id_number, full_name, mobile_number, gender_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),'1234567890','Anggi', '082219823926', (SELECT id FROM t_gender WHERE gender_code='G001'), (SELECT id FROM t_user WHERE email='system'),NOW(), TRUE, 0),
	(uuid_generate_v4(), '0987654321','Firman','082219823926', (SELECT id FROM t_gender WHERE gender_code='G001'), (SELECT id FROM t_user WHERE email='system'),NOW(), TRUE, 0),
	(uuid_generate_v4(),'1029384756', 'Torang','082219823926', (SELECT id FROM t_gender WHERE gender_code='G002'), (SELECT id FROM t_user WHERE email='system'),NOW(), TRUE, 0);

INSERT INTO t_user(id, email, pass, profile_id, role_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'anggi@gmail.com', 'anggi', (SELECT id FROM t_profile WHERE full_name='Anggi'), (SELECT id FROM t_role WHERE role_code='RL001'),(SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'firman@gmail.com', 'firman', (SELECT id FROM t_profile WHERE full_name='Firman'), (SELECT id FROM t_role WHERE role_code='RL002'),(SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'torang@gmail.com', 'torang', (SELECT id FROM t_profile WHERE full_name='Torang'), (SELECT id FROM t_role WHERE role_code='RL003'),(SELECT id FROM t_user WHERE email='system'), NOW(), TRUE, 0);

INSERT INTO t_job(id, job_title, salary_start , salary_end, description, end_date, company_id , job_position_id , job_status_id , employment_type_id , hr_id ,interviewer_id , created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Fullstack Developer', 8000000, 10000000, 'Looking for Fullstack Developer','2023-08-10',(SELECT id FROM t_company WHERE company_code='CP001'), (SELECT id FROM t_job_position WHERE position_code='JP001'),(SELECT id FROM t_job_status WHERE status_code='JS001'),(SELECT id FROM t_employment_type WHERE employment_code='ET001'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Backend Developer', 6000000, 8000000, 'Looking for Backend Developer','2023-08-11' ,(SELECT id FROM t_company WHERE company_code='CP002'), (SELECT id FROM t_job_position WHERE position_code='JP002'),(SELECT id FROM t_job_status WHERE status_code='JS002'),(SELECT id FROM t_employment_type WHERE employment_code='ET002'),(SELECT id FROM t_user WHERE email='firman@gmail.com'),(SELECT id FROM t_user WHERE email='firman@gmail.com'), (SELECT id FROM t_user WHERE email='firman@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'Frontend Developer', 7000000, 9000000, 'Looking for Frontend Developer','2023-08-12 ',(SELECT id FROM t_company WHERE company_code='CP003'), (SELECT id FROM t_job_position WHERE position_code='JP003'),(SELECT id FROM t_job_status WHERE status_code='JS003'),(SELECT id FROM t_employment_type WHERE employment_code='ET003'),(SELECT id FROM t_user WHERE email='torang@gmail.com'),(SELECT id FROM t_user WHERE email='torang@gmail.com'), (SELECT id FROM t_user WHERE email='torang@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_candidate_profile(id, id_number, full_name, summary, birthdate, mobile_number, photo_id, cv_id, expected_salary, gender_id, marital_status_id, person_type_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), '1234567890', 'Anggi', 'Im a Fullstack Developer', '2000-12-20' ,'082219823926', (SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_file WHERE file='system'),10000000,(SELECT id FROM t_gender WHERE gender_code='GD001'),(SELECT id FROM t_marital_status WHERE status_code='MS001'),(SELECT id FROM t_person_type WHERE type_code='PT001'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), '0987654321', 'Firman', 'Im a Backend Developer', '2000-12-20' ,'082219823926', (SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_file WHERE file='system'),9000000,(SELECT id FROM t_gender WHERE gender_code='GD002'),(SELECT id FROM t_marital_status WHERE status_code='MS002'),(SELECT id FROM t_person_type WHERE type_code='PT002'), (SELECT id FROM t_user WHERE email='firman@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), '9809876567', 'Torang', 'Im a Frontend Developer','2000-12-20' ,'082219823926', (SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_file WHERE file='system'),8000000,(SELECT id FROM t_gender WHERE gender_code='GD001'),(SELECT id FROM t_marital_status WHERE status_code='MS003'),(SELECT id FROM t_person_type WHERE type_code='PT001'), (SELECT id FROM t_user WHERE email='torang@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_candidate(id, email, pass, profile_id, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), 'anggi@gmail.com', 'anggi', (SELECT id FROM t_candidate_profile WHERE full_name='Anggi'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'firman@gmail.com', 'firman', (SELECT id FROM t_candidate_profile WHERE full_name='Firman'), (SELECT id FROM t_user WHERE email='firman@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), 'torang@gmail.com', 'torang', (SELECT id FROM t_candidate_profile WHERE full_name='Torang'), (SELECT id FROM t_user WHERE email='torang@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_user_skill(id, candidate_id, skill_id , level_id , created_by , created_at , is_active , ver ) VALUES 
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_skill WHERE skill_code='SK001'),(SELECT id FROM t_level WHERE level_code='LV001'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'), (SELECT id FROM t_skill WHERE skill_code='SK002'),(SELECT id FROM t_level WHERE level_code='LV002'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'), (SELECT id FROM t_skill WHERE skill_code='SK003'),(SELECT id FROM t_level WHERE level_code='LV003'), (SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_job_candidate_status(id, candidate_id, job_id, status_id, created_by, created_at, is_active,ver) VALUES 
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_job WHERE job_title='Fullstack Developer'), (SELECT id FROM t_status_process WHERE process_code='SP001'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'), (SELECT id FROM t_job WHERE job_title='Backend Developer'), (SELECT id FROM t_status_process WHERE process_code='SP002'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'), (SELECT id FROM t_job WHERE job_title='Frontend Developer'), (SELECT id FROM t_status_process WHERE process_code='SP003'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_job WHERE job_title='Fullstack Developer'), (SELECT id FROM t_status_process WHERE process_code='SP004'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'), (SELECT id FROM t_job WHERE job_title='Backend Developer'), (SELECT id FROM t_status_process WHERE process_code='SP005'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'), (SELECT id FROM t_job WHERE job_title='Frontend Developer'), (SELECT id FROM t_status_process WHERE process_code='SP006'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_job WHERE job_title='Fullstack Developer'), (SELECT id FROM t_status_process WHERE process_code='SP007'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_application(id, candidate_id, job_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'), (SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'), (SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE, 0);

INSERT INTO t_assessment(id, candidate_id, job_id, hr_id, schedule, notes, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(),'Good Job Anggi',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(),TRUE,0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='firman@gmail.com'),NOW(),'Good Job Firman',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(),TRUE,0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='torang@gmail.com'),NOW(),'Good Job Torang',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(),TRUE,0);
	
INSERT INTO t_interview (id, candidate_id, job_id, interviewer_id, schedule, notes, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(),'Good Job Anggi',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='firman@gmail.com'),NOW(),'Good Job Firman',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='torang@gmail.com'),NOW(),'Good Job Torang',(SELECT id FROM t_user WHERE email='anggi@gmail.com'),NOW(), TRUE, 0);

INSERT INTO t_medical_checkup (id, candidate_id, job_id, file_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='anggi@gmail.com'), (SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='firman@gmail.com'), (SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0),
	(uuid_generate_v4(), (SELECT id FROM t_candidate WHERE email='torang@gmail.com'), (SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_file WHERE file='system'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(), TRUE, 0);

INSERT INTO t_offering(id, candidate_id, job_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);
	
INSERT INTO t_hired(id, candidate_id, job_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);
	
INSERT INTO t_skill_test(id, test_name, job_id, grade, notes, candidate_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(), 'Test Fullstack Developer',(SELECT id FROM t_job WHERE job_title='Fullstack Developer'), 90, 'Good Job',(SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(), 'Test Backend Developer',(SELECT id FROM t_job WHERE job_title='Backend Developer'), 80, 'Good Job',(SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(), 'Test Frontend Developer',(SELECT id FROM t_job WHERE job_title='Frontend Developer'), 70, 'Good Job',(SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);

INSERT INTO t_question(id, question, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),'1+1=?',(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),'1+2=?',(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),'1+3=?',(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);

INSERT INTO t_skill_test_question(id, question_id, skill_test_id, created_by, created_at, is_active, ver) VALUES
	(uuid_generate_v4(), (SELECT id FROM t_question WHERE question='1+1=?'),(SELECT id FROM t_skill_test WHERE test_name='Test Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(), (SELECT id FROM t_question WHERE question='1+2=?'),(SELECT id FROM t_skill_test WHERE test_name='Test Backend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(), (SELECT id FROM t_question WHERE question='1+3=?'),(SELECT id FROM t_skill_test WHERE test_name='Test Frontend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);

INSERT INTO t_question_option(id, labels, is_answer, question_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),'2',TRUE,(SELECT id FROM t_question WHERE question='1+1=?'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),'3',FALSE,(SELECT id FROM t_question WHERE question='1+1=?'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),'4',FALSE,(SELECT id FROM t_question WHERE question='1+1=?'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),'5',FALSE,(SELECT id FROM t_question WHERE question='1+1=?'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);

INSERT INTO t_blacklist (id, candidate_id, company_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='anggi@gmail.com'),(SELECT id FROM t_company WHERE company_code='CP001'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='firman@gmail.com'),(SELECT id FROM t_company WHERE company_code='CP002'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_candidate WHERE email='torang@gmail.com'),(SELECT id FROM t_company WHERE company_code='CP003'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);

INSERT INTO t_job_benefit(id,benefit_id, job_id, created_by, created_at, is_active, ver) VALUES 
	(uuid_generate_v4(),(SELECT id FROM t_benefit WHERE benefit_code='BN001'),(SELECT id FROM t_job WHERE job_title='Fullstack Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_benefit WHERE benefit_code='BN002'),(SELECT id FROM t_job WHERE job_title='Backend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0),
	(uuid_generate_v4(),(SELECT id FROM t_benefit WHERE benefit_code='BN003'),(SELECT id FROM t_job WHERE job_title='Frontend Developer'),(SELECT id FROM t_user WHERE email='anggi@gmail.com'), NOW(),TRUE,0);
