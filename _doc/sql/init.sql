/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.7.21-log : Database - boot-b03
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`boot-b03` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `boot-b03`;

/*Table structure for table `gen_table` */

DROP TABLE IF EXISTS `gen_table`;

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表';

/*Data for the table `gen_table` */

/*Table structure for table `gen_table_column` */

DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码生成业务表字段';

/*Data for the table `gen_table_column` */

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_cron_triggers` */

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` tinyint(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` tinyint(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_job_details` */

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_triggers` */

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `config_id` bigint(20) NOT NULL COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数配置表';

/*Data for the table `sys_config` */

insert  into `sys_config`(`config_id`,`config_name`,`config_key`,`config_value`,`config_type`,`remark`) values (1267339485323071546,'主框架页-默认皮肤样式名称','sys_index_skinName','skin-blue','Y','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(1267339698431463424,'用户管理-账号初始密码','sys_user_initPassword','123456','Y','初始化密码 123456'),(1267339711018569728,'主框架页-侧边栏主题','sys_index_sideTheme','theme-dark','Y','深色主题theme-dark，浅色主题theme-light');

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT 'N' COMMENT '状态（Y正常N停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典数据表';

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`dict_code`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`list_class`,`is_default`,`status`,`remark`) values (1,1,'男','0','sys_user_sex','','','Y','Y','性别男'),(2,2,'女','1','sys_user_sex','','','N','Y','性别女'),(3,3,'未知','2','sys_user_sex','','','N','Y','性别未知'),(4,1,'显示','Y','sys_show_hide','','primary','Y','Y','显示菜单'),(5,2,'隐藏','N','sys_show_hide','','danger','N','Y','隐藏菜单'),(6,1,'正常','Y','sys_normal_disable','','primary','Y','Y','正常状态'),(7,2,'停用','N','sys_normal_disable','','danger','N','Y','停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','Y','默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','Y','系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','Y','系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','Y','系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','Y','通知'),(15,2,'公告','2','sys_notice_type','','success','N','Y','公告'),(18,1,'新增','1','sys_oper_type','','info','N','Y','新增操作'),(19,2,'修改','2','sys_oper_type','','info','N','Y','修改操作'),(20,3,'删除','3','sys_oper_type','','danger','N','Y','删除操作'),(22,4,'导出','4','sys_oper_type','','warning','N','Y','导出操作'),(23,5,'导入','5','sys_oper_type','','warning','N','Y','导入操作'),(25,6,'代码','6','sys_oper_type','','warning','N','Y','代码'),(26,99,'其他','99','sys_oper_type','','danger','N','Y','其他'),(27,1,'正常','Y','sys_oper_status',NULL,NULL,'N','Y','正常'),(28,2,'失败','N','sys_oper_status',NULL,NULL,'N','Y','失败');

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT 'Y' COMMENT '状态（Y正常N停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`dict_id`,`dict_name`,`dict_type`,`status`,`remark`) values (1267339845307600896,'用户性别','sys_user_sex','Y','用户性别列表'),(1267339849501904896,'菜单状态','sys_show_hide','Y','菜单状态列表'),(1267339862089011200,'系统开关','sys_normal_disable','Y','系统开关列表'),(1267339874676117504,'任务分组','sys_job_group','Y','任务分组列表'),(1267339899850330112,'系统是否','sys_yes_no','Y','系统是否列表'),(1267339908243132416,'通知类型','sys_notice_type','Y','通知类型列表'),(1267339950207143936,'操作类型','sys_oper_type','Y','操作类型列表'),(1357231985294073858,'操作状态','sys_oper_status','Y','操作状态列表');

/*Table structure for table `sys_job` */

DROP TABLE IF EXISTS `sys_job`;

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT 'N' COMMENT '是否并发执行（Y允许N禁止）',
  `status` char(1) DEFAULT 'N' COMMENT '状态（Y正常N暂停）',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

/*Data for the table `sys_job` */

/*Table structure for table `sys_job_log` */

DROP TABLE IF EXISTS `sys_job_log`;

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

/*Data for the table `sys_job_log` */

/*Table structure for table `sys_login_log` */

DROP TABLE IF EXISTS `sys_login_log`;

CREATE TABLE `sys_login_log` (
  `info_id` bigint(20) NOT NULL COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ip_addr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `ip_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT 'N' COMMENT '登录状态（Y成功 N失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统访问记录';

/*Data for the table `sys_login_log` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` char(1) DEFAULT 'N' COMMENT '是否为外链（Y是N否）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（Y显示N隐藏）',
  `status` char(1) DEFAULT 'Y' COMMENT '菜单状态（Y正常N停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`menu_type`,`visible`,`status`,`perms`,`icon`,`remark`) values (1590993102,'系统管理',0,2,'sys',NULL,'N','M','Y','Y','','system','系统管理目录'),(1590993118,'系统监控',0,3,'monitor',NULL,'N','M','Y','Y','','monitor','系统监控目录'),(1590993136,'系统工具',0,4,'tool',NULL,'N','M','Y','Y','','tool','系统工具目录'),(1590993159,'用户管理',1590993506,4,'user','sys/user/index','N','C','Y','Y','sys:user:list','user','用户管理菜单'),(1590993181,'角色管理',1590993506,1,'role','sys/role/index','N','C','Y','Y','sys:role:list','peoples','角色管理菜单'),(1590993195,'菜单管理',1590993102,1,'menu','sys/menu/index','N','C','Y','Y','sys:menu:list','tree-table','菜单管理菜单'),(1590993303,'字典管理',1590993102,2,'dict','sys/dict/index','N','C','Y','Y','sys:dict:list','dict','字典管理菜单'),(1590993314,'参数设置',1590993102,3,'config','sys/config/index','N','C','Y','Y','sys:config:list','edit','参数设置菜单'),(1590993348,'日志管理',1590993118,3,'log','sys/log/index','N','M','Y','Y','','log','日志管理菜单'),(1590993365,'在线用户',1590993118,1,'online','monitor/online/index','N','C','Y','Y','monitor:online:list','online','在线用户菜单'),(1590993390,'定时任务',1590993118,2,'job','monitor/job/index','N','C','Y','Y','monitor:job:list','job','定时任务菜单'),(1590993434,'表单构建',1590993136,1,'build','tool/build/index','N','C','Y','Y','tool:build:list','build','表单构建菜单'),(1590993447,'代码生成',1590993136,2,'gen','tool/gen/index','N','C','Y','Y','tool:gen:list','code','代码生成菜单'),(1590993460,'操作日志',1590993348,1,'operLog','monitor/operLog/index','N','C','Y','Y','monitor:operLog:list','form','操作日志菜单'),(1590993481,'登录日志',1590993348,2,'loginLog','monitor/loginLog/index','N','C','Y','Y','monitor:loginLog:list','logininfor','登录日志菜单'),(1590993506,'用户管理',0,1,'users',NULL,'N','M','Y','Y',NULL,'people',''),(1590993541,'用户查询',1590993159,1,'','','N','F','Y','Y','sys:user:query','#',''),(1590993543,'用户新增',1590993159,2,'','','N','F','Y','Y','sys:user:add','#',''),(1590993546,'用户修改',1590993159,3,'','','N','F','Y','Y','sys:user:edit','#',''),(1590993549,'用户删除',1590993159,4,'','','N','F','Y','Y','sys:user:remove','#',''),(1590993554,'用户导出',1590993159,5,'','','N','F','Y','Y','sys:user:export','#',''),(1590993556,'用户导入',1590993159,6,'','','N','F','Y','Y','sys:user:import','#',''),(1590993559,'重置密码',1590993159,7,'','','N','F','Y','Y','sys:user:resetPwd','#',''),(1590993564,'角色查询',1590993181,1,'','','N','F','Y','Y','sys:role:query','#',''),(1590993567,'角色新增',1590993181,2,'','','N','F','Y','Y','sys:role:add','#',''),(1590993570,'角色修改',1590993181,3,'','','N','F','Y','Y','sys:role:edit','#',''),(1590993579,'角色删除',1590993181,4,'','','N','F','Y','Y','sys:role:remove','#',''),(1590993624,'菜单查询',1590993195,1,'','','N','F','Y','Y','sys:menu:query','#',''),(1590993625,'菜单新增',1590993195,2,'','','N','F','Y','Y','sys:menu:add','#',''),(1590993629,'菜单修改',1590993195,3,'','','N','F','Y','Y','sys:menu:edit','#',''),(1590993633,'菜单删除',1590993195,4,'','','N','F','Y','Y','sys:menu:remove','#',''),(1590993730,'字典查询',1590993303,1,'#','','N','F','Y','Y','sys:dict:query','#',''),(1590993734,'字典新增',1590993303,2,'#','','N','F','Y','Y','sys:dict:add','#',''),(1590993737,'字典修改',1590993303,3,'#','','N','F','Y','Y','sys:dict:edit','#',''),(1590993750,'字典删除',1590993303,4,'#','','N','F','Y','Y','sys:dict:remove','#',''),(1590993757,'参数查询',1590993314,1,'#','','N','F','Y','Y','sys:config:query','#',''),(1590993761,'参数新增',1590993314,2,'#','','N','F','Y','Y','sys:config:add','#',''),(1590993764,'参数修改',1590993314,3,'#','','N','F','Y','Y','sys:config:edit','#',''),(1590993770,'参数删除',1590993314,4,'#','','N','F','Y','Y','sys:config:remove','#',''),(1590993791,'操作查询',1590993460,1,'#','','N','F','Y','Y','monitor:operLog:query','#',''),(1590993793,'操作删除',1590993460,2,'#','','N','F','Y','Y','monitor:operLog:remove','#',''),(1590993800,'登录查询',1590993481,1,'#','','N','F','Y','Y','monitor:loginLog:query','#',''),(1590993804,'登录删除',1590993481,2,'#','','N','F','Y','Y','monitor:loginLog:remove','#',''),(1590993811,'在线查询',1590993365,1,'#','','N','F','Y','Y','monitor:online:query','#',''),(1590993816,'批量强退',1590993365,2,'#','','N','F','Y','Y','monitor:online:batchLogout','#',''),(1590993820,'单条强退',1590993365,3,'#','','N','F','Y','Y','monitor:online:forceLogout','#',''),(1590993824,'任务查询',1590993390,1,'#','','N','F','Y','Y','monitor:job:query','#',''),(1590993827,'任务新增',1590993390,2,'#','','N','F','Y','Y','monitor:job:add','#',''),(1590993831,'任务修改',1590993390,3,'#','','N','F','Y','Y','monitor:job:edit','#',''),(1590993834,'任务删除',1590993390,4,'#','','N','F','Y','Y','monitor:job:remove','#',''),(1590993838,'状态修改',1590993390,5,'#','','N','F','Y','Y','monitor:job:changeStatus','#',''),(1590993845,'生成查询',1590993447,1,'#','','N','F','Y','Y','tool:gen:query','#',''),(1590993849,'生成修改',1590993447,2,'#','','N','F','Y','Y','tool:gen:edit','#',''),(1590993852,'生成删除',1590993447,3,'#','','N','F','Y','Y','tool:gen:remove','#',''),(1590993854,'导入代码',1590993447,4,'#','','N','F','Y','Y','tool:gen:import','#',''),(1590993860,'预览代码',1590993447,5,'#','','N','F','Y','Y','tool:gen:preview','#',''),(1590993863,'生成代码',1590993447,6,'#','','N','F','Y','Y','tool:gen:code','#',''),(1612438635,'执行一次',1590993390,6,'',NULL,'N','F','Y','Y','monitor:job:run','#',''),(1612507643,'通知公告',1590993102,4,'notice','sys/notice/index','N','C','Y','Y','sys:notice:list','system',''),(1612507748,'新增',1612507643,1,'',NULL,'N','F','Y','Y','sys:notice:add','#',''),(1612507772,'修改',1612507643,2,'',NULL,'N','F','Y','Y','sys:notice:edit','#',''),(1612507829,'删除',1612507643,3,'',NULL,'N','F','Y','Y','sys:notice:remove','#',''),(1612507845,'查询',1612507643,4,'',NULL,'N','F','Y','Y','sys:notice:query','#','');

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `notice_id` bigint(20) NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知2公告）',
  `notice_content` varchar(2000) DEFAULT NULL COMMENT '公告内容',
  `status` char(1) DEFAULT 'N' COMMENT '公告状态（Y正常N禁用）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知公告表';

/*Data for the table `sys_notice` */

/*Table structure for table `sys_oper_log` */

DROP TABLE IF EXISTS `sys_oper_log`;

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` char(2) DEFAULT '0' COMMENT '业务类型',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` char(1) DEFAULT 'N' COMMENT '操作状态（Y正常N异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志记录';

/*Data for the table `sys_oper_log` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL DEFAULT 'Y' COMMENT '角色状态（Y正常N停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`),
  UNIQUE KEY `role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

/*Data for the table `sys_role` */

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

/*Data for the table `sys_role_menu` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT '0' COMMENT '角色id',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` char(2) DEFAULT '1' COMMENT '用户类型（0管理员1用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(2000) DEFAULT '' COMMENT '头像地址',
  `salt` varchar(4) DEFAULT '' COMMENT '盐',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT 'Y' COMMENT '帐号状态（Y正常N停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`role_id`,`user_name`,`nick_name`,`user_type`,`email`,`phone`,`sex`,`avatar`,`salt`,`password`,`status`,`remark`) values (1,0,'super','super1','0','939313737@qq.com','13888888888','1','https://upload.q3z3.com/group1/M00/00/00/CmQCymAdGqCAbQLhAADFNKFGhgo381.png','n0ro','d875341e29bd968c0d0da108ec6a3f9e','Y','管理员');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
