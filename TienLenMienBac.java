package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
//
//public class TienLenMienBac extends Game {
//    private BasePlayer currentPlayer;
//    private List<Card> lastPlayedCards = new ArrayList<>();
//    private Scanner scanner;
//
//    public TienLenMienBac(int numRealPlayers, int numBots, ArrayList<BasePlayer> players, String mode) {
//        super("TienLenMienBac", numRealPlayers, numBots, 13, players, mode);
//        this.scanner = new Scanner(System.in);
//    }
//
//    public void startGame() {
//        this.deck.createDeck();
//        this.deck.shuffle();
//        this.dealCardsToPlayers();
//        this.determineFirstPlayer();
//        this.playGame();
//    }
//
//    private void determineFirstPlayer() {
//        BasePlayer firstPlayer = this.players.get(0); // Chọn người đầu tiên làm mặc định
//        Card smallestCard = firstPlayer.getHand().get(0); // Lấy quân bài đầu tiên
//
//        for (BasePlayer player : this.players) {
//            for (Card card : player.getHand()) {
//                if (card.compareTo(smallestCard) < 0) {
//                    smallestCard = card;
//                    firstPlayer = player;
//                }
//            }
//        }
//
//        this.currentPlayer = firstPlayer;
//        System.out.println("🎉 " + firstPlayer.getName() + " starts with the smallest card: " + smallestCard);
//    }
//
//    public void playGame() {
//        BasePlayer lastSuccessfulPlayer = null; // Lưu người chơi cuối cùng đã đánh bài hợp lệ
//        int skipCount = 0; // Đếm số người bỏ lượt liên tiếp
//
//        while (this.players.size() > 1) {
//            System.out.println(skipCount);
//            // Chơi đến khi chỉ còn 1 người
//            System.out.println("\n🔹 " + this.currentPlayer.getName() + "'s turn");
//
//            // 🃏 Hiển thị bài đã đánh trước đó
//            if (!this.lastPlayedCards.isEmpty()) {
//                System.out.println("🃏 Previous player played: " + this.lastPlayedCards);
//            } else {
//                System.out.println("🃏 No previous cards played.");
//            }
//
//            if (this.currentPlayer instanceof Player) {
//                this.currentPlayer.displayCards(); // Hiển thị bài của người chơi hiện tại
//            }
//
//            List<Card> playedCards;
//            boolean validMove;
//
//            do {
//                playedCards = this.currentPlayer.playTurn(); // Người chơi thực hiện nước đi
//                validMove = (playedCards != null && this.isValidMove(playedCards));
//
//                if (playedCards == null) { // Người chơi bỏ lượt
//                    skipCount++;
//                    break;
//                }
//                if (!validMove) {
//                    for (Card card : playedCards) {
//                        this.currentPlayer.hand.add(card);
//                    }
//                    System.out.println("❌ Invalid move! Try again.");
//                }
//            } while (!validMove);
//
//            // ✅ Cập nhật nước đi cuối cùng
//            if (playedCards != null) {
//                this.lastPlayedCards = playedCards;
//                lastSuccessfulPlayer = this.currentPlayer; // Cập nhật người chơi vừa đánh hợp lệ
//                System.out.println("✅ " + this.currentPlayer.getName() + " played: " + playedCards);
//                skipCount = 0; // Reset số lượt bỏ qua
//            }
//
//            // Nếu tất cả đều bỏ lượt, vòng mới bắt đầu từ người chơi đánh bài cuối cùng
//            if (skipCount + 1 == this.players.size()) {
//                System.out.println("🔄 All players skipped! Resetting last played cards.");
//                this.lastPlayedCards.clear(); // Reset bài trước đó
//                skipCount = 0; // Reset bộ đếm skip
//            }
//
//            // Kiểm tra nếu người chơi hết bài
//            if (this.currentPlayer.getHand().isEmpty()) {
//                System.out.println("🏆 " + this.currentPlayer.getName() + " has finished all cards!");
//                this.players.remove(this.currentPlayer);
//            }
//
//            this.nextPlayer(); // Chuyển sang lượt người chơi tiếp theo
//        }
//
//        // 🏁 Thông báo người thua cuộc
//        if (!this.players.isEmpty()) {
//            System.out.println("❌ " + this.players.get(0).getName() + " is the last one with cards! They lost the game.");
//        }
//    }
//
//    private boolean isValidMove(List<Card> playedCards) {
//        if (this.lastPlayedCards.isEmpty()) return true;
//
//        int playedValue = playedCards.get(0).getCardRank();
//        int lastValue = this.lastPlayedCards.get(0).getCardRank();
//
//        if (this.isSingle(playedCards) && this.isSingle(this.lastPlayedCards)) {
//            return playedValue > lastValue;
//        }
//        if (this.isPair(playedCards) && this.isPair(this.lastPlayedCards)) {
//            return playedCards.get(1).getCardRank() > this.lastPlayedCards.get(1).getCardRank();
//        }
//        if (this.isTriple(playedCards) && this.isTriple(this.lastPlayedCards)) {
//            return playedCards.get(2).getCardRank() > this.lastPlayedCards.get(2).getCardRank();
//        }
//        if (this.isStraight(playedCards) && this.isStraight(this.lastPlayedCards)) {
//            return playedCards.size() == this.lastPlayedCards.size() &&
//                    playedCards.get(playedCards.size() - 1).getCardRank() >
//                            this.lastPlayedCards.get(this.lastPlayedCards.size() - 1).getCardRank();
//        }
//        if (this.isFourOfAKind(playedCards)) {
//            return this.isSingle(this.lastPlayedCards) && lastValue == 2;
//        }
//        if (this.isFourOfAKind(this.lastPlayedCards) && this.isFourOfAKind(playedCards)) {
//            return playedValue > lastValue;
//        }
//
//        return false;
//    }
//
//    private boolean isSingle(List<Card> cards) {
//        return cards.size() == 1;
//    }
//
//    private boolean isPair(List<Card> cards) {
//        return cards.size() == 2 && cards.get(0).getCardRank() == cards.get(1).getCardRank();
//    }
//
//    private boolean isTriple(List<Card> cards) {
//        return cards.size() == 3 && cards.get(0).getCardRank() == cards.get(1).getCardRank() &&
//                cards.get(0).getCardRank() == cards.get(2).getCardRank();
//    }
//
//    private boolean isStraight(List<Card> cards) {
//        if (cards.size() < 3) return false;
//
//        cards.sort((a, b) -> a.getCardRank() - b.getCardRank());
//
//        for (int i = 1; i < cards.size(); i++) {
//            if (cards.get(i).getCardRank() - cards.get(i - 1).getCardRank() != 1) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean isFourOfAKind(List<Card> cards) {
//        if (cards.size() != 4) return false;
//
//        int value1 = cards.get(0).getCardRank();
//        int value2 = cards.get(1).getCardRank();
//        int value3 = cards.get(2).getCardRank();
//        int value4 = cards.get(3).getCardRank();
//
//        return value1 == value2 && value1 == value3 && value1 == value4;
//    }
//
//    private void nextPlayer() {
//        int currentIndex = this.players.indexOf(this.currentPlayer);
//        this.currentPlayer = this.players.get((currentIndex + 1) % this.players.size());
//    }
//}

