package ui;

import database.Test;
import database.TestMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortnumAmend extends JDialog implements ActionListener {
    JLabel Amendlabel;
    JTextField Amendtext;
    JButton jb1,jb2;
    JPanel jp1,jp2;
    static TestMain dataOper;
    public  PortnumAmend(Frame owner, String title, boolean model)
    {
        Amendlabel=new JLabel("修改端口号为");
        dataOper = new TestMain();
        String portnumAmendnum=dataOper.GetValueBykey("Port");
        Amendtext=new JTextField(portnumAmendnum,10);

        jb1=new JButton("保存");
        jb1.addActionListener(this);
        jb2=new JButton("取消");
        jb2.addActionListener(this);
        jp1=new JPanel(new GridLayout(1,2));
        jp2=new JPanel();
        jp1.add(Amendlabel);
        jp1.add(Amendtext);
        jp2.add(jb1);
        jp2.add(jb2);
        this.add(jp1,BorderLayout.NORTH);
        this.add(jp2,BorderLayout.SOUTH);
        //设置窗体
        this.setLocation(600,300);
        this.setSize(200,100);
        this.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
     if(e.getSource()==jb1)
     {

         dataOper.WriteProperties("Port",Amendtext.getText());
         this.setVisible(false);
     }else if (e.getSource()==jb2)
     {
         this.setVisible(false);
     }

    }
}
