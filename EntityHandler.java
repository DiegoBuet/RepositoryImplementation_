
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class EntityHandler<T extends Element> implements ElementOperations {
    private ListRepository<T> entityRepository;
    private Scanner scanner;
    private MenuUtils.ElementCreator<T> creator;
    private Menu menu;

    public EntityHandler(ListRepository<T> entityRepository, Scanner scanner, MenuUtils.ElementCreator<T> creator, Menu menu) {
        this.entityRepository = entityRepository;
        this.scanner = scanner;
        this.creator = creator;
        this.menu = menu;
    }

    public boolean handleEntityOperations(String entityName) {
        String entityOption;
        do {
            System.out.println("\n----- " + entityName + " Operations -----");
            System.out.println("1. Save " + entityName);
            System.out.println("2. Count " + entityName + "s");
            System.out.println("3. Find " + entityName + " by Index");
            System.out.println("4. Sort " + entityName + "s by Name");
            System.out.println("0. Back to Main Menu");
            System.out.println("-----------------------------");

            entityOption = getMenuOption();

            switch (entityOption) {
                case "1" -> createAndSaveElement();
                case "2" -> countEntitiesAndStock(entityName);
                case "3" -> findEntityByIndex(entityName);
                case "4" -> {
                    sortEntitiesByName(entityName);
                    if (!entityName.equalsIgnoreCase("Country") && !entityName.equalsIgnoreCase("Product")) {
                        printUsedDomains();
                    }
                }
                case "0" -> {
                    return true; // Volver al menú principal
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (!entityOption.equals("0"));

        return false;
    }


    private void printUsedDomains() {
        entityRepository.printUsedDomains();
    }

    private void sortEntitiesByName(String entityName) {
        List<T> sortedList = entityRepository.getAllSortedBy(Comparator.comparing(Element::getName));
        if (!sortedList.isEmpty()) {
            System.out.println(entityName + "s sorted by name:");
            for (T entity : sortedList) {
                System.out.println(entity);
            }
        } else {
            System.out.println("No " + entityName + "s found.");
        }
    }


    private void countEntitiesAndStock(String entityName) {
        int count = entityRepository.size();
        System.out.println("Number of " + entityName + "s: " + count);

        if (entityRepository.size() > 0 && entityRepository.get(0) instanceof Product) {
            int totalStock = calculateTotalStock();
            System.out.println("Total stock: " + totalStock);
        }
    }




    private String getMenuOption() {
        while (true) {
            System.out.print("Enter option: ");
            String input = scanner.nextLine();

            if (isValidOption(input)) {
                return input;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private boolean isValidOption(String input) {
        try {
            int option = Integer.parseInt(input);
            return option >= 0 && option <= 4; // Cambiado de 3 a 4
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void createAndSaveElement() {
        T newElement = creator.create(scanner);
        if (newElement != null) {
            boolean saved = entityRepository.add(newElement);
            if (saved) {
                System.out.println("Entity saved successfully.");
            } else {
                System.out.println("Username or email already registered.");
            }
        } else {
            System.out.println("Error creating entity.");
        }
    }




    public int calculateTotalStock() {
        int totalStock = 0;
        for (T entity : entityRepository) {
            if (entity instanceof Product) {
                totalStock += ((Product) entity).getStock();
            }
        }
        return totalStock;
    }

    public void findEntityByIndex(String entityName) {
        System.out.print("Enter index: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        if (index >= 0 && index < entityRepository.size()) {
            System.out.println(entityName + " at index " + (index + 1) + ": " + entityRepository.get(index));
        } else {
            System.out.println(entityName + " not found at index " + (index + 1));
        }
    }
}

