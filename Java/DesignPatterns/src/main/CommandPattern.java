package main;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author Qiang
 * @since 30/03/2017
 */
public class CommandPattern {

    public static void main(String[] a) {

        System.out.println("Please input command, 1 -- to cut, 2 -- to copy, 3 -- undo, 4 -- redo, 5 -- quit");
        Invoker invoker = new Invoker();
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        while (input != 5) {
            switch (input) {
                case 1:
                    invoker.cut();break;
                case 2:
                    invoker.copy();break;
                case 3:
                    invoker.undo();break;
                case 4:
                    invoker.redo();break;
                default:
                    //do nothing
            }


            input = scanner.nextInt();
        }


    }
}

class Invoker {
    private Stack<Command> redoCommandStack;
    private Stack<Command> doneCommandStack;
    private Doc doc;
    public Invoker() {
        redoCommandStack = new Stack<>();
        doneCommandStack = new Stack<>();
        doc = new Doc();
    }

    public void cut() {
        Command command = new CutCommand(doc);
        command.execute();
        doneCommandStack.push(command);
    }

    public void copy() {
        Command command = new CopyCommand(doc);
        command.execute();
        doneCommandStack.push(command);
    }


    public void undo() {
        if (!doneCommandStack.empty()) {
            Command command = doneCommandStack.pop();
            command.undo();
            redoCommandStack.push(command);
        } else {
            System.out.println("Sorry, nothing to undo");
        }
    }

    public void redo() {
        if (!redoCommandStack.empty()) {
            Command command = redoCommandStack.pop();
            command.execute();
            doneCommandStack.push(command);
        } else {
            System.out.println("Sorry, nothing to redo");
        }
    }
}



class Doc {
    private int offset;
    private int length;
    private String textOut;


    public void cut(){
        // do something
        System.out.println("Cutting ... ");

    }
    public void copy(){
        // do something
        System.out.println("Copying ... ");
    }
    public void paste(){}


}

interface Command {
    void execute();

    void undo();
}

class CutCommand implements Command {
    private Doc receiver;

    public CutCommand(Doc receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        // 记录 剪切了什么数据，或者暴力直接clone一个doc
        receiver.cut();
    }

    @Override
    public void undo() {

        // undo something
        System.out.println("Undo Cutting ...");
    }
}

class CopyCommand implements Command {

    private Doc receiver;

    public CopyCommand(Doc receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.copy();
    }

    @Override
    public void undo() {
        // undo somethingg
        System.out.println("Undo Copying ...");
    }
}


