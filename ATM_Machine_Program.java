

import java.util.*;

// Class representing a bank account with basic functionalities
class BankAccount {
    String accountHolderName;
    String accountNumber;
    int pin;
    double balance;
    double dailyWithdrawalLimit;
    double dailyWithdrawnAmount;

    // Constructor to initialize a new bank account
    BankAccount(String name, String accNumber, int pin, double initialBalance) {
        this.accountHolderName = name;
        this.accountNumber = accNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.dailyWithdrawalLimit = 20000; // ATM withdrawal limit per day
        this.dailyWithdrawnAmount = 0; // Tracks how much has been withdrawn today
    }

    // Method to deposit money into the account
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited successfully.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw money from the account
    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            // Check if daily withdrawal limit is not exceeded
            if ((dailyWithdrawnAmount + amount) <= dailyWithdrawalLimit) {
                balance -= amount;
                dailyWithdrawnAmount += amount;
                System.out.println("Amount withdrawn successfully.");
            } else {
                System.out.println("Daily withdrawal limit exceeded! You can withdraw up to " + (dailyWithdrawalLimit - dailyWithdrawnAmount));
            }
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    // Method to check account balance
    void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    // Method to change account PIN
    void changePin(int newPin) {
        this.pin = newPin;
        System.out.println("PIN changed successfully.");
    }

    // Method to reset the daily withdrawal counter (e.g., simulating a new day)
    void resetDailyLimit() {
        this.dailyWithdrawnAmount = 0;
    }
}

// Main class to simulate an ATM machine system
public class ATM_Machine_Program {
    static ArrayList<BankAccount> accounts = new ArrayList<>(); // Stores all bank accounts
    static Scanner sc = new Scanner(System.in); // Scanner object for input

    public static void main(String[] args) {
        int choice;

        // ATM main menu loop
        do {
            System.out.println("\n--- ATM Main Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login to ATM");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Handle user choice
            switch (choice) {
                case 1:
                    createAccount(); // Create a new account
                    break;

                case 2:
                    login(); // Login to existing account
                    break;

                case 3:
                    System.out.println("Thank you for using our ATM!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3); // Exit loop when user chooses to exit

        sc.close(); // Close scanner
    }

    // Method to create a new bank account
    static void createAccount() {
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Account Number: ");
        String accNumber = sc.nextLine();
        System.out.print("Set 4-digit PIN: ");
        int pin = sc.nextInt();
        System.out.print("Enter Initial Balance: ");
        double initialBalance = sc.nextDouble();
        sc.nextLine(); // Consume newline

        BankAccount account = new BankAccount(name, accNumber, pin, initialBalance);
        accounts.add(account); // Add new account to list
        System.out.println("Account created successfully!");
    }

    // Method to find a bank account by account number and PIN
    static BankAccount findAccount(String accNumber, int pin) {
        for (BankAccount acc : accounts) {
            if (acc.accountNumber.equals(accNumber) && acc.pin == pin) {
                return acc;
            }
        }
        return null; // Account not found
    }

    // Method to log into an existing account
    static void login() {
        System.out.print("Enter Account Number: ");
        String accNumber = sc.nextLine();
        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();
        sc.nextLine(); // Consume newline

        BankAccount account = findAccount(accNumber, pin);
        if (account != null) {
            System.out.println("Login successful!");
            atmMenu(account); // Show ATM menu after successful login
        } else {
            System.out.println("Invalid account number or PIN.");
        }
    }

    // Method to show the ATM transaction menu
    static void atmMenu(BankAccount account) {
        int choice;

        // Transaction menu loop
        do {
            System.out.println("\n--- ATM Transaction Menu ---");
            System.out.println("1. Withdraw Money");
            System.out.println("2. Deposit Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Handle transaction menu choices
            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = sc.nextDouble();
                    sc.nextLine(); // Consume newline
                    account.withdraw(withdrawAmount);
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();
                    sc.nextLine(); // Consume newline
                    account.deposit(depositAmount);
                    break;

                case 3:
                    account.checkBalance();
                    break;

                case 4:
                    System.out.print("Enter new 4-digit PIN: ");
                    int newPin = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    account.changePin(newPin);
                    break;

                case 5:
                    System.out.println("Logged out successfully.");
                    account.resetDailyLimit(); // Reset daily withdrawal on logout
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5); // Logout when user chooses option 5
    }
}
