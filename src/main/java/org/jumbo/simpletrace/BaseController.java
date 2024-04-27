package org.jumbo.simpletrace;

import com.microsoft.playwright.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.jumbo.simpletrace.configuration.RestPlaywright;
import org.jumbo.simpletrace.configuration.api.ApiCatalog4;
import org.jumbo.simpletrace.configuration.api.ApiFactory;
import org.jumbo.simpletrace.configuration.api.ApiType;
import org.jumbo.simpletrace.configuration.api.EnvType;
import org.jumbo.simpletrace.constants.Constants;
import org.jumbo.simpletrace.curlparser.CurlToClass;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;


public class BaseController {

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private ComboBox<String> apiTypeCombobox;

    @FXML
    private Button applyButtonBlanks;

    @FXML
    private Button applyButtonCurl;

    @FXML
    private Hyperlink blanksLink;

    @FXML
    private Pane blanksPane;

    @FXML
    private Button cancelButtonSettings;

    @FXML
    private Button clearButtonBlanks;

    @FXML
    private Button clearButtonCurl;

    @FXML
    private Button copyButtonBlanks;

    @FXML
    private Button copyButtonCurl;

    @FXML
    private TextArea curlField;

    @FXML
    private Label curlLabel;

    @FXML
    private Hyperlink curlLink;

    @FXML
    private Pane curlPane;

    @FXML
    private Label enterURLLabel;

    @FXML
    private ComboBox<String> envTypeComboBox;

    @FXML
    private TextField numberFieldSettings;

    @FXML
    private Label numberLabelSettings;

    @FXML
    private Button openButtonBlanks;

    @FXML
    private Button openButtonCurl;

    @FXML
    private RadioButton radioButtonDark;

    @FXML
    private RadioButton radioButtonLight;

    @FXML
    private Label resultLabelBlanks;

    @FXML
    private Label resultLabelCurl;

    @FXML
    private Pane resultPaneBlanks;

    @FXML
    private Pane resultPaneCurl;

    @FXML
    private Button retryButtonBlanks;

    @FXML
    private Button retryButtonCurl;

    @FXML
    private Button saveButtonSettings;

    @FXML
    private Pane settingsContainer;

    @FXML
    private Pane settingsPane;

    @FXML
    private Pane settingsPane1;

    @FXML
    private Pane settingsPane2;

    @FXML
    private Hyperlink settingslLink;

    @FXML
    private Label themeLabel;

    @FXML
    private TextField tokenFieldSettings;

    @FXML
    private Label tokenLabelSettings;

    @FXML
    private Label traceIDLabel1;

    @FXML
    private Label traceIDLabel11;

    @FXML
    private TextField traceIdFieldBlanks;

    @FXML
    private TextField traceIdFieldCurl;

    @FXML
    private TextField repeatsFieldSettings;


    protected ApiCatalog4 apiCatalog4;
    protected EnvType envType;

    protected String urlEndpoint = "";
    protected String traceId;
    protected String apiTypeTitle;
    protected String number;
    protected String token;
    protected int repeats;


    protected List<String> traceIdList;

