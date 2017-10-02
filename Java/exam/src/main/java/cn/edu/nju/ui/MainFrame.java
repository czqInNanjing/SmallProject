package cn.edu.nju.ui;

import cn.edu.nju.service.FileSearchService;
import cn.edu.nju.service.impl.FileSearchServiceImpl;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame implements ActionListener{

    // Variables declaration
    private JFrame mainFrame;
    private JPanel body;

    private JButton button;

    private FileSearchService searchService;

    private JButton searchBUtton;
    private JTextField commandField;
    private JTextArea output;


    // End of variables declaration

    public MainFrame() {

        componentsInstantiation();
        initComponents();
        mainFrame.setSize(1000, 800);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        searchService = new FileSearchServiceImpl();
    }

    // Instantiation of components
    private void componentsInstantiation() {
        mainFrame = new JFrame();
        body = new JPanel();
        body.setSize(1000, 800);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {
        mainFrame
                .setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);


        mainFrame.getContentPane().setLayout(null);

        button = new JButton("浏览");
        button.setLocation(100, 100);
        button.addActionListener(this);

        searchBUtton = new JButton("搜索");
        commandField = new JTextField();
        commandField.setLocation(300, 300);
        commandField.setSize(200, 300);
        commandField.setColumns(10);
        output = new JTextArea();
        output.setRows(30);
        output.setColumns(30);
        output.setLocation(300, 1000);
        output.setSize(1000, 600);

        searchBUtton.setVisible(false);
        commandField.setVisible(false);
        output.setVisible(false);


        searchBUtton.addActionListener(this);

        body.add(button);
        body.add(searchBUtton);
        body.add(commandField);
        body.add(output);
        mainFrame.getContentPane().add(body);


        mainFrame.setTitle("Searcher");




    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button) {
            JFileChooser chooser = new JFileChooser();
//            FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                    "JPG & GIF Images", "jpg", "gif");
//            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this.mainFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getCurrentDirectory().getAbsolutePath() + "/";
                path += chooser.getSelectedFile().getName();
                searchService.setFilePath(path);
//                System.out.println("You chose to open this file: " +
//                        chooser.getSelectedFile().getName());

                button.setVisible(false);
                searchBUtton.setVisible(true);
                commandField.setVisible(true);
                output.setVisible(true);
            }



        } else if (e.getSource() == this.searchBUtton) {
            String command = commandField.getText();
            java.util.List<String> result = searchService.search(command);
            output.setLineWrap(true);
            for (String str : result) {
                output.append(str);
                output.append("\n");

            }


        }
    }
}



