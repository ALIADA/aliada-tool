CREATE PROCEDURE associate_list_ext_rules_to_virtpath (IN create_virtual_path INTEGER, IN urrl_list_subset INTEGER, IN rules_suffix_dataset VARCHAR, IN rules_suffix VARCHAR, IN vhost VARCHAR, IN lhost VARCHAR, IN uri_doc_concept_parent VARCHAR)
{
--It associates LIST rules, with extension, to parent folder (if not existing, it creates the parent folder)
  DECLARE p_urrl_inx INTEGER;
  DECLARE urrl_member_1, urrl_member_2, urrl_member_3, urrl_member_4, urrl_member_5 VARCHAR;
  DECLARE p_urrl_list VARCHAR;
  IF (urrl_list_subset > 0)
    {
     p_urrl_list := sprintf('http_rule_list_%s_Doc', rules_suffix_dataset);
    }
  ELSE
    {
     p_urrl_list := sprintf('http_rule_list_%s_Root', rules_suffix_dataset);
    }

  urrl_member_1:= sprintf ('http_rule_%s_Doc_list_extension_n3', rules_suffix);
  urrl_member_2 := sprintf ('http_rule_%s_Doc_list_extension_xml', rules_suffix);
  urrl_member_3 := sprintf ('http_rule_%s_Doc_list_extension_json', rules_suffix);
  urrl_member_4 := sprintf ('http_rule_%s_Doc_list_extension_jsonld', rules_suffix);
  urrl_member_5 := sprintf ('http_rule_%s_Doc_list_extension_nt', rules_suffix);
  IF(create_virtual_path > 0)
    {
--Create new Virtual Path, including the List with extension rules
      DECLARE lpath, ppath, rules_list, uri_doc_concept_parent_noslash, rules_list_name VARCHAR;
      lpath := sprintf('/%s',uri_doc_concept_parent);
      ppath := sprintf('/DAV/home/%s/',uri_doc_concept_parent);
      uri_doc_concept_parent_noslash := REPLACE(uri_doc_concept_parent, '/', '');
      rules_list_name := sprintf('http_rule_list_%s%s_Doc', rules_suffix_dataset, uri_doc_concept_parent_noslash);
      DB.DBA.VHOST_REMOVE (lhost=>lhost, vhost=>vhost, lpath=>lpath);

      DB.DBA.VHOST_DEFINE (lhost=>lhost, vhost=>vhost, lpath=>lpath, ppath=>ppath, is_dav=>1, def_page=>'', vsp_user=>'dba', ses_vars=>0, opts=>vector ('browse_sheet', '', 'url_rewrite', rules_list_name), is_default_host=>0);

      DB.DBA.URLREWRITE_CREATE_RULELIST (rules_list_name, 1, 
  vector (urrl_member_1, urrl_member_2, urrl_member_3, urrl_member_4, urrl_member_5));
    }
  ELSE
    {
--Add List rules with extension, to existing virtual path
      SELECT MAX(URRL_INX) INTO p_urrl_inx FROM DB.DBA.URL_REWRITE_RULE_LIST WHERE URRL_LIST=p_urrl_list;
      p_urrl_inx := p_urrl_inx + 1;
      INSERT INTO DB.DBA.URL_REWRITE_RULE_LIST (URRL_LIST, URRL_INX, URRL_MEMBER) VALUES (p_urrl_list, p_urrl_inx, urrl_member_1);
      p_urrl_inx := p_urrl_inx + 1;
      INSERT INTO DB.DBA.URL_REWRITE_RULE_LIST (URRL_LIST, URRL_INX, URRL_MEMBER) VALUES (p_urrl_list, p_urrl_inx, urrl_member_2);
      p_urrl_inx := p_urrl_inx + 1;
      INSERT INTO DB.DBA.URL_REWRITE_RULE_LIST (URRL_LIST, URRL_INX, URRL_MEMBER) VALUES (p_urrl_list, p_urrl_inx, urrl_member_3);
      p_urrl_inx := p_urrl_inx + 1;
      INSERT INTO DB.DBA.URL_REWRITE_RULE_LIST (URRL_LIST, URRL_INX, URRL_MEMBER) VALUES (p_urrl_list, p_urrl_inx, urrl_member_4);
      p_urrl_inx := p_urrl_inx + 1;
      INSERT INTO DB.DBA.URL_REWRITE_RULE_LIST (URRL_LIST, URRL_INX, URRL_MEMBER) VALUES (p_urrl_list, p_urrl_inx, urrl_member_5);
    }
};

