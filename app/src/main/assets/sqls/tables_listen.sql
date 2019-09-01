DROP TABLE IF EXISTS `listen_shall_learning_tb`;
CREATE TABLE listen_shall_learning_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `listen_shall_learning_tb_index`;
CREATE UNIQUE INDEX listen_shall_learning_tb_index ON listen_shall_learning_tb (
    fd_id ASC
);


DROP TABLE IF EXISTS `listen_learned_today_tb`;
CREATE TABLE listen_learned_today_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `listen_learned_today_tb_index`;
CREATE UNIQUE INDEX listen_learned_today_tb_index ON listen_learned_today_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `listen_grasped_tb`;
CREATE TABLE listen_grasped_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `listen_grasped_tb_index`;
CREATE UNIQUE INDEX listen_grasped_tb_index ON listen_grasped_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `listen_deserted_tb`;
CREATE TABLE listen_deserted_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `listen_deserted_tb_index`;
CREATE UNIQUE INDEX listen_deserted_tb_index ON listen_deserted_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `listen_marked_tb`;
CREATE TABLE listen_marked_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
);
DROP INDEX IF EXISTS `listen_marked_tb_index`;
CREATE UNIQUE INDEX listen_marked_tb_index ON listen_marked_tb (
    fd_id ASC
);
