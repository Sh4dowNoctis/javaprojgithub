package epita.tp.repository;

public interface BaseRepository<T> {
    T findById(Long id);
    void deleteById(Long id);
    void create(T obj);
    void update(T obj);
}
