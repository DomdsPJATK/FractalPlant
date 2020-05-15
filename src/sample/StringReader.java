package sample;

public class StringReader {

    private String text;
    private int currentIndex;

    public StringReader(String str) {
        if(str == null) str = "";
        this.text = str;
        this.currentIndex = 0;
    }

    public char nextChar(){
        return text.charAt(currentIndex++);
    }

    public String getText() {
        return text;
    }

    public boolean hasNextChar(){
        return currentIndex < text.length();
    }

    public void setText(String text){
        this.text = text;
    }

}
