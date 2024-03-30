package org.FishFromSanDiego.cats.dao;

import jakarta.persistence.EntityManagerFactory;
import org.FishFromSanDiego.cats.util.Helper;

public class PostgresCatDao extends CatDaoImpl {

    public PostgresCatDao() {
        super(Helper.postgresEntityManagerFactory());
    }
}