public class TienLenMienBac extends Game {
    private BasePlayer currentPlayer;
    private List<Card> lastPlayedCards = new ArrayList<>();
    protected GameLog gameLog;
    public TienLenMienBac(int numRealPlayers, int numBots, ArrayList<BasePlayer> players, String mode, GameLog gameLog) {
        super("TienLenMienBac", numRealPlayers, numBots, 13, players, mode);
        this.gameLog = gameLog;
    }

    public void startGame() {
        this.deck.createDeck();
        this.deck.shuffle();
        this.dealCardsToPlayers();
        this.determineFirstPlayer();
        this.playGame();
    }

    private void determineFirstPlayer() {
        BasePlayer firstPlayer = this.players.get(0);
        Card smallestCard = firstPlayer.getHand().get(0);

        for (BasePlayer player : this.players) {
            for (Card card : player.getHand()) {
                if (card.compareTo(smallestCard) < 0) {
                    smallestCard = card;
                    firstPlayer = player;
                }
            }
        }

        this.currentPlayer = firstPlayer;
        this.gameLog.updateLog("🎉 " + firstPlayer.getName() + " starts with the smallest card: " + smallestCard);
    }

    public void playGame() {
        BasePlayer lastSuccessfulPlayer = null;
        int skipCount = 0;

        while (this.players.size() > 1) {
            System.out.println(skipCount);

            // this.gameLog.updateLog("🔹 " + this.currentPlayer.getName() + "'s turn");

            if (!this.lastPlayedCards.isEmpty()) {
                this.gameLog.updateLog("🃏 Previous player played: " + this.lastPlayedCards);
            } else {
                this.gameLog.updateLog("🃏 No previous cards played.");
            }

            if (this.currentPlayer instanceof Player) {
                this.currentPlayer.displayCards();
            }

            List<Card> playedCards;
            boolean validMove;

            do {
                playedCards = this.currentPlayer.playTurn();
                validMove = (playedCards != null && this.isValidMove(playedCards));

                if (playedCards == null) {
                    skipCount++;
                    break;
                }
                if (!validMove) {
                    for (Card card : playedCards) {
                        this.currentPlayer.hand.add(card);
                    }
                    this.gameLog.updateLog("❌ Invalid move! Try again.");
                }
            } while (!validMove);

            if (playedCards != null) {
                this.lastPlayedCards = playedCards;
                lastSuccessfulPlayer = this.currentPlayer;
                this.gameLog.updateLog("✅ " + this.currentPlayer.getName() + " played: " + playedCards);
                skipCount = 0;
            }

            if (skipCount + 1 == this.players.size()) {
                this.gameLog.updateLog("🔄 All players skipped! Resetting last played cards.");
                this.lastPlayedCards.clear();
                skipCount = 0;
            }

            if (this.currentPlayer.getHand().isEmpty()) {
                this.gameLog.updateLog("🏆 " + this.currentPlayer.getName() + " has finished all cards!");
                this.players.remove(this.currentPlayer);
            }

            this.nextPlayer();
        }

        if (!this.players.isEmpty()) {
            this.gameLog.updateLog("❌ " + this.players.get(0).getName() + " is the last one with cards! They lost the game.");

        }
    }

