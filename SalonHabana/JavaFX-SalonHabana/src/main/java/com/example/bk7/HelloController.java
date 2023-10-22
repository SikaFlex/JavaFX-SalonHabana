package com.example.bk7;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Popup;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {






    @FXML
    private Label NombreNotFound;
    @FXML
    private  Label NumNoEncontrado;
    @FXML
    private   ProgressBar progressBar;
    @FXML
    private RadioButton checkRedonda;
    @FXML
    private  RadioButton checkRectangular1;
    @FXML
    private  Label labelMesas;
    @FXML
    private Button enviarButton;
    @FXML
    private TextField Nombre;
    @FXML
    private TextField TelefonoLabel;
    @FXML
    private ComboBox<String> CCarta;
    @FXML
    private ComboBox<String> CEvento;
    @FXML
    private DatePicker fecha;
    @FXML
    private TextField nPersonas;
    @FXML
    private TextField nJorandasText; //input
    @FXML
    private Label NJornadas; //texto

    @FXML
    private Label habitaciones;
    @FXML
    private RadioButton checkSi;
    @FXML
    private RadioButton checkNo;



    //in
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CCarta.getItems().addAll("Bufé", "Carta", "Cita Con el chef", "No precisar");//añado las opciones al combobox
        CEvento.getItems().addAll("Congreso", "Banquete", "Jornada");
      //hago invisibles todos los componentes que no quiero que se muestren
        nJorandasText.setVisible(false);
        NJornadas.setVisible(false);
        habitaciones.setVisible(false);
        checkSi.setVisible(false);
        checkNo.setVisible(false);
        NumNoEncontrado.setVisible(false);
        labelMesas.setVisible(false);
        checkRectangular1.setVisible(false);
        checkRedonda.setVisible(false);
        NombreNotFound.setVisible(false);


    }


    @FXML
    private void enviar(ActionEvent event) {
        try{
           if (validator()){
               showBooking(generateBookingString());
           }
           else {
               System.out.println("Se ha producido un error en la reserva");
           }

        }catch (Exception e){
            System.err.println(e.getMessage());

        }



    }
    //genera el texto de la reserva
    private String generateBookingString () {
        Date date = new Date();
       String reserva = "ID reserva: "+date.getTime()+"\nNombre: "+Nombre.getText().toUpperCase()+"\n" + "Telefono: "+TelefonoLabel.getText()+"\n"+"Fecha: "+fecha.getValue()+"\n"+
                "Numero de personas: "+nPersonas.getText()+"\n"+"Tipo de carta: "+CCarta.getValue()+"\n"+ "Lugar del evento: "+CEvento.getValue()+"\n";

       switch (CEvento.getValue()){
           case "Congreso": reserva+="Numero de jornadas: "+nJorandasText.getText()+"\n";
           if (checkSi.isSelected()){reserva+="Dispone de habitacion: Si\n";}
           else{reserva+="Dispone de habitacion: No\n";}

               break;
           case "Banquete": if(checkRedonda.isSelected()){reserva+="Mesa: Redonda\n";}
               else{reserva+="Mesa: Rectangular\n";}

               break;
           case "Jornada":

               break;

       }

        reserva+="¡Muchas gracias esperemos que disfrute!";

        return reserva;



    }
    //valida que que todos los textfield han sido correctamente rellenados
    private Boolean validator () {

        boolean tlState = false, //telefono
                nState=false,   //Nombre
                nPState=false,  //Numero personas
                oKState = false,//Todo ok
                fState=false, //Fecha evento
                cState=false, //Tipo de carta
                eState=false,//Lugar evento state
                nJState=false;//numero de jornadas



        //Nombre:
        if (comprobarNombre(Nombre.getText())) {Nombre.setOpacity(0.99);nState=true;}
        else {Nombre.setOpacity(0.60);NombreNotFound.setVisible(true);alert(Nombre);}
        //Numero de telefono:
        if (comprobarNumeroTel(TelefonoLabel.getText())) {TelefonoLabel.setOpacity(0.99);tlState=true;}
        else {TelefonoLabel.setOpacity(0.60);NumNoEncontrado.setVisible(true);alert(TelefonoLabel);}
        //Fecha evento:
        if (fecha.getValue()!=null){fecha.setOpacity(0.99); fState=true; }
        else {fecha.setOpacity(0.60);}
        //Numero de personas
        if (comprobarNumero(nPersonas.getText())){nPersonas.setOpacity(0.99); nPState=true; }
        else {nPersonas.setOpacity(0.60);alert(nPersonas); }
        //Tipo de carta
        if (CCarta.getValue()!=null){CCarta.setOpacity(0.99);cState=true;}
        else {CCarta.setOpacity(0.60);}
        //Lugar del evento
        if (CEvento.getValue()!=null){CEvento.setOpacity(0.99);eState=true;}
        else {CEvento.setOpacity(0.60);}
        //numero de jornadas
        if(CEvento.getValue()=="Congreso") {
            if (comprobarNumero(nJorandasText.getText())) {
                nJorandasText.setOpacity(0.99);
                nJState = true;
            } else {
                nJorandasText.setOpacity(0.60);
                alert(nJorandasText);
                nJState=false;
            }
        }
        else {nJState=true;}
       if (tlState && nState && nPState && fState && cState && eState && nJState) {return oKState=true;}

       return oKState;
    }
