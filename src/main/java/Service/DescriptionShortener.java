package Service;

public class DescriptionShortener {
    public String shorten(String desc){
        StringBuilder ret = new StringBuilder();
        ret.append(desc.substring(0, Math.min(desc.length(), 300)));
        if(desc.length() > 300)
            ret.append("...");
        return ret.toString();
    }
}
