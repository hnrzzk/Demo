package priv.hotupdate.analyzer;

import java.io.File;

/**
 * 类解析器接口
 *
 * @author guojijun
 * @version v0.1 2017-9-2 下午6:11:24  guojijun
 */
public interface IClassAnalyzer {

	/**类文件开头,用来判断文件是否是能被虚拟机接受的Class文件*/
	int CLASS_MAGIC = 0xCAFEBABE;
	String CLASS_RELOAD_CLASS_READ_CLASS_NAME_FAILED = "读取类名称失败";

	/**
	 * 解析
	 * 
	 * @param classFile 类文件
	 * @return
	 */
	ClassAnalyzeResult analyze(File classFile);

	/**
	 * 获取版本
	 * 
	 * @return
	 */
	String getVersion();
}
