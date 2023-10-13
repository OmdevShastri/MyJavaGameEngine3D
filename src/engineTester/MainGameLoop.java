package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float [] vertices = {
                //left bottom triANGLE
            -0.5f, 0.5f, 0f,
                    -0.5f, -0.5f, 0f,

                //right top triangle

                    0.5f, -0.5f, 0f,
                    0.5f, 0.5f, 0f
        };

        int [] indices = {0,1,3 // top triangle
                ,3,1,2}; // bottom triangle

        RawModel model = loader.loadToVAO(vertices, indices);

        while (!Display.isCloseRequested()){
            renderer.prepare();
            //game logic
            //render
            shader.start();
            renderer.render(model);
            shader.stop();
            DisplayManager.updateDisplay();

        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
