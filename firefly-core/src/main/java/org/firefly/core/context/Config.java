package org.firefly.core.context;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Setter
public class Config {

    private String displayTitle;
    private int windowWidth;
    private int windowHeight;

    private boolean glfwGLVSync;

    public Config(){
       Properties properties = new Properties();
        try {
            InputStream vInputStream = Config.class.getClassLoader().getResourceAsStream("firefly-config.properties");
            properties.load(vInputStream);
            vInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        windowWidth = Integer.valueOf(properties.getProperty("window.width"));
        windowHeight = Integer.valueOf(properties.getProperty("window.height"));
        displayTitle = properties.getProperty("display.title");

        if (properties.getProperty("glfw.vsync") != null){
            glfwGLVSync = Integer.valueOf(properties.getProperty("glfw.vsync")) == 1 ? true : false;
        }

    }
}
