/*
MySQL Backup
Source Server Version: 5.7.26
Source Database: sink
Date: 2019/11/15 13:49:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Procedure definition for `findMenuChildren`
-- ----------------------------
DROP FUNCTION IF EXISTS `findMenuChildren`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `findMenuChildren`(rootId INT) RETURNS varchar(4000) CHARSET utf8mb4
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);
    SET sTemp = '$';
    SET sTempChd = CAST(rootId as CHAR);
    WHILE sTempChd is not null DO
        SET sTemp = CONCAT(sTemp,',',sTempChd);
    SELECT GROUP_CONCAT(menu_id) INTO sTempChd FROM sink_menu
    WHERE FIND_IN_SET(parent_id,sTempChd)>0;
END WHILE;
RETURN sTemp;
END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `PROC_LOG`
-- ----------------------------
DROP PROCEDURE IF EXISTS `PROC_LOG`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PROC_LOG`(
                        IN  in_code varchar(10), 
												IN	in_workdate varchar(30),
												IN	in_progname varchar(100),
												IN	in_prog_run_wid varchar(100), 
												IN	in_datascal varchar(12), 
												IN	in_step_num int(5), 
												IN	in_begin_time varchar(30),
												IN	in_errno varchar(20), 
												IN	in_errmsg varchar(500), 
												IN	in_errlevel varchar(1))
BEGIN
        IF IN_CODE = '0' THEN 						
          /*将成功日志记入成功日志表*/
               INSERT INTO sink.LOG_PROC_EXEC
									(WID,
                   WORKDATE,
                   PROG_RUN_WID,
									 PROGNAME,
									 JOBNAME,
									 STEP_NUM,
                   DATASCAL,
									 BEGIN_TIME,
									 END_TIME,
									 RUNSTATUS)
								VALUES(
									date_format(now(),'%Y-%m-%d %H:%I:%S'),
                   IN_WORKDATE,
                   IN_PROG_RUN_WID,
									 IN_PROGNAME,
									 '',
									 IN_STEP_NUM,
                   IN_DATASCAL,
									 IN_BEGIN_TIME,
									 date_format(now(),'%Y-%m-%d %H:%I:%S'),
									 '0');
        ELSE
						/*将错误日志记入错误日志表*/
 						INSERT INTO LOG_PROC_ERR
 							(WID,
                WORKDATE,
 							 DEAL_DATETIME,
                PROG_RUN_WID,
 							 PROGNAME,
 							 JOBNAME,
 							 STEP_NUM,
 							 ERRNO,
 							 ERRMSG,
 							 ERRLEVEL)
 						VALUES(
  							date_format(now(),'%Y-%m-%d %H:%I:%S'),
                IN_WORKDATE,
									date_format(now(),'%Y-%m-%d %H:%I:%S'),
                IN_PROG_RUN_WID,
  							 IN_PROGNAME,
  							 '',
  							 IN_STEP_NUM,
  							 IN_ERRNO,
  							 IN_ERRMSG,
  							 IN_ERRLEVEL);



        END IF;
-- 		EXCEPTION WHEN OTHERS THEN
--         INSERT INTO SINK.LOG_PROC_ERR
-- 							(wid,WORKDATE,
-- 							 DEAL_DATETIME,
-- 							 PROGNAME,
-- 							 JOBNAME,
-- 							 STEP_NUM,
-- 							 ERRNO,
-- 							 ERRMSG,
-- 							 ERRLEVEL)
-- 						VALUES(
-- 							date_format(now(),'%Y-%m-%d %H:%I:%S'),1,date_format(now(),'%Y-%m-%d %H:%I:%S'),'日志脚本错误',4,5,6,7,8);
			--  DBMS_OUTPUT.PUT_LINE('ALERT!记LOG_PROC_ERR表出错，错误代码:' || SQLERRM);


  END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `sp_1`
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_1`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_1`(IN P_I_DATE VARCHAR(20),out p_o_date varchar(10) 
                                                           )
BEGIN 
											DECLARE LOG_SUCCESS_COUNT VARCHAR(10);
											DECLARE LOG_ERROR_COUNT VARCHAR(10);
											DECLARE LOG_SUM_COUNT VARCHAR(10);
											DECLARE V_P_I_DATE    VARCHAR(20);
											DECLARE V_P_I_TIME    VARCHAR(30);
											DECLARE V_SQLCOUNT    VARCHAR(10);
											DECLARE P_O_CODE     VARCHAR(10);
