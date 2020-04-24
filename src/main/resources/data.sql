DROP TABLE IF EXISTS hashtag;
DROP TABLE IF EXISTS tweet;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS place;

CREATE TABLE user (
  id VARCHAR(4000) NOT NULL,
  name VARCHAR(250) NOT NULL,
  screen_name VARCHAR(250) NOT NULL,
  location VARCHAR(250),
  followers_count INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE place (
  id VARCHAR(4000) NOT NULL,
  place_type VARCHAR(100),
  name VARCHAR(100) NOT NULL,
  full_name VARCHAR(250) NOT NULL,
  country_code VARCHAR(10),
  country VARCHAR(250),
  PRIMARY KEY (id)
);

CREATE TABLE tweet (
  id VARCHAR(4000) NOT NULL,
  text VARCHAR(250) NOT NULL,
  user_id VARCHAR(4000) NOT NULL,
  place_id VARCHAR(4000),
  lang VARCHAR(20),
  validated BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY(user_id) REFERENCES user(id),
  FOREIGN KEY(place_id) REFERENCES place(id)
);

CREATE TABLE hashtag (
  id BIGINT NOT NULL AUTO_INCREMENT,
  index_1 INT NOT NULL,
  index_2 INT NOT NULL,
  text VARCHAR(4000) NOT NULL,
  tweet_id VARCHAR(4000),
  PRIMARY KEY (id),
  FOREIGN KEY(tweet_id) REFERENCES tweet(id)
);
