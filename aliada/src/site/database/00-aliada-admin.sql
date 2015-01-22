CREATE DATABASE IF NOT EXISTS `aliada` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'aliada'@'%' IDENTIFIED BY 'aliada';

GRANT ALL PRIVILEGES ON * . * TO 'aliada'@'%' IDENTIFIED BY 'aliada' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

-- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generaci�n: 12-06-2014 a las 12:13:33
-- Versi�n del servidor: 5.0.95
-- Versi�n de PHP: 5.1.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `aliada`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organisation`
--

CREATE TABLE IF NOT EXISTS `aliada`.`organisation` (
    `organisationId` int(11) NOT NULL AUTO_INCREMENT,
  	`organisation_name` varchar(32) NOT NULL,
  	`organisation_path` varchar(128) NOT NULL,
  	`organisation_logo` BLOB,
	`organisation_catalog_url` varchar(128) NOT NULL,
	`aliada_ontology`  VARCHAR( 245 ) default NULL,
	`linking_config_file`  VARCHAR( 245 ) default NULL, 
	`tmp_dir`  VARCHAR( 245 ) default NULL,
	`linking_client_app_bin_dir`  VARCHAR( 245 ) default NULL, 
	`linking_client_app_user`  VARCHAR( 245 ) default NULL, 
	`sparql_endpoint_uri`  VARCHAR( 245 ) default NULL, 
	`sparql_endpoint_login`  VARCHAR( 32 ) default NULL, 
	`sparql_endpoint_password`  VARCHAR( 32 ) default NULL, 
	`store_ip`  VARCHAR( 245 ) default NULL, 
	`store_sql_port`  INT default 1111, 
	`sql_login`  VARCHAR( 32 ) default NULL, 
	`sql_password`  VARCHAR( 32 ) default NULL, 
	`isql_command_path`  VARCHAR( 245 ) default NULL, 
	`isql_commands_file_default`  VARCHAR( 245 ) default NULL, 
	`public_sparql_endpoint_uri` VARCHAR(45) default NULL,
PRIMARY KEY ( `organisationId` )
) ENGINE = InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `aliada`.`graph` (
    `graphId` int(11) NOT NULL AUTO_INCREMENT,
	`graph_uri`  VARCHAR( 245 ) default NULL, 
	`dataset_base`  VARCHAR( 245 ) default NULL, 
	`listening_host` VARCHAR(45) default NULL,
	`virtual_host` VARCHAR(45) default NULL,
	`organisationId` int(11) NOT NULL,
	`isql_commands_file`  VARCHAR( 245 ) default NULL, 
	FOREIGN KEY (organisationId) 
    REFERENCES organisation(organisationId) 
    ON DELETE CASCADE,
PRIMARY KEY ( `graphId` )
) ENGINE = InnoDB  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------


--
-- Estructura de tabla para la tabla `profile`
--

CREATE TABLE IF NOT EXISTS `aliada`.`profile` (
  `profile_id` int(11) NOT NULL AUTO_INCREMENT,
  `profile_name` varchar(32) NOT NULL,
  `profile_type_code` int(11) NOT NULL,
  `profile_description` varchar(128) NOT NULL,
  `metadata_scheme_code` int(11) NOT NULL,
  `file_type_code` int(11) NOT NULL,
  `file_format_code` int(11) NOT NULL,
  `character_set_code` int(11) NOT NULL,
  PRIMARY KEY  (`profile_id`),
  KEY `profile_type_code` (`profile_type_code`),
  KEY `metadata_scheme_code` (`metadata_scheme_code`),
  KEY `file_type_code` (`file_type_code`),
  KEY `file_format_code` (`file_format_code`),
  KEY `character_set_code` (`character_set_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `profile`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template`
--

CREATE TABLE IF NOT EXISTS `aliada`.`template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(32) NOT NULL,
  `template_description` varchar(128) default NULL,
  `file_type_code` int(11) NOT NULL,
  PRIMARY KEY  (`template_id`),
  KEY `fk_file_type_code_idx` (`file_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `template`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template_xml_tag`
--

CREATE TABLE IF NOT EXISTS `aliada`.`template_xml_tag` (
  `template_id` int(11) NOT NULL,
  `xml_tag_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`template_id`,`xml_tag_id`),
  KEY `xml_tag_id` (`xml_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `template_xml_tag`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_character_set`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_character_set` (
  `character_set_code` int(11) NOT NULL,
  `character_set_name` varchar(32) NOT NULL,
  `character_set_description` varchar(128) default NULL,
  PRIMARY KEY  (`character_set_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_format`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_file_format` (
  `file_format_code` int(11) NOT NULL,
  `file_format_name` varchar(32) NOT NULL,
  `file_format_description` varchar(128) default NULL,
  PRIMARY KEY  (`file_format_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_type`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_file_type` (
  `file_type_code` int(11) NOT NULL,
  `file_type_name` varchar(32) NOT NULL,
  `file_type_description` varchar(128) default NULL,
  `file_type_conversion_file` varchar(32) NOT NULL,
  PRIMARY KEY  (`file_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_metadata_scheme`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_metadata_scheme` (
  `metadata_code` int(11) NOT NULL,
  `metadata_name` varchar(32) NOT NULL,
  `metadata_description` varchar(128) default NULL,
  `metadata_conversion_file` varchar(32) NOT NULL,
  PRIMARY KEY  (`metadata_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_profile_type`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_profile_type` (
  `profile_code` int(11) NOT NULL,
  `profile_name` varchar(32) NOT NULL,
  `profile_description` varchar(128) default NULL,
  PRIMARY KEY  (`profile_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_role`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_user_role` (
  `user_role_code` int(11) NOT NULL,
  `user_role` varchar(32) NOT NULL,
  `user_role_description` varchar(128) default NULL,
  PRIMARY KEY  (`user_role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_type`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_user_type` (
  `user_type_code` int(11) NOT NULL,
  `user_type` varchar(32) NOT NULL,
  `user_type_description` varchar(128) default NULL,
  PRIMARY KEY  (`user_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_xml_tag_type`
--

CREATE TABLE IF NOT EXISTS `aliada`.`t_xml_tag_type` (
  `xml_tag_type_code` int(11) NOT NULL,
  `xml_tag_type_name` varchar(32) NOT NULL,
  `xml_tag_type_description` varchar(128) default NULL,
  PRIMARY KEY  (`xml_tag_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `aliada`.`user` (
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(150) NOT NULL,
  `user_email` varchar(128) NOT NULL,
  `user_type_code` int(11) NOT NULL,
  `user_role_code` int(11) NOT NULL,
  `organisationId` int(11) NOT NULL,
  PRIMARY KEY  (`user_name`),
  KEY `user_type_code` (`user_type_code`),
  KEY `user_role_code` (`user_role_code`),
  FOREIGN KEY (organisationId) REFERENCES organisation(organisationId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `xml_tag`
--

CREATE TABLE IF NOT EXISTS `aliada`.`xml_tag` (
  `xml_tag_id` varchar(32) NOT NULL,
  `xml_tag_mandatory` tinyint(1) NOT NULL,
  `xml_tag_description` varchar(128) NOT NULL,
  `xml_tag_type_code` int(11) NOT NULL,
  PRIMARY KEY  (`xml_tag_id`),
  KEY `xml_tag_type_code` (`xml_tag_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `aliada`.`t_external_dataset` (
  `external_dataset_code` int(11) NOT NULL,
  `external_dataset_name` varchar(32) NOT NULL,
  `external_dataset_description` varchar(128) default NULL,
  PRIMARY KEY  (`external_dataset_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `profile`
--
ALTER TABLE `aliada`.`profile`
  ADD CONSTRAINT `profile_ibfk_5` FOREIGN KEY (`character_set_code`) REFERENCES `t_character_set` (`character_set_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`profile_type_code`) REFERENCES `t_profile_type` (`profile_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_2` FOREIGN KEY (`metadata_scheme_code`) REFERENCES `t_metadata_scheme` (`metadata_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_3` FOREIGN KEY (`file_type_code`) REFERENCES `t_file_type` (`file_type_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_4` FOREIGN KEY (`file_format_code`) REFERENCES `t_file_format` (`file_format_code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `template_xml_tag`
--
ALTER TABLE `aliada`.`template_xml_tag`
  ADD CONSTRAINT `template_xml_tag_ibfk_2` FOREIGN KEY (`xml_tag_id`) REFERENCES `xml_tag` (`xml_tag_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `template_xml_tag_ibfk_1` FOREIGN KEY (`template_id`) REFERENCES `template` (`template_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `aliada`.`user`
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`user_role_code`) REFERENCES `t_user_role` (`user_role_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_ibfk_3` FOREIGN KEY (`user_type_code`) REFERENCES `t_user_type` (`user_type_code`) ON DELETE CASCADE ON UPDATE CASCADE;


--
-- Filtros para la tabla `template`
--
ALTER TABLE `aliada`.`template`
  ADD CONSTRAINT `fk_file_type_code` FOREIGN KEY (`file_type_code`) REFERENCES `t_file_type` (`file_type_code`) ON DELETE CASCADE ON UPDATE CASCADE;
  
--
-- Filtros para la tabla `xml_tag`
--
ALTER TABLE `aliada`.`xml_tag`
  ADD CONSTRAINT `xml_tag_ibfk_1` FOREIGN KEY (`xml_tag_type_code`) REFERENCES `t_xml_tag_type` (`xml_tag_type_code`) ON DELETE CASCADE ON UPDATE CASCADE;


--
-- Volcar la base de datos para la tabla `t_character_set`
--
INSERT INTO `aliada`.`t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`) VALUES
(0, 'MARC standard', NULL),
(1, 'AMICUS', NULL),
(2, 'Latin1', NULL);

--
-- Volcar la base de datos para la tabla `t_file_format`
--
INSERT INTO `aliada`.`t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`) VALUES
(0, 'XML', 'XML file');

--
-- Volcar la base de datos para la tabla `t_file_type`
--
INSERT INTO `aliada`.`t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`) VALUES
(0, 'Bibliographic', NULL, 'marc_bib.xsl'),
(1, 'Authority', NULL, 'marc_aut.xsl'),
(2, 'Museum Resource', NULL, 'lido.xsl');

--
-- Volcar la base de datos para la tabla `t_metadata_scheme`
--
INSERT INTO `aliada`.`t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`) VALUES
(0, 'marcxml', NULL, 'MARC21slim.xsd'),
(1, 'lido', NULL, 'lido-v1.0.xsd'),
(2, 'Dublin Core', NULL, 'xsd');

--
-- Volcar la base de datos para la tabla `t_profile_type`
--
INSERT INTO `aliada`.`t_profile_type` (`profile_code`, `profile_name`, `profile_description`) VALUES
(0, 'ILS', NULL),
(1, 'TMS', NULL),
(2, 'Drupal', NULL);

--
-- Volcar la base de datos para la tabla `t_user_role`
--
INSERT INTO `aliada`.`t_user_role` (`user_role_code`, `user_role`, `user_role_description`) VALUES
(0, 'Basic', NULL),
(1, 'Administrator', NULL);

--
-- Volcar la base de datos para la tabla `t_user_type`
--
INSERT INTO `aliada`.`t_user_type` (`user_type_code`, `user_type`, `user_type_description`) VALUES
(0, 'Basic', NULL),
(1, 'Advanced', NULL);

--
-- Volcar la base de datos para la tabla `t_xml_tag_type`
--
INSERT INTO `aliada`.`t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES
(0, 'MARC21 Bibliographic', NULL),
(1, 'MARC21 Authority', NULL),
(2, 'LIDO', NULL),
(3, 'DC', NULL);

--
-- Volcar la base de datos para la tabla `xml_tag`
--
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100a', '0', '100a', '0');
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100t', '0', '100t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110t', '0', '110t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111t', '0', '111t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100k', '0', '100k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700k', '0', '700k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100n', '0', '100n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700n', '0', '700n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100p', '0', '100p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700p', '0', '700p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700t', '0', '700t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110k', '0', '110k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710k', '0', '710k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110n', '0', '110n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710n', '0', '710n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110p', '0', '110p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710p', '0', '710p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710t', '0', '710t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111k', '0', '111k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711k', '0', '711k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111n', '0', '111n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711n', '0', '711n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111p', '0', '111p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711p', '0', '711p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711t', '0', '711t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130a', '0', '130a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730a', '0', '730a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830a', '0', '830a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130k', '0', '130k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730k', '0', '730k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830k', '0', '830k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130v', '0', '130v', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730n', '0', '730n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830n', '0', '830n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130p', '0', '130p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730p', '0', '730p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830p', '0', '830p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240a', '0', '240a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240n', '0', '240n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240p', '0', '240p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243a', '0', '243a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243k', '0', '243k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243n', '0', '243n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243p', '0', '243p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800k', '0', '800k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810k', '0', '810k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811k', '0', '811k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800n', '0', '800n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810n', '0', '810n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811n', '0', '811n', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800p', '0', '800p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810p', '0', '810p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811p', '0', '811p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800t', '0', '800t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810t', '0', '810t', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811t', '0', '811t', '0');    
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110d', '0', '110d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710d', '0', '710d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111d', '0', '111d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711d', '0', '711d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130d', '0', '130d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730d', '0', '730d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830d', '0', '830d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240d', '0', '240d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243d', '0', '243d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('648a', '0', '648a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810d', '0', '810d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811d', '0', '811d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('024a', '0', '024a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('024d', '0', '024d', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('024z', '0', '024z', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130h', '0', '130h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240h', '0', '240h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243h', '0', '243h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('336a', '0', '336a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('336b', '0', '336b', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700h', '0', '700h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710h', '0', '710h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711h', '0', '711h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730h', '0', '730h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800h', '0', '800h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811h', '0', '811h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830h', '0', '830h', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('045a', '0', '045a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('045b', '0', '045b', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('045c', '0', '045c', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('046k', '0', '046k', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('046l', '0', '046l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('046o', '0', '046o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('046p', '0', '046p', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100f', '0', '100f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110f', '0', '110f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111f', '0', '111f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130f', '0', '130f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240f', '0', '240f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700f', '0', '700f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710f', '0', '710f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711f', '0', '711f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730f', '0', '730f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800f', '0', '800f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810f', '0', '810f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811f', '0', '811f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830f', '0', '830f', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('008[35-38]', '0', '008[35-38]', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100l', '0', '100l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('110l', '0', '110l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('111l', '0', '111l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130l', '0', '130l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240l', '0', '240l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243l', '0', '243l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('377a', '0', '377a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('377l', '0', '377l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('546a', '0', '546a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700l', '0', '700l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710l', '0', '710l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711l', '0', '711l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730l', '0', '730l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800l', '0', '800l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810l', '0', '810l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830l', '0', '830l', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130o', '0', '130o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('130s', '0', '130s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240o', '0', '240o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('240s', '0', '240s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243o', '0', '243o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('243s', '0', '243s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('381a', '0', '381a', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700o', '0', '700o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700s', '0', '700s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710o', '0', '710o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('710s', '0', '710s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('711s', '0', '711s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730o', '0', '730o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('730s', '0', '730s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800o', '0', '800o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('800s', '0', '800s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810o', '0', '810o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('810s', '0', '810s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('811s', '0', '811s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830o', '0', '830o', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('830s', '0', '830s', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('001', '1', '001', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100(0-1)a-b-c-d-q-u', '0', '100(0-1)a-b-c-d-q-u', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700(0-1)a-b-c-d-q-u', '0', '700(0-1)a-b-c-d-q-u', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('100(3)a-c-d-g', '0', '100(3)a-c-d-g', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('700(3)a-c-d-g', '0', '700(3)a-c-d-g', '0');   
INSERT INTO `aliada`.`xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES ('852()a', '0', '852()a', '0');   

--
-- Volcar la base de datos para la tabla `t_external_dataset`
--
INSERT INTO `aliada`.`t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`) VALUES
(0, 'DBPedia', NULL),
(1, 'GeoNames', NULL);

--
-- Volcar la base de datos para las siguientes tablas 
--
INSERT INTO `aliada`.`organisation` (`organisation_name`, `organisation_path`, `organisation_catalog_url` , `aliada_ontology`, `linking_config_file`, `tmp_dir`, `linking_client_app_bin_dir`, `linking_client_app_user`, `sparql_endpoint_uri`, `sparql_endpoint_login`, `sparql_endpoint_password`, `store_ip`,  `store_sql_port`, `sql_login`, `sql_password`, `isql_command_path`, `isql_commands_file_default`, `public_sparql_endpoint_uri`)
 VALUES ('MFAB','/var/lib/tomcat7/upload','http://www.szepmuveszeti.hu/collection_browser_eng','http://aliada-project.eu/2014/aliada-ontology/' , '/home/aliada/links-discovery/config/linksdiscoveryTest.properties', '/home/aliada/tmp', '/home/aliada/links-discovery/bin/',
'aliada', 'http://data.szepmuveszeti.hu/sparql-auth', 'aliada_dev', 'aliada_dev', 'localhost', '1111', 'dba', 'dba', '/home/virtuoso/bin/isql-v', '/home/aliada/linked-data-server/config/isql_id_rewrite_rules_html_default.sql', 'http://data.szepmuveszeti.hu/sparql');

INSERT INTO `aliada`.`user` (`user_name`, `user_password`, `user_email`, `user_type_code`, `user_role_code`,`organisationId`) VALUES
('admin','1eh/F6TPx3EfCmKlAEeeppB1PHE+J16XaJIS/ig/78o+3yfNwSsso7YsldTyPnhW','admin@aliada.eu',1,1,1);

INSERT INTO `aliada`.`profile` VALUES (1,'MARC BIB',0,'MARC biblio',0,0,0,0),(2,'MARC AUT',0,'MARC authorities',0,1,0,0),(3,'LIDO',1,'LIDO MUSEUM',1,2,0,2);

INSERT INTO `aliada`.`template` VALUES (1,'MARC BIB','MARC biblio',0),(3,'LIDO','lido',2),(10,'Authorities','Authorities template',1);

INSERT INTO `aliada`.`template_xml_tag` VALUES (1,'024z'),(1,'130l'),(1,'700(0-1)a-b-c-d-q-u'),(1,'810k'),(3,'024d'),(3,'045c'),(3,'046l'),(3,'100(0-1)a-b-c-d-q-u'),(3,'100n'),(3,'110d'),(3,'110n'),(3,'111k'),(3,'111p'),(3,'111t'),(3,'240a'),(3,'240p'),(3,'240s'),(3,'243a'),(3,'336a'),(3,'546a'),(3,'700l'),(3,'700o'),(3,'700p'),(3,'710d'),(3,'710h'),(3,'710o'),(3,'710p'),(3,'710t'),(3,'711n'),(3,'730d'),(3,'730h'),(3,'800n'),(3,'810d'),(3,'830l');

INSERT INTO aliada.graph (graphId, graph_uri, dataset_base, listening_host, virtual_host, organisationId, isql_commands_file) VALUES (1, 'http://data.szepmuveszeti.hu', 'http://data.szepmuveszeti.hu', '*ini*', '*ini*', 1, '/home/aliada/linked-data-server/config/isql_id_rewrite_rules_html_mfab.sql');

