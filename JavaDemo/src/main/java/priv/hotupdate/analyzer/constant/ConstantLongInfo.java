package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 长整型字面量
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午8:30:20  guojijun
 */
public class ConstantLongInfo extends AbstractConstantInfo {

	private long value;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.value = input.readLong();
	}

	@Override
	public String toString() {
		return String.format("ConstantLongInfo[value:%s]", this.value);
	}

	public long getValue() {
		return value;
	}

}
