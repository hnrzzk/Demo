package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 常量数据接口
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午6:26:32  guojijun
 */
public abstract class AbstractConstantInfo {

	private ConstantInfoEnum constantInfoEnum;

	/**
	 * 解析
	 * 
	 * @param input
	 */
	abstract void parse(DataInputStream input) throws IOException;

	public ConstantInfoEnum getConstantInfoEnum() {
		return constantInfoEnum;
	}

	void setConstantInfoEnum(ConstantInfoEnum constantInfoEnum) {
		this.constantInfoEnum = constantInfoEnum;
	}

}