    @FXML
    void initialize() {
//        apiCatalog4 = ApiFactory.getApiCatalog4(ApiType.FALSETEASER, EnvType.TEST, number, token);
        repeats = Constants.REPEATS;
        apiTypeTitle = Constants.FALSETEASER_TITLE;
        envType = EnvType.TEST;

        blanksLink.setDisable(true);
        blanksLink.getStyleClass().add("link_pressed");

        //Number
        if (numberFieldSettings.getText() != null || !numberFieldSettings.getText().isEmpty()) {
            number = numberFieldSettings.getText();
        } else {
            number = Constants.NUMBER;
        }

        //Token
        if (tokenFieldSettings.getText() != null || !tokenFieldSettings.getText().isEmpty()) {
            token = tokenFieldSettings.getText();
        } else {
            token = Constants.TOKEN;
        }


        //Blanks
        blanksLink.setOnAction(event -> {
            blanksPane.setVisible(true);
            blanksLink.getStyleClass().add("link_pressed");

            settingslLink.getStyleClass().remove("link_pressed");
            curlLink.getStyleClass().remove("link_pressed");

            blanksLink.setDisable(true);
            curlLink.setDisable(false);
            settingslLink.setDisable(false);

            curlPane.setVisible(false);
            settingsPane.setVisible(false);

        });
        //CURL
        curlLink.setOnAction(event -> {
            curlPane.setVisible(true);
            curlLink.getStyleClass().add("link_pressed");

            settingslLink.getStyleClass().remove("link_pressed");
            blanksLink.getStyleClass().remove("link_pressed");


            curlLink.setDisable(true);
            blanksLink.setDisable(false);
            settingslLink.setDisable(false);

            blanksPane.setVisible(false);
            settingsPane.setVisible(false);
        });

        //Settings
        settingslLink.setOnAction(event -> {
            settingsPane.setVisible(true);
            settingslLink.getStyleClass().add("link_pressed");

            curlLink.getStyleClass().remove("link_pressed");
            blanksLink.getStyleClass().remove("link_pressed");


            settingslLink.setDisable(true);
            curlLink.setDisable(false);
            blanksLink.setDisable(false);

            curlPane.setVisible(false);
            blanksPane.setVisible(false);
        });


        //EnvType
        ObservableList<String> envList = FXCollections.observableArrayList(
                EnvType.TEST.toString(),
                EnvType.PROD.toString()
        );
        envTypeComboBox.setItems(envList);

        envTypeComboBox.setOnAction(even -> {
            envType = EnvType.valueOf(envTypeComboBox.getValue());
            apiCatalog4 = ApiFactory.getApiCatalog4(
                    ApiType.valueOf(apiTypeTitle),
                    envType == EnvType.TEST ? EnvType.TEST : EnvType.PROD,
                    number,
                    token
            );
//            if (!traceIdFieldBlanks.getText().isEmpty()) {
//                retryButtonBlanks.setDisable(true);
//                copyButtonBlanks.setDisable(false);
//            }
            removeAlerts(resultLabelBlanks, resultPaneBlanks);
            resultLabelBlanks.setText(Constants.RESULT_BLANKS_TEXT);
        });

        //ApiType
        ObservableList<String> apiList = FXCollections.observableArrayList(
                ApiType.FALSETEASER.toString(),
                ApiType.CATEGORIES.toString(),
                ApiType.SET.toString(),
                ApiType.PRODUCT.toString(),
                ApiType.PRODUCT_PROPERTIES.toString(),
                ApiType.SHOPPING_LIST.toString(),
                ApiType.TREE_AVAILABLE.toString(),
                ApiType.BFF_GET_SCREEN_WIDGETS.toString()
        );
        apiTypeCombobox.setItems(apiList);

        apiTypeCombobox.setOnAction(even -> {
            apiTypeTitle = apiTypeCombobox.getValue();
            ApiType apiType = ApiType.valueOf(apiTypeTitle);
            apiCatalog4 = ApiFactory.getApiCatalog4(
                    apiType,
                    envType == EnvType.TEST ? EnvType.TEST : EnvType.PROD,
                    number,
                    token
            );

//            if (!traceIdFieldBlanks.getText().isEmpty()) {
//                retryButtonBlanks.setDisable(true);
//                copyButtonBlanks.setDisable(false);
//            }
            removeAlerts(resultLabelBlanks, resultPaneBlanks);
            resultLabelBlanks.setText(Constants.RESULT_BLANKS_TEXT);
        });


        //Actions 'ApplyButtonBlanks'
        applyButtonBlanks.setOnAction(event -> {
            apiCatalog4 = ApiFactory.getApiCatalog4(ApiType.FALSETEASER, EnvType.TEST, number, token);
            traceIdList = new ArrayList<>();
            urlEndpoint = apiCatalog4.getEndpoint().getUrl();

            if (!urlEndpoint.isEmpty()) {
                traceIdFieldBlanks.setText("");

                // Rest request
                for (int i = 0; i < repeats; i++) {
                    traceIdList.add(RestPlaywright.getTraceId(apiCatalog4.getEndpoint()));
                }

                //Check url
                checkingTheTrace(traceIdFieldBlanks, resultLabelBlanks, resultPaneBlanks);
                retryButtonBlanks.setDisable(false);
                if (!traceIdFieldBlanks.getText().isEmpty()) clearButtonBlanks.setDisable(false);
            }
        });

        //Actions 'ApplyButtonCurl'
        applyButtonCurl.setOnAction(event -> {
            try {
                apiCatalog4.setEndpoint(CurlToClass.getCurlEndpoint(curlField.getText()));
                traceIdFieldCurl.setText("");
                removeAlerts(resultLabelCurl, resultPaneCurl);

                traceIdList = new ArrayList<>();

                // Rest request
                for (int i = 0; i < repeats; i++) {
                    traceIdList.add(RestPlaywright.getTraceId(apiCatalog4.getEndpoint()));
                }

                //Check url
                checkingTheTrace(traceIdFieldCurl, resultLabelCurl,resultPaneCurl);
                retryButtonCurl.setDisable(false);
                if (!traceIdFieldCurl.getText().isEmpty() || !curlField.getText().isEmpty()) {
                    clearButtonCurl.setDisable(false);
                    copyButtonCurl.setDisable(false);
                }

            } catch (RuntimeException e){
                resultLabelCurl.setText("You have not entered curl");
            }

        });



        //RetryButtonBlanks
        retryButtonBlanks.setOnAction(event -> {
            if (traceIdFieldBlanks.getText().isEmpty()) removeAlerts(resultLabelBlanks, resultPaneBlanks);

            //Check url
            checkingTheTrace(traceIdFieldBlanks, resultLabelBlanks, resultPaneBlanks);
//            if (!traceIdFieldBlanks.getText().isEmpty()) {
//                copyButtonBlanks.setDisable(false);
//                clearButtonBlanks.setDisable(false);
//            }

        });

        //RetryButtonCurl
        retryButtonCurl.setOnAction(event -> {
            removeAlerts(resultLabelCurl, resultPaneCurl);
            removeTraceIdField(traceIdFieldCurl);

            //Check url
            checkingTheTrace(traceIdFieldCurl, resultLabelCurl, resultPaneCurl);
//            if (!traceIdFieldCurl.getText().isEmpty()) copyButtonCurl.setDisable(true);

        });


        //CopyButtonBlanks
        copyButtonBlanks.setOnAction(event -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            clipboard.setContents(
                    new StringSelection(traceId),
                    null
            );
        });

