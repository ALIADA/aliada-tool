CREATE DATABASE IF NOT EXISTS `aliada` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'aliada'@'%' IDENTIFIED BY 'aliada';

GRANT ALL PRIVILEGES ON * . * TO 'aliada'@'%' IDENTIFIED BY 'aliada' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

-- phpMyAdmin SQL Dump
-- version 4.3.9
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 24-02-2015 a las 14:28:58
-- Versión del servidor: 5.5.41-MariaDB
-- Versión de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `aliada`
--

USE `aliada`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dataset`
--

CREATE TABLE IF NOT EXISTS `dataset` (
  `datasetId` int(11) NOT NULL,
  `organisationId` int(11) NOT NULL,
  `dataset_desc` varchar(245) DEFAULT NULL,
  `domain_name` varchar(245) DEFAULT NULL,
  `uri_id_part` varchar(10) NOT NULL,
  `uri_doc_part` varchar(10) DEFAULT NULL,
  `uri_def_part` varchar(10) NOT NULL,
  `uri_concept_part` varchar(245) DEFAULT NULL,
  `uri_set_part` VARCHAR( 245 ) NOT NULL,
  `listening_host` varchar(45) DEFAULT NULL,
  `virtual_host` varchar(45) DEFAULT NULL,
  `sparql_endpoint_uri` varchar(245) DEFAULT NULL,
  `sparql_endpoint_login` varchar(32) DEFAULT NULL,
  `sparql_endpoint_password` varchar(32) DEFAULT NULL,
  `public_sparql_endpoint_uri` varchar(245) DEFAULT NULL,
  `dataset_author` varchar(245) DEFAULT NULL,
  `dataset_author_email` varchar(245) DEFAULT NULL,
  `ckan_dataset_name` varchar(245) DEFAULT NULL,
  `dataset_long_desc` varchar(500) DEFAULT NULL,
  `dataset_source_url` varchar(245) DEFAULT NULL,
  `license_ckan_id` varchar(245) DEFAULT NULL,
  `license_url` varchar(245) DEFAULT NULL,
  `isql_commands_file_dataset` varchar(245) DEFAULT NULL,
  `dataset_web_page_root` varchar(245) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organisation`
--

