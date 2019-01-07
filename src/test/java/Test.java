import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2018/12/24.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String[] strings = {"ada", "da","dwawda5,dada",",,,944","5959,","aad,daw16","dad\"adadwdwaadw95949\""};
        CsvWriter writer = CsvUtil.getWriter("/a.csv", Charset.defaultCharset());
        writer.write(strings);
        writer.close();


    }
}
