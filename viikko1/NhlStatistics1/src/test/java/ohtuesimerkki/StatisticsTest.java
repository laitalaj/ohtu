package ohtuesimerkki;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jiilaitala on 2017-03-26.
 */
public class StatisticsTest {

    Reader readerStub = new Reader() {
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));
            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() throws Exception {
        stats = new Statistics(readerStub);
    }

    @Test
    public void search() throws Exception {
        Player p = stats.search("urr");
        assertEquals("Kurri", p.getName());
        assertEquals("EDM", p.getTeam());
        assertEquals(37, p.getGoals());
        assertEquals(53, p.getAssists());
        assertEquals(null, stats.search("Osama bin Laden"));
    }

    @Test
    public void team() throws Exception {
        List<Player> team = stats.team("EDM");
        assertEquals(3, team.size());
        for(Player p: team) {
            assertEquals("EDM", p.getTeam());
        }
    }

    @Test
    public void topScorers() throws Exception {
        List<Player> tops = stats.topScorers(2);
        assertEquals(2, tops.size());
        assertEquals("Gretzky", tops.get(0).getName());
        assertEquals("Lemieux", tops.get(1).getName());
    }

}