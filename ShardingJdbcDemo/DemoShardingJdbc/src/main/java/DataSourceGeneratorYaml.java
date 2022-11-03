import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DataSourceGeneratorYaml implements DataSourceGenerator{
    @Override
    public DataSource generateDataSource() {
        DataSource dataSource = null;
        try {
            dataSource = YamlShardingSphereDataSourceFactory.createDataSource(getFile("shardingJdbc_standalone.yaml"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    private File getFile(final String fileName) {
        return new File(DataSourceGeneratorYaml.class.getClassLoader().getResource(fileName).getFile());
    }
}
