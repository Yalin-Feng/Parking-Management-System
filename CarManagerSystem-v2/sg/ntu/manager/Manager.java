package sg.ntu.manager;

import sg.ntu.biz.LoginService;
import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;

import java.util.Scanner;

public class Manager {
    //本系统采用
    private boolean sys_flag = false; //用户权限 开关
    private boolean flag = true;  //选择功能 开关
    //上面二者都false，就退出系统， flag = false sys_flag=true，就后退一步
    public boolean isSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(boolean sys_flag) {
        this.sys_flag = sys_flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }



    public static void main(String[] args) {

        //实例化系统所要使用的对象
        Manager manager = new Manager();
        Car car = new Car();
        Parking parking = new Parking();
        SysManager sysManager = new SysManager();
        UserManager userManager = new UserManager();
        LoginService loginService = new LoginService();


        //采用先遍历一遍，再判断的方式
        do {
            System.out.println("*****************************************欢迎进入乾坤停车管理系统**********************************");
            System.out.println("该停车场剩余空位为：" + parking.getTotalSpaces());

            //选择用户的权限
            System.out.println("1.用户停车入口  2.系统管理员入口");
            System.out.println("请输入选择执行的命令");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();

            //这个是为了重新进入下面的循环
            manager.setFlag(true);


            while (manager.isFlag()) {
                switch (input) {
                    //普通用户可以使用的功能
                    case 1:
                        UserManager.Control(car, parking, manager);
                        break;
                    //管理员可以使用的功能
                    case 2:
                        SysManager.Control(car, parking, manager);
                        break;
                }
            }

        } while (manager.isSys_flag());

    }
}
