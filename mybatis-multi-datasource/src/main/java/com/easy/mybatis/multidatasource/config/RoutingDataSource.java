package com.easy.mybatis.multidatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
    //@Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContext.get();
    }
}