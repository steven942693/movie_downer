package com.steven.view;

import com.steven.spider.MovieSpider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TSDView {
    private static JFrame main_frame;
    private static JPanel top_pane;
    private static JPanel mid_pane;
    private static JPanel bottom_pane;
    private static SimpleDateFormat sdf;
    private static JLabel current_time;
    public static JProgressBar bar;
    private static JButton start_button;
    public static JTextArea textArea;
    public static JTextField nums_th_input;
    public static JTextField start_page;
    public static JTextField end_page;
    public static JRadioButton radio_true;
    public static JRadioButton radio_false;
    public static JButton binder;
    public static File directory;
    public static JTextField video_name_field;
    public static void main(String[] args) {
        try {
//            去除顶部设置按钮
            UIManager.put("RootPane.setupButtonVisible", false);
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

            start_button = new JButton("开始");
            textArea = new JTextArea();
            nums_th_input = new JTextField("1024");
            start_page = new JTextField("0");
            end_page = new JTextField("0");
            radio_true = new JRadioButton("是");
            radio_true.setSelected(true);
            radio_false = new JRadioButton("否");
            binder = new JButton("TS合并");
            video_name_field = new JTextField();
        } catch (Exception e) {
            //TODO exception
        }
//        JFrame设置标题
        main_frame = new JFrame("TS下载器");
//        设置窗口不可以拖动大小
        main_frame.setResizable(false);
//        JFrame设置图标
        ImageIcon icon = new ImageIcon(TSDView.class.getResource("/app_ico/logo.png"));
        main_frame.setIconImage(icon.getImage());

//        JFrame加载背景图片
        ImageIcon bg = new ImageIcon(TSDView.class.getResource("/app_ico/bg.jpg"));
        JLabel bg_label = new JLabel(bg);
        bg_label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
//        把背景图片放入JFrame上
        main_frame.getLayeredPane().add(bg_label, new Integer(Integer.MIN_VALUE));

//        获取系统的分辨率
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        根据系统的分辨率设置窗口的大小
        main_frame.setSize(new Dimension((int) (screenSize.width * 0.6), (int) (screenSize.height * 0.6)));

//        把整个界面的布局分为上中下三块
//        顶部 提示搜索栏
//        ===========================================  顶栏  ===========================================
        top_pane = new JPanel();


//      设置当前时间
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current_time = new JLabel("年-月-日 时-分-秒", JLabel.LEFT);
        current_time.setFont(new Font("楷体", Font.BOLD, 30));
        current_time.setForeground(new Color(238, 87, 87));


//        将顶栏组件添加到 top_pane上
        top_pane.add(current_time);

//        ===========================================  中栏  ===========================================
        mid_pane = new JPanel(new BorderLayout());
//        加载开场动画
        ImageIcon gif = new ImageIcon(TSDView.class.getResource("/app_ico/start.png"));
        gif.setImage(gif.getImage().getScaledInstance((int) (main_frame.getWidth()), (int) (main_frame.getHeight()), Image.SCALE_DEFAULT));
        JLabel label_gif = new JLabel(gif);
        mid_pane.add(label_gif);
        label_gif.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mid_pane.removeAll();
                mid_pane.repaint();

                //      上栏 URL
                JPanel mid_top_pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
                mid_top_pane.setPreferredSize(new Dimension(mid_pane.getWidth(),200));
                JLabel url = new JLabel("URL", JLabel.LEFT);
                url.setFont(new Font("楷体", Font.BOLD, 40));
                JLabel video_name = new JLabel("视频名称:", JLabel.LEFT);
                video_name.setFont(new Font("楷体", Font.BOLD, 25));

                video_name_field.setFont(new Font("楷体", Font.BOLD, 25));
                video_name_field.setColumns(65);

                textArea.setColumns(70);
                textArea.setRows(3);
                textArea.setFont(new Font("楷体", Font.BOLD, 25));
                textArea.setBorder(new LineBorder(new Color(19, 234, 243), 3));
                textArea.setOpaque(false);
                textArea.setLineWrap(true);

                JScrollPane jsp = new JScrollPane(textArea);
                jsp.setOpaque(false);
                mid_top_pane.add(video_name);
                mid_top_pane.add(video_name_field);
                mid_top_pane.add(url);
                mid_top_pane.add(jsp);

                //      中栏选项卡
                JPanel mid_mid_pane = new JPanel(new GridLayout(1, 7, 10, 7));
//                mid_mid_pane.setPreferredSize(new Dimension(mid_pane.getWidth(),40));
                start_page.setFont(new Font("楷体", Font.BOLD, 25));
                start_page.setHorizontalAlignment(0);
                end_page.setFont(new Font("楷体", Font.BOLD, 25));
                end_page.setHorizontalAlignment(0);

                JLabel s_2_e = new JLabel("==>");
                s_2_e.setFont(new Font("楷体", Font.BOLD, 30));
//                s_2_e.setPreferredSize(new Dimension(100,40));
                s_2_e.setHorizontalAlignment(0);

                JLabel type_pj = new JLabel("序号拼接:");
                type_pj.setFont(new Font("楷体", Font.BOLD, 20));
//                type_pj.setHorizontalAlignment(0);


                radio_true.setFont(new Font("楷体", Font.BOLD, 25));
                radio_true.setOpaque(false);//透明
                radio_false.setFont(new Font("楷体", Font.BOLD, 25));
                radio_false.setOpaque(false);
                ButtonGroup buttonGroup = new ButtonGroup();
                buttonGroup.add(radio_true);
                buttonGroup.add(radio_false);

                JLabel nums_thread = new JLabel("线程数:", JLabel.CENTER);
                nums_thread.setFont(new Font("楷体", Font.BOLD, 20));

                nums_th_input.setFont(new Font("楷体", Font.BOLD, 25));
                nums_th_input.setHorizontalAlignment(0);

                start_button.setForeground(Color.GREEN);
                start_button.setFont(new Font("楷体", Font.BOLD, 28));

                JLabel notice = new JLabel("视频号:");
                notice.setFont(new Font("楷体", Font.BOLD, 25));
                notice.setHorizontalAlignment(0);

                mid_mid_pane.add(notice);
                mid_mid_pane.add(start_page);
                mid_mid_pane.add(s_2_e);
                mid_mid_pane.add(end_page);
                mid_mid_pane.add(type_pj);
                mid_mid_pane.add(radio_true);
                mid_mid_pane.add(radio_false);

                mid_mid_pane.add(nums_thread);
                mid_mid_pane.add(nums_th_input);
                mid_mid_pane.add(start_button);

//                mid_mid_pane.add(new JLabel("\t"));


                //      低栏 进度条
                JPanel mid_bottom_pane = new JPanel(new GridBagLayout());
                mid_bottom_pane.setPreferredSize(new Dimension(mid_pane.getWidth() - 50, 230));
                bar = new JProgressBar();
                bar.setPreferredSize(new Dimension(800, 50));
                bar.setStringPainted(true);
                bar.setFont(new Font("楷体", Font.BOLD, 30));
                bar.setValue(0);

                mid_bottom_pane.add(new JLabel("\t"));
                mid_bottom_pane.add(bar);
                mid_bottom_pane.add(new JLabel("\t"));


                binder.setForeground(Color.GREEN);
                binder.setFont(new Font("楷体", Font.BOLD, 28));

                JLabel to_binder = new JLabel("===>");
                to_binder.setFont(new Font("楷体", Font.BOLD, 28));
                mid_bottom_pane.add(to_binder);
                mid_bottom_pane.add(binder);
                mid_bottom_pane.add(new JLabel("\t"));

                mid_top_pane.setOpaque(false);
                mid_mid_pane.setOpaque(false);
                mid_bottom_pane.setOpaque(false);

                mid_pane.add(mid_top_pane, BorderLayout.NORTH);
                mid_pane.add(mid_mid_pane, BorderLayout.CENTER);
                mid_pane.add(mid_bottom_pane, BorderLayout.SOUTH);
            }
        });


