import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private ListRepository<Product> productRepository;
    private ListRepository<Country> countryRepository;
    private ListRepository<User> userRepository;
    private MenuUtils menuUtils;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
        this.productRepository = new ListRepository<>();
        this.countryRepository = new ListRepository<>();
        this.userRepository = new ListRepository<>();
        this.menuUtils = new MenuUtils(scanner);
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
                case "1" -> handleProductOperations();
                case "2" -> handleCountryOperations();
                case "3" -> handleUserOperations();
                case "0" -> System.out.println("Exiting.");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (!option.equals("0"));
    }

    public void handleProductOperations() {
        EntityHandler<Product> entityHandler = new EntityHandler<>(productRepository, scanner, menuUtils::createProduct, this); // Pasar "this" como referencia al objeto Menu
        boolean backToMenu;
        do {
            backToMenu = entityHandler.handleEntityOperations("Product");
            if (!backToMenu) {
                int totalStock = productRepository.stream().mapToInt(Product::stock).sum();
                System.out.println("Total stock: " + totalStock);
            }
        } while (!backToMenu);
    }

    public void handleCountryOperations() {
        EntityHandler<Country> entityHandler = new EntityHandler<>(countryRepository, scanner, menuUtils::createCountry, this); // Pasar "this" como referencia al objeto Menu
        entityHandler.handleEntityOperations("Country");
    }

    public void handleUserOperations() {
        EntityHandler<User> entityHandler = new EntityHandler<>(userRepository, scanner, menuUtils::createUser, this); // Pasar "this" como referencia al objeto Menu
        entityHandler.handleEntityOperations("User");
    }
}
