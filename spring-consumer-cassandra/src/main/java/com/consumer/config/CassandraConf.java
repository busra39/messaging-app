package com.consumer.config;

/**
 * Created by busracanak on 24/04/18.
 */

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

@Configuration
@Profile("default")
public class CassandraConf {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConf.class);

    @Value("${cassandra.contactPoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.username}")
    private String username;

    @Value("${cassandra.password}")
    private String password;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.replicationStrategy}")
    private String replicationStrategy;

    @Value("${cassandra.replicationFactor}")
    private int replicationFactor;

    private Cluster cluster;

    @Bean
    public Session session(){
        final Session session = cluster().connect();
        createKeyspaceIfNotExists(session);
        useKeyspace(session);
        return session;
    }

    private Cluster cluster(){
        LOGGER.info("Creating cluster with contact points: {} and port: {}", contactPoints, port);
        cluster = Cluster.builder().addContactPoints(contactPoints).build();
        metadata(cluster);
        return cluster;
    }

    private void metadata(final Cluster cluster) {
        final Metadata metadata = cluster.getMetadata();
        LOGGER.info("Connected to cluster: {}", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts()) {
            LOGGER.info("Datacenter: {} host: {}", host.getDatacenter(), host.getAddress());
        }
    }

    private void createKeyspaceIfNotExists(final Session session) {
        final String createKeyspace = String.format(
                "CREATE KEYSPACE IF NOT EXISTS %s  WITH replication = "
                        + "{'class':'%s', 'replication_factor':%d};",
                keyspace, replicationStrategy, replicationFactor);
        session.execute(createKeyspace);
        session.execute(String.format("USE %s;", keyspace));
    }

    public String getKeyspace() {
        return keyspace;
    }

    private void useKeyspace(final Session session) {
        session.execute(String.format("USE %s;", keyspace));
    }


    @PreDestroy
    public void closeCluster() {
        LOGGER.info("Disposing cluster {}", cluster.getClusterName());
        if (cluster != null){
            cluster.close();
            cluster = null;
        }
    }}
