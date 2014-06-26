CREATE TABLE `aliada`.`rdfizer_job_instances` (
`job_id` INT NOT NULL ,
`datafile`  VARCHAR( 245 ) default NULL,
`format`  VARCHAR( 32 ) default NULL,
`namespace`  VARCHAR( 245 ) default NULL,
`aliada_ontology`  VARCHAR( 245 ) default NULL,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;