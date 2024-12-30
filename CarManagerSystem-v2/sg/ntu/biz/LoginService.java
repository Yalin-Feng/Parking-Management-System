package sg.ntu.biz;

import sg.ntu.manager.Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class LoginService {
    private boolean Loginflag = false;

    public boolean isLoginflag() {
        return Loginflag;
    }

    public void setLoginflag(boolean loginflag) {
        Loginflag = loginflag;
    }

    public void generateVerificateCode(Manager manager){
        //生成随机数
        Random rand = new Random();
        //生成四位随机数
        int number = (int) (Math.random() * 9000) + 1000;
        String verificateCode = "";
        try{
            verificateCode = String.valueOf(number);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        //int verificateCode = random.nextInt(9000) + 1000;
        //int randomFourDigit = ThreadLocalRandom.current().nextInt(1000, 10000);

        //查到一种很新的四位随机数生成
//        Random random = new Random();
//        StringBuilder randomFourDigit = new StringBuilder();
//
//        for (int i = 0; i < 4; i++) {
//            randomFourDigit.append(random.nextInt(10)); // 生成 0-9 的随机数字
//        }
//
//        System.out.println("随机四位数: " + randomFourDigit);

        //利用IO流 将随机数写入到txt文件中
        try(FileWriter fw = new FileWriter("VerificationCode.txt")){
            fw.write(verificateCode);
        }catch (IOException e){
            System.out.println("验证码生成错误：" + e.getMessage());
        }
        int n = 3;//有三次机会
        boolean flag = true;//判断三次机会是否没用完
        System.out.println("请输入验证码");
        Scanner sc= new Scanner(System.in);
        while(flag){//用户不断输入验证码
            int input = sc.nextInt();
            if(input == number){//判断验证码是否正确
                //正确就进入到下一步
                setLoginflag(true);
                flag = false;
            }else {
                n--;
                if(n == 0){
                    System.out.println("对不起您多次输入错误，存在可疑行为，系统已自动退回");
                    flag = false;
                    manager.setFlag(false);
                }else {
                    System.out.println("请重新输入，还有" + n + "次机会");

                }
            }
        }

    }

}
