----------------------------------------------------------------
--Input arguments
----------------------------------------------------------------
--$u{lhost}: listening_host (no *ini*, choose another port so that default page for the dataset works properly), e.g.: :8891
--$u{vhost}: virtual_host (no *ini*, choose another port so that default page for the dataset works properly), e.g: data.szepmuveszeti.hu
----------------------------------------------------------------
--Cross-Origin Resource Sharing (CORS) enabling the Virtuoso SPARQL Endpoint
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/sparql'
);
DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/sparql',
	 ppath=>'/!sparql/',
	 is_dav=>1,
	 vsp_user=>'dba',
	 ses_vars=>0,
 	 opts=>vector ('browse_sheet', '', 'noinherit', 1),
	 is_default_host=>0
);


----------------------------------------------------------------
--Create virtual paths for /sparql-auth, /describe , /fct 
--only if not first organisation (virtual host)
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/sparql-auth'
);
DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/sparql-auth',
	 ppath=>'/!sparql/',
	 is_dav=>1,
	 vsp_user=>'dba',
	 ses_vars=>0,
         sec=>'digest',
         auth_fn=>'DB.DBA.HP_AUTH_SPARQL_USER',
         realm=>'SPARQL',
 	 opts=>vector ('browse_sheet', '', 'noinherit', 1),
	 is_default_host=>0
);


DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/describe'
);
DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/describe',
	 ppath=>'/SOAP/Http/EXT_HTTP_PROXY_1',
	 ses_vars=>0,
	 soap_user=>'PROXY',
 	 opts=>vector ('url_rewrite', 'ext_fctabout_http_proxy_rule_list1'),
	 is_default_host=>0
);

DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/fct'
);
DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/fct',
         is_dav=>1,
	 ppath=>'/DAV/VAD/fct/',
	 ses_vars=>0,
	 vsp_user=>'SPARQL',
         def_page=>'facet.vsp',
 	 opts=>vector (),
	 is_default_host=>0
);
