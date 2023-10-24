package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Text;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
//        StaticShader shader = new StaticShader();
//        Renderer renderer = new Renderer(shader);

//        float[] vertices = {
//                -0.5f,0.5f,0,
//                -0.5f,-0.5f,0,
//                0.5f,-0.5f,0,
//                0.5f,0.5f,0,
//
//                -0.5f,0.5f,1,
//                -0.5f,-0.5f,1,
//                0.5f,-0.5f,1,
//                0.5f,0.5f,1,
//
//                0.5f,0.5f,0,
//                0.5f,-0.5f,0,
//                0.5f,-0.5f,1,
//                0.5f,0.5f,1,
//
//                -0.5f,0.5f,0,
//                -0.5f,-0.5f,0,
//                -0.5f,-0.5f,1,
//                -0.5f,0.5f,1,
//
//                -0.5f,0.5f,1,
//                -0.5f,0.5f,0,
//                0.5f,0.5f,0,
//                0.5f,0.5f,1,
//
//                -0.5f,-0.5f,1,
//                -0.5f,-0.5f,0,
//                0.5f,-0.5f,0,
//                0.5f,-0.5f,1
//
//        };
//
//        float[] textureCoords = {
//
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0
//
//
//        };
//
//        int[] indices = {
//                0,1,3,
//                3,1,2,
//                4,5,7,
//                7,5,6,
//                8,9,11,
//                11,9,10,
//                12,13,15,
//                15,13,14,
//                16,17,19,
//                19,17,18,
//                20,21,23,
//                23,21,22
//
//        };

        RawModel model = OBJLoader.loadObjModel("cube",loader);

//        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.textureLoader("white128")));
//        ModelTexture texture = staticModel.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(0.2f);

        //Entity entity = new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
        Light light = new Light(new Vector3f(200,200,100), new Vector3f(1,1,1));

        TexturedModel cubeModel = new TexturedModel(model, new ModelTexture(loader.textureLoader("white128")));
        ModelTexture texture = cubeModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Camera camera = new Camera();

        List<Entity> allCubes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            float x = random.nextFloat() *100-50;
            float y = random.nextFloat() *100-50;
            float z = random.nextFloat() *-300;
            allCubes.add(new Entity(cubeModel, new Vector3f(x,y,z), random.nextFloat()* 180f, random.nextFloat()* 100f, 0f,1f ));

        }

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()){
            //game logic
            //entity.increaseRotation(0, 0.5f,0);
            camera.move();
            //entity.increaseRotation(0,1,0);

            //renderer.prepare();
            //render
//            shader.start();
//            shader.loadLight(light);
//            shader.loadViewMatrix(camera);
//            renderer.render(entity, shader);
//            shader.stop();
            for (Entity cube :
                    allCubes) {
                renderer.processEntity(cube);
            }
            renderer.render(light,camera);
            DisplayManager.updateDisplay();

        }
        //shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
