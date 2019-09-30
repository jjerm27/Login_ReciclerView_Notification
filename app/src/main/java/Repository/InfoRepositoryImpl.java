package Repository;

import com.example.login.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoRepositoryImpl implements InfoRepository<Model> {
    private static InfoRepositoryImpl instance;
    private List<Model> list;

    public static InfoRepositoryImpl getInstance(){
        if(instance==null)
            instance = new InfoRepositoryImpl();
        return instance;
    }

    private InfoRepositoryImpl() {
        this.list = new ArrayList<>();
    }

    public List<Model> getDefaultItems(){
        for (int i = 1; i <= 25; i++) {
            list.add(new Model("Item " + i));
        }
        return list;
    }

    @Override
    public List<Model> getAll() {
        return list;
    }

    @Override
    public Model getOne(int id) {
        return list.get(id);
    }
}
