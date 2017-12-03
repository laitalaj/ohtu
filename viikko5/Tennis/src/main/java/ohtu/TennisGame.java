package ohtu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TennisGame {

    private static final Map<Integer, String> SCORE_NAMES;
    static {
        Map<Integer, String> scoreNames = new HashMap<>();
        scoreNames.put(0, "Love");
        scoreNames.put(1, "Fifteen");
        scoreNames.put(2, "Thirty");
        scoreNames.put(3, "Forty");
        SCORE_NAMES = Collections.unmodifiableMap(scoreNames);
    }
    private static final String TIE_NAME = "All";
    private static final String ENDGAME_TIE_NAME = "Deuce";
    private static final String SCORE_SEPARATOR = "-";
    private static final String ADVANTAGE_STRING = "Advantage ";
    private static final String WIN_STRING = "Win for ";
    private static final int ENDGAME_SCORE = 4;

    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            player1Score++;
        else
            player2Score++;
    }

    private String getEndgameScore() {
        int scoreDifference = player1Score - player2Score;
        if (scoreDifference==0) return ENDGAME_TIE_NAME;
        else if (scoreDifference==1) return ADVANTAGE_STRING + player1Name;
        else if (scoreDifference ==-1) return ADVANTAGE_STRING + player2Name;
        else if (scoreDifference>=2) return WIN_STRING + player1Name;
        else return WIN_STRING + player2Name;
    }

    private String getMidgameScore() {
        String score = SCORE_NAMES.get(player1Score) + SCORE_SEPARATOR;
        if(player2Score == player1Score) {
            score += TIE_NAME;
        } else {
            score += SCORE_NAMES.get(player2Score);
        }
        return score;
    }

    public String getScore() {
        if (player1Score >= ENDGAME_SCORE || player2Score >= ENDGAME_SCORE) {
            return getEndgameScore();
        } else {
            return getMidgameScore();
        }
    }
}