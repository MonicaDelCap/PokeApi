package org.infantaelena.vista;

import org.infantaelena.modelo.entidades.Pokemon;

import javax.swing.*;

/**
 * Clase que representa la vista de la aplicación
 * Implementar con menus de texto o con interfaz gráfica
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */
public class Vista extends JFrame {

    private JPanel panel1;
    private JPanel panel0;
    private JPanel panelBase;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton anadirButton;
    private JButton eliminarButton;
    private JButton modificarButton;
    private JTable table1;
    private JButton buscarButton;
    private JTextArea textArea1;


    private JButton listarTodosButton;

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    private JButton limpiarButton;
    private JList<Pokemon> listaPokemons;

    public JButton getListarTodosButton() {
        return listarTodosButton;
    }

    public JList<Pokemon> getListaPokemons() {
        return listaPokemons;
    }

    public void setListaPokemons(JList<Pokemon> listaPokemons) {
        this.listaPokemons = listaPokemons;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public void setTextArea1(JTextArea textArea1) {
        this.textArea1 = textArea1;
    }

    public Vista() {
        JFrame frame = new JFrame("Vista");
        frame.setContentPane(panelBase);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900,650);
        frame.setVisible(true);
    }
    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JPanel getPanel0() {
        return panel0;
    }

    public void setPanel0(JPanel panel0) {
        this.panel0 = panel0;
    }

    public JPanel getPanelBase() {
        return panelBase;
    }

    public void setPanelBase(JPanel panelBase) {
        this.panelBase = panelBase;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public void setTextField4(JTextField textField4) {
        this.textField4 = textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public void setTextField5(JTextField textField5) {
        this.textField5 = textField5;
    }

    public JButton getAnadirButton() {
        return anadirButton;
    }

    public void setAnadirButton(JButton anadirButton) {
        this.anadirButton = anadirButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public void setEliminarButton(JButton eliminarButton) {
        this.eliminarButton = eliminarButton;
    }

    public JButton getModificarButton() {
        return modificarButton;
    }

    public void setModificarButton(JButton modificarButton) {
        this.modificarButton = modificarButton;
    }

    public JTable getTable1() {
        return table1;
    }

    public void setTable1(JTable table1) {
        this.table1 = table1;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public void setBuscarButton(JButton buscarButton) {
        this.buscarButton = buscarButton;
    }
    public void mostrarMensajeVentana(Exception e) {
        JFrame frame = new JFrame("Ventana de error");
        JOptionPane.showMessageDialog(frame, e.getMessage());
    }
    public void mostrarMensajeVentana(String m) {
        JFrame frame = new JFrame("");
        JOptionPane.showMessageDialog(frame, m);
    }

    public boolean confirmarBorrado(String nombre) {
        JFrame frame = new JFrame("Ventana de confirmación");
        frame.setSize(300, 200);

        String mensaje = "¿Está seguro de que desea borrar a "+nombre+"?";
        int respuesta = JOptionPane.showConfirmDialog(frame, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);

        return respuesta == JOptionPane.YES_OPTION;
    }

    public void limpiarCampos() {
        getTextField1().setText("");
        getTextField2().setText("");
        getTextField3().setText("");
        getTextField4().setText("");
        getTextField5().setText("");
    }

    public boolean hayCamposNull() {
        return textField1.getText().equals("") || textField2.getText().equals("") || textField3.getText().equals("") || textField4.getText().equals("") || textField5.getText().equals("");
    }

    public void rellenarCampos(Pokemon pokemon) {
        getTextField2().setText(pokemon.getTipo());
        getTextField3().setText(String.valueOf(pokemon.getNivel()));
        getTextField4().setText(String.valueOf(pokemon.getVida()));
        getTextField5().setText(String.valueOf(pokemon.getAtaque()));
    }

    public void limpiarTextArea() {
        getTextArea1().setText("");
    }

}
