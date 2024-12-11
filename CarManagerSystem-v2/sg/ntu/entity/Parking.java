package sg.ntu.entity;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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


    //实现车位剩余数量+1
    public int TotalSpaces_Plus(Parking parking) {

        parking.setTotalSpaces(parking.getTotalSpaces() + 1);
        return parking.getTotalSpaces();
    }


    //实现车位剩余数量-1
    public int TotalSpaces_Minus(Parking parking) {

        parking.setTotalSpaces(parking.getTotalSpaces() - 1);
        return parking.getTotalSpaces();
    }

    //判断停车场是否有空位
    public boolean DetermineSpace() {

        if(this.totalSpaces==0){
            System.out.println("不好意思，该停车场无空位");
            return false;
        }else if(this.totalSpaces > 0){
            return true;
        }else {
            return false;
        }
    }


    //判断停车场是否停泊这个车牌号的车辆
    public boolean IsPlatesInParking(Parking parking, String plate) {
        if(parking.getCars().containsKey(plate)){
            return true;
        }else {
            System.out.println("输入错误，停车场无此车");
            return false;
        }
    }

    //计算用户需要缴纳的费用
    public double CalculatorFee(Parking parking, Car car) {
        double totalFee = parking.hourlyRate * car.GetStopTime(car);
        totalFee = Math.round(totalFee * 100.0) / 100.0;
        return totalFee;
    }

    //记录车辆信息
    public void Record(Car car, Parking parking) {
        Car newCar = new Car(car.getType(), car.getPlate(), car.getEntryTime());
        parking.getCars().put(car.getPlate(), newCar);
        parking.TotalSpaces_Minus(parking);

    }

    //删除车辆信息
    public void RemoveCar(Car car, Parking parking) {
        parking.getCars().remove(car.getPlate());
        parking.TotalSpaces_Plus(parking);
    }

    //打印小票的功能
    public void PrintRecipt(Car car, Parking parking) {
        System.out.println("正在为您打印小票，请稍后.....");
        car.GetStopTime_output(car);
        String ticket = "****************乾坤停车场管理系统小票****************\n"
                + "♣ 车辆类型: " + car.getType() + "\n"
                + "♣ 车牌号: " + car.getPlate() + "\n"
                + "♣ 车辆入场时间: " + car.getEntryTime() + "\n"
                + "♣ 车辆离场时间: " + car.getDepartTime() + "\n"
                + "祝您一路顺风 ♥♥♥\n"
                + "***************************************************";
        System.out.println(ticket);

        //固定好缴纳的金额
        double Fee = parking.CalculatorFee(parking, car);


        //计算用户需要缴纳的费用
        System.out.println("您应该缴纳"+ parking.CalculatorFee(parking, car) + "￥");

        //当日的盈利
        parking.Money += Fee;


        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //IO输出流， 使用filewriter的方法， 这里用了try catch之后不需要使用close方法
        try (FileWriter writer = new FileWriter(date+ "--"+ car.getPlate() + ".txt")) {
            writer.write(ticket);
            System.out.println("小票已成功保存到文件！");
        } catch (IOException e) {
            System.out.println("保存小票时发生错误: " + e.getMessage());
        }
    }

    //记录离开车辆的信息
    public void OutRecord(Car car, Parking parking) {
        Car newCar = new Car(car.getType(), car.getPlate(), car.getEntryTime(), car.getDepartTime(), car.getDescription());
        parking.getOutcars().put(car.getPlate(), car);
    }

}
