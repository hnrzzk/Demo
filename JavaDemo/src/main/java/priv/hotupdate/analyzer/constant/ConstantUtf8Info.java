package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * UTF-8编码的字符串
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午7:17:15  guojijun
 */
public class ConstantUtf8Info extends AbstractConstantInfo {

	private String value;

	@Override
	void parse(DataInputStream input) throws IOException {
		int length = input.readUnsignedShort();
		byte[] bs = new byte[length];
		input.read(bs);
		this.value = new String(bs, Charset.forName("utf8"));
	}

	@Override
	public String toString() {
		return String.format("ConstantUtf8Info[value:%s]", this.value);
	}

	public String getValue() {
		return value;
	}

}
