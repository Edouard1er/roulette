package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

public class RouletteTest {
    
    private Roulette roulette;
    private static Instant startedAt;
    
    @BeforeEach
    @DisplayName("Configuration Initiale")
    public void setUp() {
		System.out.println("Avant tous mes tests, j'initialise mes ressources");
        roulette = new Roulette();
    }
    
    @BeforeAll
    @DisplayName("Debut du calcul de duree")
	public static void initStartingTime() {
		System.out.println("Juste pour appliquer mon cours d'Openclassroom. Hahaha !");
		startedAt = Instant.now();
	}

	@AfterAll
	@DisplayName("Fin du calcul de duree")
	public static void showTestDuration() {
		System.out.println("Maintenant je calcule la duree. Apres tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}
    
    @Test
    @DisplayName("Get et Set Resultat")
    public void testResultat() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    	Field field = Roulette.class.getDeclaredField("resultat");
        field.setAccessible(true);
        field.set(roulette, "4");
        assertEquals(roulette.getResultat(), "4");
    }

    @Timeout(20) // Ici pour tester la performance d'une fonction
    @Test
    @DisplayName("Tourner")
    public void testTourner() throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        roulette.tourner();
        int result = Integer.valueOf(roulette.getResultat());
        assertTrue(roulette.getResultat() == "00" || (result >= 0 && result <= 36));
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
    public void testGagnerAvecCouleur(String pari, String numero, int result) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = Roulette.class.getDeclaredField("resultat");
        field.setAccessible(true);
        field.set(roulette, numero);
		int gain = roulette.gagnerAvecCouleur(pari, 10);
        assertTrue(gain == result);
    }
        
    @ParameterizedTest(name = "testGagnerAvecNumeroGagnant")
	@CsvSource({ "'2','1',0", "'0','00',0", "'0', '0', 100", "'00', '00', 100", "'2', '2', 100"}) //j'utilise CsvSource pour passer les paramettres, je pouvais utiliser ValueSource si c'etait un seul param 
    public void testGagnerAvecNumero(String pari, String numero, int result) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
    	Field field = Roulette.class.getDeclaredField("resultat");
        field.setAccessible(true);
        field.set(roulette, numero);
    	int gain = roulette.gagnerAvecNumero(pari, 10);
        assertTrue( gain == result);
    }
    
}
