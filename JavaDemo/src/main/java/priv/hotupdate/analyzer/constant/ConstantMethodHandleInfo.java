package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 表示方法句柄
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:24:32  guojijun
 */
public class ConstantMethodHandleInfo extends AbstractConstantInfo {

	private int referenceKind;
	/**常量池索引*/
	private int referenceIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.referenceKind = input.readByte();
		this.referenceIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantMethodHandleInfo[referenceKind:%s,referenceIndex:%s]", this.referenceKind,
			this.referenceIndex);
	}

	public int getReferenceKind() {
		return referenceKind;
	}

	public int getReferenceIndex() {
		return referenceIndex;
	}

}
