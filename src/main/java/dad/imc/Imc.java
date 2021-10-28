package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Imc extends Application {

	private TextField pesoText;
	private TextField alturaText;
	private HBox pesaHBox;
	private HBox mideHBox;
	private HBox imcHBox;
	private Label pesoLabel;
	private Label kgLabel;
	private Label alturaLabel;
	private Label cmLabel;
	private Label resultadoLabel;
	private Label tipoPesoLabel;
	
	private DoubleProperty operando1 = new SimpleDoubleProperty(0);
	private DoubleProperty operando2 = new SimpleDoubleProperty(0);
	private DoubleProperty devuelve = new SimpleDoubleProperty(0);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		pesoLabel = new Label("Peso:");
		pesoText = new TextField();
		pesoText.setPrefColumnCount(5);
		pesoText.setAlignment(Pos.CENTER);
		kgLabel = new Label("kg");
		
		alturaLabel = new Label("Altura:");
		alturaText = new TextField();
		alturaText.setPrefColumnCount(5);
		alturaText.setAlignment(Pos.CENTER);
		cmLabel = new Label("cm");
	
		resultadoLabel = new Label("IMC: (peso*altura^2)");
		tipoPesoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
					
		VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);
		
		pesaHBox = new HBox(3);
		pesaHBox.setAlignment(Pos.CENTER);
		pesaHBox.getChildren().addAll(pesoLabel,pesoText,kgLabel);
		
		mideHBox = new HBox(3);
		mideHBox.setAlignment(Pos.CENTER);
		mideHBox.getChildren().addAll(alturaLabel,alturaText,cmLabel);
		
		imcHBox = new HBox();
		imcHBox.setSpacing(1);
		imcHBox.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(pesaHBox,mideHBox,imcHBox,resultadoLabel,tipoPesoLabel);
		
		Scene scene = new Scene(root, 300, 200);
	
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		operando1 = new SimpleDoubleProperty();
		Bindings.bindBidirectional(pesoText.textProperty(),operando1, new NumberStringConverter());
		
		operando2 = new SimpleDoubleProperty();
		Bindings.bindBidirectional(alturaText.textProperty(),operando2, new NumberStringConverter());
		
		DoubleBinding calculo=operando2.divide(100);
		calculo=operando1.divide(calculo.multiply(calculo));
		
		devuelve.bind(calculo);

		pesoLabel.textProperty().bind(Bindings.concat("Peso: ")
		.concat(Bindings.when(operando2.isEqualTo(0)).then("").otherwise(devuelve.asString("%.2f"))));
		
		devuelve.addListener((o, ov, nv)->{
			double vale=nv.doubleValue();
			if(vale < 18.5) {
				alturaLabel.setText("Bajo peso");
			}else if(vale >=18.5 && vale < 25) {
				alturaLabel.setText("Normal");
			}else if(vale >=25 && vale < 30) {
				alturaLabel.setText("Sobrepeso");
			}else {
				alturaLabel.setText("Obeso");
			}
		});
	}

	public static void main(String[] args) {
		launch(args);

	}

}
