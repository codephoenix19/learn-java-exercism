class BankAccount {
    private int balance;

    private boolean isActive;

    boolean isActive() {
        return this.isActive;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    void open() throws BankAccountActionInvalidException {
        if (isActive()) {
            throw new BankAccountActionInvalidException("Account already open");
        }
        setActive(true);
        setBalance(0);
    }

    void close() throws BankAccountActionInvalidException {
        if (!isActive()) {
            throw new BankAccountActionInvalidException("Account not open");
        }
        setBalance(0);
        setActive(false);
    }

    synchronized int getBalance() throws BankAccountActionInvalidException {
        if (!isActive()) {
            throw new BankAccountActionInvalidException("Account closed");
        }
        return this.balance;
    }

    synchronized void deposit(int amount) throws BankAccountActionInvalidException {
        if (!isActive()) {
            throw new BankAccountActionInvalidException("Account closed");
        }

        if(amount < 0) {
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }

        int currentBalance = this.getBalance();
        setBalance(currentBalance + amount);
    }

    synchronized void withdraw(int amount) throws BankAccountActionInvalidException {
        if (!isActive()) {
            throw new BankAccountActionInvalidException("Account closed");
        }

        if(amount < 0) {
            throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");
        }

        int currentBalance = this.getBalance();
        if (currentBalance < amount) {
            throw new BankAccountActionInvalidException("Cannot withdraw more money than is currently in the account");
        }
        setBalance(currentBalance - amount);
    }
}