CREATE TABLE `aliada`.`linksdiscovery_job_instances` (
`job_id` INT NOT NULL ,
`input_uri`  VARCHAR( 245 ) default NULL,
`input_login`  VARCHAR( 32 ) default NULL,
`input_password`  VARCHAR( 32 ) default NULL,
`input_graph`  VARCHAR( 245 ) default NULL,
`output_uri`  VARCHAR( 245 ) default NULL,
`output_login`  VARCHAR( 32 ) default NULL,
`output_password`  VARCHAR( 32 ) default NULL,
`output_graph`  VARCHAR( 245 ) default NULL,
`config_file`  VARCHAR( 245 ) default NULL,
`tmp_dir`  VARCHAR( 245 ) default NULL,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
PRIMARY KEY ( `job_id` )
) ENGINE = InnoDB ;

CREATE TABLE `aliada`.`linksdiscovery_subjob_instances` (
`job_id` INT NOT NULL ,
`subjob_id` INT NOT NULL ,
`start_date` DATETIME default NULL,
`end_date` DATETIME default NULL ,
PRIMARY KEY ( `job_id` , `subjob_id`)
) ENGINE = InnoDB ;

ALTER TABLE  `aliada`.`linksdiscovery_subjob_instances`
  ADD CONSTRAINT `linksdiscovery_subjob_instances_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `aliada`.`linksdiscovery_job_instances` (`job_id`) ON DELETE CASCADE ON UPDATE CASCADE;
