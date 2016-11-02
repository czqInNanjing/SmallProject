package cn.edu.nju;

import java.util.*;

/**
 *
 * @author Qiang
 * @version  01/11/2016
 */
public class Analyzer {

    private Map<Pair, Integer> actionTable;
    private Map<Pair, Integer> gotoTable;
    private List<Reduction> reductions;

    private static final String tableSplit = " ";
    private static final String CFGsplit = ":";
    public Analyzer(List<String> parseTable, List<String> CFG){


        actionTable = new HashMap<>(3000);
        gotoTable = new HashMap<>(3000);
        reductions = new ArrayList<>();
        initializeParseTable(parseTable);
        initializeReductions(CFG);

    }



    public List<Reduction> parseInput(char[] userInput){
        List<Reduction> reductionsSeq = new ArrayList<>(100);

        Stack<Character> symbolStack = new Stack<>();
        Stack<Integer> stateStack = new Stack<>();

        // Stack initialization
        symbolStack.push('$');
        stateStack.push(0);


        int state;
        Pair tempPair;
        Reduction reduction;
        char currentChar = userInput[0];
        for (int i = 0;;) {


            state = stateStack.peek();
            if (symbolStack.size() > stateStack.size()) {
                tempPair = new Pair(state, symbolStack.peek());
            } else {
                tempPair = new Pair(state, currentChar);
            }


            if (actionTable.containsKey(tempPair)) {

                int next = actionTable.get(tempPair);
                if (next == 0) {              // r0 acceptable
                    reductionsSeq.add(reductions.get(0));
                    break;
                } else if (next > 0) {        // shift
                    symbolStack.push(currentChar);
                    stateStack.push(next);
                    currentChar = userInput[++i];
                } else {                      // reducible

                    reduction = reductions.get( -next );
                    reductionsSeq.add(reduction);

                    int redLen = reduction.getLength();
                    for (int j = 0; j < redLen; j++) {
                        symbolStack.pop();
                        stateStack.pop();


                    }
                    symbolStack.push(reduction.nonTerminal);



                }


            } else if (gotoTable.containsKey(tempPair)) {
                stateStack.push(gotoTable.get(tempPair));


            }

        }





        return reductionsSeq;
    }


    private void initializeParseTable(List<String> parseTable){

        for (String temp : parseTable) {
            String[] pairs = temp.split(tableSplit);
            int state = Integer.parseInt(pairs[0]);

            for (int i = 1; i < pairs.length; i++) {
                char symbol = pairs[i].charAt(0);

                if (Character.isUpperCase(pairs[i].charAt(2))){
                    actionTable.put(new Pair(state, symbol), Integer.parseInt(pairs[i].substring(3)));

                } else if (Character.isLowerCase(pairs[i].charAt(2))){
                    actionTable.put(new Pair(state, symbol),  - Integer.parseInt(pairs[i].substring(3)) );
                } else{
                    gotoTable.put(new Pair(state, symbol) ,Integer.parseInt(pairs[i].substring(2)) );
                }


            }



        }




    }

    private void initializeReductions(List<String> cfg) {

        for (String temp : cfg) {
            String[] reduction = temp.split(CFGsplit);
            reductions.add(new Reduction(reduction[0].charAt(0) , reduction[1]));
        }

    }
    private class Pair {

        private int state;
        private char symbol;

        Pair(int state, char symbol){
            this.state = state;
            this.symbol = symbol;
        }



        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            return state == pair.state && symbol == pair.symbol;

        }

        @Override
        public int hashCode() {
            int result = state;
            result = 31 * result + (int) symbol;
            return result;
        }

        @Override
        public String toString() {
            return "state " + state + " symbol " + symbol;
        }


    }
}