-- 日志的声明
                      DECLARE in_code varchar(10);
											DECLARE	in_workdate varchar(20);
											DECLARE	in_progname varchar(100);
											DECLARE	in_prog_run_wid varchar(100); 
											DECLARE	in_datascal varchar(12);
											DECLARE	in_step_num int(5);
											DECLARE	IN_DEAL_DATETIME varchar(30);
											DECLARE	in_errno varchar(20);
											DECLARE	in_errmsg varchar(500); 
											DECLARE	in_errlevel varchar(10);
											
SET V_P_I_DATE    = DATE_FORMAT(P_I_DATE,'%Y-%m-%d');
SET P_O_CODE='0';
SET LOG_SUCCESS_COUNT='0';
SET LOG_ERROR_COUNT='0';
SET LOG_SUM_COUNT ='0';

-- 日志的初始化
SET IN_PROGNAME      = 'SP_SINK_JDBC_LOG_SUM';
SET IN_PROG_RUN_WID  = date_format(now(),'%Y-%m-%d %H:%I:%S');
SET V_SQLCOUNT       = 0;





-- 批量日志
SET IN_STEP_NUM      = 3;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/

/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');



END
;;
DELIMITER ;

-- ----------------------------
--  Procedure definition for `SP_SINK_JDBC_LOG_SUM`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SP_SINK_JDBC_LOG_SUM`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SP_SINK_JDBC_LOG_SUM`(IN P_I_DATE VARCHAR(20)
                                                           )
BEGIN 
											DECLARE LOG_SUCCESS_COUNT VARCHAR(10);
											DECLARE LOG_ERROR_COUNT VARCHAR(10);
											DECLARE LOG_SUM_COUNT VARCHAR(10);
											DECLARE V_P_I_DATE    VARCHAR(20);
											DECLARE V_P_I_TIME    VARCHAR(30);
											DECLARE V_SQLCOUNT    VARCHAR(10);
											DECLARE P_O_CODE     VARCHAR(10);
											DECLARE P_I_DATE_D   VARCHAR(20);
-- 日志的声明
                      DECLARE in_code varchar(10);
											DECLARE	in_workdate varchar(20);
											DECLARE	in_progname varchar(100);
											DECLARE	in_prog_run_wid varchar(100); 
											DECLARE	in_datascal varchar(12);
											DECLARE	in_step_num int(5);
											DECLARE	IN_DEAL_DATETIME varchar(30);
											DECLARE	in_errno varchar(20);
											DECLARE	in_errmsg varchar(500); 
											DECLARE	in_errlevel varchar(10);
											
SET V_P_I_DATE    = DATE_FORMAT(P_I_DATE,'%Y-%m-%d');
SET P_O_CODE='0';
SET LOG_SUCCESS_COUNT='0';
SET LOG_ERROR_COUNT='0';
SET LOG_SUM_COUNT ='0';
SET P_I_DATE_D    = DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY) ;

-- 日志的初始化
SET IN_PROGNAME      = 'SP_SINK_JDBC_LOG_SUM';
SET IN_PROG_RUN_WID  = date_format(now(),'%Y-%m-%d %H:%I:%S');
SET V_SQLCOUNT       = 0;


SET IN_STEP_NUM      = 1;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
-- 删除以前日志
TRUNCATE TABLE    SINK.sink_jdbc_log_sum;
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');

-- 实时日志
SET IN_STEP_NUM      = 2;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
insert into sink_jdbc_log_sum(data_dt,dim1,dim2,value1)
select  V_P_I_DATE,'实时交换','成功',count(1) from  sink_jdbc_log_success where sc_opt_date = V_P_I_DATE
union all
select  V_P_I_DATE,'实时交换','未处理',count(1) from  sink_jdbc_log_error where sc_opt_date = V_P_I_DATE and q_status = '1'
union all
select  V_P_I_DATE,'实时交换','已处理',count(1) from  sink_jdbc_log_error where sc_opt_date = V_P_I_DATE and q_status <> '1'
;
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');


-- 批量日志
SET IN_STEP_NUM      = 3;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
insert into sink_jdbc_log_sum(data_dt,dim1,dim2,value1)
select  V_P_I_DATE,'批量交换','失败',count(1) from  sink_jdbc_log_batch where batch_opt_date = V_P_I_DATE and q_status = '1'
union all
select  V_P_I_DATE,'批量交换','成功',count(1) from  sink_jdbc_log_batch where batch_opt_date = V_P_I_DATE and q_status = '0';
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');

