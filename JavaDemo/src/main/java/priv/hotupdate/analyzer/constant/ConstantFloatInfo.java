package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * 浮点型字面量
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午8:23:11  guojijun
 */
public class ConstantFloatInfo extends AbstractConstantInfo {

	private float value;

	@Override
	void parse(DataInputStream input) throws IOException {
		this.value = input.readFloat();
	}

	@Override
	public String toString() {
		return String.format("ConstantFloatInfo[value:%s]", this.value);
	}

	public float getValue() {
		return value;
	}

}
