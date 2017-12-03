
package ohtu.intjoukkosovellus;

import java.util.*;

public class IntJoukko {

    private Set<Integer> ints;

    public IntJoukko() {
        ints = new LinkedHashSet<>();
    }

    public IntJoukko(Set<Integer> ints) {
        this.ints = ints;
    }

    public IntJoukko(int kapasiteetti) {
        this();
    }


    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        this();
    }

    public boolean lisaa(int luku) {
        return ints.add(luku);
    }

    public boolean lisaaKaikki(Collection<Integer> luvut) {
        return ints.addAll(luvut);
    }

    public boolean kuuluu(int luku) {
        return ints.contains(luku);
    }

    public boolean poista(int luku) {
        return ints.remove(luku);
    }

    public boolean poistaKaikki(Collection<Integer> luvut) {
        return ints.removeAll(luvut);
    }

    public int mahtavuus() {
        return ints.size();
    }


    @Override
    public String toString() {
        String repr = ints.toString();
        return "{" + repr.substring(1, repr.length() - 1) + "}";
    }

    public int[] toIntArray() {
        return ints.stream().mapToInt(Integer::intValue).toArray(); // Tää vissiin simppelein tapa saada Integer-collectionista int-array .__.
    }


    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        yhdiste.lisaaKaikki(a.getInts());
        yhdiste.lisaaKaikki(b.getInts());
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int i: a.getInts()) {
            if(b.kuuluu(i)) {
                leikkaus.lisaa(i);
            }
        }
        return leikkaus;
    }

    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();
        erotus.lisaaKaikki(a.getInts());
        erotus.poistaKaikki(b.getInts());
        return erotus;
    }

    private Set<Integer> getInts() {
        return new LinkedHashSet<>(ints);
    }
}