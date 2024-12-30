package sg.ntu.entity;

import sg.ntu.utils.DataTimeUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Parking {
    private Map<String, Car> cars = new HashMap<>(); //记录在停车场的车辆信息
    private Map<String, Car> outcars = new HashMap<>(); //记录离开的车辆信息
    private int totalSpaces = 100; //停车场的车位数量
    private double hourlyRate = 5.00; // 停车每小时的单价
    private double Money = 0; //收入

    public Parking() {
    }

    public Parking(Map<String, Car> cars,Map<String, Car> outcars, int totalSpaces, double hourlyRate) {
        this.cars = cars;
        this.outcars = outcars;
        this.totalSpaces = totalSpaces;
        this.hourlyRate = hourlyRate;
    }

    public Map<String, Car> getCars() {
        return cars;
    }

    public void setCars(Map<String, Car> cars) {
        this.cars = cars;
    }

    public Map<String, Car> getOutcars() {
        return outcars;
    }

    public void setOutcars(Map<String, Car> outcars) {
        this.outcars = outcars;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }


    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double money) {
        Money = money;
    }

    public int TotalSpaces_Plus(Parking parking) {//实现车位剩余数量+1
        parking.setTotalSpaces(parking.getTotalSpaces() + 1);//剩余车位数量+1
        return parking.getTotalSpaces();//返回剩余车位数量
    }

    public int TotalSpaces_Minus(Parking parking) {//实现车位剩余数量-1
        parking.setTotalSpaces(parking.getTotalSpaces() - 1);//剩余车位数量-1
        return parking.getTotalSpaces(); //返回剩余车位数量
    }

    public boolean DetermineSpace() { //判断停车场是否有空位
        if(this.totalSpaces==0){//停车场剩余位置为0
            System.out.println("不好意思，该停车场无空位");//报错
            return false;//无空位返回false
        }else if(this.totalSpaces > 0){//停车场剩余位置大于0
            return true;//有空位返回true
        }else {//异常输入
            return false;//异常输入返回false
        }
    }

    public boolean IsPlatesInParking(Parking parking, String plate) {//判断停车场是否停泊这个车牌号的车辆
        if(parking.getCars().containsKey(plate)){//判断在场车辆字典中是否存在车牌号
            return true;//存在返回true
        }else {//否则
            System.out.println("输入错误，停车场无此车");//报错
            return false;//不存在返回false
        }
    }

    //计算用户需要缴纳的费用
    public double CalculatorFee(Parking parking, Car car) {
        double totalFee = parking.hourlyRate * DataTimeUtils.GetStopTime(car);
        totalFee = Math.round(totalFee * 100.0) / 100.0;
        return totalFee;
    }

    //记录车辆信息
    public void RecordEntryVehicleInfo(Car car, Parking parking) {
        if (car instanceof Sedan){ //多态
            Sedan sedan = (Sedan) car; //将car对象转为sedan对象
            sedan = new Sedan(car.getType(), car.getPlate(), car.getEntryTime()); //初始化
            parking.getCars().put(car.getPlate(), sedan); // 将轿车信息放入到停车场中（信息放入字典Map中）
            parking.TotalSpaces_Minus(parking); //停车位-1
        }else if (car instanceof Bus){ //多态
            Bus bus = (Bus) car; //将car对象转为bus对象
            bus = new Bus(car.getType(), car.getPlate(), car.getEntryTime()); //初始化
            parking.getCars().put(car.getPlate(), bus); // 将公车信息放入到停车场中（信息放入字典Map中）
            parking.TotalSpaces_Minus(parking); //停车位-1
        }
    }

    public void RemoveCar(Car car, Parking parking) {//删除车辆信息
        parking.getCars().remove(car.getPlate());//通过车牌号删除车辆信息
        parking.TotalSpaces_Plus(parking);//停车位+1
    }

    public void PrintRecipt(Car car, Parking parking) {//打印小票的功能
        System.out.println("正在为您打印小票，请稍后.....");//提示打印小票
        car.GetStopTime_output(car);//获取离场车辆的停车时长
        String ticket = "****************乾坤停车场管理系统小票****************\n"
                + "♣ 车辆类型: " + car.getType() + "\n"
                + "♣ 车牌号: " + car.getPlate() + "\n"
                + "♣ 车辆入场时间: " + car.getEntryTime() + "\n"
                + "♣ 车辆离场时间: " + car.getDepartTime() + "\n"
                + "祝您一路顺风 ♥♥♥\n"
                + "***************************************************";//输出的字符串
        System.out.println(ticket);//输出小票信息
        double Fee = 0;
        if (car instanceof Sedan){
            Sedan sedan = (Sedan) car;
            Fee = sedan.CalculateParkingFee(car);//计算小轿车的缴纳金额
        }else if (car instanceof Bus){
            Bus bus = (Bus) car;
            Fee = bus.CalculateParkingFee(car);//计算公车的缴纳金额
        }
        System.out.println("您应该缴纳"+ Fee + "￥");//计算用户需要缴纳的费用
        parking.Money += Fee;//当日的盈利
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//时间日期格式化
        //IO输出流， 使用filewriter的方法， 这里用了try catch之后不需要使用close方法
        try (FileWriter writer = new FileWriter(date+ "--"+ car.getPlate() + ".txt")) {//字符流输出
            writer.write(ticket);//写入小票字符串
            System.out.println("小票已成功保存到文件！");//输出保存成功
        } catch (IOException e) {//异常
            System.out.println("保存小票时发生错误: " + e.getMessage());//输出保存失败
        }
    }

    public void OutRecord(Car car, Parking parking) {//记录离开车辆的信息
        if (car instanceof Sedan) {//多态
            Sedan sedan = new Sedan(car.getType(), car.getPlate(), car.getEntryTime(), car.getDepartTime(), car.getDescription());//初始化sedan对象
            parking.getOutcars().put(car.getPlate(), car);//将离场车辆信息放入到离场车辆字典中
        } else if (car instanceof Bus){//多态
            Bus bus = new Bus(car.getType(), car.getPlate(), car.getEntryTime(), car.getDepartTime(), car.getDescription());//初始化bus对象
            parking.getOutcars().put(car.getPlate(), car);//将离场车辆信息放入到离场车辆字典中
        }
    }
}
