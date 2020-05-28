package com.freshworks.sharding;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardingConfig {

    private static ShardingConfig shardingConfig = null;

    private DataSource dataSource;

    private ShardingConfig() throws SQLException {
        this.dataSource = getDataSource();
    }


    DataSource getDataSource() throws SQLException {
        ShardingRuleConfiguration ruleConfiguration = new ShardingRuleConfiguration();
        ruleConfiguration.getTableRuleConfigs().add(getOrderTableRuleConfig());
        ruleConfiguration.getTableRuleConfigs().add(getOrderItemTableRuleConfig());
        //If binding tables are not configured, multiple query will be executed and result will be aggregated.
        ruleConfiguration.getBindingTableGroups().add("contacts, contacts_custom");
        ruleConfiguration.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("org_id",new CustomShardingAlgorithm()));
        /**
         * By default all tables will go to DataSource0.
         */
        ruleConfiguration.setDefaultDataSourceName("ds0");
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), ruleConfiguration, new Properties());
    }


    TableRuleConfiguration getOrderTableRuleConfig() {
        return new TableRuleConfiguration("contacts", "ds${0..1}.contacts");
    }

    TableRuleConfiguration getOrderItemTableRuleConfig() {
        return new TableRuleConfiguration("contacts_custom", "ds${0..1}.contacts_custom");
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", DataSourceUtil.createDataSource("campaigns0"));
        dataSourceMap.put("ds1", DataSourceUtil.createDataSource("campaigns1"));
        return dataSourceMap;
    }


    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static ShardingConfig shardingConfig() throws SQLException {
        if(shardingConfig == null) {
            shardingConfig = new ShardingConfig();
        }

        return shardingConfig;
    }


}
