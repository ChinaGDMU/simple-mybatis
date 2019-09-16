/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 5.6.42-log : Database - mybatis
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mybatis`;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) DEFAULT NULL COMMENT '用户名称',
  `realName` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `sex` tinyint(3) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `position_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`realName`,`sex`,`mobile`,`email`,`note`,`position_id`) values 

(1,'xiaoming','周小明',1,'13800138000','xiaoming@qq.com','xiaoming的备注',1),

(2,'xiaoting','江小婷',1,'13800138001','xiaoting@qq.com','xiaoting的备注',2),

(3,'xiaole','李永乐',2,'13800138002','xiaole@163.com','xiaole的备注',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
