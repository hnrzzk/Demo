package priv.hotupdate.analyzer;


import priv.hotupdate.analyzer.constant.AbstractConstantInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 类解析结果
 *
 * @author guojijun
 * @version v0.1 2017-9-2 下午6:23:10  guojijun
 */
public class ClassAnalyzeResult {
	private String qualifiedName;

	private final Map<Integer, AbstractConstantInfo> constantInfos;

	ClassAnalyzeResult() {
		this.constantInfos = new HashMap<>();
	}

	void setQualifiedName(String qualifiedName) {
		if (qualifiedName != null) {
			this.qualifiedName = qualifiedName.replaceAll("/", ".");
		}
	}

	void addConstantInfo(int index, AbstractConstantInfo constantInfo) {
		this.constantInfos.put(index, constantInfo);
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	@SuppressWarnings("unchecked")
	public <T> T getConstantInfo(int index) {
		return (T) constantInfos.get(index);
	}
}
