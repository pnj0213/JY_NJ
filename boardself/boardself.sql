CREATE TABLE boardself(
no number(3) primary key,
title varchar2(100),
content varchar2(500),
author varchar2(100),
nal varchar2(10),
readcount number(3)
)

create sequence boardself_no
insert into BOARDSELF(no, title, content, author, nal, readcount) values(boardself_no.nextval,'����1','����1','�ۼ���','2020.11.14',0)
select * from boardself