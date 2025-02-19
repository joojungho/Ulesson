create table purchased_lesson(
pch_num number not null, primary key(pch_num),
les_num number not null, constraint fk_les_num foreign key (les_num) references LESSON(les_num),
mem_id varchar2(12) not null, constraint fk_mem_id foreign key (mem_id) references MEMBER(mem_id),
pch_date date default sysdate not null,
pch_status number(1) default 0 not null
);

CREATE TABLE mylesson (
    ml_num NUMBER NOT NULL, 
    PRIMARY KEY(ml_num),
    les_num NUMBER NOT NULL, 
    CONSTRAINT fk_les_num_01 FOREIGN KEY (les_num) REFERENCES LESSON(les_num),
    mem_id VARCHAR2(12) NOT NULL, 
    CONSTRAINT fk_mem_id_01 FOREIGN KEY (mem_id) REFERENCES MEMBER(mem_id),
    ml_progress NUMBER(3) NOT NULL
);
 
CREATE TABLE point (
    pt_num NUMBER NOT NULL, 
    PRIMARY KEY(pt_num),
    mem_id VARCHAR2(12) NOT NULL, 
    CONSTRAINT fk_mem_id_02 FOREIGN KEY (mem_id) REFERENCES MEMBER(mem_id),
    pt_chng_date DATE DEFAULT SYSDATE NOT NULL,
    pt_value NUMBER NOT NULL
);
   