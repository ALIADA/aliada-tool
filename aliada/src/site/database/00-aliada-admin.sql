CREATE DATABASE IF NOT EXISTS `aliada` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'aliada'@'%' IDENTIFIED BY '******';

GRANT ALL PRIVILEGES ON * . * TO 'aliada'@'%' IDENTIFIED BY '******' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;

CREATE TABLE `aliada`.`t_user_role` (
`user_role_code` INT NOT NULL ,
`user_role` VARCHAR( 32 ) NOT NULL ,
PRIMARY KEY ( `user_role_code` )
) ENGINE = InnoDB ;

 CREATE TABLE `aliada`.`t_user_type` (
`user_type_code` INT NOT NULL ,
`user_type` VARCHAR( 32 ) NOT NULL ,
PRIMARY KEY ( `user_type_code` )
) ENGINE = InnoDB ;

 CREATE TABLE `aliada`.`user` (
`user_name` VARCHAR( 20 ) NOT NULL ,
`user_password` VARCHAR( 32 ) NOT NULL ,
`user_email` VARCHAR( 128 ) NOT NULL ,
`user_type_code` INT NOT NULL ,
`user_role_code` INT NOT NULL ,
PRIMARY KEY ( `user_name` )
) ENGINE = InnoDB ;

 

 -- phpMyAdmin SQL Dump
-- version 2.11.11.3
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-06-2014 a las 12:13:33
-- Versión del servidor: 5.0.95
-- Versión de PHP: 5.1.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `aliada`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organisation`
--

CREATE TABLE IF NOT EXISTS `organisation` (
  `organisation_name` varchar(32) NOT NULL,
  `organisation_path` varchar(128) NOT NULL,
  `organisation_uri_domain` varchar(128) NOT NULL,
  `organisation_uri_resource` varchar(128) NOT NULL,
  `organisation_logo` varchar(128) NOT NULL,
  `organisation_catalog_url` varchar(128) NOT NULL,
  PRIMARY KEY  (`organisation_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `organisation`
--


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

