package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 接口中方法的符号引用
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:13:28  guojijun
 */
public class ConstantInterfaceMethodRefInfo extends AbstractConstantInfo {

	private int classInfoIndex;
	private int nameAndTypeIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.classInfoIndex = input.readUnsignedShort();
		this.nameAndTypeIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantInterfaceMethodRefInfo[classInfoIndex:%s,nameAndTypeIndex:%s]",
			this.classInfoIndex, this.nameAndTypeIndex);
	}

	public int getClassInfoIndex() {
		return classInfoIndex;
	}

	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

}
