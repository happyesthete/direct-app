insert into comp_catalog(component_id , current_version , short_desc , component_name , description , function_desc , create_time , status_id , root_category_id , modify_date) values (100, 1 ,"sd" , "cn", "desc", "fd", CURRENT, 301, null,CURRENT);
insert into comp_versions(comp_vers_id ,component_id, version, version_text, create_time, phase_id, phase_time, price, comments, modify_date, suspended_ind) values (200, 100, 1 ,"1.0" ,CURRENT ,1 ,CURRENT , 2500, "c",CURRENT ,0);