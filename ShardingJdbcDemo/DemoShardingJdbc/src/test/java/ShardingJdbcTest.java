

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShardingJdbcTest {
    private DataSource getDataSourceByAPI() {
        DataSourceGenerator generator = new DataSourceGeneratorAPI();
        return generator.generateDataSource();
    }

    private DataSource getDataSourceByYaml() {
        return new DataSourceGeneratorYaml().generateDataSource();
    }


    @Test
    public void testConfigType() {
        testDBActive(getDataSourceByAPI());
        testDBActive(getDataSourceByYaml());
    }

    private void testDBActive(DataSource dataSource) {
        testInsert(dataSource);
        testQueue(dataSource);
    }

    private void testInsert(DataSource dataSource) {
        Thread thread = null;
        for (int i = 0 ; i < 10; i++) {
            final int index = i;
            thread = new Thread(() -> doInsert(dataSource, 50000 + index * 100000, 100000));
            thread.start();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        doInsert(dataSource);
    }

    private void doInsert(DataSource dataSource, int start, int count) {
        for (int i = start; i < (count + start); i++) {
            long heroId = i;
            long playerId = i;
            String sql = "INSERT INTO inst_hero_split(`heroGuid`, `heroId`, `playerId`, `exp`, `level`, `transferLevel`, `occupation`, `expertPoints`, `treasuresMap`, `weaponGuid`, `talentPoint`, `useTalentPoint`, `talentInfo`, `godSolutionInfo`, `modifyTime`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, '{}', 1294693940066357251, ?, ?, '{}', '{}', 1665653236579)";
            try (
                    Connection conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, heroId);
                ps.setLong(2, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(3, playerId);
                ps.setLong(4, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(5, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(6, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(7, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(8, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(9, Utils.randomInt(0, Integer.MAX_VALUE/2));
                ps.setLong(10, Utils.randomInt(0, Integer.MAX_VALUE/2));

                System.out.println(i + ": "+ ps.executeUpdate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void testQueue(DataSource dataSource) {
        String sql = "SELECT o.* FROM inst_hero_split o WHERE o.playerId = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 0);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    System.out.println(rs.getLong("heroGuid"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
