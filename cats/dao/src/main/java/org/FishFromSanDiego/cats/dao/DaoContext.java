package org.FishFromSanDiego.cats.dao;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DaoContext {
    private CatDao catDao;
    private UserDao userDao;
}
