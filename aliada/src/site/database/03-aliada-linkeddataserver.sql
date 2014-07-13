CREATE TABLE `aliada`.`linkeddataserver_job_instances` (
`job_id` INT NOT NULL ,
`store_ip`  VARCHAR( 245 ) default NULL,
`store_sql_port`  INT default 1111,
`sql_login`  VARCHAR( 32 ) default NULL,
`sql_password`  VARCHAR( 32 ) default NULL,
`graph`  VARCHAR( 245 ) default NULL,
`dataset_base`  VARCHAR( 245 ) default NULL,
`isql_command_path`  VARCHAR( 245 ) default NULL,
`isql_commands_file`  VARCHAR( 245 ) default NULL,
`isql_commands_file_default`  VARCHAR( 245 ) default NULL,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;
