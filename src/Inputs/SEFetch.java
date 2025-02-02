package Inputs;

import Actions.Fetch;
import Articles.Event;
import Articles.Lecture;
import Articles.Medication;
import Articles.Notification;

import java.util.ArrayList;

public class SEFetch extends SE{
    public ArrayList<SEFetchArticles> articles = new ArrayList<>();

    public SEFetch() {
        super(new Fetch());
        articles.add(new SEFetchArticles(new Lecture()));
        articles.add(new SEFetchArticles(new Event()));
        articles.add(new SEFetchArticles(new Notification()));
        articles.add(new SEFetchArticles(new Medication()));
    }


}
