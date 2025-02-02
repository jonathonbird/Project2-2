package Actions;

import Articles.*;
import Attributes.Attribute;
import Utils.Data;

import java.util.ArrayList;

public class Fetch extends Action {
    Article type;
    ArrayList<Attribute> limiters = new ArrayList<>();
    boolean noLimit=true;
    ArrayList<Integer> attributeIDs;
    boolean allAtt=true;
    public Fetch(){}
    public Fetch(Article a,ArrayList<Attribute> l, ArrayList<Integer> at){
        type=a;
        limiters=l;
        attributeIDs=at;
        noLimit=false;
        allAtt=false;
        if(l==null){
            noLimit=true;
        }
        if(at==null){
            allAtt=true;
        }
    }
    public String action(){
        ArrayList<Article> items = new ArrayList<>();
        if(type instanceof Lecture) {
            ArrayList<ArrayList<Boolean>> checklist = new ArrayList<>();
            for (Lecture l : Data.lectures) {
                if(noLimit){
                    items.add(l);
                }else {
                    ArrayList<Boolean> temp = new ArrayList<>();
                    for (Attribute limit : limiters) {
                        boolean t = false;
                        for (Attribute a : l.attributes) {
                            if(a.equalsTo(limit)){
                                t=true;
                            }
                        }
                        temp.add(t);
                    }
                    checklist.add(temp);
                }
            }
            for(int i=0;i<checklist.size();i++){
                boolean allTrue=true;
                for(boolean b:checklist.get(i)){
                    if (!b) {
                        allTrue = false;
                        break;
                    }
                }
                if(allTrue){
                    items.add(Data.lectures.get(i));
                }
            }
        }
        if(type instanceof Event){
            ArrayList<ArrayList<Boolean>> checklist = new ArrayList<>();
            for (Event l : Data.events) {
                if(noLimit){
                    items.add(l);
                }else {
                    ArrayList<Boolean> temp = new ArrayList<>();
                    for (Attribute limit : limiters) {
                        boolean t = false;
                        for (Attribute a : l.attributes) {
                            if(a.equalsTo(limit)){
                                t=true;
                            }
                        }
                        temp.add(t);
                    }
                    checklist.add(temp);
                }
            }
            for(int i=0;i<checklist.size();i++){
                boolean allTrue=true;
                for(boolean b:checklist.get(i)){
                    if (!b) {
                        allTrue = false;
                        break;
                    }
                }
                if(allTrue){
                    items.add(Data.events.get(i));
                }
            }
        }
        if(type instanceof Notification){
            ArrayList<ArrayList<Boolean>> checklist=new ArrayList<>();
            for(Notification n:Data.notifications){
                if(noLimit){
                    items.add(n);
                } else{
                    ArrayList<Boolean> temp=new ArrayList<>();
                    for(Attribute limit:limiters){
                        boolean t=false;
                        for(Attribute a:n.attributes){
                            if(a.equalsTo(limit)){
                                t=true;
                            }
                        }
                        temp.add(t);
                    }
                    checklist.add(temp);
                }
            }
            for(int i=0;i<checklist.size();i++){
                boolean allTrue=true;
                for(boolean b:checklist.get(i)){
                    if (!b) {
                        allTrue = false;
                        break;
                    }
                }
                if(allTrue){
                    items.add(Data.notifications.get(i));
                }
            }
        }
        if(type instanceof Medication) {
            ArrayList<ArrayList<Boolean>> checklist=new ArrayList<>();
            for(Medication n:Data.medications){
                if(noLimit){
                    items.add(n);
                } else{
                    ArrayList<Boolean> temp=new ArrayList<>();
                    for(Attribute limit:limiters) {
                        boolean t=false;
                        for(Attribute a:n.attributes){
                            if(a.equalsTo(limit)){
                                t=true;
                            }
                        }
                        temp.add(t);
                    }
                    checklist.add(temp);
                }
            }
            for(int i=0;i<checklist.size();i++){
                boolean allTrue=true;
                for(boolean b:checklist.get(i)){
                    if (!b) {
                        allTrue = false;
                        break;
                    }
                }
                if(allTrue){
                    items.add(Data.medications.get(i));
                }
            }
        }

        ArrayList<String> toReturn = new ArrayList<>();
        for(Article a:items){
            String toAdd = "";
            if(!allAtt) {
                for (Integer i : attributeIDs) {
                    if(i<a.attributes.size()) {
                        if(!a.attributes.get(i).toBeInputted) {
                            toAdd += a.attributes.get(i).toString() + " ";
                        }
                    }
                }
            }else{
                for(Attribute at:a.attributes){
                    if(!at.toBeInputted) {
                        toAdd += at.toString() + " ";
                    }
                }
            }
            toReturn.add(toAdd);
        }
        String realToReturn="";
        for(String s:toReturn){
            realToReturn+=s+'\n';
        }
        return realToReturn;
    }
    @Override
    public String toString(){
        return "Fetch";
    }
}
