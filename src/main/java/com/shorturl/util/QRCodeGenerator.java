package com.shorturl.util;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator
{
    public static String generateQRCode(String text)
    {
        try
        {
            String filePath = "qr.png";

            QRCodeWriter qrCodeWriter =
                    new QRCodeWriter();

            BitMatrix bitMatrix =
                    qrCodeWriter.encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            250,
                            250);

            MatrixToImageWriter.writeToPath(
                    bitMatrix,
                    "PNG",
                    Paths.get(filePath));

            return filePath;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}