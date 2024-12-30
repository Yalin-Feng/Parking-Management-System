package sg.ntu.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public abstract class Car {
    private String type;  //汽车类型
    private String entryTime; //入场时间
    private String departTime; //离场时间
    private String plate; //车牌
    private String description;  //评价反馈
    private static final int SEDAN = 1;
    private static final int BUS = 2;

    public Car() {
    }

    public Car(String type, String entryTime, String departTime, String plate, String description) {
        this.type = type;
        this.entryTime = entryTime;
        this.departTime = departTime;
        this.plate = plate;
        this.description = description;
    }

    public Car(String type, String entryTime, String plate) {
        this.type = type;
        this.entryTime = entryTime;
        this.plate = plate;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract int CalculateParkingFee(Car car);

    public boolean getCarType(Car car){//获取车辆的种类  1.轿车  2.客车
        Scanner sc = new Scanner(System.in); //接收输入
        System.out.println("1.轿车 2.客车 ");//输出车的两种类型
        int input = sc.nextInt();//接收输入
        if (input == SEDAN) { //输入是1
            car.setType("轿车");  // 直接设置传入对象的类型
            return true;//返回true
        } else if (input == BUS) {//输入是2
            car.setType("客车");  // 直接设置传入对象的类型
            return true;//返回true
        } else {
            System.out.println("输入错误！");//输出错误
            return false;
        }
    }

    public boolean DeterminePlate(Car car, String plate) {//判断车牌号的格式是否正确 优化之后用正则比较好，且易读
        String platePattern = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵青藏川宁琼港澳台]{1}[A-Z]{1}[A-Z0-9]{5,6}$";//正则表达式 lambda表达式
        if(plate.matches(platePattern)){//车牌号满足正则表达式
            car.setPlate(plate);//更新车辆的车牌
            return true;//返回true
        }else {//车牌号不满足正则表达式
            System.out.println("您输入的车牌号格式错误");//提示错误
            return false;//返回false
        }
    }

    public void OutPutCarInfo(Car car){//输出进入停车场车辆的信息
        System.out.println("以下为您的停车信息，请核实");//提示词
        System.out.println("******************************************");//输出分隔符
        System.out.println("车辆类型\t\t车牌号\t\t\t入场时间");//表头
        System.out.println(car.getType() + "\t\t" + car.getPlate() + "\t" + car.getEntryTime());//输出入场车辆的信息
        System.out.println("******************************************");//输出分隔符
        System.out.println();//换行
    }

    public boolean DetermineDepartTime_input(Car car){//判断离场的时间是否正确
        System.out.println("请输入离场时间，请按正确的格式输入(yyyy-MM-dd/HH:mm:ss)");//提示输入离场时间
        Scanner sc = new Scanner(System.in);//接收输入
        String input = sc.nextLine();//接收输入
        String regix = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])/(0\\d|1\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";//利用正则表达式去判断离场时间的格式
        //这个正则表达式是生成的  大致意思是先匹配4位数字，匹配月份第一位的范围是 01 - 12，然后匹配日期，范围是01 - 31, 依次匹配
//        然后正则表达式不要用 == ，在java中 == 用于比较两个对象的引用是否相同，两个基本类型的值是否相同
        if(input.matches(regix) ) {//判断输入的车牌号是否符合lambda表达式
            car.setDepartTime(input);//更新离场时间
            return true;
        }
        LocalDateTime entryDateTime = LocalDateTime.parse(car.getEntryTime());//将这两个值转成 LocalDateTime类型去比较
        LocalDateTime departDateTime = LocalDateTime.parse(input);//将这两个值转成 LocalDateTime类型去比较
        if(departDateTime.isAfter(entryDateTime)){//判断离开的时间是否在进入的时间之后
            car.setDepartTime(input);//符合条件，赋值给车辆对象的属性中
            return true;//返回true
        } else {
            System.out.println("您输入错误的离开时间");//提示错误
            return false;//返回false
        }
    }

    public void setEntryTime_auto(Car car){//自动获取进入的时间
        LocalDateTime ldt = LocalDateTime.now(); //直接获取当前的时间
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");//规定时间的格式
        car.setEntryTime(ldt.format(df));//将格式化的时间赋值给相对应的属性
    }

    public void setDepartTime_auto(Car car){//自动获取离场时间
        LocalDateTime ldt = LocalDateTime.now();//直接获取当前的时间
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");//规定时间的格式
        car.setDepartTime(ldt.format(df));//将格式化的时间赋值给相对应的属性

    }

    //这个是输出， 上面是返回值，没有输出
    public void GetStopTime_output(Car car){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");//标准化格式
        LocalDateTime entryDateTime = LocalDateTime.parse(car.getEntryTime(), formatter);// 将入场时间字符串解析为 LocalDateTime 对象
        LocalDateTime departDateTime = LocalDateTime.parse(car.getDepartTime(), formatter);// 将出场时间字符串解析为 LocalDateTime 对象
        Duration duration = Duration.between(entryDateTime, departDateTime);// 计算时间差

        // 将时长转换为天、小时、分钟和秒
        long totalSeconds = duration.getSeconds(); // 获取总秒数
        long days = totalSeconds / (24 * 3600); // 停了几天
        long hours = (totalSeconds % (24 * 3600)) / 3600; // 停了多少小时
        long minutes = (totalSeconds % 3600) / 60; // 停了多少分钟
        long seconds = totalSeconds % 60; // 停了多少秒
        System.out.println("车牌号为"+car.getPlate() +"的车辆停车时长："+days + " 天 " + hours + " 小时 " + minutes + " 分钟"+ seconds+" 秒");//输出出场车辆信息
    }

    public void evaluation(Car car){//接收用户的反馈评价
        System.out.println("请留下您对此系统的评价或反馈");//接收用户对系统的评价
        Scanner sc = new Scanner(System.in);//接收输入
        String input = sc.nextLine();//接收输入
        car.setDescription(input);//记录评价
    }




}
