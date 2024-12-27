import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    private String stockName;
    private int numberOfShares;
    private double sharePrice;

    public Stock(String stockName, int numberOfShares, double sharePrice) {
        this.stockName = stockName;
        this.numberOfShares = numberOfShares;
        this.sharePrice = sharePrice;
    }

    public String getStockName() {
        return stockName;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public double getSharePrice() {
        return sharePrice;
    }

    public double calculateStockValue() {
        return numberOfShares * sharePrice;
    }

}

class StockPortfolio {
    private ArrayList<Stock> stocks;

    public StockPortfolio() {
        stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double calculateTotalValue() {
        double totalValue = 0;
        for (Stock stock : stocks) {
            totalValue += stock.calculateStockValue();
        }
        return totalValue;
    }

    public void printStockReport() {
        System.out.println("\tStock Report:\t");
        for (Stock stock : stocks) {
            System.out.println("Stock Name: " + stock.getStockName());
            System.out.println("Number of shares: " + stock.getNumberOfShares());
            System.out.println("Share Price: " + stock.getSharePrice());
            System.out.println("Total Value: " + stock.calculateStockValue());
            System.out.println("---------------------------------------");
        }
        System.out.println("Total Value of All Stocks: " + calculateTotalValue());
    }
}


public class StockAccountManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StockPortfolio portfolio = new StockPortfolio();


        System.out.println("Enter No. of Stocks: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter Details of Stock " + (i+1) + ": ");
            sc.nextLine();
            System.out.println("Stock Name: ");
            String stockName = sc.nextLine();
            System.out.println("Number of Shares: ");
            int numberOfShares = sc.nextInt();
            System.out.println("Share Price: ");
            double sharePrice = sc.nextDouble();
            portfolio.addStock(new Stock(stockName, numberOfShares, sharePrice));
        }

        portfolio.printStockReport();
        sc.close();
    }
}