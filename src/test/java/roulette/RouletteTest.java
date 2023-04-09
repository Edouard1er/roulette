package roulette;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RouletteTest {
    
    private Roulette roulette;
    private Random random;
    
    @BeforeEach
    public void setUp() {
        random = new Random();
        roulette = new Roulette(random);
    }

    @Test
    public void testTourner() throws InterruptedException {
        String result = roulette.tourner();
        assertNotNull(result);
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
    
	@ParameterizedTest(name = "testGagnerAvecCouleurGagnante")
	@CsvSource({ "'Rouge',50", "'Vert',50", "'Noir', 50"})
    public void testGagnerAvecCouleurGagnante(String couleur, int result) throws InterruptedException {
        int gain = roulette.gagnerAvecCouleur(couleur, 10);
        assertTrue(gain == result);
    }
    
    @ParameterizedTest(name = "testGagnerAvecCouleurPerdante")
	@CsvSource({ "'Rouge',0", "'Vert',0", "'Noir', 0"})
    public void testGagnerAvecCouleurPerdante(String couleur, int result) throws InterruptedException {
    	int gain = roulette.gagnerAvecCouleur(couleur, 10);
        assertTrue(gain == result);
    }
    
    @ParameterizedTest(name = "testGagnerAvecNumeroGagnant")
	@CsvSource({ "'1',100", "'4',100", "'6', 100"})
    public void testGagnerAvecNumeroGagnant(String numero, int result) throws InterruptedException {
        int gain = roulette.gagnerAvecNumero(numero, 10);
        assertTrue( gain == result);
    }
    
    @ParameterizedTest(name = "testGagnerAvecNumeroPerdant")
	@CsvSource({ "'1',0", "'2',0", "'3', 0"})
    public void testGagnerAvecNumeroPerdant(String numero, int result) throws InterruptedException {
    	int gain = roulette.gagnerAvecNumero(numero, 10);
    	assertTrue( gain == result);
    }
}
