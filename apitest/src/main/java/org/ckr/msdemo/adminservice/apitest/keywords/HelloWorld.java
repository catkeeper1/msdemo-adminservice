package org.ckr.msdemo.adminservice.apitest.keywords;

public class HelloWorld {
    String from, to;

    public HelloWorld(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public HelloWorld() {
    }

    public void say(){
        if(from == null || to == null){
            System.out.println("*ERROR*"+" Hello World");
            System.out.println("*INFO*"+" Hello World");
            System.out.println("*WARN*"+" Hello World");
        }else {
            System.out.println("*INFO* "+ from + " say Hello World to " + to);
            System.out.println("*ERROR* "+ from + " say Hello World to " + to);
            System.out.println("*WARN* "+ from + " say Hello World to " + to);
        }
    }


}
