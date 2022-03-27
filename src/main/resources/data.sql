INSERT INTO country (id,code, name) VALUES
  (1,'FR', 'FRANCE'),
  (2,'UK', 'ENGLAND'),
  (3,'DE', 'DEUTCHLAND');


INSERT INTO user (id,country_id, username,birthdate,gender,phonenumber) VALUES
  (1,1, 'JEAN',null ,null,null),
  (2,1, 'JULES',null ,null,null),
  (3,2, 'BOB',null ,null,null),
  (4,2 ,'ARTHUR',null,null,null),
  (5,1, 'YVES','1998-10-10',null,null),
  (6,2, 'THOMAS','2013-10-10',null,null),
  (7,2, 'EDOUARD','2013-10-10','M',null),
  (8,2, 'EDOUARD','2001-12-12','M',null),
  (9,1, 'EDOUARD','1999-01-01','M',null),
  (10,1, 'EDOUARD','2019-01-01','M',null),
  (11,2, 'ALEXANDER','1999-10-10','M',null),
  (12,2,'SARAH','2013-10-10','F',null),
  (13,2, 'SARAH','2013-10-10','F',null),
  (14,1, 'PIERRE','2000-10-10','M','0601020313'),
  (15,1, 'PAUL','1990-10-10','M','0602020314'),
  (16,1, 'PASCAL','2012-10-28','M','0603020315'),
  (17,1, 'JACQUES','2001-10-28','M','0603020316'),
  (18,1, 'JEAN','2001-10-28','M','0603020317'),
  (19,1, 'JULES','2001-10-28','M','0603020318'),
  (20,1, 'JULES','2001-10-28','M','0603020319'),
  (21,1, 'ISABELLE','2001-10-28','F','0611020320'),
  (22,1, 'MATHIDLE','2001-11-28','F','0612020321'),
  (23,1, 'CLOTHILDE','2019-12-28','F','0612020322'),
  (24,1, 'MARIE','1998-10-28','F','0612020323')
 ;

