package com.gitee.fastmybatis.core.handler;

/**
 * 权限控制算法(位与算)
 * @author tanghc
 */
class Opt {
	public static final int SELECT_RIGHT = 1;
	public static final int INSERT_RIGHT = 2;
	public static final int UPDATE_RIGHT = 3;

	public static final int SELECT_POWER = (int) Math.pow(2, SELECT_RIGHT);
	public static final int INSERT_POWER = (int) Math.pow(2, INSERT_RIGHT);
	public static final int UPDATE_POWER = (int) Math.pow(2, UPDATE_RIGHT);
}

/**
 * 字段填充类型. <br>
 * 字段插入后不能修改用FillType.INSERT，如：create_time字段<br>
 * 字段插入后能修改用FillType.UPDATE，如：update_time字段
 * @author tanghc
 */
public enum FillType {
    /** 查询权限 */
	SELECT(Opt.SELECT_RIGHT, Opt.SELECT_POWER), 
	/** 插入权限 */
	INSERT(Opt.INSERT_RIGHT, Opt.INSERT_POWER + Opt.SELECT_POWER), 
	/** 修改权限 */
	UPDATE(Opt.UPDATE_RIGHT, Opt.SELECT_POWER + Opt.INSERT_POWER + Opt.UPDATE_POWER)
	;

	private int right;
	private int power;

	FillType(int right, int power) {
		this.right = right;
		this.power = power;
	}

	public static boolean checkPower(FillType power, FillType right) {
		return checkPower(power.power, right.right);
	}

	/**
	 * 检查是否有权限
	 * 
	 * @param power
	 *            总权限
	 * @param right
	 *            当前操作权限
	 * @return true，有权限
	 */
	private static boolean checkPower(int power, int right) {
		int purview = (int) Math.pow(2, right);
		return (power & purview) == purview;
	}

}
