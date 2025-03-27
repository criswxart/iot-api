package com.tld.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest  
public class LogUtilTest {

	@Test
    void testLog() throws IOException {
        // Crear un ByteArrayOutputStream para capturar los logs
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();
        
        // Crear un StreamHandler y asignar el ByteArrayOutputStream a él
        Handler handler = new StreamHandler(logOutput, new SimpleFormatter());
        
        // Resetear el LogManager para evitar configuraciones previas
        LogManager.getLogManager().reset();
        
        Logger logger = Logger.getLogger(LogUtil.class.getName());
        logger.addHandler(handler);
        
        // Llamar al método log
        LogUtil.log(LogUtil.class, Level.INFO, "Test message");

        // Flushing the output to ensure the log content is captured
        handler.flush();

        // Verificar que el mensaje esperado está en la salida del log
        String logContents = logOutput.toString();
        assertTrue(logContents.contains("Test message"), "Log should contain the expected message.");
        
        // Limpiar el handler para no interferir con otras pruebas
        logger.removeHandler(handler);
    }
}