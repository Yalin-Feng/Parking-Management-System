package sg.ntu.manager;

import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;

import java.util.Scanner;

public class UserManager {

    public static void Control(Car car, Parking parking, Manager manager){
        System.out.println("1.记录车辆信息 2.车辆离场结算 3.车位数量 4.退出用户菜单");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
            switch(input){
                //记录车辆信息
                case 1:
                    //先判断停车场是否有剩余的车位
                    if(!parking.DetermineSpace()){
                        break;
                    }

                    //获取车辆的类型
                    if (!car.getCarType(car)){
                        break;
                    }

                    //获取车辆的车牌号
                    System.out.println("请输入你的车牌号");
                    Scanner sc1 = new Scanner(System.in);
                    String plate = sc1.nextLine();

                    //判断车辆的车牌格式是否正确
                    if(!car.DeterminePlate(car, plate)){
                        break;
                    }

                    //记录车辆进入的时间
                    car.setEntryTime_auto(car);

                    //输出车辆信息
                    car.OutPutCarInfo(car);

                    //停车场记录进入的车辆
                    parking.Record(car, parking);

                    break;

                //车辆离场结算功能
                case 2:

                    //离场输入车牌号
                    System.out.println("请输入你的车牌号");
                    sc = new Scanner(System.in);
                    String outPlate = sc.nextLine();

                    //判断车牌格式是否正确
                    if(!car.DeterminePlate(car, outPlate)){
                        break;
                    } else if (!parking.IsPlatesInParking(parking, outPlate)) { //判断停车场中是否有这个车辆
                        break;
                    }

                    //判断离开时间是否合适，
                    if(!car.DetermineDepartTime_input(car)){
                        break;
                    }

                    //这里我写了一个自动设定离开时间 有兴趣可以把上面一行注掉，然后用下面这个,中间至少等个10分钟，因为我是 分钟部分 按比例计费的
                    //car.setDepartTime_auto(car);

                    //计算停车的费用并且打印小票
                    parking.PrintRecipt(car, parking);

                    //评价功能
                    car.evaluation(car);

                    //更新到系统中
                    parking.Record(car, parking);

                    //移除车辆
                    parking.RemoveCar(car, parking);

                    //放入到离开车辆的表格中
                    parking.OutRecord(car, parking);

                    //车位数量
                case 3:
                    System.out.println("车位数量为 " + parking.getTotalSpaces());
                    break;

                //退出用户菜单
                case 4:
                    System.out.println("你确定要退出吗♥？  1.残忍退出   2.我再想想");
                    sc = new Scanner(System.in);
                    int in = sc.nextInt();
                    switch(in){
                        case 1:
                            manager.setFlag(false);
                            manager.setSys_flag(true);
                            break;
                        case 2:
                            break;
                    }

        }
    }


}
