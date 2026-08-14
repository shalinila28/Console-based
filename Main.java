import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BankManager bankManager = new BankManager();
        bankManager.start();
    }

    enum AccountType {
        SAVINGS,
        CURRENT
    }

    static class BankAccount {

        private static int totalAccounts = 0;

        private final String accountNumber;
        private String holderName;
        private BigDecimal balance;
        private AccountType accountType;

        public BankAccount() {
            this("UNKNOWN", "Guest", BigDecimal.ZERO, AccountType.SAVINGS);
        }

        public BankAccount(String accountNumber, String holderName) {
            this(accountNumber, holderName, BigDecimal.ZERO, AccountType.SAVINGS);
        }

        public BankAccount(String accountNumber, String holderName, BigDecimal balance) {
            this(accountNumber, holderName, balance, AccountType.SAVINGS);
        }

        public BankAccount(String accountNumber, String holderName,
                           BigDecimal balance, AccountType accountType) {

            this.accountNumber = accountNumber;
            this.holderName = holderName;

            if (balance.compareTo(BigDecimal.ZERO) >= 0) {
                this.balance = balance;
            } else {
                this.balance = BigDecimal.ZERO;
            }

            this.accountType = accountType;
            totalAccounts++;
        }

        public void deposit(BigDecimal amount) {

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid deposit amount.");
                return;
            }

            this.balance = this.balance.add(amount);

            System.out.println("Deposit successful.");
        }

        public boolean withdraw(BigDecimal amount) {

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Invalid withdrawal amount.");
                return false;
            }

            if (amount.compareTo(this.balance) > 0) {
                System.out.println("Withdrawal failed: Insufficient balance.");
                return false;
            }

            this.balance = this.balance.subtract(amount);

            System.out.println("Withdrawal successful.");
            return true;
        }

        public static int getTotalAccounts() {
            return totalAccounts;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getHolderName() {
            return holderName;
        }

        public AccountType getAccountType() {
            return accountType;
        }

        public void displayAccount() {
            System.out.println("Account Number : " + accountNumber);
            System.out.println("Holder Name    : " + holderName);
            System.out.println("Account Type   : " + accountType);
            System.out.println("Balance        : ₹" + balance);
        }
    }

    static class BankManager {

        private final Map<String, BankAccount> accounts = new HashMap<>();
        private final Scanner scanner = new Scanner(System.in);

        public void start() {

            while (true) {

                showMenu();

                int choice = readInteger("Enter your choice: ");

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
                        viewAccount();
                        break;

                    case 6:
                        System.out.println();
                        System.out.println("Total Accounts: "
                                + BankAccount.getTotalAccounts());
                        break;

                    case 7:
                        System.out.println();
                        System.out.println(
                                "Thank you for using Bank Management System.");
                        scanner.close();
                        return;

                    default:
                        System.out.println();
                        System.out.println("Invalid choice.");
                        System.out.println("Please select 1 to 7.");
                }
            }
        }

        private void showMenu() {

            System.out.println();
            System.out.println("============================================");
            System.out.println("        BANK MANAGEMENT SYSTEM");
            System.out.println("============================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Account");
            System.out.println("6. Total Accounts");
            System.out.println("7. Exit");
            System.out.println("============================================");
        }

        private void createAccount() {

            System.out.println();
            System.out.println("========== CREATE ACCOUNT ==========");

            String accountNumber = readAccountNumber();

            if (accounts.containsKey(accountNumber)) {
                System.out.println();
                System.out.println("Account already exists.");
                return;
            }

            String holderName = readHolderName();
            AccountType accountType = readAccountType();

            BigDecimal initialBalance =
                    readAmount("Enter initial balance: ");

            if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println();
                System.out.println("Balance cannot be negative.");
                return;
            }

            BankAccount account = new BankAccount(
                    accountNumber,
                    holderName,
                    initialBalance,
                    accountType
            );

            accounts.put(accountNumber, account);

            System.out.println();
            System.out.println("Account created successfully.");
            System.out.println("Account Number : " + accountNumber);
            System.out.println("Holder Name    : " + holderName);
            System.out.println("Account Type   : " + accountType);
            System.out.println("Balance        : ₹" + initialBalance);
        }

        private void depositMoney() {

            System.out.println();
            System.out.println("========== DEPOSIT ==========");

            String accountNumber = readAccountNumber();

            BankAccount account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println();
                System.out.println("Account not found.");
                return;
            }

            BigDecimal amount =
                    readAmount("Enter deposit amount: ");

            account.deposit(amount);

            System.out.println(
                    "Current Balance: ₹" + account.getBalance());
        }

        private void withdrawMoney() {

            System.out.println();
            System.out.println("========== WITHDRAW ==========");

            String accountNumber = readAccountNumber();

            BankAccount account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println();
                System.out.println("Account not found.");
                return;
            }

            BigDecimal amount =
                    readAmount("Enter withdrawal amount: ");

            boolean success = account.withdraw(amount);

            if (success) {
                System.out.println(
                        "Current Balance: ₹" + account.getBalance());
            }
        }

        private void checkBalance() {

            System.out.println();
            System.out.println("========== CHECK BALANCE ==========");

            String accountNumber = readAccountNumber();

            BankAccount account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println();
                System.out.println("Account not found.");
                return;
            }

            System.out.println();
            System.out.println("Account Number : "
                    + account.getAccountNumber());
            System.out.println("Holder Name    : "
                    + account.getHolderName());
            System.out.println("Account Type   : "
                    + account.getAccountType());
            System.out.println("Balance        : ₹"
                    + account.getBalance());
        }

        private void viewAccount() {

            System.out.println();
            System.out.println("========== VIEW ACCOUNT ==========");

            String accountNumber = readAccountNumber();

            BankAccount account = accounts.get(accountNumber);

            if (account == null) {
                System.out.println();
                System.out.println("Account not found.");
                return;
            }

            System.out.println();
            account.displayAccount();
        }

        private String readAccountNumber() {

            while (true) {

                System.out.print("Enter account number: ");

                String accountNumber =
                        scanner.nextLine().trim();

                if (accountNumber.isEmpty()) {
                    System.out.println(
                            "Account number cannot be empty.");
                    continue;
                }

                return accountNumber;
            }
        }

        private String readHolderName() {

            while (true) {

                System.out.print("Enter holder name: ");

                String holderName =
                        scanner.nextLine().trim();

                if (holderName.isEmpty()) {
                    System.out.println(
                            "Holder name cannot be empty.");
                    continue;
                }

                return holderName;
            }
        }

        private AccountType readAccountType() {

            while (true) {

                System.out.println();
                System.out.println("Select Account Type:");
                System.out.println("1. SAVINGS");
                System.out.println("2. CURRENT");

                int choice =
                        readInteger("Enter choice: ");

                if (choice == 1) {
                    return AccountType.SAVINGS;
                }

                if (choice == 2) {
                    return AccountType.CURRENT;
                }

                System.out.println(
                        "Invalid account type.");
            }
        }

        private BigDecimal readAmount(String message) {

            while (true) {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                try {
                    return new BigDecimal(input);
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Please enter a valid amount.");
                }
            }
        }

        private int readInteger(String message) {

            while (true) {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Please enter a valid number.");
                }
            }
        }
    }
}
