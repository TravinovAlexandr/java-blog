# DROP TABLE  user;
# DROP TABLE  role;
# DROP TABLE  email_verification;
# DROP TABLE IF EXISTS article;
# DROP TABLE IF EXISTS comment;
# DROP TABLE IF EXISTS comment_bundle;
# DROP TABLE IF EXISTS users_roles;
# DROP TABLE IF EXISTS user_details;
# DROP TABLE article;
# DROP TABLE avatar;

# SET FOREIGN_KEY_CHECKS=1;
# SET GLOBAL FOREIGN_KEY_CHECKS=1;

# CREATE TABLE user (
#   id BIGINT(20) NOT NULL  AUTO_INCREMENT  PRIMARY KEY ,
#   nick VARCHAR(20) NOT NULL UNIQUE ,
#   name VARCHAR(20),
#   last_name VARCHAR(30),
#   email VARCHAR(30) NOT NULL UNIQUE ,
#   password VARCHAR(100) NOT NULL UNIQUE ,
#   enable BIT(1) NOT NULL,
#   uid VARCHAR(250) NOT NULL UNIQUE,
#   creation_date DATE NOT NULL,
#   creation_time TIME NOT NULL
# ) ENGINE = InnoDB DEFAULT CHARSET = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1;
#
# CREATE TABLE role (
#   id BIGINT(20) NOT NULL  AUTO_INCREMENT  PRIMARY KEY ,
#   name VARCHAR(20)
# ) ENGINE = InnoDB DEFAULT CHARSET = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1;
#
# CREATE TABLE email_verification(
#   id BIGINT(20) NOT NULL  AUTO_INCREMENT  PRIMARY KEY ,
#   expire_time TIME NOT NULL ,
#   expire_date DATE NOT NULL ,
#   token VARCHAR(50) NOT NULL
# ) ENGINE = InnoDB DEFAULT CHARSET = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1;

# CREATE TABLE article (
#   id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
#   uid VARCHAR(12) NOT NULL ,
#   header VARCHAR(250) NOT NULL ,
#   content VARCHAR(5000) NOT NULL ,
#   creation_date DATE NOT NULL ,
#   creation_time TIME NOT NULL
# ) ENGINE = InnoDB DEFAULT CHARSET  = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1;

# CREATE TABLE avatar (
#   id BIGINT(20) NOT NULL  AUTO_INCREMENT PRIMARY KEY,
#   image BLOB
# ) ENGINE = InnoDB DEFAULT CHARSET  = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1

# CREATE TABLE comment (
#   id BIGINT(20) NOT NULL  AUTO_INCREMENT PRIMARY KEY,
#   content VARCHAR(500) NOT NULL ,
#   creation_date DATE NOT NULL,
#   parent_id BIGINT (20)
# ) ENGINE = InnoDB DEFAULT CHARSET  = utf8 DEFAULT COLLATE utf8_unicode_ci AUTO_INCREMENT = 1

drop TABLE article_comments;