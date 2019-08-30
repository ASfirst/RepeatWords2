DROP TABLE IF EXISTS `write_shall_learning_tb`;
CREATE TABLE write_shall_learning_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
                     NOT NULL
);
DROP INDEX IF EXISTS `write_shall_learning_tb_index`;
CREATE UNIQUE INDEX write_shall_learning_tb_index ON write_shall_learning_tb (
    fd_id ASC
);


DROP TABLE IF EXISTS `write_learned_today_tb`;
CREATE TABLE write_learned_today_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
                     NOT NULL
);
DROP INDEX IF EXISTS `write_learned_today_tb_index`;
CREATE UNIQUE INDEX write_learned_today_tb_index ON write_learned_today_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `write_grasped_tb`;
CREATE TABLE write_grasped_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
                     NOT NULL
);
DROP INDEX IF EXISTS `write_grasped_tb_index`;
CREATE UNIQUE INDEX write_grasped_tb_index ON write_grasped_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `write_deserted_tb`;
CREATE TABLE write_deserted_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
                     NOT NULL
);
DROP INDEX IF EXISTS `write_deserted_tb_index`;
CREATE UNIQUE INDEX write_deserted_tb_index ON write_deserted_tb (
    fd_id ASC
);

DROP TABLE IF EXISTS `write_marked_tb`;
CREATE TABLE write_marked_tb (
    fd_id   INTEGER  PRIMARY KEY AUTOINCREMENT
                     UNIQUE,
    word_id INTEGER,
    time    DATETIME,
    level   INTEGER  DEFAULT (0)
                     NOT NULL
);
DROP INDEX IF EXISTS `write_marked_tb_index`;
CREATE UNIQUE INDEX write_marked_tb_index ON write_marked_tb (
    fd_id ASC
);
