import java.io.*;
import java.util.*;


class CompanyShares {
    private String stockSymbol;
    private int numberOfShares;
    private Date dateTime;

    public CompanyShares(String stockSymbol, int numberOfShares, Date dateTime) {
        this.stockSymbol = stockSymbol;
        this.numberOfShares = numberOfShares;
        this.dateTime = dateTime;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void addShares(int shares) {
        this.numberOfShares += shares;
    }

    public void subtractShares(int shares) {
        this.numberOfShares -= shares;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Stock Symbol: " + stockSymbol + ", Shares: " + numberOfShares + ", DateTime: " + dateTime;
    }
}


public class StockAccount_UC3 {
    private List<CompanyShares> companySharesList;

    public StockAccount_UC3(String filename) {
        companySharesList = new ArrayList<>();
        loadFromFile(filename);
    }

    private void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String stockSymbol = parts[0];
                int shares = Integer.parseInt(parts[1]);
                Date dateTime = new Date(Long.parseLong(parts[2]));
                companySharesList.add(new CompanyShares(stockSymbol, shares, dateTime));
            }
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    public double valueOf() {
        double totalValue = 0;
        for (CompanyShares shares : companySharesList) {
            double sharePrice = fetchSharePrice(shares.getStockSymbol());
            totalValue += sharePrice * shares.getNumberOfShares();
        }
        return totalValue;
    }

    public void buy(int amount, String symbol) {
        double sharePrice = fetchSharePrice(symbol);
        int sharesToBuy = (int) (amount / sharePrice);
        CompanyShares shares = findShares(symbol);
        if (shares == null) {
            shares = new CompanyShares(symbol, sharesToBuy, new Date());
            companySharesList.add(shares);
        } else {
            shares.addShares(sharesToBuy);
        }
    }

    public void sell(int amount, String symbol) {
        CompanyShares shares = findShares(symbol);
        if (shares == null) {
            System.out.println("Stock not found: " + symbol);
            return;
        }

        double sharePrice = fetchSharePrice(symbol);
        int sharesToSell = (int) (amount / sharePrice);
        if (sharesToSell > shares.getNumberOfShares()) {
            System.out.println("Not enough shares to sell.");
        } else {
            shares.subtractShares(sharesToSell);
        }
    }

    public void save(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (CompanyShares shares : companySharesList) {
                bw.write(shares.getStockSymbol() + "," + shares.getNumberOfShares() + "," + shares.getDateTime().getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    public void printReport() {
        System.out.println("Stock Report:");
        for (CompanyShares shares : companySharesList) {
            System.out.println(shares);
        }
        System.out.println("Total Value: " + valueOf());
    }

    private CompanyShares findShares(String symbol) {
        for (CompanyShares shares : companySharesList) {
            if (shares.getStockSymbol().equals(symbol)) {
                return shares;
            }
        }
        return null;
    }

    private double fetchSharePrice(String symbol) {
        return 100.0;
    }

    public static void main(String[] args) {
        StockAccount_UC3 account = new StockAccount_UC3("stocks.txt");
        account.printReport();

        account.buy(500, "APPLE");
        account.sell(200, "APPLE");

        account.printReport();
        account.save("updated_stocks.txt");
    }
}

