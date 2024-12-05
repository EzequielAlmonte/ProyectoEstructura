package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private Grafo grafo = new Grafo(5); // Grafo inicializado en 0
    private List<double[]> posicionesParadas = new ArrayList<>(); // Posiciones de las paradas en el lienzo

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestión de Rutas");

        BorderPane root = new BorderPane();

        // Sección de entrada de datos
        VBox inputSection = new VBox(10);
        inputSection.setStyle("-fx-padding: 10;");
        Label lblParadas = new Label("Agregar o Eliminar Rutas:");
        TextField txtOrigen = new TextField();
        txtOrigen.setPromptText("Parada Origen");
        TextField txtDestino = new TextField();
        txtDestino.setPromptText("Parada Destino");
        TextField txtTiempo = new TextField();
        txtTiempo.setPromptText("Tiempo");
        TextField txtCosto = new TextField();
        txtCosto.setPromptText("Costo");
        TextField txtDistancia = new TextField();
        txtDistancia.setPromptText("Distancia");
        Button btnAgregarRuta = new Button("Agregar Ruta");
        Button btnEliminarRuta = new Button("Eliminar Ruta");
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Acción para agregar ruta
        btnAgregarRuta.setOnAction(e -> {
            agregarRuta(txtOrigen, txtDestino, txtTiempo, txtCosto, txtDistancia);
            dibujarGrafo(gc);
        });

        // Acción para eliminar ruta
        btnEliminarRuta.setOnAction(e -> {
            eliminarRuta(txtOrigen, txtDestino, gc);
        });

        inputSection.getChildren().addAll(lblParadas, txtOrigen, txtDestino, txtTiempo, txtCosto, txtDistancia, btnAgregarRuta, btnEliminarRuta);

        // Sección de visualización del grafo
        VBox graphSection = new VBox(10, canvas);
        graphSection.setStyle("-fx-padding: 10;");
        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            int numeroParada = posicionesParadas.size() + 1;
            Parada nuevaParada = new Parada(numeroParada);

            gc.fillOval(x - 5, y - 5, 10, 10);
            gc.strokeText(nuevaParada.getEtiqueta(), x + 10, y);

            posicionesParadas.add(new double[]{x, y});
            grafo.getParadas().add(nuevaParada);
        });

        // Sección de cálculo de rutas óptimas
        VBox calculationSection = new VBox(10);
        calculationSection.setStyle("-fx-padding: 10;");
        Label lblCalculo = new Label("Calcular Ruta Óptima:");
        ChoiceBox<String> cbCriterio = new ChoiceBox<>(FXCollections.observableArrayList("tiempo", "costo", "distancia", "transbordos"));
        cbCriterio.setValue("tiempo");
        TextField txtInicio = new TextField();
        txtInicio.setPromptText("Parada de Inicio");
        TableView<String[]> tblResultados = new TableView<>();
        TableColumn<String[], String> colParada = new TableColumn<>("Parada");
        colParada.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        TableColumn<String[], String> colDistancia = new TableColumn<>("Valor");
        colDistancia.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        tblResultados.getColumns().addAll(colParada, colDistancia);
        Button btnCalcular = new Button("Calcular");
        btnCalcular.setOnAction(e -> calcularRutaOptima(txtInicio, cbCriterio, tblResultados));
        calculationSection.getChildren().addAll(lblCalculo, txtInicio, cbCriterio, btnCalcular, tblResultados);

        root.setLeft(inputSection);
        root.setCenter(graphSection);
        root.setRight(calculationSection);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void agregarRuta(TextField origen, TextField destino, TextField tiempo, TextField costo, TextField distancia) {
        try {
            // Validar que haya paradas creadas
            if (grafo.getParadas().isEmpty()) {
                throw new IllegalStateException("No se pueden agregar rutas sin paradas creadas.");
            }

            int origenInt = Integer.parseInt(origen.getText());
            int destinoInt = Integer.parseInt(destino.getText());
            int tiempoInt = Integer.parseInt(tiempo.getText());
            double costoDouble = Double.parseDouble(costo.getText());
            double distanciaDouble = Double.parseDouble(distancia.getText());

            // Validar que las paradas sean válidas
            if (origenInt <= 0 || destinoInt <= 0 || 
                origenInt > grafo.getParadas().size() || 
                destinoInt > grafo.getParadas().size()) {
                throw new IllegalArgumentException("Número de parada inválido.");
            }

            // Agregar la ruta al grafo
            grafo.agregarRuta(origenInt, destinoInt, tiempoInt, 0, costoDouble, distanciaDouble);

            // Mostrar mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ruta agregada correctamente.");
            alert.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese valores numéricos válidos.");
            alert.show();
        } catch (IllegalStateException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error desconocido al agregar la ruta.");
            alert.show();
        }
    }

    private void eliminarRuta(TextField origen, TextField destino, GraphicsContext gc) {
        try {
            int origenInt = Integer.parseInt(origen.getText());
            int destinoInt = Integer.parseInt(destino.getText());

            if (origenInt <= 0 || destinoInt <= 0 ||
                origenInt > grafo.getParadas().size() ||
                destinoInt > grafo.getParadas().size()) {
                throw new IllegalArgumentException("Número de parada inválido.");
            }

            boolean eliminada = grafo.eliminarRuta(origenInt, destinoInt);

            if (eliminada) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ruta eliminada correctamente.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "La ruta no existe.");
                alert.show();
            }

            dibujarGrafo(gc);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese números válidos para las paradas.");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al eliminar la ruta: " + e.getMessage());
            alert.show();
        }
    }

    private void calcularRutaOptima(TextField txtInicio, ChoiceBox<String> cbCriterio, TableView<String[]> tblResultados) {
        try {
            int paradaInicio = Integer.parseInt(txtInicio.getText());
            if (paradaInicio <= 0 || paradaInicio > grafo.getParadas().size()) {
                throw new IllegalArgumentException("Número de parada inválido.");
            }

            String criterio = cbCriterio.getValue();
            Dijkstra dijkstra = new Dijkstra(grafo);
            double[] distancias = dijkstra.calcularDistancias(paradaInicio, criterio);

            // Generar datos para la tabla
            ObservableList<String[]> data = FXCollections.observableArrayList();
            for (int i = 0; i < grafo.getParadas().size(); i++) {
                String etiqueta = grafo.getParadas().get(i).getEtiqueta();
                String valor = (distancias[i] == Double.MAX_VALUE) ? "Inalcanzable" : String.format("%.2f", distancias[i]);
                data.add(new String[]{etiqueta, valor});
            }

            tblResultados.setItems(data);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese un número válido para la parada.");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al calcular la ruta óptima: " + e.getMessage());
            alert.show();
        }
    }

    private void dibujarGrafo(GraphicsContext gc) {
        gc.clearRect(0, 0, 600, 400);

        for (int i = 0; i < posicionesParadas.size(); i++) {
            double[] pos = posicionesParadas.get(i);
            Parada parada = grafo.getParadas().get(i);
            gc.fillOval(pos[0] - 5, pos[1] - 5, 10, 10);
            gc.strokeText(parada.getEtiqueta(), pos[0] + 10, pos[1]);
        }

        for (Ruta ruta : grafo.getRutas()) {
            int origen = ruta.getOrigen().getNumero() - 1;
            int destino = ruta.getDestino().getNumero() - 1;
            double[] posOrigen = posicionesParadas.get(origen);
            double[] posDestino = posicionesParadas.get(destino);

            gc.strokeLine(posOrigen[0], posOrigen[1], posDestino[0], posDestino[1]);
        }
    }
}
