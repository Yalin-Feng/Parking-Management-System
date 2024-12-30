package sg.ntu.manager;

//这里很奇怪， 他不自动引用
import sg.ntu.biz.LoginService;
import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SysManager {

    private static final int PARKING_CARINFO = 1;//查看在场车辆
    private static final int LEAVE_CARINFO = 2;//查看离场的车辆
    private static final int PROFIT = 3;//查看利润
    private static final int FEEDBACK = 4;//查看用户反馈
    private static final int EXIT_MANAGE = 5;//退出管理页面
    private static final int EXIT_SYSTEM = 6;//退出系统

    private static final int GENERATE_VERIFICATE_CODE = 1; //生成验证码
    private static final int EXIT_MANAGE_PAGE = 2; //退出管理页面

    public void AfterLogin(Parking parking, Manager manager){
        boolean flag = true;//这里加了一个判断标识符 这里是为了实现退出
        while(flag){//循环体
            System.out.println("1.在场车辆列表查询  2.离场的车辆列表信息  3.统计停车场每日营收额  4.查看今日车主反馈  5.退出管理员操作菜单  6.结束系统");//提示菜单
            Scanner sc = new Scanner(System.in);//接收输入
            int input = sc.nextInt();//接收输入
            switch (input){ //根据输入判断要调用那个方法
                case PARKING_CARINFO://查看在场车辆
                    parkingCarInfo(parking);//查看在场车辆
                    break;//结束case
                case LEAVE_CARINFO://查看离场的车辆
                    leaveCarInfo(parking);//查看离场的车辆
                    break;//结束case
                case PROFIT://查看利润
                    Profit(parking);//查看利润
                    break;//结束case
                case FEEDBACK://查看用户反馈
                    FeedBack(parking);//查看用户反馈
                    break;//结束case
                case EXIT_MANAGE:
                    //退出管理
                    //这里退了两步，推到选择用户权限的界面
                    manager.setSys_flag(true);
                    manager.setFlag(false);
                    flag = false;
                    break;//结束case
                case EXIT_SYSTEM://退出系统
                    System.exit(0);//退出系统
                default:
                    System.out.println("错误输入！");
                    break;
            }
        }
    }
    public void parkingCarInfo(Parking parking) {//查看在场车辆
        System.out.println("车辆类型\t车牌号\t\t\t入场时间"); //表头
        Iterator<Map.Entry<String, Car>> iterator = parking.getCars().entrySet().iterator();//迭代器
        while (iterator.hasNext()) {//循环迭代
            Map.Entry<String, Car> entry = iterator.next();//获得迭代器中的元素
            Car car = entry.getValue();    // 获取值
            System.out.println(car.getType()+"\t\t"+car.getPlate()+"\t"+car.getEntryTime()); //输出汽车的具体信息
        }
    }

    public void leaveCarInfo(Parking parking) {//查看离场车辆
        System.out.println("车辆类型\t车牌号\t\t\t入场时间\t\t\t离场时间");//表头
        Iterator<Map.Entry<String, Car>> it = parking.getOutcars().entrySet().iterator();//迭代器
        while (it.hasNext()) {//循环迭代
            Map.Entry<String, Car> entry = it.next();//获得迭代器中的元素
            Car car = entry.getValue();// 获取值
            System.out.println(car.getType()+"\t\t"+car.getPlate()+"\t"+car.getEntryTime()+"\t"+car.getDepartTime());//输出汽车的具体信息
        }
    }

    public void Profit(Parking parking) {
        System.out.println("今日营业额为：" + parking.getMoney());
    }//查看利润

    public void FeedBack(Parking parking) {//查看用户反馈
        Iterator<Map.Entry<String, Car>> it = parking.getOutcars().entrySet().iterator(); //这里可以理解为字典里面 key value， value为一个对象，那么迭代的时候，获取到这个字典，然后获取字典中的值
        System.out.println("以下为用户的体验反馈");//提示词
        while(it.hasNext()){//利用迭代器 遍历 字典//遍历用户反馈意见
            Map.Entry<String, Car> entry = it.next();//获取迭代器中的元素
            Car car = entry.getValue();//获得key对应的value
            System.out.println(car.getDescription());//输出用户的体验反馈
        }
    }

    public void Control(Car car, Parking parking, Manager manager){//登录控制
        LoginService loginService = new LoginService();//登录服务可以刷新 持久化的时候可能要去掉 不能让它刷新登录状态
        System.out.println("1.生成验证码  2.退出管理");//登录菜单
        Scanner sc = new Scanner(System.in);//接收输入
        int input = sc.nextInt();//接收输入
        switch(input){//选择功能
            case GENERATE_VERIFICATE_CODE://生成以及验证验证码
                loginService.generateVerificateCode(manager);//生成以及验证验证码
                AfterLogin(parking, manager);//登录之后的功能选择以及实现
                break;//结束case
            case EXIT_MANAGE_PAGE:
                //退出管理 这里没有描述清楚，我的理解是跳转到 用户停车入口 和 系统管理员入口
                //退一步，退回到上一个页面
                manager.setSys_flag(true);
                manager.setFlag(false);
                break;//结束case
        }

    }

}
