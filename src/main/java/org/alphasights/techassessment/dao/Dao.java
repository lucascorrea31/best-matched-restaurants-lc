package org.alphasights.techassessment.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(String id);

    List<T> getAll();

    Optional<T> save(T t);

    Optional<T> update(T t, String[] params);

    boolean delete(T t);
}
