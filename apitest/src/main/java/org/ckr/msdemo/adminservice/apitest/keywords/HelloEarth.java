package org.ckr.msdemo.adminservice.apitest.keywords;

public class HelloEarth extends HelloWorld {

    public void speak(){
        if(from == null || to == null){

            System.out.println("*ERROR*"+" Hello Earth");
            System.out.println("*INFO*"+" Hello Earth");
            System.out.println("*WARN*"+" Hello Earth");
        }else {
            System.out.println("*INFO* "+ from + " say Hello Earth to " + to);
            System.out.println("*ERROR* "+ from + " say Hello Earth to " + to);
            System.out.println("*WARN* "+ from + " say Hello Earth to " + to);
        }
    }

    public String whaleSay(){
        return "I'm a whale.";
    }



    public void sing(){
        System.out.println("*WARN* "+ "ABCDEF");
    }
}
