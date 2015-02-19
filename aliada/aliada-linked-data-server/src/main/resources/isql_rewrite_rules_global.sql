----------------------------------------------------------------
--Input arguments
----------------------------------------------------------------
--$u{lhost}: listening_host (no *ini*, choose another port so that default page for the dataset works properly), e.g.: :8891
--$u{vhost}: virtual_host (no *ini*, choose another port so that default page for the dataset works properly), e.g: data.szepmuveszeti.hu
--$u{uri_id}: uri_id_part, e.g.: id, id/resource
--$u{uri_doc_slash}: "/" +  uri_doc_part, e.g.: / , /doc
--$u{uri_def}: uri_def_part, e.g.: def
--$u{graphs_select_encoded}: URL encoded dataset graphs uri-s with FROM form, e.g.: FROM <http://aliada.artium.org> FROM <http://aliada_graph1>
--$u{graphs_encoded}: URL encoded dataset graphs uri-s with & form, e.g.: &graph=http://aliada.artium.org&graph=http://aliada_graph1
--$u{domain_name_encoded}: URL encoded domain_name, e.g.: data.artium.org
--$u{rules_suffix}: rulesNamesSuffix, e.g.: dataartiumorg
--$u{uri_doc_concept}: uri_doc_part + dataset.uri_concept_part, e.g.: doc, collections, doc/collections
--$u{dataset_page}: dataset_page, e.g.: dataset.html
--$u{aliada_ont_encoded}: URL encoded aliada_uri, e.g.: http://aliada-project.eu/2014/aliada-ontology# URL encoded
--$u{uri_id_encoded}: URL encoded uri_id_part, e.g.: id%2Fresource
----------------------------------------------------------------

----------------------------------------------------------------
--Dataset default page
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/',
	 ppath=>'/$u{rules_suffix}/',
	 is_dav=>0,
	 def_page=>'$u{dataset_page}',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', ''),
	 is_default_host=>0
);
----------------------------------------------------------------
--Identifier URI : With 303 redirects
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_id}'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_id},
	 ppath=>'/DAV/home/$u{uri_id}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_1'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_1', 1, 
  vector ('http_rule_$u{rules_suffix}_1_n3', 'http_rule_$u{rules_suffix}_1_xml', 'http_rule_$u{rules_suffix}_1_json', 'http_rule_$u{rules_suffix}_1_html', 'http_rule_$u{rules_suffix}_1_other'));


DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_1_n3', 1, 
'/$u{uri_id}(/[^#]*)', 
vector ('par_1'), 
1, 
'/$u{uri_doc_concept}%U.ttl', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_1_xml', 1, 
'/$u{uri_id}(/[^#]*)', 
vector ('par_1'), 
1, 
'/$u{uri_doc_concept}%U.rdf', 
vector ('par_1', '*accept*'), 
NULL, 
'(application/rdf.xml)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_1_json', 1, 
'/$u{uri_id}(/[^#]*)', 
vector ('par_1'), 
1, 
'/$u{uri_doc_concept}%U.json', 
vector ('par_1', '*accept*'), 
NULL, 
'(application/rdf.json)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_1_html', 1, 
'/$u{uri_id}(/[^#]*)', 
vector ('par_1'), 
1, 
'/$u{uri_doc_concept}%U.html', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/html)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_1_other', 1, 
'/$u{uri_id}(/[^#]*)', 
vector ('par_1'), 
1, 
'/$u{uri_doc_concept}%U', 
vector ('par_1', '*accept*'), 
NULL, 
'(application/ld.json)|(text/plain)|(\\*/\\*)', 
2, 
303, 
'' 
);

