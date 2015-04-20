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
  `ckan_dataset_name` varchar(245) DEFAULT NULL,
  `dataset_long_desc` varchar(500) DEFAULT NULL,
  `dataset_source_url` varchar(245) DEFAULT NULL,
  `license_ckan_id` varchar(245) DEFAULT NULL,
  `license_url` varchar(245) DEFAULT NULL,
  `isql_commands_file_dataset` varchar(245) DEFAULT NULL,
  `dataset_web_page_root` varchar(245) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
  `isql_commands_file_subset_default` varchar(245) DEFAULT NULL
  `isql_commands_file_graph_dump` varchar(245) DEFAULT NULL,
  `virtuoso_http_server_root`  VARCHAR( 245 ) default NULL,
  `ckan_api_url` varchar(245) DEFAULT NULL,
  `ckan_api_key` varchar(245) DEFAULT NULL,
  `ckan_org_url` varchar(245) DEFAULT NULL,
  `dataset_author` varchar(245) NOT NULL,
  `isql_commands_file_dataset_creation` varchar(245) NOT NULL,
  `aliada_tool_hostname` varchar(245) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `template`
--

CREATE TABLE IF NOT EXISTS `template` (
  `template_id` int(11) NOT NULL,
  `template_name` varchar(32) NOT NULL,
  `template_description` varchar(128) DEFAULT NULL,
  `file_type_code` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

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
  `character_set_description` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_external_dataset`
--

CREATE TABLE IF NOT EXISTS `t_external_dataset` (
  `external_dataset_code` int(11) NOT NULL,
  `external_dataset_name` varchar(32) NOT NULL,
  `external_dataset_description` varchar(128) DEFAULT NULL,
  `external_dataset_homepage` varchar(128) DEFAULT NULL,
  `external_dataset_linkingfile` varchar(245) DEFAULT NULL,
  `external_dataset_linkingnumthreads` tinyint(1) DEFAULT '8',
  `external_dataset_linkingreloadtarget` tinyint(1) default 0,
  `external_dataset_linkingreloadsource` tinyint(1) default 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_format`
--

CREATE TABLE IF NOT EXISTS `t_file_format` (
  `file_format_code` int(11) NOT NULL,
  `file_format_name` varchar(32) NOT NULL,
  `file_format_description` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_file_type`
--

CREATE TABLE IF NOT EXISTS `t_file_type` (
  `file_type_code` int(11) NOT NULL,
  `file_type_name` varchar(32) NOT NULL,
  `file_type_description` varchar(128) DEFAULT NULL,
  `file_type_conversion_file` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_metadata_scheme`
--

CREATE TABLE IF NOT EXISTS `t_metadata_scheme` (
  `metadata_code` int(11) NOT NULL,
  `metadata_name` varchar(32) NOT NULL,
  `metadata_description` varchar(128) DEFAULT NULL,
  `metadata_conversion_file` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_profile_type`
--

CREATE TABLE IF NOT EXISTS `t_profile_type` (
  `profile_code` int(11) NOT NULL,
  `profile_name` varchar(32) NOT NULL,
  `profile_description` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_role`
--

CREATE TABLE IF NOT EXISTS `t_user_role` (
  `user_role_code` int(11) NOT NULL,
  `user_role` varchar(32) NOT NULL,
  `user_role_description` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_user_type`
--

CREATE TABLE IF NOT EXISTS `t_user_type` (
  `user_type_code` int(11) NOT NULL,
  `user_type` varchar(32) NOT NULL,
  `user_type_description` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `t_xml_tag_type`
--

CREATE TABLE IF NOT EXISTS `t_xml_tag_type` (
  `xml_tag_type_code` int(11) NOT NULL,
  `xml_tag_type_name` varchar(32) NOT NULL,
  `xml_tag_type_description` varchar(128) DEFAULT NULL
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
  `job_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `xml_tag`
--

CREATE TABLE IF NOT EXISTS `xml_tag` (
  `xml_tag_id` varchar(32) NOT NULL,
  `xml_tag_mandatory` tinyint(1) NOT NULL,
  `xml_tag_description` varchar(128) NOT NULL,
  `xml_tag_type_code` int(11) NOT NULL
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
  ADD PRIMARY KEY (`character_set_code`);

--
-- Indices de la tabla `t_external_dataset`
--
ALTER TABLE `t_external_dataset`
  ADD PRIMARY KEY (`external_dataset_code`);

--
-- Indices de la tabla `t_file_format`
--
ALTER TABLE `t_file_format`
  ADD PRIMARY KEY (`file_format_code`);

--
-- Indices de la tabla `t_file_type`
--
ALTER TABLE `t_file_type`
  ADD PRIMARY KEY (`file_type_code`);

--
-- Indices de la tabla `t_metadata_scheme`
--
ALTER TABLE `t_metadata_scheme`
  ADD PRIMARY KEY (`metadata_code`);

--
-- Indices de la tabla `t_profile_type`
--
ALTER TABLE `t_profile_type`
  ADD PRIMARY KEY (`profile_code`);

--
-- Indices de la tabla `t_user_role`
--
ALTER TABLE `t_user_role`
  ADD PRIMARY KEY (`user_role_code`);

--
-- Indices de la tabla `t_user_type`
--
ALTER TABLE `t_user_type`
  ADD PRIMARY KEY (`user_type_code`);

--
-- Indices de la tabla `t_xml_tag_type`
--
ALTER TABLE `t_xml_tag_type`
  ADD PRIMARY KEY (`xml_tag_type_code`);

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
  ADD PRIMARY KEY (`xml_tag_id`), ADD KEY `xml_tag_type_code` (`xml_tag_type_code`);

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

--
-- Volcado de datos para la tabla `organisation`
--

INSERT INTO `organisation` (`organisationId`,`org_name`,`org_path`,`org_catalog_url`, `org_description`,`org_home_page`, `aliada_ontology`,`tmp_dir`,`linking_client_app_bin_dir`,
`linking_client_app_user`,`store_ip`,`store_sql_port`,`sql_login`,`sql_password`,`isql_command_path`,`isql_commands_file_dataset_default`,  `isql_commands_file_subset_default`, 
`isql_commands_file_graph_dump`, `virtuoso_http_server_root`, `ckan_api_url`,`ckan_api_key`,`dataset_author`,`isql_commands_file_dataset_creation`,`aliada_tool_hostname`) VALUES 
(1,'artium','/usr/share/tomcat/upload/','http://aliada.artium.org', 'Basque Museum-Center of Contemporary Art', 'http://www.artium.org/', 'http://aliada-project.eu/2014/aliada-ontology#', '/home/aliada/tmp', '/home/aliada/links-discovery/bin/','linking','localhost',1111,'dba','dba','/home/virtuoso/bin/isql','/home/aliada/linked-data-server/config/isql_rewrite_rules_global.sql', '/home/aliada/linked-data-server/config/isql_rewrite_rules_subset_default.sql', '/home/aliada/ckan-datahub-page-creation/config/dump_one_graph_nt.sql', '/home/virtuoso/var/lib/virtuoso/vsp', 'http://datahub.io/api/action', '59465962-6eb1-4a06-8318-985fc4ffd1fc','Aliada Consortium','/home/aliada/bin/aliada_new_dataset.sql','aliada.scanbit.net');

--
-- Volcado de datos para la tabla `dataset`
--

INSERT INTO `dataset` (`datasetId`, `organisationId`, `dataset_desc`, `domain_name`, `uri_id_part`, `uri_doc_part`, `uri_def_part`, `uri_concept_part`, `uri_set_part`, `listening_host`, `virtual_host`, `sparql_endpoint_uri`, `sparql_endpoint_login`, `sparql_endpoint_password`, `public_sparql_endpoint_uri`, `dataset_author`, `ckan_dataset_name`, `dataset_long_desc`, `dataset_source_url`, `license_ckan_id`, `license_url`, `isql_commands_file_dataset`) VALUES(1, 1, 'artium_dataset', 'data.artium.org', 'id', 'doc', 'def', 'collections', 'set', '*ini*', '*ini*', 'http://localhost:8891/sparql-auth', 'aliada_dev', 'aliada_dev', 'http://localhost:8891/sparql', 'Aliada Consortium', 'datos-artium-org', 'Open linked data from the Library and Museum of ARTIUM', 'http://biblioteca.artium.org', 'cc-zero', 'http://creativecommons.org/publicdomain/zero/1.0/','/home/aliada/linked-data-server/config/isql_rewrite_rules_global.sql');

--
-- Volcado de datos para la tabla `profile`
--

INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(1, 'MARC BIB', 0, 'MARC biblio', 0, 0, 0, 0);
INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(2, 'MARC AUT', 0, 'MARC authorities', 0, 1, 0, 0);
INSERT INTO `profile` (`profile_id`, `profile_name`, `profile_type_code`, `profile_description`, `metadata_scheme_code`, `file_type_code`, `file_format_code`, `character_set_code`) VALUES(3, 'LIDO', 1, 'LIDO MUSEUM', 1, 2, 0, 2);


--
-- Volcado de datos para la tabla `subset`
--

INSERT INTO `subset` (`datasetId`, `subsetId`, `subset_desc`, `uri_concept_part`, `graph_uri`, `links_graph_uri`, `isql_commands_file_subset`) VALUES(1, 1, 'artium_subset', 'library/bib', 'http://data.artium.org/id/collections/library/bib', 'http://data.artium.org/id/collections/library/bib/links', '/home/aliada/linked-data-server/config/ isql_rewrite_rules_subset_artium_library.sql');

--
-- Volcado de datos para la tabla `template`
--

INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(1, 'MARC BIB', 'MARC biblio', 0);
INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(3, 'LIDO', 'lido', 2);
INSERT INTO `template` (`template_id`, `template_name`, `template_description`, `file_type_code`) VALUES(10, 'Authorities', 'Authorities template', 1);

--
-- Volcado de datos para la tabla `template_xml_tag`
--

INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(1, '024z');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(1, '130l');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(1, '700(0-1)a-b-c-d-q-u');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(1, '810k');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '024d');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '045c');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '046l');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '100(0-1)a-b-c-d-q-u');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '100n');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '110d');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '110n');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '111k');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '111p');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '111t');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '240a');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '240p');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '240s');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '243a');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '336a');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '546a');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '700l');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '700o');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '700p');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '710d');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '710h');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '710o');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '710p');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '710t');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '711n');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '730d');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '730h');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '800n');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '810d');
INSERT INTO `template_xml_tag` (`template_id`, `xml_tag_id`) VALUES(3, '830l');

