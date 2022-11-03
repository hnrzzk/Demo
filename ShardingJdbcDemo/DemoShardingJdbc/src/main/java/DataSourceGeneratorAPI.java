import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class DataSourceGeneratorAPI implements DataSourceGenerator{
    @Override
    public DataSource generateDataSource() {
        String databaseName = "xknight_test"; // 指定逻辑 Database 名称
        ModeConfiguration modeConfig = createModeConfiguration(); // 构建运行模式
        Map<String, DataSource> dataSourceMap = createDataSources(); // 构建真实数据源
        Collection<RuleConfiguration> ruleConfigs = new ArrayList<>(); // 构建具体规则
        ruleConfigs.add(createShardingRuleConfiguration());
        Properties props = new Properties(); // 构建属性配置
        try {
            return ShardingSphereDataSourceFactory.createDataSource(databaseName, modeConfig, dataSourceMap, ruleConfigs, props);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ModeConfiguration createModeConfiguration() {
        Properties properties = new Properties();
        return new ModeConfiguration("Standalone", new StandalonePersistRepositoryConfiguration("JDBC", properties));
    }

    public Map<String, DataSource> createDataSources() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置第 1 个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3307/xknight_test");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("xknight_test_1", dataSource1);

        // 配置第 2 个数据源
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://localhost:3308/xknight_test");
        dataSource2.setUsername("root");
        dataSource2.setPassword("root");
        dataSourceMap.put("xknight_test_2", dataSource2);

        return dataSourceMap;
    }

    private ShardingRuleConfiguration createShardingRuleConfiguration() {
        ShardingRuleConfiguration result = new ShardingRuleConfiguration();
        result.getTables().add(getOrderTableRuleConfiguration());
        result.setDefaultDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("playerId", "table_sharding_algorithm"));
        result.getShardingAlgorithms().put("table_sharding_algorithm", tableShardingAlgorithms());
        result.getShardingAlgorithms().put("db_sharding_algorithm", dbShardingAlgorithms());
        result.getKeyGenerators().put("snowflake", new AlgorithmConfiguration("SNOWFLAKE", new Properties()));
        result.getAuditors().put("sharding_key_required_auditor", new AlgorithmConfiguration("DML_SHARDING_CONDITIONS", new Properties()));
        return result;
    }

    private ShardingTableRuleConfiguration getOrderTableRuleConfiguration() {
        ShardingTableRuleConfiguration result = new ShardingTableRuleConfiguration("inst_hero_split", "xknight_test_${1..2}.inst_hero_split_${0..4}");
//        ShardingTableRuleConfiguration result = new ShardingTableRuleConfiguration("inst_hero_split", "xknight_test_1.inst_hero_split_${0..4}");
        result.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("playerId", "snowflake"));
        result.setTableShardingStrategy(new StandardShardingStrategyConfiguration("playerId", "table_sharding_algorithm"));
        result.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("playerId", "db_sharding_algorithm"));
        return result;
    }

    private AlgorithmConfiguration tableShardingAlgorithms() {
        Properties props = new Properties();
        props.setProperty("algorithm-expression", "inst_hero_split_${playerId % 5}");
        props.put("allow-range-query-with-inline-sharding", true);
        return new AlgorithmConfiguration("INLINE", props);
    }

    private AlgorithmConfiguration dbShardingAlgorithms() {
        Properties props = new Properties();
        props.setProperty("algorithm-expression", "xknight_test_${playerId % 2 + 1}");
        props.put("allow-range-query-with-inline-sharding", true);
        return new AlgorithmConfiguration("INLINE", props);
    }
}
