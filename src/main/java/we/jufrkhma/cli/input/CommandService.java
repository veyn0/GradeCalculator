package we.jufrkhma.cli.input;

import java.util.HashMap;
import java.util.Map;

public class CommandService {

    private Map<String, CommandExecutor> registeredCommands = new HashMap<>();

    public void registerCommand(String name, CommandExecutor executor){
        if(registeredCommands.containsKey(name)){
            System.out.println("Error while registering Command \""+ name +"\": command already exists");
            return;
        }
        registeredCommands.put(name, executor);
    }

    public void onCommand(String[] s){
        String commandName = s[0];
        if(registeredCommands.containsKey(commandName)){
            String[] args = new String[s.length-1];
            for(int i = 1; i < s.length; i++){
                args[i-1] = s[i];
            }
            registeredCommands.get(commandName).onCommand(args);
        }
        else {
            System.out.println("Command \"" + s[0] +"\" not found.");
        }
    }

    public CommandExecutor getCommand(String name){
        return registeredCommands.get(name);
    }



}
