-- 1.删除表 app_menu记录，触发删除 app_role_menu, app_user_permission 中对应的记录
DROP TRIGGER IF EXISTS tri_del_app_menu;
CREATE TRIGGER `tri_del_app_menu` BEFORE DELETE ON `app_menu` FOR EACH ROW BEGIN
	DELETE a FROM app_role_menu a WHERE a.menu_id = old.id;
	DELETE a FROM app_user_permission a WHERE a.menu_id = old.id;

END;

-- 2.删除表 app_role 触发删除 app_role_menu, app_user_role
DROP TRIGGER IF EXISTS tri_del_app_role;
CREATE TRIGGER `tri_del_app_role` BEFORE DELETE ON `app_role` FOR EACH ROW BEGIN
	DELETE a FROM app_role_menu a WHERE a.role_id = old.id;
	DELETE a FROM app_user_role a WHERE a.role_id = old.id;

END;

-- 3.删除 app_user ,触发删除 app_user_permission,app_user_role,app_user_scope
DROP TRIGGER IF EXISTS tri_del_app_user;
CREATE TRIGGER `tri_del_app_user` BEFORE DELETE ON `app_user` FOR EACH ROW BEGIN
	DELETE a FROM app_user_permission a WHERE a.user_id = old.id;
	DELETE a FROM app_user_role a WHERE a.user_id = old.id;
	DELETE a FROM app_user_scope a WHERE a.user_id = old.id;

END;

-- 4.删除 sys_menu ,触发删除 sys_role_menu,
DROP TRIGGER IF EXISTS tri_del_sys_menu;
CREATE TRIGGER `tri_del_sys_menu` BEFORE DELETE ON `sys_menu` FOR EACH ROW BEGIN
	DELETE a FROM sys_role_menu a WHERE a.menu_id = old.id;	

END;



-- 5.删除 sys_role ,触发删除 sys_role_menu,
DROP TRIGGER IF EXISTS tri_del_sys_role;
CREATE TRIGGER `tri_del_sys_role` BEFORE DELETE ON `sys_role` FOR EACH ROW BEGIN
	DELETE a FROM sys_role_menu a WHERE a.role_id = old.id;	

END;

-- 6.删除 sys_user ,触发删除 sys_user_role,
DROP TRIGGER IF EXISTS tri_del_sys_user;
CREATE TRIGGER `tri_del_sys_user` BEFORE DELETE ON `sys_user` FOR EACH ROW BEGIN
	DELETE a FROM sys_user_role a WHERE a.user_id = old.id;	

END;

-- 7.删除wx_account 触发删除 
DROP TRIGGER IF EXISTS tri_del_wx_account;
CREATE TRIGGER `tri_del_wx_account` BEFORE DELETE ON `wx_account` FOR EACH ROW BEGIN
	DELETE a FROM wx_areply a WHERE a.account_id=old.id;
	DELETE n FROM wx_news n WHERE n.account_id=old.id;
	DELETE n FROM wx_news_multi n WHERE n.account_id=old.id;
	DELETE n FROM wx_news_article n WHERE n.account_id=old.id;
	DELETE n FROM wx_news_multi n WHERE n.account_id=old.id;
	DELETE n FROM wx_text n WHERE n.account_id=old.id;
	DELETE n FROM wx_mass_msg n WHERE n.account_id=old.id;
	DELETE n FROM wx_material_images n WHERE n.account_id=old.id;
	DELETE n FROM wx_msg_tpl n WHERE n.account_id=old.id;	

END;

-- 8.删除 wx_mass_msg_common ,触发删除 wx_mass_msg,
DROP TRIGGER IF EXISTS tri_del_wx_mass_msg_common;
CREATE TRIGGER `tri_del_wx_mass_msg_common` BEFORE DELETE ON `wx_mass_msg_common` FOR EACH ROW BEGIN
	DELETE a FROM wx_mass_msg a WHERE a.msg_id = old.id;	

END;

-- 9.删除 wx_msg_tpl_common ,触发删除 wx_msg_tpl,
DROP TRIGGER IF EXISTS tri_del_wx_msg_tpl_common;
CREATE TRIGGER `tri_del_wx_msg_tpl_common` BEFORE DELETE ON `wx_msg_tpl_common` FOR EACH ROW BEGIN
	DELETE a FROM wx_msg_tpl a WHERE a.common_tpl_id = old.id;	
	DELETE a FROM wx_mass_msg_common a WHERE a.tpl_id = old.id;

END;

