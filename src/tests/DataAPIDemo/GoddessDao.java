package tests.DataAPIDemo;

/**
 * Created by 305027244 on 2018/10/10.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoddessDao {

    //添加女神
    public void addGoddess(Goddess g) throws Exception{
        Connection con=DBUtil.getConnection();//首先拿到数据库的连接
        String sql="" +
                "insert into imooc_goddess"+
                "(user_name,sex,age,birthday,email,mobile,"+
                "create_user,create_date,update_user,update_date,isdel) "+
                "values("+
                "?,?,?,?,?,?,?,current_date(),?,current_date(),?)";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, g.getUserName());
        psmt.setInt(2, g.getSex());
        psmt.setInt(3, g.getAge());
        //注意：setDate()函数第二个参数需要的是java.sql.Date类型，我们传进来的是java.util.Date，类型不符，需要做一下转换
        psmt.setDate(4, new Date(g.getBirthday().getTime()));
        psmt.setString(5, g.getEmail());
        psmt.setString(6, g.getMobile());
        psmt.setString(7, g.getCreateUser());
        psmt.setString(8, g.getUpdateUser());
        psmt.setInt(9, g.getIsDel());
        //执行SQL语句
        psmt.execute();
        /**
         * prepareStatement这个方法会将SQL语句加载到驱动程序conn集成程序中，但是并不直接执行
         * 而是当它调用execute()方法的时候才真正执行；
         *
         * 上面SQL中的参数用?表示，相当于占位符，然后在对参数进行赋值。
         * 当真正执行时，这些参数会加载在SQL语句中，把SQL语句拼接完整才去执行。
         * 这样就会减少对数据库的操作
         */
    }

    //更新女神
    public void updateGoddess(Goddess g) throws SQLException{
        Connection con=DBUtil.getConnection();//首先拿到数据库的连接
        String sql="" +
                "update imooc_goddess "+
                "set user_name=?,sex=?,age=?,birthday=?,email=?,mobile=?,"+
                "update_user=?,update_date=current_date(),isdel=?) "+
                "where id=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, g.getUserName());
        psmt.setInt(2, g.getSex());
        psmt.setInt(3, g.getAge());
        //注意：setDate()函数第二个参数需要的是java.sql.Date类型，我们传进来的是java.util.Date，类型不符，需要做一下转换
        psmt.setDate(4, new Date(g.getBirthday().getTime()));
        psmt.setString(5, g.getEmail());
        psmt.setString(6, g.getMobile());
        psmt.setString(7, g.getUpdateUser());
        psmt.setInt(8, g.getIsDel());
        psmt.setInt(9, g.getId());
        //执行SQL语句
        psmt.execute();
    }

    //删除女神
    public void delGoddess(Integer id) throws SQLException{
        Connection con=DBUtil.getConnection();//首先拿到数据库的连接
        String sql="" +
                "delete from imooc_goddess "+
                "where id=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, id);
        //执行SQL语句
        psmt.execute();
    }

    //查询单个女神(根据id去查询)
    public Goddess get(Integer id) throws SQLException{
        Goddess g=null;
        Connection con=DBUtil.getConnection();//首先拿到数据库的连接
        String sql="" +
                "select * from imooc_goddess "+
                "where id=?";//参数用?表示，相当于占位符;用mysql的日期函数current_date()来获取当前日期
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sql);
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, id);
        //执行SQL语句
        /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
        ResultSet rs = psmt.executeQuery();//返回一个结果集
        //遍历结果集
        while(rs.next()){
            g=new Goddess();
            g.setId(rs.getInt("id"));
            g.setUserName(rs.getString("user_name"));
            g.setAge(rs.getInt("age"));
            g.setSex(rs.getInt("sex"));
            //rs.getDate("birthday")获得的是java.sql.Date类型。注意：java.sql.Date类型是java.util.Date类型的子集，所以这里不需要进行转换了。
            g.setBirthday(rs.getDate("birthday"));
            g.setEmail(rs.getString("email"));
            g.setMobile(rs.getString("mobile"));
            g.setCreateUser(rs.getString("create_user"));
            g.setCreateDate(rs.getDate("create_date"));
            g.setUpdateUser(rs.getString("update_user"));
            g.setUpdateDate(rs.getDate("update_date"));
            g.setIsDel(rs.getInt("isdel"));
        }
        return g;
    }

    public void delGoddess() {

    }

    public List<Goddess> query() throws Exception {
        Connection con = DBUtil.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select user_name,age from imooc_goddess");
        List<Goddess> gs = new ArrayList<Goddess>();
        Goddess g = null;
        while (rs.next()) {//如果对象中有数据，就会循环打印出来
            g = new Goddess();
            g.setUserName(rs.getString("user_name"));
            g.setAge(rs.getInt("age"));
            gs.add(g);
        }
        return gs;
    }

    //查询单个女神(根据姓名等信息去查询)----->使用Map存储条件信息；防止条件为空可加一条where 1=1
    public List<Goddess> get(List<Map<String, Object>> params) throws SQLException{
        List<Goddess> result=new ArrayList<Goddess>();
        Connection con=DBUtil.getConnection();//首先拿到数据库的连接
        StringBuffer sb=new StringBuffer();
        sb.append("select * from imooc_goddess where 1=1 ");//注意where 1=1 的小技巧
        if(params !=null && params.size()>0){//先判断集合是否为空
            //遍历集合
            for(int i=0;i<params.size();i++){
                Map<String, Object> map=params.get(i);
                sb.append("and "+map.get("name")+" " +map.get("rela")+" "+map.get("value")+" ");//查询什么？关系是什么？以及值是什么，我们都可以通过参数传进来。
            }

        }
        //预编译sql语句
        PreparedStatement psmt = con.prepareStatement(sb.toString());
        System.out.println(sb.toString());
        //执行SQL语句
                    /*psmt.execute();*///execute()方法是执行更改数据库操作（包括新增、修改、删除）;executeQuery()是执行查询操作
        ResultSet rs = psmt.executeQuery();//返回一个结果集
        Goddess g=null;
        //遍历结果集
        while(rs.next()){
            g=new Goddess();
            g.setId(rs.getInt("id"));
            g.setUserName(rs.getString("user_name"));
            g.setAge(rs.getInt("age"));
            g.setSex(rs.getInt("sex"));
            //rs.getDate("birthday")获得的是java.sql.Date类型。注意：java.sql.Date类型是java.util.Date类型的子集，所以这里不需要进行转换了。
            g.setBirthday(rs.getDate("birthday"));
            g.setEmail(rs.getString("email"));
            g.setMobile(rs.getString("mobile"));
            g.setCreateUser(rs.getString("create_user"));
            g.setCreateDate(rs.getDate("create_date"));
            g.setUpdateUser(rs.getString("update_user"));
            g.setUpdateDate(rs.getDate("update_date"));
            g.setIsDel(rs.getInt("isdel"));

            result.add(g);//将结果封装到对象中
        }
        return result;
    }
}
