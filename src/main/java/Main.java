import picocli.CommandLine;

import java.io.*;
import java.util.*;

@CommandLine.Command(name="family-tree", mixinStandardHelpOptions = true)
public class Main implements Runnable{

    static Human human = new Human();
    static HashMap<String ,Human> map;
    static HashMap<String,String> relations;
    static ArrayList<String> list ;

    @CommandLine.Parameters(index = "0")
    private String command;

    @CommandLine.Parameters(index = "1",arity = "0..1")
    private String type;

    @CommandLine.Parameters(index = "2",arity = "0..1")
    private String name;

    @CommandLine.Parameters(index = "3",arity = "0..1")
    private String as;

    @CommandLine.Parameters(index = "4",arity = "0..1")
    private String relationship;

    @CommandLine.Parameters(index = "5",arity = "0..1")
    private String of;

    @CommandLine.Parameters(index = "6",arity = "0..1")
    private String name2;


    public static void main(String[] args) throws IOException {

        map = new HashMap<>();
        relations = new HashMap<>();
        new CommandLine(new Main()).execute(args);

    }

    @Override
    public void run() {

        list = new ArrayList<>(List.of("father","mother","son","daughter","wife","husband"));

        try {

            FileInputStream fileInput = new FileInputStream(
                    "hashmap.txt");
            ObjectInputStream objectInput
                    = new ObjectInputStream(fileInput);
            map = (HashMap)objectInput.readObject();

            FileInputStream fileInputR = new FileInputStream(
                    "Relation.txt");
            ObjectInputStream objectInputR
                    = new ObjectInputStream(fileInputR);
            relations = (HashMap)objectInputR.readObject();

            objectInput.close();
            fileInput.close();

        } catch (Exception e) {
            map = new HashMap<>();
        }


        switch (command) {

            case "add" -> {
                if(Objects.equals(type, "Person")){
                    if(name!=null){
                        map.put(name,human.addPerson(name));
                        System.out.println(map);
                    }
                }else if(Objects.equals(type, "relationship")){
                    if(list.contains(name)){
                        relations.put(name,name);
                    }else {
                        System.out.println("This relation isn't supported or you have to add this relationship");
                        break;
                    }
                }else{
                    System.out.println("Invalid Command " + type + "And" + command);
                }
            }

            case "connect" -> {
                if(as != null && of !=null) {
                    if(relations.containsKey(as)){
                        map = human.connect(as,type,of,map);
                    }else {
                        System.out.println("This relation isn't supported");
                    }
                } else {
                    System.out.println("Both Names are not present");
                    System.exit(0);
                }}

            case "count" -> {
                switch (type) {
                    case "sons" ->{
                        System.out.println(human.countSons(as, map));
                        break;
                    }

                    case "daughters" ->{
                        System.out.println(human.countDaughters(as, map));
                        break;
                    }

                    case "wives" -> {
                        System.out.println(human.countWives(as, map));
                        break;
                    }

                }
                System.exit(0);
            }

            case "father" -> {
                if (map.get(name).father != null) {
                    System.out.println(map.get(name).father.name);
                } else {
                    System.out.println("Father Doesn't Exist");
                }
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("hashmap.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);

            FileOutputStream fileOutputStreamR = new FileOutputStream("Relation.txt");
            ObjectOutputStream objectOutputStreamR = new ObjectOutputStream(fileOutputStreamR);
            objectOutputStreamR.writeObject(relations);

            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

}

