DB.DBA.VHOST_REMOVE (
	 lhost=>'$ARGV[8]',
	 vhost=>'$ARGV[9]',
	 lpath=>'/id'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'$ARGV[8]',
	 vhost=>'$ARGV[9]',
	 lpath=>'/id',
	 ppath=>'/DAV/home/id/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$ARGV[10]'),
	 is_default_host=>0
);


    

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$ARGV[10]', 1, 
  vector ('http_rule_1_rdf_$ARGV[10]', 'http_rule_2_htmlvirtuoso_$ARGV[10]', 'http_rule_3_html_$ARGV[10]'));

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_1_rdf_$ARGV[10]', 1, 
  '(/[^#]*)', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3C$ARGV[6]%U%%3E%%20FROM%%20%%3C$ARGV[7]%%3E&format=%U', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)', 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_2_htmlvirtuoso_$ARGV[10]', 1, 
  '(/[^#]*)', 
vector ('par_1'), 
1, 
'/describe/?uri=$ARGV[6]%s&graph=$ARGV[7]', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_3_html_$ARGV[10]', 1, 
  '/id/resource/E22_Man-Made_Object/([^#/]*)_([^#/_]*)\$', 
vector ('par_1', 'par_2'), 
3, 
'http://www.szepmuveszeti.hu/adatlap/%s', 
vector ('par_2'), 
NULL, 
'(text/html)|(\\*/\\*)', 
0, 
303, 
'' 
);