----------------------------------------------------------------
--Ontology URI 
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_def}'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_def},
	 ppath=>'/DAV/home/$u{uri_def}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_3'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_3', 1, 
  vector ('http_rule_$u{rules_suffix}_3_no_extension_rdf', 'http_rule_$u{rules_suffix}_3_no_extension_others', 'http_rule_$u{rules_suffix}_3_extension_n3', 'http_rule_$u{rules_suffix}_3_extension_xml', 'http_rule_$u{rules_suffix}_3_extension_json'));

  
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_3_no_extension_rdf', 1, 
'/$u{uri_def}(/[^.]*)', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=%U', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
2, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_3_no_extension_others', 1, 
'/$u{uri_def}(/[^.]*)', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_3_extension_n3', 1, 
'/$u{uri_def}(/.*)\\.(ttl)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=n3', 
', 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_3_extension_xml', 1, 
'/$u{uri_def}(/.*)\\.(rdf)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
, 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_3_extension_json', 1, 
'/$u{uri_def}(/.*)\\.(json)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=json', 
', 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

----------------------------------------------------------------
--Document URI
----------------------------------------------------------------
DB.DBA.VHOST_REMOVE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_doc_concept}'
);

DB.DBA.VHOST_DEFINE (
	 lhost=>'$u{lhost}',
	 vhost=>'$u{vhost}',
	 lpath=>'/$u{uri_doc_concept},
	 ppath=>'/DAV/home/$u{uri_doc_concept}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_2'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_2', 1, 
  vector ('http_rule_$u{rules_suffix}_2_list_no_extension_rdf', 'http_rule_$u{rules_suffix}_2_list_no_extension_others', 'http_rule_$u{rules_suffix}_2_list_extension_list_n3', 'http_rule_$u{rules_suffix}_2_list_extension_xml', 'http_rule_$u{rules_suffix}_2_list_extension_json',
  'http_rule_$u{rules_suffix}_2_no_extension_rdf', 'http_rule_$u{rules_suffix}_2_no_extension_htmlvirtuoso', 
  'http_rule_$u{rules_suffix}_2_extension_n3', 'http_rule_$u{rules_suffix}_2_extension_xml', 'http_rule_$u{rules_suffix}_2_extension_json', 'http_rule_$u{rules_suffix}_2_extension_html', 'http_rule_$u{rules_suffix}_2_extension_opac'));

	
--List URI-s of a dataset
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_list_no_extension_rdf', 1, 
'($u{uri_doc_concept})', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=%U', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
1, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_list_no_extension_others', 1, 
'($u{uri_doc_concept})', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
1, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_list_extension_list_n3', 1, 
'($u{uri_doc_concept})\\.(ttl)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=n3', 
', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_list_extension_xml', 1, 
'($u{uri_doc_concept})\\.(rdf)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
, 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_list_extension_json', 1, 
'($u{uri_doc_concept})\\.(json)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20SELECT%%20%%2A%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=json', 
', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

--Doc URI-s	
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_no_extension_rdf', 1, 
'($u{uri_doc_slash})?(/[^.#]*)', 
vector ('par_1', 'par_2'),  
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%U%%3E%%20$u{graphs_select_encoded}&format=%U', 
vector ('par_2', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
2, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_no_extension_htmlvirtuoso', 1, 
'($u{uri_doc_slash})?(/[^.#]*)', 
vector ('par_1', 'par_2'),  
2, 
'/describe/?uri=http%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%s$u{graphs_encoded}', 
vector ('par_2'), 
NULL, 
'(text/html)|(\\*/\\*)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_extension_n3', 1, 
'($u{uri_doc_slash})?(/[^#]*)\\.(ttl)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%U%%3E%%20$u{graphs_select_encoded}&format=n3', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_extension_xml', 1, 
'($u{uri_doc_slash})?(/[^#]*)\\.(rdf)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%U%%3E%%20$u{graphs_select_encoded}&format=rdf', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_extension_json', 1, 
'($u{uri_doc_slash})?(/[^#]*)\\.(json)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%U%%3E%%20$u{graphs_select_encoded}&format=json', 
vector ('par_3'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_extension_html', 1, 
'($u{uri_doc_slash})?(/[^#]*)\\.(html)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/describe/?uri=http%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%s$u{graphs_encoded}', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_2_extension_opac', 1, 
'($u{uri_doc_slash})?(/[^#]*)\\.(opac)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/describe/?uri=http%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%s$u{graphs_encoded}', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
303, 
'' 
);
