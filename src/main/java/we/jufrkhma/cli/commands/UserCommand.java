package we.jufrkhma.cli.commands;

import we.jufrkhma.GradeCalculator;
import we.jufrkhma.cli.input.CommandExecutor;
import we.jufrkhma.data.user.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserCommand implements CommandExecutor {

    private final GradeCalculator gradeCalculator;

    public UserCommand(GradeCalculator gradeCalculator) {
        this.gradeCalculator = gradeCalculator;
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length==0){
            printInvalidArgs();
            return;
        }
        String action = args[0];
        if(action.equalsIgnoreCase("info")){
            if(args.length==1){
                printInvalidArgs();
                return;
            }
            String user = args[1];
            printUserInfo(user);
        }
        else if(action.equalsIgnoreCase("create")){
            if(args.length==1){
                printInvalidArgs();
                return;
            }
            if(args[1].length()>16){
                System.out.println("Failed to create User. Username may only be 16 characters in length");
                return;
            }
            String name = args[1];
            UserInfo created = new UserInfo(UUID.randomUUID(), name);
            gradeCalculator.getUserRepository().addUser(created);
            System.out.println("User \"" + name + "\" created successfully.");
            printUserInfo(created.userId().toString());
        }

        else if(action.equalsIgnoreCase("list")){
            Collection<UserInfo> userInfos = gradeCalculator.getUserRepository().getUserData().values();
            if(userInfos.isEmpty()){
                System.out.println("There are no users created yet");
                return;
            }
            System.out.println();
            for(UserInfo userInfo : userInfos){
                printUserInfo(userInfo.userId().toString());
                System.out.println();
            }
        }
    }

    private void printUserInfo(String user){

        UserInfo currentUser = gradeCalculator.getUserRepository().getUserInfo(user);
        if(currentUser==null){
            System.out.println("User \"" + user + "\" not found.");
            return;
        }
        System.out.println("User info for \"" + currentUser.name() + "\": \nUUID: " + currentUser.userId());
    }

    private void printInvalidArgs(){
        System.out.println("invalid arguments. use \"help user\" to see a full list of available commands");
    }

    @Override
    public void printHelpPage() {

        System.out.println("""
                user command info:
                - create <name:String> : creates a user with the given Name
                - list : lists all users currently saved
                - info <userName:String> : prints the saved information of the specified user
                """);

    }
}
