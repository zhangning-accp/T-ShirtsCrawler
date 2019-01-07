package com.sjw.mail;

/**
 *
 * 标题：项目研发
 *
 * 模块：公共部分
 *
 * 公司: yyang
 *
 * 作者：meteors，2009-4-16
 *
 * 描述：底层静态变量
 *
 * 说明:
 *
 */
public class EnergyConstants {

	// 组织机构根节点
	public static final String ORG_ROOT = "1";
	// 工程标示
	public static final String PROJECT = "pmipEnergy";
	// 包名标示
	public static final String PACKAGE = "package";
	// 系统session标示
	public static final String ENERGY_SESSION = "energy_session_";
	// 系统request标示
	public static final String ENERGY_REQUEST = "energy_request_";
	// 成功状态
	public static final String DEFAULT_STATUS_SUCESS = "success";
	// 失败状态
	public static final String DEFAULT_STATUS_FAILED = "failed";
	// 但前用户session
	public static final String CUR_USER_SESSION = "current_user_session";
	// 表对象
	public static final String ENERGY_TABLE_OBJECT = "energy_tableobject";
	// 基本标示状态
	public static final String SYSTEM_ZT_00 = "0";
	public static final String SYSTEM_ZT_01 = "1";
	public static final String SYSTEM_ZT_02 = "2";
	public static final String SYSTEM_ZT_03 = "3";

	// 系统开关
	public static final boolean debuggingOn = true;

	// 状态表示
	public static final String STATE_TRUE = "true";
	public static final String STATE_FALSE = "false";
	/**
	 * 是否校验后保存校验
	 */
	public static final String Validate_TRUE = "save";
	public static final String Validate_FALSE = "not save";
	/**
	 * 方法标识
	 */
	public static final String Method_index = "index";
	public static final String Method_add = "add";
	public static final String Method_edit = "edit";
	public static final String Method_delete = "delete";
	public static final String Method_store = "store";
	// 只读属性
	public static final String Readonly_TRUN = "true";
	public static final String Readonly_FALSE = "false";
	// 组织机构隶属号开头
	public static final String ORG_SUBID = "C";
	// 隶属号终止时间
	public static final String SUBORGEndDate = "2050-01-01";
	// 组织机构级别,最小和最大级别
	public static final int Min_Level = 1;
	public static final int Max_Level = 9;

	// 消息提示通过管理
	public static final String ValidateMsg(String message) {
		return "数据库已存在与下列字段完全相同的值：" + message;
	};
}
