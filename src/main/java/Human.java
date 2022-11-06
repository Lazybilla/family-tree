
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "name")
public class Human implements Serializable {

    String name ;
    Human father;
    Human mother;
    List<Human> SO;
    List<Human> kids ;
    String gender ;

    Human() {
    }

    Human(String name , Human father,Human mother, Human SO, List<Human> kids, String gender){

        this.name = name;
        this.father = father;
        this.mother = mother;
        this.gender = gender;

        if(SO==null){
            this.SO = new ArrayList<>();
        }

        if(kids==null){
            this.kids = new ArrayList<>();
        }

    }

    public Human addPerson(String name){
        return new Human(name,null,null,null,null,null);
    }

    public HashMap<String,Human> connect(String relation,String human1, String human2, HashMap<String,Human> map){

        switch (relation) {

            case "father" :
                return father(human1,human2,map);

            case "son" :
                return son(human1,human2,map);

            case "daughter" :
                return daughter(human1,human2,map);

            case "mother":
                return  mother(human1,human2,map);

            case "wife":
                return wife(human1,human2,map);

            case "husband":
                return husband(human1,human2,map);

            default:
                System.out.println("Invalid Relation");
        }

        return null;
    }

    public HashMap<String,Human> father(String fathername, String sonName, HashMap<String,Human> map) {

        Human parent = map.get(fathername);
        Human kid = map.get(sonName);
        kid.father = parent;
        List<Human> list = parent.kids;

        if(!list.contains(kid)){
            list.add(kid);
        }

        parent.kids = list;
        parent.gender = "male";
        map.put(fathername,parent);
        map.put(sonName,kid);
        return map;

    }

    public HashMap<String,Human> mother(String mothername, String sonName, HashMap<String,Human> map) {

        Human parent = map.get(mothername);
        Human kid = map.get(sonName);
        kid.mother = parent;
        List<Human> list = parent.kids;

        if(!list.contains(kid)){
            list.add(kid);
        }

        parent.kids = list;
        parent.gender = "female";
        map.put(mothername,parent);
        map.put(sonName,kid);
        return map;

    }

    public HashMap<String,Human> son(String sonName, String parentName, HashMap<String,Human> map){

        Human son = map.get(sonName);
        Human parent = map.get(parentName);
        List<Human> list = parent.kids;

        if(!list.contains(son)){
            list.add(son);
        }

        List<Human> sos = parent.SO;

        for (Human h : sos) {
            h.kids.add(son);
        }

        parent.kids = list;
        son.gender = "male";

        map.put(sonName,son);
        map.put(parentName,parent);

        return map;

    }


    public HashMap<String,Human> daughter(String daughterName, String parentName, HashMap<String,Human> map){

        Human daughter = map.get(daughterName);
        Human parent = map.get(parentName);
        List<Human> list = parent.kids;

        if(!list.contains(daughter)){
            list.add(daughter);
        }

        List<Human> sos = parent.SO;

        for (Human h : sos) {
            h.kids.add(daughter);
        }

        parent.kids = list;
        daughter.gender = "female";

        map.put(daughterName,daughter);
        map.put(parentName,parent);

        return map;
    }

    public HashMap<String,Human> wife(String wifeName, String husbandName, HashMap<String,Human> map) {

        Human wife = map.get(wifeName);

        Human husband = map.get(husbandName);
        wife.gender = "female";


        List<Human> bothkids = Stream.concat(wife.kids.stream(),husband.kids.stream()).distinct().toList() ;

        wife.kids = bothkids;
        husband.kids = bothkids;

        List<Human> wso = wife.SO;

        if(!wso.contains(husband)){
            wso.add(husband);
        }

        wife.SO = wso;
        List<Human> hso = husband.SO;

        if(!hso.contains(wife)){
            hso.add(wife);
        }

        husband.SO = hso;



        map.put(wifeName,wife);
        map.put(husbandName,husband);

        return map;
    }

    public HashMap<String,Human> husband(String husbandName, String wifeName, HashMap<String,Human> map) {

        Human wife = map.get(wifeName);

        Human husband = map.get(husbandName);

        List<Human> bothkids = Stream.concat(wife.kids.stream(),husband.kids.stream()).distinct().toList() ;

        wife.kids = bothkids;
        husband.kids = bothkids;

        List<Human> wso = wife.SO;

        if(!wso.contains(husband)){
            wso.add(husband);
        }

        wife.SO = wso;
        List<Human> hso = husband.SO;

        if(!hso.contains(wife)){
            hso.add(wife);
        }

        husband.SO = hso;

        husband.gender = "male";

        map.put(wifeName,wife);
        map.put(husbandName,husband);

        return map;
    }

    public long countDaughters(String name, HashMap<String,Human> map){
        long kids = map.get(name).kids.stream().filter(Human -> Objects.equals(Human.gender, "female")).count();
        return kids;
    }

    public long countSons(String name , HashMap<String,Human> map){
        long kids = map.get(name).kids.stream().filter(Human -> Objects.equals(Human.gender, "male")).count();
        return kids;
    }

    public long countWives(String name , HashMap<String,Human> map){
        long wives = map.get(name).SO.stream().filter(Human -> Objects.equals(Human.gender, "female")).count();
        return wives;
    }

}