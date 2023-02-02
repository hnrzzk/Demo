package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 整形字面量
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午7:43:21  guojijun
 */
public class ConstantIntegerInfo extends AbstractConstantInfo {

	private int value;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.value = input.readInt();
	}

	@Override
	public String toString() {
		return String.format("ConstantIntegerInfo[value:%s]", this.value);
	}

	public int getValue() {
		return value;
	}

}
