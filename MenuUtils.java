

import java.util.*;

public class MenuUtils {
    private final Scanner scanner;

    public MenuUtils(Scanner scanner) {
        this.scanner = scanner;
    }

    private int readIntInput(String message) {
        int stock = 0;
        boolean validInput = false;
        do {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                stock = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } while (!validInput);
        return stock;
    }



    @FunctionalInterface
    interface ElementCreator<T> {
        T create(Scanner scanner);
    }

    public Product createProduct(Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        int stock = readIntInput("Enter product stock: ");

        return new Product(name, stock);
    }

    public Country createCountry(Scanner scanner) {
        System.out.print("Enter country name: ");
        String name = scanner.nextLine();

        System.out.print("Enter country ISO code: ");
        String isoCode = scanner.nextLine();

        return new Country(name, isoCode);
    }

    public User createUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        String domain;
        do {
            System.out.print("Enter domain: ");
            domain = scanner.nextLine();

            if (isValidDomain(domain)) {
                break;
            } else {
                System.out.println("Invalid domain format. Try again.");
            }
        } while (true);

        User user = new User(username, firstName, lastName);
        user.setDomain(domain);
        return user;
    }



    public boolean isValidDomain(String domain) {
        int atSymbolIndex = domain.indexOf('@');
        int dotSymbolIndex = domain.lastIndexOf('.');
        return atSymbolIndex >= 0 && dotSymbolIndex > atSymbolIndex;
    }



}

