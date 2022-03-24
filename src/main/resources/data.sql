INSERT INTO country (id,code, name) VALUES
  (1,'FR', 'FRANCE'),
  (2,'UK', 'ENGLAND'),
  (3,'DE', 'DEUTCHLAND');


INSERT INTO user (id,country_id, username,birthdate,gender,phonenumber) VALUES
  (1,1, 'JEAN',null ,null,null),
  (2,2, 'BOB',null ,null,null),
  (3,2 ,'ARTHUR',null,null,null),
  (4,2, 'THOMAS','2013-10-10',null,null),
  (5,2, 'EDOUARD','2013-10-10','M',null),
  (6,2, 'EDOUARD','2001-12-12','M',null),
  (7,1, 'EDOUARD','1999-01-01','M',null),
  (8,1, 'EDOUARD','2019-01-01','M',null),
  (9,2, 'ALEXANDER','1999-10-10','M',null),
  (10,2,'SARAH','2013-10-10','F',null),
  (11,2, 'SARAH','2013-10-10','F',null),
  (12,1, 'PIERRE','2000-10-10','M','0601020312'),
  (13,1, 'PAUL','1990-10-10','M','0602020313'),
  (14,1, 'PASCAL','2012-10-28','M','0603020314'),
  (15,1, 'JACQUES','2001-10-28','M','0603020315'),
  (16,1, 'JEAN','2001-10-28','M','0603020316'),
  (17,1, 'ISABELLE','2001-10-28','F','0611020317'),
  (18,1, 'MATHIDLE','2001-11-28','F','0612020318'),
  (19,1, 'CLOTHILDE','2019-12-28','F','0612020319'),
  (20,1, 'MARIE','1998-10-28','F','0612020304')
 ;

