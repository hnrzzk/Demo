package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 表示一个动态方法调用点
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:43:28  guojijun
 */
public class ConstantInvokeDynamicInfo extends AbstractConstantInfo {

	private int bootstrapMethodAttrIndex;
	/**常量池索引*/
	private int nameAndTypeIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.bootstrapMethodAttrIndex = input.readUnsignedShort();
		this.nameAndTypeIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantInvokeDynamicInfo[bootstrapMethodAttrIndex:%s,nameAndTypeIndex:%s]",
			this.bootstrapMethodAttrIndex, this.nameAndTypeIndex);
	}

	public int getBootstrapMethodAttrIndex() {
		return bootstrapMethodAttrIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

}
