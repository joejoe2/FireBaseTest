/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firebasetest;

import com.firebase.client.Firebase;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author 70136
 */
public class FireBaseTest extends JFrame{
    JButton send,stop;
    JButton ini;
    Firebase myFirebaseRef;
    Map<String, Object> sensor1;
    LinkedList<String> list1;
    Timer timer1;
    public FireBaseTest() {
        this.setSize(500,500);
        getContentPane().setLayout(null);
        //
        ini=new JButton("initialize");
        ini.setSize(100,50);
        ini.setLocation(0,0);
        this.add(ini);
        //
        send=new JButton("send");
        send.setSize(100,50);
        send.setLocation(150,0);
        this.add(send);
        //
        stop=new JButton("stop");
        stop.setSize(100,50);
        stop.setLocation(300,0);
        this.add(stop);
        //
        sensor1=new HashMap<String,Object>();
        list1=new LinkedList<>();
        //list1.add("-1");list1.add("-1");list1.add("-1");list1.add("-1");
        sensor1.put("t0",-1);
        sensor1.put("t1",-1);
        sensor1.put("t2",-1);
        sensor1.put("t3",-1);
        ini.addActionListener((e) -> {
            myFirebaseRef =new Firebase ( "https://test-89b56.firebaseio.com/ " );
            myFirebaseRef.child("/sensor1").updateChildren(sensor1);
        });
        //
        timer1=new Timer(5000,(e) -> {
            if(list1.size()>=4){
            list1.pollFirst();
            list1.add(LocalDateTime.now().toString().split("[.]")[0]);
            sensor1.put("t0",list1.get(0));
            sensor1.put("t1",list1.get(1));
            sensor1.put("t2",list1.get(2));
            sensor1.put("t3",list1.get(3));
            }else{
                list1.add(LocalDateTime.now().toString().split("[.]")[0]);
                sensor1.put("t"+(list1.size()-1),list1.peekLast());
            }
            myFirebaseRef.child("/sensor1").updateChildren(sensor1);
        });
        
        send.addActionListener((e) -> {
            timer1.start();
        });
        stop.addActionListener((e) -> {
            timer1.stop();
        });
        //
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.toFront();
        this.setAlwaysOnTop(false);
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new FireBaseTest();
        System.out.println(LocalDateTime.now().toString().split("[.]")[0]);
    }
    
}
