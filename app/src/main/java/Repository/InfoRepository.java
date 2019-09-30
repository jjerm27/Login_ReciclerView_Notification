package Repository;

import java.util.List;

public interface InfoRepository<T> {
    List<T> getAll();
    T getOne(int id);
}