--
-- Volcado de datos para la tabla `t_character_set`
--

INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`) VALUES(0, 'MARC standard', NULL);
INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`) VALUES(1, 'AMICUS', NULL);
INSERT INTO `t_character_set` (`character_set_code`, `character_set_name`, `character_set_description`) VALUES(2, 'Latin1', NULL);

--
-- Volcado de datos para la tabla `t_external_dataset`
--

INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(0, 'DBPedia', 'Linked Data version of Wikipedia', 'http://dbpedia.org', '/home/aliada/links-discovery/config/silk/aliada_dbpedia_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(1, 'GeoNames', 'Linked Data version of Geonames', 'http://www.geonames.org', '/home/aliada/links-discovery/config/silk/aliada_geonames_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(2, 'Freebase', 'A community-curated database of well-known people, places, and things', 'https://www.freebase.com', '/home/aliada/links-discovery/config/silk/aliada_freebase_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(3, 'BNE', 'National Library of Spain Linked Data Dataset', 'http://datos.bne.es', '/home/aliada/links-discovery/config/silk/aliada_bne_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(4, 'BNB', 'The British National Bibliography as Linked Data', 'http://bnb.data.bl.uk/id/data/BNB', '/home/aliada/links-discovery/config/silk/aliada_bnb_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(5, 'Europeana', 'Europeana as Linked Open Data', 'http://data.europeana.eu', '/home/aliada/links-discovery/config/silk/aliada_europeana_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(6, 'NSZL', 'Hungarian National Library (NSZL) as Linked Open Data', 'http://nektar.oszk.hu/wiki/Semantic_web', '/home/aliada/links-discovery/config/silk/aliada_nszl_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`, `external_dataset_linkingfile`, `external_dataset_linkingnumthreads`) VALUES(7, 'MARC', 'Linked Data version of MARC Codes List', 'http://id.loc.gov', '/home/aliada/links-discovery/config/silk/aliada_marc_config.xml', 8);
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`) VALUES(8, 'VIAF', 'Virtual International Authority File', 'http://viaf.org');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`) VALUES(9, 'Lobid: Libraries & rel. orgs', 'Lobid-organisations provides URIs for library-organisations and museums', 'http://lobid.org');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`) VALUES(10, 'Lobid: Bibliographic Resources', 'Lobid-resources offers access to metadata in RDF about bibliographic resources', 'http://lobid.org');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`) VALUES(11, 'Library of Congress SH', 'LCSH has been actively maintained since 1898 to catalogue materials held at the Library of Congress', 'http://id.loc.gov/authorities/subjects');
INSERT INTO `t_external_dataset` (`external_dataset_code`, `external_dataset_name`, `external_dataset_description`, `external_dataset_homepage`) VALUES(12, 'Open Library', 'Open Library is an open, editable library catalogue, building towards a web page for every book ever published', 'http://openlibrary.org');

