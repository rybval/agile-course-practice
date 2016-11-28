package ru.unn.agile.vector3d.view;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.vector3d.viewmodel.ViewModel;
import ru.unn.agile.vector3d.viewmodel.ViewModel.OperationTab;

public class Controller {
    @FXML
    private TabPane opsTabPane;
    @FXML
    private Tab normTab;
    @FXML
    private Tab normalizationTab;
    @FXML
    private Tab dotProductTab;
    @FXML
    private Tab crossProductTab;
    @FXML
    private TextField vectorTextField;
    @FXML
    private TextField normResultTextField;
    @FXML
    private TextField normalizationResultTextField;
    @FXML
    private TextField dotProductOperandTextField;
    @FXML
    private TextField dotProductResultTextField;
    @FXML
    private TextField crossProductOperandTextField;
    @FXML
    private TextField crossProductResultTextField;
    @FXML
    private Button calculateButton;

    private final ViewModel viewModel = new ViewModel();

    private void bind() {
        normResultTextField.setText(viewModel.getNormResultText());
        normalizationResultTextField.setText(viewModel.getNormalizationResultText());
        dotProductResultTextField.setText(viewModel.getDotProductResultText());
        crossProductResultTextField.setText(viewModel.getCrossProductResultText());
        calculateButton.setDisable(!viewModel.isButtonEnabled());
    }

    private void backbind() {
        viewModel.setVectorText(vectorTextField.getText());
        viewModel.setDotProductOperandText(dotProductOperandTextField.getText());
        viewModel.setCrossProductOperandText(crossProductOperandTextField.getText());
        Tab activeTab = opsTabPane.getSelectionModel().getSelectedItem();
        if (activeTab.equals(normTab)) {
            viewModel.setActiveTab(OperationTab.NORM);
        } else if (activeTab.equals(normalizationTab)) {
            viewModel.setActiveTab(OperationTab.NORMALIZATION);
        } else if (activeTab.equals(dotProductTab)) {
            viewModel.setActiveTab(OperationTab.DOTPRODUCT);
        } else if (activeTab.equals(crossProductTab)) {
            viewModel.setActiveTab(OperationTab.CROSSPRODUCT);
        }
    }

    @FXML
    void initialize() {
        bind();

        calculateButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    viewModel.calculate();
                    bind();
                }
            }
        );

        opsTabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(final ObservableValue<? extends Tab> ov,
                                    final Tab oldTab, final Tab newTab) {
                    backbind();
                    bind();
                }
            }
        );

        ChangeListener<String> textFieldListener = new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String oldText, final String newText) {
                backbind();
                bind();
            }
        };

        vectorTextField.textProperty().addListener(textFieldListener);
        dotProductOperandTextField.textProperty().addListener(textFieldListener);
        crossProductOperandTextField.textProperty().addListener(textFieldListener);
    }
}
