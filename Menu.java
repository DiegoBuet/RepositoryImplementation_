

import java.util.Scanner;
import java.util.Set;


public class Menu {
    private Scanner scanner;
    private ListRepository<Product> productRepository;
    private ListRepository<Country> countryRepository;
    private ListRepository<User> userRepository;
    private MenuUtils menuUtils;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
        this.menuUtils = new MenuUtils(scanner);
        this.productRepository = new ListRepository<>(menuUtils);
        this.countryRepository = new ListRepository<>(menuUtils);
        this.userRepository = new ListRepository<>(menuUtils);
    }

    public void printMainMenu() {
        System.out.println("\n----- Main Menu -----");
        System.out.println("1. Manage Users");
        System.out.println("2. Manage Countries");
        System.out.println("3. Manage Products");
        System.out.println("0. Exit");
        System.out.println("---------------------");
    }

    public String getOption() {
        System.out.print("Enter option: ");
        return scanner.nextLine();
    }

    public void runMenu() {
        String option;
        do {
            printMainMenu();
            option = getOption();
            switch (option) {
                case "1" -> handleUserOperations();
                case "2" -> handleCountryOperations();
                case "3" -> handleProductOperations();
                case "0" -> System.out.println("Exiting.");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (!option.equals("0"));
    }

    public void handleUserOperations() {
        EntityHandler<User> entityHandler = new EntityHandler<>(userRepository, scanner, menuUtils::createUser, this);
        boolean backToMenu = entityHandler.handleEntityOperations("User");

        if (!backToMenu) {
            Set<String> domains = userRepository.getDomains();
            System.out.println("Unique domains: " + domains);
        }
    }

    public void handleCountryOperations() {
        EntityHandler<Country> entityHandler = new EntityHandler<>(countryRepository, scanner, menuUtils::createCountry, this);
        entityHandler.handleEntityOperations("Country");
    }

    public void handleProductOperations() {
        EntityHandler<Product> entityHandler = new EntityHandler<>(productRepository, scanner, menuUtils::createProduct, this);
        boolean backToMenu;
        do {
            backToMenu = entityHandler.handleEntityOperations("Product");
            if (!backToMenu) {
                int totalStock = productRepository.calculateTotalStock();
                System.out.println("Total stock: " + totalStock);
            }
        } while (!backToMenu);
    }

}
