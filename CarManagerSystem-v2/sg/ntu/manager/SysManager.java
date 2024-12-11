package sg.ntu.manager;

//这里很奇怪， 他不自动引用
import sg.ntu.biz.LoginService;
import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SysManager {

    public static void AfterLogin(Parking parking, Manager manager){
        //这里加了一个判断标识符 这里是为了实现退出
        boolean flag = true;

        while(flag){
            System.out.println("1.在场车辆列表查询  2.离场的车辆列表信息  3.统计停车场每日营收额  4.查看今日车主反馈  5.退出管理员操作菜单  6.结束系统");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            switch (input){
                case 1:
                    //查看在场车辆
                    parkingCarInfo(parking);
                    break;
                case 2:
                    //查看离场的车辆
                    leaveCarInfo(parking);
                    break;
                case 3:
                    //查看利润
                    Profit(parking);
                    break;
                case 4:
                    //查看用户反馈
                    FeedBack(parking);
                    break;
                case 5:
                    //退出管理
                    //这里退了两步，推到选择用户权限的界面
                    manager.setSys_flag(true);
                    manager.setFlag(false);
                    flag = false;
                    break;
                case 6:
                    //退出系统
                    System.exit(0);
            }
        }

    }

    //查看在场车辆
    public static void parkingCarInfo(Parking parking) {
        System.out.println("车辆类型\t车牌号\t入场时间");

        Iterator<Map.Entry<String, Car>> iterator = parking.getCars().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Car> entry = iterator.next();
            Car car = entry.getValue();    // 获取值
            System.out.println(car.getType()+"\t\t"+car.getPlate()+"\t"+car.getEntryTime());
        }
    }

    //查看离场车辆
    public static void leaveCarInfo(Parking parking) {
        System.out.println("车辆类型\t车牌号\t入场时间\t离场时间");

        Iterator<Map.Entry<String, Car>> it = parking.getOutcars().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Car> entry = it.next();
            Car car = entry.getValue();
            System.out.println(car.getType()+"\t\t"+car.getPlate()+"\t"+car.getEntryTime());
        }
    }

    //查看利润
    public static void Profit(Parking parking) {
        System.out.println("今日营业额为：" + parking.getMoney());
    }


    //查看用户反馈
    public static void FeedBack(Parking parking) {
        //利用迭代器 遍历 字典
        //这里可以理解为字典里面 key value， value为一个对象，那么迭代的时候，获取到这个字典，然后获取字典中的值
        Iterator<Map.Entry<String, Car>> it = parking.getOutcars().entrySet().iterator();


        System.out.println("以下为用户的体验反馈");
        //遍历用户反馈意见
        while(it.hasNext()){
            Map.Entry<String, Car> entry = it.next();
            Car car = entry.getValue();
            System.out.println(car.getDescription());

        }
    }


    public static void Control(Car car, Parking parking, Manager manager){
        //登录服务可以刷新 持久化的时候可能要去掉 不能让它刷新登录状态
        LoginService loginService = new LoginService();

        //登录菜单
        System.out.println("1.生成验证码  2.退出管理");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        //选择功能
        switch(input){
            case 1:
                //生成以及验证验证码
                loginService.generateVerificateCode(manager);

                //登录之后的功能选择以及实现
                AfterLogin(parking, manager);
                break;
            case 2:
                //退出管理 这里没有描述清楚，我的理解是跳转到 用户停车入口 和 系统管理员入口
                //退一步，退回到上一个页面
                manager.setSys_flag(true);
                manager.setFlag(false);
                break;
        }

    }

}
