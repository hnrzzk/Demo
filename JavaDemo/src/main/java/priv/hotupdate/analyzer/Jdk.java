package priv.hotupdate.analyzer;

/**
 * Jdk
 *
 * @author guojijun
 * @version v0.1 2017-9-2 下午6:29:03  guojijun
 */
public enum Jdk {

					JDK_16(new Jdk16ClassAnalyzer()),

					JDK_17(new Jdk17ClassAnalyzer()),

					JDK_111(new Jdk117ClassAnalyzer()),;

	private final IClassAnalyzer classAnalyzer;

	/**
	 * 获取当前版本的类解析器
	 * 
	 * @return
	 */
	public static IClassAnalyzer getCurClassAnalyzer() {
		String version = System.getProperty("java.version");
		if (version == null) {
			return null;
		}
		for (Jdk jdk : values()) {
			if (version.contains(jdk.getClassAnalyzer().getVersion())) {
				return jdk.getClassAnalyzer();
			}
		}
		return null;
	}

	private Jdk(IClassAnalyzer classAnalyzer) {
		this.classAnalyzer = classAnalyzer;
	}

	public IClassAnalyzer getClassAnalyzer() {
		return classAnalyzer;
	}

}
