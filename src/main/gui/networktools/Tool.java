package gui.networktools;

import gui.NetworkPanel;
import network.FoodParser;
import org.json.JSONException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Tool implements ActionListener {

    protected static NetworkPanel networkPanel;
    protected JButton button;
    static final ClassLoader loader = Tool.class.getClassLoader();


    public Tool(NetworkPanel panel, JComponent parent) throws IOException {
        networkPanel = panel;
        createButton();
        addToParent(parent);
        button.addActionListener(this);
    }

    private void addToParent(JComponent parent) {
        parent.add(button);
    }

    protected void createButton() throws IOException {
        ImageIcon icon = createImageIcon("recipe.jpg");
        button = new JButton("Find new recipes");
        button.setIcon((Icon) icon);
    }


    public void readSource() throws IOException {
////        String url = "http://www.recipepuppy.com/api/";
//        String url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet";

        String ingredient = JOptionPane.showInputDialog("Input an ingredient that the dish must have");
        String dish = JOptionPane.showInputDialog("Input what kind of dish you want");
        String url = "http://www.recipepuppy.com/api/?i=" + ingredient + "&q=" + dish + "&p=1";
        URL theUrl = new URL(url);
        URLConnection urlc = theUrl.openConnection();

        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
                .getInputStream()));
        String l = null;
        while ((l = br.readLine()) != null) {
            networkPanel.setJsonData(l);
        }
        br.close();
    }

    private void parse() {
        try {
            FoodParserGUI libParser = new FoodParserGUI();
            libParser.parseLibrary(networkPanel.getJsonData());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON data");
            e.printStackTrace();
        }
    }

    public void main() throws IOException, JSONException {
        readSource();
        parse();
    }

    protected static ImageIcon createImageIcon(String path) {
//        java.net.URL imgURL = Tool.class.getResource(path);
        java.net.URL imgURL = loader.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            main();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
