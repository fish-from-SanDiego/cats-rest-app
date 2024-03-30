package org.fishFromSanDiego.cats;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.FishFromSanDiego.cats.models.User;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        var user = User.builder().firstName("aaa").secondName("bbb").build();
        inTransaction(em -> {
            em.persist(user);
        });
        inTransaction(em -> {
            var usr = em.find(User.class, 1);
            usr.setBirthDate(LocalDate.now());
        });
        var emf = Persistence.createEntityManagerFactory("cats-db");
        emf.createEntityManager()
                .createQuery("FROM User")
                .getResultList().forEach(u -> System.out.println(u));
        new Scanner(System.in).nextLine();
    }

    static void inTransaction(Consumer<EntityManager> work) {
        var entityManagerFactory = Persistence.createEntityManagerFactory("cats-db");
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

