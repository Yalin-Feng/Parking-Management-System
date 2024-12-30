package sg.ntu.manager;

import sg.ntu.biz.LoginService;
import sg.ntu.entity.Car;
import sg.ntu.entity.Parking;
import sg.ntu.entity.Sedan;

import java.util.Scanner;

public class Manager {

    private static final int USER_MANAGER_CONTROL = 1;
    private static final int SYSTEM_MANAGER_CONTROL = 2;
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
        Car car = new Sedan();
        Parking parking = new Parking();
        UserManager userManager = new UserManager();
        SysManager sysManager = new SysManager();
        LoginService loginService = new LoginService();
        //采用先遍历一遍，再判断的方式
        do {
            System.out.println("*****************************************欢迎进入乾坤停车管理系统**********************************");//欢迎词
            System.out.println("该停车场剩余空位为：" + parking.getTotalSpaces());//输出停车场剩余车位
            //选择用户的权限
            System.out.println("1.用户停车入口  2.系统管理员入口");//输出各入口功能
            System.out.println("请输入选择执行的命令");//输出提示词
            Scanner sc = new Scanner(System.in);//接收输入
            int input = sc.nextInt();//接收输入
            //这个是为了重新进入下面的循环
            manager.setFlag(true);//初始化Flag标识符
            while (manager.isFlag()) {//进入循环
                switch (input) {//判断使用哪个入口功能
                    case USER_MANAGER_CONTROL://普通用户入口
                        userManager.Control(car, parking, manager);//普通用户入口
                        break;//结束case
                    case SYSTEM_MANAGER_CONTROL://管理员用户入口
                        sysManager.Control(car, parking, manager);//管理员用户入口
                        break;//结束case
                    default://错误输入
                        System.out.println("错误输入！");//报错
                        manager.setFlag(false);
                        manager.setSys_flag(true);
                        break;//结束case
                }
            }
        } while (manager.isSys_flag());//判断是否继续循环

    }
}
