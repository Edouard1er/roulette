package roulette;

import java.util.Random;

public class Roulette {

	private final Random random;
	private final int seconds = 1;

    public Roulette(Random random) {
        this.random = random;
    }

    public String tourner() throws InterruptedException {
        Thread.sleep(seconds * 1000L); // wait for the ball to stop
        int result = random.nextInt(38);
        if (result == 37) {
            return "00";
        }
        return Integer.toString(result);
    }
    
    public String getCouleur(int numero) {
        if (numero == 0 ) {
            return "Vert";
        } else if (numero % 2 == 0) {
            return "Noir";
        } else {
            return "Rouge";
        }
    }
    
    public int gagner(String color, int mise) throws InterruptedException {
        int result = Integer.valueOf(tourner());
        if (color.equals(getCouleur(result))) {
            return mise * 5;
        }else {
        	return 0;
        }
    }
    
    public int gagner(int numero, int mise) throws InterruptedException {
        if(numero == Integer.valueOf(tourner())) {
        	return mise * 10;
        }else {
        	return 0;
        }
    }
    
    
}
