package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 字符串类型字面量
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午9:00:23  guojijun
 */
public class ConstantStringInfo extends AbstractConstantInfo {

	private int utf8InfoIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.utf8InfoIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantStringInfo[utf8InfoIndex:%s]", this.utf8InfoIndex);
	}

	public int getUtf8InfoIndex() {
		return utf8InfoIndex;
	}

}
