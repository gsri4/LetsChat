package com.innominds.letschat.models;



import com.innominds.letschat.helper.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

@Table(name = "User", database = AppDatabase.class)
public class User  extends Model {

    @PrimaryKey
    private Long id;

    @Column(name = "jit_id")
    public String user_JitId;

    @Column(name = "username")
    public String userName;

    @Column(name = "status")
    public boolean userPresence;

    @Column(name = "timestamp")
    public long timeStamp;


    public User() {
        super();
    }

    public User(String user_JitId, String userName, boolean userPresence, long timeStamp) {
        this.user_JitId = user_JitId;
        this.userName = userName;
        this.userPresence = userPresence;
        this.timeStamp = timeStamp;
    }

    public String getUser_JitId() {
        return user_JitId;
    }

    public void setUser_JitId(String user_JitId) {
        this.user_JitId = user_JitId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getUserPresence() {
        return userPresence;
    }

    public void setUserPresence(boolean userPresence) {
        this.userPresence = userPresence;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Used to all users list in UsersTable
     * @return list of users
     */
    public static ArrayList<User> getAllUsers() {
        List<User> usersList = Select.from(User.class)
                .fetch();


        ArrayList<User> listOfUsers = new ArrayList<User>(usersList.size());
        listOfUsers.addAll(usersList);

        return  listOfUsers;

    }
}
