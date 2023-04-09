package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RouletteTest {
    
    private Roulette roulette;
    private Random random;
    private static Instant startedAt;
    
    @BeforeEach
    public void setUp() {
		System.out.println("Avant tous mes tests, j'initialise mes ressources");
        random = new Random();
        roulette = new Roulette(random);
    }
    
    @AfterEach
	public void undefCalculator() {
		System.out.println("Appel après chaque test, je mets le result a null. (Bien que pas utile pour l'instant)");
		roulette.setResultat(null);
	}
    
    @BeforeAll
	public static void initStartingTime() {
		System.out.println("Juste pour appliquer mon cours d'Openclassroom. Hahaha !");
		startedAt = Instant.now();
	}

	@AfterAll
	public static void showTestDuration() {
		System.out.println("Maintenant je calcule la duree. Apres tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}
    
    @Test
    public void testResultat() {
        roulette.setResultat("4");
        assertEquals(roulette.getResultat(), "4");
    }

    @Timeout(20) // Ici pour tester la performance d'une fonction
    @Test
    public void testTourner() throws InterruptedException {
        roulette.tourner();
        int result = Integer.valueOf(roulette.getResultat());
        assertTrue(result >= 0 && result <= 36);
    }
    
    @Test
    public void testGetCouleur() {
        String couleurRouge = roulette.getCouleur(1);
        String couleurNoire = roulette.getCouleur(2);
        String couleurVerte = roulette.getCouleur(0);
        
        assertEquals("Rouge", couleurRouge);
        assertEquals("Noir", couleurNoire);
        assertEquals("Vert", couleurVerte);
    }
    
	@ParameterizedTest(name = "testGagnerAvecCouleurGagnante") // j'utilise une fonction parametree pour couvrir tous les cas, au lieu d'ecrire plusieurs tests (bien que je prends un exemple pour pair, impair...
	@CsvSource({ "'Rouge','1', 50", "'Vert','0',50","'Vert','00',50", "'Noir', '2', 50", "'Rouge','2', 0", "'Vert','1',0", "'Noir', '1', 0"})
    public void testGagnerAvecCouleur(String pari, String numero, int result){
        roulette.setResultat(numero);
		int gain = roulette.gagnerAvecCouleur(pari, 10);
        assertTrue(gain == result);
    }
        
    @ParameterizedTest(name = "testGagnerAvecNumeroGagnant")
	@CsvSource({ "'2','1',0", "'0','00',0", "'0', '0', 100", "'00', '00', 100", "'2', '2', 100"}) //j'utilise CsvSource pour passer les paramettres, je pouvais utiliser ValueSource si c'etait un seul param 
    public void testGagnerAvecNumero(String pari, String numero, int result){
    	roulette.setResultat(numero);
    	int gain = roulette.gagnerAvecNumero(pari, 10);
        assertTrue( gain == result);
    }
    
}