--
-- Volcado de datos para la tabla `t_file_format`
--

INSERT INTO `t_file_format` (`file_format_code`, `file_format_name`, `file_format_description`) VALUES(0, 'XML', 'XML file');

--
-- Volcado de datos para la tabla `t_file_type`
--

INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`) VALUES(0, 'Bibliographic', NULL, 'marc_bib.xsl');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`) VALUES(1, 'Authority', NULL, 'marc_aut.xsl');
INSERT INTO `t_file_type` (`file_type_code`, `file_type_name`, `file_type_description`, `file_type_conversion_file`) VALUES(2, 'Museum Resource', NULL, 'lido.xsl');

--
-- Volcado de datos para la tabla `t_metadata_scheme`
--

INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`) VALUES(0, 'marcxml', NULL, 'MARC21slim.xsd');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`) VALUES(1, 'lido', NULL, 'lido-v1.0.xsd');
INSERT INTO `t_metadata_scheme` (`metadata_code`, `metadata_name`, `metadata_description`, `metadata_conversion_file`) VALUES(2, 'Dublin Core', NULL, 'xsd');

--
-- Volcado de datos para la tabla `t_profile_type`
--

INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`) VALUES(0, 'ILS', NULL);
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`) VALUES(1, 'TMS', NULL);
INSERT INTO `t_profile_type` (`profile_code`, `profile_name`, `profile_description`) VALUES(2, 'Drupal', NULL);

