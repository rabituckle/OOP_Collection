package project;

import java.util.HashMap;

public class GameOrder {
    private static final HashMap<String, String[]> rankOrders = new HashMap<>();
    private static final HashMap<String, String[]> suitOrders = new HashMap<>();

    static {
        // Rank orders for different games
        rankOrders.put("TienLenMienNam", new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"});
        rankOrders.put("TienLenMienBac", new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"});
        rankOrders.put("BaCay", new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"});

        // Suit orders for different games (lower index means weaker suit)
        suitOrders.put("TienLenMienNam", new String[]{"S", "C", "D", "H"});
        suitOrders.put("TienLenMienBac", new String[]{"S", "C", "D", "H"});
        suitOrders.put("BaCay", new String[]{"S", "C", "D", "H"});   // Same suit order
    }

    // Get rank order for a specific game
    public static String[] getRankOrder(String gameType) {
        return rankOrders.getOrDefault(gameType, rankOrders.get("TienLenMienBac")); // Default to Tiến Lên
    }

    // Get suit order for a specific game
    public static String[] getSuitOrder(String gameType) {
        return suitOrders.getOrDefault(gameType, suitOrders.get("TienLenMienBac")); // Default to Tiến Lên
    }
}
