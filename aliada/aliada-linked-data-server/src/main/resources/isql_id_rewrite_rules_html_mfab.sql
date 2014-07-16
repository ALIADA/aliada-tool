DB.DBA.VHOST_REMOVE (
	 lhost=>'*ini*',
	 vhost=>'*ini*',
	 lpath=>'/id'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'*ini*',
	 vhost=>'*ini*',
	 lpath=>'/id',
	 ppath=>'/DAV/home/id/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_1'),
	 is_default_host=>0
);


    

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_1', 1, 
  vector ('http_rule_1_rdf', 'http_rule_2_html'));

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_1_rdf', 1, 
  '(/[^#]*)', 
vector ('par_1'), 
1, 
'/sparql?query=DESCRIBE%%20%%3C$ARGV[6]%U%%3E%%20FROM%%20%%3C$ARGV[7]%%3E&format=%U', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)', 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_2_html', 1, 
  '/id/resource/([^#/]*)/([^#/]*)_([^#/_]*)', 
vector ('par_1', 'par_2', 'par_3'), 
3, 
'http://www.szepmuveszeti.hu/adatlap/%s', 
vector ('par_3'), 
NULL, 
'(text/html)|(\\*/\\*)', 
2, 
303, 
'' 
);
