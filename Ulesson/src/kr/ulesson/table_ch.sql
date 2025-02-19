CREATE TABLE board (
    bd_num NUMBER NOT NULL PRIMARY KEY,
    mem_id VARCHAR2(12) NOT NULL,
    bd_content VARCHAR2(300),
    bd_category NUMBER NOT NULL,
    bd_date DATE NOT NULL,
    bd_mdate DATE,
    bd_recommend NUMBER(3) NOT NULL,
    FOREIGN KEY (mem_id) REFERENCES member(mem_id),
    FOREIGN KEY (bd_category) REFERENCES category(ct_num)
);

create sequence bd_seq
start with 1
increment by 1
maxvalue 100000;
	
	create table board_comment(
	cmt_num number not null primary key,
	mem_id varchar2(30) not null,
	cmt_content varchar2(300),
	cmt_date date not null,
	cmt_mdate date,
	foreign key (mem_id) references member(mem_id)
	);
	
create sequence bd_cmt_seq
start with 1
increment by 1
maxvalue 100000;
	
	create table board_category(
    bdct_num number not null primary key,
    bdct_name varchar2(30) not null
	);
	
create sequence bd_ct_seq
start with 1
increment by 1
maxvalue 100000;