//crea la alerta de que la aplicacion a sido un exito
    private void showBooking (String reserva) {
        Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
        popup.setTitle("¡La Reserva a sido un exito!");
        popup.setContentText(reserva);
        popup.show();

    }
    //Muestra alertas indicando al usuario donde esta el fallo y un posible porque
    private void alert (TextField objeto){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (objeto.getId()){
            case "Nombre": { alert.setTitle("Error Nombre de usuario");
                alert.setContentText("El nombre no esta bien introducido recuerde que solo se permiten caracteres alfanumericos y con un tamaño menor a 50 caracteres");
                alert.show();}
            break;
            case "TelefonoLabel": { alert.setTitle("Error Numero de telefono");
                alert.setContentText("El numero no de telefono no es valido porfavor introduzca uno de verdad. (Solo se permiten numero españoles [+34])");
                alert.show();}
            break;
            case "nPersonas": { alert.setTitle("Error Numero de personas");
                alert.setContentText("Recuerda que numero de personas es un campo obligatorio y no puede estar vacio. El maximo de personas que se admiten son 99 por reserva. " +
                        "\nSolo se permiten numeros del 1 al 99");
                alert.show();}
            break;
            case "nJorandasText": { alert.setTitle("Error Numero de jornadas");
                alert.setContentText("Recuerda que numero de jornadas es un campo obligatorio y no puede estar vacio. El maximo de jornadas que se admiten son 99 por reserva. "+
                        "\nSolo se permiten numeros del 1 al 99");
                alert.show();}
            break;

        }

    }
    //dependiendo lo que tenga seleccionado el combo box de evento PD:
    @FXML
    private void onChangeEventoCombo() {


        switch (CEvento.getValue()) {
            case "Congreso": {
                lookBanqueteElements(false);
                lookCongresoElements(true);
            }
            break;
            case "Banquete": {
                lookCongresoElements(false);
                lookBanqueteElements(true);

            }
            break;
            case "Jornada": {
                lookBanqueteElements(false);
                lookCongresoElements(false);
            }
        }

    }
    //muestra los elementos de congreso PD: estas 3 funciones se prodrian refactorizar y hacer una interfaz mejor
    private void lookCongresoElements(boolean state) {
        if (state) {
            nJorandasText.setVisible(true);
            NJornadas.setVisible(true);
            habitaciones.setVisible(true);
            checkSi.setVisible(true);
            checkNo.setVisible(true);

        } else {
            nJorandasText.setVisible(false);
            NJornadas.setVisible(false);
            habitaciones.setVisible(false);
            checkSi.setVisible(false);
            checkNo.setVisible(false);
        }
    }
    //muestra los elementos de banquete
    private void lookBanqueteElements(boolean state) {
        if (state) {
            labelMesas.setVisible(true);
            checkRectangular1.setVisible(true);
            checkRedonda.setVisible(true);

        } else {
            labelMesas.setVisible(false);
            checkRectangular1.setVisible(false);
            checkRedonda.setVisible(false);
        }
    }

    //comprueba que el nombre no tiene ni espacios ni numeros ni caracteres especiales
    @FXML
    private static Boolean comprobarNombre(String nombre) {
        if (nombre.isEmpty() || nombre.length() > 50) {return false;}
        for (int i = 0; i < nombre.length(); i++) {
            char letra = nombre.charAt(i);
            if (!Character.isLetter(letra) && letra != ' ') {
                return false;
            }
        }

        return true;
    }

    //comprobar que el numero contiene los requisitos que necesitamos y es de un formato valido
    private static Boolean comprobarNumeroTel(String numero) {
        Boolean is = false;
        if (!numero.isEmpty() && !numero.contains(" ") && numero.length() == 9) {
            char primerNumero = numero.charAt(0);
            if (primerNumero == '6' || primerNumero == '9' || primerNumero == '8' || primerNumero == '7') {
                for (int i = 0; i < numero.length(); i++) {
                    if (Character.isDigit(numero.charAt(i))) {
                        is = true;
                    } else {
                       return is = false;
                    }
                }
            }
        }
        return is;
    }
    //comprobar si ha metido todo numeros
    private Boolean comprobarNumero(String numero) {
        Boolean is = false;

        if (numero.isEmpty() || numero.contains(" ")) {return is = false;}


            for (int i = 0; i < numero.length(); i++) {
                if (Character.isDigit(numero.charAt(i))) {
                    is = true;
                } else {
                    return is = false;
                }
            }
        if (Integer.parseInt(numero)>=99){return is=false;}
        return is;
    }



    //funciones para evitar que puedas seleccionar dos
    @FXML
    private void onChangeMesaRed() {
        checkRectangular1.setSelected(false);
        checkRectangular1.setMouseTransparent(false);
        checkRedonda.setMouseTransparent(true);
    }

    @FXML
    private void onChangeMesaRect() {
        checkRedonda.setSelected(false);
        checkRedonda.setMouseTransparent(false);
        checkRectangular1.setMouseTransparent(true);
    }

    @FXML
    private void onChangeSi() {
        checkNo.setSelected(false);
        checkNo.setMouseTransparent(false);
        checkSi.setMouseTransparent(true);

    }

    @FXML
    private void onChangeNo() {
        checkSi.setSelected(false);
        checkSi.setMouseTransparent(false);
        checkNo.setMouseTransparent(true);

    }
    //funciones para restaurar el estado de los componentes que estan con  baja opacidad
    @FXML
    private void restartNumTextField() {
        TelefonoLabel.setOpacity(0.99);
        NumNoEncontrado.setVisible(false);
    }

    @FXML
    private void restartNombreText() {
        Nombre.setOpacity(0.99);
        NombreNotFound.setVisible(false);
    }
    @FXML
    private void restartNumPersonas() {
        nPersonas.setOpacity(0.99);
    }
    @FXML
    private void restartCalendar() {
        fecha.setOpacity(0.99);
    }

    @FXML
    private void restartnJorandasText() {
        nJorandasText.setOpacity(0.99);
    }
    @FXML
    private void restartnCCarta() {
        CCarta.setOpacity(0.99);
    }
    @FXML
    private void restartlugarevento() {
        CEvento.setOpacity(0.99);
    }



}