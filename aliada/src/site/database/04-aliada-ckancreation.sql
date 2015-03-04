CREATE TABLE `aliada`.`ckancreation_job_instances` (
`job_id` INT NOT NULL AUTO_INCREMENT,
`ckan_api_url`  VARCHAR( 245 ) default NULL,
`ckan_api_key`  VARCHAR( 245 ) default NULL,
`tmp_dir`  VARCHAR( 245 ) default NULL,
`store_ip`  VARCHAR( 245 ) default NULL,
`store_sql_port`  INT default 1111,
`sql_login`  VARCHAR( 32 ) default NULL,
`sql_password`  VARCHAR( 32 ) default NULL,
`isql_command_path`  VARCHAR( 245 ) default NULL,
`virtuoso_http_server_root`  VARCHAR( 245 ) default NULL,
`aliada_ontology`  VARCHAR( 245 ) default NULL,
`org_name`  VARCHAR( 245 ) default NULL,
`org_description`  VARCHAR( 245 ) default NULL,
`org_home_page`  VARCHAR( 245 ) default NULL,
`datasetId` int(11) NOT NULL,
`organisationId` int(11) NOT NULL,
`ckan_org_url`  VARCHAR( 245 ) default NULL,
`ckan_dataset_url`  VARCHAR( 245 ) default NULL,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
FOREIGN KEY (datasetId) 
REFERENCES dataset(datasetId) 
ON DELETE CASCADE,
FOREIGN KEY (organisationId) 
REFERENCES organisation(organisationId) 
ON DELETE CASCADE,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;
