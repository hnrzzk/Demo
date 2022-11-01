package priv.str;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CodeTransform {
    public static void main(String[] args) {
        String str = "Non-UTF-8 output:   \\xd5\\xfd\\xd4\\xda\\xb4\\xb4\\xbd\\xa8\\xbf\\xe2 E:\\\\WorkSpace\\\\Rust\\\\dreamland-battle-server\\\\target\\\\debug\\\\deps\\\\dreamland_logic.dll.lib \\xba\\xcd\\xb6\\xd4\\xcf\\xf3 E:\\\\WorkSpace\\\\Rust\\\\dreamland-battle-server\\\\target\\\\debug\\\\deps\\\\dreamland_logic.dll.exp\\r\\ndreamland_logic.dreamland_logic.3b933102-cgu.7.rcgu.o : error LNK2019: \\xce\\xde\\xb7\\xa8\\xbd\\xe2\\xce\\xf6\\xb5\\xc4\\xcd\\xe2\\xb2\\xbf\\xb7\\xfb\\xba\\xc5 __imp_RegistePublishReadyToDoGuideTask\\xa3\\xac\\xb8\\xc3\\xb7\\xfb\\xba\\xc5\\xd4\\xda\\xba\\xaf\\xca\\xfd _ZN15dreamland_logic8behaviac20register_b_unit_func17h308545a9aba31aadE \\xd6\\xd0\\xb1\\xbb\\xd2\\xfd\\xd3\\xc3\\r\\nE:\\\\WorkSpace\\\\Rust\\\\dreamland-battle-server\\\\target\\\\debug\\\\deps\\\\dreamland_logic.dll : fatal error LNK1120: 1 \\xb8\\xf6\\xce\\xde\\xb7\\xa8\\xbd\\xe2\\xce\\xf6\\xb5\\xc4\\xcd\\xe2\\xb2\\xbf\\xc3\\xfc\\xc1\\xee\\r\\n";
        str = str.replace("\\x", "%");
        try {
            System.out.println(str);
            System.out.println(URLDecoder.decode(str, "gbk"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
