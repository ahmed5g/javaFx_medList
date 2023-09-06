package tn.esprit.medlist.Core.Repository;

import java.util.List;

public interface Repo<T> {

    void save(T entity);
    List<T> findAll();
}
