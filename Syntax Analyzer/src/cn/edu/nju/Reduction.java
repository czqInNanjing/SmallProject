package cn.edu.nju;

/**
 * @author Qiang
 * @date 01/11/2016
 */
public class Reduction {

    public char nonTerminal;
    public String derivation;

    public Reduction(char nonTerminal, String derivation){
        this.nonTerminal = nonTerminal;
        this.derivation = derivation;
    }

    public Reduction(char nonTerminal, char[] derivation){
        this.nonTerminal = nonTerminal;
        this.derivation = new String(derivation);
    }

    @Override
    public String toString() {
        return nonTerminal + " -> " + derivation;
    }

    public int getLength() {
        return derivation.length();
    }
}
