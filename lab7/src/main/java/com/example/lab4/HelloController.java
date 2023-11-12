package com.example.lab4;

import com.example.lab4.cipher.DES;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HelloController {

    @FXML
    private TextField minLength;

    @FXML
    private TextArea sourceArea;

    @FXML
    private TextArea encodedArea;

    @FXML
    private Label invalidKey;

    @FXML
    protected void onEncodeHandler() throws
            NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        invalidKey.setVisible(false);

        SecretKey key = DES.genKey(56, new SecureRandom());

        String text = sourceArea.getText();

        if(text.toCharArray().length < Integer.parseInt(minLength.getText()))
        {
            invalidKey.setText("Количество символов исходного текста меньше " + minLength.getText());
            invalidKey.setVisible(true);
            return;
        }

        byte[] encoded = DES.getEncoder().encode(sourceArea.getText().getBytes(StandardCharsets.US_ASCII), key);

        encodedArea.setText(Base64.getEncoder().encodeToString(encoded));
    }
}