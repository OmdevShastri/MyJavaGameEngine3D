package engineTester;

import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
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

        RawModel model = loader.loadToVAO(vertices, textureCoord, indices);

        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.textureLoader("fire")));

        Entity entity = new Entity(staticModel, new Vector3f(-1,0,0),0,0,0,1);

        while (!Display.isCloseRequested()){
            //game logic
            entity.increasePosition(0.002f, 0,0);
            entity.increaseRotation(0,1,0);

            renderer.prepare();
            //render
            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();

        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
