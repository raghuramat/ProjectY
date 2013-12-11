package com.yammer.dropwizard;

import com.yammer.dropwizard.DL.MySqlDAO;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import com.yammer.dropwizard.resources.HelloWorldResource;
import com.yammer.dropwizard.resources.IndoorMapping;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;

public class SampleService extends Service<SampleConfiguration>{

    public static void main(String[] args) throws Exception {
       // Class.forName("com.mysql.jdbc.Driver");
        new SampleService().run(args);
    }

    @Override
    public void initialize(Bootstrap<SampleConfiguration> sampleConfigurationBootstrap) {

    }

    @Override
    public void run(SampleConfiguration sampleConfiguration, Environment environment) throws Exception {
        environment.addResource(new HelloWorldResource());
       // environment.addResource(new IndoorMapping());

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, sampleConfiguration.getDatabaseConfiguration(), "mysql");
        final MySqlDAO dao = jdbi.onDemand(MySqlDAO.class);
        environment.addResource(new IndoorMapping(dao));


    }
}