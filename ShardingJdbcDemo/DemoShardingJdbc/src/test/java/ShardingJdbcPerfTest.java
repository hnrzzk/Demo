import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;

public class ShardingJdbcPerfTest {

    DataSource shardingJDBCDataSource;
    Connection shardingJDBConnect;

    private static String JDBC1_URL1 = "jdbc:mysql://10.32.33.141:3307/xknight_test";
    private static String JDBC1_URL2 = "jdbc:mysql://10.32.33.141:3308/xknight_test";
    private static String JDBC_USER_NAME = "root";
    private static String JDBC_PASS_WORD = "root";

    Connection connection1;
    Connection connection2;

    {
        shardingJDBCDataSource = new DataSourceGeneratorAPI().generateDataSource();
        try {
            shardingJDBConnect = shardingJDBCDataSource.getConnection();
            connection1 = DriverManager.getConnection(JDBC1_URL1, JDBC_USER_NAME, JDBC_PASS_WORD);
            connection2 = DriverManager.getConnection(JDBC1_URL2, JDBC_USER_NAME, JDBC_PASS_WORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @JunitPerfConfig(threads = 1, warmUp = 10000, duration = 60000, reporter = HtmlReporter.class)
    public void shardingJdbcIndexTest() {
        try {
            String sql = "SELECT o.* FROM inst_hero_split o WHERE o.playerId = ?";
            int randomId = Utils.randomInt(0, 1000000);
            PreparedStatement ps = shardingJDBConnect.prepareStatement(sql);
            ps.setInt(1, randomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getLong("heroGuid"));
            }
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @JunitPerfConfig(threads = 1, warmUp = 10000, duration = 60000, reporter = HtmlReporter.class)
    public void shardingJdbcNoIndexTest() {
        try {
            String sql = "SELECT o.* FROM inst_hero_split o WHERE o.heroGuid = ?";
            int randomId = Utils.randomInt(0, 1000000);
            PreparedStatement ps = shardingJDBConnect.prepareStatement(sql);
            ps.setInt(1, randomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getLong("heroGuid"));
            }
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @JunitPerfConfig(threads = 1, warmUp = 10000, duration = 60000, reporter = HtmlReporter.class)
    public void jdbcIndexTest() throws SQLException {
        int randomId = Utils.randomInt(0, 1000000);
        Connection connection = choiceConnection(randomId);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from inst_hero_split_").append(randomId % 5).append(" where playerId = ?");
        System.out.println(executeQueue(connection, sqlBuilder.toString(), randomId));
    }

    private Connection choiceConnection(int value) {
        int shardingValue = (value % 2) + 1;
        return switch (shardingValue) {
            case 1 -> connection1;
            case 2 -> connection2;
            default -> null;
        };
    }


    String sql0 = "select * from inst_hero_split_0 where heroGuid = ?";
    String sql1 = "select * from inst_hero_split_1 where heroGuid = ?";
    String sql2 = "select * from inst_hero_split_2 where heroGuid = ?";
    String sql3 = "select * from inst_hero_split_3 where heroGuid = ?";
    String sql4 = "select * from inst_hero_split_4 where heroGuid = ?";

    @JunitPerfConfig(threads = 1, warmUp = 10000, duration = 60000, reporter = HtmlReporter.class)
    public void jdbcNoIndexTest() throws SQLException {
        int randomId = Utils.randomInt(0, 1000000);
        long result = -1;
        if ((result = executeDb(connection1, randomId)) >= 0) {
            System.out.println(result);
            return;
        }
        if ((result = executeDb(connection2, randomId)) >= 0) {
            System.out.println(result);
            return;
        }
    }

    private long executeDb(Connection connection, int value) throws SQLException {
        long result = -1;
        if ((result = executeQueue(connection, sql0, value)) > 0) {
            return result;
        }
        if ((result = executeQueue(connection, sql1, value)) > 0) {
            return result;
        }
        if ((result = executeQueue(connection, sql2, value)) > 0) {
            return result;
        }
        if ((result = executeQueue(connection, sql3, value)) > 0) {
            return result;
        }
        if ((result = executeQueue(connection, sql4, value)) > 0) {
            return result;
        }
        return result;
    }

    private long executeQueue(Connection connection, String sql, int value) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, value);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            long result = rs.getLong("heroGuid");
            rs.close();
            ps.close();
            return result;
        }
        return -1;
    }

//    @JunitPerfConfig(threads = 1, warmUp = 10000, duration = 20000, reporter = HtmlReporter.class)
//    @Order(4)
//    public void test() {
//        for (int i = 0; i< 100000; i++) {
//            int a = i + 1;
////            System.out.println("this is a test");
//        }
//    }
}
