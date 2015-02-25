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
  `isql_commands_file_global` varchar(245) DEFAULT NULL,
  `isql_commands_file_subset_default` varchar(245) DEFAULT NULL,
  `virtuoso_http_server_root` varchar(245) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organisation`
--

CREATE TABLE IF NOT EXISTS `organisation` (
  `organisationId` int(11) NOT NULL,
  `organisation_name` varchar(32) NOT NULL,
  `organisation_path` varchar(128) NOT NULL,
  `organisation_logo` blob,
  `organisation_catalog_url` varchar(128) NOT NULL,
  `org_description` varchar(245) DEFAULT NULL,
  `org_home_page` varchar(245) DEFAULT NULL,
  `org_image_url` varchar(245) DEFAULT NULL,
  `aliada_ontology` varchar(245) DEFAULT NULL,
  `tmp_dir` varchar(245) DEFAULT NULL,
  `linking_client_app_bin_dir` varchar(245) DEFAULT NULL,
  `linking_client_app_user` varchar(245) DEFAULT NULL,
  `store_ip` varchar(245) DEFAULT NULL,
  `store_sql_port` int(11) DEFAULT '1111',
  `sql_login` varchar(32) DEFAULT NULL,
  `sql_password` varchar(32) DEFAULT NULL,
  `isql_command_path` varchar(245) DEFAULT NULL,
  `isql_commands_file_global` varchar(245) DEFAULT NULL,
  `isql_commands_file_subset_default` varchar(245) DEFAULT NULL,
  `ckan_api_url` varchar(245) DEFAULT NULL,
  `ckan_api_key` varchar(245) DEFAULT NULL,
  `ckan_org_url` varchar(245) DEFAULT NULL
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
  `external_dataset_linkingnumthreads` tinyint(1) DEFAULT '8'
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

INSERT INTO `organisation` (`organisationId`, `organisation_name`, `organisation_path`, `organisation_logo`, `organisation_catalog_url`, `org_description`, `org_home_page`, `org_image_url`, `aliada_ontology`, `tmp_dir`, `linking_client_app_bin_dir`, `linking_client_app_user`, `store_ip`, `store_sql_port`, `sql_login`, `sql_password`, `isql_command_path`, `isql_commands_file_global`, `isql_commands_file_subset_default`, `ckan_api_url`, `ckan_api_key`, `ckan_org_url`) VALUES(1, 'SCANBIT', '/usr/share/tomcat/upload/', 0x89504e470d0a1a0a0000000d494844520000004b0000004b0802000000b72cedbd000024b749444154789cb47b077c5cd599ef39e7d6691a8d7ab164cb5572af18578c71030ca624864008107809cb4b366593f7db4dde86bcec8690f74b1eef256421092424980006d30c1863630ceebd61c945d56aa33a9a19cdccbd73ef3de77de7cc8c24db92edb0bfbd1e4ba3997beff9beef7ce5ff952b33c6d008077c85311ee60b2a5e1839d8618462388d529b2918138291451d19615992e0845e23de108f1e09070f7535b724c233f4dc7f9d779386e8d68e8e07f76e74b9dcab8ac6ae28183ddb5f3ad19bc597b2ad24434c5208ac4e9184185f062189a47e2334402cb9569ae591d8bbca81077ec152144b400391d3d4c8404753bcff486f5b4da8ed7c5f575bd2303505b9dd92eac60813c4884c5c1e9f499d973aab5f0b56cfcb2a5e1e285b1218755dfe68b782077970705262f0b7f83174ddbfe3f8221c32be7bb0759880bc6cb887021ff651eb54474b4db8ab4762fd8875f5453a8c480cd992aa1277b646194932394d3d0819cb206a592e20be24a307623d87235d1bdacf56661516b974b7ed54b8b217178f9be9cf5381270b593225fc2a06724c6fe335b33a3c87ec923f30e36cc10ae21717aa2013815222fc79ac6f6f47fdf168a8291aee67c9243f116958522545271ae3d7724d56401ea0796911c1bfb43a49187b148dcaacd3b1821d170c6c5b84798934b6b57abe3bf7b6315397178f750ba5741837293c84bef49f1865d4f922e2f1481c3a434e92e01640956d3b124b122a1109364f869f62c9dde1ce0f9b6ace447b22cc3608d614d5652b409c8328953171600d0a54502123a09ba5a9e3a2e2fb016b499c6acde60b3944b65cc84d74e6508ae90527d1dc77e1dd13cde535ee35c5e31e99b8a85c1286499d24016b072d4012acc3644e31dc59e1ce810d6c8ad886e139cce8416ac7f84651054b447165be8e5067475df58efad3f52a0d69a06b2eafed52520e479628381c3046ca5948dd81a4379ef16f522e82ff0504309902ff0cf6d14e13095738fc14461449055b70106b36137f3a7b6c43f5a1f98565df98b96c99b74045484def00b1103265040e5307ab811519167c0eeee920878ee3a4371733782fc3e9182529102d3b4cded31d7cb1fec891485b3fc85a53255d09e4ebd9a61c400aa3c454805e2ab18bac035ffce3d2030fa357627b99b053e1c430963049a8d8d23599a277c29daf7cf04724d9c84ab865efe3e3163e387e76953bdbeb5087d954469661699a8641e1b070be927c1187c04b7a9314c5b2b972ca8a0ce23c70befa37ad27ce9ae12c8f779c2707ae3364ac30ac26b85704e26c2e0a108bb006eeac05d169228770c0d27a31f80ea74d08679c07973cc603be24f5bdd72152920b8fcaba9de372b885e0384efeaa79f7af1a77de9e33fec9f9b74fd1b39388591677bfd8e11e502284a4f790a537146e0d8bb6b5053f3970f84b77dcd6dcde75a6ae71fcb4caf2f2b1ffbdc07fb0a7f9747f77b03f9eb42c66494852a824f3f0c718d8970ae2010af81d52dc0a45e3ec32c1adf052dc4dc007589836381fee7ee0239a399b6b3045e034b9df6429def9e510751d85d958b119352c0b628c8a5991c7b7ac7cf65c6fde9ad20901831c3e5773b6a1e1d69b57bdfcb78d7367cd9a3c69824406b494fb3749c8d4a698b487a29bdedfb3feee5b4391befff1efcf964d1dbb6ad1a259e5a5f388b7c8a2f19c221af07424fa1bfb42ad915e5b064e55f0292acdf04151ca433afc0777337c8bc4b670a219088238c2d209837d518157f05da03d201d2ab0071362218243b81908ce9190e93876d22a24aec53963ca0af3b2a923d57715b7a9635ccad993277fbeedb3b7b7ed5c71dde4b56b6f7ee1cd2ddeecc0ccc9e3071448e64a2fd4138236dc56d6547f208f60a62a4a4e7e696b47cff32fbe6119aa4c506545e1c2b91317cd9b3275f2d45805ded7d1f871b0a13a1e8e71c1eb44e2e6aef1d0c06ca049e28e52e10a45c50e020b18ec25058714ca249b8474057c8b9a7434ae00d496882123c521aacd45023e068c3b6e9b8e4147ebbe156525778eae9c9b35bab5ae6df76747366fdbf3f363479c4804211772057477494e7e212c919d5ba0ea3ad0cf2ef234f812fb1fc04accad6abaea71b8f4595bb877c3e6edefecdc073ab068f6f835cb96ac99517934dcb5bdad6e4f4f538f95d45dba4b5624876f820a0462d9863d9398c9cc84c9add6a3f9751e09982211d54a1a46dcf46b1e5902f5531c2e6ab84a02d4a7c83d2a7066b9fa62b35d05774c9eb676dce431c4bd7bef911f7ef0c667bbaaab5b5a914e743dc7ab170299607a5cb1854ae394ff1fc2d230d182a5fea7a4004a42a925cc56533555564cc43efdfccc81da736f6fdf7fe3d4cab52b16fda4eafa7e74fd27fd6def569f3cd6db1ad1b14214dd42094a21b2e538a842f3ce2bad9c945b34c19387934cd17095d7ff7f96df531f6cdbd1d6b0b7fd423b8a21c07300f78882ac248ad32c59bd67cacc476e5cb804b688a197dfd9fef0a6cde76a3b423d314424b7d7af081f00e1c42660a2574238c37028516e45a02910e12588d69828c27f51ee0a240da17cc9e350d6dcdef7d7e0fed73e3decf77a41bfc71779efbdfda66757ae092274b2ad1eb0d8c4407e1e283d435e499165292d3eca655ca0bbd7b83cc85ff8d8d89926b513d80913561b0f1f0d36cdab98b848090443fd2f3efbdaf7b76fe895e570cc88f5270cc35454cde5f5f240c006c111800089a12ba0b861384c41348ea9187ff138cd2eda628e2809965c3a6c9265db1dbd7d8e432f84e29fd66c28cd7efd1bf7ae5d7bd3f599b30760064de1984c1081352022c19ec92ea4b8989263a38a2cd7caaca296a6de479efef7b7dfdbcac07fc96ec306b723e9bae605de32b051786b94028ee9d04346e470b82424138852bff02502c283e780475664495315afc7952b493958e9ed8efee8a96766dff6b5275fdad415eb17f797c48bf0a4e22239a10caee2774b2ae8957d87c7acbbaf6cf6f297dfda65780b4d7f11f5f8bd9e6c9fdb03ab603cb02c4243f142e61dbb044e678eabe516dcdd5f09cca7e22a9c14d3131c4c53d5e72db393ec2f2f6e7b63c33b6bd72c5977dbada5857e70b55e6d5057e13a8819d1583f8437cb9136beb7fd9917dfac3b710e15956a155300ab5282d3c153bc1921191c2ab0cc668aa89bf90c5f0b8738a517573e80144fd28378f87600bc618de596f91c27ef8d9da75f7c6faf938cf97579e1dc395fbe65d1d4aa09bad75773fec21bef7df29757dfeaab6de718d39b85f2fcfaf88980cf1936617fc1b502c08578684be4a2c461240228771f0018702a2c73eb2057e070106565c0d7887580cc0a90c2d948889060088d92c483a1a3fb5df9596ea018129f4335170e1e3d5e35693c93d4b7b7ecb4a226c9c97355567117965ac16183f480ab93f8dda42bb23790d1636189dc150292e5df381cc68b63b86821a2b6381c7032d47680e8ab269c976ef480f98a780fb7c9f2b95196eb7c5be8ecd95a59d354970f920100f982c461d4e45ab27aee0d85110927c47750785936d4bf0cef6928b2cda4d9d9d31b8f1b88a4ee3260907ff79192341c16b84e701eb9f93c15231297f7b075a0c12b87be4bdbd9d0ef442c9392b6130d85839dedb02f008d2e29e90cc32113d942f9a892471fba3b2fdbd5dd7ea13f91808c931229adbefc2e34cdb6502f8eb1101d89ce147b409c426d9927d296008a183484e24b096028a53e9c7acaa11413400f4c8c401086149039b6589267a08e6546db9a93a1ae95ab167cef5b8fe0147e176bd2cbf3c381250024a84c2fc956fff1fe550fdcb9f4c8d1637f7e77cfa1936740b37c3e1fc01b001f385d4da0022c51c62b14d25534194c54c45a24720e2654f7f24b9880f05464548ce76700fb78fa62837cb1643ab6a2c81087130993f504bd01fd9fbeb5fea1afacab1855ec91b109f220298e30bb028742ac706b40c92ccbadaf58bc005e754dcdcfbffce6db1fef4baa3e5756be8f706182a67134407829825cdddfa532fd74058b1b274d3bb38bce4a07078e34a9506f99dad8316d5bb12405a085198aa2eeb6e209a53f7efa9fefbfe7f6ec0125a071044e7c6013331f5fce2176d2591de2793faf355009d9e34697fde247df81d7cbef6edff4ee074dad61246ba03992ac2245839f2aa4def8b20ad6e0db819f3863256968c20678642917001b46c140297578b6e5d0b045b963b51388f5b955fbf69b173cf1c33f5456940fac03a915f7baf852618dc421445b5009ee6b39a8e2540078e7913a15adefbf7d05bc52a75637b7bcf2fa7b078f55476221036bb2aa495c89711a4d0d410a194781d331390ded53e5bbc17390c05fa010b663598938762c9f57af2ccefae603eb1fbd7fdd70918d1ba924713b19e207d2b9f648f110b394987942ca1c822033949005ef0d8b44134e4f3cd9118e2662dd053981f2b2d27ffbfe631d7163eb879fec3cf079435b57241a566455d174cca322af8bf0f42955dc12c90af730206fca526809c29ec8f87989074b92edd0844d911987fd1d575cb070d6c407be72db8ab9d3809cbde72e7c74f09461e3f163c64e1a933f3a572b74eb9a942a6d61d86ac22dc501db16617f30071c86432146d03fbe8be043bba291886df71b767f0c124e23611ae068928ad6158c3476460a7dee8ac29cafdd75f3bdeb56ef3d7c62df91cf4f9ebd50d3d01e331998a7aa69bacb278312b3a4422d0b127c1121782b00036891e013c86492f1188a27906d03cf8565c5d75d3f63d98219ebd62c1b5b58d81c365edd777e774d5d4b6f84b8bd1060ce1f3db7ed584d20e0aa28291e939755ea95ca02ee51f979907eca8e4178ed32830246d2524978ec9eae9e606f78dca4719a4b358366b03bd2631a60725e8f072015c1165679edaf3d6c04fbda7dde707eb677dcd4e92563c7cf6b6c0e767438368a24ccb3f54dc74f56472226757b1c5915de88f7232c22f3723980d2580c9989ca69954be6cf1c5b9ae75241cde531151581e2e253ddc6c6e3c7ce347685123145533cbe0001d208b5b354b0c8f3e1785377f5f451058169e37d59596d3d91e6baba39b3a6b221fa3ed21e82125b49c61a3abafff76fff3cb6b26ae5d2790b674f995a51d8d0136eec0805c3c998e4f27088e4704fa72a20b35ec3696f8fb0965e37b68b7cfaa279d30ab33d6ecdd51f8f9da9adffecf0e9adbb8fd7b6f77934cde7f5241d6a533bd9dda66a68edc25977af5db560d182f292829ea45373aefe5853e2f5d3a1de43f5166c3ba44d8a27dbed9699e92070a9d8a2b4bf3f91eb536f9a316acd8c711302b9cded9dcf3dfffad6ddc746e568bf9f35537830968e3923ec21e4be36280f93d5d6aefef31dc70f1fab193d267fc5c2b9f7ac59363ed7df1e8e9e853d0a25c1cd4268c2bc0c83348274c0dcf01e4b9d71a7b53e242bfd7e9595e6f8a64c9d3667fab42fdf7ad391e3d5af7f74e048756da823e8f1e95f7d78fd0377aebc6eea24b72cefab6d7afbad8f1bfbcc60bfa9c8449565bfdb4d990f6895b089a99c64d9719aa076244f45772f9cbc66d644bf468e9d6f79f0d7cf7ebaff644b6bab6325ef5e399793c201a628e28dcc2156589646921266ba968d65a7df744e54779e3dffc1cbaf7d78f38df31efaea5dcbfc3e38af33c68ed49cef8a9b26d1a8a2162003b4d022e0785549e66ebed724e1b6707d474fc025575594deb1fa0678d5343636d7d5afba6939dcc160e8bda3e73e3d5e1b331c032202a8a84797e05e3c37b6986487122c9c94c1f25d247ce394510fac5831c6cd3dfa07bb0f3ef5fceb474e9c4d2441e965b7e6c56e2c29bc43c5480aa6e22bc543c208117919afc6130636a08072301a8c265f78fbd3675fff9851275f7316ce99faedc71f2e0ef8e19ae6e68623ad4ec8811490c8c496b0038ed496244a25832a6d31d672a20524eba749afdbdd94d41f7beecd5e2445056c50c125a92e85292ac03a0b5cb811c33892443462cc29f33fba72d6d8bc6c58e2a58dafdf75db93271a7a29c9e6dd1c198284acc90ae8116ffcf0e04a70a69c8c87a4dbc3c59821e97d1a86f04444d25519d0a7cc9b2938ec581f1eb9f0ca1d8fcf9c34e6db8f3db87cf6c4b232d4d117ae6968ee317042f25992cbcd0c059b36571b895745110a211244e44877ac0f81064ab93676511b449944c4901d935a096a02e0f038c65d534bef59be12560b26ac879e78e62f7f7c99e3275f36f61523ccbdbcc6216da614418662f44ba3fe35f50f45bd97178e21cbe455558654590198515256d1128a3ffec3274b8b8b1fba67e5eae5f397ce9a1a7558435b6f4f3411352cd3e6f5182e71898838ccc3a42ae9049249866466419c351d66dba6c25881472a70b9164e1bbfa872ac1ba10327cffff2772fbeb5793b52fd6a610521628f10001d472483b2c82c5269978346ce51ae89431e99d3b545268af7bc7e0a5647b18d65d953521a3793bff8dd86175e7d77ddea65cb17cdf6c8922a1b6e8f4ab42cdbb16389443c9ea0bc5eac49008d457b15308bed243cdeacd29c9c5c8fa698f1028d8c2bca0d68ead6ad7b9ffafdc68307ab91a691c2d13eb02f806c5811ed002cba0f9962511a2ce1ff2c87dc84c9e04d053c22e96294c27b0d8aaee4e905098bfd7ee3f6a75f7c476176514ed69a4533962f9e3ba16a62c057140c85825ddd4d312944b1611b6a2c5491adcfa8a85834adb228db53d7d0f8f16767ffdf2b5b8f1eae458042c13567793d05012cea4022f55750dac006fb064308bc12e6bfb62ef74577b8545c99f220d2149c1ff0a11c3f1091b49c4dbb4ebcbfe744d5a4b14be64d5d306ffaec89134a4daba9bd0b174863e64e9f39798242d1ce9d07dfdbb1e783dd7bdb9adb9127cf53528e65d8210ba1b452b3a135c38b68b96ad5e10a1cb24c6146bcb97a19489c4d85c1f3aa040324c04325520b2cc73e72aee56875ddfb9f1c983e79e2ea4593174c9bbe604c4967ccdcf0b777dffb64efee6375667f027934a97014372c278679ee74b5dcffb236fc10f0ce2e39e1320eb9f9022a129f1303de10d12311991116162db1813ad69035b86d7213e5b9bcc4ab8196c46c7e9e5b63546b6e0f35b6ecdb75e85441c18eeede5063734753737bd2b065b7cb9595c5ef667395e4493d8086816edf4875139a02dce98c057c1fcfae104f8932c64946e690836e9b894a39243114a93cd6102735953050d96397558933ad4e9c6aa6895437f325c1baee8288da1fb3bace359dab6f349296aaea5e9f5b2809cdec1826d750b9e402e539acd0e40c2720519c2eef93748e36222e055fc96b608859763cd6efe89ea488ae24dd9b67b2c88c79f58290a11ca2b441323ca4ca36788268dc6a80c754393bcb1b4d18dce3d38c500758bc3a77290e292594977018b3a9081a499a8c44c8804c5192f056bf3c1c87dc7dc99063057cde71a5f9276bdb88ea4a32de4fd75d3a488ac8527a2f87ece6d00218befcedd58e8bce1bd2f16643ee33700eec8e6dcb495068c85f20dd7192c8b6548f366f4aa54c7987925e74a361382426c05d462bcaf35f7ae6c9d4472d3da157376ddeb1f750c444110313d5ad487cd883d70352da8933ce7cc072aee22a463ca8286d61615d42ef49ea1715fd21db7692c924b8ea6c379e32beecb107eefaea2dab06ae05b5e34090eba1c615511a9e432a030a018de4b911f78a70faa8dcc00fbef1b56f3e78cf968f777dbce7585d5ba82bda2f010485d4164ec5e99103de66609970fc0519449688eaaa63c38d242aa77afba09386693a4642756b132b0a17cf1cf7e8fa9b17cc9826ae004768f19a16751cc801b8a3028708efa5cca4c2a5471a97f3d9973426826b0d10a44fd3d6dfb2025e7b8e9fdd7da2e6f8e7e71a9adbba42518ac0ba5c1e97aacb60a45434d058aa5ef1053874dba07f9241dcfdbc23d58f625db03510f12b2a4ae7cf98b966d1acbb572ff166732cce1bb0204e3e22c8b7816003674625d015738bc18372f4220a4384df052e36006b99c9aa09a515e5050ba654b475f51aa6633aa4aea1f9c8e9dab68ea0d7e351554534a4301a69b4f18a8705808e9ae036c01fcc983169e5c2198501376887acebe3c655e407fc757df1806d97e4e4ca24954040a666517ab9771b91c38baaf7bcca8b2560a32b1ced8a5b6183834cd34c00fb595e5fe58c51793e57b1cf6dda764dfd85dd874eeede7ff46c5d0bd15c2e4f9694bafc12f723fe5196aa0d67fe65820da8a21d892a59ea3db7ccfdd2aa254b16cc29cccda9e98d9f6aecf8bc3178e8506dccb4759756e2f794677bc714f9c717654d282950898a9891f603e9387185882f722bca2c1e6bb0dcdad5ded21c0cb1ac38932cbea70e61b6a6aa16c2a1240bb574d741a22c939cecec89a3cae74c1c7bcf6d2b8f9e3ebb79dba73bf71f668adfedc952081f43e4b601962df1c931f0783a35c5909b22d92aa42b718d9a8930eaebcd1e55f6f8430fde77db0d9593cac030769c3873f8a3fd8d11d467cb2aa6900dfa5439e9d88de1585dd8942f84b21512c0898985be5b97ce2bf1c9d48c63eaa4075baec021af82313b6920cda394e617c3eb647317d89c0d44ca0a040d07290a76788152813371dca1b1ee70b0b7dbaf4b638a0a6e59301b5ec15064c3a60fdffa60cb85966e0b6b9a2f909d9befe3d571d83fdd92a4b8c5685f0445822816462a59b966e98fbffdc40d4be60105ed51e38f5bf71c6e6aefa7aa43754542d94a52602648bac044d5682216ef0fcbd4f217e4ac583a6b75d528c42d28c693e074f3491a3903a61cf712ac563734fcc33ffdc2d28cc2ecd2afdeb5e2be3bb8530e3be8d49986ce9e4e53f212c5258b5e21ef3fd06492793b12b8a3b6e7686d509598264b77dcbcf4078fae4fdd75ebeea32ffee595c3d567b59c92ae68cc6a6b43b2f1ad87effee5f7fec12d52f87d6d3d9bf61efa8f17de300c1df658b420fdb06f6e055c2518a7cb465a3216c7f1ae12cd5877d382d5332be1aada86c67ffde5efd67cb00fc9ae95374c79f5f95f3149e3188a39033d9f613c8dccfb3c90111135e067b2abc7707efadce69ffcf6b57bef5cf5c0dd6b164fa940a8e24c4b57536b6f22e9504506f80451c3071e8fa2a42c9b448a622b8aedee60e4e08548ae4fab2ccb5fbd7836bcdac389675ef8dbfe5d9f3df6f39f7cf9562eb21eca5ef9ecc0969367a2c4e3930bf47ebfae256d2d0ed18a374460c31d94c00a84416fb26b66bee7ce1b575595e4c2852fbdb3e3674ffda6f64c23caab40f995607d3eb74fe1266043a0032f4547dc43de10015516cd0b45865c46265abeec9648d6fb5b3e7effbdf7172fbaeebebb6f9d3fb9a272547e6b385edfd11bb558d4b4a34009af246bc0af0669ae45c02b320d8513b103d5a1d39a569c9f5b39aae0e7df7f04c10ba143cddd9bf79f3fd0dc2e6b52402dcc61c4b263ccaf38d482309c4802f0e023336eb756ec4655f9aeb5d72f2971bb40a84f3cfbb7e7fefc7a67631b2e18a58c9f29334bb682b2a468b898972020b9e12d9dc11ec6651cf2495655f47f9862cb0e1169aec4a7a2bc811cf87ccfd1b3db769f9833bd72dd2d374c19579ca76117358ab3e5a452148d250d2309be960709acc80a08934a928224356ed173cdbd8d6ddd797939dd716bd789b375ed5d4477077c3932c30a4d3a0ca014326d4a6c5b65725e41911f9cb56cb99dc8b85ccfe8c2bc53d54d3fddb2fb4f1b3639f104c902673a96f2619824762c59966d3edc0094234be00f3238f4351cf2e64e94277ba2e8961e54e157f00a1c425e9fcfe3f57e5e173cf8d49f1c23aa4b74fa944937de70fdf2d955f326948003ac0b76b586a26180200e001e9dafc76754b12c8168f1859ed8a7c76a7a6de4ce8680e6c84e82522521aba6239178b45827651585f3a7962f2c0fa06864f7e1fa17de3ff8fd0f3e323b5a11f8617f3601beb3b2311f8fe393bd3c2848dc4c38a5a2742b468078e67ca55a1b1efa2e03382fc2fe18f93caad7a313940fb26b0846cebcb0e9cd77fcf3e64e5b307ffabc999595a545c150474330de1d31f88015a005299576604d96fd5eb711b71c510e31906e822b31fb8b7cd275b3cb97548d195f103873bee9a9dfbdb979d791c3874f21a2128f479f50256ad94cd4b39c0c64c143a27c06395e16f9bfc8ac7eaa142d3acfbc23a969ba4755fa2cface8e03bb0e9eac1a5bbc70d6d4e537cc5f5a5508273747e3e79a3b82bd61507e091159230e95e2861936398aced3cdc555e537cd993339370b4efe78fff19ffdea793084faa676d06d352be0420907831303efefa4c654a90093d23553fbc59eb7c0dce3f25f1ce362314daa114953b39249ebf0e9a693e75adffce4484196cf05d9a0dfbd7ae5c2f58b6742d8bcd0de75b63d6446bba79716df38af6a4a5920d4d5b771d33bfff38d8d86adf4868d96d6f6ceae1ea26b2e9f974f32f329380f5f8c65ea35999fd74eeb17e4309530e14c8984a5c63d1096c1bfa8bc17ddd6d1ddd4dccea805db7be0c8b18a5185f7adbf63c9acca69a30a6f9839315f216d71e3e95f3fb3e99d2d1d11bbb71f74d60d1bec52c08964b1746ecd8d3f95ff0f6d2ee3bf8fc12ffacccc25052f3c64692e7209b0baa66932236e4ced88699f3cdf7ef489df9414b8befec097a6544ef8f64f9f7c67c72183f8c0cc14c5a5e66898b71ba9ecd8172fc2f0c5eb7d81434603736038dd7d4ea1e14cd92a536b636c20a9bdc6ec9da626de206ce82a588fa2b0a6bef877feedd9869a1aa4fa485631527c322f3e5944cc9ef08ebc30afcb0750d090818621b5c5f4f324432b0c0c5d3abf25339c11959808c362ce9a8fe03934914c3a0acfe533de945d5c4db9129b24c362aa5bc2ef2b318fec525dde3e03274c1bf08a42a3540cafa5e8140f1b49e4b2fa5a7ae87798cfd392473cbbb71386291270961970ca70281a1c62cadee14f67c14596642954f2c8d89523b544b0d7ee55f42c0c605b386c51ef43031d832b3139981ca6c62e20b662d0194d7292d814b543497c279e41493d6544e9e5e50f476c8cc245c4e70a6c1ef178edcfc11c52d8b118b2926ebfaf203f1710ad2af49c0d54fb81433c58b416e33f7c328ce3a549e5f94ffff0eb5b0f7c7ef254f5d9c64e40f39acb2dcb0a9f55e678c0267f67828b2f7a8b877c70714dee7249892da1620680cff8c8bced613b94415262f48f2ecdbf6efa949b6eb8fe4b2b175244e344e7a302a95d147793078c8a895a75c2303adadbdabb4365f9b95327f9a74e9ad01e59fde981e3478f9f3972b2ba25d862412c94d52c8fcba36b928453336becb20231ba76ef70d9d983969fea56619a4a88e286c3a251644420d544599e593326ae5a326bc5f5b3572c983d706d475f2c168d899a8d92b2673e17c92b3d90d8dabc50da13ee3f75b6ce1dc8f9fc746dc399cf674c2c5f77db2d6e5d87534fd6d476f4f2f19dd6cede5d874e1f3e715a5635b7c72d8a51a2b0cfb3779a1ea8499339e83552b200f5aa6b6a4ed54b45fe8068aa529ec9f43931a2bbc447a758fa110c903bebe9f39516ddbe6ad1e2eb26672928cfab554d1a5b56520adfb775f7bcb4f1ed63274fcfb86ea1273be7b6d963ca8bf36dc5ab09c4299ee3104def944f4d194fd43001259fa9ad3b70f0685d7dd38ce9131eb8e7ee71c58503e476f6844fd6b76ed97d60e7de43b16822df9fcd4081c14ff01bd944d40429d767e9ca1c8a674e68ea719d944f91c5982925ba8d65c7b1ad681f8ac6c64f1af7c8fdebd62c9d35aebcc8a7ab03f73c5c73f6c9fffb8793e79a664e9d78fb8ac553274d9c55399e4bcb36215de0562f5cd4a53e0aee2b1e1be2bad71b371b9b5b3fd8b663c78e4f2654543cf4d57b01910d9c6939564357f8ed8ff66f7a7f67a8bb2b901380784f24454c42e0d46cda9539c46226143e4f4df7f2b6134f1124dbb2506f174a8417af5cf283fff695657327039a1d4ae49fb7ef7ffad7bf6d696cb8efcbebee5b7f57d5848a80260b7b75c42689f470240e6d27356d4679db5554cde0f4bea4fddaa6cd7ffddb6bb1847deffabb1fb9efce42af3ec82a425bf69cfac39f5f3e7ca2da263ad6dc6e5fa0c0afeafcf2f47069aa3f71318792cd27b961af1cfef8492c8eba7b51b8073916ca753ffc8daf3cf1ddaf8f2ec81e4a5b4367f867bf7ce6c5ff78c133baec897ff9eee30fdee9497f434d9ee171682ef347d652600b0fcfa11816452234a6a6a5517a8c77083fefeedcf7bbe7fed2d4d8b16eedaaef7df3beb27cffd01b8463e686575edff0fe9e7e873f48004255f9781177d1031cf2bf1c0918b5a981cc188af5ce9e3ee97ffdf83b6b17ccbf849c3da79b7ef4d4339fbdf6c69885b37ff62fdf7960f5d2215fd2ccf39254f44cb198e0cb909a7ad471180ea9804ebc6ac499e3f7a0763a4be42383a24d2314a0359a7cf6c5373fd8b66dc6b4cafb6eb9e1fa19937d5eefa02e20b471fbe1ad1fed686c0dc6934086cec76f24e97c735b246e5a469c99095d21b93ed7ea2573fff9bb8f4e28cd1d4a456b5fff5bdbf63df7d74d8de76abeb67edd133ffac742175f94d91107298ee412957f1e630576b205c8236ce8a4091e8943962e1c0f4682c16e04caf4ae06dd659f696fdcfcf1473b77e99ab260ce8cb933aa4697e4e707fc52c6cd9c385dfbe6877baaeb3b5b3b3bfae2664d7d1059567e9e77cad8ec5b6f5cf0f057eecc1d6263f56d9d671a5bb7ef39bc6dc767209087eeffd263f7dfaea5e8a20ececc29a723dc806aa5927af1d0f125517ae42e241af979fc3456e2ae8152511117aafbf1ae7d7bf61df267e7cc9c3bc7e375755d68f669faf4a99372033cf76b6b6bd976e058535b7757670870c68a25d7df7ad3c2d4ddea2e341f3b713a6ad8132a2b9b5adb77ed3d988884efbdeb969b97a69ebda194a67ade9890ab3c7a7139cd5f8c43262afe627c41cc3cf2bc942f9df6e3fd8671aae6dcfe2335e71a2e10491a3faefcc625f3668e1d7df98db61d3cfeee964f6a6a9b14459a3e75f20d4b162c9f3b591fb2a6954a56aeccd67f018710a51d40957c143df5e00f4bb5c251ca14f8a30f0273b6f5860f7d7ee6f0f1d30d0d0d258579b72c5bbcecfa3988576f9d97376f7b75cb8e485f78daa4f1372d5bb874feec529f0ba124785c8b1185a37f8712496057fc9fe1f0ff070000ffff382f9fbe90a606970000000049454e44ae426082, 'http://aliada.artium.org', 'Basque Museum-Center of Contemporary Art', 'http://www.artium.org/', 'http://fbcdn-profile-a.akamaihd.net/hprofile-ak-ash2/t1.0-1/c21.0.160.160/p160x160/299052_10151242154497809_77907368_n.jpg', 'http://aliada-project.eu/2014/aliada-ontology#', '/home/aliada/tmp', ' /home/aliada/links-discovery/bin/', 'linking', 'localhost', 1111, 'dba', 'dba', '/home/virtuoso/bin/isql', '/home/aliada/linked-data-server/config/isql_id_rewrite_rules_html_default.sql', '/home/aliada/linked-data-server/config/isql_rewrite_rules_subset_default.sql', 'http://datahub.io/api/action', '59465962-6eb1-4a06-8318-985fc4ffd1fc', NULL);
--
-- Volcado de datos para la tabla `dataset`
--

INSERT INTO `dataset` (`datasetId`, `organisationId`, `dataset_desc`, `domain_name`, `uri_id_part`, `uri_doc_part`, `uri_def_part`, `uri_concept_part`, `uri_set_part`, `listening_host`, `virtual_host`, `sparql_endpoint_uri`, `sparql_endpoint_login`, `sparql_endpoint_password`, `public_sparql_endpoint_uri`, `dataset_author`, `ckan_dataset_name`, `dataset_long_desc`, `dataset_source_url`, `license_ckan_id`, `license_url`, `isql_commands_file_global`, `isql_commands_file_subset_default`, `virtuoso_http_server_root`) VALUES(1, 1, 'artium_dataset', 'data.artium.org', 'id', 'doc', 'def', 'collections', 'set', '*ini*', '*ini*', 'http://localhost:8891/sparql-auth', 'aliada_dev', 'aliada_dev', 'http://aliada.scanbit.net:8891/sparql', 'Aliada Consortium', 'datos-artium-org', 'Open linked data from the Library and Museum of ARTIUM', 'http://biblioteca.artium.org', 'cc-zero', 'http://creativecommons.org/publicdomain/zero/1.0/', '/home/aliada/linked-data-server/config/isql_rewrite_rules_global.sql', '/home/aliada/linked-data-server/config/isql_rewrite_rules_subset_default.sql', '/home/virtuoso/var/lib/virtuoso/vsp');

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

