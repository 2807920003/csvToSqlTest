package com.kk;

import com.csvreader.CsvReader;
import com.kk.entity.Diease;
import com.kk.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println( "Hello World!" );
        Class[] classes={User.class, Diease.class};
        String filepath="C:\\Users\\lenovo\\Desktop\\Test.csv";
        CsvReader csvReader=new CsvReader(filepath);
        csvReader.readHeaders();
        String[] headers=csvReader.getHeaders();
        ArrayList list=new ArrayList();
        for(int i=0;i<headers.length;i++){
            for(int j=0;j<classes.length;j++){
                Field[] fields=classes[j].getDeclaredFields();
                for(int k=0;k<fields.length;k++){
                    if(fields[k].getName().equals(headers[i])){
                        list.add(j);
                        k=fields.length;
                        j=classes.length;
                    }
                }
            }

        }

        while (csvReader.readRecord()){
            Object[] objects={new User(),new Diease()};
            for(int i=0;i<list.size();i++){
                Method method=classes[(int) list.get(i)].getDeclaredMethod("set"+caputerName(headers[i]),String.class);
                method.invoke(objects[(int)list.get(i)],csvReader.get(i));
            }
            SqlSessionFactory factory=makeSession();
            SqlSession session=factory.openSession();
            sqlMapper mapper=session.getMapper(sqlMapper.class);
           // System.out.println(classes[0].getMethod("getUsr",null).invoke(objects[0],null));
            mapper.addUser((User) objects[0]);
            mapper.addDiease((Diease) objects[1]);
            session.commit();

        }


        for(int i=0;i<list.size();i++){

        }


//        CsvReader csvReader= null;
//        try {
//           // InputStream in= new FileInputStream(filepath);
//           // csvReader = new CsvReader(in, Charset.forName("UTF-8"));
//            csvReader=new CsvReader(filepath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            csvReader.readHeaders();
//            System.out.println(csvReader.getHeader(0));
//            System.out.println(csvReader.getHeader(0).equals("name"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        while(true){
//            try {
//                if (!csvReader.readRecord()) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(csvReader.getRawRecord());
//            try {
//                System.out.println(csvReader.get("name"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }
    public static String caputerName(String name){
        name = name.substring(0, 1).toUpperCase() + name.substring(1);//UpperCase大写
        return  name;
    }

    public static SqlSessionFactory makeSession() throws IOException {
        String resource="mybatis-config.xml";
        InputStream in= Resources.getResourceAsStream(resource);
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory=builder.build(in);
        return factory;

    }
}
