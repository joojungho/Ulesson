create table member(
	mem_id varchar2(12) primary key,
	mem_pw varchar2(30) not null,
	mem_auth number(1) not null,
	mem_name varchar2(30) not null,
	mem_cell varchar2(15) not null,
	mem_email varchar2(50) not null,
	mem_date date DEFAULT SYSDATE not null,
	mem_mdate date,
	mem_point number(6) not null
);

create table lesson(
	les_num number primary key,
	les_name varchar2(30) not null,
	les_teacher varchar2(15) not null,
	les_date date DEFAULT SYSDATE not null,
	les_mdate date,
	les_price number(6) not null,
	les_detail varchar2(300) not null,
	les_time number(4) not null,
	ct_num number not null,
	les_score number(3) not null,
	FOREIGN KEY (ct_num) REFERENCES category(ct_num)
);

create sequence les_seq
start with 1
increment by 1
maxvalue 100000;

create table review(
	rv_num number primary key,
	les_num number not null,
	mem_id varchar2(12) not null,
	rv_content varchar2(300),
	rv_score number(1) not null,
	FOREIGN KEY (les_num) REFERENCES lesson(les_num),
	FOREIGN KEY (mem_id) REFERENCES member(mem_id)
);

create sequence rv_seq
start with 1
increment by 1
maxvalue 100000;

create table section(
	sec_num number primary key,
	sec_name varchar2(30) not null,
	les_num number not null,
	sec_link varchar2(150) not null,
	sec_time number(3) not null,
	sec_date date DEFAULT SYSDATE not null,
	sec_mdate date,
	FOREIGN KEY (les_num) REFERENCES lesson(les_num)
);
 
create sequence sc_seq
start with 1
increment by 1
maxvalue 1000000;;