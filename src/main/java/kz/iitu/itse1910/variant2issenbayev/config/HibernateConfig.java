package kz.iitu.itse1910.variant2issenbayev.config;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@AllArgsConstructor
public class HibernateConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    @DependsOn("dataSource")
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("kz.iitu.itse1910.variant2issenbayev.entity");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    private DataSource dataSource() {
        return applicationContext.getBean(DataSource.class);
    }

    @Bean("dataSource")
    @Profile("dev")
    public DataSource postgreSQLDataSource() {
        DataSourceBuilder<?> dataSource =  DataSourceBuilder.create();
        dataSource.driverClassName("org.postgresql.Driver");
        dataSource.url("jdbc:postgresql://localhost:5432/variant2_issenbayev");
        dataSource.username("postgres");
        dataSource.password("pass");
        return dataSource.build();
    }

    @Bean("dataSource")
    @Profile("test")
    public DataSource h2DataSource() {
        DataSourceBuilder<?> dataSource =  DataSourceBuilder.create();
        dataSource.driverClassName("org.h2.Driver");
        dataSource.url("jdbc:h2:mem:variant2_issenbayev;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.username("sa");
        dataSource.password("pass");
        return dataSource.build();
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();

        props.put("hibernate.hbm2ddl.auto", "create");

        // Caching
        props.put("hibernate.cache.use_second_level_cache", true);
        props.put("hibernate.cache.use_query_cache", true);
        props.put("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.JCacheRegionFactory");

        // Show SQL
//        props.put("hibernate.format_sql", true);
//        props.put("hibernate.use_sql_comments", true);
//        props.put("hibernate.show_sql", true);

        // Fetching
        props.put("hibernate.max_fetch_depth", 3);
        props.put("hibernate.jdbc.batch_size", 10);
        props.put("hibernate.jdbc.fetch_size", 50);

        return props;
    }
}
