# Singleton

В проекте паттерн Singleton используется для создания подключения к базе данных.

Класс, реализующий паттерн:  
```java
public class DatabaseConnect {
    private static DatabaseConnect mInstance;
    private static Context mContext;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    private DatabaseConnect() {
        helper = new DatabaseHelper(mContext);
        try{
            helper.createDataBase();
        } catch (IOException ioex){
            throw new Error("Can't initialize DB");
        }
        try{
            helper.openDataBase();
        } catch (SQLException sqlex){
            throw sqlex;
        }
        db = helper.getWritableDatabase();
    }

    public static void initInstance() {
        if (mInstance == null) {
            mInstance = new DatabaseConnect();
        }
    }

    public static DatabaseConnect getInstance() {
        return mInstance;
    }

    public static void setContext(Context context){
        mContext = context;
    }

    public SQLiteDatabase getDb(){
        return db;
    }

}
```
Инициализация происходит при запуске приложения:

```java 
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        DatabaseConnect.setContext(getApplicationContext());
        DatabaseConnect.initInstance();
    }
}
```
Последующие обращения к БД выполняюся через объект класса DatabaseConnect за счет вызова метода getInstance():
```java 
 DatabaseConnect dbc = DatabaseConnect.getInstance();
 ```
Диаграмма классов:
![](../../Patterns/SingletonClassDiagram.png)
