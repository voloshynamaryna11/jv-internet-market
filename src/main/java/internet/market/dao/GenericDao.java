package internet.market.dao;

import internet.market.exceptions.DataProcessingException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item) throws DataProcessingException;

    Optional<T> get(K id) throws DataProcessingException;

    List<T> getAll() throws DataProcessingException;

    T update(T item) throws DataProcessingException;

    boolean delete(K id) throws DataProcessingException;
}
