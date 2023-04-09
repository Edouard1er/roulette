package roulette;

import java.util.Random;

public class Roulette {

	private final Random random;
	private final int seconds = 1;
	private String resultat;

    public Roulette(Random random) {
        this.random = random;
    }
    
    public String getResultat() {
    	return this.resultat;
    }
    
    public void setResultat(String resultat) {
    	this.resultat = resultat;
    }

    public void tourner() throws InterruptedException {
    	Thread.sleep(seconds * 1000L); // wait for the ball to stop
        int numeroAleatoire = random.nextInt(38);
        if (numeroAleatoire == 37) {
            this.resultat = "00";
        }else {
        	this.resultat = Integer.toString(numeroAleatoire);
        }
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
    
    public int gagnerAvecCouleur(String color, int mise){
        if (color.equals(getCouleur(Integer.valueOf(this.resultat)))) {
            return mise * 5;
        }else {
        	return 0;
        }
    }
    
    public int gagnerAvecNumero(String numero, int mise){
        if(numero.equals(this.resultat)){
        	return mise * 10;
        }else {
        	return 0;
        }
    }
}
