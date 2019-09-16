package cn.damon.mybatis.session;

import cn.damon.mybatis.config.Configuration;
import cn.damon.mybatis.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName SqlSessionFactory
 * @Description
 * @Author Damon
 * @Date 2019/9/15 23:55
 * @Email zdmsjyx@163.com
 * @Version 1.0
 */
public class SqlSessionFactory {
    private final Configuration conf = new Configuration();


    public SqlSessionFactory() {
        loadDbInfo();
        loadMappersInfo();
    }


    //记录mapper xml文件存放的位置
    private static final String MAPPER_CONFIG_LOCATION = "mappers";
    //记录数据库连接信息文件存放位置
    private static final String DB_CONFIG_FILE = "db.properties";


    //加载数据库配置信息
    private void loadDbInfo() {
        //加载数据库信息配置文件
        InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();
        try {
            //将配置信息写入Properties对象
            p.load(dbIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将数据库配置信息写入conf对象
        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
    }

    //加载指定文件夹下的所有mapper.xml
    private void loadMappersInfo() {
        URL resources =null;
        resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        if(resources == null){
            System.out.println("resources is null");
            return;
        }
        //获取指定文件夹信息
        File mappers = new File(resources.getFile());
        if(mappers.isDirectory()){
            File[] listFiles = mappers.listFiles();
            //遍历文件夹下所有的mapper.xml，解析信息后，注册至conf对象中
            if(listFiles != null &&  listFiles.length != 0){
                for (File file : listFiles) {
                    loadMapperInfo(file);
                }
            }
        }
    }



    //加载指定的mapper.xml文件
    @SuppressWarnings("unchecked")
    private void loadMapperInfo(File file) {
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document=null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点元素对象  <mapper>
        if(document == null){
            System.out.println("获取不到document");
            return;
        }
        Element root = document.getRootElement();
        //获取命名空间
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点列表
        List<Element> selects = root.elements("select");
        //遍历select节点，将信息记录到MappedStatement对象，并登记到configuration对象中
        for (Element element : selects) {
            //实例化mappedStatement
            MappedStatement mappedStatement = new MappedStatement();
            //读取id属性
            String id = element.attribute("id").getData().toString();
            //读取resultType属性
            String resultType = element.attribute("resultType").getData().toString();
            //读取SQL语句信息
            String sql = element.getData().toString();
            String sourceId = namespace+"."+id;
            //给mappedStatement属性赋值
            mappedStatement.setSourceId(sourceId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setNameSpace(namespace);
            //注册到configuration对象中
            conf.getMapperStatements().put(sourceId, mappedStatement);
        }
    }

    public SqlSession openSession(){
        return new DefaultSqlSession(conf);
    }

}
