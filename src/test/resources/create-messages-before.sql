DELETE from messages;

insert into messages values(1, NULL, 'testtag1', 'testmessage1', 1);
insert into messages values(2, NULL, 'testtag2', 'testmessage2', 1);
insert into messages values(3, NULL, 'testtag3', 'testmessage3', 1);

alter SEQUENCE hibernate_sequence RESTART WITH 10;