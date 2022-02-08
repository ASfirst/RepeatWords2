DROP TABLE IF EXISTS `read_shall_learning_tb`;
CREATE TABLE read_shall_learning_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `read_shall_learning_tb_index`;
CREATE UNIQUE INDEX read_shall_learning_tb_index ON read_shall_learning_tb (
    fd_id ASC
);


DROP TABLE IF EXISTS `read_learned_today_tb`;
CREATE TABLE read_learned_today_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `read_learned_today_tb_index`;
CREATE UNIQUE INDEX read_learned_today_tb_index ON read_learned_today_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `read_grasped_tb`;
CREATE TABLE read_grasped_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `read_grasped_tb_index`;
CREATE UNIQUE INDEX read_grasped_tb_index ON read_grasped_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `read_deserted_tb`;
CREATE TABLE read_deserted_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `read_deserted_tb_index`;
CREATE UNIQUE INDEX read_deserted_tb_index ON read_deserted_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `read_marked_tb`;
CREATE TABLE read_marked_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `read_marked_tb_index`;
CREATE UNIQUE INDEX read_marked_tb_index ON read_marked_tb (
    fd_id ASC
);
