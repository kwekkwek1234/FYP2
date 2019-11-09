package my.edu.tarc.user.fyp;

public class Transaction {
    String transactionId, toWho, transactionDesc,dateTime,balance,amount,status;

    public Transaction() {
    }

    public Transaction(String transactionId, String toWho, String transactionDesc, String dateTime,String balance,String amount,String status) {
        this.transactionId = transactionId;
        this.toWho = toWho;
        this.transactionDesc = transactionDesc;
        this.dateTime = dateTime;
        this.balance = balance;
        this.amount = amount;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