CREATE TABLE IF NOT EXISTS `organisation` (
  `organisationId` int(11) NOT NULL,
  `org_name` varchar(32) NOT NULL,
  `org_path` varchar(128) NOT NULL,
  `org_logo` blob,
  `org_catalog_url` varchar(128) NOT NULL,
  `org_description` varchar(245) DEFAULT NULL,
  `org_home_page` varchar(245) DEFAULT NULL,
  `aliada_ontology` varchar(245) DEFAULT NULL,
  `tmp_dir` varchar(245) DEFAULT NULL,
  `linking_client_app_bin_dir` varchar(245) DEFAULT NULL,
  `linking_client_app_user` varchar(245) DEFAULT NULL,
  `store_ip` varchar(245) DEFAULT NULL,
  `store_sql_port` int(11) DEFAULT '1111',
  `sql_login` varchar(32) DEFAULT NULL,
  `sql_password` varchar(32) DEFAULT NULL,
  `isql_command_path` varchar(245) DEFAULT NULL,
  `isql_commands_file_dataset_default` varchar(245) DEFAULT NULL,
  `isql_commands_file_subset_default` varchar(245) DEFAULT NULL,
  `isql_commands_file_graph_dump` varchar(245) DEFAULT NULL,
  `virtuoso_http_server_root`  VARCHAR( 245 ) default NULL,
  `ckan_api_url` varchar(245) DEFAULT NULL,
  `ckan_api_key` varchar(245) DEFAULT NULL,
  `ckan_org_url` varchar(245) DEFAULT NULL,
  `dataset_author` varchar(245) NOT NULL,
  `isql_commands_file_dataset_creation` varchar(245) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `profile_id` int(11) NOT NULL,
  `profile_name` varchar(32) NOT NULL,
  `profile_type_code` int(11) NOT NULL,
  `profile_description` varchar(128) NOT NULL,
  `metadata_scheme_code` int(11) NOT NULL,
  `file_type_code` int(11) NOT NULL,
  `file_format_code` int(11) NOT NULL,
  `character_set_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subset`
--

CREATE TABLE IF NOT EXISTS `subset` (
  `datasetId` int(11) NOT NULL,
  `subsetId` int(11) NOT NULL,
  `subset_desc` varchar(245) DEFAULT NULL,
  `uri_concept_part` varchar(245) DEFAULT NULL,
  `graph_uri` varchar(245) DEFAULT NULL,
  `links_graph_uri` varchar(245) DEFAULT NULL,
  `isql_commands_file_subset` varchar(245) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template`
--

CREATE TABLE IF NOT EXISTS `template` (
  `template_id` int(11) NOT NULL,
  `template_name` varchar(32) NOT NULL,
  `template_description` varchar(128) DEFAULT NULL,
  `file_type_code` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template_xml_tag`
--

CREATE TABLE IF NOT EXISTS `template_xml_tag` (
  `template_id` int(11) NOT NULL,
  `xml_tag_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_character_set`
--

CREATE TABLE IF NOT EXISTS `t_character_set` (
  `character_set_code` int(11) NOT NULL,
  `character_set_name` varchar(32) NOT NULL,
  `character_set_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_external_dataset`
--

CREATE TABLE IF NOT EXISTS `t_external_dataset` (
  `external_dataset_code` int(11) NOT NULL,
  `external_dataset_name` varchar(32) NOT NULL,
  `external_dataset_name_ckan` varchar(100) NOT NULL,
  `external_dataset_description` varchar(128) DEFAULT NULL,
  `external_dataset_homepage` varchar(128) DEFAULT NULL,
  `external_dataset_linkingfile` varchar(245) DEFAULT NULL,
  `external_dataset_linkingnumthreads` tinyint(1) DEFAULT '8',
  `external_dataset_linkingreloadtarget` tinyint(1) default 0,
  `external_dataset_linkingreloadsource` tinyint(1) default 1,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_format`
--

CREATE TABLE IF NOT EXISTS `t_file_format` (
  `file_format_code` int(11) NOT NULL,
  `file_format_name` varchar(32) NOT NULL,
  `file_format_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_type`
--

CREATE TABLE IF NOT EXISTS `t_file_type` (
  `file_type_code` int(11) NOT NULL,
  `file_type_name` varchar(32) NOT NULL,
  `file_type_description` varchar(128) DEFAULT NULL,
  `file_type_conversion_file` varchar(32) NOT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_metadata_scheme`
--

CREATE TABLE IF NOT EXISTS `t_metadata_scheme` (
  `metadata_code` int(11) NOT NULL,
  `metadata_name` varchar(32) NOT NULL,
  `metadata_description` varchar(128) DEFAULT NULL,
  `metadata_conversion_file` varchar(32) NOT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_profile_type`
--

CREATE TABLE IF NOT EXISTS `t_profile_type` (
  `profile_code` int(11) NOT NULL,
  `profile_name` varchar(32) NOT NULL,
  `profile_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_role`
--

CREATE TABLE IF NOT EXISTS `t_user_role` (
  `user_role_code` int(11) NOT NULL,
  `user_role` varchar(32) NOT NULL,
  `user_role_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_type`
--

CREATE TABLE IF NOT EXISTS `t_user_type` (
  `user_type_code` int(11) NOT NULL,
  `user_type` varchar(32) NOT NULL,
  `user_type_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_xml_tag_type`
--

CREATE TABLE IF NOT EXISTS `t_xml_tag_type` (
  `xml_tag_type_code` int(11) NOT NULL,
  `xml_tag_type_name` varchar(32) NOT NULL,
  `xml_tag_type_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_language`
--

CREATE TABLE IF NOT EXISTS `t_language` (
  `language_code` varchar(3) NOT NULL,
  `lang_code` varchar(2) NOT NULL,
  `lang_country` varchar(2),
  `language_name` varchar(32) NOT NULL,
  `language_description` varchar(128) DEFAULT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_template_entity`
--

CREATE TABLE `t_template_entity` (
  `template_entity_code` int(11) NOT NULL,
  `template_entity_name` varchar(32) NOT NULL,
  `template_entity_description` varchar(128) DEFAULT NULL,
  `xml_tag_type_code` int(11) NOT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- --------------------------------------------------------


--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(150) NOT NULL,
  `user_email` varchar(128) NOT NULL,
  `user_type_code` int(11) NOT NULL,
  `user_role_code` int(11) NOT NULL,
  `organisationId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_session`
--

CREATE TABLE IF NOT EXISTS `user_session` (
  `user_name` varchar(20) NOT NULL,
  `file_name` varchar(245) NOT NULL,
  `datafile` varchar(245) DEFAULT NULL,
  `profile` int(11) DEFAULT NULL,
  `template` int(11) DEFAULT NULL,
  `graph` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `job_id` int(11) DEFAULT NULL,
  `links_disc_job_id` int(11) DEFAULT NULL,
  `linked_data_server_job_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `xml_tag`
--
CREATE TABLE `xml_tag` (
  `xml_tag_id` varchar(128) NOT NULL,
  `xml_tag_mandatory` tinyint(1) NOT NULL,
  `xml_tag_description` text NOT NULL,
  `template_entity_code` int(11) NOT NULL,
  `xml_tag_type_code` int(11) NOT NULL,
  `language` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dataset`
--
ALTER TABLE `dataset`
  ADD PRIMARY KEY (`datasetId`), ADD KEY `organisationId` (`organisationId`);

--
-- Indices de la tabla `organisation`
--
ALTER TABLE `organisation`
  ADD PRIMARY KEY (`organisationId`);

--
-- Indices de la tabla `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profile_id`), ADD KEY `profile_type_code` (`profile_type_code`), ADD KEY `metadata_scheme_code` (`metadata_scheme_code`), ADD KEY `file_type_code` (`file_type_code`), ADD KEY `file_format_code` (`file_format_code`), ADD KEY `character_set_code` (`character_set_code`);

--
-- Indices de la tabla `subset`
--
ALTER TABLE `subset`
  ADD PRIMARY KEY (`datasetId`,`subsetId`), ADD KEY `subsetId` (`subsetId`);
  
ALTER TABLE `subset`
	ADD CONSTRAINT fk_name
	    FOREIGN KEY (`datasetId`)
	    REFERENCES `dataset` (`datasetId`)
	    ON DELETE CASCADE;

--
-- Indices de la tabla `template`
--
ALTER TABLE `template`
  ADD PRIMARY KEY (`template_id`), ADD KEY `fk_file_type_code_idx` (`file_type_code`);

--
-- Indices de la tabla `template_xml_tag`
--
ALTER TABLE `template_xml_tag`
  ADD PRIMARY KEY (`template_id`,`xml_tag_id`), ADD KEY `xml_tag_id` (`xml_tag_id`);

--
-- Indices de la tabla `t_character_set`
--
ALTER TABLE `t_character_set`
  ADD PRIMARY KEY (`character_set_code`, `language`);
ALTER TABLE `t_character_set` 
ADD INDEX `language_character_set_idx` (`language` ASC);

--
-- Indices de la tabla `t_external_dataset`
--
ALTER TABLE `t_external_dataset`
  ADD PRIMARY KEY (`external_dataset_code`, `language`);
ALTER TABLE `t_external_dataset` 
ADD INDEX `language_external_dataset_idx` (`language` ASC);

--
-- Indices de la tabla `t_file_format`
--
ALTER TABLE `t_file_format`
  ADD PRIMARY KEY (`file_format_code`, `language`);
ALTER TABLE `t_file_format` 
ADD INDEX `language_file_format_idx` (`language` ASC);

--
-- Indices de la tabla `t_file_type`
--
ALTER TABLE `t_file_type`
  ADD PRIMARY KEY (`file_type_code`, `language`);
ALTER TABLE `t_file_type` 
ADD INDEX `language_file_type_idx` (`language` ASC);

--
-- Indices de la tabla `t_metadata_scheme`
--
ALTER TABLE `t_metadata_scheme`
  ADD PRIMARY KEY (`metadata_code`, `language`);
ALTER TABLE `t_metadata_scheme` 
ADD INDEX `language_metadata_scheme_idx` (`language` ASC);

--
-- Indices de la tabla `t_profile_type`
--
ALTER TABLE `t_profile_type`
  ADD PRIMARY KEY (`profile_code`, `language`);
ALTER TABLE `t_profile_type` 
ADD INDEX `language_profile_type_idx` (`language` ASC);

--
-- Indices de la tabla `t_user_role`
--
ALTER TABLE `t_user_role`
  ADD PRIMARY KEY (`user_role_code`, `language`);
ALTER TABLE `t_user_role` 
ADD INDEX `language_user_role_idx` (`language` ASC);

--
-- Indices de la tabla `t_user_type`
--
ALTER TABLE `t_user_type`
  ADD PRIMARY KEY (`user_type_code`, `language`);
ALTER TABLE `t_user_type` 
ADD INDEX `language_user_type_idx` (`language` ASC);

--
-- Indices de la tabla `t_xml_tag_type`
--
ALTER TABLE `t_xml_tag_type`
  ADD PRIMARY KEY (`xml_tag_type_code`, `language`);
ALTER TABLE `t_xml_tag_type` 
ADD INDEX `language_xml_tag_type_idx` (`language` ASC);

--
-- Indices de la tabla `t_language`
--
ALTER TABLE `t_language` 
ADD PRIMARY KEY (`language_code`, `lang_code`, `language`);
ALTER TABLE `t_language` 
ADD INDEX `language_language_idx` (`language` ASC);
 

--
-- Indices de la tabla `t_template_entity`
--

ALTER TABLE `t_template_entity` 
ADD PRIMARY KEY (`template_entity_code`, `language`, `xml_tag_type_code`);
ALTER TABLE `t_template_entity` 
ADD INDEX `language_template_entity_idx` (`language` ASC);

  
--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_name`), ADD KEY `user_type_code` (`user_type_code`), ADD KEY `user_role_code` (`user_role_code`), ADD KEY `organisationId` (`organisationId`);

--
-- Indices de la tabla `user_session`
--
ALTER TABLE `user_session`
  ADD PRIMARY KEY (`user_name`,`file_name`);

--
-- Indices de la tabla `xml_tag`
--
ALTER TABLE `xml_tag`
  ADD PRIMARY KEY (`xml_tag_id`,`xml_tag_type_code`,`language`), ADD KEY `xml_tag_type_code` (`xml_tag_type_code`);
  
--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `dataset`
--
ALTER TABLE `dataset`
  MODIFY `datasetId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `organisation`
--
ALTER TABLE `organisation`
  MODIFY `organisationId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `profile`
--
ALTER TABLE `profile`
  MODIFY `profile_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `subset`
--
ALTER TABLE `subset`
  MODIFY `subsetId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `template`
--
ALTER TABLE `template`
  MODIFY `template_id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `t_character_set` 
ADD CONSTRAINT `language_character_set_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_external_dataset` 
ADD CONSTRAINT `language_external_dataset_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_file_type` 
ADD CONSTRAINT `language_file_type_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_metadata_scheme` 
ADD CONSTRAINT `language_metadata_scheme_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_profile_type` 
ADD CONSTRAINT `language_profile_type_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_user_role` 
ADD CONSTRAINT `language_user_role_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_user_type` 
ADD CONSTRAINT `language_user_type_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_xml_tag_type` 
ADD CONSTRAINT `language_xml_tag_type_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `t_language` 
ADD CONSTRAINT `language_language_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  ALTER TABLE `t_template_entity` 
ADD CONSTRAINT `language_template_entity_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
ALTER TABLE `xml_tag` 
ADD CONSTRAINT `language_xml_tag_frk`
  FOREIGN KEY (`language`)
  REFERENCES `t_language` (`language_code`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
--
-- Volcado de datos para la tabla `organisation`
--

INSERT INTO `organisation` (`organisationId`,`org_name`,`org_path`,`org_catalog_url`, `org_description`,`org_home_page`, `aliada_ontology`,`tmp_dir`,`linking_client_app_bin_dir`,
`linking_client_app_user`,`store_ip`,`store_sql_port`,`sql_login`,`sql_password`,`isql_command_path`,`isql_commands_file_dataset_default`,  `isql_commands_file_subset_default`, 
`isql_commands_file_graph_dump`, `virtuoso_http_server_root`, `ckan_api_url`,`ckan_api_key`,`dataset_author`,`isql_commands_file_dataset_creation`) VALUES 
(1,'artium','/usr/share/tomcat/upload/','http://aliada.artium.org', 'Basque Museum-Center of Contemporary Art', 'http://www.artium.org/', 'http://aliada-project.eu/2014/aliada-ontology#', '/home/aliada/tmp', '/home/aliada/links-discovery/bin/','linking','localhost',1111,'dba','dba','/home/virtuoso/bin/isql','/home/aliada/linked-data-server/config/isql_rewrite_rules_dataset_default.sql', '/home/aliada/linked-data-server/config/isql_rewrite_rules_subset_default.sql', '/home/aliada/ckan-datahub-page-creation/config/dump_one_graph_nt.sql', '/home/virtuoso/var/lib/virtuoso/vsp', 'http://datahub.io/api/action', '****','Aliada Consortium','/home/aliada/bin/aliada_new_dataset.sql');


--
-- Volcado de datos para la tabla `t_language`
--
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('eng', 'en', 'English', 'eng');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('spa', 'es', 'Spanish', 'eng');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('ita', 'it', 'Italian', 'eng');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('hun', 'hu', 'Hungarian', 'eng');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('eng', 'en', 'Inglés', 'spa');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('spa', 'es', 'Español', 'spa');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('ita', 'it', 'Italiano', 'spa');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('hun', 'hu', 'Húngaro', 'spa');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('eng', 'en', 'English', 'hun');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('spa', 'es', 'Spanish', 'hun');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('ita', 'it', 'Italian', 'hun');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('hun', 'hu', 'Hungarian', 'hun');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('eng', 'en', 'English', 'ita');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('spa', 'es', 'Spanish', 'ita');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('ita', 'it', 'Italian', 'ita');
INSERT INTO `t_language` (`language_code`, `lang_code`, `language_name`, `language`) VALUES ('hun', 'hu', 'Hungarian', 'ita');


--
-- Volcado de datos para la tabla `profile`
--

INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(1, 'MARC BIB', 0, 'MARC biblio', 0, 0, 0, 0);
INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(2, 'MARC AUT', 0, 'MARC authorities', 1, 1, 0, 0);
INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(3, 'LIDO', 1, 'LIDO MUSEUM', 2, 2, 0, 0);
INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(4, 'DC', 2, 'Dublin Core Drupal', 3, 3, 0, 0);


--
-- Volcado de datos para la tabla `template`
--

INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(1, 'MARC BIB', 'MARC biblio', 0);
INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(2, 'Authorities', 'Authorities template', 1);
INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(3, 'LIDO', 'lido', 2);
INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES (4, 'Dublin Core', 'Drupal', 3);


--
-- Volcado de datos para la tabla `template_xml_tag`
--

INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'001');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'006');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'008');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'100a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'100b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'100f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'100l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'110a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'110b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'110f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'110l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'111f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'111l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130o');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130r');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'130s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'210a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'222a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240k');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240m');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240o');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240r');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'240s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'242a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'242c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'242h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'242n');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'242p');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243k');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243m');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243o');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243r');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'243s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245k');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245n');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245p');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'245s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'246a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'246h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'246n');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'246p');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'247a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'247h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'247n');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'247p');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'250a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'250b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'254a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'255g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'256a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'260b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'260c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'300a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'300c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'300f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'300g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'306a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'310a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'321a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'340l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'342o');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3526');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3528');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352i');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'352q');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3556');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3558');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'355h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3576');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'3578');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'357a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'357b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'357c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'357g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'362a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'4400');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'4406');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'4408');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440n');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440p');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440v');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440w');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'440x');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'490a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'490l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'490v');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'490x');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'504a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'505a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'505g');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'505r');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'505t');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'505u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'506u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'507b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'508a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'510a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'510b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'510c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'510x');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'511a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'514m');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'514u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'515a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'516a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'518a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'520a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'520b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'520u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'522a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'524a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'525a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'530a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'530b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'530c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'530d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'530u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'5337');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'533a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'533c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600h');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600k');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600m');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600o');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600r');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600u');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600y');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'600z');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'650c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'651a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (1,'655a');

INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'001');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'100a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'100c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'100d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'110a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'368d');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'368s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'368t');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'370a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'370b');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'370c');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'370e');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'370f');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'371a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'372a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'373a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'374a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'375a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'376a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'376s');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'376t');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'377a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'500a');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'500i');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (2,'678a');

INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'actorID[source=VIAF]');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'actorID[type=local]');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'actorInRole');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'actorInRole/actor');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'administrativeMetadata/recordWra');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'classification/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'culture/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'date/earliestDate');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'descriptiveNoteValue');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayActorInRole');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayMaterialsTech');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayObjectMeasurements');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayPlace');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayStateEditionWrap/displayE');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'displayStateEditionWrap/displayS');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'eventDescriptionSet');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'eventMaterialsTech');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'eventPlace');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'eventSet/event');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'gml/Point/pos');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'inscriptionDescription/descripti');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'lido:administrativeMetadata/lido');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'lido:appellationValue');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'lido:identifierComplexType');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'lido:term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'lido:type');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'measurementType');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'measurementUnit');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'measurementValue');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'nameActorSet/appellationValue[@l');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'namePlaceSet/appellationValue');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'nationalityActor/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectDescriptionSet/descriptive');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectMeasurements/formatMeasure');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectMeasurements/measurementsS');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectMeasurements/scaleMeasurem');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectMeasurements/shapeMeasurem');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectMeasurementsWrap/objectMea');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'objectWorkType/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'place');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'recordID');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'recordInfoSet/recordInfoLink');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'recordRights/rightsHolder');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'recordSource');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'repositorySet/repositoryLocation');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'repositorySet/repositoryName');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'resourceRepresentation/linkResou');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'resourceType/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'resourceWrap/resourceSet');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'rightsResource');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'rightsType');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'rightsWorkSet/creditLine');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'roleActor/term');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'titleSet/appellationValue');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'vitalDatesActor/earliestDate');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (3,'vitalDatesActor/latestDate');

INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:contributor');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:coverage');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:creator');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:date');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:description');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:format');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:language');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:publisher');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:source');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:subject');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:title');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'dc:type');
INSERT INTO `template_xml_tag` (`template_id`,`xml_tag_id`) VALUES (4,'identifier');

--
-- Volcado de datos para la tabla `t_character_set`
--

INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`, `language`) VALUES(0, 'UTF8', NULL, 'eng');
INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`, `language`) VALUES(0, 'UTF8', NULL, 'spa');
INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`, `language`) VALUES(0, 'UTF8', NULL, 'hun');
INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`, `language`) VALUES(0, 'UTF8', NULL, 'ita');

--
-- Volcado de datos para la tabla `t_external_dataset`
--

INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(0, 'DBPedia','dbpedia', 'Linked Data version of Wikipedia', 'http://dbpedia.org', '/home/aliada/links-discovery/config/silk/aliada_dbpedia_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(1, 'GeoNames','geonames', 'Linked Data version of Geonames', 'http://www.geonames.org', '/home/aliada/links-discovery/config/silk/aliada_geonames_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(2, 'Freebase','freebase', 'A community-curated database of well-known people, places, and things', 'https://www.freebase.com', '/home/aliada/links-discovery/config/silk/aliada_freebase_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(3, 'BNE','datos-bne-es', 'National Library of Spain Linked Data Dataset', 'http://datos.bne.es', '/home/aliada/links-discovery/config/silk/aliada_bne_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(4, 'BNB','bluk-bnb', 'The British National Bibliography as Linked Data', 'http://bnb.data.bl.uk/id/data/BNB', '/home/aliada/links-discovery/config/silk/aliada_bnb_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(5, 'Europeana','europeana-lod-v1', 'Europeana as Linked Open Data', 'http://data.europeana.eu', '/home/aliada/links-discovery/config/silk/aliada_europeana_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(6, 'NSZL','hungarian-national-library-catalog', 'Hungarian National Library (NSZL) as Linked Open Data', 'http://nektar.oszk.hu/wiki/Semantic_web', '/home/aliada/links-discovery/config/silk/aliada_nszl_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(7, 'MARC','marc-codes', 'Linked Data version of MARC Codes List', 'http://id.loc.gov', '/home/aliada/links-discovery/config/silk/aliada_marc_config.xml', 8, 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(8, 'VIAF','viaf', 'Virtual International Authority File', 'http://viaf.org', 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(9, 'Lobid: Libraries & rel. orgs','lobid-organisations', 'Lobid-organisations provides URIs for library-organisations and museums', 'http://lobid.org', 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(10, 'Lobid: Bibliographic Resources','lobid-resources', 'Lobid-resources offers access to metadata in RDF about bibliographic resources', 'http://lobid.org', 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(11, 'Library of Congress SH','lcsh', 'LCSH has been actively maintained since 1898 to catalogue materials held at the Library of Congress', 'http://id.loc.gov/authorities/subjects', 'eng');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(12, 'Open Library','open-library', 'Open Library is an open, editable library catalogue, building towards a web page for every book ever published', 'http://openlibrary.org', 'eng');

INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(0, 'DBPedia','dbpedia', 'Linked Data version of Wikipedia', 'http://dbpedia.org', '/home/aliada/links-discovery/config/silk/aliada_dbpedia_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(1, 'GeoNames','geonames', 'Linked Data version of Geonames', 'http://www.geonames.org', '/home/aliada/links-discovery/config/silk/aliada_geonames_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(2, 'Freebase','freebase', 'A community-curated database of well-known people, places, and things', 'https://www.freebase.com', '/home/aliada/links-discovery/config/silk/aliada_freebase_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(3, 'BNE','datos-bne-es', 'National Library of Spain Linked Data Dataset', 'http://datos.bne.es', '/home/aliada/links-discovery/config/silk/aliada_bne_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(4, 'BNB','bluk-bnb', 'The British National Bibliography as Linked Data', 'http://bnb.data.bl.uk/id/data/BNB', '/home/aliada/links-discovery/config/silk/aliada_bnb_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(5, 'Europeana','europeana-lod-v1', 'Europeana as Linked Open Data', 'http://data.europeana.eu', '/home/aliada/links-discovery/config/silk/aliada_europeana_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(6, 'NSZL','hungarian-national-library-catalog', 'Hungarian National Library (NSZL) as Linked Open Data', 'http://nektar.oszk.hu/wiki/Semantic_web', '/home/aliada/links-discovery/config/silk/aliada_nszl_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(7, 'MARC','marc-codes', 'Linked Data version of MARC Codes List', 'http://id.loc.gov', '/home/aliada/links-discovery/config/silk/aliada_marc_config.xml', 8, 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(8, 'VIAF','viaf', 'Virtual International Authority File', 'http://viaf.org', 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(9, 'Lobid: Libraries & rel. orgs','lobid-organisations', 'Lobid-organisations provides URIs for library-organisations and museums', 'http://lobid.org', 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(10, 'Lobid: Bibliographic Resources','lobid-resources', 'Lobid-resources offers access to metadata in RDF about bibliographic resources', 'http://lobid.org', 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(11, 'Library of Congress SH','lcsh', 'LCSH has been actively maintained since 1898 to catalogue materials held at the Library of Congress', 'http://id.loc.gov/authorities/subjects', 'spa');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(12, 'Open Library','open-library', 'Open Library is an open, editable library catalogue, building towards a web page for every book ever published', 'http://openlibrary.org', 'spa');

INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(0, 'DBPedia','dbpedia', 'Linked Data version of Wikipedia', 'http://dbpedia.org', '/home/aliada/links-discovery/config/silk/aliada_dbpedia_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(1, 'GeoNames','geonames', 'Linked Data version of Geonames', 'http://www.geonames.org', '/home/aliada/links-discovery/config/silk/aliada_geonames_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(2, 'Freebase','freebase', 'A community-curated database of well-known people, places, and things', 'https://www.freebase.com', '/home/aliada/links-discovery/config/silk/aliada_freebase_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(3, 'BNE','datos-bne-es', 'National Library of Spain Linked Data Dataset', 'http://datos.bne.es', '/home/aliada/links-discovery/config/silk/aliada_bne_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(4, 'BNB','bluk-bnb', 'The British National Bibliography as Linked Data', 'http://bnb.data.bl.uk/id/data/BNB', '/home/aliada/links-discovery/config/silk/aliada_bnb_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(5, 'Europeana','europeana-lod-v1', 'Europeana as Linked Open Data', 'http://data.europeana.eu', '/home/aliada/links-discovery/config/silk/aliada_europeana_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(6, 'NSZL','hungarian-national-library-catalog', 'Hungarian National Library (NSZL) as Linked Open Data', 'http://nektar.oszk.hu/wiki/Semantic_web', '/home/aliada/links-discovery/config/silk/aliada_nszl_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(7, 'MARC','marc-codes', 'Linked Data version of MARC Codes List', 'http://id.loc.gov', '/home/aliada/links-discovery/config/silk/aliada_marc_config.xml', 8, 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(8, 'VIAF','viaf', 'Virtual International Authority File', 'http://viaf.org', 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(9, 'Lobid: Libraries & rel. orgs','lobid-organisations', 'Lobid-organisations provides URIs for library-organisations and museums', 'http://lobid.org', 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(10, 'Lobid: Bibliographic Resources','lobid-resources', 'Lobid-resources offers access to metadata in RDF about bibliographic resources', 'http://lobid.org', 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(11, 'Library of Congress SH','lcsh', 'LCSH has been actively maintained since 1898 to catalogue materials held at the Library of Congress', 'http://id.loc.gov/authorities/subjects', 'hun');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(12, 'Open Library','open-library', 'Open Library is an open, editable library catalogue, building towards a web page for every book ever published', 'http://openlibrary.org', 'hun');

INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(0, 'DBPedia','dbpedia', 'Linked Data version of Wikipedia', 'http://dbpedia.org', '/home/aliada/links-discovery/config/silk/aliada_dbpedia_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(1, 'GeoNames','geonames', 'Linked Data version of Geonames', 'http://www.geonames.org', '/home/aliada/links-discovery/config/silk/aliada_geonames_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(2, 'Freebase','freebase', 'A community-curated database of well-known people, places, and things', 'https://www.freebase.com', '/home/aliada/links-discovery/config/silk/aliada_freebase_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(3, 'BNE','datos-bne-es', 'National Library of Spain Linked Data Dataset', 'http://datos.bne.es', '/home/aliada/links-discovery/config/silk/aliada_bne_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(4, 'BNB','bluk-bnb', 'The British National Bibliography as Linked Data', 'http://bnb.data.bl.uk/id/data/BNB', '/home/aliada/links-discovery/config/silk/aliada_bnb_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(5, 'Europeana','europeana-lod-v1', 'Europeana as Linked Open Data', 'http://data.europeana.eu', '/home/aliada/links-discovery/config/silk/aliada_europeana_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(6, 'NSZL','hungarian-national-library-catalog', 'Hungarian National Library (NSZL) as Linked Open Data', 'http://nektar.oszk.hu/wiki/Semantic_web', '/home/aliada/links-discovery/config/silk/aliada_nszl_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`, `language`) VALUES(7, 'MARC','marc-codes', 'Linked Data version of MARC Codes List', 'http://id.loc.gov', '/home/aliada/links-discovery/config/silk/aliada_marc_config.xml', 8, 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(8, 'VIAF','viaf', 'Virtual International Authority File', 'http://viaf.org', 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(9, 'Lobid: Libraries & rel. orgs','lobid-organisations', 'Lobid-organisations provides URIs for library-organisations and museums', 'http://lobid.org', 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(10, 'Lobid: Bibliographic Resources','lobid-resources', 'Lobid-resources offers access to metadata in RDF about bibliographic resources', 'http://lobid.org', 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(11, 'Library of Congress SH','lcsh', 'LCSH has been actively maintained since 1898 to catalogue materials held at the Library of Congress', 'http://id.loc.gov/authorities/subjects', 'ita');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_name_ckan`, `external_dataset_description`, `external_dataset_homepage`, `language`) VALUES(12, 'Open Library','open-library', 'Open Library is an open, editable library catalogue, building towards a web page for every book ever published', 'http://openlibrary.org', 'ita');


--
-- Volcado de datos para la tabla `t_file_format`
--

INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`, `language`) VALUES(0, 'XML', 'XML file', 'eng');
INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`, `language`) VALUES(0, 'XML', 'XML file', 'spa');
INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`, `language`) VALUES(0, 'XML', 'XML file', 'hun');
INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`, `language`) VALUES(0, 'XML', 'XML file', 'ita');

--
-- Volcado de datos para la tabla `t_file_type`
--

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(0, 'Bibliographic', NULL, 'marc_bib.xsl', 'eng');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(1, 'Authority', NULL, 'marc_aut.xsl', 'eng');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(2, 'Museum Resource', NULL, 'lido.xsl', 'eng');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(3, 'Dublin Core',  NULL, 'dc.xsl', 'eng');

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(0, 'Bibliographic', NULL, 'marc_bib.xsl', 'spa');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(1, 'Authority', NULL, 'marc_aut.xsl', 'spa');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(2, 'Museum Resource', NULL, 'lido.xsl', 'spa');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(3, 'Dublin Core',  NULL, 'dc.xsl', 'spa');

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(0, 'Bibliographic', NULL, 'marc_bib.xsl', 'hun');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(1, 'Authority', NULL, 'marc_aut.xsl', 'hun');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(2, 'Museum Resource', NULL, 'lido.xsl', 'hun');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(3, 'Dublin Core',  NULL, 'dc.xsl', 'hun');

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(0, 'Bibliographic', NULL, 'marc_bib.xsl', 'ita');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(1, 'Authority', NULL, 'marc_aut.xsl', 'ita');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(2, 'Museum Resource', NULL, 'lido.xsl', 'ita');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`, `language`) VALUES(3, 'Dublin Core',  NULL, 'dc.xsl', 'ita');

--
-- Volcado de datos para la tabla `t_metadata_scheme`
--

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(0, 'marcxml', NULL, 'MARC21slim.xsd', 'eng');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(1, 'auth', NULL, 'MARC21slim.xsd', 'eng');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(2, 'lido', NULL, 'lido-v1.0.xsd', 'eng');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(3, 'dc', NULL, 'dc.xsd', 'eng');

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(0, 'marcxml', NULL, 'MARC21slim.xsd', 'spa');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(1, 'auth', NULL, 'MARC21slim.xsd', 'spa');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(2, 'lido', NULL, 'lido-v1.0.xsd', 'spa');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(3, 'dc', NULL, 'dc.xsd', 'spa');

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(0, 'marcxml', NULL, 'MARC21slim.xsd', 'hun');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(1, 'auth', NULL, 'MARC21slim.xsd', 'hun');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(2, 'lido', NULL, 'lido-v1.0.xsd', 'hun');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(3, 'dc', NULL, 'dc.xsd', 'hun');

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(0, 'marcxml', NULL, 'MARC21slim.xsd', 'ita');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(1, 'auth', NULL, 'MARC21slim.xsd', 'ita');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(2, 'lido', NULL, 'lido-v1.0.xsd', 'ita');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`, `language`) VALUES(3, 'dc', NULL, 'dc.xsd', 'ita');


--
-- Volcado de datos para la tabla `t_profile_type`
--

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(0, 'ILS', NULL, 'eng');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(1, 'TMS', NULL, 'eng');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(2, 'Drupal', NULL, 'eng');

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(0, 'ILS', NULL, 'spa');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(1, 'TMS', NULL, 'spa');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(2, 'Drupal', NULL, 'spa');

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(0, 'ILS', NULL, 'hun');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(1, 'TMS', NULL, 'hun');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(2, 'Drupal', NULL, 'hun');

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(0, 'ILS', NULL, 'ita');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(1, 'TMS', NULL, 'ita');
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`, `language`) VALUES(2, 'Drupal', NULL, 'ita');


--
-- Volcado de datos para la tabla `t_user_role`
--

INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(0, 'Administrador', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(1, 'Administrative Officer', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(2, 'Programmer', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(3, 'Librarian', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(4, 'Archivist', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(5, 'Manager', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(6, 'Cataloguer', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(7, 'Supervisor', NULL, 'eng');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(8, 'Library Director', NULL, 'eng');

INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(0, 'Administrador', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(1, 'Administrativo', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(2, 'Programador', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(3, 'Bibliotecario', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(4, 'Archivero', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(5, 'Gestor', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(6, 'Catalogador', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(7, 'Supervisor', NULL, 'spa');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(8, 'Director de la Biblioteca', NULL, 'spa');

INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(0, 'Administrador', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(1, 'Administrative Officer', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(2, 'Programmer', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(3, 'Librarian', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(4, 'Archivist', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(5, 'Manager', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(6, 'Cataloguer', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(7, 'Supervisor', NULL, 'hun');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(8, 'Library Director', NULL, 'hun');

INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(0, 'Administrador', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(1, 'Administrative Officer', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(2, 'Programmer', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(3, 'Librarian', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(4, 'Archivist', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(5, 'Manager', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(6, 'Cataloguer', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(7, 'Supervisor', NULL, 'ita');
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`, `language`) VALUES(8, 'Library Director', NULL, 'ita');

--
-- Volcado de datos para la tabla `t_user_type`
--

INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(0, 'Basic', NULL, 'eng');
INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(1, 'Advanced', NULL, 'eng');

INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(0, 'Basic', NULL, 'spa');
INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(1, 'Advanced', NULL, 'spa');

INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(0, 'Basic', NULL, 'hun');
INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(1, 'Advanced', NULL, 'hun');

INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(0, 'Basic', NULL, 'ita');
INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`, `language`) VALUES(1, 'Advanced', NULL, 'ita');


--
-- Volcado de datos para la tabla `t_xml_tag_type`
--

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(0, 'MARC21 Bibliographic', NULL, 'eng');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(1, 'MARC21 Authority', NULL, 'eng');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(2, 'LIDO', NULL, 'eng');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(3, 'DC', NULL, 'eng');

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(0, 'MARC21 Bibliographic', NULL, 'spa');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(1, 'MARC21 Authority', NULL, 'spa');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(2, 'LIDO', NULL, 'spa');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(3, 'DC', NULL, 'spa');

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(0, 'MARC21 Bibliographic', NULL, 'hun');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(1, 'MARC21 Authority', NULL, 'hun');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(2, 'LIDO', NULL, 'hun');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(3, 'DC', NULL, 'hun');

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(0, 'MARC21 Bibliographic', NULL, 'ita');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(1, 'MARC21 Authority', NULL, 'ita');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(2, 'LIDO', NULL, 'ita');
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`, `language`) VALUES(3, 'DC', NULL, 'ita');


--
-- Volcado de datos para la tabla `t_template_entity`
--

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Work','Work',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Expression','Expression',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestation','Manifestation',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Item','Item',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Person','Person',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Corporate Body','Corporate Body',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Concept','Concept',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Object','Object',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Event','Event',0,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Place','Place',0,'eng');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Obra','Obra',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Expresión','Expresión',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestatación','Manifestatación',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Ítem','Ítem',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Persona','Persona',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Entidad corporativa','Entidad corporativa',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Concepto','Concepto',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Objeto','Objeto',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Evento','Evento',0,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Lugar','Lugar',0,'spa');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Mű','Mű',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Kifejezési forma','Kifejezési forma',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Megjelenési forma','Megjelenési forma',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Példány','Példány',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Személy','Személy',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Testület','Testület',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Fogalom','Fogalom',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Tárgy','Tárgy',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Esemény','Esemény',0,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Hely','Hely',0,'hun');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Opera','Opera',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Espressione','Espressione',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestazione','Manifestazione',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Item','Item',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Persona','Persona',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Ente','Ente',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Soggetto','Soggetto',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Oggetto','Oggetto',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Evento','Evento',0,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Luogo','Luogo',0,'ita');


INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Person','Person',1,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Corporate Body','Corporate Body',1,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Family','Family',1,'eng');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Persona','Persona',1,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Entidad corporativa','Entidad corporativa',1,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Familia','Familia',1,'spa');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Személy','Személy',1,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Testület','Testület',1,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Család','Család',1,'hun');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Persona','Persona',1,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Ente','Ente',1,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Famiglia','Famiglia',1,'ita');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Object','Object',2,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Event','Event',2,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Place','Place',2,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Person','Person',2,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Dimension','Dimension',2,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Rights','Rights',2,'eng');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Objeto','Objeto',2,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Evento','Evento',2,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Lugar','Lugar',2,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Persona','Persona',2,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Dimensión','Dimensión',2,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Derecho','Derecho',2,'spa');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Tárgy','Tárgy',2,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Esemény','Esemény',2,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Hely','Hely',2,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Személy','Személy',2,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Méret','Méret',2,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Jogok','Jogok',2,'hun');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Oggetto','Oggetto',2,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Evento','Evento',2,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Luogo','Luogo',2,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Persona','Persona',2,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Dimensione','Dimensione',2,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Diritti','Diritti',2,'ita');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Work','Work',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Expression','Expression',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestation','Manifestation',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Item','Item',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Person','Person',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Corporate Body','Corporate Body',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Concept','Concept',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Object','Object',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Event','Event',3,'eng');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Place','Place',3,'eng');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Obra','Obra',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Expresión','Expresión',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestatación','Manifestatación',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Ítem','Ítem',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Persona','Persona',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Entidad corporativa','Entidad corporativa',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Concepto','Concepto',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Objeto','Objeto',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Evento','Evento',3,'spa');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Lugar','Lugar',3,'spa');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Mű','Mű',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Kifejezési forma','Kifejezési forma',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Megjelenési forma','Megjelenési forma',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Példány','Példány',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Személy','Személy',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Testület','Testület',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Fogalom','Fogalom',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Tárgy','Tárgy',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Esemény','Esemény',3,'hun');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Hely','Hely',3,'hun');

INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (1,'Opera','Opera',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (2,'Espressione','Espressione',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (3,'Manifestazione','Manifestazione',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (4,'Item','Item',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (5,'Persona','Persona',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (6,'Ente','Ente',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (7,'Soggetto','Soggetto',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (8,'Oggetto','Oggetto',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (9,'Evento','Evento',3,'ita');
INSERT INTO `t_template_entity` (`template_entity_code`,`template_entity_name`,`template_entity_description`,`xml_tag_type_code`,`language`) VALUES (10,'Luogo','Luogo',3,'ita');

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`user_name`, `user_password`, `user_email`, `user_type_code`, `user_role_code`, `organisationId`) VALUES('admin', '1eh/F6TPx3EfCmKlAEeeppB1PHE+J16XaJIS/ig/78o+3yfNwSsso7YsldTyPnhW', 'admin@aliada.eu', 1, 1, 1);

--
-- Volcado de datos para la tabla `xml_tag`
--

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('006',0,'006',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('008',0,'008',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024a',0,'024a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024d',0,'024d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024z',0,'024z',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045a',0,'045a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045b',0,'045b',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045c',0,'045c',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046k',0,'046k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046l',0,'046l',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046o',0,'046o',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046p',0,'046p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',0,'100a',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100b',0,'100b',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100f',0,'100f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100g',0,'100f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100j',0,'100j',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100k',0,'100k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100l',0,'100l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100n',0,'100n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100p',0,'100p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'1001',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100t',0,'100t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100u',0,'100u',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',0,'110a',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110b',0,'110b',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110c',0,'110c',10,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110d',0,'110d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110f',0,'110f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110j',0,'110j',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110k',0,'110k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110l',0,'110l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110n',0,'110n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110p',0,'110p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110q',0,'110q',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110t',0,'110t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110u',0,'110u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',0,'111a',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111b',0,'111b',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111c',0,'111c',10,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111d',0,'111d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111f',0,'111f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111j',0,'111j',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111k',0,'111k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111l',0,'111l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111n',0,'111n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111p',0,'111p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111q',0,'111q',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111t',0,'111t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111u',0,'111u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',0,'130a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130d',0,'130d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130f',0,'130f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130h',0,'130h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130k',0,'130k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130l',0,'130l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130m',0,'130m',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130n',0,'130n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130o',0,'130o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130p',0,'130p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130r',0,'130r',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130s',0,'130s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130t',0,'130t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130v',0,'130v',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('210a',0,'210a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('222a',0,'222a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240a',0,'240a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240d',0,'240d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240f',0,'240f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240h',0,'240h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240k',0,'240k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240l',0,'240l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240m',0,'240m',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240n',0,'240n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240o',0,'240o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240p',0,'240p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240r',0,'240r',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240s',0,'240s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242a',0,'242a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242c',0,'242c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242h',0,'242h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242n',0,'242n',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242p',0,'242p',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243a',0,'243a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243d',0,'243d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243f',0,'243f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243h',0,'243h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243k',0,'243k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243l',0,'243l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243m',0,'243m',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243n',0,'243n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243o',0,'243o',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243p',0,'243p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243r',0,'243r',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243s',0,'243s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245a',0,'245a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245c',0,'245c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245f',0,'245f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245g',0,'245g',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245h',0,'245h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245k',0,'245k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245n',0,'245n',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245p',0,'245p',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245s',0,'245s',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246a',0,'246a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246h',0,'246h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246n',0,'246n',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246p',0,'246p',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247a',0,'247a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247h',0,'247h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247n',0,'247n',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247p',0,'247p',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250a',0,'250a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250b',0,'250b',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('254a',0,'254a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255a',0,'255a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255b',0,'255b',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255c',0,'255c',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255d',0,'255d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255e',0,'255e',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255g',0,'255g',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('256a',0,'256a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260b',0,'260b',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260c',0,'260c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300a',0,'300a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300c',0,'300c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300f',0,'300f',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300g',0,'300g',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('306a',0,'306a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('310a',0,'310a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('321a',0,'321a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336a',0,'336a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336b',0,'336b',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340a',0,'340a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340b',0,'340b',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340c',0,'340c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340d',0,'340d',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340e',0,'340e',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340f',0,'340f',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340l',0,'3401',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('342o',0,'343o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3526',0,'3526',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3528',0,'3528',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352a',0,'352a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352b',0,'352b',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352c',0,'352c',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352d',0,'352d',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352e',0,'352e',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352f',0,'352f',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352g',0,'352g',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352i',0,'352i',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352q',0,'352q',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3556',0,'3556',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3558',0,'3558',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355a',0,'355a',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355b',0,'355b',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355c',0,'355c',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355d',0,'355d',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355e',0,'355e',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355f',0,'355f',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355g',0,'355g',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355h',0,'355h',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3576',0,'3576',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3578',0,'3578',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357a',0,'357a',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357b',0,'357b',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357c',0,'357c',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357g',0,'357g',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('362a',0,'362a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377l',0,'377l',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('381a',0,'381a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4400',0,'4400',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4406',0,'4406',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4408',0,'4408',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440a',0,'440a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440n',0,'440n',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440p',0,'440p',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440v',0,'440v',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440w',0,'440w',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440x',0,'440x',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490a',0,'490a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490l',0,'490l',4,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490v',0,'490v',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490x',0,'490x',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('504a',0,'504a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505a',0,'505a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505g',0,'505g',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505r',0,'505r',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505t',0,'505t',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505u',0,'505u',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506a',0,'506a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506b',0,'506b',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506c',0,'506c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506d',0,'506d',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506e',0,'506e',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506u',0,'506u',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('507b',0,'507b',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('508a',0,'508a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510a',0,'510a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510b',0,'510b',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510c',0,'510c',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510x',0,'510x',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('511a',0,'511a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514m',0,'514m',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514u',0,'514u',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('515a',0,'515a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('516a',0,'516a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('518a',0,'518a',9,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520a',0,'520a',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520b',0,'520b',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520u',0,'520u',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('522a',0,'522a',10,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('524a',0,'524a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('525a',0,'525a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530a',0,'530a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530b',0,'530b',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530c',0,'530c',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530d',0,'530d',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530u',0,'530u',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('5337',0,'5337',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533a',0,'533a',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533c',0,'533c',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('546a',0,'546a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600f',0,'600f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600h',0,'600h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600k',0,'600k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600l',0,'600l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600m',0,'600m',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600n',0,'600n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600o',0,'600o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600p',0,'600p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600r',0,'600r',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600s',0,'600s',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600t',0,'600t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600u',0,'600u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600y',0,'600y',7,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600z',0,'600z',7,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('648a',0,'648a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('650c',0,'600c',10,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('651a',0,'651a',10,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('655a',0,'655a',7,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700a',0,'700a',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700b',0,'700b',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700c',0,'700c',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700d',0,'700d',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700e',0,'700e',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700f',0,'700f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700g',0,'700g',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700h',0,'700h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700k',0,'700k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700l',0,'700l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700n',0,'700n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700o',0,'700o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700p',0,'700p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700q',0,'700q',5,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700s',0,'700s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700t',0,'700t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700u',0,'700u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710a',0,'710a',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710b',0,'710b',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710c',0,'710c',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710d',0,'710d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710e',0,'710e',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710f',0,'710f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710h',0,'710h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710k',0,'710k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710l',0,'710l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710n',0,'710n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710o',0,'710o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710p',0,'710p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710q',0,'710q',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710s',0,'710s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710t',0,'710t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710u',0,'710u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711a',0,'711a',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711b',0,'711b',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711c',0,'711c',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711d',0,'711d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711e',0,'711e',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711f',0,'711f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711h',0,'711h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711k',0,'711k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711l',0,'711l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711n',0,'711n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711p',0,'711p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711q',0,'711q',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711s',0,'711s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711t',0,'711t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711u',0,'711u',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730a',0,'730a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730d',0,'730d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730f',0,'730f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730h',0,'730h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730k',0,'730k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730l',0,'730l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730n',0,'730n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730o',0,'730o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730p',0,'730p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730s',0,'730s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800f',0,'800f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800h',0,'800h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800k',0,'800k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800l',0,'800l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800n',0,'800n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800o',0,'800o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800p',0,'800p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800s',0,'800s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800t',0,'800t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810d',0,'810d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810f',0,'810f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810k',0,'810k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810l',0,'810l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810n',0,'810n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810o',0,'810o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810p',0,'810p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810s',0,'810s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810t',0,'810t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811d',0,'811d',6,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811f',0,'811f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811h',0,'811h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811k',0,'811k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811n',0,'811n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811p',0,'811p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811s',0,'811s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811t',0,'811t',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830a',0,'830a',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830d',0,'830d',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830f',0,'830f',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830h',0,'830h',3,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830k',0,'830k',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830l',0,'830l',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830n',0,'830n',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830o',0,'830o',2,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830p',0,'830p',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830s',0,'830s',1,0,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('852a',0,'852a',4,0,'eng');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('006',0,'006',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('008',0,'008',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024a',0,'024a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024d',0,'024d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024z',0,'024z',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045a',0,'045a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045b',0,'045b',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045c',0,'045c',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046k',0,'046k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046l',0,'046l',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046o',0,'046o',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046p',0,'046p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',0,'100a',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100b',0,'100b',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100f',0,'100f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100g',0,'100f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100j',0,'100j',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100k',0,'100k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100l',0,'100l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100n',0,'100n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100p',0,'100p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'1001',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100t',0,'100t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100u',0,'100u',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',0,'110a',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110b',0,'110b',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110c',0,'110c',10,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110d',0,'110d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110f',0,'110f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110j',0,'110j',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110k',0,'110k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110l',0,'110l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110n',0,'110n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110p',0,'110p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110q',0,'110q',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110t',0,'110t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110u',0,'110u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',0,'111a',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111b',0,'111b',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111c',0,'111c',10,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111d',0,'111d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111f',0,'111f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111j',0,'111j',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111k',0,'111k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111l',0,'111l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111n',0,'111n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111p',0,'111p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111q',0,'111q',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111t',0,'111t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111u',0,'111u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',0,'130a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130d',0,'130d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130f',0,'130f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130h',0,'130h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130k',0,'130k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130l',0,'130l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130m',0,'130m',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130n',0,'130n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130o',0,'130o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130p',0,'130p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130r',0,'130r',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130s',0,'130s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130t',0,'130t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130v',0,'130v',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('210a',0,'210a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('222a',0,'222a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240a',0,'240a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240d',0,'240d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240f',0,'240f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240h',0,'240h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240k',0,'240k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240l',0,'240l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240m',0,'240m',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240n',0,'240n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240o',0,'240o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240p',0,'240p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240r',0,'240r',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240s',0,'240s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242a',0,'242a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242c',0,'242c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242h',0,'242h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242n',0,'242n',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242p',0,'242p',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243a',0,'243a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243d',0,'243d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243f',0,'243f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243h',0,'243h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243k',0,'243k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243l',0,'243l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243m',0,'243m',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243n',0,'243n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243o',0,'243o',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243p',0,'243p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243r',0,'243r',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243s',0,'243s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245a',0,'245a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245c',0,'245c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245f',0,'245f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245g',0,'245g',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245h',0,'245h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245k',0,'245k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245n',0,'245n',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245p',0,'245p',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245s',0,'245s',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246a',0,'246a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246h',0,'246h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246n',0,'246n',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246p',0,'246p',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247a',0,'247a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247h',0,'247h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247n',0,'247n',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247p',0,'247p',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250a',0,'250a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250b',0,'250b',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('254a',0,'254a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255a',0,'255a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255b',0,'255b',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255c',0,'255c',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255d',0,'255d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255e',0,'255e',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255g',0,'255g',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('256a',0,'256a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260b',0,'260b',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260c',0,'260c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300a',0,'300a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300c',0,'300c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300f',0,'300f',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300g',0,'300g',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('306a',0,'306a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('310a',0,'310a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('321a',0,'321a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336a',0,'336a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336b',0,'336b',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340a',0,'340a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340b',0,'340b',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340c',0,'340c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340d',0,'340d',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340e',0,'340e',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340f',0,'340f',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340l',0,'3401',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('342o',0,'343o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3526',0,'3526',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3528',0,'3528',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352a',0,'352a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352b',0,'352b',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352c',0,'352c',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352d',0,'352d',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352e',0,'352e',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352f',0,'352f',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352g',0,'352g',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352i',0,'352i',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352q',0,'352q',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3556',0,'3556',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3558',0,'3558',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355a',0,'355a',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355b',0,'355b',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355c',0,'355c',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355d',0,'355d',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355e',0,'355e',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355f',0,'355f',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355g',0,'355g',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355h',0,'355h',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3576',0,'3576',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3578',0,'3578',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357a',0,'357a',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357b',0,'357b',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357c',0,'357c',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357g',0,'357g',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('362a',0,'362a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377l',0,'377l',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('381a',0,'381a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4400',0,'4400',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4406',0,'4406',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4408',0,'4408',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440a',0,'440a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440n',0,'440n',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440p',0,'440p',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440v',0,'440v',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440w',0,'440w',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440x',0,'440x',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490a',0,'490a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490l',0,'490l',4,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490v',0,'490v',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490x',0,'490x',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('504a',0,'504a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505a',0,'505a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505g',0,'505g',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505r',0,'505r',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505t',0,'505t',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505u',0,'505u',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506a',0,'506a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506b',0,'506b',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506c',0,'506c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506d',0,'506d',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506e',0,'506e',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506u',0,'506u',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('507b',0,'507b',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('508a',0,'508a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510a',0,'510a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510b',0,'510b',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510c',0,'510c',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510x',0,'510x',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('511a',0,'511a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514m',0,'514m',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514u',0,'514u',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('515a',0,'515a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('516a',0,'516a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('518a',0,'518a',9,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520a',0,'520a',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520b',0,'520b',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520u',0,'520u',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('522a',0,'522a',10,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('524a',0,'524a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('525a',0,'525a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530a',0,'530a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530b',0,'530b',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530c',0,'530c',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530d',0,'530d',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530u',0,'530u',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('5337',0,'5337',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533a',0,'533a',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533c',0,'533c',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('546a',0,'546a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600f',0,'600f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600h',0,'600h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600k',0,'600k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600l',0,'600l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600m',0,'600m',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600n',0,'600n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600o',0,'600o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600p',0,'600p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600r',0,'600r',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600s',0,'600s',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600t',0,'600t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600u',0,'600u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600y',0,'600y',7,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600z',0,'600z',7,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('648a',0,'648a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('650c',0,'600c',10,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('651a',0,'651a',10,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('655a',0,'655a',7,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700a',0,'700a',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700b',0,'700b',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700c',0,'700c',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700d',0,'700d',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700e',0,'700e',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700f',0,'700f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700g',0,'700g',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700h',0,'700h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700k',0,'700k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700l',0,'700l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700n',0,'700n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700o',0,'700o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700p',0,'700p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700q',0,'700q',5,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700s',0,'700s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700t',0,'700t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700u',0,'700u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710a',0,'710a',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710b',0,'710b',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710c',0,'710c',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710d',0,'710d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710e',0,'710e',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710f',0,'710f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710h',0,'710h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710k',0,'710k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710l',0,'710l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710n',0,'710n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710o',0,'710o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710p',0,'710p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710q',0,'710q',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710s',0,'710s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710t',0,'710t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710u',0,'710u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711a',0,'711a',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711b',0,'711b',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711c',0,'711c',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711d',0,'711d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711e',0,'711e',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711f',0,'711f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711h',0,'711h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711k',0,'711k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711l',0,'711l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711n',0,'711n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711p',0,'711p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711q',0,'711q',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711s',0,'711s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711t',0,'711t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711u',0,'711u',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730a',0,'730a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730d',0,'730d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730f',0,'730f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730h',0,'730h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730k',0,'730k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730l',0,'730l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730n',0,'730n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730o',0,'730o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730p',0,'730p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730s',0,'730s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800f',0,'800f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800h',0,'800h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800k',0,'800k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800l',0,'800l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800n',0,'800n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800o',0,'800o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800p',0,'800p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800s',0,'800s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800t',0,'800t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810d',0,'810d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810f',0,'810f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810k',0,'810k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810l',0,'810l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810n',0,'810n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810o',0,'810o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810p',0,'810p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810s',0,'810s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810t',0,'810t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811d',0,'811d',6,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811f',0,'811f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811h',0,'811h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811k',0,'811k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811n',0,'811n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811p',0,'811p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811s',0,'811s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811t',0,'811t',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830a',0,'830a',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830d',0,'830d',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830f',0,'830f',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830h',0,'830h',3,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830k',0,'830k',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830l',0,'830l',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830n',0,'830n',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830o',0,'830o',2,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830p',0,'830p',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830s',0,'830s',1,0,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('852a',0,'852a',4,0,'spa');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('006',0,'006',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('008',0,'008',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024a',0,'024a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024d',0,'024d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024z',0,'024z',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045a',0,'045a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045b',0,'045b',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045c',0,'045c',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046k',0,'046k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046l',0,'046l',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046o',0,'046o',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046p',0,'046p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',0,'100a',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100b',0,'100b',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100f',0,'100f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100g',0,'100f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100j',0,'100j',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100k',0,'100k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100l',0,'100l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100n',0,'100n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100p',0,'100p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'1001',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100t',0,'100t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100u',0,'100u',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',0,'110a',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110b',0,'110b',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110c',0,'110c',10,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110d',0,'110d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110f',0,'110f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110j',0,'110j',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110k',0,'110k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110l',0,'110l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110n',0,'110n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110p',0,'110p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110q',0,'110q',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110t',0,'110t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110u',0,'110u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',0,'111a',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111b',0,'111b',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111c',0,'111c',10,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111d',0,'111d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111f',0,'111f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111j',0,'111j',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111k',0,'111k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111l',0,'111l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111n',0,'111n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111p',0,'111p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111q',0,'111q',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111t',0,'111t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111u',0,'111u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',0,'130a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130d',0,'130d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130f',0,'130f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130h',0,'130h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130k',0,'130k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130l',0,'130l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130m',0,'130m',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130n',0,'130n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130o',0,'130o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130p',0,'130p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130r',0,'130r',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130s',0,'130s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130t',0,'130t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130v',0,'130v',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('210a',0,'210a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('222a',0,'222a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240a',0,'240a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240d',0,'240d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240f',0,'240f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240h',0,'240h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240k',0,'240k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240l',0,'240l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240m',0,'240m',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240n',0,'240n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240o',0,'240o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240p',0,'240p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240r',0,'240r',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240s',0,'240s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242a',0,'242a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242c',0,'242c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242h',0,'242h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242n',0,'242n',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242p',0,'242p',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243a',0,'243a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243d',0,'243d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243f',0,'243f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243h',0,'243h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243k',0,'243k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243l',0,'243l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243m',0,'243m',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243n',0,'243n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243o',0,'243o',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243p',0,'243p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243r',0,'243r',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243s',0,'243s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245a',0,'245a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245c',0,'245c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245f',0,'245f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245g',0,'245g',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245h',0,'245h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245k',0,'245k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245n',0,'245n',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245p',0,'245p',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245s',0,'245s',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246a',0,'246a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246h',0,'246h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246n',0,'246n',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246p',0,'246p',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247a',0,'247a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247h',0,'247h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247n',0,'247n',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247p',0,'247p',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250a',0,'250a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250b',0,'250b',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('254a',0,'254a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255a',0,'255a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255b',0,'255b',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255c',0,'255c',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255d',0,'255d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255e',0,'255e',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255g',0,'255g',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('256a',0,'256a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260b',0,'260b',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260c',0,'260c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300a',0,'300a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300c',0,'300c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300f',0,'300f',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300g',0,'300g',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('306a',0,'306a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('310a',0,'310a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('321a',0,'321a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336a',0,'336a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336b',0,'336b',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340a',0,'340a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340b',0,'340b',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340c',0,'340c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340d',0,'340d',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340e',0,'340e',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340f',0,'340f',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340l',0,'3401',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('342o',0,'343o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3526',0,'3526',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3528',0,'3528',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352a',0,'352a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352b',0,'352b',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352c',0,'352c',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352d',0,'352d',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352e',0,'352e',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352f',0,'352f',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352g',0,'352g',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352i',0,'352i',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352q',0,'352q',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3556',0,'3556',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3558',0,'3558',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355a',0,'355a',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355b',0,'355b',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355c',0,'355c',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355d',0,'355d',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355e',0,'355e',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355f',0,'355f',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355g',0,'355g',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355h',0,'355h',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3576',0,'3576',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3578',0,'3578',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357a',0,'357a',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357b',0,'357b',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357c',0,'357c',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357g',0,'357g',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('362a',0,'362a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377l',0,'377l',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('381a',0,'381a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4400',0,'4400',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4406',0,'4406',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4408',0,'4408',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440a',0,'440a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440n',0,'440n',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440p',0,'440p',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440v',0,'440v',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440w',0,'440w',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440x',0,'440x',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490a',0,'490a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490l',0,'490l',4,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490v',0,'490v',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490x',0,'490x',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('504a',0,'504a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505a',0,'505a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505g',0,'505g',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505r',0,'505r',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505t',0,'505t',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505u',0,'505u',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506a',0,'506a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506b',0,'506b',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506c',0,'506c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506d',0,'506d',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506e',0,'506e',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506u',0,'506u',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('507b',0,'507b',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('508a',0,'508a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510a',0,'510a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510b',0,'510b',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510c',0,'510c',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510x',0,'510x',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('511a',0,'511a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514m',0,'514m',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514u',0,'514u',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('515a',0,'515a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('516a',0,'516a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('518a',0,'518a',9,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520a',0,'520a',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520b',0,'520b',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520u',0,'520u',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('522a',0,'522a',10,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('524a',0,'524a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('525a',0,'525a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530a',0,'530a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530b',0,'530b',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530c',0,'530c',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530d',0,'530d',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530u',0,'530u',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('5337',0,'5337',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533a',0,'533a',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533c',0,'533c',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('546a',0,'546a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600f',0,'600f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600h',0,'600h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600k',0,'600k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600l',0,'600l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600m',0,'600m',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600n',0,'600n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600o',0,'600o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600p',0,'600p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600r',0,'600r',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600s',0,'600s',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600t',0,'600t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600u',0,'600u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600y',0,'600y',7,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600z',0,'600z',7,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('648a',0,'648a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('650c',0,'600c',10,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('651a',0,'651a',10,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('655a',0,'655a',7,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700a',0,'700a',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700b',0,'700b',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700c',0,'700c',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700d',0,'700d',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700e',0,'700e',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700f',0,'700f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700g',0,'700g',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700h',0,'700h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700k',0,'700k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700l',0,'700l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700n',0,'700n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700o',0,'700o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700p',0,'700p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700q',0,'700q',5,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700s',0,'700s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700t',0,'700t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700u',0,'700u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710a',0,'710a',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710b',0,'710b',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710c',0,'710c',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710d',0,'710d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710e',0,'710e',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710f',0,'710f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710h',0,'710h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710k',0,'710k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710l',0,'710l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710n',0,'710n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710o',0,'710o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710p',0,'710p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710q',0,'710q',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710s',0,'710s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710t',0,'710t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710u',0,'710u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711a',0,'711a',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711b',0,'711b',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711c',0,'711c',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711d',0,'711d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711e',0,'711e',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711f',0,'711f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711h',0,'711h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711k',0,'711k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711l',0,'711l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711n',0,'711n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711p',0,'711p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711q',0,'711q',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711s',0,'711s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711t',0,'711t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711u',0,'711u',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730a',0,'730a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730d',0,'730d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730f',0,'730f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730h',0,'730h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730k',0,'730k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730l',0,'730l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730n',0,'730n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730o',0,'730o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730p',0,'730p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730s',0,'730s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800f',0,'800f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800h',0,'800h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800k',0,'800k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800l',0,'800l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800n',0,'800n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800o',0,'800o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800p',0,'800p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800s',0,'800s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800t',0,'800t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810d',0,'810d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810f',0,'810f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810k',0,'810k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810l',0,'810l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810n',0,'810n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810o',0,'810o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810p',0,'810p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810s',0,'810s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810t',0,'810t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811d',0,'811d',6,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811f',0,'811f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811h',0,'811h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811k',0,'811k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811n',0,'811n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811p',0,'811p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811s',0,'811s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811t',0,'811t',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830a',0,'830a',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830d',0,'830d',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830f',0,'830f',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830h',0,'830h',3,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830k',0,'830k',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830l',0,'830l',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830n',0,'830n',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830o',0,'830o',2,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830p',0,'830p',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830s',0,'830s',1,0,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('852a',0,'852a',4,0,'hun');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('006',0,'006',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('008',0,'008',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024a',0,'024a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024d',0,'024d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('024z',0,'024z',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045a',0,'045a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045b',0,'045b',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('045c',0,'045c',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046k',0,'046k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046l',0,'046l',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046o',0,'046o',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('046p',0,'046p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',0,'100a',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100b',0,'100b',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100f',0,'100f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100g',0,'100f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100j',0,'100j',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100k',0,'100k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100l',0,'100l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100n',0,'100n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100p',0,'100p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'1001',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100t',0,'100t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100u',0,'100u',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',0,'110a',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110b',0,'110b',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110c',0,'110c',10,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110d',0,'110d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110f',0,'110f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110j',0,'110j',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110k',0,'110k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110l',0,'110l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110n',0,'110n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110p',0,'110p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110q',0,'110q',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110t',0,'110t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110u',0,'110u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',0,'111a',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111b',0,'111b',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111c',0,'111c',10,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111d',0,'111d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111f',0,'111f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111j',0,'111j',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111k',0,'111k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111l',0,'111l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111n',0,'111n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111p',0,'111p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111q',0,'111q',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111t',0,'111t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111u',0,'111u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',0,'130a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130d',0,'130d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130f',0,'130f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130h',0,'130h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130k',0,'130k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130l',0,'130l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130m',0,'130m',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130n',0,'130n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130o',0,'130o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130p',0,'130p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130r',0,'130r',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130s',0,'130s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130t',0,'130t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130v',0,'130v',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('210a',0,'210a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('222a',0,'222a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240a',0,'240a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240d',0,'240d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240f',0,'240f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240h',0,'240h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240k',0,'240k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240l',0,'240l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240m',0,'240m',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240n',0,'240n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240o',0,'240o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240p',0,'240p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240r',0,'240r',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('240s',0,'240s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242a',0,'242a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242c',0,'242c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242h',0,'242h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242n',0,'242n',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('242p',0,'242p',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243a',0,'243a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243d',0,'243d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243f',0,'243f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243h',0,'243h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243k',0,'243k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243l',0,'243l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243m',0,'243m',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243n',0,'243n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243o',0,'243o',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243p',0,'243p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243r',0,'243r',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('243s',0,'243s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245a',0,'245a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245c',0,'245c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245f',0,'245f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245g',0,'245g',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245h',0,'245h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245k',0,'245k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245n',0,'245n',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245p',0,'245p',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('245s',0,'245s',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246a',0,'246a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246h',0,'246h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246n',0,'246n',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('246p',0,'246p',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247a',0,'247a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247h',0,'247h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247n',0,'247n',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('247p',0,'247p',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250a',0,'250a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('250b',0,'250b',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('254a',0,'254a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255a',0,'255a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255b',0,'255b',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255c',0,'255c',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255d',0,'255d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255e',0,'255e',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('255g',0,'255g',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('256a',0,'256a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260b',0,'260b',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('260c',0,'260c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300a',0,'300a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300c',0,'300c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300f',0,'300f',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('300g',0,'300g',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('306a',0,'306a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('310a',0,'310a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('321a',0,'321a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336a',0,'336a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('336b',0,'336b',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340a',0,'340a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340b',0,'340b',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340c',0,'340c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340d',0,'340d',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340e',0,'340e',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340f',0,'340f',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('340l',0,'3401',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('342o',0,'343o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3526',0,'3526',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3528',0,'3528',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352a',0,'352a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352b',0,'352b',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352c',0,'352c',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352d',0,'352d',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352e',0,'352e',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352f',0,'352f',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352g',0,'352g',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352i',0,'352i',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('352q',0,'352q',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3556',0,'3556',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3558',0,'3558',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355a',0,'355a',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355b',0,'355b',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355c',0,'355c',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355d',0,'355d',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355e',0,'355e',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355f',0,'355f',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355g',0,'355g',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('355h',0,'355h',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3576',0,'3576',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('3578',0,'3578',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357a',0,'357a',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357b',0,'357b',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357c',0,'357c',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('357g',0,'357g',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('362a',0,'362a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377l',0,'377l',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('381a',0,'381a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4400',0,'4400',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4406',0,'4406',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('4408',0,'4408',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440a',0,'440a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440n',0,'440n',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440p',0,'440p',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440v',0,'440v',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440w',0,'440w',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('440x',0,'440x',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490a',0,'490a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490l',0,'490l',4,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490v',0,'490v',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('490x',0,'490x',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('504a',0,'504a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505a',0,'505a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505g',0,'505g',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505r',0,'505r',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505t',0,'505t',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('505u',0,'505u',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506a',0,'506a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506b',0,'506b',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506c',0,'506c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506d',0,'506d',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506e',0,'506e',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('506u',0,'506u',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('507b',0,'507b',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('508a',0,'508a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510a',0,'510a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510b',0,'510b',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510c',0,'510c',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('510x',0,'510x',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('511a',0,'511a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514m',0,'514m',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('514u',0,'514u',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('515a',0,'515a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('516a',0,'516a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('518a',0,'518a',9,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520a',0,'520a',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520b',0,'520b',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('520u',0,'520u',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('522a',0,'522a',10,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('524a',0,'524a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('525a',0,'525a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530a',0,'530a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530b',0,'530b',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530c',0,'530c',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530d',0,'530d',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('530u',0,'530u',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('5337',0,'5337',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533a',0,'533a',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('533c',0,'533c',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('546a',0,'546a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600f',0,'600f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600h',0,'600h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600k',0,'600k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600l',0,'600l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600m',0,'600m',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600n',0,'600n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600o',0,'600o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600p',0,'600p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600r',0,'600r',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600s',0,'600s',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600t',0,'600t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600u',0,'600u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600y',0,'600y',7,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('600z',0,'600z',7,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('648a',0,'648a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('650c',0,'600c',10,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('651a',0,'651a',10,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('655a',0,'655a',7,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700a',0,'700a',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700b',0,'700b',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700c',0,'700c',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700d',0,'700d',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700e',0,'700e',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700f',0,'700f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700g',0,'700g',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700h',0,'700h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700k',0,'700k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700l',0,'700l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700n',0,'700n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700o',0,'700o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700p',0,'700p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700q',0,'700q',5,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700s',0,'700s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700t',0,'700t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('700u',0,'700u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710a',0,'710a',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710b',0,'710b',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710c',0,'710c',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710d',0,'710d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710e',0,'710e',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710f',0,'710f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710h',0,'710h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710k',0,'710k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710l',0,'710l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710n',0,'710n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710o',0,'710o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710p',0,'710p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710q',0,'710q',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710s',0,'710s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710t',0,'710t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('710u',0,'710u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711a',0,'711a',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711b',0,'711b',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711c',0,'711c',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711d',0,'711d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711e',0,'711e',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711f',0,'711f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711h',0,'711h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711k',0,'711k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711l',0,'711l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711n',0,'711n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711p',0,'711p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711q',0,'711q',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711s',0,'711s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711t',0,'711t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('711u',0,'711u',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730a',0,'730a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730d',0,'730d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730f',0,'730f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730h',0,'730h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730k',0,'730k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730l',0,'730l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730n',0,'730n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730o',0,'730o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730p',0,'730p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('730s',0,'730s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800f',0,'800f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800h',0,'800h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800k',0,'800k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800l',0,'800l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800n',0,'800n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800o',0,'800o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800p',0,'800p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800s',0,'800s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('800t',0,'800t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810d',0,'810d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810f',0,'810f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810k',0,'810k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810l',0,'810l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810n',0,'810n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810o',0,'810o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810p',0,'810p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810s',0,'810s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('810t',0,'810t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811d',0,'811d',6,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811f',0,'811f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811h',0,'811h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811k',0,'811k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811n',0,'811n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811p',0,'811p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811s',0,'811s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('811t',0,'811t',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830a',0,'830a',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830d',0,'830d',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830f',0,'830f',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830h',0,'830h',3,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830k',0,'830k',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830l',0,'830l',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830n',0,'830n',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830o',0,'830o',2,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830p',0,'830p',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('830s',0,'830s',1,0,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('852a',0,'852a',4,0,'ita');


INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',1,'100a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100m',0,'100m',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'100q',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',1,'110a',2,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110m',0,'110m',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',1,'111a',2,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',1,'130a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('150a',1,'150a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('151a',1,'151a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('153a',1,'153a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('155a',1,'155a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('180a',1,'180a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368d',0,'368d',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368s',0,'368s',2,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368t',0,'368t',2,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370a',0,'370a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370b',0,'370b',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370c',0,'370c',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370e',0,'370e',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370f',0,'370f',3,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('371a',0,'371a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('372a',0,'372a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('373a',0,'373a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('374a',0,'374a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('375a',0,'375a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376a',0,'376a',3,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376s',0,'376s',3,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376t',0,'376t',3,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500a',0,'500a',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500i',0,'500i',1,1,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('678a',0,'678a',1,1,'eng');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',1,'100a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100m',0,'100m',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'100q',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',1,'110a',2,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110m',0,'110m',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',1,'111a',2,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',1,'130a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('150a',1,'150a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('151a',1,'151a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('153a',1,'153a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('155a',1,'155a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('180a',1,'180a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368d',0,'368d',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368s',0,'368s',2,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368t',0,'368t',2,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370a',0,'370a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370b',0,'370b',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370c',0,'370c',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370e',0,'370e',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370f',0,'370f',3,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('371a',0,'371a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('372a',0,'372a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('373a',0,'373a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('374a',0,'374a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('375a',0,'375a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376a',0,'376a',3,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376s',0,'376s',3,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376t',0,'376t',3,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500a',0,'500a',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500i',0,'500i',1,1,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('678a',0,'678a',1,1,'spa');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',1,'100a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100m',0,'100m',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'100q',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',1,'110a',2,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110m',0,'110m',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',1,'111a',2,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',1,'130a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('150a',1,'150a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('151a',1,'151a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('153a',1,'153a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('155a',1,'155a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('180a',1,'180a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368d',0,'368d',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368s',0,'368s',2,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368t',0,'368t',2,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370a',0,'370a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370b',0,'370b',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370c',0,'370c',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370e',0,'370e',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370f',0,'370f',3,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('371a',0,'371a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('372a',0,'372a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('373a',0,'373a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('374a',0,'374a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('375a',0,'375a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376a',0,'376a',3,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376s',0,'376s',3,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376t',0,'376t',3,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500a',0,'500a',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500i',0,'500i',1,1,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('678a',0,'678a',1,1,'hun');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('Leader',1,'Leader',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('001',1,'001',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100a',1,'100a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100c',0,'100c',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100d',0,'100d',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100m',0,'100m',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('100q',0,'100q',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110a',1,'110a',2,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('110m',0,'110m',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('111a',1,'111a',2,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('130a',1,'130a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('150a',1,'150a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('151a',1,'151a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('153a',1,'153a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('155a',1,'155a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('180a',1,'180a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368d',0,'368d',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368s',0,'368s',2,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('368t',0,'368t',2,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370a',0,'370a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370b',0,'370b',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370c',0,'370c',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370e',0,'370e',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('370f',0,'370f',3,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('371a',0,'371a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('372a',0,'372a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('373a',0,'373a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('374a',0,'374a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('375a',0,'375a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376a',0,'376a',3,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376s',0,'376s',3,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('376t',0,'376t',3,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('377a',0,'377a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500a',0,'500a',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('500i',0,'500i',1,1,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('678a',0,'678a',1,1,'ita');


INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[source=VIAF]',0,'actorID[@lido:source=\"VIAF\"]',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[type=local]',0,'actorID[@lido:type=\"local\"]',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole',0,'actorInRole',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole/actor',0,'actorInRole/actor',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('administrativeMetadata/recordWrap',0,'administrativeMetadata/recordWrap',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('classification/term',0,'descriptiveMetadata/objectClassificationWrap/classificationWrap/classification/term',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('culture/term',0,'culture/term',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('date/earliestDate',0,'date/earliestDate',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('descriptiveNoteValue',0,'descriptiveNoteValue',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayActorInRole',0,'displayActorInRole',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayMaterialsTech',0,'displayMaterialsTech',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayObjectMeasurements',0,'displayObjectMeasurements',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayPlace',0,'displayPlace',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayEdition',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayEdition',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayState',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayState',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventDescriptionSet',0,'eventDescriptionSet',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventMaterialsTech',0,'eventMaterialsTech',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventPlace',0,'eventPlace',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventSet/event',0,'descriptiveMetadata/eventWrap/eventSet/event',2,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('gml/Point/pos',0,'gml/Point/pos',3,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('inscriptionDescription/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/inscriptionsWrap/inscriptions/inscriptionDescription/descriptiveNoteValue',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:administrativeMetadata/lido',1,'identifier',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:appellationValue',1,'title',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:identifierComplexType',0,'lidoRecID',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:term',1,'lido term',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:type',1,'lido type',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementType',0,'measurementType',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementUnit',0,'measurementUnitlidoRecID',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementValue',0,'measurementValue',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nameActorSet/appellationValue[pref=preferred]',0,'nameActorSet/appellationValue[@lido:pref=\"preferred\"]',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('namePlaceSet/appellationValue',0,'namePlaceSet/appellationValue',3,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nationalityActor/term',0,'nationalityActor/term',3,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectDescriptionSet/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/objectDescriptionWrap/objectDescriptionSet/descriptiveNoteValue',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/formatMeasurements',0,'objectMeasurements/formatMeasurements',5,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/measurementsSet',0,'objectMeasurements/measurementsSet',5,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/scaleMeasurements',0,'objectMeasurements/scaleMeasurements',5,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/shapeMeasurements',0,'objectMeasurements/shapeMeasurements',5,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurementsWrap/objectMeasurementsSet',0,'descriptiveMetadata/objectIdentificationWrap/objectMeasurementsWrap/objectMeasurementsSet',5,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectWorkType/term',0,'descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('place',0,'place',3,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordID',0,'recordID',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordInfoSet/recordInfoLink',0,'recordInfoSet/recordInfoLink',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordRights/rightsHolder',0,'recordRights/rightsHolder',6,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordSource',0,'recordSource',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryLocation',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryLocation',3,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryName',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryName',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceRepresentation/linkResource',0,'resourceRepresentation/linkResource',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceType/term',0,'resourceType/term',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceWrap/resourceSet',0,'administrativeMetadata/resourceWrap/resourceSet',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsResource',0,'rightsResource',6,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsType',0,'rightsType',6,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsWorkSet/creditLine',0,'administrativeMetadata/rightsWorkWrap/rightsWorkSet/creditLine',6,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('roleActor/term',0,'roleActor/term',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('titleSet/appellationValue',0,'descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue',1,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/earliestDate',0,'vitalDatesActor/earliestDate',4,2,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/latestDate',0,'vitalDatesActor/latestDate',4,2,'eng');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[source=VIAF]',0,'actorID[@lido:source=\"VIAF\"]',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[type=local]',0,'actorID[@lido:type=\"local\"]',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole',0,'actorInRole',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole/actor',0,'actorInRole/actor',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('administrativeMetadata/recordWrap',0,'administrativeMetadata/recordWrap',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('classification/term',0,'descriptiveMetadata/objectClassificationWrap/classificationWrap/classification/term',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('culture/term',0,'culture/term',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('date/earliestDate',0,'date/earliestDate',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('descriptiveNoteValue',0,'descriptiveNoteValue',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayActorInRole',0,'displayActorInRole',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayMaterialsTech',0,'displayMaterialsTech',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayObjectMeasurements',0,'displayObjectMeasurements',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayPlace',0,'displayPlace',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayEdition',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayEdition',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayState',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayState',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventDescriptionSet',0,'eventDescriptionSet',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventMaterialsTech',0,'eventMaterialsTech',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventPlace',0,'eventPlace',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventSet/event',0,'descriptiveMetadata/eventWrap/eventSet/event',2,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('gml/Point/pos',0,'gml/Point/pos',3,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('inscriptionDescription/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/inscriptionsWrap/inscriptions/inscriptionDescription/descriptiveNoteValue',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:administrativeMetadata/lido',1,'identifier',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:appellationValue',1,'title',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:identifierComplexType',0,'lidoRecID',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:term',1,'lido term',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:type',1,'lido type',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementType',0,'measurementType',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementUnit',0,'measurementUnitlidoRecID',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementValue',0,'measurementValue',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nameActorSet/appellationValue[pref=preferred]',0,'nameActorSet/appellationValue[@lido:pref=\"preferred\"]',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('namePlaceSet/appellationValue',0,'namePlaceSet/appellationValue',3,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nationalityActor/term',0,'nationalityActor/term',3,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectDescriptionSet/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/objectDescriptionWrap/objectDescriptionSet/descriptiveNoteValue',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/formatMeasurements',0,'objectMeasurements/formatMeasurements',5,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/measurementsSet',0,'objectMeasurements/measurementsSet',5,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/scaleMeasurements',0,'objectMeasurements/scaleMeasurements',5,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/shapeMeasurements',0,'objectMeasurements/shapeMeasurements',5,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurementsWrap/objectMeasurementsSet',0,'descriptiveMetadata/objectIdentificationWrap/objectMeasurementsWrap/objectMeasurementsSet',5,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectWorkType/term',0,'descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('place',0,'place',3,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordID',0,'recordID',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordInfoSet/recordInfoLink',0,'recordInfoSet/recordInfoLink',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordRights/rightsHolder',0,'recordRights/rightsHolder',6,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordSource',0,'recordSource',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryLocation',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryLocation',3,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryName',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryName',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceRepresentation/linkResource',0,'resourceRepresentation/linkResource',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceType/term',0,'resourceType/term',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceWrap/resourceSet',0,'administrativeMetadata/resourceWrap/resourceSet',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsResource',0,'rightsResource',6,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsType',0,'rightsType',6,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsWorkSet/creditLine',0,'administrativeMetadata/rightsWorkWrap/rightsWorkSet/creditLine',6,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('roleActor/term',0,'roleActor/term',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('titleSet/appellationValue',0,'descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue',1,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/earliestDate',0,'vitalDatesActor/earliestDate',4,2,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/latestDate',0,'vitalDatesActor/latestDate',4,2,'spa');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[source=VIAF]',0,'actorID[@lido:source=\"VIAF\"]',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[type=local]',0,'actorID[@lido:type=\"local\"]',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole',0,'actorInRole',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole/actor',0,'actorInRole/actor',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('administrativeMetadata/recordWrap',0,'administrativeMetadata/recordWrap',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('classification/term',0,'descriptiveMetadata/objectClassificationWrap/classificationWrap/classification/term',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('culture/term',0,'culture/term',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('date/earliestDate',0,'date/earliestDate',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('descriptiveNoteValue',0,'descriptiveNoteValue',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayActorInRole',0,'displayActorInRole',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayMaterialsTech',0,'displayMaterialsTech',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayObjectMeasurements',0,'displayObjectMeasurements',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayPlace',0,'displayPlace',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayEdition',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayEdition',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayState',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayState',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventDescriptionSet',0,'eventDescriptionSet',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventMaterialsTech',0,'eventMaterialsTech',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventPlace',0,'eventPlace',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventSet/event',0,'descriptiveMetadata/eventWrap/eventSet/event',2,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('gml/Point/pos',0,'gml/Point/pos',3,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('inscriptionDescription/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/inscriptionsWrap/inscriptions/inscriptionDescription/descriptiveNoteValue',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:administrativeMetadata/lido',1,'identifier',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:appellationValue',1,'title',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:identifierComplexType',0,'lidoRecID',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:term',1,'lido term',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:type',1,'lido type',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementType',0,'measurementType',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementUnit',0,'measurementUnitlidoRecID',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementValue',0,'measurementValue',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nameActorSet/appellationValue[pref=preferred]',0,'nameActorSet/appellationValue[@lido:pref=\"preferred\"]',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('namePlaceSet/appellationValue',0,'namePlaceSet/appellationValue',3,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nationalityActor/term',0,'nationalityActor/term',3,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectDescriptionSet/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/objectDescriptionWrap/objectDescriptionSet/descriptiveNoteValue',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/formatMeasurements',0,'objectMeasurements/formatMeasurements',5,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/measurementsSet',0,'objectMeasurements/measurementsSet',5,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/scaleMeasurements',0,'objectMeasurements/scaleMeasurements',5,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/shapeMeasurements',0,'objectMeasurements/shapeMeasurements',5,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurementsWrap/objectMeasurementsSet',0,'descriptiveMetadata/objectIdentificationWrap/objectMeasurementsWrap/objectMeasurementsSet',5,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectWorkType/term',0,'descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('place',0,'place',3,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordID',0,'recordID',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordInfoSet/recordInfoLink',0,'recordInfoSet/recordInfoLink',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordRights/rightsHolder',0,'recordRights/rightsHolder',6,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordSource',0,'recordSource',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryLocation',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryLocation',3,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryName',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryName',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceRepresentation/linkResource',0,'resourceRepresentation/linkResource',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceType/term',0,'resourceType/term',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceWrap/resourceSet',0,'administrativeMetadata/resourceWrap/resourceSet',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsResource',0,'rightsResource',6,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsType',0,'rightsType',6,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsWorkSet/creditLine',0,'administrativeMetadata/rightsWorkWrap/rightsWorkSet/creditLine',6,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('roleActor/term',0,'roleActor/term',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('titleSet/appellationValue',0,'descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue',1,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/earliestDate',0,'vitalDatesActor/earliestDate',4,2,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/latestDate',0,'vitalDatesActor/latestDate',4,2,'hun');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[source=VIAF]',0,'actorID[@lido:source=\"VIAF\"]',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorID[type=local]',0,'actorID[@lido:type=\"local\"]',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole',0,'actorInRole',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('actorInRole/actor',0,'actorInRole/actor',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('administrativeMetadata/recordWrap',0,'administrativeMetadata/recordWrap',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('classification/term',0,'descriptiveMetadata/objectClassificationWrap/classificationWrap/classification/term',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('culture/term',0,'culture/term',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('date/earliestDate',0,'date/earliestDate',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('descriptiveNoteValue',0,'descriptiveNoteValue',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayActorInRole',0,'displayActorInRole',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayMaterialsTech',0,'displayMaterialsTech',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayObjectMeasurements',0,'displayObjectMeasurements',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayPlace',0,'displayPlace',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayEdition',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayEdition',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('displayStateEditionWrap/displayState',0,'descriptiveMetadata/objectIdentificationWrap/displayStateEditionWrap/displayState',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventDescriptionSet',0,'eventDescriptionSet',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventMaterialsTech',0,'eventMaterialsTech',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventPlace',0,'eventPlace',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('eventSet/event',0,'descriptiveMetadata/eventWrap/eventSet/event',2,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('gml/Point/pos',0,'gml/Point/pos',3,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('inscriptionDescription/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/inscriptionsWrap/inscriptions/inscriptionDescription/descriptiveNoteValue',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:administrativeMetadata/lido',1,'identifier',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:appellationValue',1,'title',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:identifierComplexType',0,'lidoRecID',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:term',1,'lido term',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('lido:type',1,'lido type',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementType',0,'measurementType',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementUnit',0,'measurementUnitlidoRecID',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('measurementValue',0,'measurementValue',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nameActorSet/appellationValue[pref=preferred]',0,'nameActorSet/appellationValue[@lido:pref=\"preferred\"]',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('namePlaceSet/appellationValue',0,'namePlaceSet/appellationValue',3,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('nationalityActor/term',0,'nationalityActor/term',3,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectDescriptionSet/descriptiveNoteValue',0,'descriptiveMetadata/objectIdentificationWrap/objectDescriptionWrap/objectDescriptionSet/descriptiveNoteValue',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/formatMeasurements',0,'objectMeasurements/formatMeasurements',5,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/measurementsSet',0,'objectMeasurements/measurementsSet',5,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/scaleMeasurements',0,'objectMeasurements/scaleMeasurements',5,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurements/shapeMeasurements',0,'objectMeasurements/shapeMeasurements',5,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectMeasurementsWrap/objectMeasurementsSet',0,'descriptiveMetadata/objectIdentificationWrap/objectMeasurementsWrap/objectMeasurementsSet',5,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('objectWorkType/term',0,'descriptiveMetadata/objectClassificationWrap/objectWorkTypeWrap/objectWorkType/term',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('place',0,'place',3,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordID',0,'recordID',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordInfoSet/recordInfoLink',0,'recordInfoSet/recordInfoLink',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordRights/rightsHolder',0,'recordRights/rightsHolder',6,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('recordSource',0,'recordSource',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryLocation',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryLocation',3,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('repositorySet/repositoryName',0,'descriptiveMetadata/objectIdentificationWrap/repositoryWrap/repositorySet/repositoryName',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceRepresentation/linkResource',0,'resourceRepresentation/linkResource',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceType/term',0,'resourceType/term',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('resourceWrap/resourceSet',0,'administrativeMetadata/resourceWrap/resourceSet',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsResource',0,'rightsResource',6,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsType',0,'rightsType',6,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('rightsWorkSet/creditLine',0,'administrativeMetadata/rightsWorkWrap/rightsWorkSet/creditLine',6,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('roleActor/term',0,'roleActor/term',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('titleSet/appellationValue',0,'descriptiveMetadata/objectIdentificationWrap/titleWrap/titleSet/appellationValue',1,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/earliestDate',0,'vitalDatesActor/earliestDate',4,2,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('vitalDatesActor/latestDate',0,'vitalDatesActor/latestDate',4,2,'ita');


INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:identifier',1,'dc:identifier',1,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('identifier',1,'identifier',1,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:contributor',0,'dc:contributor',5,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:coverage',0,'dc:coverage',10,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:creator',0,'dc:creator',3,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:date',0,'dc:date',3,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:description',0,'dc:description',3,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:format',0,'dc:format',3,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:language',0,'dc:language',2,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:publisher',0,'dc:publisher',3,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:source',0,'dc:source',1,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:subject',0,'dc:subject',7,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:title',1,'dc:title',1,3,'eng');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:type',0,'dc:type',3,3,'eng');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:identifier',1,'dc:identifier',1,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('identifier',1,'identifier',1,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:contributor',0,'dc:contributor',5,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:coverage',0,'dc:coverage',10,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:creator',0,'dc:creator',3,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:date',0,'dc:date',3,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:description',0,'dc:description',3,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:format',0,'dc:format',3,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:language',0,'dc:language',2,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:publisher',0,'dc:publisher',3,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:source',0,'dc:source',1,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:subject',0,'dc:subject',7,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:title',1,'dc:title',1,3,'spa');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:type',0,'dc:type',3,3,'spa');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:identifier',1,'dc:identifier',1,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('identifier',1,'identifier',1,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:contributor',0,'dc:contributor',5,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:coverage',0,'dc:coverage',10,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:creator',0,'dc:creator',3,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:date',0,'dc:date',3,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:description',0,'dc:description',3,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:format',0,'dc:format',3,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:language',0,'dc:language',2,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:publisher',0,'dc:publisher',3,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:source',0,'dc:source',1,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:subject',0,'dc:subject',7,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:title',1,'dc:title',1,3,'hun');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:type',0,'dc:type',3,3,'hun');

INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:identifier',1,'dc:identifier',1,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('identifier',1,'identifier',1,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:contributor',0,'dc:contributor',5,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:coverage',0,'dc:coverage',10,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:creator',0,'dc:creator',3,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:date',0,'dc:date',3,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:description',0,'dc:description',3,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:format',0,'dc:format',3,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:language',0,'dc:language',2,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:publisher',0,'dc:publisher',3,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:source',0,'dc:source',1,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:subject',0,'dc:subject',7,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:title',1,'dc:title',1,3,'ita');
INSERT INTO `xml_tag` (`xml_tag_id`,`xml_tag_mandatory`,`xml_tag_description`,`template_entity_code`,`xml_tag_type_code`,`language`) VALUES ('dc:type',0,'dc:type',3,3,'ita');

