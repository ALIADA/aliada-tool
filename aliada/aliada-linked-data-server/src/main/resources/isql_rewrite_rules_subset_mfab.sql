----------------------------------------------------------------
--Input arguments
----------------------------------------------------------------
--$u{lhost}: listening_host (no *ini*, choose another port so that default page for the dataset works properly), e.g.: :8891
--$u{vhost}: virtual_host (no *ini*, choose another port so that default page for the dataset works properly), e.g: data.szepmuveszeti.hu
--$u{uri_id}: uri_id_part, e.g.: id, id/resource
--$u{uri_doc_slash}: "/" +  uri_doc_part + "/", e.g.: / , /doc/
--$u{uri_def}: uri_def_part, e.g.: def
--$u{graphs_select_encoded}: URL encoded dataset graphs uri-s with FROM form, e.g.: FROM <http://aliada.artium.org> FROM <http://aliada_graph1>
--$u{graphs_encoded}: URL encoded dataset graphs uri-s with & form, e.g.: &graph=http://aliada.artium.org&graph=http://aliada_graph1
--$u{domain_name_encoded}: URL encoded domain_name, e.g.: data.artium.org
--$u{rules_suffix}: rulesNamesSuffix, e.g.: dataartiumorg
--$u{uri_doc_concept}: uri_doc_part + dataset.uri_concept_part, e.g.: doc, collections, doc/collections
--$u{dataset_page}: dataset_page, e.g.: dataset.html
--$u{aliada_ont_encoded}: URL encoded aliada_uri, e.g.: http://aliada-project.eu/2014/aliada-ontology# URL encoded
--$u{uri_id_encoded}: URL encoded uri_id_part, e.g.: id%2Fresource
--$u{create_virtual_path}: specifies whether it is required to create a new virtual path for the DOC listing with extension (1 or 0).
--$u{urrl_list_subset}: specifies whether it refers to the global dataset or a subset (1 or 0).
--$u{rules_suffix_dataset}: rulesNamesSuffix for the global dataset, e.g.: dataartiumorg
--$u{uri_doc_concept_parent}: parent path of uri_doc_part + dataset.uri_concept_part, e.g.: '' for 'doc', 'doc' for 'doc/collections'
----------------------------------------------------------------


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
	 lpath=>'/$u{uri_doc_concept}',
	 ppath=>'/DAV/home/$u{uri_doc_concept}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_Doc'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_Doc', 1, 
  vector ('http_rule_$u{rules_suffix}_Doc_list_no_extension_rdf', 'http_rule_$u{rules_suffix}_Doc_list_no_extension_others', 'http_rule_$u{rules_suffix}_Doc_no_extension_rdf', 'http_rule_$u{rules_suffix}_Doc_no_extension_htmlvirtuoso', 
  'http_rule_$u{rules_suffix}_Doc_extension_n3', 'http_rule_$u{rules_suffix}_Doc_extension_xml', 'http_rule_$u{rules_suffix}_Doc_extension_json', 'http_rule_$u{rules_suffix}_Doc_extension_jsonld', 'http_rule_$u{rules_suffix}_Doc_extension_nt', 'http_rule_$u{rules_suffix}_Doc_extension_html', 'http_rule_$u{rules_suffix}_Doc_extension_opac'));

	
--List URI-s of a dataset
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_no_extension_rdf', 1, 
'/($u{uri_doc_concept})\$', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=%U', 
vector ('*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
1, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_no_extension_others', 1, 
'/($u{uri_doc_concept})\$', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=html', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_extension_n3', 1, 
'/($u{uri_doc_concept})\\.(ttl)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=n3', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_extension_xml', 1, 
'/($u{uri_doc_concept})\\.(rdf)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_extension_json', 1, 
'/($u{uri_doc_concept})\\.(json)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&output=application%%2Frdf%%2Bjson', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

--Doc URI-s	
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_no_extension_rdf', 1, 
'($u{uri_doc_slash})([^#]*)', 
vector ('par_1', 'par_2'),  
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&format=%U', 
vector ('par_2', '*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
2, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_no_extension_htmlvirtuoso', 1, 
'($u{uri_doc_slash})([^#]*)', 
vector ('par_1', 'par_2'),  
2, 
'/fct/rdfdesc/description.vsp?g=http%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%s$u{graphs_encoded}', 
vector ('par_2'), 
NULL, 
'(text/html)|(\\*/\\*)', 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_n3', 1, 
'($u{uri_doc_slash})([^#]*)\\.(ttl)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&format=n3', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_xml', 1, 
'($u{uri_doc_slash})([^#]*)\\.(rdf)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&format=rdf', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_json', 1, 
'($u{uri_doc_slash})([^#]*)\\.(json)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&output=application%%2Frdf%%2Bjson', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_jsonld', 1, 
'($u{uri_doc_slash})([^#]*)\\.(jsonld)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&output=application%%2Fld%%2Bjson', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_nt', 1, 
'($u{uri_doc_slash})([^#]*)\\.(nt)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Chttp%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%U%%3E%%20$u{graphs_select_encoded}&output=text%%2Fplain', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_html', 1, 
'($u{uri_doc_slash})([^#]*)\\.(html)', 
vector ('par_1', 'par_2', 'par_3'),
3, 
'/fct/rdfdesc/description.vsp?g=http%%3A%%2F%%2F$u{domain_name_encoded}%%2F$u{uri_id_encoded}%%2F%s$u{graphs_encoded}', 
vector ('par_2'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_extension_opac', 1, 
'($u{uri_doc_slash})([^#]*)/([^#/]*)_([^#/_]*)\\.(opac)', 
vector ('par_1', 'par_2', 'par_3', 'par_4', 'par_5'),
4, 
'http://www.szepmuveszeti.hu/adatlap/%s', 
vector ('par_4'), 
NULL, 
NULL, 
2, 
303, 
'' 
);

replace_symbols_and_equal('http_rule_$u{rules_suffix}_Doc_no_extension_htmlvirtuoso');
replace_symbols_and_equal('http_rule_$u{rules_suffix}_Doc_extension_html');
replace_symbols_and_equal('http_rule_$u{rules_suffix}_Doc_extension_opac');
associate_list_ext_rules_to_virtpath ($u{create_virtual_path}, $u{urrl_list_subset}, '$u{rules_suffix_dataset}', '$u{rules_suffix}', '$u{vhost}', '$u{lhost}', '$u{uri_doc_concept_parent}');
