package com.example.chance.learn_dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_PROGRESS = 100;    //进度条最大数
    private ProgressDialog mProgressDialog  = null; //进度条
    final String[] m_Items = {"Frist","Second","Third"};
    int mSingleChoiceID = -1;    //记录单选中的ID
    ArrayList <Integer>MultiChoiceID = new ArrayList<Integer>();//记录多选选中的id号

    // ////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    private void findViews()
    {
        // //////////////////////////////////////////////////////////////////////////////
         /* 【简单提示对话框】 */
        Button btn1 = (Button) findViewById(R.id.dialg_demo_btn01);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(MainActivity.this).setTitle("简单提示对话框").setMessage("这是提示信息")
                        .show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /* 【带确定取消按钮的提示对话框】 */
        Button btn2 = (Button) findViewById(R.id.dialg_demo_btn02);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                AlertDialog.Builder dialog02 = new AlertDialog.Builder(MainActivity.this);
                dialog02.setTitle("带确定取消按钮的提示对话框");
                dialog02.setIcon(R.drawable.qq);
                dialog02.setMessage("这是提示内容");
                dialog02.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialoginterface, int which)
                    {
                        Toast.makeText(MainActivity.this, "你选择了确定", Toast.LENGTH_LONG).show();
                    }
                });
                dialog02.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialoginterface, int which)
                    {
                        Toast.makeText(MainActivity.this, "你选择了取消", Toast.LENGTH_LONG).show();
                    }
                });
                dialog02.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /* 【带多个按钮的提示对话框】 */
        Button btn3 = (Button) findViewById(R.id.dialg_demo_btn03);
        btn3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                AlertDialog.Builder dialog03 = new AlertDialog.Builder(MainActivity.this);
                dialog03.setIcon(R.drawable.qq);
                dialog03.setTitle("带多个按钮的提示对话框");
                dialog03.setMessage("你最喜欢的球类运动是什么呢？");
                dialog03.setPositiveButton("篮球", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialoginterface, int which)
                    {
                        showDialog("篮球很不错");
                    }
                });
                dialog03.setNeutralButton("乒乓球", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialoginterface, int which)
                    {
                        showDialog("乒乓球很不错");
                    }
                });
                dialog03.setNegativeButton("足球", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialoginterface, int which)
                    {
                        showDialog("足球很不错");
                    }
                });
                dialog03.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【单选按钮对话框】*/
        Button btn4 = (Button) findViewById(R.id.dialg_demo_btn04);
        btn4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                mSingleChoiceID = -1;
                AlertDialog.Builder dialog04 = new AlertDialog.Builder(MainActivity.this);
                dialog04.setTitle("单选按妞");
                dialog04.setSingleChoiceItems(m_Items, 0, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        mSingleChoiceID = whichButton;
                        showDialog("你选择的id为" + whichButton + " , " + m_Items[whichButton]);
                    }
                });
                dialog04.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        if (mSingleChoiceID > 0)
                        {
                            showDialog("你选择的是" + mSingleChoiceID);
                        }
                    }
                });
                dialog04.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                    }
                });
                dialog04.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【多选按钮对话框】*/
        Button btn5 = (Button) findViewById(R.id.dialg_demo_btn05);
        btn5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                MultiChoiceID.clear();
                AlertDialog.Builder dialog05 = new AlertDialog.Builder(MainActivity.this);
                dialog05.setTitle("多选按钮");
                dialog05.setMultiChoiceItems(m_Items, new boolean[]
                                { false, false, false},
                        new DialogInterface.OnMultiChoiceClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton,
                                                boolean isChecked)
                            {
                                if (isChecked)
                                {
                                    MultiChoiceID.add(whichButton);
                                    showDialog("你选择的id为" + whichButton + " , "
                                            + m_Items[whichButton]);
                                } else
                                {
                                    MultiChoiceID.remove(whichButton);
                                }

                            }
                        });
                dialog05.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【列表框对话框】*/
        Button btn6 = (Button) findViewById(R.id.dialg_demo_btn06);
        btn6.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                AlertDialog.Builder dialog06 = new AlertDialog.Builder(MainActivity.this);
                dialog06.setTitle("列表框");
                dialog06.setItems(m_Items, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 点击后弹出窗口选择了第几项
                        showDialog("你选择的id为" + which + " , " + m_Items[which]);
                    }
                });
                dialog06.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【自定义登录对话框】*/
        Button btn7 = (Button) findViewById(R.id.dialg_demo_btn07);
        btn7.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View view = factory.inflate(R.layout.dialog_demo_login, null);// 获得自定义对话框

                AlertDialog.Builder dialog07 = new AlertDialog.Builder(MainActivity.this);
                dialog07.setIcon(R.drawable.qq);
                dialog07.setTitle("自定义登录对话框");
                dialog07.setView(view);
                dialog07.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        EditText userName = (EditText) view
                                .findViewById(R.id.dialog_demo_loginETUserName);
                        EditText password = (EditText) view
                                .findViewById(R.id.dialog_demo_loginETPassWord);
                        showDialog("姓名 ：" + userName.getText().toString() + "密码："
                                + password.getText().toString());
                    }
                });
                dialog07.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        //Toast.makeText(dialog_demo.this, "你选择了取消", Toast.LENGTH_LONG).show();
                        showDialog("你选择了取消");
                    }
                });
                dialog07.create().show();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
        Button btn8 = (Button) findViewById(R.id.dialg_demo_btn08);
        btn8.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                mProgressDialog = new ProgressDialog(MainActivity.this);//创建ProgressDialog对象
                mProgressDialog.setIcon(R.drawable.qq);// 设置ProgressDialog标题 图标
                mProgressDialog.setTitle("进度条窗口");// 设置ProgressDialog标题
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条风格，风格为长形
                mProgressDialog.setMax(MAX_PROGRESS);// 设置ProgressDialo进度条进度
                mProgressDialog.setButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // 这里添加点击后的逻辑
                    }
                });
                mProgressDialog.setButton2("取消", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // 这里添加点击后的逻辑
                    }
                });
                mProgressDialog.show();
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        int Progress = 0;
                        while (Progress < MAX_PROGRESS)
                        {
                            try
                            {
                                mProgressDialog.setProgress(Progress++);
                                //mProgressDialog.incrementProgressBy(1);
                                Thread.sleep(100);
                            } catch (Exception e)
                            {
                                // TODO Auto-generated catch block
                                mProgressDialog.cancel();
                                //e.printStackTrace();
                            }
                        }
                    };
                }.start();
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【圆形(转圈)进度条】*/
        Button btn9 = (Button) findViewById(R.id.dialg_demo_btn09);
        btn9.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                mProgressDialog = new ProgressDialog(MainActivity.this);//创建ProgressDialog对象
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //设置进度条风格，风格为圆形，旋转的
                mProgressDialog.setTitle("读取ing...");// 设置ProgressDialog标题
                mProgressDialog.setMessage("正在读取中请稍候...");// 设置ProgressDialog提示信息
                mProgressDialog.setIndeterminate(true);//设置ProgressDialog 的进度条不明确
                mProgressDialog.setCancelable(true);// 设置ProgressDialog 可以按退回键取消
                mProgressDialog.setButton("确定", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        // 这里添加点击后的逻辑
                    }
                });
                mProgressDialog.show();// 让ProgressDialog显示
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
         /*【带补充对话框】*/
        Button btn10 = (Button) findViewById(R.id.dialg_demo_btn10);
        btn10.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                return;
            }
        });
        // //////////////////////////////////////////////////////////////////////////////
    }

    // ////////////////////////////////////////////////////////////////////////////////////
     /*显示子函数*/
    private void showDialog(String str)
    {
        new AlertDialog.Builder(MainActivity.this).setMessage(str).show();
        // Toast.makeText(dialog_demo.this, str, Toast.LENGTH_LONG).show();
    }
    // ////////////////////////////////////////////////////////////////////////////////////
}
