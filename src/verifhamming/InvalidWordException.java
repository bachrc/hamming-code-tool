/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verifhamming;

/**
 *
 * @author totorolepacha
 */
public class InvalidWordException extends Exception{
    public String word;
    public int indice;
    
    public InvalidWordException(String word, int indice) {
        this.word = word;
        this.indice = indice;
    }
    
    public String toString() {
        return "Erreur : Le mot " + this.word + " a une erreur à l'indice " + this.indice + ".";
    }
}
