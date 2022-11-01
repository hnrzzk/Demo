package priv.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;

/**
 * Created by zhangkai on 2017/9/27.
 */
public class TestMyBatis {
    public static void main(String[] argvs) {
        test1();
        test2();
    }

    public static void test1()
    {
        String resource = "mybatis/mybatis_conf.xml";

        InputStream is = TestMyBatis.class.getClassLoader().getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession();

        String statement = "priv.frame.orm.mybatis.userMapper.getUser";

        User user = session.selectOne(statement, 2);
        System.out.println(user);
    }

    public static void test2()
    {
        String resource = "mybatis/mybatis_conf.xml";

        try {
            Reader reader = Resources.getResourceAsReader(resource);

            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession session = sessionFactory.openSession();

            String statement = "priv.frame.orm.mybatis.userMapper.getUser";

            User user = session.selectOne(statement, 1);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
