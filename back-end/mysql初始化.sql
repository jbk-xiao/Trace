CREATE TABLE comment_score (
	sku_id VARCHAR ( 20 ) NOT NULL,
	brand VARCHAR ( 20 ) NOT NULL,
	package_score VARCHAR ( 20 ),
	price_score VARCHAR ( 20 ),
	logistics_score VARCHAR ( 20 ),
	taste_score VARCHAR ( 20 ),
	service_score VARCHAR ( 20 ),
	PRIMARY KEY ( sku_id ) 
) ENGINE = INNODB CHARSET = utf8;

CREATE TABLE company (
	regis_id VARCHAR ( 20 ) NOT NULL,
	proj_name VARCHAR ( 50 ) NOT NULL,
	img_url VARCHAR ( 100 ),
	finance_round VARCHAR ( 10 ),
	es_time VARCHAR ( 20 ),
	region VARCHAR ( 20 ),
	proj_desc text,
	company_name VARCHAR ( 100 ),
	address text,
	lng VARCHAR ( 20 ) NULL,
	lat VARCHAR ( 20 ) NULL,
	regis_capital VARCHAR ( 20 ),
	org_code VARCHAR ( 30 ),
	phone_num VARCHAR ( 20 ),
	PRIMARY KEY ( regis_id ) 
) ENGINE = INNODB CHARSET = utf8;

CREATE TABLE jd_info (
	sku_id VARCHAR ( 20 ) NOT NULL,
	title text,
	price DOUBLE,
	shop VARCHAR ( 50 ),
	detail_url text,
	pname VARCHAR ( 100 ),
	weight VARCHAR ( 20 ),
	origin VARCHAR ( 20 ),
	img_url text,
	brand VARCHAR ( 20 ),
	brand_url text,
	commentCount INT,
goodRate DOUBLE,
PRIMARY KEY ( sku_id )) ENGINE = INNODB CHARSET = utf8;

CREATE TABLE age_distribution(
	age_id INT AUTO_INCREMENT,
	age_range VARCHAR(20),
	tgi VARCHAR(20),
	word_rate VARCHAR(20),
	all_rate VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(age_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
	
CREATE TABLE relate_search(
	relate_id INT AUTO_INCREMENT,
	word VARCHAR(70),
	pv VARCHAR(20),
	ratio VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(relate_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
	
CREATE TABLE sex_distribution(
	sex_id INT AUTO_INCREMENT,
	sex_range VARCHAR(20),
	tgi VARCHAR(20),
	word_rate VARCHAR(20),
	all_rate VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(sex_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
	
CREATE TABLE province_index(
	province_id INT AUTO_INCREMENT,
	province VARCHAR(20),
	sum_index VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(province_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
	
CREATE TABLE baidu_index(
	index_id INT AUTO_INCREMENT,
	`date` VARCHAR(20),
	sum_index VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(index_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;

create table 3d_score (
	sku_id VARCHAR(20) NOT NULL,
	brand VARCHAR(20) NOT NULL,
	comment_score VARCHAR(30) NOT NULL,
	price VARCHAR(20) NOT NULL,
	`count` VARCHAR(20) NOT NULL,
	PRIMARY KEY ( sku_id )