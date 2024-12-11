import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;
import sg.ntu.manager.SysManager;
import sg.ntu.manager.UserManager;

import java.util.Scanner;

public class test {
    private boolean sys_flag = false;

    public static void main(String[] args) {

        //循环标识符
        boolean flag = true;

        //实例化系统所要使用的对象
        Car car = new Car();
        Parking parking = new Parking();
        SysManager sysManager = new SysManager();
        UserManager userManager = new UserManager();


            System.out.println("*****************************************欢迎进入乾坤停车管理系统**********************************");
            System.out.println("该停车场剩余空位为：" + parking.getTotalSpaces());

            System.out.println("1.用户停车入口  2.系统管理员入口");
            System.out.println("请输入选择执行的命令");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            while (flag) {

                switch (input) {
                    //普通用户
                    case 1:

                        break;
                    //管理员
                    case 2:
                        break;


                }
            }



    }
}
