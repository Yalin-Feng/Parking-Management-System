package sg.ntu.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class Car {
    private String type;
    private String entryTime;
    private String departTime;
    private String plate;
    private String description;


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

    //获取车辆的种类  1.轿车  2.客车
    public boolean getCarType(Car car){

        Scanner sc = new Scanner(System.in);
        System.out.println("1.轿车 2.客车 ");
        int input = sc.nextInt();


        if(input == 1){
            car.setType("轿车");
            return true;
        }else if(input == 2){
            car.setType("客车");
            return true;
        }else {
            System.out.println("输入错误！");
            return false;
        }
    }


    //判断车牌号的格式是否正确 优化之后用正则比较好，且易读
    public boolean DeterminePlate(Car car, String plate) {
        String platePattern = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵青藏川宁琼港澳台]{1}[A-Z]{1}[A-Z0-9]{5,6}$";
        if(plate.matches(platePattern)){
            car.setPlate(plate);
            return true;
        }else {
            System.out.println("您输入的车牌号格式错误");
            return false;
        }
    }

    //输出进入停车场车辆的信息
    public void OutPutCarInfo(Car car){
        System.out.println("以下为您的停车信息，请核实");
        System.out.println("******************************************");
        System.out.println("车辆类型\t\t车牌号\t\t\t入场时间");
        System.out.println(car.getType() + "\t\t" + car.getPlate() + "\t" + car.getEntryTime());
        System.out.println("******************************************");
        System.out.println();
    }

    //判断离场的时间是否正确
    public boolean DetermineDepartTime_input(Car car){

        System.out.println("请输入离场时间，请按正确的格式输入(yyyy-MM-dd/HH:mm:ss)");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        //利用正则表达式去判断离场时间的格式
        String regix = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])/(0\\d|1\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

        //这个正则表达式是生成的  大致意思是先匹配4位数字，匹配月份第一位的范围是 01 - 12，然后匹配日期，范围是01 - 31, 依次匹配
//        然后正则表达式不要用 == ，在java中 == 用于比较两个对象的引用是否相同，两个基本类型的值是否相同

        if(input.matches(regix) ) {
            car.setDepartTime(input);
            return true;
        }

        //将这两个值转成 LocalDateTime类型去比较
        LocalDateTime entryDateTime = LocalDateTime.parse(car.getEntryTime());
        LocalDateTime departDateTime = LocalDateTime.parse(input);

        //判断离开的时间是否在进入的时间之后
        if(departDateTime.isAfter(entryDateTime)){
            //符合条件，赋值给车辆对象的属性中
            car.setDepartTime(input);
            return true;
        } else {
            System.out.println("您输入错误的离开时间");
            return false;
        }

    }

    //自动获取进入的时间
    public void setEntryTime_auto(Car car){
        //直接获取当前的时间
        LocalDateTime ldt = LocalDateTime.now();

        //规定时间的格式
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");

        //将格式化的时间赋值给相对应的属性
        car.setEntryTime(ldt.format(df));
    }

    public void setDepartTime_auto(Car car){
        //直接获取当前的时间
        LocalDateTime ldt = LocalDateTime.now();

        //规定时间的格式
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");

        //将格式化的时间赋值给相对应的属性
        car.setDepartTime(ldt.format(df));

    }

    //获取停车的时长
    public double GetStopTime(Car car){
        double totalHours = 0;
        try {
            //规范一下时间的格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");

            // 将字符串解析为 LocalDateTime 对象
            LocalDateTime entryDateTime = LocalDateTime.parse(car.getEntryTime(), formatter);
            LocalDateTime departDateTime = LocalDateTime.parse(car.getDepartTime(), formatter);

            // 计算时间差
            Duration duration = Duration.between(entryDateTime, departDateTime);
            long totalMinutes = duration.toMinutes();

            // 计算时长       分别是 天、小时、分钟
            long days = totalMinutes / (24 * 60);
            long hours = (totalMinutes % (24 * 60)) / 60;
            long minutes = totalMinutes % 60;

            //计算总小时数
            totalHours = days * 24 + hours + (minutes / 60.0);


            //这里我按不足一小时， 算一个小时的钱
            if(totalHours < 1){
                totalHours = 1;
            }
        } catch (Exception e) {
            System.out.println("时间解析错误: " + e.getMessage());
        }
        return totalHours;
    }

    //这个是输出， 上面是返回值，没有输出
    public void GetStopTime_output(Car car){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss");


        // 将字符串解析为 LocalDateTime 对象
        LocalDateTime entryDateTime = LocalDateTime.parse(car.getEntryTime(), formatter);
        LocalDateTime departDateTime = LocalDateTime.parse(car.getDepartTime(), formatter);


        // 计算时间差
        Duration duration = Duration.between(entryDateTime, departDateTime);
        long totalMinutes = duration.toMinutes();

        // 计算时长       分别是 天、小时、分钟
        long days = totalMinutes / (24 * 60);
        long hours = (totalMinutes % (24 * 60)) / 60;
        long minutes = totalMinutes % 60;
        long seconds = totalMinutes % 60 / 60;

        System.out.println("车牌号为"+car.getPlate() +"的车辆停车时长："+days + " 天 " + hours + " 小时 " + minutes + " 分钟"+ seconds+" 秒");
    }

    //接收用户的反馈评价
    public void evaluation(Car car){
        //接收用户对系统的评价
        System.out.println("请留下您对此系统的评价或反馈");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        //记录评价
        car.setDescription(input);
    }




}