-- OGG进程
SET IN_STEP_NUM      = 4;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
insert into sink_jdbc_log_sum(data_dt,dim1,dim2,value1)
select  V_P_I_DATE,'OGG进程','运行中',count(1) from  sink_jdbc_ogg_stats
where substr(update_time,1,10)  = V_P_I_DATE and PROGRAM_status = 'RUNNING' 
  and substr(update_time,1,13) = 
	    (select substr(max(update_time),1,13) from sink_jdbc_ogg_stats where substr(update_time,1,10)  = V_P_I_DATE and PROGRAM_status = 'RUNNING' )
union all
select  V_P_I_DATE,'OGG进程','已停止',count(1) from  sink_jdbc_ogg_stats
where substr(update_time,1,10)  = V_P_I_DATE and PROGRAM_status = 'STOPPED' 
  and substr(update_time,1,13) = 
	    (select substr(max(update_time),1,13) from sink_jdbc_ogg_stats where substr(update_time,1,10)  = V_P_I_DATE and PROGRAM_status = 'RUNNING' )
union all
select  V_P_I_DATE,'OGG进程','异常',count(1) from  sink_jdbc_ogg_stats
where substr(update_time,1,10)  =V_P_I_DATE and PROGRAM_status not in ( 'STOPPED' ,'RUNNING')
  and substr(update_time,1,13) = 
	    (select substr(max(update_time),1,13) from sink_jdbc_ogg_stats where substr(update_time,1,10)  = V_P_I_DATE and PROGRAM_status = 'RUNNING' );
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');			

			
-- 任务状态	实时运行中
SET IN_STEP_NUM      = 5;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/

insert into sink_jdbc_log_sum(data_dt,dim1,dim2,value1)
select  V_P_I_DATE,'任务状态','实时运行中',count(1) from  sink_jdbc_cof_task where task_status = '1' 
union all
select  V_P_I_DATE,'任务状态','实时已停止',count(1) from  sink_jdbc_cof_task where task_status = '2' 
union all
select  V_P_I_DATE,'任务状态','批量运行中',count(1) from  sink_jdbc_cof_task_batch where task_status = '1' 
union all
select  V_P_I_DATE,'任务状态','批量已停止',count(1) from  sink_jdbc_cof_task_batch where task_status = '2' ;
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');		
			

-- 交换分布	
SET IN_STEP_NUM      = 6;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
insert into sink_jdbc_log_sum(data_dt,dim1,dim2,dim3,value1)
select V_P_I_DATE,'交换分布'
        ,concat(t1.sc_sys_no,t3.sys_name ) as sc_sys_no_name
        ,concat(t1.tg_sys_no,t4.sys_name ) as tg_sys_no_name
				,ifnull(t2.tgrn,0) 
from (select distinct  SC_SYS_NO,TG_SYS_NO from  sink_jdbc_cof_task ) T1
LEFT JOIN (select  SC_SYS_NO,TG_SYS_NO,count(1) as tgrn  from sink_jdbc_log_error 
            where  sc_opt_date = V_P_I_DATE and q_status <> '1'  group by TG_SYS_NO
				 ) T2  ON T1.SC_SYS_NO = T2.SC_SYS_NO 
left join sink_jdbc_sys_no_name t3
 on t1.sc_sys_no = t3.sys_no
left join sink_jdbc_sys_no_name t4
 on t1.tg_sys_no = t4.sys_no
where t3.sys_name is not null  and t4.sys_name is not null ;
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');		
				 

-- 交换走势	
SET IN_STEP_NUM      = 7;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/
insert into sink_jdbc_log_sum(data_dt,dim1,dim2,dim3,value1)
select  V_P_I_DATE,'交换走势','成功数',t1.opt_date,ifnull(t2.cnt,0)
from (
   select V_P_I_DATE as opt_date union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 1 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 2 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 3 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 4 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 5 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 6 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 7 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 8 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 9 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 10 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 11 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 12 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 13 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 14 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)
			
    ) t1
left join (select sc_opt_date,count(1) as cnt from  sink_jdbc_log_success 
      where sc_opt_date between DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)   and V_P_I_DATE
			 group by sc_opt_date
			)t2 on t1.opt_date = t2.sc_opt_date