        //CopyButtonCurl
        copyButtonCurl.setOnAction(event -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            clipboard.setContents(
                    new StringSelection(traceId),
                    null
            );
        });


        //ClearButtonBlanks
        clearButtonBlanks.setOnAction(event -> {
            removeAlerts(resultLabelBlanks, resultPaneBlanks);
            removeTraceIdField(traceIdFieldBlanks);
            resultLabelBlanks.setText(Constants.RESULT_BLANKS_TEXT);

            retryButtonBlanks.setDisable(true);
            clearButtonBlanks.setDisable(true);
//            copyButtonBlanks.setDisable(true);

        });

        //ClearButtonCurl
        clearButtonCurl.setOnAction(event -> {
            removeAlerts(resultLabelCurl, resultPaneCurl);
            removeTraceIdField(traceIdFieldCurl);
            resultLabelCurl.setText(Constants.RESULT_CURL_TEXT);
            curlField.setText("");

            retryButtonCurl.setDisable(true);
            clearButtonCurl.setDisable(true);
//            copyButtonCurl.setDisable(true);
        });

        //OpenButtonBlanks
        openButtonBlanks.setOnAction(event -> {
            String trace = "";
            if (traceIdFieldBlanks.getText() != "" || !traceIdFieldBlanks.getText().isEmpty()) {
                trace = traceIdFieldBlanks.getText();
                Playwright playwright = Playwright.create();
                Browser browser = playwright
                        .chromium()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(false));
                Page page = browser.newPage();

                page.navigate(Constants.JAEGER_URL + trace);
            }
        });

        //OpenButtonCurl
        openButtonCurl.setOnAction(event -> {
            String trace = "";
            if (traceIdFieldCurl.getText() != null || !traceIdFieldCurl.getText().isEmpty()) {
                trace = traceIdFieldCurl.getText();
                Playwright playwright = Playwright.create();
                Browser browser = playwright
                        .chromium()
                        .launch(new BrowserType.LaunchOptions()
                                .setHeadless(false));
                Page page = browser.newPage();

                page.navigate(Constants.JAEGER_URL + trace);
            }
        });

        //SaveButtonSettings
        saveButtonSettings.setOnAction(event -> {
            if (!numberFieldSettings.getText().isEmpty()) {
                number = numberFieldSettings.getText();
                token = tokenFieldSettings.getText();
            }
            if (!repeatsFieldSettings.getText().isEmpty()) {
                try {
                    repeats = Integer.parseInt(repeatsFieldSettings.getText());
                } catch (NumberFormatException e) {
                    e.getMessage();
                }
            }

        });

        radioButtonDark = new RadioButton();
        radioButtonLight = new RadioButton();

        ToggleGroup themeToggleGroup = new ToggleGroup();
        radioButtonDark.setToggleGroup(themeToggleGroup);
        radioButtonLight.setToggleGroup(themeToggleGroup);


