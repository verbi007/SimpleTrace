package org.jumbo.simpletrace;

import com.microsoft.playwright.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private Pane settingsPane1;

    @FXML
    private Pane settingsPane2;

    @FXML
    private Label traceIDLabel1;

    @FXML
    private Label traceIDLabelBlanks;

    @FXML
    private TextField traceIdFieldBlanks;

    @FXML
    private TextField traceIdFieldCurl;

    @FXML
    private Pane traceIdPaneBlanks;


    protected ApiCatalog4 apiCatalog4;
    protected EnvType envType;

    protected String urlEndpoint = "";
    protected String traceId;
    protected String apiTypeTitle;


    protected List<String> traceIdList;

    @FXML
    void initialize() {
        apiCatalog4 = ApiFactory.getApiCatalog4(ApiType.FALSETEASER, EnvType.TEST);
        apiTypeTitle = Constants.FALSETEASER_TITLE;
        envType = EnvType.TEST;

        blanksLink.setDisable(true);
        blanksLink.getStyleClass().add("link_pressed");


        //Blanks
        blanksLink.setOnAction(event -> {
            blanksPane.setVisible(true);
            blanksLink.getStyleClass().add("link_pressed");
            curlLink.getStyleClass().remove("link_pressed");

            blanksLink.setDisable(true);
            curlLink.setDisable(false);

            curlPane.setVisible(false);

        });
        //CURL
        curlLink.setOnAction(event -> {
            curlPane.setVisible(true);
            curlLink.getStyleClass().add("link_pressed");
            blanksLink.getStyleClass().remove("link_pressed");


            curlLink.setDisable(true);
            blanksLink.setDisable(false);

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
                    envType == EnvType.TEST ? EnvType.TEST : EnvType.PROD
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
                    envType == EnvType.TEST ? EnvType.TEST : EnvType.PROD
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
            traceIdList = new ArrayList<>();
            urlEndpoint = apiCatalog4.getEndpoint().getUrl();

            if (!urlEndpoint.isEmpty()) {
                traceIdFieldBlanks.setText("");

                // Rest request
                for (int i = 0; i < 20; i++) {
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
                for (int i = 0; i < 20; i++) {
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


    }

    public void setDisabledRetryButtons() {
        retryButtonBlanks.setDisable(true);
        retryButtonCurl.setDisable(true);
    }

    public void removeAlerts(Label result, Pane resultPane) {
        result.setStyle("-fx-text-fill: rgb(255, 255, 255);");
        resultPane.setStyle("-fx-background-color: rgb(159, 159, 159);");
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
                    page.waitForSelector(".ErrorMessage--msg ", new Page.WaitForSelectorOptions().setTimeout(500)).isVisible();
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
