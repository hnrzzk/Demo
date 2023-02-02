package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 标志方法类型
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:38:12  guojijun
 */
public class ConstantMethodTypeInfo extends AbstractConstantInfo {

	/**常量池索引*/
	private int descriptorIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.descriptorIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantMethodTypeInfo[descriptorIndex:%s]", this.descriptorIndex);
	}

	public int getDescriptorIndex() {
		return descriptorIndex;
	}

}
