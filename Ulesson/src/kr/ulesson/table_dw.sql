create table category(
 ct_num number primary key,
 ct_name varchar2(30) not null unique,
 parent_ct_num number(2),
 ct_date date not null,
 ct_depth number(2) not null,
 
);

create sequence ct_seq
 start with 1
 increment by 1
 maxvalue 100000;

create table wishlist(
 wish_num number primary key,
 les_num number not null,
 mem_id varchar2(12) not null,
 constraint fk_wishlist_lesson foreign key (les_num) references lesson (les_num),
 constraint fk_wishlist_member foreign key (mem_id) references member (mem_id)
 );
 
 create sequence wish_seq
  start with 1
  increment by 1
  maxvalue 1000000;
 
 create table customer_inquire(
  iq_num number primary key,
  iq_cate varchar2(30) not null,
  iq_content varchar2(300) not null,
  mem_id varchar2(12) not null,
  constraint fk_inquire_member foreign key (mem_id) references member (mem_id),
  iq_date date DEFAULT SYSDATE NOT NULL,
  iq_mdate date DEFAULT SYSDATE NOT NULL,
  rs_content varchar2(300),
  rs_date date
 );
 
 create sequence iq_seq
  start with 1
  increment by 1
  maxvalue 1000000;
 
 create table notice(
  nt_num number primary key,
  nt_content varchar2(600),
  nt_type number(1) not null,
  nt_date date default sysdate not null
 );
 
 create sequence nt_seq
  start with 1
  increment by 1
  maxvalue 1000000;