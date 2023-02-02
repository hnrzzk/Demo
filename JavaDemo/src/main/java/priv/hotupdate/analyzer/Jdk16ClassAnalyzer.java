package priv.hotupdate.analyzer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * 1.6版本的类解析器
 *
 * @author guojijun
 * @version v0.1 2017-9-2 下午6:14:45  guojijun
 */
class Jdk16ClassAnalyzer implements IClassAnalyzer {
	@Override
	public ClassAnalyzeResult analyze(File classFile) {
		DataInputStream input = null;
		try {
			input = new DataInputStream(new FileInputStream(classFile));
			int val = input.readInt();
			if (val != CLASS_MAGIC) {// 读取魔数(固定值)
				return null;
			}
			val = input.readUnsignedShort();// 次版本号
			val =input.readUnsignedShort();// 主版本号
			val =input.readUnsignedShort();// 长度
			val =input.readByte();// CLASS=7
			val =input.readUnsignedShort();// 忽略这个地方
			val =input.readByte();// UTF8=1

			ClassAnalyzeResult analyzeResult = new ClassAnalyzeResult();
			analyzeResult.setQualifiedName(input.readUTF());// 类的名字
			return analyzeResult;
		} catch (IOException e) {
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
		return "1.6";
	}

}