CREATE TABLE IF NOT EXISTS `template` (
  `template_id` int(11) NOT NULL,
  `template_name` varchar(32) NOT NULL,
  `template_description` varchar(128) default NULL,
  PRIMARY KEY  (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `template`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template_xml_tag`
--

CREATE TABLE IF NOT EXISTS `template_xml_tag` (
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

CREATE TABLE IF NOT EXISTS `t_character_set` (
  `character_set_code` int(11) NOT NULL,
  `character_set_name` varchar(32) NOT NULL,
  `character_set_description` varchar(128) default NULL,
  PRIMARY KEY  (`character_set_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_character_set`
--

INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`) VALUES
(0, 'MARC standard', NULL),
(1, 'AMICUS', NULL),
(2, 'Latin1', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_format`
--

CREATE TABLE IF NOT EXISTS `t_file_format` (
  `file_format_code` int(11) NOT NULL,
  `file_format_name` varchar(32) NOT NULL,
  `file_format_description` varchar(128) default NULL,
  PRIMARY KEY  (`file_format_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_file_format`
--

INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`) VALUES
(0, 'XML', 'XML file');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_type`
--

CREATE TABLE IF NOT EXISTS `t_file_type` (
  `file_type_code` int(11) NOT NULL,
  `file_type_name` varchar(32) NOT NULL,
  `file_type_description` varchar(128) default NULL,
  PRIMARY KEY  (`file_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_file_type`
--

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`) VALUES
(0, 'Bibliographic', NULL),
(1, 'Authority', NULL),
(2, 'Museum Resource', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_metadata_scheme`
--

CREATE TABLE IF NOT EXISTS `t_metadata_scheme` (
  `metadata_code` int(11) NOT NULL,
  `metadata_name` varchar(32) NOT NULL,
  `metadata_description` varchar(128) default NULL,
  PRIMARY KEY  (`metadata_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_metadata_scheme`
--

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`) VALUES
(0, 'MARC21', NULL),
(1, 'LIDO', NULL),
(2, 'Dublin Core', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_profile_type`
--

CREATE TABLE IF NOT EXISTS `t_profile_type` (
  `profile_code` int(11) NOT NULL,
  `profile_name` varchar(32) NOT NULL,
  `profile_description` varchar(128) default NULL,
  PRIMARY KEY  (`profile_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_profile_type`
--

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`) VALUES
(0, 'ILS', NULL),
(1, 'TMS', NULL),
(2, 'Drupal', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_role`
--

CREATE TABLE IF NOT EXISTS `t_user_role` (
  `user_role_code` int(11) NOT NULL,
  `user_role` varchar(32) NOT NULL,
  PRIMARY KEY  (`user_role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_user_role`
--

INSERT INTO `t_user_role` (`user_role_code`, `user_role`) VALUES
(0, 'Basic'),
(1, 'Administrator');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_type`
--

CREATE TABLE IF NOT EXISTS `t_user_type` (
  `user_type_code` int(11) NOT NULL,
  `user_type` varchar(32) NOT NULL,
  PRIMARY KEY  (`user_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_user_type`
--

INSERT INTO `t_user_type` (`user_type_code`, `user_type`) VALUES
(0, 'Basic'),
(1, 'Advanced');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_xml_tag_type`
--

CREATE TABLE IF NOT EXISTS `t_xml_tag_type` (
  `xml_tag_type_code` int(11) NOT NULL,
  `xml_tag_type_name` varchar(32) NOT NULL,
  `xml_tag_type_description` varchar(128) default NULL,
  PRIMARY KEY  (`xml_tag_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `t_xml_tag_type`
--

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES
(0, 'MARC21 Bibliographic', NULL),
(1, 'MARC21 Authority', NULL),
(2, 'LIDO', NULL),
(3, 'DC', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(32) NOT NULL,
  `user_email` varchar(128) NOT NULL,
  `user_type_code` int(11) NOT NULL,
  `user_role_code` int(11) NOT NULL,
  PRIMARY KEY  (`user_name`),
  KEY `user_type_code` (`user_type_code`),
  KEY `user_role_code` (`user_role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `user`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `xml_tag`
--

CREATE TABLE IF NOT EXISTS `xml_tag` (
  `xml_tag_id` varchar(32) NOT NULL,
  `xml_tag_mandatory` tinyint(1) NOT NULL,
  `xml_tag_description` varchar(128) NOT NULL,
  `xml_tag_type_code` int(11) NOT NULL,
  PRIMARY KEY  (`xml_tag_id`),
  KEY `xml_tag_type_code` (`xml_tag_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcar la base de datos para la tabla `xml_tag`
--

INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES
('001', 0, '', 0),
('040', 0, '', 0),
('100', 0, '', 0),
('245', 1, '', 0);

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `profile_ibfk_5` FOREIGN KEY (`character_set_code`) REFERENCES `t_character_set` (`character_set_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`profile_type_code`) REFERENCES `t_profile_type` (`profile_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_2` FOREIGN KEY (`metadata_scheme_code`) REFERENCES `t_metadata_scheme` (`metadata_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_3` FOREIGN KEY (`file_type_code`) REFERENCES `t_file_type` (`file_type_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `profile_ibfk_4` FOREIGN KEY (`file_format_code`) REFERENCES `t_file_format` (`file_format_code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `template_xml_tag`
--
ALTER TABLE `template_xml_tag`
  ADD CONSTRAINT `template_xml_tag_ibfk_2` FOREIGN KEY (`xml_tag_id`) REFERENCES `xml_tag` (`xml_tag_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `template_xml_tag_ibfk_1` FOREIGN KEY (`template_id`) REFERENCES `template` (`template_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`user_role_code`) REFERENCES `t_user_role` (`user_role_code`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_type_code`) REFERENCES `t_user_type` (`user_type_code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `xml_tag`
--
ALTER TABLE `xml_tag`
  ADD CONSTRAINT `xml_tag_ibfk_1` FOREIGN KEY (`xml_tag_type_code`) REFERENCES `t_xml_tag_type` (`xml_tag_type_code`) ON DELETE CASCADE ON UPDATE CASCADE;