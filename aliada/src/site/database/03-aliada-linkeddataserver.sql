CREATE TABLE `aliada`.`linkeddataserver_job_instances` (
`job_id` INT NOT NULL AUTO_INCREMENT,
`store_ip`  VARCHAR( 245 ) default NULL,
`store_sql_port`  INT default 1111,
`sql_login`  VARCHAR( 32 ) default NULL,
`sql_password`  VARCHAR( 32 ) default NULL,
`isql_command_path`  VARCHAR( 245 ) default NULL,
`isql_commands_file_dataset_default` varchar(245) DEFAULT NULL,
`isql_commands_file_subset_default` varchar(245) DEFAULT NULL,
`virtuoso_http_server_root`  VARCHAR( 245 ) default NULL,
`aliada_ontology`  VARCHAR( 245 ) default NULL,
`datasetId` int(11) NOT NULL,
`organisationId` int(11) NOT NULL,
`tmp_dir`  VARCHAR( 245 ) default NULL,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
FOREIGN KEY (datasetId) 
REFERENCES dataset(datasetId) 
ON DELETE CASCADE,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;
