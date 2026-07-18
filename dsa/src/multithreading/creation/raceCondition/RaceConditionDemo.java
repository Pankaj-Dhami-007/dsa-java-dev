package multithreading.creation.raceCondition;

public class RaceConditionDemo {
    static class BankAccount {
        private int balance;

        public BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        // UNSAFE - Race condition exists!
        public void withdraw(int amount) {
            if (balance >= amount) {
                // Simulate some processing time
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                balance = balance - amount;
                System.out.println(Thread.currentThread().getName() +
                        " withdrew $" + amount + ". Balance: $" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " - Insufficient funds!");
            }
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        // Both threads will try to withdraw from the same account
        Thread person1 = new Thread(() -> {
            account.withdraw(800);
        }, "Person-1");

        Thread person2 = new Thread(() -> {
            account.withdraw(800);
        }, "Person-2");

        person1.start();
        person2.start();

        person1.join();
        person2.join();

        System.out.println("Final balance: $" + account.getBalance());
    }
}