//        radioButtonLight.setOnAction(event -> {
//            mainContainer.getStylesheets().add("style-light.css");
//            mainContainer.getStylesheets().remove("style-black.css");
//        });
//
//        radioButtonDark.setOnAction(event -> {
//            mainContainer.getStylesheets().add("style-black.css");
//            mainContainer.getStylesheets().remove("style-light.css");
//        });

        themeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldValue, Toggle newValue){

                // получаем выбранный элемент RadioButton
                saveButtonSettings = (Button) newValue;
                if (saveButtonSettings.getText().equals("Light")) mainContainer.getStylesheets().remove("style-light.css");
            }
        });


    }

    public void setDisabledRetryButtons() {
        retryButtonBlanks.setDisable(true);
        retryButtonCurl.setDisable(true);
    }

    public void removeAlerts(Label result, Pane resultPane) {
        result.setStyle("-fx-text-fill: rgb(255, 255, 255);");
        resultPane.setStyle("-fx-background-color: rgb(44, 44, 44);");
        result.setText("");
    }

    public void getSuccessAlert(Label result, Pane resultPane) {
        result.setStyle("-fx-text-fill: rgb(80,200,120);");
        resultPane.setStyle("-fx-background-color: rgb(173, 235, 173);");
        result.setText("SUCCESS!!!");
    }

    public void getErrorAlert(Label result, Pane resultPane) {
        result.setStyle("-fx-text-fill: rgb(255,255,255);");
        resultPane.setStyle("-fx-background-color: rgb(250, 90, 90);");
        result.setText("NO TRACE FOUND");
    }

    public void getTraceIdField(String traceId, TextField traceIdField) {
        traceIdField.setStyle("-fx-background-color: rgb(173, 235, 173);");

        traceIdField.setText(traceId);
    }

    public void removeTraceIdField(TextField traceIdField) {
        traceIdField.setStyle("-fx-background-color: rgb(209, 209, 209);");
        traceIdField.setText("");
    }

    private void checkingTheTrace(TextField traceIdField,Label resultLabel, Pane resultPane) {
        if (traceIdList != null || !traceIdList.isEmpty()) {

            Playwright playwright = Playwright.create();
            Browser browser = playwright
                    .chromium()
                    .launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            Page page = browser.newPage();


            for (int i = 0; i < traceIdList.size(); i++) {
                page.navigate(Constants.JAEGER_URL + traceIdList.get(i));
                try {
                    page.waitForSelector(".ErrorMessage--msg ", new Page.WaitForSelectorOptions().setTimeout(900)).isVisible();
                } catch (TimeoutError e) {
                    traceId = traceIdList.get(i);

                    getTraceIdField(traceId, traceIdField);
                    getSuccessAlert(resultLabel, resultPane);

                    page.close();
                    break;
                }

                if (traceIdList.size() == i+1) {
                    getErrorAlert(resultLabel, resultPane);
                }
            }
            page.close();
        } else {
            getErrorAlert(resultLabel, resultPane);
        }
    }


}
