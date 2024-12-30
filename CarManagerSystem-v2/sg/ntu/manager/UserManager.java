package sg.ntu.manager;

import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;
import java.util.Scanner;

public class UserManager {
    private static final int RECORD_VEHICLE_INFORMATION = 1;//记录车辆信息
    private static final int VEHICLE_DEPARTURE_SETTLEMENT = 2;//车辆离场结算
    private static final int SHOW_PARKING_SPACES_NUMBER = 3;//车位数量
    private static final int EXIT_USER_MENU = 4;//退出用户菜单

    private static final int EXIT = 1;//退出
    private static final int STILL = 2;//留在用户页面

    public void Control(Car car, Parking parking, Manager manager){
        System.out.println("1.记录车辆信息 2.车辆离场结算 3.车位数量 4.退出用户菜单");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
            switch(input){
                case RECORD_VEHICLE_INFORMATION://记录入场车辆信息
                    if(!parking.DetermineSpace()){//先判断停车场是否有剩余的车位
                        break;//结束case
                    }
                    if (!car.getCarType(car)){//判断是否获取到入场车辆的类型
                        break;//结束case
                    }
                    System.out.println("请输入你的车牌号");//获取入场车辆的车牌号
                    Scanner sc1 = new Scanner(System.in);//接收用户的输入
                    String plate = sc1.nextLine();//接收用户的输入
                    if(!car.DeterminePlate(car, plate)){//判断入场车辆的车牌格式是否正确
                        break;//结束case
                    }
                    car.setEntryTime_auto(car);//记录入场车辆进入的时间
                    car.OutPutCarInfo(car);//输出入场车辆信息
                    parking.RecordEntryVehicleInfo(car, parking);//停车场记录入场车辆
                    break;//结束case
                case VEHICLE_DEPARTURE_SETTLEMENT://车辆离场结算功能
                    System.out.println("请输入你的车牌号");//离场输入车牌号
                    sc = new Scanner(System.in); //接收用户的输入
                    String outPlate = sc.nextLine(); //接收用户的输入
                    if(!car.DeterminePlate(car, outPlate)){//判断车牌格式是否正确
                        break;//结束case
                    } else if (!parking.IsPlatesInParking(parking, outPlate)) { //判断停车场中是否有这个车辆
                        break;//结束case
                    }
                    if(!car.DetermineDepartTime_input(car)){//判断离开时间是否合适，
                        break;//结束case
                    }
                    //这里我写了一个自动设定离开时间 有兴趣可以把上面一行注掉，然后用下面这个,中间至少等个10分钟，因为我是 分钟部分 按比例计费的
                    //car.setDepartTime_auto(car);
                    parking.PrintRecipt(car, parking);//计算停车的费用并且打印小票
                    car.evaluation(car); //评价功能
                    parking.RemoveCar(car, parking);//移除车辆
                    parking.OutRecord(car, parking);//放入到离开车辆的表格中
                case SHOW_PARKING_SPACES_NUMBER://车位数量
                    System.out.println("车位数量为 " + parking.getTotalSpaces());//输出车位数量
                    break;//结束当前case
                case EXIT_USER_MENU://退出用户菜单
                    System.out.println("你确定要退出吗♥？  1.残忍退出   2.我再想想");//提示是否要退出用户菜单
                    sc = new Scanner(System.in);//接收键盘输入
                    int in = sc.nextInt();//接收输入
                    switch(in){//判断是否退出用户菜单
                        case EXIT://退出
                            manager.setFlag(false);//退出到上一层
                            manager.setSys_flag(true);//退出到上上一层
                            break;//结束case
                        case STILL://我再想想，留在本页面
                            break;//结束case
                        default:
                            System.out.println("错误输入！");
                            break;
                    }
                default:
                    System.out.println("错误输入！");
                    break;

        }
    }

}
