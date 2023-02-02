package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 双精度浮点型字面量
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午8:34:01  guojijun
 */
public class ConstantDoubleInfo extends AbstractConstantInfo {

	private double value;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.value = input.readDouble();
	}

	@Override
	public String toString() {
		return String.format("ConstantDoubleInfo[value:%s]", this.value);
	}

	public double getValue() {
		return value;
	}

}
