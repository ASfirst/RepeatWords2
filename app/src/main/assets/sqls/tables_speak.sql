DROP TABLE IF EXISTS `speak_shall_learning_tb`;
CREATE TABLE speak_shall_learning_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `speak_shall_learning_tb_index`;
CREATE UNIQUE INDEX speak_shall_learning_tb_index ON speak_shall_learning_tb (
    fd_id ASC
);


DROP TABLE IF EXISTS `speak_learned_today_tb`;
CREATE TABLE speak_learned_today_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `speak_learned_today_tb_index`;
CREATE UNIQUE INDEX speak_learned_today_tb_index ON speak_learned_today_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `speak_grasped_tb`;
CREATE TABLE speak_grasped_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `speak_grasped_tb_index`;
CREATE UNIQUE INDEX speak_grasped_tb_index ON speak_grasped_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `speak_deserted_tb`;
CREATE TABLE speak_deserted_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `speak_deserted_tb_index`;
CREATE UNIQUE INDEX speak_deserted_tb_index ON speak_deserted_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `speak_marked_tb`;
CREATE TABLE speak_marked_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `speak_marked_tb_index`;
CREATE UNIQUE INDEX speak_marked_tb_index ON speak_marked_tb (
    fd_id ASC
);
