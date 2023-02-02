package priv.hotupdate.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.hotupdate.analyzer.constant.AbstractConstantInfo;
import priv.hotupdate.analyzer.constant.ConstantClassInfo;
import priv.hotupdate.analyzer.constant.ConstantInfoEnum;
import priv.hotupdate.analyzer.constant.ConstantUtf8Info;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 1.7版本的类解析器
 *
 * @author guojijun
 * @version v0.1 2017-9-2 下午6:14:45  guojijun
 */
class Jdk17ClassAnalyzer implements IClassAnalyzer {

	@Override
	public ClassAnalyzeResult analyze(File classFile) {
		DataInputStream input = null;
		try {
			input = new DataInputStream(new FileInputStream(classFile));
			if (input.readInt() != CLASS_MAGIC) {// 读取魔数(固定值)
				return null;
			}
			input.readUnsignedShort();// 次版本号
			input.readUnsignedShort();// 主版本号

			ClassAnalyzeResult analyzeResult = new ClassAnalyzeResult();

			int constantPoolCount = input.readUnsignedShort();
			AbstractConstantInfo constantInfo;
			for (int index = 1; index < constantPoolCount; index++) {
				constantInfo = ConstantInfoEnum.parseConstantInfo(input);
				analyzeResult.addConstantInfo(index, constantInfo);
				// long和double特殊,会跳一个索引
				if (constantInfo.getConstantInfoEnum() == ConstantInfoEnum.CONSTANT_DOUBLE_INFO
					|| constantInfo.getConstantInfoEnum() == ConstantInfoEnum.CONSTANT_LONG_INFO) {
					index++;
				}
			}

			input.readUnsignedShort();// 访问标志

			// 读取完整类名(包括包名)
			int classInfoIndex = input.readUnsignedShort();
			ConstantClassInfo constantClassInfo = analyzeResult.getConstantInfo(classInfoIndex);
			ConstantUtf8Info constantUtf8Info = analyzeResult.getConstantInfo(constantClassInfo.getUtf8InfoIndex());
			analyzeResult.setQualifiedName(constantUtf8Info.getValue());
			return analyzeResult;
		} catch (Exception e) {
			System.out.println(CLASS_RELOAD_CLASS_READ_CLASS_NAME_FAILED);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	@Override
	public String getVersion() {
		return "1.7";
	}

}
