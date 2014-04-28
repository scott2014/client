CREATE TABLE authority (
  id int(4) NOT NULL,
  authorityCode int(16) DEFAULT NULL,
  authorityName varchar(10) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE category (
  id int(16) NOT NULL AUTO_INCREMENT,
  categoryName varchar(20) NOT NULL,
  isLeaf int(2) DEFAULT NULL,
  isRoot int(2) DEFAULT NULL,
  pId int(16) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE designer (
  id bigint(64) NOT NULL,
  userId bigint(64) DEFAULT NULL,
  extra varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE follower (
  id bigint(64) NOT NULL,
  userId bigint(64) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE list (
  id bigint(64) NOT NULL,
  userId bigint(64) DEFAULT NULL,
  productId bigint(64) DEFAULT NULL,
  categorys varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE poster (
  id bigint(64) NOT NULL,
  userId bigint(64) DEFAULT NULL,
  productId bigint(64) DEFAULT NULL,
  categorys varchar(200) DEFAULT NULL,
  page int(6) DEFAULT NULL,
  images varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE privatemsg (
  id bigint(64) NOT NULL,
  sUserId bigint(64) DEFAULT NULL,
  rUserId bigint(64) DEFAULT NULL,
  msg text,
  sDate date DEFAULT NULL,
  rDate date DEFAULT NULL,
  hasRead int(2) DEFAULT NULL,
  hasDel int(2) DEFAULT NULL,
  attach varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY rUserId (rUserId),
  CONSTRAINT privatemsg_ibfk_1 FOREIGN KEY (rUserId) REFERENCES user (id)
);


CREATE TABLE product (
  id bigint(64) NOT NULL AUTO_INCREMENT,
  price double DEFAULT NULL,
  description text,
  images varchar(600) DEFAULT NULL,
  createDate date DEFAULT NULL,
  color varchar(10) DEFAULT NULL,
  fields varchar(100) DEFAULT NULL,
  pName varchar(30) DEFAULT NULL,
  categoryId int(16) DEFAULT NULL,
  nLike bigint(64) DEFAULT NULL,
  userId bigint(64) DEFAULT NULL,
  nickname varchar(30) DEFAULT NULL,
  fromWhere int(4) NOT NULL,
  PRIMARY KEY (id),
  KEY categoryId (categoryId),
  CONSTRAINT product_ibfk_1 FOREIGN KEY (categoryId) REFERENCES category (id)
);


CREATE TABLE productcomment (
  id bigint(64) NOT NULL AUTO_INCREMENT,
  publishDate date DEFAULT NULL,
  userId bigint(64) DEFAULT NULL,
  productId bigint(64) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_Reference_2 (productId),
  CONSTRAINT FK_Reference_2 FOREIGN KEY (productId) REFERENCES product (id)
);


CREATE TABLE ulikeproduct (
  id bigint(64) NOT NULL AUTO_INCREMENT,
  productId bigint(64) DEFAULT NULL,
  userId bigint(64) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_Reference_3 (userId),
  KEY FK_Reference_4 (productId),
  CONSTRAINT FK_Reference_3 FOREIGN KEY (userId) REFERENCES user (id),
  CONSTRAINT FK_Reference_4 FOREIGN KEY (productId) REFERENCES product (id)
)


CREATE TABLE user (
  id bigint(64) NOT NULL AUTO_INCREMENT,
  userName varchar(30) NOT NULL,
  password varchar(30) NOT NULL,
  nickname varchar(30) NOT NULL,
  gender tinyint(2) DEFAULT NULL,
  constellation varchar(10) DEFAULT NULL,
  member tinyint(2) DEFAULT NULL,
  memberPoints int(32) DEFAULT NULL,
  description text,
  photo varchar(50) DEFAULT NULL,
  birthdate date DEFAULT NULL,
  registerDate date DEFAULT NULL,
  lastLoginTime date DEFAULT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE user_authority (
  id bigint(64) NOT NULL,
  userId bigint(64) DEFAULT NULL,
  authorityId int(4) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY userId (userId),
  KEY authorityId (authorityId),
  CONSTRAINT user_authority_ibfk_1 FOREIGN KEY (userId) REFERENCES user (id),
  CONSTRAINT user_authority_ibfk_2 FOREIGN KEY (authorityId) REFERENCES authority (id)
);


CREATE TABLE user_follower (
  id bigint(64) NOT NULL,
  followerId bigint(64) DEFAULT NULL,
  userId bigint(64) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY followerId (followerId),
  KEY userId (userId),
  CONSTRAINT user_follower_ibfk_1 FOREIGN KEY (followerId) REFERENCES follower (id),
  CONSTRAINT user_follower_ibfk_2 FOREIGN KEY (userId) REFERENCES user (id)
);