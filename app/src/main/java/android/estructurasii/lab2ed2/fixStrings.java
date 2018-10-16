package android.estructurasii.lab2ed2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fixStrings {
    public String ChangeTroubleStrings (String linetochange) {

        if (linetochange.contains("\n")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\n"), Matcher.quoteReplacement("ꦃ"));
        }
        if (linetochange.contains("\t")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\t"), Matcher.quoteReplacement("ꦄ"));
        }
        if (linetochange.contains("\r")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\r"), Matcher.quoteReplacement("ﬀ"));
        }
        if (linetochange.contains("\f")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\f"), Matcher.quoteReplacement("ﬆ"));
        }
        if (linetochange.contains("\b")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\b"), Matcher.quoteReplacement("ﬡ"));
        }
        if (linetochange.contains("\"")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\""), Matcher.quoteReplacement("תּ"));
        }
        if (linetochange.contains("\'")) {
            linetochange = linetochange.replaceAll(Pattern.quote("\'"), Matcher.quoteReplacement("פֿ"));
        }

        return linetochange;
    }
    public String RevertChanges(String revert){
        if(revert.contains("ꦃ")) {
            revert = revert.replaceAll(Pattern.quote("ꦃ"), Matcher.quoteReplacement("\n"));
        }
        if(revert.contains("ꦄ")) {
            revert = revert.replaceAll(Pattern.quote("ꦄ"), Matcher.quoteReplacement("\r"));
        }
        if(revert.contains("ﬀ")) {
            revert = revert.replaceAll(Pattern.quote("ﬀ"), Matcher.quoteReplacement("\t"));
        }
        if(revert.contains("ﬆ")) {
            revert = revert.replaceAll(Pattern.quote("ﬆ"), Matcher.quoteReplacement("\f"));
        }
        if(revert.contains("ﬡ")) {
            revert = revert.replaceAll(Pattern.quote("ﬡ"), Matcher.quoteReplacement("\b"));
        }
        if(revert.contains("תּ")) {
            revert = revert.replaceAll(Pattern.quote("תּ"), Matcher.quoteReplacement("\""));
        }
        if(revert.contains("פֿ")){
            revert = revert.replaceAll(Pattern.quote("פֿ"), Matcher.quoteReplacement("\'"));
        }
        if(revert.contains("³")){
            revert = revert.replaceAll(Pattern.quote("³"), Matcher.quoteReplacement("h"));
        }


        return revert;
    }
}
