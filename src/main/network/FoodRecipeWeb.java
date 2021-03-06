package network;

import gui.PersonalSettingsPanel;
import org.json.JSONException;
import org.json.JSONObject;
import ui.UserInterface;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

//*Code was adapted from stackoverflow//

public class FoodRecipeWeb {
    protected static JSONObject jsonObject;
    protected static String jsonData;


    //REQUIRES: user input
    //EFFECTS: read api url and obtain recipes from userinput prompts
    public static void readSource() throws IOException {

////        String url = "http://www.recipepuppy.com/api/";
//        String url = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet";

        System.out.println("Input an ingredient that the dish must have");
//       String ingredient = JOptionPane.showInputDialog("Input an ingredient that the dish must have");
        String ingredient = UserInterface.myObj.next();
        System.out.println("Input what kind of dish you want");
//        String dish = JOptionPane.showInputDialog("Input what kind of dish you want");
        String dish = UserInterface.myObj.next();
        String url = "http://www.recipepuppy.com/api/?i=" + ingredient + "&q=" + dish + "&p=1";
        URL theUrl = new URL(url);
        URLConnection urlc = theUrl.openConnection();



        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
                .getInputStream()));
        String l = null;
        while ((l = br.readLine()) != null) {
            jsonData = l;
        }
        br.close();
    }

    private static void parse() {
        try {
            FoodParser libParser = new FoodParser();
            libParser.parseLibrary(jsonData);
        } catch (JSONException e) {
            System.out.println("Error parsing JSON data");
            e.printStackTrace();
        }
    }


    public static void main() throws IOException, JSONException {
        readSource();
//        System.out.println(jsonData);
        FoodRecipeWeb.parse();

    }
}
