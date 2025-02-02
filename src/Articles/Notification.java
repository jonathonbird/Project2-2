package Articles;

import Attributes.ADate;
import Attributes.Course;
import Attributes.ExtraText;
import Attributes.Time;

import java.util.ArrayList;

public class Notification extends Article {
    Course course;
    Time time;
    ADate date;
    ExtraText extraText;

    public Notification(){super(new ArrayList<>());}

    public Notification(Course c, Time deadline, ADate dueDate, ExtraText information){
        super(new ArrayList<>());
        attributes.add(c);
        attributes.add(deadline);
        attributes.add(dueDate);
        attributes.add(information);
        course=c;
        time=deadline;
        date=dueDate;
        extraText=information;
    }

    @Override
    public String toString(){
        if(course==null) {
            return "Notification";
        }
        return "";
    }
}