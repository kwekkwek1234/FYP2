package my.edu.tarc.user.fyp;

public class Transaction {
    String transactionId, toWho, transactionDesc,date,time,balance,amount;

    public Transaction() {
    }

    public Transaction(String transactionId, String toWho, String transactionDesc, String date, String time,String balance,String amount) {
        this.transactionId = transactionId;
        this.toWho = toWho;
        this.transactionDesc = transactionDesc;
        this.date = date;
        this.time = time;
        this.balance = balance;
        this.amount = amount;
    }

    public Transaction(String transactionId, String toWho, String transactionDesc, String amount) {
        this.transactionId = transactionId;
        this.toWho = toWho;
        this.transactionDesc = transactionDesc;
        this.amount = amount;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
