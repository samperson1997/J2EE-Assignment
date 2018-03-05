package service;

import javax.ejb.Remote;

@Remote
public interface UserService {
    String getPassword(String id);
}
