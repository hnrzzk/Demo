package priv.hotupdate.analyzer;

/**
 * 11版本的类解析器
 *
 * @author guojijun
 * @version v0.1 2020年4月24日 下午4:43:26 guojijun
 */
class Jdk117ClassAnalyzer extends Jdk17ClassAnalyzer {
    @Override
    public String getVersion() {
        return "17";
    }
}
