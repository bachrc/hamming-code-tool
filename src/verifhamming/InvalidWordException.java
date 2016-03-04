package verifhamming;

/**
 *
 * @author Yohann Bacha
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
