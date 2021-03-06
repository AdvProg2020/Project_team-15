package Client.ViewController.seller;

import Client.ViewController.Controller;
import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeaturesPopUpController extends Controller implements Initializable {

    public TextField featuresText;
    public Label featuresLabel;
    private String category;

    private int i;
    private ArrayList<String> featuresNames;
    private ArrayList<Feature> allFeatures;
    private int featuresNumbers;
    private ManageProductsController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFeatures = new ArrayList<>();
        i = -1;
        featuresLabel.setText("Enter category:");
    }

    public void setController(ManageProductsController controller) {
        this.controller = controller;
    }

    public void next(ActionEvent actionEvent) {
        if (i == -1) {
            String categoryName = featuresText.getText();
            category = categoryName;
            Category category = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY " + categoryName), Category.class);
            if (category == null) {
                error("Wrong category name.");
                return;
            }
            featuresNames = category.getFeaturesNames();
            featuresNumbers = featuresNames.size();
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        } else if (i == featuresNumbers - 1) {
            allFeatures.add(new Feature(featuresLabel.getText(), featuresText.getText()));
            featuresText.clear();
            controller.addProduct(allFeatures, category);
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        } else {
            allFeatures.add(new Feature(featuresNames.get(i), featuresText.getText()));
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        }
    }
}
