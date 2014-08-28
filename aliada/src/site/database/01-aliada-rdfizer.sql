CREATE TABLE `aliada`.`rdfizer_job_instances` (
`job_id` INT NOT NULL AUTO_INCREMENT,
`datafile`  VARCHAR( 245 ) default NULL,
`format`  VARCHAR( 32 ) default NULL,
`namespace`  VARCHAR( 245 ) default NULL,
`aliada_ontology`  VARCHAR( 245 ) default NULL,
`start_date` TIMESTAMP default NULL,
`end_date` TIMESTAMP default NULL ,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;

CREATE TABLE `aliada`.`rdfizer_job_stats` (
`job_id` INT NOT NULL ,
`total_records_count`  INT NOT NULL,
`total_triples_produced`  INT NOT NULL,
`records_throughput`  DECIMAL NOT NULL,
`triples_throughput`  DECIMAL NOT NULL,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;
