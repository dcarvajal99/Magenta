/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.grupo7.magenta.views;

import com.grupo7.magenta.controller.PeliculaController;
import com.grupo7.magenta.models.Pelicula;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diegocarvajalarias
 */
public class JFrameCRUD extends javax.swing.JFrame {
    
    private PeliculaController peliculaController = new PeliculaController();
    private DefaultTableModel tableModel;
    private boolean idFieldEnabledOnce = false;

    // Lista original de películas para filtros
    private List<Pelicula> peliculasOriginales;

    private void clearFields(){
        txt_id.setText("");
        txt_tittle.setText("");
        txt_director.setText("");
        txt_year.setText("");
        txt_duration.setText("");
        cbx_gender.setSelectedIndex(0);
    }

    // Método público para limpiar campos (para el botón Limpiar)
    public void limpiarCampos() {
        clearFields();
        // Enfocar el primer campo
        txt_tittle.requestFocus();
    }

    // Método para validar campos numéricos en tiempo real
    private void configurarValidacionesEnTiempoReal() {
        // Limitar caracteres en campos de texto
        txt_tittle.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 150) {
                    super.insertString(offs, str, a);
                }
            }
        });

        txt_director.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (getLength() + str.length() <= 50) {
                    super.insertString(offs, str, a);
                }
            }
        });

        // Validar solo números en campos numéricos
        txt_year.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.matches("\\d*") && getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });

        txt_duration.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.matches("\\d*") && getLength() + str.length() <= 3) {
                    super.insertString(offs, str, a);
                }
            }
        });

        txt_id.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.matches("\\d*") && getLength() + str.length() <= 10) {
                    super.insertString(offs, str, a);
                }
            }
        });
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Título", "Director", "Año", "Duración (min)", "Género"};
        tableModel = new DefaultTableModel(columnas, 0);
        jTable2.setModel(tableModel);

        // Configurar ancho de columnas
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(200); // Título
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(150); // Director
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(80);  // Año
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(100); // Duración
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(120); // Género

        // Hacer la tabla no editable
        jTable2.setDefaultEditor(Object.class, null);

        // Cargar datos iniciales
        cargarTabla();
    }

    private void cargarTabla() {
        try {
            List<Pelicula> lista = peliculaController.listarPeliculas();
            // Actualizar también la lista original para filtros
            peliculasOriginales = new ArrayList<>(lista);

            tableModel.setRowCount(0);
            for (Pelicula p : lista) {
                tableModel.addRow(new Object[]{p.getId(), p.getTitulo(), p.getDirector(), p.getAnio(), p.getDuracion(), p.getGenero()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPeliculaEnFormulario(Pelicula pelicula) {
        if (pelicula != null) {
            txt_id.setText(String.valueOf(pelicula.getId()));
            txt_tittle.setText(pelicula.getTitulo());
            txt_director.setText(pelicula.getDirector());
            txt_year.setText(String.valueOf(pelicula.getAnio()));
            txt_duration.setText(String.valueOf(pelicula.getDuracion()));

            // Seleccionar el género en el combobox
            for (int i = 0; i < cbx_gender.getItemCount(); i++) {
                if (cbx_gender.getItemAt(i).equals(pelicula.getGenero())) {
                    cbx_gender.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * Creates new form NewJFrame
     */
    public JFrameCRUD() {
        initComponents();
        configurarTabla();
        configurarValidacionesEnTiempoReal();
        configurarFiltros(); // Agregar configuración de filtros
        txt_id.setEnabled(false); // Bloquea el campo ID al iniciar

        // Configurar tooltips para ayudar al usuario
        txt_tittle.setToolTipText("Ingrese el título de la película (máximo 150 caracteres)");
        txt_director.setToolTipText("Ingrese el nombre del director (máximo 50 caracteres)");
        txt_year.setToolTipText("Ingrese el año de estreno (entre 1900 y 2030)");
        txt_duration.setToolTipText("Ingrese la duración en minutos (entre 1 y 600)");
        txt_id.setToolTipText("ID de la película (deje vacío para asignación automática)");
        cbx_gender.setToolTipText("Seleccione el género de la película");

        jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable2.getSelectedRow() != -1) {
                int fila = jTable2.getSelectedRow();
                txt_id.setText(tableModel.getValueAt(fila, 0).toString());
                txt_tittle.setText(tableModel.getValueAt(fila, 1).toString());
                txt_director.setText(tableModel.getValueAt(fila, 2).toString());
                txt_year.setText(tableModel.getValueAt(fila, 3).toString());
                txt_duration.setText(tableModel.getValueAt(fila, 4).toString());
                String genero = tableModel.getValueAt(fila, 5).toString();
                for (int i = 0; i < cbx_gender.getItemCount(); i++) {
                    if (cbx_gender.getItemAt(i).equals(genero)) {
                        cbx_gender.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });
        txt_id.setEnabled(false); // El campo ID siempre bloqueado
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_tittle = new javax.swing.JTextField();
        txt_director = new javax.swing.JTextField();
        txt_year = new javax.swing.JTextField();
        txt_duration = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbx_gender = new javax.swing.JComboBox<>();
        btn_save = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btn_save2 = new javax.swing.JButton();
        btn_save3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_filter = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbx_filter_gender = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btn_cleanFilter = new javax.swing.JButton();
        txt_filterdate_2 = new javax.swing.JTextField();
        txt_filterdate_1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID:");

        jLabel2.setText("Año:");

        jLabel3.setText("Titulo:");

        jLabel4.setText("Director:");

        jLabel5.setText("Duracion:");

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 0, 48)); // NOI18N
        jLabel6.setText("Cine Magenta");

        jLabel7.setText("Género:");

        cbx_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecciona", "Comedia", "Drama", "Accion", "Suspenso", "Romance", "Ciencia Ficcion" }));
        cbx_gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_genderActionPerformed(evt);
            }
        });

        btn_save.setText("Guardar");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        btn_save2.setText("Actualizar");
        btn_save2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save2ActionPerformed(evt);
            }
        });

        btn_save3.setText("Eliminar");
        btn_save3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Gestion de Inventario de Peliculas - Sucursal Providencia");

        btn_clear.setText("Limpiar");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_filter.setText("Filtrar");
        btn_filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filterActionPerformed(evt);
            }
        });

        jLabel9.setText("Filtros:");

        jLabel10.setText("Genero:");

        cbx_filter_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_filter_gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_filter_genderActionPerformed(evt);
            }
        });

        jLabel11.setText("Fecha:");

        jLabel12.setText("Desde:");

        jLabel13.setText("Hasta:");

        btn_cleanFilter.setText("Borrar Filtros");
        btn_cleanFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanFilterActionPerformed(evt);
            }
        });

        txt_filterdate_1.setText("    ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_save2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222)
                .addComponent(btn_save3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(217, 217, 217))
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_year, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_director, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbx_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_duration, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(btn_clear)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_save))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_tittle, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(cbx_filter_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12))
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_filterdate_1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txt_filterdate_2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(btn_cleanFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(btn_filter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cbx_filter_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13)
                        .addComponent(txt_filterdate_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_filterdate_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_cleanFilter))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txt_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_buscar, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txt_tittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_director, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addComponent(txt_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txt_duration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbx_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_save)
                            .addComponent(btn_clear)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save2)
                    .addComponent(btn_save3))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        if (!idFieldEnabledOnce) {
            txt_id.setEnabled(true); // Habilita el campo ID solo la primera vez
            idFieldEnabledOnce = true;
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para buscar.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String idStr = txt_id.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para buscar.", "Buscar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Pelicula pelicula = peliculaController.buscarPelicula(idStr);
        if (pelicula != null) {
            cargarPeliculaEnFormulario(pelicula);
            JOptionPane.showMessageDialog(this, "ID encontrado.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "ID no encontrado.", "Buscar", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void cbx_genderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_genderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_genderActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        boolean resultado = peliculaController.guardarPelicula(
            txt_id.getText(),  // Ahora incluimos el ID para validación de duplicados
            txt_tittle.getText(),
            txt_director.getText(),
            txt_year.getText(),
            txt_duration.getText(),
            cbx_gender.getSelectedItem().toString()
        );

        if (resultado) {
            clearFields();
            cargarTabla(); // Recargar la tabla para mostrar la nueva película
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_save2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save2ActionPerformed
        String idStr = txt_id.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para actualizar.", "Actualizar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Pelicula pelicula = peliculaController.buscarPelicula(idStr);
        if (pelicula == null) {
            JOptionPane.showMessageDialog(this, "ID no encontrado. No se puede actualizar.", "Actualizar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmación previa antes de actualizar
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea actualizar la película '" + pelicula.getTitulo() + "'?\nLos datos actuales serán reemplazados.",
            "Confirmar actualización",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Solo proceder si el usuario confirma la actualización
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean resultado = peliculaController.actualizarPelicula(
                Integer.parseInt(idStr),
                txt_tittle.getText(),
                txt_director.getText(),
                txt_year.getText(),
                txt_duration.getText(),
                cbx_gender.getSelectedItem().toString()
            );
            if (resultado) {
                JOptionPane.showMessageDialog(this, "Película actualizada correctamente.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                cargarTabla();
            }
        }
        // Si el usuario cancela (NO_OPTION), no se hace nada

    }//GEN-LAST:event_btn_save2ActionPerformed

    private void btn_save3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save3ActionPerformed
        String idStr = txt_id.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID para eliminar.", "Eliminar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Pelicula pelicula = peliculaController.buscarPelicula(idStr);
            if (pelicula == null) {
                JOptionPane.showMessageDialog(this, "ID no encontrado. No se puede eliminar.", "Eliminar", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Confirmación previa antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar la película '" + pelicula.getTitulo() + "'?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            // Solo proceder si el usuario confirma la eliminación
            if (confirmacion == JOptionPane.YES_OPTION) {
                peliculaController.eliminarPelicula(id);
                JOptionPane.showMessageDialog(this, "Película eliminada correctamente.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                cargarTabla();
            }
            // Si el usuario cancela (NO_OPTION), no se hace nada

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_save3ActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filterActionPerformed
        aplicarFiltros();
    }//GEN-LAST:event_btn_filterActionPerformed

    private void btn_cleanFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanFilterActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_btn_cleanFilterActionPerformed

    private void cbx_filter_genderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_filter_genderActionPerformed
        // Filtrar automáticamente al cambiar género (opcional)
        // Uncomment the next line if you want auto-filter on genre change
        // aplicarFiltros();
    }//GEN-LAST:event_cbx_filter_genderActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameCRUD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_cleanFilter;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_filter;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_save2;
    private javax.swing.JButton btn_save3;
    private javax.swing.JComboBox<String> cbx_filter_gender;
    private javax.swing.JComboBox<String> cbx_gender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txt_director;
    private javax.swing.JTextField txt_duration;
    private javax.swing.JTextField txt_filterdate_1;
    private javax.swing.JTextField txt_filterdate_2;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_tittle;
    private javax.swing.JTextField txt_year;
    // End of variables declaration//GEN-END:variables

    // Método para configurar los filtros
    private void configurarFiltros() {
        // Configurar ComboBox de filtro de género
        cbx_filter_gender.removeAllItems();
        cbx_filter_gender.addItem("Todos");
        cbx_filter_gender.addItem("Comedia");
        cbx_filter_gender.addItem("Drama");
        cbx_filter_gender.addItem("Accion");
        cbx_filter_gender.addItem("Suspenso");
        cbx_filter_gender.addItem("Romance");
        cbx_filter_gender.addItem("Ciencia Ficcion");

        // Configurar validación solo números en campo "Desde"
        txt_filterdate_1.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.matches("\\d*") && getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });

        // Configurar validación solo números en campo "Hasta"
        txt_filterdate_2.setDocument(new javax.swing.text.PlainDocument() {
            @Override
            public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                if (str.matches("\\d*") && getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });

        // Configurar tooltips correctamente
        cbx_filter_gender.setToolTipText("Seleccione un género para filtrar");
        txt_filterdate_1.setToolTipText("Año desde (ej: 2000)");
        txt_filterdate_2.setToolTipText("Año hasta (ej: 2023)");
        btn_filter.setToolTipText("Aplicar filtros a la tabla");
        btn_cleanFilter.setToolTipText("Limpiar todos los filtros y mostrar todas las películas");
    }

    // Método para aplicar filtros
    private void aplicarFiltros() {
        try {
            // Si no hay películas originales, cargarlas
            if (peliculasOriginales == null) {
                peliculasOriginales = peliculaController.listarPeliculas();
            }

            List<Pelicula> peliculasFiltradas = new ArrayList<>(peliculasOriginales);

            // Filtro por género
            String generoSeleccionado = (String) cbx_filter_gender.getSelectedItem();
            if (generoSeleccionado != null && !generoSeleccionado.equals("Todos")) {
                peliculasFiltradas = peliculasFiltradas.stream()
                    .filter(pelicula -> pelicula.getGenero().equals(generoSeleccionado))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Filtro por rango de años
            String anioDesdeStr = txt_filterdate_1.getText().trim();
            String anioHastaStr = txt_filterdate_2.getText().trim();

            if (!anioDesdeStr.isEmpty() || !anioHastaStr.isEmpty()) {
                int anioDesde, anioHasta;

                if (anioDesdeStr.isEmpty() && !anioHastaStr.isEmpty()) {
                    anioDesde = 1900;
                    anioHasta = Integer.parseInt(anioHastaStr);
                } else if (!anioDesdeStr.isEmpty() && anioHastaStr.isEmpty()) {
                    anioDesde = Integer.parseInt(anioDesdeStr);
                    anioHasta = 2030;
                } else {
                    anioDesde = Integer.parseInt(anioDesdeStr);
                    anioHasta = Integer.parseInt(anioHastaStr);
                }

                if (!anioDesdeStr.isEmpty() && !anioHastaStr.isEmpty() && anioDesde > anioHasta) {
                    JOptionPane.showMessageDialog(this,
                        "El año 'Desde' (" + anioDesde + ") no puede ser mayor que el año 'Hasta' (" + anioHasta + ")",
                        "Error en filtros",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                peliculasFiltradas = peliculasFiltradas.stream()
                    .filter(pelicula -> pelicula.getAnio() >= anioDesde && pelicula.getAnio() <= anioHasta)
                    .collect(java.util.stream.Collectors.toList());
            }

            // Actualizar tabla con resultados filtrados
            actualizarTablaConFiltros(peliculasFiltradas);

            // Mostrar mensaje con resultados
            if (peliculasFiltradas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron películas que coincidan con los filtros aplicados.",
                    "Filtros aplicados",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Filtros aplicados correctamente. Se encontraron " + peliculasFiltradas.size() + " película(s).",
                    "Filtros aplicados",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese años válidos (solo números).",
                "Error en filtros",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al aplicar filtros: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar tabla con películas filtradas
    private void actualizarTablaConFiltros(List<Pelicula> peliculas) {
        tableModel.setRowCount(0);
        for (Pelicula p : peliculas) {
            tableModel.addRow(new Object[]{
                p.getId(),
                p.getTitulo(),
                p.getDirector(),
                p.getAnio(),
                p.getDuracion(),
                p.getGenero()
            });
        }
    }

    // Método para limpiar filtros
    private void limpiarFiltros() {
        cbx_filter_gender.setSelectedIndex(0); // "Todos"
        txt_filterdate_1.setText("");
        txt_filterdate_2.setText("");

        // Recargar tabla completa
        cargarTabla();

        JOptionPane.showMessageDialog(this,
            "Filtros limpiados. Mostrando todas las películas.",
            "Filtros",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
