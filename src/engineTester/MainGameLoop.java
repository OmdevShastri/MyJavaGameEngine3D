package engineTester;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

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
        float [] textureCoord = {
                0,0, //V0
                0,1, //V1
                1,1, //V2
                1,0 //V3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoord,indices);
        ModelTexture texture = new ModelTexture(loader.textureLoader("textuu"));
        TexturedModel texturedModel = new TexturedModel(model,texture);

        while (!Display.isCloseRequested()){
            renderer.prepare();
            //game logic
            //render
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();

        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