-- 10.删除 wy_act_candidate ,触发删除 wy_act_candidate_detail,
DROP TRIGGER IF EXISTS tri_del_wy_act_candidate;
CREATE TRIGGER `tri_del_wy_act_candidate` BEFORE DELETE ON `wy_act_candidate` FOR EACH ROW BEGIN
	DELETE a FROM wy_act_candidate_detail a WHERE a.act_candidate_id = old.id;	

END;


-- 11.删除 wy_act_candidate ,触发删除 wy_act_candidate_detail,
DROP TRIGGER IF EXISTS tri_del_wy_act_candidate;
CREATE TRIGGER `tri_del_wy_act_candidate` BEFORE DELETE ON `wy_act_candidate` FOR EACH ROW BEGIN
	DELETE a FROM wy_act_candidate_detail a WHERE a.act_candidate_id = old.id;	

END;


-- 12.删除 wy_act_def ,触发删除 wy_act_business,wy_act_candidate,wy_act_form,wy_act_handler,wy_act_instance,wy_act_job,wy_act_relation,wy_approve,wy_approve_detail,wy_re_biz_act
DROP TRIGGER IF EXISTS tri_del_wy_act_def;
CREATE TRIGGER `tri_del_wy_act_def` BEFORE DELETE ON `wy_act_def` FOR EACH ROW BEGIN
	DELETE a FROM wy_act_business a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_candidate a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_form a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_handler a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_instance a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_job a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_act_relation a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_approve a WHERE a.proc_def_id = old.proc_def_id;
	DELETE a FROM wy_re_biz_act a WHERE a.act_id = old.id;
END;


-- 13.删除 wy_approve ,触发删除 ,wy_approve_detail,wy_inst_candidate
DROP TRIGGER IF EXISTS tri_del_wy_approve;
CREATE TRIGGER `tri_del_wy_approve` BEFORE DELETE ON `wy_approve` FOR EACH ROW BEGIN
	DELETE a FROM wy_approve_detail a WHERE a.proc_ins_id = old.proc_inst_id;	
	DELETE a FROM wy_inst_candidate a WHERE a.proc_inst_id = old.proc_inst_id;

END;



-- 14.删除 wy_repair ,触发删除 
DROP TRIGGER IF EXISTS tri_del_wy_repair;
CREATE TRIGGER `tri_del_wy_repair` BEFORE DELETE ON `wy_repair` FOR EACH ROW BEGIN
	DELETE a FROM wy_repair_budget a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_budget_labor a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_budget_materiel a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_form a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_settlement a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_settlement_labor a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_repair_settlement_materiel a WHERE a.repair_id = old.id;	
	DELETE a FROM wy_evaluate a WHERE a.relation_id = old.id;	
	DELETE a FROM wy_approve a WHERE a.proc_inst_id = old.proc_ins_id;
END;


-- 15.删除 wy_complain ,触发删除 
DROP TRIGGER IF EXISTS tri_del_wy_complain;
CREATE TRIGGER `tri_del_wy_complain` BEFORE DELETE ON `wy_complain` FOR EACH ROW BEGIN	
	DELETE a FROM wy_evaluate a WHERE a.relation_id = old.id;	
	DELETE a FROM wy_approve a WHERE a.proc_inst_id = old.proc_ins_id;
END;



-- 16.#会员房屋关联表
DROP TRIGGER IF EXISTS tri_del_member_room;
CREATE TRIGGER `tri_del_member_room` AFTER DELETE ON `wy_member` FOR EACH ROW #这句话在mysql是固定的
begin
DELETE FROM wy_room WHERE memberId = old.member_id;
end;

-- 17.#问卷关联删除分类,题目,选项
DROP TRIGGER IF EXISTS tri_del_questionnaire_cla_sub_opt;
CREATE TRIGGER `tri_del_questionnaire_cla_sub_opt` AFTER DELETE ON `wy_questionnaire` FOR EACH ROW BEGIN
 delete from wy_classification where questionnaireid=old.questionnaireid;
 delete from wy_subject where questionnaireid=old.questionnaireid;
 delete from wy_option where questionnaireid=old.questionnaireid;
END;

-- 18.#分类关联删除题目,选项
DROP TRIGGER IF EXISTS tri_del_classification_subject_option;
CREATE TRIGGER `tri_del_classification_subject_option` AFTER DELETE ON `wy_classification` FOR EACH ROW BEGIN
 delete from wy_subject where classificationid=old.classificationid;
 delete from wy_option where classificationid=old.classificationid;
END;

-- 19.#题目关联删除选项
DROP TRIGGER IF EXISTS tri_del_subject_option;
CREATE TRIGGER `tri_del_subject_option` AFTER DELETE ON `wy_subject` FOR EACH ROW BEGIN
 delete from wy_option where subjectid=old.subjectid;      
END;