--
-- Volcado de datos para la tabla `t_user_role`
--

INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`) VALUES(0, 'Basic', NULL);
INSERT INTO `t_user_role` (`user_role_code`, `user_role`, `user_role_description`) VALUES(1, 'Administrator', NULL);

--
-- Volcado de datos para la tabla `t_user_type`
--

INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`) VALUES(0, 'Basic', NULL);
INSERT INTO `t_user_type` (`user_type_code`, `user_type`, `user_type_description`) VALUES(1, 'Advanced', NULL);

--
-- Volcado de datos para la tabla `t_xml_tag_type`
--

INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES(0, 'MARC21 Bibliographic', NULL);
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES(1, 'MARC21 Authority', NULL);
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES(2, 'LIDO', NULL);
INSERT INTO `t_xml_tag_type` (`xml_tag_type_code`, `xml_tag_type_name`, `xml_tag_type_description`) VALUES(3, 'DC', NULL);

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`user_name`, `user_password`, `user_email`, `user_type_code`, `user_role_code`, `organisationId`) VALUES('admin', '1eh/F6TPx3EfCmKlAEeeppB1PHE+J16XaJIS/ig/78o+3yfNwSsso7YsldTyPnhW', 'admin@aliada.eu', 1, 1, 1);

--
-- Volcado de datos para la tabla `xml_tag`
--

INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('001', 1, '001', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('008[35-38]', 0, '008[35-38]', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('024a', 0, '024a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('024d', 0, '024d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('024z', 0, '024z', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('045a', 0, '045a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('045b', 0, '045b', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('045c', 0, '045c', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('046k', 0, '046k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('046l', 0, '046l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('046o', 0, '046o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('046p', 0, '046p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100(0-1)a-b-c-d-q-u', 0, '100(0-1)a-b-c-d-q-u', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100(3)a-c-d-g', 0, '100(3)a-c-d-g', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100a', 0, '100a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100f', 0, '100f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100k', 0, '100k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100l', 0, '100l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100n', 0, '100n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100p', 0, '100p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('100t', 0, '100t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110d', 0, '110d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110f', 0, '110f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110k', 0, '110k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110l', 0, '110l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110n', 0, '110n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110p', 0, '110p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('110t', 0, '110t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111d', 0, '111d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111f', 0, '111f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111k', 0, '111k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111l', 0, '111l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111n', 0, '111n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111p', 0, '111p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('111t', 0, '111t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130a', 0, '130a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130d', 0, '130d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130f', 0, '130f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130h', 0, '130h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130k', 0, '130k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130l', 0, '130l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130o', 0, '130o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130p', 0, '130p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130s', 0, '130s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('130v', 0, '130v', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240a', 0, '240a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240d', 0, '240d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240f', 0, '240f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240h', 0, '240h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240l', 0, '240l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240n', 0, '240n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240o', 0, '240o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240p', 0, '240p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('240s', 0, '240s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243a', 0, '243a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243d', 0, '243d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243h', 0, '243h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243k', 0, '243k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243l', 0, '243l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243n', 0, '243n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243o', 0, '243o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243p', 0, '243p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('243s', 0, '243s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('336a', 0, '336a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('336b', 0, '336b', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('377a', 0, '377a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('377l', 0, '377l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('381a', 0, '381a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('546a', 0, '546a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('648a', 0, '648a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700(0-1)a-b-c-d-q-u', 0, '700(0-1)a-b-c-d-q-u', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700(3)a-c-d-g', 0, '700(3)a-c-d-g', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700f', 0, '700f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700h', 0, '700h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700k', 0, '700k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700l', 0, '700l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700n', 0, '700n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700o', 0, '700o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700p', 0, '700p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700s', 0, '700s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('700t', 0, '700t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710d', 0, '710d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710f', 0, '710f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710h', 0, '710h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710k', 0, '710k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710l', 0, '710l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710n', 0, '710n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710o', 0, '710o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710p', 0, '710p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710s', 0, '710s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('710t', 0, '710t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711d', 0, '711d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711f', 0, '711f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711h', 0, '711h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711k', 0, '711k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711l', 0, '711l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711n', 0, '711n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711p', 0, '711p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711s', 0, '711s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('711t', 0, '711t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730a', 0, '730a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730d', 0, '730d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730f', 0, '730f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730h', 0, '730h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730k', 0, '730k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730l', 0, '730l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730n', 0, '730n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730o', 0, '730o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730p', 0, '730p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('730s', 0, '730s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800f', 0, '800f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800h', 0, '800h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800k', 0, '800k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800l', 0, '800l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800n', 0, '800n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800o', 0, '800o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800p', 0, '800p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800s', 0, '800s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('800t', 0, '800t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810d', 0, '810d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810f', 0, '810f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810k', 0, '810k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810l', 0, '810l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810n', 0, '810n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810o', 0, '810o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810p', 0, '810p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810s', 0, '810s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('810t', 0, '810t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811d', 0, '811d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811f', 0, '811f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811h', 0, '811h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811k', 0, '811k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811n', 0, '811n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811p', 0, '811p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811s', 0, '811s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('811t', 0, '811t', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830a', 0, '830a', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830d', 0, '830d', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830f', 0, '830f', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830h', 0, '830h', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830k', 0, '830k', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830l', 0, '830l', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830n', 0, '830n', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830o', 0, '830o', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830p', 0, '830p', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('830s', 0, '830s', 0);
INSERT INTO `xml_tag` (`xml_tag_id`, `xml_tag_mandatory`, `xml_tag_description`, `xml_tag_type_code`) VALUES('852()a', 0, '852()a', 0);