    private boolean isValidMove(List<Card> playedCards) {
        if (this.lastPlayedCards.isEmpty()) return true;

        int playedValue = playedCards.get(0).getCardRank();
        int lastValue = this.lastPlayedCards.get(0).getCardRank();

        if (this.isSingle(playedCards) && this.isSingle(this.lastPlayedCards)) {
            return playedValue > lastValue;
        }
        if (this.isPair(playedCards) && this.isPair(this.lastPlayedCards)) {
            return playedCards.get(1).getCardRank() > this.lastPlayedCards.get(1).getCardRank();
        }
        if (this.isTriple(playedCards) && this.isTriple(this.lastPlayedCards)) {
            return playedCards.get(2).getCardRank() > this.lastPlayedCards.get(2).getCardRank();
        }
        if (this.isStraight(playedCards) && this.isStraight(this.lastPlayedCards)) {
            return playedCards.size() == this.lastPlayedCards.size() &&
                    playedCards.get(playedCards.size() - 1).getCardRank() >
                            this.lastPlayedCards.get(this.lastPlayedCards.size() - 1).getCardRank();
        }
        if (this.isFourOfAKind(playedCards)) {
            return this.isSingle(this.lastPlayedCards) && lastValue == 2;
        }
        if (this.isFourOfAKind(this.lastPlayedCards) && this.isFourOfAKind(playedCards)) {
            return playedValue > lastValue;
        }

        return false;
    }

    private boolean isSingle(List<Card> cards) {
        return cards.size() == 1;
    }

    private boolean isPair(List<Card> cards) {
        return cards.size() == 2 && cards.get(0).getCardRank() == cards.get(1).getCardRank();
    }

    private boolean isTriple(List<Card> cards) {
        return cards.size() == 3 && cards.get(0).getCardRank() == cards.get(1).getCardRank() &&
                cards.get(0).getCardRank() == cards.get(2).getCardRank();
    }

    private boolean isStraight(List<Card> cards) {
        if (cards.size() < 3) return false;

        cards.sort((a, b) -> a.getCardRank() - b.getCardRank());

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getCardRank() - cards.get(i - 1).getCardRank() != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isFourOfAKind(List<Card> cards) {
        if (cards.size() != 4) return false;

        int value1 = cards.get(0).getCardRank();
        int value2 = cards.get(1).getCardRank();
        int value3 = cards.get(2).getCardRank();
        int value4 = cards.get(3).getCardRank();

        return value1 == value2 && value1 == value3 && value1 == value4;
    }

    private void nextPlayer() {
        int currentIndex = this.players.indexOf(this.currentPlayer);
        this.currentPlayer = this.players.get((currentIndex + 1) % this.players.size());
    }
}




