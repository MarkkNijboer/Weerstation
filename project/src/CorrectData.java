import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kevin on 25-9-2015.
 */
public class CorrectData {
    private HashMap<String, String> map;

    public CorrectData(HashMap<String, String> map) {
        this.map = map;
    }

    public void saveData() {
        Set set = map.keySet();
        Iterator iter = set.iterator();

        while(iter.hasNext()) {
            switch(iter.next().toString()) {
                case "":
            }
        }
    }
}
