package com.se1dhe.redqueen.bot.config;


import com.se1dhe.redqueen.util.Config;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
class HibernateConf {


    @Bean
    public DriverManagerDataSource dataSource() {
        //Config.load();


        final DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUsername(Config.DB_USER);
        source.setPassword(Config.DB_PWD);
        source.setUrl(Config.DB_URL);

        return source;
    }
}
