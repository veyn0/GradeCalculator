package we.jufrkhma.data.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepository {

    private Map<UUID, UserInfo> userData = new HashMap<>();

    public UserInfo getUserInfo(UUID userId){
        return  userData.get(userId);
    }

    public UserInfo getUserInfo(String userNameOrUUID){
        if(userNameOrUUID.length()>16) return userData.get(UUID.fromString(userNameOrUUID));

        for(UserInfo info : userData.values()){
            if(info.name().equalsIgnoreCase(userNameOrUUID)){
                return info;
            }
        }
        return null;
    }


    public void addUser(UserInfo userInfo){
        userData.put(userInfo.userId(), userInfo);
    }

    public Map<UUID, UserInfo> getUserData() {
        return userData;
    }

    public void remove(UUID userId){
        userData.remove(userId);
    }
}
