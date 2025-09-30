/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.grupo7.magenta;

import com.grupo7.magenta.views.JFrameCRUD;

/**
 *
 * @author diegocarvajalarias
 */
public class App {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrameCRUD frame = new JFrameCRUD();
            frame.setVisible(true);
        });
    }
}
