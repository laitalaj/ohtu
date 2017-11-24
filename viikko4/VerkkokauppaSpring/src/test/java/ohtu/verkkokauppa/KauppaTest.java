package ohtu.verkkokauppa;

import org.junit.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    private Pankki pankki;
    private Viitegeneraattori viite;
    private Varasto varasto;
    private Kauppa k;
    private Tuote kalja;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);

        viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42).thenReturn(43);

        kalja = new Tuote(5, "olut", 1);
        varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(5)).thenReturn(9001);
        when(varasto.haeTuote(5)).thenReturn(kalja);
        when(varasto.saldo(7)).thenReturn(1);
        when(varasto.haeTuote(7)).thenReturn(new Tuote(7, "porakone", 79));
        when(varasto.saldo(12)).thenReturn(0);
        when(varasto.haeTuote(12)).thenReturn(new Tuote(12, "pupun ruaka", 7));

        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto("pekka", 42, "12345", "33333-44455",5);
    }

    @Test
    public void ostosToimiiKahdellaEriVarastossaOlevallaTuotteella() {
        when(viite.uusi()).thenReturn(66);

        k.aloitaAsiointi();
        k.lisaaKoriin(5);
        k.lisaaKoriin(7);
        k.tilimaksu("raimo", "ryä raha tili-39-45-95-11");

        verify(pankki).tilisiirto("raimo", 66, "ryä raha tili-39-45-95-11", "33333-44455", 80);
    }

    @Test
    public void ostosToimiiKahdellaSamallaVarastossaOlevallaTuotteella() {
        k.aloitaAsiointi();
        k.lisaaKoriin(5);
        k.lisaaKoriin(5);
        k.tilimaksu("tarmo", "kjeh kjäh säästöt-h3h3-m3m3s");

        verify(pankki).tilisiirto("tarmo", 42, "kjeh kjäh säästöt-h3h3-m3m3s", "33333-44455", 2);
    }

    @Test
    public void ostosToimiiYhdellaVarastossaOlevallaJaYhdellaLoppuneellaTuotteella() {
        k.aloitaAsiointi();
        k.lisaaKoriin(5);
        k.lisaaKoriin(12);
        k.tilimaksu("taneli", "viikon_lopu_r14nt0_t1l1");

        verify(pankki).tilisiirto("taneli", 42, "viikon_lopu_r14nt0_t1l1", "33333-44455", 1);
    }

    @Test
    public void aloitaAsiointiNollaaEdellisen() {
        k.aloitaAsiointi();
        k.lisaaKoriin(5);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("raimo", "ryä raha tili-39-45-95-11");
        verify(pankki).tilisiirto("raimo", 42, "ryä raha tili-39-45-95-11", "33333-44455", 5);
    }

    @Test
    public void uusiViitenumeroJokaiselleMaksutapahtumalle() {
        k.aloitaAsiointi();
        k.lisaaKoriin(5);
        k.tilimaksu("taneli", "viikon_lopu_r14nt0_t1l1");
        verify(pankki).tilisiirto("taneli", 42, "viikon_lopu_r14nt0_t1l1", "33333-44455", 1);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("raimo", "ryä raha tili-39-45-95-11");
        verify(pankki).tilisiirto("raimo", 43, "ryä raha tili-39-45-95-11", "33333-44455", 5);
    }

    @Test
    public void tuotePoistuuKoristaJaPalaaVarastoon() {
        when(viite.uusi()).thenReturn(66);

        k.aloitaAsiointi();
        k.lisaaKoriin(5);
        k.lisaaKoriin(7);

        k.poistaKorista(5);
        verify(varasto).palautaVarastoon(kalja);

        k.tilimaksu("raimo", "ryä raha tili-39-45-95-11");
        verify(pankki).tilisiirto("raimo", 66, "ryä raha tili-39-45-95-11", "33333-44455", 79);
    }

}