package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.util.Helper;

public final class PostgresUserDao extends UserDaoImpl {

    public PostgresUserDao() {
        super(Helper.postgresEntityManagerFactory());
    }
}
