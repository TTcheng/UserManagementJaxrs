package com.wangchuncheng.dao;

import com.wangchuncheng.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用文件IO代替数据库操作
 */
public class UserDao {
    /**
     * get all users
     * @return user list
     */
    public List<User> getAllUsers() {
        List<User> userList = null;
        try {
            File file = new File("Users.dat");
            if (!file.exists()) {
                User user = new User(1, "Mahesh", "Teacher");
                userList = new ArrayList<User>();
                userList.add(user);
                saveUserList(userList);
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * get user by id
     * @param id
     * @return identified user
     */
    public User getUser(int id) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * add user to list
     * @param inputUser
     * @return 0 for false,1 for true
     */
    public int addUser(User inputUser) {
        List<User> users = getAllUsers();
        boolean userExists = false;
        for (User user : users) {
            if (user.getId() == inputUser.getId()) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            users.add(inputUser);
            saveUserList(users);
            return 1;
        }
        return 0;
    }

    /**
     *  update user info
     * @param inputUser
     * @return 0 for false,1 for true
     */
    public int updateUser(User inputUser) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId()==inputUser.getId()){
                int index = users.indexOf(user);
                users.set(index,inputUser);
                saveUserList(users);
                return 1;
            }
        }
        return 0;
    }

    /**
     * delete identified user
     * @param id
     * @return 0 for false,1 for true
     */
    public int deleteUser(int id){
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId()==id){
                users.remove(user.getId());
                saveUserList(users);
                return 1;
            }
        }
        return 0;
    }
    /**
     * save user list to file
     *
     * @param userList
     */
    private void saveUserList(List<User> userList) {
        try {
            File file = new File("Users.dat");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
