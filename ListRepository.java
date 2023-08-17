
import java.lang.reflect.Field;
import java.util.*;

public class ListRepository<T extends Element> implements List<T> {
    private List<T> elements = new ArrayList<>();
    private MenuUtils menuUtils;

    public ListRepository(MenuUtils menuUtils) {
        this.menuUtils = menuUtils;
    }

    public int calculateTotalStock() {
        int totalStock = 0;
        for (T entity : elements) {
            if (entity instanceof Product) {
                totalStock += ((Product) entity).getStock();
            }
        }
        return totalStock;
    }

    public List<T> getAllSortedBy(Comparator<T> comparator) {
        List<T> sortedList = new ArrayList<>(elements);
        sortedList.sort(comparator);
        return sortedList;
    }

    public void printUsedDomains() {
        Set<String> domains = getDomains();
        System.out.println("Domains used:");
        for (String domain : domains) {
            System.out.println(domain);
        }
    }
    @Override
    public boolean add(T t) {
        if (t instanceof Product) {
            String productName = ((Product) t).getName().toLowerCase();
            Optional<T> existingProduct = elements.stream()
                    .filter(p -> p instanceof Product && ((Product) p).getName().equalsIgnoreCase(productName))
                    .findFirst();
            if (existingProduct.isPresent()) {
                int currentStock = ((Product) existingProduct.get()).getStock();
                ((Product) existingProduct.get()).setStock(currentStock + ((Product) t).getStock());
                System.out.println("Product already registered, stock will be added to the existing product.");
                return true;
            } else {
                return elements.add(t);
            }
        } else if (t instanceof Country) {
            String isoCode = ((Country) t).getIsoCode().toLowerCase();
            if (elements.stream().anyMatch(c -> ((Country) c).getIsoCode().equalsIgnoreCase(isoCode))) {
                return false;
            }
        } else if (t instanceof User) {
            String email = t.getName();
            User existingUser = elements.stream()
                    .filter(u -> u instanceof User && email.equalsIgnoreCase(u.getName()))
                    .map(u -> (User) u)
                    .findFirst()
                    .orElse(null);

            if (existingUser != null) {
                System.out.println("Username or email already registered.");
                return false;
            }

            User user = (User) t;
            String domain = user.getDomain();

            if (!menuUtils.isValidDomain(domain)) {
                System.out.println("Invalid domain format. User not saved.");
                return false;
            }

            boolean domainExists = elements.stream()
                    .anyMatch(u -> u instanceof User && domain.equalsIgnoreCase(((User) u).getDomain()));

            if (domainExists) {
                System.out.println("Domain already registered.");
                return false;
            }

            user.setDomain(domain);
        }
        return elements.add(t);
    }

    public Set<String> getDomains() {
        Set<String> domains = new HashSet<>();
        for (T element : elements) {
            if (element instanceof User) {
                String domain = ((User) element).getDomain();
                if (domain != null && !domain.isEmpty()) {
                    int atIndex = domain.indexOf('@');
                    if (atIndex >= 0) {
                        domain = domain.substring(atIndex + 1);
                        domains.add(domain);
                    }
                }
            }
        }
        return domains;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return elements.toArray(a);
    }


    @Override
    public boolean remove(Object o) {
        return elements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return elements.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return elements.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elements.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public T get(int index) {
        return elements.get(index);
    }

    @Override
    public T set(int index, T element) {
        return elements.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        elements.add(index, element);
    }

    @Override
    public T remove(int index) {
        return elements.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return elements.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return elements.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return elements.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return elements.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return elements.subList(fromIndex, toIndex);
    }

    public List<T> getAllSortedByName() {
        Comparator<T> comparator = Comparator.comparing(entity -> entity.getName());
        List<T> sortedList = getAllSortedBy(comparator);
        return sortedList;
    }
}

