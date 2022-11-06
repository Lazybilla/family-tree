# family-tree

So Basically This is an Command Line Tool Jar for Managing Family Tree
(Wanted to Build a native interface though)

System Requirements:
JDK-17,JRE17

So yeah I've attached an Jar in this Repository

It supports Commands such as:

1) add Person [name] : where name is a single word 
2) add relationship [relationshipname] :  where relationshipName is a single word
3) connect [name] as [relationshipname] of [name2]

And Query Commands Such as 

4) count sons of [name]
5) count wives of [name]
6) count daughters of [name]
7) father of [name]

So for example you've to add a person named zeeshan your command would be something like
 
1)java -jar family-tree-cli.jar add Person zeeshan


2)java -jar family-tree-cli.jar add Person Iftekhar


Similarly for connecting

java -jar family-tree-cli.jar connect ifte as father of zeeshan

for Queries

java -jar family-tree-cli.jar count sons of Iftekhar
java -jar family-tree-cli.jar count daughters of Iftekhar
java -jar family-tree-cli.jar father of zeeshan


How to use being said I'll Explain the Thought Process behind my design :-

Every Gender is derived through Relations in this program

So Lets say Iftekhar is father of zeeshan 

What this specify is that the father is Male , So we dont know what the Child Gender is unless We Connect that Child as son of his .
Similarly When we add someone as someone's mother , The Child has a mother which is female but the child gender is unspecified unless we connect that child as son or daughter of that Person


That Being Clear Few Cases That I've considered :-

Lets say PersonA has 2 kids and PersonB has 2 kids 
So if they marry each other Both of them have 4 kids

If you add personA as father of personB
Then count sons or daughter of personA you would get 0 because the gender of child isn't explicit

Apart from this Everything is pretty straight forward

Male can have multiple wives and vice versa (This wasn't clear for me However)


==========================================================================================================================================

Regarding Using only One Object Human

which have following properties:-

    String name ;
    Human father;
    Human mother;
    List<Human> SO;
    List<Human> kids ;
    String gender ;
    
We can Recursively add relations to this because each Human has the above properties 

Here is an image of the operations:-
![alt text](https://github.com/Lazybilla/family-tree/blob/main/Screenshot from 2022-11-06 20-47-33.png?raw=true)
    

