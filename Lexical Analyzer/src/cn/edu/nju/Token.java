package cn.edu.nju;

/**
 * Token Class Definition
 * @author Qiang
 * @version  15/10/2016
 */
public class Token {

    private Constants.TOKEN_TYPE tokenType;
    private String value;
    private String description;

    @Override
    public String toString() {
        if (description != null){
            return "( " + tokenType.toString() + " , " + value + " , " + description + " )" ;
        }
        return "( " + tokenType.toString() + " , " + value + " )";
    }

    public Token(Constants.TOKEN_TYPE tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public Token(Constants.TOKEN_TYPE tokenType, String value, String description) {
        this.tokenType = tokenType;
        this.value = value;
        this.description = description;
    }


}
