delete from userroles;
delete from usr;

INSERT INTO usr VALUES (1, NULL, true, 'bek.andrey@gmail.com', '$2a$08$mLGSTaLKMrLT4P11W.YhgO/MeAwQgQfsCKovEIG1CaVMm8Z/351ga', 'admin');
INSERT INTO usr VALUES (2, NULL, true, 'bek@prog-matik.ru', '$2a$08$jCJb/D3fUSo7VCQHynobO.WpgQQmCatdmPu6.6eMCoca82Im8ouOO', 'bek');

INSERT INTO userroles VALUES
  (1, 'ADMIN'),(1, 'USER'),(2, 'USER');

SELECT pg_catalog.setval('public.hibernate_sequence', 2, true);