union all
select  V_P_I_DATE,'交换走势','未处理',t1.opt_date,ifnull(t2.cnt,0)
from (
      select V_P_I_DATE as opt_date union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 1 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 2 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 3 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 4 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 5 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 6 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 7 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 8 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 9 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 10 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 11 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 12 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 13 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 14 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)  
    ) t1
left join (select sc_opt_date,count(1) as  cnt  from  sink_jdbc_log_error
 where sc_opt_date between  DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)   and V_P_I_DATE and q_status = '1'
  group by sc_opt_date
			)t2 on t1.opt_date = t2.sc_opt_date

union all
select  V_P_I_DATE,'交换走势','已处理',t1.opt_date,ifnull(t2.cnt,0)
from (
   select V_P_I_DATE as opt_date union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 1 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 2 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 3 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 4 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 5 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 6 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 7 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 8 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 9 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 10 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 11 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 12 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 13 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 14 DAY) union all
			select DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)  
    ) t1
left join (select sc_opt_date,count(1) as  cnt from  sink_jdbc_log_error
 where sc_opt_date between DATE_SUB(V_P_I_DATE,INTERVAL 15 DAY)  and V_P_I_DATE and q_status <>'1'
  group by sc_opt_date
			)t2  on t1.opt_date = t2.sc_opt_date;
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');	
			
	
WHILE V_P_I_DATE >= P_I_DATE_D   DO
	
--  成功数（成功+已处理），失败数（未处理）	
SET IN_STEP_NUM      = 8;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/	
select  count(1)  INTO LOG_SUCCESS_COUNT  
from  sink_jdbc_log_success where sc_opt_date = P_I_DATE_D;
								 		/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');		
		
		SET IN_STEP_NUM      = 9;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/	

select count(1)  INTO LOG_ERROR_COUNT
from  sink_jdbc_log_error 
where sc_opt_date = P_I_DATE_D and q_status = '1';
								 		/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');		
		/*SQLSTART*/	
		SET IN_STEP_NUM      = 10;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;

/*SQLSTART*/	
select  count(1)  INTO LOG_SUM_COUNT
from  sink_jdbc_log_batch 
where batch_opt_date = P_I_DATE_D and q_status = '1';
/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');	



-- 如果批量日志报错，则减10
-- 成功分=实时交换成功/（实时交换成功+实时交换失败） 的商
-- 批量分数（如果批量均成功，则为0，否则为10）

SET IN_STEP_NUM      =11;
SET IN_DEAL_DATETIME = date_format(now(),'%Y-%m-%d %H:%I:%S') ;
/*SQLSTART*/			
	 insert into SINK.sink_jdbc_log_sum(data_dt,dim1,dim2,dim3,value1)
	 select    V_P_I_DATE        ,
	          '健康度'           ,
						'成功率'           ,
						P_I_DATE_D         ,
						ROUND(((LOG_SUCCESS_COUNT/(LOG_SUCCESS_COUNT+LOG_ERROR_COUNT))*100-(CASE WHEN LOG_SUM_COUNT<>'0' THEN '10' ELSE '0' END)),2)
		         FROM DUAL;
						 		/*SQLEND*/
select row_count() into V_SQLCOUNT;
CALL	SINK.proc_log('0',V_P_I_DATE, IN_PROGNAME,IN_PROG_RUN_WID, V_SQLCOUNT, IN_STEP_NUM, IN_DEAL_DATETIME,'','','');		

 set  P_I_DATE_D= DATE_ADD(P_I_DATE_D,INTERVAL 1 DAY) ; 
  end while;  
	


END
;;
DELIMITER ;

-- ----------------------------
--  Event definition for `e_sp_sink_jdbc_log_sum`
-- ----------------------------
DROP EVENT IF EXISTS `e_sp_sink_jdbc_log_sum`;
CREATE DEFINER=`root`@`%` EVENT `e_sp_sink_jdbc_log_sum` ON SCHEDULE EVERY 300 SECOND STARTS '2019-09-25 12:35:36' ON COMPLETION NOT PRESERVE ENABLE DO begin
			declare  e_p_i_date varchar(20);
	    set      e_p_i_date  = date_format(now(),'%Y-%m-%d');
		CALL sink.SP_SINK_JDBC_LOG_SUM(e_p_i_date);
		 END;

-- ----------------------------
--  Records 
-- ----------------------------
