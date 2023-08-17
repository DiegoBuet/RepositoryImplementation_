public interface Repository<T> {
    void save(T element);
    int count();
    T find(int index);
}