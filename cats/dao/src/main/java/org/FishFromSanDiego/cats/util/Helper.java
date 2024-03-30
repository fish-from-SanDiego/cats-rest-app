package org.FishFromSanDiego.cats.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class Helper {
    public static EntityManagerFactory postgresEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("cats-db");
    }

    public static void inTransaction(EntityManagerFactory entityManagerFactory, Consumer<EntityManager> work) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            work.accept(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
