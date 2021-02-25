package Inputs;

import Articles.Article;
import Articles.Lecture;
import Attributes.*;
import Attributes.Number;
import Skills.Skill;

import java.util.ArrayList;

public class SEArticles {
    Article article;
    public ArrayList<String> inputs = new ArrayList<>();
    public ArrayList<ArrayList<String>> eachInputsLimiters = new ArrayList<>();

    public SEArticles(Article a){
        if(a instanceof Lecture){
            inputs.add(new ADate().toString());
            inputs.add(new Course().toString());
            inputs.add(new Time().toString());
            inputs.add(new ExtraText().toString());
            inputs.add(new Day().toString());
            inputs.add(new Number().toString());
            // Non-input needed limiters
            ArrayList<String> to = new ArrayList<>();
            to.add("Date<TODAY>");
            to.add("Date<YESTERDAY>");
            to.add("Date<TOMORROW>");
            eachInputsLimiters.add(to);
            // <DATE> limiters
            ArrayList<String> date = new ArrayList<>();
            date.add("<DATE>");
            eachInputsLimiters.add(date);
            // <COURSE> limiters
            ArrayList<String> course = new ArrayList<>();
            course.add("<COURSE>");
            eachInputsLimiters.add(course);
            // <TIME> limiters
            ArrayList<String> time = new ArrayList<>();
            eachInputsLimiters.add(time);
            // <EXTRA> limiters
            ArrayList<String> extra = new ArrayList<>();
            eachInputsLimiters.add(extra);
            // <DAY> limiters
            ArrayList<String> day = new ArrayList<>();
            day.add("NEXT<DAY>");
            eachInputsLimiters.add(day);
            // <NUM> limiters
            ArrayList<String> num = new ArrayList<>();
            num.add("<DATE><TODAY+<NUM>>");
            eachInputsLimiters.add(num);
        }
    }

    @Override
    public String toString() {
        return "Lecture";
    }
}
