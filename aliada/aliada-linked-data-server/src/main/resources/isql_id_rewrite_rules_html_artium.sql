DB.DBA.VHOST_REMOVE (
	 lhost=>':8891',
	 vhost=>'aliada.scanbit.net',
	 lpath=>'/id'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>':8891',
	 vhost=>'aliada.scanbit.net',
	 lpath=>'/id',
	 ppath=>'/DAV/home/id/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_artium1'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_artium1', 1, 
  vector ('http_rule_artium1_1_rdf', 'http_rule_artium1_2_htmlvirtuoso', 'http_rule_artium1_3_html'));

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_artium1_1_rdf', 1, 
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
'http_rule_artium1_2_htmlvirtuoso', 1, 
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
'http_rule_artium1_3_html', 1, 
  '/id/resource/F3_Manifestation_Product_Type/0*([^#/]*)\$', 
vector ('par_1'), 
2, 
'http://biblioteca.artium.org/Record/%s', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
0, 
303, 
'' 
);

DB.DBA.VHOST_REMOVE (
	 lhost=>':8891',
	 vhost=>'aliada.scanbit.net',
	 lpath=>'/doc'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>':8891',
	 vhost=>'aliada.scanbit.net',
	 lpath=>'/doc',
	 ppath=>'/DAV/home/doc/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_artium2'),
	 is_default_host=>0
);


    
DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_artium2', 1, 
  vector ('http_rule_artium2_1_html'));

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_artium2_1_html', 1, 
 '/doc(/[^#]*)', 
vector ('par_1'), 
1, 
'/describe/?uri=$ARGV[6]/id%s&graph=$ARGV[7]', 
vector ('par_1'), 
NULL, 
'(text/html)', 
0, 
303, 
'' 
);