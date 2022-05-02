import com.tutoring.tutoring.domain.team.TeamType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> tags = new ArrayList<>();
        tags.add("hello");
        tags.add("new");
        tags.add("world");
        System.out.println(tags);
        String t = "";
        for (String tag : tags) {
            t += tag + ',';
        }
        System.out.println(t);
        String[] split = t.split(",");
        List<String> splits = Arrays.asList(split);
        System.out.println(splits);
    }
}
