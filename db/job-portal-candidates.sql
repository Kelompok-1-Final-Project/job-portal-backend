
--DROP TABLE IF EXISTS t_blacklist;
--DROP TABLE IF EXISTS t_employee;
--DROP TABLE IF EXISTS t_save_job;
--DROP TABLE IF EXISTS t_work_experience;
--DROP TABLE IF EXISTS t_education;
--DROP TABLE IF EXISTS t_job;
--DROP TABLE IF EXISTS t_employement_type;
--DROP TABLE IF EXISTS t_job_status;
--DROP TABLE IF EXISTS t_user_skill;
--DROP TABLE IF EXISTS t_user;
--DROP TABLE IF EXISTS t_profile;
--DROP TABLE IF EXISTS t_skill;
--DROP TABLE IF EXISTS t_company;
--DROP TABLE IF EXISTS t_gender;
--DROP TABLE IF EXISTS t_role;
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

CREATE TABLE t_role(
	id VARCHAR(36) NOT NULL,
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

CREATE TABLE t_company(
	id VARCHAR(36) NOT NULL,
	company_code VARCHAR(5) NOT NULL,
	company_name VARCHAR(30) NOT NULL,
	file_id VARCHAR(36) NOT NULL,
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
	mobile_number VARCHAR(15),
	photo_id VARCHAR(36),
	cv_id VARCHAR(36),
	expected_salary VARCHAR(36),
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
ALTER TABLE t_profile ADD CONSTRAINT profile_photo_fk
	FOREIGN KEY(photo_id)
	REFERENCES t_file(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_cv_fk
	FOREIGN KEY(cv_id)
	REFERENCES t_file(id);
ALTER TABLE t_profile ADD CONSTRAINT profile_gender_fk
	FOREIGN KEY(gender_id)
	REFERENCES t_gender(id);

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

CREATE TABLE t_employement_type(
	id VARCHAR(36) NOT NULL,
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

CREATE TABLE t_job(
	id VARCHAR(36) NOT NULL,
	job_title VARCHAR(30) NOT NULL,
	salary_start BIGINT NOT NULL,
	salary_end BIGINT NOT NULL,
	description text NOT NULL,
	end_date DATE NOT NULL,
	company_id VARCHAR(36) NOT NULL,
	job_status_id VARCHAR(36) NOT NULL,
	employement_type_id VARCHAR(36) NOT NULL,
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
	years VARCHAR(4) NOT NULL,
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
	
CREATE TABLE t_employee(
	id VARCHAR(36) NOT NULL,
	candidate_id VARCHAR(36) NOT NULL,
	created_by VARCHAR(36) NOT NULL,
	created_at timestamp NOT NULL,
	updated_by VARCHAR(36),
	updated_at timestamp,
	is_active boolean NOT NULL,
	ver int NOT NULL
);

ALTER TABLE t_employee ADD CONSTRAINT employee_pk
	PRIMARY KEY(id);
ALTER TABLE t_employee ADD CONSTRAINT employee_candidate_fk
	FOREIGN KEY(candidate_id)
	REFERENCES t_user(id);

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
	
