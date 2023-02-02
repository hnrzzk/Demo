package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 类或接口的符号引用
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午8:53:18  guojijun
 */
public class ConstantClassInfo extends AbstractConstantInfo {

	private int utf8InfoIndex;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.utf8InfoIndex = input.readUnsignedShort();
	}

	@Override
	public String toString() {
		return String.format("ConstantClassInfo[utf8InfoIndex:%s]", this.utf8InfoIndex);
	}

	public int getUtf8InfoIndex() {
		return utf8InfoIndex;
	}

}
