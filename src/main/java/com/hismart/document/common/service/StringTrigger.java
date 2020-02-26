package com.hismart.document.common.service;

public interface StringTrigger {

    //TODO MYSQL DELETE 触发器
      String  MYSQL_DELETE_TITLE="DROP TRIGGER IF EXISTS ${table}_D ;\n" +
            "CREATE TRIGGER ESB_${table} AFTER DELETE\n" +
            "ON ${table} FOR EACH ROW \n" +
              "BEGIN \n" +
              "    DECLARE  T_DATE        VARCHAR(100);\n" +
              "    DECLARE  T_TIMESTAMP   VARCHAR(100);\n" +
              "    DECLARE  T_OP_TS       VARCHAR(100);\n" +
              "    DECLARE  T_CURRENT_TS  VARCHAR(100);\n" +
              "    DECLARE  T_NULL        VARCHAR(50);\n" +
              "    declare  T_TRIGGER     VARCHAR(100);\n" +
              "    set      T_DATE        = DATE_FORMAT(CURRENT_DATE(),'%Y-%m-%d');\n" +
              "    set      T_TIMESTAMP   = REPLACE(replace(replace(cast(CURRENT_TIMESTAMP(6) as char),'-',''),' ',''),':','');\n" +
              "    set      T_OP_TS       = cast(CURRENT_TIMESTAMP(6) as char);\n" +
              "    set      T_CURRENT_TS  = REPLACE(T_OP_TS,' ','T');\n" +
              "    set      T_NULL        ='null';\n" +
              "    set      T_TRIGGER     ='trigger';\n";


      String FIELD="CASE WHEN OLD.${field} IS NULL THEN ";

      String INSERT_ESB_EVENT_TABLE="INSERT INTO esb_event_table\n" +
              " (wid,\n" +
              "sc_sys_no,\n" +
              "sc_sys_schema,\n" +
              "sc_table_name,\n" +
              "sc_table_key_colname,\n" +
              "sc_table_key_value,\n" +
              "sc_opt_date,\n" +
              "sc_opt_time,\n" +
              "sc_opt_mark,\n" +
              "sc_ogg_text)\n" +
              "values";

         String   concat  ="  concat('{table:\"','${systemPrefix}','.','${tableName}','\",',\n"+
            "        'op_type:\"D\",',\n"+
            "        'op_ts:\"',T_OP_TS,'\",',\n"+
            "        'current_ts:\"',T_CURRENT_TS,'\",',\n"+
            "        concat('pos:\"',T_TRIGGER,'\",'),";



         //TODO MYSQL INSERT 触发器
      String MYSQL_INSERT_TITLE="DROP TRIGGER IF EXISTS ESB_RS_T_JZG_JBXX_OGG_I ;\n" +
              "CREATE TRIGGER ESB_RS_T_JZG_JBXX_OGG_I AFTER INSERT\n" +
              "ON RS_T_JZG_JBXX_OGG FOR EACH ROW\n" +
              "BEGIN\n" +
              "    DECLARE  T_DATE        VARCHAR(100);\n" +
              "    DECLARE  T_TIMESTAMP   VARCHAR(100);\n" +
              "    DECLARE  T_OP_TS       VARCHAR(100);\n" +
              "    DECLARE  T_CURRENT_TS  VARCHAR(100);\n" +
              "    DECLARE  T_NULL        VARCHAR(50);\n" +
              "    declare  T_TRIGGER     VARCHAR(100);\n" +
              "    set      T_DATE        = DATE_FORMAT(CURRENT_DATE(),'%Y-%m-%d');\n" +
              "    set      T_TIMESTAMP   = REPLACE(replace(replace(cast(CURRENT_TIMESTAMP(6) as char),'-',''),' ',''),':','');\n" +
              "    set      T_OP_TS       = cast(CURRENT_TIMESTAMP(6) as char);\n" +
              "    set      T_CURRENT_TS  = REPLACE(T_OP_TS,' ','T');\n" +
              "    set      T_NULL        ='null';\n" +
              "    set      T_TRIGGER     ='trigger';\n";

}
