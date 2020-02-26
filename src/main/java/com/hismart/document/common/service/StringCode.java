package com.hismart.document.common.service;


public interface StringCode {

    String PROJECT_NAME_TASK = "data_synchronization_task";


    String PROJECT_NAME_JDBC = "data_synchronization_jdbc";

    String PROJECT_NAME_MAP = "data_synchronization_map";

    String INITIALIZATION_TASK = "initialization_task";
    /**
     * 批量交换
     */
    String INITIALIZATION_BATCH_EXCHANGE_TASK = "initialization_batch_exchange_task";
    /**
     * 批量抽取
     */
    String INITIALIZATION_BATCH_EXTRACT_TASK = "initialization_batch_extract_task";

    String PROJECT_WORK = "data_work";

    /**
     * update标记字段名称
     */
    String UPDATE_MARK_NAME = "update_mark";
    /**
     * updatetime标记字段名称
     */
    String UPDATE_TIME_NAME = "update_time";

    String INSERT_MARK = "I";

    String UPDATE_MARK = "U";

    String DELETE_MARK = "D";

    String TRANCAT_MARK = "T";

    String IS_PRIMARY_KEY = "Y";

    String SUCCESS = "0";

    String UNPROCESSED = "1";
    String HAS_BEEN_RERUN = "2";
    String IGNORE = "3";
    String TIME_OUT = "4";
    String THROW_AWAY = "5";
    String SIX = "6";
    String COUNT = "count";
    String SPACE = " ";

    Integer ZERO = 0;

    String REASON_FAILURE = "重跑失败次为：";

    String REASON = "目标不存在";

    Integer TWO = 2;

    Integer ONE = 1;

    Integer THREE = 3;


    Integer FOUR = 4;


    String SUCCESS_LOG = "sink_jdbc_log_success";

    String ERROR_LOG = "sink_jdbc_log_error";

    String RERUN_LOG = "sink_jdbc_log_backup";


    String TASK_TABLE = "sink_jdbc_cof_task";

    String STATUS = "status";


    String OK = "ok";

    String  MYSQL="MYSQL";


    String  ORACLE="ORACLE";

   String   POSGRESQL="POSGRESQL";

    String SQLSERVER2 = "SQLSERVER";

    String SQLSERVER = "sqlserver";

    String STARTTIME = "startTime";

    String OPTDATE = "optDate";

    String ENDTIME = "endTime";



    String REASONS = "reason";

    String SUCCESSINFO  ="successInfo";


    String SELECT_COUNT_FROM = "  SELECT count(*) FROM  ";


    String WHERE = "  where  ";

}
