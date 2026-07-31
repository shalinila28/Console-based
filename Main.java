import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigDecimal;

public class Main {
    static ArrayList<Account> accounts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    static class Account {
        private int accountId;
        private String pin;
        private BigDecimal balance;

        public Account(int accountId, String pin, BigDecimal balance) {
            this.accountId = accountId;
            this.pin = pin;
            this.balance = balance;
        }

        public int getAccountId() {
            return accountId;
        }

        public String getPin() {
            return pin;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void deposit(BigDecimal amount) {
            balance = balance.add(amount);
        }

        public boolean withdraw(BigDecimal amount) {
            if (balance.compareTo(amount) >= 0) {
                balance = balance.subtract(amount);
                return true;
            }
            return false;
        }
    }

    public static Account findAccount(int id) {
        for (Account account : accounts) {
            if (account.getAccountId() == id) {
                return account;
            }
        }
        return null;
    }

    public static void createAccount() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        if (findAccount(id) != null) {
            System.out.println("Account ID already exists!");
            return;
        }
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        System.out.print("Enter Initial Deposit: ");
        BigDecimal amount = scanner.nextBigDecimal();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        accounts.add(new Account(id, pin, amount));
        System.out.println("Account created successfully!");
    }

    public static boolean authenticate(Account account) {
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        if (pin.equals(account.getPin())) {
            System.out.println("Authentication successful!");
            return true;
        }
        System.out.println("Invalid PIN!");
        return false;
    }

    public static void depositMoney() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        Account account = findAccount(id);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        if (!authenticate(account))
            return;
        System.out.print("Enter Deposit Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid amount!");
            return;
        }
        account.deposit(amount);
        System.out.println("Deposit successful!");
        System.out.println("Balance: ₹" + account.getBalance());
    }

    public static void withdrawMoney() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        Account account = findAccount(id);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        if (!authenticate(account))
            return;
        System.out.print("Enter Withdrawal Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid amount!");
            return;
        }
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful!");
            System.out.println("Balance: ₹" + account.getBalance());
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public static void checkBalance() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        Account account = findAccount(id);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        if (!authenticate(account))
            return;
        System.out.println("Account ID: " + account.getAccountId());
        System.out.println("Balance: ₹" + account.getBalance());
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Banking Application =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    System.out.println("Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
