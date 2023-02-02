package priv.hotupdate.analyzer.constant;

import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量枚举
 *
 * @author guojijun
 * @version v0.1 2017-9-3 下午5:34:49  guojijun
 */
public enum ConstantInfoEnum {

	CONSTANT_UTF8_INFO(1, ConstantUtf8Info.class),

	CONSTANT_INTEGER_INFO(3, ConstantIntegerInfo.class),

	CONSTANT_FLOAT_INFO(4, ConstantFloatInfo.class),

	CONSTANT_LONG_INFO(5, ConstantLongInfo.class),

	CONSTANT_DOUBLE_INFO(6, ConstantDoubleInfo.class),

	CONSTANT_CLASS_INFO(7, ConstantClassInfo.class),

	CONSTANT_STRING_INFO(8, ConstantStringInfo.class),

	CONSTANT_FIELD_REF_INFO(9, ConstantFieldRefInfo.class),

	CONSTANT_METHOD_REF_INFO(10, ConstantMethodRefInfo.class),

	CONSTANT_INTERFACE_METHOD_REF_INFO(11, ConstantInterfaceMethodRefInfo.class),

	CONSTANT_NAME_AND_TYPE_INFO(12, ConstantNameAndTypeInfo.class),

	CONSTANT_METHOD_HANDLE_INFO(15, ConstantMethodHandleInfo.class),

	CONSTANT_METHOD_TYPE_INFO(16, ConstantMethodTypeInfo.class),

	CONSTANT_INVOKEDYNAMIC_INFO(18, ConstantInvokeDynamicInfo.class), ;

	private final int index;
	private final Class<? extends AbstractConstantInfo> clasz;

	private static final Map<Integer, ConstantInfoEnum> map = new HashMap<>();

	static {
		for (ConstantInfoEnum constantInfoEnum : values()) {
			map.put(constantInfoEnum.index, constantInfoEnum);
		}
	}

	/**
	 * 解析常量数据
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static AbstractConstantInfo parseConstantInfo(DataInputStream input) throws Exception {
		final int index = input.readUnsignedByte();
		ConstantInfoEnum constantInfoEnum = map.get(index);
		if (constantInfoEnum != null) {
			AbstractConstantInfo constantInfo = constantInfoEnum.clasz.newInstance();
			constantInfo.setConstantInfoEnum(constantInfoEnum);
			constantInfo.parse(input);
			return constantInfo;
		} else {
			throw new Exception("未找到常量,index:" + index);
		}
	}

	private ConstantInfoEnum(int index, Class<? extends AbstractConstantInfo> clasz) {
		this.index = index;
		this.clasz = clasz;
	}

}
