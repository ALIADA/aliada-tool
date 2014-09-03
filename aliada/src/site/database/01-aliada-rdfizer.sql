CREATE TABLE `rdfizer_job_instances` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT,
  `datafile` varchar(245) NOT NULL,
  `format` varchar(32) NOT NULL,
  `namespace` varchar(245) NOT NULL,
  `graph_name` varchar(245) DEFAULT NULL,
  `aliada_ontology` varchar(245) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `sparql_endpoint_uri` varchar(245) NOT NULL,
  `sparql_endpoint_login` varchar(32) NOT NULL,
  `sparql_endpoint_password` varchar(32) NOT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `aliada`.`rdfizer_job_stats` (
`job_id` INT NOT NULL ,
`total_records_count`  INT NOT NULL,
`total_triples_produced`  INT NOT NULL,
`records_throughput`  DECIMAL NOT NULL,
`triples_throughput`  DECIMAL NOT NULL,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;
