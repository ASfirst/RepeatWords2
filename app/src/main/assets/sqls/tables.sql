DROP TABLE IF EXISTS `dictionary_tb`;
CREATE TABLE `dictionary_tb` (
  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
  `en` varchar(60) DEFAULT NULL,
  `ch` varchar(255) DEFAULT NULL,
  `phonetic` varchar(255) DEFAULT NULL
);

DROP TABLE IF EXISTS `listen_have_grasped_tb`;
CREATE TABLE `listen_have_grasped_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `listen_have_learned_today_tb`;
CREATE TABLE `listen_have_learned_today_tb` (
  `id` int DEFAULT NULL,
 	`time` DATETIME
) ;

DROP TABLE IF EXISTS `listen_marked_tb`;
CREATE TABLE `listen_marked_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `listen_shall_learning_tb`;
CREATE TABLE `listen_shall_learning_tb` (
   `id`       INT     DEFAULT NULL,
	 `time` DATETIME
);

DROP TABLE IF EXISTS `listen_deserted_learning_tb`;
CREATE TABLE `listen_deserted_learning_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `speak_have_grasped_tb`;
CREATE TABLE `speak_have_grasped_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `speak_have_learned_today_tb`;
CREATE TABLE `speak_have_learned_today_tb` (
 `id` int DEFAULT NULL,
  	`time` DATETIME
) ;

DROP TABLE IF EXISTS `speak_marked_tb`;
CREATE TABLE `speak_marked_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `speak_shall_learning_tb`;
CREATE TABLE `speak_shall_learning_tb` (
   `id`       INT     DEFAULT NULL,
	 `time` DATETIME
);

DROP TABLE IF EXISTS `speak_deserted_learning_tb`;
CREATE TABLE `speak_deserted_learning_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `write_have_grasped_tb`;
CREATE TABLE `write_have_grasped_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `write_have_learned_today_tb`;
CREATE TABLE `write_have_learned_today_tb` (
 `id` int DEFAULT NULL,
  	`time` DATETIME
) ;

DROP TABLE IF EXISTS `write_marked_tb`;
CREATE TABLE `write_marked_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;

DROP TABLE IF EXISTS `write_shall_learning_tb`;
CREATE TABLE `write_shall_learning_tb` (
   `id`       INT     DEFAULT NULL,
	 `time` DATETIME
);

DROP TABLE IF EXISTS `write_deserted_learning_tb`;
CREATE TABLE `write_deserted_learning_tb` (
  `id` int DEFAULT NULL,
	`time` DATETIME
) ;