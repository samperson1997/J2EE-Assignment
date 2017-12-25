package dao;

import java.util.List;

public interface OrderDao {

    public List find(String column, String value);

    public List find(String name);

    public List find();
}