//        ===========================================  尾栏  ===========================================
//        作者信息
        bottom_pane = new JPanel(new FlowLayout());
        bottom_pane.setBorder(new BevelBorder(1));
        JLabel author = new JLabel("Coding By Steven");
        author.setFont(new Font("楷体", Font.BOLD, 25));
        author.setForeground(new Color(105, 87, 238));
        bottom_pane.add(author);

//        将上中下三个面板的背景设置为透明
        top_pane.setOpaque(false);
        mid_pane.setOpaque(false);
        bottom_pane.setOpaque(false);
//        将上中下三个面板添加到JFrame上
        main_frame.add(top_pane, BorderLayout.NORTH);
        main_frame.add(mid_pane, BorderLayout.CENTER);
        main_frame.add(bottom_pane, BorderLayout.SOUTH);
//      设置JFrame居中显示
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);

        new Thread() {
            @Override
            public void run() {
                while (true) {
//                    不断刷新窗体,防止输入时阻塞
                    main_frame.repaint();
//                    显示当前的系统时间
                    current_time.setText(sdf.format(new Date()));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

//      设置窗口点击 X 时,正常关闭
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myEvent();
    }

    public static void myEvent() {
        binder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog binder_jDialog = new JDialog();

                binder_jDialog.setSize(300, 200);
                binder_jDialog.setLocationRelativeTo(main_frame);
                binder_jDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);

                JPanel binder_jPanel = new JPanel(new GridLayout(2,1,10,20));

                JButton current = new JButton("合并当前下载");
                current.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        binder(MovieSpider.save_dir);
                        binder_jDialog.setVisible(false);
                    }
                });


                JButton other = new JButton("合并其他视频");
                other.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser jfc = new JFileChooser();
                        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        jfc.showDialog(binder_jDialog, "选择输出路径");
                        directory = jfc.getSelectedFile();
                        if (directory != null) {
                            System.out.println(directory.toString());
                            binder(directory.getPath());
                        }
                        binder_jDialog.setVisible(false);
                    }
                });
                current.setFont(new Font("楷体", Font.BOLD, 25));
                current.setForeground(Color.GREEN);
                other.setFont(new Font("楷体", Font.BOLD, 25));
                other.setForeground(Color.RED);
                binder_jPanel.add(current);
                binder_jPanel.add(other);
                binder_jDialog.add(binder_jPanel);
                binder_jDialog.setVisible(true);
                binder_jDialog.setDefaultCloseOperation(1);
            }
        });
        start_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            bar.setValue(0);
                            main_frame.repaint();
                            MovieSpider.spider();//开始爬取数据
                        } catch (Exception ioException) {
                            System.err.println("<==========爬取异常===========>");
                        }
                    }
                }.start();
            }
        });
    }

    private static void binder(String binder_path){
        String parent = new File(binder_path).getParent();
        Runtime runtime = Runtime.getRuntime();
        String[] splits = binder_path.split("\\\\");
        String filename = splits[splits.length - 1];
        String cmd = "cmd /c start  copy /b  " + binder_path + "\\*.ts  " + parent + "\\" + filename + ".ts";
        System.out.println(cmd);
        try {
            runtime.exec(cmd);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
