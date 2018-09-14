INSERT INTO usr VALUES (1, NULL, true, 'bek@prog-matik.ru', '$2a$08$mLGSTaLKMrLT4P11W.YhgO/MeAwQgQfsCKovEIG1CaVMm8Z/351ga', 'admin');

INSERT INTO userroles VALUES (1, 'ADMIN');
INSERT INTO userroles VALUES (1, 'USER');

SELECT pg_catalog.setval('public.hibernate_sequence', 2, true);