CREATE PROCEDURE replace_symbols_and_equal (IN p_rule_name VARCHAR)
{
--It replaces the '=' and '&' symbols in the rule definition of the rule specified as input parameter
  DECLARE rule_def VARCHAR;
  SELECT URR_TARGET_FORMAT INTO rule_def FROM DB.DBA.URL_REWRITE_RULE WHERE URR_RULE=p_rule_name;
--Replace '=' and '&' symbols in rule definition
  rule_def := REPLACE(rule_def, '%%3D', '=');
  rule_def := REPLACE(rule_def, '%%26', '&'); 
  UPDATE DB.DBA.URL_REWRITE_RULE SET URR_TARGET_FORMAT=rule_def WHERE URR_RULE=p_rule_name;
};

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
--Domain name default page
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
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_Root'),
	 is_default_host=>0
);
DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_Root', 1, 
  vector ('http_rule_$u{rules_suffix}_Ont_extension_n3', 'http_rule_$u{rules_suffix}_Ont_extension_xml', 'http_rule_$u{rules_suffix}_Ont_extension_json'));

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Ont_extension_n3', 1, 
'/$u{uri_def}(.*)\\.(ttl)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=n3', 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Ont_extension_xml', 1, 
'/$u{uri_def}(.*)\\.(rdf)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=rdf', 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Ont_extension_json', 1, 
'/$u{uri_def}(.*)\\.(json)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&output=application%%2Frdf%%2Bjson', 
vector ('par_1'), 
NULL, 
NULL, 
2, 
0, 
'' 
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
	 lpath=>'/$u{uri_id}',
	 ppath=>'/DAV/home/$u{uri_id}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_Id'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_Id', 1, 
  vector ('http_rule_$u{rules_suffix}_Id_n3', 'http_rule_$u{rules_suffix}_Id_xml', 'http_rule_$u{rules_suffix}_Id_json', 'http_rule_$u{rules_suffix}_Id_html', 'http_rule_$u{rules_suffix}_Id_other'));


DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Id_n3', 1, 
'/$u{uri_id}/([^#]*)', 
vector ('par_1'), 
1, 
'$u{uri_doc_slash}%s', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/rdf.n3)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Id_xml', 1, 
'/$u{uri_id}/([^#]*)', 
vector ('par_1'), 
1, 
'$u{uri_doc_slash}%s', 
vector ('par_1', '*accept*'), 
NULL, 
'(application/rdf.xml)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Id_json', 1, 
'/$u{uri_id}/([^#]*)', 
vector ('par_1'), 
1, 
'$u{uri_doc_slash}%s', 
vector ('par_1', '*accept*'), 
NULL, 
'(application/rdf.json)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Id_html', 1, 
'/$u{uri_id}/([^#]*)', 
vector ('par_1'), 
1, 
'$u{uri_doc_slash}%s', 
vector ('par_1', '*accept*'), 
NULL, 
'(text/html)', 
2, 
303, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Id_other', 1, 
'/$u{uri_id}/([^#]*)', 
vector ('par_1'), 
1, 
'$u{uri_doc_slash}%s', 
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
	 lpath=>'/$u{uri_def}',
	 ppath=>'/DAV/home/$u{uri_def}/',
	 is_dav=>1,
	 def_page=>'',
	 vsp_user=>'dba',
	 ses_vars=>0,
	 opts=>vector ('browse_sheet', '', 'url_rewrite', 'http_rule_list_$u{rules_suffix}_Ont'),
	 is_default_host=>0
);

DB.DBA.URLREWRITE_CREATE_RULELIST ( 
'http_rule_list_$u{rules_suffix}_Ont', 1, 
  vector ('http_rule_$u{rules_suffix}_Ont_no_extension_rdf', 'http_rule_$u{rules_suffix}_Ont_no_extension_others'));

  
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Ont_no_extension_rdf', 1, 
'/$u{uri_def}([^.]*)', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=%U', 
vector ('*accept*'), 
NULL, 
'(text/rdf.n3)|(application/rdf.xml)|(application/rdf.json)|(application/ld.json)|(text/plain)', 
2, 
0, 
'' 
);
 
DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Ont_no_extension_others', 1, 
'/$u{uri_def}([^.]*)', 
vector ('par_1'), 
1, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20FROM%%20%%3C$u{aliada_ont_encoded}%%3E%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&format=html', 
vector ('par_1'), 
NULL, 
'(text/html)|(\\*/\\*)', 
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

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_extension_jsonld', 1, 
'/($u{uri_doc_concept})\\.(jsonld)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&output=application%%2Fld%%2Bjson', 
vector ('par_1'), 
NULL, 
NULL, 
1, 
0, 
'' 
);

DB.DBA.URLREWRITE_CREATE_REGEX_RULE ( 
'http_rule_$u{rules_suffix}_Doc_list_extension_nt', 1, 
'/($u{uri_doc_concept})\\.(nt)', 
vector ('par_1', 'par_2'),
2, 
'/sparql?query=define%%20sql%%3Adescribe-mode%%20%%22LOD%%22%%20DESCRIBE%%20%%3Fa%%20$u{graphs_select_encoded}%%20WHERE%%20%%7B%%3Fa%%20%%3Fb%%20%%3Fc%%7D&output=text%%2Fplain', 
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
