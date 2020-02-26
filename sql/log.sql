/*
Navicat MySQL Data Transfer

Source Server         : tj_192.168.50.143
Source Server Version : 50726
Source Host           : 192.168.50.143:3306
Source Database       : sink

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-11-15 10:35:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_proc_err
-- ----------------------------
DROP TABLE IF EXISTS `log_proc_err`;
CREATE TABLE `log_proc_err` (
  `wid` varchar(100) DEFAULT NULL,
  `workdate` varchar(10) DEFAULT NULL,
  `deal_datetime` varchar(30) DEFAULT NULL,
  `prog_run_wid` varchar(100) DEFAULT NULL,
  `progname` varchar(100) DEFAULT NULL,
  `jobname` varchar(50) DEFAULT NULL,
  `step_num` varchar(5) DEFAULT NULL,
  `errno` varchar(20) DEFAULT NULL,
  `errmsg` varchar(500) DEFAULT NULL,
  `errlevel` varchar(1) DEFAULT NULL,
  `errid` varchar(8) DEFAULT NULL,
  `errtype` varchar(1) DEFAULT NULL,
  `organ_code` varchar(11) DEFAULT NULL,
  `tablename1` varchar(20) DEFAULT NULL,
  `pkey1` varchar(200) DEFAULT NULL,
  `tablename2` varchar(20) DEFAULT NULL,
  `pkey2` varchar(200) DEFAULT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  `notes1` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for log_proc_exec
-- ----------------------------
DROP TABLE IF EXISTS `log_proc_exec`;
CREATE TABLE `log_proc_exec` (
  `wid` varchar(100) DEFAULT NULL,
  `workdate` varchar(10) DEFAULT NULL,
  `prog_run_wid` varchar(100) DEFAULT NULL,
  `progname` varchar(100) DEFAULT NULL,
  `jobname` varchar(50) DEFAULT NULL,
  `datascal` decimal(12,0) DEFAULT NULL,
  `updatescal` decimal(12,0) DEFAULT NULL,
  `insertscal` decimal(12,0) DEFAULT NULL,
  `deletescal` decimal(12,0) DEFAULT NULL,
  `step_num` varchar(5) DEFAULT NULL,
  `begin_time` varchar(30) DEFAULT NULL,
  `end_time` varchar(30) DEFAULT NULL,
  `runstatus` varchar(100) DEFAULT NULL,
  `organ_code` varchar(11) DEFAULT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  `notes1` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_jdbc_log_backup
-- ----------------------------
DROP TABLE IF EXISTS `sink_jdbc_log_backup`;
CREATE TABLE `sink_jdbc_log_backup` (
  `wid` varchar(50) NOT NULL DEFAULT '' COMMENT 'WID唯一主键',
  `task_wid` varchar(50) DEFAULT NULL COMMENT '任务WID(sink_jdbc_cof_task.WID)',
  `kafka_partition` bigint(20) DEFAULT NULL COMMENT 'kafka分区',
  `kafka_offset` bigint(20) DEFAULT NULL COMMENT 'kafka_offset数值类型',
  `sc_sys_no` varchar(10) DEFAULT NULL COMMENT '源系统编号',
  `sc_sys_schema` varchar(100) DEFAULT NULL COMMENT '源schema',
  `sc_table_name` varchar(100) DEFAULT NULL COMMENT '源表名',
  `sc_table_key_colname` varchar(250) DEFAULT NULL COMMENT '源主键字段名',
  `sc_table_key_value` varchar(250) DEFAULT ' ' COMMENT '源主键值',
  `sc_opt_date` varchar(10) DEFAULT NULL COMMENT '源操作日期YYYY-MM-DD',
  `sc_opt_time` varchar(30) DEFAULT NULL COMMENT '源操作时间戳yyyymmddhh24missff',
  `sc_opt_mark` varchar(10) DEFAULT NULL COMMENT '源操作类型I/U/D',
  `tg_sys_no` varchar(10) DEFAULT NULL COMMENT '目标系统编号',
  `tg_sys_schema` varchar(100) DEFAULT NULL COMMENT '目标schema',
  `tg_table_name` varchar(100) DEFAULT NULL COMMENT '目标表名',
  `tg_table_key_colname` varchar(250) DEFAULT NULL COMMENT '目标主键字段名',
  `tg_opt_time` varchar(30) DEFAULT NULL COMMENT '目标操作时间戳yyyymmddhh24missff',
  `tg_opt_mark` varchar(10) DEFAULT NULL COMMENT '目标操作类型I/U/D',
  `q_status` varchar(10) DEFAULT NULL COMMENT '失败处理状态0/成功/1未处理/2已重跑/3忽略/4暂停/5丢弃',
  `q_time` varchar(30) DEFAULT NULL COMMENT '失败时间戳yyyymmddhh24missff',
  `dim1` varchar(100) DEFAULT NULL COMMENT '备用1',
  `dim2` varchar(100) DEFAULT NULL COMMENT '备用2',
  `dim3` varchar(100) DEFAULT NULL COMMENT '备用3',
  `dim4` varchar(100) DEFAULT NULL COMMENT '备用4',
  `dim5` varchar(100) DEFAULT NULL COMMENT '备用5',
  `sc_ogg_text` mediumtext COMMENT 'ogg的joson报文test最大16MB',
  `q_note_text` mediumtext COMMENT '失败原因text最大16MB',
  PRIMARY KEY (`wid`),
  KEY `sc_opt_date` (`sc_opt_date`),
  KEY `sc_table_key_value` (`sc_table_key_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_jdbc_log_batch
-- ----------------------------
DROP TABLE IF EXISTS `sink_jdbc_log_batch`;
CREATE TABLE `sink_jdbc_log_batch` (
  `wid` varchar(50) NOT NULL COMMENT 'WID唯一主键',
  `task_wid` varchar(50) DEFAULT NULL COMMENT '任务WID(sink_jdbc_cof_task.WID)',
  `kafka_offset` bigint(20) DEFAULT NULL COMMENT 'kafka_offset数值类型',
  `sc_sys_no` varchar(10) DEFAULT NULL COMMENT '源系统编号',
  `sc_sys_schema` varchar(100) DEFAULT NULL COMMENT '源schema',
  `sc_table_name` varchar(100) DEFAULT NULL COMMENT '源表名',
  `tg_sys_no` varchar(10) DEFAULT NULL COMMENT '目标系统编号',
  `tg_sys_schema` varchar(100) DEFAULT NULL COMMENT '目标schema',
  `tg_table_name` varchar(100) DEFAULT NULL COMMENT '目标表名',
  `exchange_frequency` varchar(100) DEFAULT NULL COMMENT '抽取频率（秒）',
  `exchange_num` varchar(100) DEFAULT NULL COMMENT '运行次数',
  `batch_size` varchar(10) DEFAULT NULL COMMENT '批次大小',
  `batch_type` varchar(100) DEFAULT NULL COMMENT '同步方式(全删全程/merge)',
  `batch_opt_date` varchar(10) DEFAULT NULL COMMENT '批量日期YYYY-MM-DD',
  `batch_opt_start_time` varchar(30) DEFAULT NULL COMMENT '批量开始时间戳yyyymmddhh24missff',
  `batch_opt_end_time` varchar(30) DEFAULT NULL COMMENT '批量结束时间戳yyyymmddhh24missff',
  `batch_data_size` varchar(20) DEFAULT NULL COMMENT '同步数据量',
  `q_status` varchar(10) DEFAULT NULL COMMENT '状态0/成功/1未处理/2已重跑/3忽略/4暂停/5丢弃',
  `q_time` varchar(30) DEFAULT NULL COMMENT '失败时间戳yyyymmddhh24missff',
  `dim1` varchar(100) DEFAULT NULL COMMENT '备用1',
  `dim2` varchar(100) DEFAULT NULL COMMENT '备用2',
  `dim3` varchar(100) DEFAULT NULL COMMENT '备用3',
  `dim4` varchar(100) DEFAULT NULL COMMENT '备用4',
  `dim5` varchar(100) DEFAULT NULL COMMENT '备用5',
  `sc_ogg_text` mediumtext COMMENT 'ogg的joson报文test最大16MB',
  `q_note_text` mediumtext COMMENT '失败原因text最大16MB',
  PRIMARY KEY (`wid`),
  KEY `batch_opt_date` (`batch_opt_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_jdbc_log_error
-- ----------------------------
DROP TABLE IF EXISTS `sink_jdbc_log_error`;
CREATE TABLE `sink_jdbc_log_error` (
  `wid` varchar(50) NOT NULL DEFAULT '' COMMENT 'WID唯一主键',
  `task_wid` varchar(50) DEFAULT NULL COMMENT '任务WID(sink_jdbc_cof_task.WID)',
  `kafka_partition` bigint(20) DEFAULT NULL COMMENT 'kafka分区',
  `kafka_offset` bigint(20) DEFAULT NULL COMMENT 'kafka_offset数值类型',
  `sc_sys_no` varchar(10) DEFAULT NULL COMMENT '源系统编号',
  `sc_sys_schema` varchar(100) DEFAULT NULL COMMENT '源schema',
  `sc_table_name` varchar(100) DEFAULT NULL COMMENT '源表名',
  `sc_table_key_colname` varchar(250) DEFAULT NULL COMMENT '源主键字段名',
  `sc_table_key_value` varchar(250) DEFAULT ' ' COMMENT '源主键值',
  `sc_opt_date` varchar(10) DEFAULT NULL COMMENT '源操作日期YYYY-MM-DD',
  `sc_opt_time` varchar(30) DEFAULT NULL COMMENT '源操作时间戳yyyymmddhh24missff',
  `sc_opt_mark` varchar(10) DEFAULT NULL COMMENT '源操作类型I/U/D',
  `tg_sys_no` varchar(10) DEFAULT NULL COMMENT '目标系统编号',
  `tg_sys_schema` varchar(100) DEFAULT NULL COMMENT '目标schema',
  `tg_table_name` varchar(100) DEFAULT NULL COMMENT '目标表名',
  `tg_table_key_colname` varchar(250) DEFAULT NULL COMMENT '目标主键字段名',
  `tg_opt_time` varchar(30) DEFAULT NULL COMMENT '目标操作时间戳yyyymmddhh24missff',
  `tg_opt_mark` varchar(10) DEFAULT NULL COMMENT '目标操作类型I/U/D',
  `q_status` varchar(10) DEFAULT NULL COMMENT '失败处理状态0/成功/1未处理/2已重跑/3忽略/4暂停/5丢弃',
  `q_time` varchar(30) DEFAULT NULL COMMENT '失败时间戳yyyymmddhh24missff',
  `dim1` varchar(100) DEFAULT NULL COMMENT '备用1',
  `dim2` varchar(100) DEFAULT NULL COMMENT '备用2',
  `dim3` varchar(100) DEFAULT NULL COMMENT '备用3',
  `dim4` varchar(100) DEFAULT NULL COMMENT '备用4',
  `dim5` varchar(100) DEFAULT NULL COMMENT '备用5',
  `sc_ogg_text` mediumtext COMMENT 'ogg的joson报文test最大16MB',
  `q_note_text` mediumtext COMMENT '失败原因text最大16MB',
  PRIMARY KEY (`wid`),
  KEY `sc_table_key_value` (`sc_table_key_value`),
  KEY `sc_opt_date` (`sc_opt_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_jdbc_log_success
-- ----------------------------
DROP TABLE IF EXISTS `sink_jdbc_log_success`;
CREATE TABLE `sink_jdbc_log_success` (
  `wid` varchar(50) NOT NULL DEFAULT '' COMMENT 'WID唯一主键',
  `task_wid` varchar(50) DEFAULT NULL COMMENT '任务WID(sink_jdbc_cof_task.WID)',
  `kafka_partition` bigint(20) DEFAULT NULL COMMENT 'kafka分区',
  `kafka_offset` bigint(20) DEFAULT NULL COMMENT 'kafka_offset数值类型',
  `sc_sys_no` varchar(10) DEFAULT NULL COMMENT '源系统编号',
  `sc_sys_schema` varchar(100) DEFAULT NULL COMMENT '源schema',
  `sc_table_name` varchar(100) DEFAULT NULL COMMENT '源表名',
  `sc_table_key_colname` varchar(250) DEFAULT NULL COMMENT '源主键字段名(以配置表的为准，用逗号拼接)',
  `sc_table_key_value` varchar(250) DEFAULT ' ' COMMENT '源主键值（多个通过逗号线拼接，按顺序）',
  `sc_opt_date` varchar(10) DEFAULT NULL COMMENT '源操作日期YYYY-MM-DD',
  `sc_opt_time` varchar(30) DEFAULT NULL COMMENT '源操作时间戳yyyymmddhh24missff',
  `sc_opt_mark` varchar(10) DEFAULT NULL COMMENT '源操作类型I/U/D',
  `tg_sys_no` varchar(10) DEFAULT NULL COMMENT '目标系统编号',
  `tg_sys_schema` varchar(100) DEFAULT NULL COMMENT '目标schema',
  `tg_table_name` varchar(100) DEFAULT NULL COMMENT '目标表名',
  `tg_table_key_colname` varchar(250) DEFAULT NULL COMMENT '目标主键字段名',
  `tg_opt_time` varchar(30) DEFAULT NULL COMMENT '目标操作时间戳yyyymmddhh24missff',
  `tg_opt_mark` varchar(10) DEFAULT NULL COMMENT '目标操作类型I/U/D',
  `q_status` varchar(10) DEFAULT NULL COMMENT '失败处理状态0成功/1未处理/2已重跑/3重跑忽略/4暂停/5丢弃/6自动忽略/7人工忽略',
  `q_time` varchar(30) DEFAULT NULL COMMENT '失败时间戳yyyymmddhh24missff',
  `dim1` varchar(100) DEFAULT NULL COMMENT '备用1',
  `dim2` varchar(100) DEFAULT NULL COMMENT '备用2',
  `dim3` varchar(100) DEFAULT NULL COMMENT '备用3',
  `dim4` varchar(100) DEFAULT NULL COMMENT '备用4',
  `dim5` varchar(100) DEFAULT NULL COMMENT '备用5',
  `sc_ogg_text` mediumtext COMMENT 'ogg的joson报文test最大16MB',
  `q_note_text` mediumtext COMMENT '失败原因text最大16MB',
  PRIMARY KEY (`wid`),
  KEY `sc_table_key_value` (`sc_table_key_value`),
  KEY `sc_opt_date` (`sc_opt_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_jdbc_log_sum
-- ----------------------------
DROP TABLE IF EXISTS `sink_jdbc_log_sum`;
CREATE TABLE `sink_jdbc_log_sum` (
  `data_dt` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `dim1` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `dim2` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `dim3` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `dim4` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `dim5` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `value1` varchar(50) DEFAULT NULL COMMENT '统计日期',
  `value2` varchar(50) DEFAULT NULL COMMENT '统计日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sink_log
-- ----------------------------
DROP TABLE IF EXISTS `sink_log`;
CREATE TABLE `sink_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '操作用户',
  `OPERATION` text COMMENT '操作内容',
  `TIME` decimal(11,0) DEFAULT NULL COMMENT '耗时',
  `METHOD` text COMMENT '操作方法',
  `PARAMS` mediumtext COMMENT '方法参数',
  `IP` varchar(64) DEFAULT NULL COMMENT '操作者IP',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25516 DEFAULT CHARSET=utf8;
