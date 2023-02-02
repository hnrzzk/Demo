package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 字段或方法的符号引用
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:17:22  guojijun
 */
public class ConstantNameAndTypeInfo extends AbstractConstantInfo {

	/**字段或者方法名称常量池索引*/
	private int namdIndex;
	/**字段或者方法描述常量池索引*/
	private int describeIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.namdIndex = input.readUnsignedShort();
		this.describeIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantNameAndTypeInfo[namdIndex:%s,describeIndex:%s]", this.namdIndex,
			this.describeIndex);
	}

	public int getNamdIndex() {
		return namdIndex;
	}

	public int getDescribeIndex() {
		return describeIndex;
	}

}
