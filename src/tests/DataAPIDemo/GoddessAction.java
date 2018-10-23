package tests.DataAPIDemo;

/**
 * Created by 305027244 on 2018/10/10.
 */

import java.util.List;
import java.util.Map;

public class GoddessAction {

    //添加女神
    public void add(Goddess goddess) throws Exception {
        GoddessDao dao = new GoddessDao();
        goddess.setSex(0);
        goddess.setIsDel(0);
        dao.addGoddess(goddess);
    }

    //修改女神
    public void edit(Goddess goddess) throws Exception {
        GoddessDao dao = new GoddessDao();
        dao.updateGoddess(goddess);
    }

    //删除女神
    public void del(Integer id) throws Exception {
        GoddessDao dao = new GoddessDao();
        dao.delGoddess(id);
    }

    //查询所有女神信息
    public List<Goddess> query() throws Exception {
        GoddessDao dao = new GoddessDao();
        return dao.query();
    }

    //查询单个女神信息(根据id)
    public Goddess get(Integer id) throws Exception {
        GoddessDao dao = new GoddessDao();
        return dao.get(id);
    }

    //查询单个女神信息（根据姓名等信息）
    public List<Goddess> get(List<Map<String, Object>> params) throws Exception {
        GoddessDao dao = new GoddessDao();
        return dao.get(params);
    }
}
